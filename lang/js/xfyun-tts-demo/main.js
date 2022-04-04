const path = require('path')

const { textToPcm } = require('./lib/text-2-pcm')
const { pcmToWav } = require('./lib/pcm-2-wav')
const { wavToBase64 } = require('./lib/wav-2-base64')

const APP_ID = '5ff425c9'
const API_SECRET = 'e60af3ce5e1b4223251c8ff23a434aa8'
const API_KEY = 'f981980c88cb93b2c36d4be5cd7f5b54'

const pcmPath = path.join(__dirname, './out/test.pcm')
const wavPath = path.join(__dirname, './out/test.wav')
const base64Path = path.join(__dirname, './out/test.txt')


async function main() {
  await textToPcm(APP_ID, API_SECRET, API_KEY, '她也曾经是希望', pcmPath)
  await pcmToWav(pcmPath, wavPath)
  await wavToBase64(wavPath, base64Path)
}

main()