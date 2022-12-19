// @ts-ignore
import image1 from '@site/static/img/关羽.jpg'
// @ts-ignore
import image2 from '@site/static/img/夏洛特.jpg'
// @ts-ignore
import image3 from '@site/static/img/百里玄策.jpg'
import clsx from 'clsx'
import React from 'react'
import styles from './styles.module.css'

type FeatureItem = {
  title: string
  image: string
  description: JSX.Element
}

const FeatureList: FeatureItem[] = [
  {
    title: '关羽',
    image: image1,
    description: <>酒且斟下，某去便回</>
  },
  {
    title: '夏洛特',
    image: image2,
    description: <>记住，玫瑰有刺 </>
  },
  {
    title: '百里玄策',
    image: image3,
    description: <>我想听话啊，但人家就是控制不住自己嘛</>
  }
]

function Feature({ title, image, description }: FeatureItem) {
  return (
    <div className={clsx('col col--4')}>
      <div className="text--center">
        <img src={image} />
      </div>
      <div className="text--center padding-horiz--md">
        <h3>{title}</h3>
        <p>{description}</p>
      </div>
    </div>
  )
}

export default function HomepageFeatures(): JSX.Element {
  return (
    <section className={styles.features}>
      <div className="container">
        <div className="row">
          {FeatureList.map((props, idx) => (
            <Feature key={idx} {...props} />
          ))}
        </div>
      </div>
    </section>
  )
}
