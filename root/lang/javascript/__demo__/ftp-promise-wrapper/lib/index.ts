import * as path from 'path'
import * as fs from 'fs'
import Client from 'ftp'

export async function ls(config: Client.Options, src: string): Promise<Client.ListingElement[]> {
  return new Promise<Client.ListingElement[]>((resolve, reject) => {
    try {
      const client = new Client()
      client
        .on('ready', () => {
          client.list(src, (error, listing) => {
            if (error) {
              reject(error)
            } else {
              resolve(listing)
            }
          })
          client.end()
        })
        .connect(config)
    } catch (e) {
      reject(e)
    }
  })
}

export async function rm(config: Client.Options, src: string): Promise<void> {
  return new Promise<void>((resolve, reject) => {
    try {
      const client = new Client()
      client
        .on('ready', () => {
          client.delete(src, (error) => {
            if (error) {
              reject(error)
            } else {
              resolve()
            }
          })
          client.end()
        })
        .connect(config)
    } catch (e: any) {
      reject(e)
    }
  })
}

export async function mv(config: Client.Options, src: string, dst: string): Promise<void> {
  return new Promise<void>((resolve, reject) => {
    try {
      const client = new Client()
      client
        .on('ready', () => {
          if (path.dirname(src) !== path.dirname(dst)) {
            client.mkdir(dst, true, (error) => {
              if (error) {
                reject(error)
              }
            })
          }
          client.rename(src, dst, (error) => {
            if (error) {
              reject(error)
            } else {
              resolve()
            }
          })
          client.end()
        })
        .connect(config)
    } catch (e: any) {
      reject(e)
    }
  })
}

export async function mkdir(config: Client.Options, dst: string): Promise<void> {
  return new Promise<void>((resolve, reject) => {
    try {
      const client = new Client()
      client
        .on('ready', () => {
          client.mkdir(dst, true, (error) => {
            if (error) {
              reject(error)
            } else {
              resolve()
            }
          })
          client.end()
        })
        .connect(config)
    } catch (e: any) {
      reject(e)
    }
  })
}

export async function put(
  config: Client.Options,
  src: string,
  dst: string,
  callback: (total: number, current: number, progress: number) => void
): Promise<void> {
  return new Promise<void>((resolve, reject) => {
    try {
      const client = new Client()
      client
        .on('ready', () => {
          const total = fs.statSync(src).size
          let current = 0

          const readStream = fs.createReadStream(src)
          readStream.on('data', (chunk) => {
            current += chunk.length
            const progress = (current / total) * 100
            callback(total, current, progress)
          })

          client.put(readStream, dst, (error) => {
            if (error) {
              reject(error)
            } else {
              resolve()
            }
          })
          client.end()
        })
        .connect(config)
    } catch (e: any) {
      reject(e)
    }
  })
}

export function get(
  config: Client.Options,
  src: string,
  dst: string,
  callback: (total: number, current: number, progress: number) => void
): Promise<void> {
  return new Promise<void>((resolve, reject) => {
    try {
      const client = new Client()
      client
        .on('ready', () => {
          client.get(src, (error, stream) => {
            if (error) {
              reject(error)
            } else {
              fs.mkdirSync(path.dirname(dst), { recursive: true })
              const writeStream = fs.createWriteStream(dst)
              stream.pipe(writeStream)
              resolve()
            }
          })
          client.end()
        })
        .connect(config)
    } catch (e: any) {
      reject(e)
    }
  })
}
