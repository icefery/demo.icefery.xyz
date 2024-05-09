import { type PageRequest, type PageResponse, type R } from '@/types/util'

export type Article = {
  id?: number
  topic?: string
  title?: string
  content?: string
}

const BASE_URL = 'http://127.0.0.1:3000/api'

export async function findEventSub(params: { [p: string]: any } & PageRequest = { pageSize: 10, currentPage: 1 }): Promise<R<PageResponse<Article>>> {
  const query = Object.keys(params)
    .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(params[key])}`)
    .join('&')
  return fetch(`${BASE_URL}/event-sub/find?${query}`, {
    method: 'get',
    headers: {}
  }).then(response => response.json())
}

export async function saveEventSub(todo: Partial<Article>): Promise<R<number>> {
  return fetch(`${BASE_URL}/event-sub/save`, {
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(todo)
  }).then(response => response.json())
}

export async function deleteEventSub(id: number): Promise<R<number>> {
  return fetch(`${BASE_URL}/event-sub/delete/${id}`, {
    method: 'delete',
    headers: {}
  }).then(response => response.json())
}
