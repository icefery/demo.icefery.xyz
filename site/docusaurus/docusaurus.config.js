const lightCodeTheme = require('prism-react-renderer/themes/github')
const darkCodeTheme = require('prism-react-renderer/themes/dracula')

const config = {
  title: '十二翼堕落天使',
  url: 'https://docusaurus.demo.icefery.github.io',
  baseUrl: '/',

  favicon: 'img/favicon.ico',
  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'throw',
  onDuplicateRoutes: 'throw',
  tagline: '我梦见过许多种我们有可能的未来——无一善终',

  organizationName: 'icefery',
  projectName: 'icefery.github.io',
  deploymentBranch: 'docusaurus',

  themeConfig: {
    // 通用
    colorMode: {
      respectPrefersColorScheme: true
    },
    docs: {
      sidebar: {
        hideable: true
      }
    },
    // 导航栏
    navbar: {
      title: '十二翼多堕落天使',
      logo: {
        src: 'img/favicon.ico'
      },
      items: [
        { type: 'doc', docId: 'index', position: 'left', label: '文档' },
        { href: 'https://blog.csdn.net/XY1790026787', label: 'CSDN', position: 'right' },
        { href: 'https://github.com/icefery/demo.icefery.xyz', label: 'GitHub', position: 'right' }
      ]
    },
    // 代码块
    prism: {
      theme: lightCodeTheme,
      darkTheme: darkCodeTheme
    },
    // 页脚
    footer: {
      style: 'dark',
      copyright: `Built with Docusaurus`
    },
    // 目录
    tableOfContents: {
      minHeadingLevel: 2,
      maxHeadingLevel: 6
    }
  },

  presets: [['@docusaurus/preset-classic', { docs: { path: 'docs' } }]]
}

module.exports = config
