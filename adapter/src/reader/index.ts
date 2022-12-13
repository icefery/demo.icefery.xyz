import path from 'node:path'
import url from 'node:url'
import fs from 'node:fs'

const SOURCE_PATH = path.join(path.dirname(url.fileURLToPath(import.meta.url)), '../../../root')
const TARGET_PATH = path.join(path.dirname(url.fileURLToPath(import.meta.url)), '../../../docusaurus/docs')

export function flatRead(root: string, input: string = '/', output: string[] = []) {
  const items = ['__static__', '__demo__', '__homework__', '__guide__']
  const f = path.join(root, input)
  const stat = fs.statSync(f)
  if (stat.isDirectory() && !RegExp(`(${items.join('|')})`).test(input)) {
    output.push(input)
    fs.readdirSync(f).map(it => flatRead(root, path.join(input, it), output))
  }
  return output
}

const output = flatRead(SOURCE_PATH, '/', [])
fs.writeFileSync('./test.json', JSON.stringify(output))
