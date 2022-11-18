<template>
  <view class="u-margin-left-20 u-margin-right-20">
    <u-form ref="form" :model="questionnaire">
      <u-divider half-width="100%"><u-tag text="基本信息" size="mini" /></u-divider>
      <!-- 标题 -->
      <u-form-item label="标题" prop="title" :required="true"><u-input :border="true" v-model="questionnaire.title" /></u-form-item>
      <!-- 描述 -->
      <u-form-item label="描述" prop="description" :required="true"><u-input :border="true" v-model="questionnaire.description" /></u-form-item>

      <template v-for="(question, index) in questionnaire.questions">
        <u-divider half-width="100%">
          <u-tag :text="index + 1" :closeable="questionnaire.questions.length >= 2" size="mini" @close="() => removeQuestion(index)" />
        </u-divider>
        <!-- 题型 -->
        <u-form-item label="类型">
          <u-subsection
            style="width: 100%"
            :animation="true"
            :list="types"
            :current="types.findIndex(item => item.value === question.type)"
            @change="i => onTypeChange(types[i].value, question)"
          />
        </u-form-item>
        <!-- 题目 -->
        <u-form-item label="题目"><u-input :border="true" v-model="question.title" /></u-form-item>
        <!-- 内容 -->
        <u-form-item label="内容">
          <!-- 问答 -->
          <template v-if="question.type === 'qa'">
            <u-input :border="true" v-model="question.value" type="textarea" />
          </template>
          <!-- 单选 -->
          <template v-else-if="question.type === 'radio'">
            <u-radio-group v-model="question.value.checked" :wrap="true">
              <template v-for="(option, optionIndex) in question.value.options">
                <u-radio :name="option">
                  <u-field v-model="question.value.options[optionIndex]" :clearable="false" :border-top="true">
                    <template #right v-if="question.value.options.length >= 2">
                      <u-icon name="minus-circle" @click="() => removeRadioOption(question, optionIndex)" size="40" />
                    </template>
                  </u-field>
                </u-radio>
              </template>
            </u-radio-group>
            <u-icon name="plus-circle" size="40" @click="() => addRadioOption(question)" />
          </template>
          <!-- 多选 -->
          <template v-else-if="question.type === 'checkbox'">
            <u-checkbox-group :wrap="true">
              <template v-for="(option, optionIndex) in question.value">
                <u-checkbox :name="option.name" v-model="option.checked">
                  <u-field v-model="option.name" :clearable="false" :border-top="true">
                    <template #right v-if="question.value.length >= 2">
                      <u-icon name="minus-circle" @click="() => removeCheckboxOption(question, optionIndex)" size="40" />
                    </template>
                  </u-field>
                </u-checkbox>
              </template>
            </u-checkbox-group>
            <u-icon name="plus-circle" size="40" @click="() => addCheckboxOption(question)"></u-icon>
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
            <u-button style="width: 100%" @click="() => (question.control.pickerShow = true)">
              {{ computedDatetimeDisplayValue(question) }}
            </u-button>
            <u-picker
              v-model="question.control.pickerShow"
              mode="time"
              :default-time="computedDatetimeDefaultTime(question)"
              @confirm="param => onDatetimeConfirm(param, question)"
              :params="computedPickerParam(question.config.datetimeParams)"
            />
          </template>

          <!-- 图片 -->
          <template v-else-if="question.type === 'image'">
            <u-upload :ref="question.control.ref" :auto-upload="false" />
          </template>
        </u-form-item>
        <!-- 第 i+1 题必选设置 -->
        <u-form-item label="必选"><u-switch v-model="question.required" /></u-form-item>
        <!-- 第 i+1 题位置配置-->
        <template v-if="question.type === 'location'">
          <u-form-item label="位置">
            <u-checkbox-group>
              <template v-for="param in question.config.locationParams">
                <u-checkbox :name="param.value" v-model="param.checked">{{ param.name }}</u-checkbox>
              </template>
            </u-checkbox-group>
          </u-form-item>
        </template>
        <!-- 第 i+1 题时间配置-->
        <template v-if="question.type === 'datetime'">
          <u-form-item label="时间">
            <u-checkbox-group>
              <template v-for="param in question.config.datetimeParams">
                <u-checkbox :name="param.value" v-model="param.checked">{{ param.name }}</u-checkbox>
              </template>
            </u-checkbox-group>
          </u-form-item>
        </template>
      </template>

      <u-form-item>
        <u-button style="width: 50%" class="u-margin-right-10" @click="addQuestion()"><u-icon name="plus" /></u-button>
        <u-button style="width: 50%" class="u-margin-left-10" @click="commitQuestionnaire()"><u-icon name="checkbox-mark" /></u-button>
      </u-form-item>
    </u-form>
  </view>
</template>

