export interface PageRequest {
  pageSize: number
  currentPage: number
}

export interface PageResponse<T> extends PageRequest {
  totalSize: number
  totalPage: number
  currentList: T[]
}

export interface R<T> {
  code: number
  message: string
  data: T
}

export type Optional<T, K extends keyof T> = Partial<Pick<T, K>> & Pick<T, Exclude<keyof T, K>>
