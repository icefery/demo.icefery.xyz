import fs from 'fs'
import path from 'path'
import { GITIGNORE_PATH, ROOT_PATH, TEMP_PATH } from '../config/index'
import { IDemo, IMarkdown, IModule } from '../types/index'
import { distinct } from '../utils/collection'
import { flatRead } from '../utils/fs'
import { extractTitle } from '../utils/markdown'

/**
 * 解析 __demo__
 */
function resolveDemo(root: string, flatList: string[]): IDemo[] {
  const segment = RegExp('/__demo__/')
  const ends = RegExp(`${['package.json', 'pom.xml', 'settings.gradle', 'settings.gradle.kts'].join('|')}$`)
  const cache: IDemo[] = []

  return distinct(
    flatList
      .filter(it => segment.test(it) && ends.test(it))
      .sort((a, b) => a.length - b.length)
      .map(it => {
        const tuple = it.split(segment)
        const moduleName = tuple[0]
        const demoName = path.dirname(tuple[1])
        const cacheValue = cache.find(it => it.moduleName === moduleName && RegExp(it.demoName).test(demoName))
        if (cacheValue) {
          return cacheValue
        } else {
          const demoReadme = path.join(demoName, 'README.md')
          const demoTitle = extractTitle(path.join(root, moduleName, '__demo__', demoReadme))
          const result = { moduleName, demoName, demoTitle, demoReadme }
          cache.push(result)
          return result
        }
      })
  )
}

/**
 * 解析 .md
 */
function resolveMarkdown(root: string, flatList: string[]): IMarkdown[] {
  const excludes = RegExp(`(${['__demo__', '__static__', '__image__', '__homework__', '__guide__', 'README.md'].join('|')})$`)
  const ends = RegExp(`(${['.md'].join('|')})`)
  return distinct(
    flatList
      .filter(it => !ends.test(it) && excludes.test(it))
      .map(it => {
        const moduleName = path.dirname(it) === '.' ? '' : path.dirname(it)
        const markdownName = it
        const markdownTitle = extractTitle(path.join(root, it))
        return { moduleName, markdownName, markdownTitle }
      })
  )
}

/**
 * 解析 module
 */
function resolveModule(root: string, flatList: string[]) {
  const excludes = RegExp(`(${['__demo__', '__static__', '__image__', '__homework__', '__guide__'].join('|')})`)
  const ends = RegExp(`(${['README.md'].join('|')})$`)
  return distinct(
    flatList
      .filter(it => !excludes.test(it) && ends.test(it))
      .map(it => {
        const moduleName = path.dirname(it) === '.' ? '' : path.dirname(it)
        const moduleReadme = it
        const moduleTitle = extractTitle(path.join(root, it))
        return { moduleName, moduleTitle, moduleReadme }
      })
  )
}

function resolve() {
  const gitignore = fs.readFileSync(GITIGNORE_PATH, 'utf-8')

  const flatList = flatRead(ROOT_PATH, gitignore)
  fs.writeFileSync(path.join(TEMP_PATH, 'flatList.json'), JSON.stringify(flatList))

  const moduleList = resolveModule(ROOT_PATH, flatList)
  const markdownList = resolveMarkdown(ROOT_PATH, flatList)
  const demoList = resolveDemo(ROOT_PATH, flatList)

  fs.writeFileSync(path.join(TEMP_PATH, 'moduleList.json'), JSON.stringify(moduleList))
  fs.writeFileSync(path.join(TEMP_PATH, 'markdownList.json'), JSON.stringify(markdownList))
  fs.writeFileSync(path.join(TEMP_PATH, 'demoList.json'), JSON.stringify(demoList))

  const result = moduleList.map<IModule>(module => {
    return {
      ...module,
      markdownList: markdownList.filter(md => md.moduleName === module.moduleName),
      demoList: demoList.filter(demo => demo.moduleName === module.moduleName)
    }
  })

  fs.writeFileSync(path.join(TEMP_PATH, 'result.json'), JSON.stringify(result))

  return result
}

resolve()
