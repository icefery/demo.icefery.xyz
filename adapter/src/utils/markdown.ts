import fs from 'fs'
import { marked } from 'marked'
import path from 'path'

/**
 * 提取 markdown 一级标题
 */
export function extractTitle(f: string): string {
  const content = fs.readFileSync(f, 'utf-8')
  const ast = marked.lexer(content)
  const h1 = ast.find(token => (token.type = 'heading' && token.depth === 1))
  return h1?.text || path.basename(f, '.md')
}
