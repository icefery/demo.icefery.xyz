const fs = require('fs')
const CryptoJS = require('crypto-js')
const WebSocket = require('ws')

const DIVIDER = '=========='

// 系统配置
const config = {
  hostUrl: 'wss://iat-api.xfyun.cn/v2/iat',
  host: 'iat-api.xfyun.cn',
  appid: '5ff425c9',
  apiSecret: 'e60af3ce5e1b4223251c8ff23a434aa8',
  apiKey: 'f981980c88cb93b2c36d4be5cd7f5b54',
  file: './16k_10.pcm',
  uri: '/v2/iat',
  highWaterMark: 1280
}

// 帧定义
const FRAME = {
  STATUS_FIRST_FRAME: 0,
  STATUS_CONTINUE_FRAME: 1,
  STATUS_LAST_FRAME: 2
}

function buildURL() {
  const date = new Date().toUTCString()
  const signatureOrigin = `host: ${config.host}\ndate: ${date}\nGET ${config.uri} HTTP/1.1`
  const signatureSha = CryptoJS.HmacSHA256(signatureOrigin, config.apiSecret)
  const signature = CryptoJS.enc.Base64.stringify(signatureSha)
  const authorizationOrigin = `api_key="${config.apiKey}", algorithm="hmac-sha256", headers="host date request-line", signature="${signature}"`
  const authStr = CryptoJS.enc.Base64.stringify(CryptoJS.enc.Utf8.parse(authorizationOrigin))
  return `wss://${config.host}${config.uri}?authorization=${authStr}&date=${date}&host=${config.host}`
}

async function pcmToText(appId, apiSecret, apiKey, pcmPath) {
  // 更新配置
  Object.assign(config, { appId, apiSecret, apiKey, file: pcmPath })

  return new Promise((resolve, reject) => {
    // 设置当前临时状态为初始化
    let status = FRAME.STATUS_FIRST_FRAME
    // 识别结果
    let iatResult = []

    const url = buildURL()
    const ws = new WebSocket(url)

    const send = data => {
      const frameDataSection = {
        status: status,
        format: 'audio/L16;rate=16000',
        audio: data.toString('base64'),
        encoding: 'raw'
      }
      let frame = null
      switch (status) {
        case FRAME.STATUS_FIRST_FRAME:
          frame = {
            common: { app_id: config.appid },
            business: { language: 'zh_cn', domain: 'iat', accent: 'mandarin', dwa: 'wpgs' },
            data: frameDataSection
          }
          status = FRAME.STATUS_CONTINUE_FRAME
          break
        case FRAME.STATUS_CONTINUE_FRAME:
        case FRAME.STATUS_LAST_FRAME:
          frame = {
            data: frameDataSection
          }
          break
        default:
          frame = ''
      }
      ws.send(JSON.stringify(frame))
    }

    let str = ''
    ws.on('close', () => {
      arr = str.split(DIVIDER)
      str = arr.length > 1 ? arr[1] : ''
      resolve(str)
    })
      .on('error', error => console.log(error))
      .on('open', () => {
        str = ''
        const readStream = fs.createReadStream(config.file, { highWaterMark: config.highWaterMark })
        readStream.on('data', chunk => send(chunk))
        // 最终帧发送结束
        readStream.on('end', () => {
          status = FRAME.STATUS_LAST_FRAME
          send('')
        })
      })
      .on('message', (message, error) => {
        message = JSON.parse(message)
        if (message.code !== 0) {
          return
        }

        if (message.data.status === 2) {
          str += DIVIDER
          ws.close()
        }

        iatResult[message.data.result.sn] = message.data.result
        if (message.data.result.pgs === 'rpl') {
          message.data.result.rg.forEach(i => (iatResult[i] = null))
        }

        iatResult.forEach(i => {
          if (i !== null) {
            i.ws.forEach(j => {
              j.cw.forEach(k => (str += k.w))
            })
          }
        })
      })
  })
}

module.exports = { pcmToText }
