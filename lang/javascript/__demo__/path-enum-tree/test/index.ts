import * as fs from 'fs'
import { buildTree } from '../lib'

const data = [
  { id: 1, name: '1' },
  { id: 2, name: '2:2-1' },
  { id: 3, name: '2:2-2' },
  { id: 4, name: '2:2-2:2-2-1' },
  { id: 5, name: '3:3-1' }
]
const result = buildTree(data, 'name', ':')

fs.writeFileSync('./tree.json', JSON.stringify(result))
