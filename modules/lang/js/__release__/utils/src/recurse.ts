import * as fs from 'fs'
import * as path from 'path'
import * as log4js from 'log4js'

export type Filter = (src: string, dst: string) => boolean

export type Processor = (src: string, dst: string) => void

const logger = log4js.getLogger('recurse')
logger.level = 'info'

export function recurse(src: string, dst: string, autoCreate: boolean, filter: Filter, processor: Processor): void {
  if (filter(src, dst)) {
    const stats = fs.statSync(src)
    if (stats.isDirectory()) {
      if (autoCreate) {
        try {
          fs.accessSync(dst, fs.constants.F_OK)
        } catch (e) {
          fs.mkdirSync(dst, { recursive: true })
          logger.info(`[${dst}] created.`)
        }
      }
      fs.readdirSync(src).forEach(sub => recurse(path.join(src, sub), path.join(dst, sub), autoCreate, filter, processor))
    } else if (stats.isFile()) {
      processor(src, dst)
    }
  } else {
    logger.info(`[${src}] skipped.`)
  }
}
