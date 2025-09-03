import { IModule } from '../types/index'
import fs from 'fs'
import path from 'path'

export function write(input: string, output: string, moduleList: IModule[]) {
  moduleList.forEach(module => {
    // 创建模块目录
    fs.mkdirSync(path.join(output, module.moduleName), { recursive: true })
    // 复制 README.md 到 index.md
    fs.copyFileSync(path.join(input, module.moduleName, 'README.md'), path.join(output, module.moduleName, 'index.md'))
    // 复制 markdown
    if (module.markdownList.length > 0) {
      module.markdownList.forEach(markdown => {
        return fs.copyFileSync(path.join(input, module.moduleName, markdown.markdownName), path.join(output, module.moduleName, markdown.markdownName))
      })
    }
    // 复制 __image__
    if (module.imageList.length > 0) {
      fs.mkdirSync(path.join(output, module.moduleName, '__image__'), { recursive: true })
      module.imageList.forEach(image => {
        fs.copyFileSync(path.join(input, module.moduleName, '__image__', image.imageName), path.join(output, module.moduleName, '__image__', image.imageName))
      })
    }
  })
}
