import puppeteer from 'puppeteer'

const CONFIG = {
  OAUTH2_AUTHORIZE_ENDPOINT: 'http://127.0.0.1:9000/oauth2/authorize',
  OAUTH2_TOKEN_ENDPOINT: 'http://127.0.0.1:9000/oauth2/token',
  OAUTH2_INTROSPECT_ENDPOINT: 'http://127.0.0.1:9000/oauth2/introspect',
  USERNAME: 'admin',
  PASSWORD: 'admin',
  CLIENT_ID: 'book',
  CLIENT_SECRET: 'book',
  REDIRECT_URI: 'https://baidu.com'
}

async function main() {
  const browser = await puppeteer.launch({
    headless: false,
    slowMo: 50,
    timeout: 5 * 60 * 60 * 1000
  })
  const page = await browser.newPage()

  // 授权
  await page.goto(`${CONFIG.OAUTH2_AUTHORIZE_ENDPOINT}?response_type=code&client_id=${CONFIG.CLIENT_ID}&redirect_uri=${CONFIG.REDIRECT_URI}`)
  console.log('[授权]')

  // 模拟登录
  await page.type('#username', CONFIG.USERNAME)
  await page.type('#password', CONFIG.PASSWORD)
  await page.click('body > div > form > button')
  console.log('[模拟登录]')

  // 获取 code
  const code = new URL(page.url()).searchParams.get('code')
  console.log(`[获取 code]`, code)

  // 获取 access_token
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
  console.log(`[获取 access_token]`, getTokenResponseBody)

  // 校验 access_token
  const checkTokenResponseBody = await fetch(CONFIG.OAUTH2_INTROSPECT_ENDPOINT, {
    method: 'post',
    headers: {
      ['Authorization']: 'Basic ' + btoa(`${CONFIG.CLIENT_ID}:${CONFIG.CLIENT_SECRET}`),
      ['Content-Type']: 'application/x-www-form-urlencoded'
    },
    body: new URLSearchParams({
      ['token']: getTokenResponseBody['access_token']
    })
  }).then(res => res.json())
  console.log('[校验 access_token]', checkTokenResponseBody)

  // 刷新 access_token
  const refreshTokenResponseBody = await fetch(CONFIG.OAUTH2_TOKEN_ENDPOINT, {
    method: 'post',
    headers: {
      ['Authorization']: 'Basic ' + btoa(`${CONFIG.CLIENT_ID}:${CONFIG.CLIENT_SECRET}`),
      ['Content-Type']: 'application/x-www-form-urlencoded'
    },
    body: new URLSearchParams({
      ['grant_type']: 'refresh_token',
      ['refresh_token']: getTokenResponseBody['refresh_token']
    })
  }).then(res => res.json())
  console.log('[刷新 access_token]', refreshTokenResponseBody)

  await browser.close()
}

main()
