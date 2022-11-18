<template>
  <view class="u-margin-left-20 u-margin-right-20">
    <u-form>
      <u-divider half-width="100%"><u-tag text="下拉拉取" size="mini" /></u-divider>
      <u-form-item><u-input :border="true" v-model="questionnaireItem.tenantId" placeholder="租户号" input-align="center" /></u-form-item>
      <u-form-item><u-input :border="true" v-model="questionnaireItem.questionnaireId" placeholder="问卷号" input-align="center" /></u-form-item>

      <template v-for="(question, index) in questionnaireItem.questions">
        <u-divider half-width="100%"><u-tag size="mini" :text="index + 1 + ' ' + question.title" /></u-divider>

        <u-form-item>
          <!--  问答 -->
          <template v-if="question.type === 'qa'">
            <u-input type="textarea" :border="true" v-model="question.value" />
          </template>
          <!--  单选  -->
          <template v-else-if="question.type === 'radio'">
            <u-radio-group v-model="question.value.checked" :wrap="true">
              <template v-for="(option, optionIndex) in question.value.options">
                <u-radio :name="option">{{ option }}</u-radio>
              </template>
            </u-radio-group>
          </template>
          <!-- 多选 -->
          <template v-else-if="question.type === 'checkbox'">
            <u-checkbox-group :wrap="true">
              <template v-for="(option, optionIndex) in question.value">
                <u-checkbox :name="option.name" v-model="option.checked">{{ option.name }}</u-checkbox>
              </template>
            </u-checkbox-group>
          </template>
          <!-- 位置 -->
          <template v-else-if="question.type === 'location'">
            <u-button style="width: 100%" @click="() => (question.control.pickerShow = true)">
              {{ computedLocationDefaultRegion(question).join(' ') }}
            </u-button>
            <u-picker
              v-model="question.control.pickerShow"
              mode="region"
              :default-region="computedLocationDefaultRegion(question)"
              @confirm="param => onLocationConfirm(param, question)"
              :params="computedPickerParam(question.config.locationParams)"
            />
          </template>
          <!-- 时间 -->
          <template v-else-if="question.type === 'datetime'">
            <u-button style="width: 100%" @click="() => (question.control.pickerShow = true)">{{ computedDatetimeDisplayValue(question) }}</u-button>
            <u-picker
              v-model="question.control.pickerShow"
              mode="time"
              :default-time="computedDatetimeDefaultTime(question)"
              @confirm="param => onDatetimeConfirm(param, question)"
              :params="computedPickerParam(question.config.datetimeParams)"
            />
          </template>
        </u-form-item>
      </template>
      <template v-if="questionnaireItem.questions.length >= 1">
        <u-form-item><u-button style="width: 100%" @click="() => (control.modalShow = true)">提交</u-button></u-form-item>
      </template>
    </u-form>

    <u-modal
      v-model="control.modalShow"
      content="确认提交？"
      @confirm="commitQuestionnaireItem()"
      @cancel="() => (control.modalShow = false)"
      :mask-close-able="true"
      :show-title="false"
      :show-cancel-button="true"
    />
  </view>
</template>

<script>
export default {
  data() {
    return {
      control: {
        modalShow: false
      },
      questionnaireItem: {
        questionnaireId: '',
        tenantId: '',
        questions: []
      }
    }
  },
  onPullDownRefresh() {
    this.pullQuestionnaire()
    setTimeout(() => uni.stopPullDownRefresh(), 500)
  },
  onShow() {
    this.pullQuestionnaire()
  },
  methods: {
    pullQuestionnaire() {
      const { jwt, tenant } = uni.getStorageSync('login')
      if (this.questionnaireItem.tenantId === '' || this.questionnaireItem.questionnaireId === '') {
        return
      }
      uni.request({
        url: `http://localhost:9000/${this.questionnaireItem.tenantId}/questionnaire/find/id/${this.questionnaireItem.questionnaireId}`,
        method: 'GET',
        success: response => {
          const { code, message, data } = response.data
          if (response.statusCode === 200 && code === 0 && data) {
            this.questionnaireItem.questions = JSON.parse(data.questions)
            uni.showToast({ title: '拉取成功' })
          } else {
            uni.showToast({ title: '拉取失败' })
            this.questionnaireItem.questions = []
          }
        }
      })
    },
    // 位置选择
    onLocationConfirm({ province, city, area }, question) {
      question.value = {}
      if (province) {
        question.value.province = province.label
      }
      if (city) {
        question.value.city = city.label
      }
      if (area) {
        question.value.area = area.label
      }
    },
    // 时间选择
    onDatetimeConfirm(param, question) {
      question.value = { ...param }
      console.log(question.value)
    },
    // 执行提交
    commitQuestionnaireItem() {
      let form = { ...this.questionnaireItem }

      form.questions = JSON.stringify(this.questionnaireItem.questions)
      console.log('form ==> ', form)

      uni.request({
        url: `http://localhost:9000/${this.questionnaireItem.tenantId}/questionnaire-item/create`,
        method: 'POST',
        data: form,
        header: { 'Content-Type': 'application/json' },
        success: response => {
          const { code, message, data } = response.data
          if (response.statusCode === 200 && code === 0) {
            uni.showToast({ title: '提交成功' })
            this.questionnaireItem.questions = []
          } else {
            uni.showToast({ title: message || '提交失败' })
          }
        }
      })
    }
  },
  computed: {
    computedLocationDefaultRegion() {
      return question => {
        const { province, city, area } = question.value
        const arr = []
        if (province) {
          arr.push(province)
        }
        if (city) {
          arr.push(city)
        }
        if (area) {
          arr.push(area)
        }
        return arr
      }
    },
    computedDatetimeDefaultTime() {
      return question => {
        const { year, month, day, hour, minute, second } = question.value
        const date = new Date()
        return [
          [year || date.getFullYear(), month || date.getMonth() + 1, day || date.getDate()].join('-'),
          [hour || date.getHours(), minute || date.getMinutes(), second || date.getSeconds()].join(':')
        ].join(' ')
      }
    },
    computedDatetimeDisplayValue() {
      return question => {
        const { year, month, day, hour, minute, second } = question.value
        let s = ''
        if (year) {
          s += year + ' 年 '
        }
        if (month) {
          s += month + ' 月 '
        }
        if (day) {
          s += day + ' 日 '
        }
        if (hour) {
          s += hour + ' 时 '
        }
        if (minute) {
          s += minute + ' 分 '
        }
        if (second) {
          s += second + ' 秒'
        }
        return s
      }
    },
    computedPickerParam() {
      return params => {
        const obj = {}
        params.filter(item => item.checked === true).forEach(item => (obj[item.value] = true))
        return obj
      }
    }
  }
}
</script>

<style></style>
