### Spring Security JWT 进行动态 URL 权限验证

用户:
- 用户`admin`密码`admin`, 拥有角色 `ROLE_1`、`ROLE_3`，
- 用户`user`密码`user`

角色:
- 角色`ROLE_1`对应权限（资源）`/hello1/**`
- 角色`ROLE_2`对应权限（资源）`/hello2`
- 角色`ROLE_3`对应权限（资源）`/hello3`


测试:
1. 匿名或 Token 错误: 只能访问 `/hello4`
2. 用户`user`: 只能访问 `/hello4`、`/hello3/sub`、`/hello5`
3. 用户`admin`: 不能访问`/hello2`

