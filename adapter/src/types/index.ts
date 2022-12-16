export interface IModule {
  moduleName: string
  moduleTitle: string
  moduleReadme: string
  markdownList?: IMarkdown[]
  guideList?: IGuide[]
  imageList?: IImage[]
  staticList?: IStatic[]
  demoList?: IDemo[]
  homeworkList?: IHomework[]
}

export interface IMarkdown {
  moduleName: string
  markdownName: string
  markdownTitle: string
}

export interface IGuide {
  moduleName: string
  guideName: string
}

export interface IImage {
  moduleName: string
  imageName: string
}

export interface IStatic {
  moduleName: string
  staticName: string
}

export interface IDemo {
  moduleName: string
  demoName: string
}

export interface IHomework {
  moduleName: string
  homeworkName: string
}

export type IUnion = IModule | IMarkdown | IGuide | IImage | IStatic | IDemo | IHomework
