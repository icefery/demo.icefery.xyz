const fs = require('fs')
const path = require('path')

async function base64ToFile(base64Path, filePath) {
  return new Promise((resolve, reject) => {
    // mkdir -p $(dirname filePath)
    fs.mkdirSync(path.dirname(filePath), { recursive: true })

    // 读取 base64
    const base64 = fs.readFileSync(base64Path, 'utf8')

    // base64 转 buffer
    const buffer = Buffer.from(base64, 'base64')

    // 写入文件
    fs.writeFileSync(filePath, buffer)

    console.log(`${base64Path} => ${filePath}`)

    resolve()
  })
}

async function fileToBase64(filePath, base64Path) {
  return new Promise((resolve, reject) => {
    // mkdir -p $(dirname target)
    fs.mkdirSync(path.dirname(base64Path), { recursive: true })

    // 文件转 base64
    const base64 = Buffer.from(fs.readFileSync(filePath), 'base64').toString('base64')

    // 写入文件
    fs.writeFileSync(base64Path, base64)

    console.log(`${filePath} => ${base64Path}`)

    resolve()
  })
}

module.exports = { base64ToFile, fileToBase64 }
