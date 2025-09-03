import express from 'express'
import cors from 'cors'

const app = express()
app.use(express.json())
app.use(express.urlencoded({ extended: true }))
app.use(cors())

/**
 * @typedef {{id: number, topic?: string, title?: string, content?: string}} Article
 * @type Article[]
 */
const DATA = Array.from({ length: 100 }, (e, i) => ({
  id: i + 1,
  topic: 'topic-a',
  title: `title-${i + 1}`,
  content: `content-${i + 1}`
}))

app
  .get('/api/event-sub/find', (req, res) => {
    const pageSize = parseInt(req.query.pageSize)
    const currentPage = parseInt(req.query.currentPage)
    const totalSize = DATA.length
    const totalPage = Math.ceil(DATA.length / pageSize)
    const currentList = DATA.slice((currentPage - 1) * pageSize, (currentPage - 1) * pageSize + pageSize)
    res.json({
      code: 0,
      message: 'success',
      data: {
        pageSize,
        currentPage,
        totalSize,
        totalPage,
        currentList
      }
    })
  })
  .post('/api/event-sub/save', (req, res) => {
    const omitEmpty = obj => Object.entries(obj).reduce((acc, [k, v]) => (v && (acc[k] = v) && acc) || acc, {})
    /**
     * @type {Partial<Article>}
     */
    const todo = req.body
    if (todo.id) {
      const index = DATA.findIndex(e => e.id === todo.id)
      if (index !== -1) {
        Object.assign(DATA[index], omitEmpty(todo))
      }
    } else {
      todo.id = DATA.length
      DATA.push(todo)
    }
    res.json({ code: 0, message: 'success', data: todo.id })
  })
  .delete('/api/event-sub/delete/:id', (req, res) => {
    const id = parseInt(req.params.id)
    const index = DATA.findIndex(e => e.id === id)
    if (index !== -1) {
      DATA.splice(index, 1)
    }
    res.json({ code: 0, message: 'success', data: id })
  })
  .listen(3000, () => console.log('listening on :3000'))
