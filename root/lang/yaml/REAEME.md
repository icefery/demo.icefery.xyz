# Yaml

## 多行字符串

#### `|` 保留行尾换行符

```yaml
text: |
  hello
  world

n: 1
```

```json
{
  "text": "hello\nworld\n",
  "n": 1
}
```

#### `|-`

```yaml
text: |-
  hello
  world

n: 1
```

```json
{
  "text": "hello\nworld",
  "n": 1
}
```

#### `|+`

```yaml
text: |+
  hello
  world

n: 1
```

```json
{
  "text": "hello\nworld\n\n",
  "n": 1
}
```

#### `>`

```yaml
text: >
  hello
  world

n: 1
```

```json
{
  "text": "hello world\n",
  "n": 1
}
```

#### `>-`

```yaml
text: >-
  hello
  world

n: 1
```

```json
{
  "text": "hello world",
  "n": 1
}
```

#### `>+`

```yaml
text: >+
  hello
  world


n: 1
```

```json
{
  "text": "hello world\n\n\n",
  "n": 1
}
```
