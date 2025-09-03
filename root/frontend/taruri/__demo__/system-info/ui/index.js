const echarts = window.echarts
const tauri = window.__TAURI__['tauri']

const memoryChart = echarts.init(document.getElementById('memoryChart'), 'dark')
const cpuChart = echarts.init(document.getElementById('cpuChart'), 'dark')
const diskChart = echarts.init(document.getElementById('diskChart'), 'dark')

let memoryChartOption = {
  title: { text: '内存使用情况' },
  tooltip: { trigger: 'axis' },
  legend: { data: ['总内存', '已用内存', '可用内存', '空闲内存'] },
  xAxis: { type: 'time', splitLine: { show: true }, min: Date.now() - 60 * 1000, max: Date.now() },
  yAxis: { type: 'value', splitLine: { show: true } },
  series: [
    { name: '总内存', type: 'line', showSymbol: false, data: [] },
    { name: '已用内存', type: 'line', showSymbol: false, data: [] },
    { name: '可用内存', type: 'line', showSymbol: false, data: [] },
    { name: '空闲内存', type: 'line', showSymbol: false, data: [] }
  ]
}

let cpuChartOption = {
  title: { text: 'CPU使用情况' },
  tooltip: { trigger: 'axis' },
  legend: { data: ['CPU使用率'] },
  xAxis: { type: 'time', splitLine: { show: true }, min: Date.now() - 60 * 1000, max: Date.now() },
  yAxis: { type: 'value', splitLine: { show: true }, min: 0, max: 1 },
  series: [{ name: 'CPU使用率', type: 'line', showSymbol: false, data: [] }]
}

let diskChartOption = {
  title: { text: '磁盘使用情况' },
  tooltip: { trigger: 'axis' },
  legend: { data: ['已用空间', '可用空间'] },
  xAxis: { type: 'value' },
  yAxis: { type: 'category', data: [] },
  grid: { left: '5%', right: '5%', containLabel: true },
  series: [
    { name: '已用空间', type: 'bar', stack: '总空间', data: [] },
    { name: '可用空间', type: 'bar', stack: '总空间', data: [] }
  ]
}

/**
 * @typedef {{
 *     hostname: string
 *     memory_total: number
 *     memory_used: number
 *     memory_available: number
 *     memory_free: number
 *     swap_total: number
 *     swap_used: number
 *     swap_free: number
 *     cpu_usage: number
 *     disks: {
 *         disk_mount: string
 *         disk_total: number
 *         disk_used: number
 *         disk_available: number
 *     }[]
 * }} SystemInfo
 *
 * @returns {Promise<SystemInfo>}
 */
async function fetchSystemInfo() {
  return await tauri.invoke('system_info')
}

/**
 * @param {{time: Date, value: number}[]} series
 * @param {Date} time
 * @param {number} value
 * @param {number} last
 * @param {number} step
 */
function pushToTimeSeries(series, time, value, last = 60, step = 5) {
  if (series.length > last / step) {
    series.shift()
  }
  series.push({ value: [time, value] })
}

setInterval(() => {
  fetchSystemInfo().then(data => {
    let now = new Date()

    // 内存
    memoryChartOption.xAxis.min = now.getTime() - 60 * 1000
    memoryChartOption.xAxis.max = now.getTime()
    pushToTimeSeries(memoryChartOption.series[0].data, now, data['memory_total'])
    pushToTimeSeries(memoryChartOption.series[1].data, now, data['memory_used'])
    pushToTimeSeries(memoryChartOption.series[2].data, now, data['memory_available'])
    pushToTimeSeries(memoryChartOption.series[3].data, now, data['memory_free'])
    // CPU
    cpuChartOption.xAxis.min = now.getTime() - 60 * 1000
    cpuChartOption.xAxis.max = now.getTime()
    pushToTimeSeries(cpuChartOption.series[0].data, now, data.cpu_usage)
    // 磁盘
    diskChartOption.yAxis.data = data['disks'].map(it => it['disk_mount'])
    diskChartOption.series[0].data = data['disks'].map(it => it['disk_total'])
    diskChartOption.series[1].data = data['disks'].map(it => it['disk_used'])

    memoryChart.setOption(memoryChartOption)
    cpuChart.setOption(cpuChartOption)
    diskChart.setOption(diskChartOption)
  })
}, 5000)
