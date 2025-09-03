import fs from 'fs'
import path from 'path'
import { GITIGNORE_PATH, OUTPUT_DOCUSAURUS_PAHT, INPUT_PATH, TEMP_PATH } from './config/index'
import { read } from './reader/index'
import { write } from './writer/docusaurus'

async function main() {
  const moduleList = read(INPUT_PATH, GITIGNORE_PATH)

  fs.mkdirSync(TEMP_PATH, { recursive: true })
  fs.writeFileSync(path.join(TEMP_PATH, 'moduleList.json'), JSON.stringify(moduleList))

  write(INPUT_PATH, OUTPUT_DOCUSAURUS_PAHT, moduleList)
}

main()
