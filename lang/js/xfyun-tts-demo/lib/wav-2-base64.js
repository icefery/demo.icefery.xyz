const fs = require('fs')
const path = require('path')

async function wavToBase64(source, target) {
  return new Promise((resolve, reject) => {
    fs.mkdirSync(path.dirname(target), { recursive: true })

    const buffer = Buffer.from(source, 'base64')
    fs.writeFileSync(target, buffer.toString('base64'))

    console.log(`${source} => ${target}`)

    resolve()
  })
}

module.exports = { wavToBase64 }
