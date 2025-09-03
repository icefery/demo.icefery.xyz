const CONFIG = {
  OAUTH2_AUTHORIZE_ENDPOINT: 'http://127.0.0.1:9000/oauth2/authorize',
  OAUTH2_TOKEN_ENDPOINT: 'http://127.0.0.1:9000/oauth2/token',
  OAUTH2_INTROSPECT_ENDPOINT: 'http://127.0.0.1:9000/oauth2/introspect',
  CLIENT_ID: 'book',
  CLIENT_SECRET: 'book'
}

async function main() {
  const getTokenResponseBody = await fetch(CONFIG.OAUTH2_TOKEN_ENDPOINT, {
    method: 'post',
    headers: {
      ['Authorization']: 'Basic ' + btoa(`${CONFIG.CLIENT_ID}:${CONFIG.CLIENT_SECRET}`),
      ['Content-Type']: 'application/x-www-form-urlencoded'
    },
    body: new URLSearchParams({
      ['grant_type']: 'client_credentials'
    })
  }).then(res => res.json())
  console.log('[获取 access_token]', getTokenResponseBody)
}

main()
