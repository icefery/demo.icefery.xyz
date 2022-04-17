const path = require('path')
const fs = require('fs')

const { textToPcm } = require('./lib/text-2-pcm')
const { pcmToText } = require('./lib/pcm-2-text')
const { pcmToWav } = require('./lib/pcm-2-wav')
const { fileToBase64, base64ToFile } = require('./lib/base64-util')

const APP_ID = '5ff425c9'
const API_SECRET = 'e60af3ce5e1b4223251c8ff23a434aa8'
const API_KEY = 'f981980c88cb93b2c36d4be5cd7f5b54'

const text = '你好'

async function main() {
  const pcmPath = path.join(__dirname, `out/${text}.pcm`)
  const pcmBase64Path = path.join(__dirname, `out/${text}.pcm.txt`)

  const wavPath = path.join(__dirname, `out/${text}.wav`)
  const wavBase64Path = path.join(__dirname, `out/${text}.wav.txt`)

  // 文字转 pcm
  await textToPcm(APP_ID, API_SECRET, API_KEY, text, pcmPath)
  // pcm 转 base64
  await fileToBase64(pcmPath, pcmBase64Path)
  // pcm 转 wav
  await pcmToWav(pcmPath, wavPath)
  // wav 转 base64
  await fileToBase64(wavPath, wavBase64Path)

  const testPcmPath = path.join(__dirname, `out/test.pcm`)

  // base64 转 pcm
  base64ToFile(pcmBase64Path, testPcmPath)
  // pcm 转文件
  const result = await pcmToText(APP_ID, API_SECRET, API_KEY, testPcmPath)
  console.log(result)
}

main()
