<template>
  <view class="u-margin-left-20 u-margin-right-20 u-margin-top-20">
    <u-grid :col="3" align="center">
      <u-grid-item @click="gotoFill()">
        <u-icon name="/static/fill.png" :size="46"></u-icon>
        <view class="grid-text">填写问卷</view>
      </u-grid-item>
      <u-grid-item @click="gotoCreate()">
        <u-icon name="/static/create.png" :size="46"></u-icon>
        <view class="grid-text">创建问卷</view>
      </u-grid-item>
      <u-grid-item @click="gotoMy()">
        <u-icon name="/static/user.png" :size="46"></u-icon>
        <view class="grid-text">个人中心</view>
      </u-grid-item>
    </u-grid>
  </view>
</template>
<script>
export default {
  data() {
    return {}
  },

  methods: {
    gotoFill() {
      uni.switchTab({ url: '/pages/fill/fill' })
    },
    gotoCreate() {
      uni.switchTab({ url: '/pages/create/create' })
    },
    gotoMy() {
      uni.switchTab({ url: '/pages/my/my' })
    }
  }
}
</script>
