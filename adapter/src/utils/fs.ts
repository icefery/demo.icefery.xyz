import fs from 'fs'
import ignore from 'ignore'
import path from 'path'

/**
 * 扁平化读取目录文件
 */
export function flatRead(root: string, gitignore: string, input: string = '', output: string[] = []): string[] {
  const content = fs.readFileSync(gitignore, 'utf-8')
  const ig = ignore().add(content)
  const fp = path.join(root, input)
  const fr = path.join('root', input)
  if (input === '' || !ig.ignores(fr)) {
    const stat = fs.statSync(fp)
    if (stat.isDirectory()) {
      fs.readdirSync(fp).forEach(it => flatRead(root, gitignore, path.join(input, it), output))
    } else if (stat.isFile()) {
      output.push(input)
    }
  }
  return output
}
