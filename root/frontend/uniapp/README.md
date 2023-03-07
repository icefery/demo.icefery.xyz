# uniapp

## 微信小程序登录

```vue
<template>
  <view>
    <image :src="state.userInfo.avatarUrl"></image>
  </view>
  <view> nickName = {{ state.userInfo.nickName }}</view>
  <view> openId = {{ state.userInfo.openId }}</view>
  <view> sessionKey = {{ state.userInfo.sessionKey }}</view>
  <view>
    <button @click="handleClick">登录</button>
  </view>
</template>

<script lang="ts" setup>
import { reactive } from 'vue'

const state = reactive({ userInfo: { avatarUrl: '', nickName: '', openId: '', sessionKey: '' } })

const handleClick = () => {
  uni.getUserProfile({
    desc: '获取用户信息',
    success: result => {
      state.userInfo.avatarUrl = result.userInfo.avatarUrl
      state.userInfo.nickName = result.userInfo.nickName
      uni.login({
        success: result => {
          const appid = ''
          const secret = ''
          uni.request({
            url: `https://api.weixin.qq.com/sns/jscode2session?appid=${appid}&secret=${secret}&js_code=${result.code}&grant_type=authorization_code`,
            method: 'GET',
            success: result => {
              state.userInfo.openId = (result.data as any).openid
              state.userInfo.sessionKey = (result.data as any).session_key
            }
          })
        }
      })
    }
  })
}
</script>
```
