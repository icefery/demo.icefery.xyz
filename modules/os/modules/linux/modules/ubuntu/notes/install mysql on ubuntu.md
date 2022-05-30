## 最简安装

1. 安装

   ```bash
   apt search mysql-server

   apt install mysql-server -y
   ```

2. 重置

   ```bash
   systemctl stop mysql

   rm -rf /var/lib/mysql/* /etc/mysql/*
   ```

3. 修改配置

   ```bash
   cat <<-EOF >/etc/mysql/my.cnf
   [mysqld]
   user                          = mysql
   bind-address                  = 0.0.0.0
   lower-case-table-names        = 1
   default-time-zone             = +8:00
   default-authentication-plugin = mysql_native_password
   EOF
   ```

4. 初始化

   ```bash
   mysqld --initialize

   systemctl start mysql

   mysql -uroot -p
   ```

5. 修改密码

   ```sql
   ALTER USER user() IDENTIFIED BY 'root';

   USE mysql;

   SELECT user, host, plugin FROM user;

   UPDATE user SET host='%' WHERE user='root';

   FLUSH PRIVILEGES;
   ```
