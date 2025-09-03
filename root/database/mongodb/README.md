# MongoDB

## 常用

#### 按指定列去重并取最新数据

> https://www.mongodb.com/zh-cn/docs/rapid/aggregation

```javascript
db.collection.aggregate([
  { $sort: { field_1: 1, field_2: 1, _ctime: -1 } },
  {
    $group: {
      _id: { field_1: '$field_1', field_2: '$field_2' },
      latest: { $first: '$$ROOT' }
    }
  },
  { $replaceRoot: { newRoot: '$latest' } }
])
```
