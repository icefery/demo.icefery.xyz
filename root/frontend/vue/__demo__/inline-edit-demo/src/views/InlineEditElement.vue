<script lang="ts" setup>
import { type PageRequest, type PageResponse } from '@/types/util'
import * as articleApi from '@/api/article'
import { type Article, findEventSub } from '@/api/article'
import { computed, onMounted, reactive } from 'vue'

type InlineEditArticle = Article & { editing: boolean; todo: Partial<Article> }

type State = {
  pageRequest: PageRequest
  pageResponse: PageResponse<Article>
  data: InlineEditArticle[]
}

function useInlineEditArticle(state: State) {
  const loadData = async () => {
    const body = await findEventSub(state.pageRequest)
    state.pageResponse = body.data
    state.data = state.pageResponse.currentList.map(e => ({
      ...e,
      editing: false,
      todo: {}
    }))
  }

  const submitSave = async (row: InlineEditArticle) => {
    await articleApi.saveEventSub(row.todo)
    await loadData()
  }

  const submitDelete = async (row: InlineEditArticle) => {
    if (row.id) {
      await articleApi.deleteEventSub(row.id)
      await loadData()
    }
  }

  const enterEditingMode = async (row: InlineEditArticle) => {
    row.editing = true
    row.todo = {
      id: row.id,
      topic: row.topic,
      title: row.title
    }
  }

  const cancelEditingMode = async (row: InlineEditArticle) => {
    row.editing = false
    row.todo = {}
    if (!row.id) {
      state.data.splice(0, 1)
    }
  }

  const enterCreatingMode = async () => {
    state.data.splice(0, 0, {
      editing: true,
      todo: {}
    })
  }

  const computedEditable = computed(() => state.data.findIndex(e => e.editing) === -1)

  return {
    loadData,
    submitSave,
    submitDelete,
    enterCreatingMode,
    enterEditingMode,
    cancelEditingMode,
    computedEditable
  }
}

const state = reactive<State>({
  pageRequest: { pageSize: 10, currentPage: 1 },
  pageResponse: { pageSize: 10, currentPage: 1, totalSize: 0, totalPage: 0, currentList: [] },
  data: []
})

const { loadData, submitSave, submitDelete, enterCreatingMode, enterEditingMode, cancelEditingMode, computedEditable } = useInlineEditArticle(state)

onMounted(async () => {
  await loadData()
})
</script>

<template>
  <el-table :data="state.data" border :row-key="(e: InlineEditArticle) => e.id">
    <el-table-column label="id" width="100" prop="id"></el-table-column>
    <el-table-column label="topic" width="100">
      <template #default="scope">
        <template v-if="scope.row.editing">
          <el-input size="small" v-model="scope.row.todo.topic" />
        </template>
        <template v-else>
          {{ scope.row.topic }}
        </template>
      </template>
    </el-table-column>
    <el-table-column label="title" width="100">
      <template #default="scope">
        <template v-if="scope.row.editing">
          <el-input size="small" v-model="scope.row.todo.title" />
        </template>
        <template v-else>
          {{ scope.row.title }}
        </template>
      </template>
    </el-table-column>
    <el-table-column label="content" prop="content"></el-table-column>
    <el-table-column>
      <template #header>
        <el-button size="small" @click="enterCreatingMode()" :disabled="!computedEditable">add</el-button>
      </template>
      <template #default="scope">
        <template v-if="scope.row.editing">
          <el-button size="small" type="warning" @click="cancelEditingMode(scope.row)">cancel</el-button>
          <el-button size="small" type="success" @click="submitSave(scope.row)">save</el-button>
        </template>
        <template v-else>
          <el-button size="small" type="primary" @click="enterEditingMode(scope.row)" :disabled="!computedEditable">edit</el-button>
          <el-button size="small" type="danger" @click="submitDelete(scope.row)" :disabled="!computedEditable">delete</el-button>
        </template>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination
    background
    layout="prev, pager, next, total"
    v-model:page-size="state.pageRequest.pageSize"
    v-model:current-page="state.pageRequest.currentPage"
    :total="state.pageResponse.totalSize"
    :page-count="state.pageResponse.totalPage"
    @current-change="loadData()"
    @size-change="loadData()"
  />
</template>
