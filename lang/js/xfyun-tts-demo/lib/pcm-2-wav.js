const fs = require('fs')
const path = require('path')
const { FileWriter } = require('wav')

async function pcmToWav(source, target) {
  return new Promise((resolve, reject) => {
    fs.mkdirSync(path.dirname(target), { recursive: true })

    const writeStream = new FileWriter(target, { channels: 1, sampleRate: 16000 })
    fs.createReadStream(source).pipe(writeStream)

    console.log(`${source} => ${target}`)

    resolve()
  })
}

module.exports = { pcmToWav }
