const fs = require('fs')
const path = require('path')
const CryptoJS = require('crypto-js')
const WebSocket = require('ws')

const config = {
  hostUrl: 'wss://tts-api.xfyun.cn/v2/tts',
  host: 'tts-api.xfyun.cn',
  appid: '5ff425c9',
  apiSecret: 'e60af3ce5e1b4223251c8ff23a434aa8',
  apiKey: 'f981980c88cb93b2c36d4be5cd7f5b54',
  text: '你好',
  uri: '/v2/tts'
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

async function textToPcm(appId, apiSecret, apiKey, text, pcmPath) {
  return new Promise((resolve, reject) => {
    // 更新配置
    Object.assign(config, { appId, apiSecret, apiKey })

    // WebSocket
    const ws = new WebSocket(buildURL())
    ws.on('open', () => {
      if (fs.existsSync(pcmPath)) {
        fs.rmSync(pcmPath)
      }
      const message = {
        common: { app_id: appId },
        business: { aue: 'raw', auf: 'audio/L16;rate=16000', vcn: 'xiaoyan', tte: 'UTF8' },
        data: { status: 2, text: Buffer.from(text).toString('base64') }
      }
      ws.send(JSON.stringify(message))
    }).on('message', message => {
      message = JSON.parse(message)

      if (message.code !== 0) {
        ws.close()
        return
      }

      const buffer = Buffer.from(message.data.audio, 'base64')
      fs.mkdirSync(path.dirname(pcmPath), { recursive: true })
      fs.writeFileSync(pcmPath, buffer, { flag: 'a' })

      if (message.data.status === 2) {
        ws.close()
        resolve()
      }
    })
  })
}

module.exports = { textToPcm }
