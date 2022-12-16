export interface IModule {
  moduleName: string
  moduleTitle: string
  moduleReadme: string
  markdownList?: IMarkdown[]
  imageList?: string[]
  staticList?: string[]
  demoList?: IDemo[]
  homeworkList?: string[]
}

export interface IMarkdown {
  moduleName: string
  markdownName: string
  markdownTitle: string
}

export interface IDemo {
  moduleName: string
  demoName: string
  demoTitle: string
}

export interface IHomework {
  moduleName: string
  homeworkName: string
  homeworkTitle: string
}
