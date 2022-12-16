import fs from 'fs'
import path from 'path'
import { GITIGNORE_PATH, ROOT_PATH, TEMP_PATH } from './config/index'
import { resolve } from './reader/index'

async function main() {
  const moduleList = resolve(ROOT_PATH, GITIGNORE_PATH)

  fs.mkdirSync(TEMP_PATH, { recursive: true })
  fs.writeFileSync(path.join(TEMP_PATH, 'moduleList.json'), JSON.stringify(moduleList))
}

main()
