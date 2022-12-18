import path from 'path'
import { IDemo, IGuide, IHomework, IImage, IMarkdown, IModule, IStatic, IUnion } from '../types/index'
import { distinct } from '../utils/collection'
import { flatRead } from '../utils/fs'
import { extractTitle } from '../utils/markdown'

/**
 * 解析 `module`
 */
export function resolveModule(root: string, flatList: string[]): IModule[] {
  const excludes = RegExp(`(${['__demo__', '__static__', '__image__', '__homework__', '__guide__'].join('|')})`)
  const ends = RegExp(`(${['README.md'].join('|')})$`)
  return distinct(
    flatList
      .filter(it => !excludes.test(it) && ends.test(it))
      .sort((a, b) => a.length - b.length)
      .map<IModule>(it => {
        const moduleName = path.dirname(it) === '.' ? '' : path.dirname(it)
        const moduleReadme = it
        const moduleTitle = extractTitle(path.join(root, it))
        return { moduleName, moduleTitle, moduleReadme }
      })
  )
}

/**
 * 解析 `markdown`
 */
export function resolveMarkdown(root: string, flatList: string[]): IMarkdown[] {
  const excludes = RegExp(`(${['__demo__', '__static__', '__image__', '__homework__', '__guide__', 'README.md'].join('|')})$`)
  const ends = RegExp(`(${['.md'].join('|')})`)
  return distinct(
    flatList
      .filter(it => !excludes.test(it) && ends.test(it))
      .sort((a, b) => a.length - b.length)
      .map<IMarkdown>(it => {
        const moduleName = path.dirname(it) === '.' ? '' : path.dirname(it)
        const markdownName = path.basename(it)
        const markdownTitle = extractTitle(path.join(root, it))
        return { moduleName, markdownName, markdownTitle }
      })
  )
}

/**
 * 解析 `__guide__`
 */
export function resolveGuide(root: string, flatList: string[]): IGuide[] {
  const segment = '/__guide__/'
  return distinct(
    flatList
      .filter(it => RegExp(segment).test(it))
      .sort((a, b) => a.length - b.length)
      .map<IGuide>(it => {
        const tuple = it.split(segment)
        const moduleName = tuple[0]
        const guideName = tuple[1]
        return { moduleName, guideName }
      })
  )
}

/**
 * 解析 `__image__`
 */
export function resolveImage(root: string, flatList: string[]): IImage[] {
  const segment = '/__image__/'
  return distinct(
    flatList
      .filter(it => RegExp(segment).test(it))
      .sort((a, b) => a.length - b.length)
      .map<IImage>(it => {
        const tuple = it.split(segment)
        const moduleName = tuple[0]
        const imageName = tuple[1]
        return { moduleName, imageName }
      })
  )
}

/**
 * 解析 `__static__`
 */
export function resolveStatic(root: string, flatList: string[]): IStatic[] {
  const segment = '/__static__/'
  return distinct(
    flatList
      .filter(it => RegExp(segment).test(it))
      .sort((a, b) => a.length - b.length)
      .map<IStatic>(it => {
        const tuple = it.split(segment)
        const moduleName = tuple[0]
        const staticName = tuple[1]
        return { moduleName, staticName }
      })
  )
}

/**
 * 解析 `__demo__`
 */
export function resolveDemo(root: string, flatList: string[]): IDemo[] {
  const segment = '/__demo__/'
  const ends = RegExp(`${['package.json', 'pom.xml', 'settings.gradle.kts', 'settings.gradle'].join('|')}$`)
  return distinct(
    flatList
      .filter(it => RegExp(segment).test(it) && ends.test(it))
      .sort((a, b) => a.length - b.length)
      .map<IDemo>(it => {
        const tuple = it.split(segment)
        const moduleName = tuple[0]
        const demoName = path.dirname(tuple[1])
        return { moduleName, demoName }
      })
  )
}

/**
 * 解析 `__homework__`
 */
export function resolveHomework(root: string, flatList: string[]): IHomework[] {
  const segment = '/__homework__/'
  const ends = RegExp(`${['package.json', 'pom.xml', 'settings.gradle.kts', 'settings.gradle'].join('|')}$`)
  return distinct(
    flatList
      .filter(it => RegExp(segment).test(it) && ends.test(it))
      .sort((a, b) => a.length - b.length)
      .map<IHomework>(it => {
        const tuple = it.split(segment)
        const moduleName = tuple[0]
        const homeworkName = path.dirname(tuple[1])
        return { moduleName, homeworkName }
      })
  )
}

export function read(root: string, gitignore: string): IModule[] {
  const flatList = flatRead(root, gitignore)

  const moduleList = resolveModule(root, flatList)
  const markdownList = resolveMarkdown(root, flatList)
  const guideList = resolveGuide(root, flatList)
  const imageList = resolveImage(root, flatList)
  const staticList = resolveStatic(root, flatList)
  const demoList = resolveDemo(root, flatList)
  const homeworkList = resolveHomework(root, flatList)

  return moduleList.map<IModule>(module => {
    const condition = (it: IUnion) => it.moduleName === module.moduleName
    return {
      ...module,
      markdownList: markdownList.filter(condition),
      guideList: guideList.filter(condition),
      imageList: imageList.filter(condition),
      staticList: staticList.filter(condition),
      demoList: demoList.filter(condition),
      homeworkList: homeworkList.filter(condition)
    }
  })
}
