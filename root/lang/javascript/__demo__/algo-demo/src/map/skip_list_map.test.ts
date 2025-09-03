import { describe, test } from '@jest/globals'
import { SkipListMap } from './skip_list_map'

describe('SkipListMap', () => {
  test('mock', () => {
    const map = SkipListMap.mock()
    console.log(map.keys())
    console.log(map.toString())
    console.log(map.structure())
  })

  test('put', () => {
    const map = new SkipListMap()
    for (let i = 0; i < 10; i++) {
      map.put(i, i)
      console.log(`key=${i} map.structure() => \n${map.structure()}\n`)
    }
  })

  test('remove', () => {
    const map = new SkipListMap()
    for (let i = 0; i < 10; i++) {
      map.put(i, i)
    }
    for (let i = 0; i < 10; i++) {
      const beforeStructure = map.structure()
      const oldValue = map.remove(i)
      const afterStructure = map.structure()
      console.log(
        `
key=${i}
oldValue=${oldValue}
beforeStructure=\n${beforeStructure}
afterStructure=\n${afterStructure}
        `
      )
    }
  })
})
