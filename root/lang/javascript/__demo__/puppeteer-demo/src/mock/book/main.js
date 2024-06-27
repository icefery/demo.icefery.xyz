import express from 'express'
import path from 'node:path'

const CONFIG = {
  OAUTH2_AUTHORIZE_ENDPOINT: 'http://127.0.0.1:9000/oauth2/authorize',
  OAUTH2_TOKEN_ENDPOINT: 'http://127.0.0.1:9000/oauth2/token',
  OAUTH2_INTROSPECT_ENDPOINT: 'http://127.0.0.1:9000/oauth2/introspect',
  CLIENT_ID: 'book',
  CLIENT_SECRET: 'book',
  REDIRECT_URI: 'http://127.0.0.1:8080/callback',
  FRONTEND_URI: 'http://127.0.0.1:8080'
}

const frontendMiddleware = express.static(path.resolve(import.meta.dirname, 'public'))

const authorizationMiddleware = async (req, res, next) => {
  const accessToken = req.header('Authorization')?.split(' ')?.[1]
  if (!accessToken) {
    res.sendStatus(401)
  } else {
    /**
     * @type {{
     *   active:     boolean,
     *   sub:        string,
     *   aud:        string[],
     *   nbf:        number,
     *   iss:        string,
     *   exp:        number,
     *   iat:        number,
     *   jti:        string,
     *   client_id:  string,
     *   token_type: string,
     * }}
     */
    const introspectResponseBody = await fetch(CONFIG.OAUTH2_INTROSPECT_ENDPOINT, {
      method: 'post',
      headers: {
        ['Authorization']: 'Basic ' + btoa(`${CONFIG.CLIENT_ID}:${CONFIG.CLIENT_SECRET}`),
        ['Content-Type']: 'application/x-www-form-urlencoded'
      },
      body: new URLSearchParams({
        ['token']: accessToken
      })
    }).then(res => res.json())
    if (!introspectResponseBody.active) {
      res.sendStatus(401)
    } else {
      next()
    }
  }
}

const callbackRouter = express.Router().get('/', async (req, res) => {
  const code = req.query['code']
  /**
   * @type {{
   *   access_token:  string,
   *   refresh_token: string,
   *   token_type:    string,
   *   expires_in:    number
   * }}
   */
  const getTokenResponseBody = await fetch(CONFIG.OAUTH2_TOKEN_ENDPOINT, {
    method: 'post',
    headers: {
      ['Authorization']: 'Basic ' + btoa(`${CONFIG.CLIENT_ID}:${CONFIG.CLIENT_SECRET}`),
      ['Content-Type']: 'application/x-www-form-urlencoded'
    },
    body: new URLSearchParams({
      ['grant_type']: 'authorization_code',
      ['code']: code,
      ['redirect_uri']: CONFIG.REDIRECT_URI
    })
  }).then(res => res.json())
  res.redirect(`${CONFIG.FRONTEND_URI}?access_token=${getTokenResponseBody.access_token}`)
})

const apiRouter = express.Router().post('/books', (req, res) => {
  const books = Array.from({ length: 4 }, (_, i) => ({
    id: i + 1,
    title: `book-${i + 1}`,
    author: `author-${i + 1}`
  }))
  res.json({ data: books })
})

function main() {
  const port = 8080
  const app = express()
  app.use(frontendMiddleware)
  app.use('/callback', callbackRouter)
  app.use('/api', authorizationMiddleware, apiRouter)
  app.listen(port, () => console.log(`Listening on :${port}`))
}

main()
