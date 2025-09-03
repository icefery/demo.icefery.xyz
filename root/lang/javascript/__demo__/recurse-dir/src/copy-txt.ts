import * as path from 'path'
import * as fs from 'fs'
import { recurse } from './recurse'

export function copyTxt(src: string, dst: string): void {
  recurse(
    src,
    dst,
    true,
    src => fs.statSync(src).isDirectory() || path.extname(src) === '.txt',
    (src, dst) => fs.createReadStream(src).pipe(fs.createWriteStream(dst))
  )
}
