import lodash from 'lodash'

/**
 * 对象数组去重
 */
export function distinct<T>(data: T[]): T[] {
  return lodash.uniqWith<T>(data, lodash.isEqual)
}
