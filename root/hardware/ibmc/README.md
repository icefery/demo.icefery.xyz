# iBMC

## 命令行

### 风扇命令

> https://support.huawei.com/enterprise/zh/doc/EDOC1100048790/6e4f76ed

```shell
# 查询风扇工作状态
ipmcget -d faninfo

# 设置风扇运行模式
ipmcset -d fanmode -v 1 0

# 设置风扇运行速度
ipmcset -d fanlevel -v 20
```