<script>
export default {
  data() {
    return {
      // 问卷
      questionnaire: {
        title: '',
        description: '',
        questions: [{ type: 'qa', title: '', value: '', required: true, control: { swipeShow: false, pickerShow: false, uploadRef: null } }]
      },
      // 题目类型
      types: [
        { name: '问答', value: 'qa' },
        { name: '单选', value: 'radio' },
        { name: '多选', value: 'checkbox' },
        { name: '位置', value: 'location' },
        { name: '时间', value: 'datetime' }
        // TODO { name: '图片', value: 'image' }
      ],

      // 校验规则
      rules: {
        title: [{ required: true, message: '请输入问卷标题', trigger: ['change', 'blur'] }],
        description: [{ required: true, message: '请输入问卷描述', trigger: ['change', 'blur'] }]
      }
    }
  },
  onReady() {
    this.$refs.form.setRules(this.rules)
  },
  methods: {
    // 更改类型
    onTypeChange(type, question) {
      question.type = type
      question.control = { swipeShow: false, pickerShow: false, uploadRef: null }
      switch (type) {
        // 问答
        case 'qa':
          question.value = ''
          break
        // 单选
        case 'radio':
          question.value = { options: ['选项 1', '选项 2'], checked: '选项 1' }
          question.control.cachedOptionNo = 2
          break
        // 多选
        case 'checkbox':
          question.value = [
            { name: '选项 1', checked: false },
            { name: '选项 2', checked: false }
          ]
          question.control.cachedOptionNo = 2
          break
        // 位置
        case 'location':
          question.config = {
            locationParams: [
              { name: '省', value: 'province', checked: true },
              { name: '市', value: 'city', checked: true },
              { name: '区', value: 'area', checked: true }
            ]
          }
          question.value = { province: '四川省', city: '成都市', area: '新都区' }
          break
        // 时间
        case 'datetime':
          question.config = {
            datetimeParams: [
              { name: '年', value: 'year', checked: true },
              { name: '月', value: 'month', checked: true },
              { name: '日', value: 'day', checked: true },
              { name: '时', value: 'hour', checked: true },
              { name: '分', value: 'minute', checked: true },
              { name: '秒', value: 'second', checked: true }
            ]
          }
          const date = new Date()
          question.value = {
            year: date.getFullYear(),
            month: date.getMonth() + 1,
            day: date.getDate(),
            hour: date.getHours(),
            minute: date.getMinutes(),
            second: date.getSeconds()
          }
          break
        // 图片
        case 'image':
          question.value = []
          break
      }
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
    // 添加问题
    addQuestion() {
      this.questionnaire.questions.push({
        type: 'qa',
        title: '',
        value: '',
        required: true,
        control: { swipeShow: false, pickerShow: false, uploadRef: null }
      })
    },
    removeQuestion(index) {
      this.questionnaire.questions.splice(index, 1)
    },
    // 添加单选
    addRadioOption(question) {
      const no = question.control.cachedOptionNo + 1
      question.value.options.push(`选项 ${no}`)
      question.control.cachedOptionNo = no
    },
    // 移除单选
    removeRadioOption(question, index) {
      const isDeletingChecked = question.value.options[index] === question.value.checked
      question.value.options.splice(index, 1)
      if (isDeletingChecked || question.value.options.length <= 1) {
        question.value.checked = question.value.options[0]
      }
    },
    // 添加多选
    addCheckboxOption(question) {
      const no = question.control.cachedOptionNo + 1
      question.value.push({ name: `选项 ${no}`, checke: false })
      question.control.cachedOptionNo = no
    },
    // 移除多选
    removeCheckboxOption(question, index) {
      question.value.splice(index, 1)
    },
    // 提交表单
    commitQuestionnaire() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const { jwt, tenant } = uni.getStorageSync('login')
          let form = { ...this.questionnaire }

          form.questions = JSON.stringify(this.questionnaire.questions)
          form = JSON.stringify(form)
          console.log('form ==> ', form)

          uni.request({
            url: `http://localhost:9000/${tenant.id}/questionnaire/create`,
            method: 'POST',
            header: { Authorization: jwt, 'Content-Type': 'application/json' },
            data: form,
            success: response => {
              console.log(response)
              const { code, message, data } = response.data
              if (response.statusCode === 200 && code === 0) {
                uni.showToast({ title: '创建成功' })
              } else {
                uni.showToast({ title: message || '创建失败' })
              }
            },
            fail: () => {
              console.log('请求失败')
            }
          })
        }
      })
    }
  },
  computed: {
    // 计算属性 | 适配时间到文本显示
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

    //  计算属性 | 适配位置到 Picker 默认值
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
    // 计算属性 | 适配时间到 Picker 默认值
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
    // 计算属性 | 适配位置与时间配置到 Picker 配置
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
