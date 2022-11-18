// 问卷
interface Questionnaire {
  // 问卷 ID (服务端生成)
  id: string
  // 租户 ID
  tenantId: string
  // 问卷标题
  title: string
  // 问卷描述
  description: string
  // 问卷项题目模板
  questions: Question[]
}

// 问卷项
interface QuestionnaireItem {
  // 问卷项 ID (服务端生成)
  id: string
  // 租户 ID
  tenantId: string
  // 问卷 ID
  questionnaireId: string
  // 问卷项题目
  questions: Question[]
}

// 题目
interface Question {
  // 题目类型 (问答 | 单选 | 多选 | 位置 | 时间 | 图片)
  type: 'qa' | 'radio' | 'checkbox' | 'location' | 'datetime' | 'image'
  // 题目标题
  title: string
  // 题目回答
  value: string | RadioValue | CheckboxValue | LocationValue | DatetimeValue | ImageValue
  // 题目是否必填
  required: boolean
  // 题目配置
  config: QuestionConfig
}

// 题目配置
interface QuestionConfig {
  // 位置型题目配置
  locationParams?: { name: 'province' | 'city' | 'area'; checked: boolean }[]
  // 时间型题目配置
  datetimeParams?: { name: 'year' | 'month' | 'day' | 'hour' | 'minute' | 'second'; checked: boolean }[]
}

type RadioValue = { options: string[]; checked: '' }
type CheckboxValue = { name: string; checked: boolean }[]
type LocationValue = { province?: string; city?: string; area?: string }
type DatetimeValue = { year?: string; month?: string; day?: string; hour?: string; minute?: string; second?: string }
type ImageValue = { base64: string }[]

// 测试问卷
const testQuestionnaire: Questionnaire = {
  id: '1349622206322548737',
  tenantId: '1349363369443135490',
  title: '测试问卷',
  description: '',
  questions: [
    {
      type: 'datetime',
      title: '当前日期',
      value: { year: '2000', month: '01', day: '01' },
      required: true,
      config: {
        datetimeParams: [
          { name: 'year', checked: true },
          { name: 'month', checked: true },
          { name: 'day', checked: true },
          { name: 'hour', checked: false },
          { name: 'minute', checked: false },
          { name: 'second', checked: false }
        ]
      }
    }
  ]
}

// 测试问卷项
const testQuestionnaireItem: QuestionnaireItem = {
  id: '1349685921243742209',
  tenantId: '1349363369443135490',
  questionnaireId: '1349622206322548737',
  questions: [
    {
      type: 'datetime',
      title: '当前日期',
      value: { year: '2021', month: '01', day: '14' },
      required: true,
      config: {
        datetimeParams: [
          { name: 'year', checked: true },
          { name: 'month', checked: true },
          { name: 'day', checked: true },
          { name: 'hour', checked: false },
          { name: 'minute', checked: false },
          { name: 'second', checked: false }
        ]
      }
    }
  ]
}
