<template>
  <view class="u-margin-left-20 u-margin-right-20">
    <!-- 未登录 -->
    <template v-if="login.username === ''">
      <u-form>
        <u-form-item><u-input v-model="tenant.username" :border="true" :clearable="false" placeholder="账号" input-align="center" /></u-form-item>
        <u-form-item>
          <u-input v-model="tenant.password" :border="true" :password-icon="false" placeholder="密码" input-align="center" type="password" :clearable="false" />
        </u-form-item>
        <u-form-item>
          <u-button style="width: 50%" class="u-margin-right-10" :disabled="tenant.username === '' || tenant.password === ''" @click="doLogin()">
            登录
          </u-button>
          <u-button style="width: 50%" class="u-margin-left-10" :disabled="tenant.username === '' || tenant.password === ''" @click="doRegister()">
            注册
          </u-button>
        </u-form-item>
      </u-form>
    </template>
    <!--  已登录 -->
    <template v-else>
      <u-table class="u-margin-top-20 u-margin-bottom-10">
        <u-tr>
          <u-td><u-tag :text="login.id" @click="() => doCopy(login.id)" /></u-td>
          <u-td><u-tag :text="login.username" @click="() => doCopy(login.username)" /></u-td>
        </u-tr>
      </u-table>

      <u-button class="u-margin-top-10 u-margin-bottom-10" style="width: 100%" @click="doLogout()" size="mini">注销</u-button>
      <template v-if="quesionnaires.length >= 1">
        <u-table class="u-margin-top-10">
          <u-tr>
            <u-th>问卷号</u-th>
            <u-th>问卷标题</u-th>
          </u-tr>
          <template v-for="quesionnaire in quesionnaires">
            <u-tr>
              <u-td><u-tag :text="quesionnaire.id" @click="() => doCopy(quesionnaire.id)" /></u-td>
              <u-td><u-tag :text="quesionnaire.title" @click="() => doCopy(quesionnaire.title)" /></u-td>
            </u-tr>
          </template>
        </u-table>
      </template>
    </template>
  </view>
</template>

<script>
export default {
  data() {
    return {
      login: { id: '', username: '' },
      tenant: { username: '', password: '' },
      quesionnaires: []
    }
  },
  onPullDownRefresh() {
    this.refresh()
    setTimeout(() => {
      uni.stopPullDownRefresh()
    }, 500)
  },
  onShow() {
    this.refresh()
  },
  methods: {
    doCopy(text) {
      this.$copyText(text).then(() => {
        uni.showToast({ title: '复制成功' })
      })
    },
    doRegister() {
      uni.request({
        url: 'http://localhost:9000/tenant/register',
        method: 'POST',
        data: JSON.stringify(this.tenant),
        header: { 'Content-Tye': 'application/json' },
        success: response => {
          const { code, message, data } = response.data
          if (response.statusCode === 200 && code === 0) {
            uni.showToast({ title: '注册成功' })
            this.refresh()
          } else {
            uni.showToast({ title: message || '无响应' })
          }
        }
      })
    },
    doLogin() {
      uni.request({
        url: ' http://localhost:9000/tenant/login',
        method: 'POST',
        data: this.tenant,
        header: { 'Content-Type': 'application/x-www-form-urlencoded' },
        success: response => {
          const { code, message, data } = response.data
          if (response.statusCode === 200 && code === 0) {
            uni.setStorage({
              key: 'login',
              data: { jwt: data.jwt, tenant: data.tenant }
            })
            uni.showToast({ title: '登录成功' })
            this.refresh()
          } else {
            uni.showToast({ title: message || '无响应' })
          }
        }
      })
    },
    doLogout() {
      uni.removeStorage({
        key: 'login',
        success: () => {
          uni.showToast({ title: '注销成功' })
          this.refresh()
        }
      })
    },

    getQuestionnaireList(tenantId, jwt) {
      uni.request({
        url: `http://localhost:9000/${tenantId}/questionnaire/list`,
        method: 'GET',
        header: { Authorization: jwt },
        success: response => {
          console.log(response)
          const { code, message, data } = response.data
          if (response.statusCode === 200 && code === 0) {
            this.quesionnaires = data
            console.log('拉取问卷列表成功')
          } else {
            console.log('拉取问卷列表失败', message)
          }
        }
      })
    },

    refresh() {
      const login = uni.getStorageSync('login')
      if (login) {
        this.login.id = login.tenant.id
        this.login.username = login.tenant.username
        this.getQuestionnaireList(login.tenant.id, login.jwt)
      } else {
        this.login.id = ''
        this.login.username = ''
        this.quesionnaires = []
      }
    }
  }
}
</script>

<style></style>
