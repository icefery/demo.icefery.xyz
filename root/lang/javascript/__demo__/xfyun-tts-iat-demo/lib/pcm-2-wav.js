const fs = require('fs')
const path = require('path')
const { FileWriter } = require('wav')

async function pcmToWav(pcmPath, wavPath) {
  return new Promise((resolve, reject) => {
    // mkdir -p $(dirname wavPath)
    fs.mkdirSync(path.dirname(wavPath), { recursive: true })

    // wav 转 pcm
    const writeStream = new FileWriter(wavPath, { channels: 1, sampleRate: 16000 })
    fs.createReadStream(pcmPath).pipe(writeStream)

    console.log(`${pcmPath} => ${wavPath}`)

    resolve()
  })
}

module.exports = { pcmToWav }
