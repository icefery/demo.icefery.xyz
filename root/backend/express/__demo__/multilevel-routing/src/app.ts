import express from 'express'
import bodyParser from 'body-parser'

const app = express()
app.use(bodyParser.urlencoded({ extended: true }))
app.use(bodyParser.json())

interface Post {
  title: string
  category: string[]
  tags: string[]
}

interface User {
  username: string
  posts: Post[]
}

const USERS: User[] = [
  {
    username: 'user-1',
    posts: [
      { title: 'post-1', category: ['category-1', 'category-1-1'], tags: ['tag-1'] },
      { title: 'post-2', category: ['category-1', 'category-1-2'], tags: ['tag-1', 'tag-2', 'tag-3'] },
      { title: 'post-3', category: ['category-2'], tags: ['tag-3'] }
    ]
  }
]

app
  .get('/', (req, res) => res.json({ data: 'Hello World' }))
  .use(
    '/users',
    express
      .Router()
      .get('/', (req, res) => res.json({ data: USERS }))
      .get('/:username', (req, res) => {
        const { username } = req.params
        const user = USERS.find(it => it.username === username)
        if (!user) {
          res.json({ code: '404', message: `user ${username} not exist` })
          return
        }
        res.json({ data: user })
      })
      .use(
        '/:username/posts',
        (req, res, next) => {
          const { username } = req.params
          const user = USERS.find(it => it.username === username)
          if (!user) {
            res.json({ code: '404', message: `user ${username} not exist` })
            return
          }
          req.query['user'] = user as any
          next()
        },
        express
          .Router()
          .get('/', (req, res) => {
            const user = req.query['user'] as unknown as User
            res.json({ data: user.posts })
          })
          .get('/:title', (req, res) => {
            const { title } = req.params
            const user = req.query['user'] as unknown as User
            const post = user.posts.find(it => it.title === title)
            if (!post) {
              res.json({ code: '404', message: `post ${title} not exist` })
              return
            }
            res.json({ data: post })
          })
          .post('/', (req, res) => {
            const post = req.body as Post
            const user = req.query['user'] as unknown as User
            user.posts.push(post)
            res.json({})
          })
          .put('/:title', (req, res) => {
            const { title } = req.params
            const user = req.query['user'] as unknown as User
            const post = req.body as Post
            const index = user.posts.findIndex(it => it.title === title)
            if (!index) {
              res.json({ code: '404', message: `post ${title} not exist` })
              return
            }
            user.posts[index] = post
            res.json({})
          })
          .delete('/', (req, res) => {
            const user = req.query['user'] as unknown as User
            user.posts.splice(0, user.posts.length)
            res.json({})
          })
          .delete('/:title', (req, res) => {
            const { title } = req.params
            const user = req.query['user'] as unknown as User
            const index = user.posts.findIndex(it => it.title === title)
            if (!index) {
              res.json({ code: '404', message: `post ${title} not exist` })
              return
            }
            user.posts.splice(index, 1)
            res.json({})
          })
      )
      .use(
        '/:username/categories/',
        (req, res, next) => {
          const { username } = req.params
          const user = USERS.find(it => it.username === username)
          if (!user) {
            res.json({ code: '404', message: `user ${username} not exist` })
            return
          }
          req.query['user'] = user as any
          next()
        },
        express.Router().get('/', (req, res) => {
          const user = req.query['user'] as unknown as User
          const set = new Set()
          user.posts.forEach(p => set.add(p.category))
          res.json({ data: Array.from(set) })
        })
      )
      .use(
        '/:username/tags',
        (req, res, next) => {
          const { username } = req.params
          const user = USERS.find(it => it.username === username)
          if (!user) {
            res.json({ code: '404', message: `user ${username} not exist` })
            return
          }
          req.query['user'] = user as any
          next()
        },
        express.Router().get('/', (req, res) => {
          const user = req.query['user'] as unknown as User
          const set = new Set()
          user.posts.forEach(p => p.tags.forEach(t => set.add(t)))
          res.json({ data: Array.from(set) })
        })
      )
  )

const host = '127.0.0.1'
const port = 3000
app.listen(port, host, () => console.log(`http://${host}:${port}`))
