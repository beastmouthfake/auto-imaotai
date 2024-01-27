### i茅台自动预约

#### 环境依赖

- jdk1.8
- mysql
- docker (非必需 也可以直接java -jar运行项目)

#### 操作流程

```shell
# 执行项目下的sql文件到mysql中
cd auto-imaotai/
mvn clean package -DskipTests
# 将打包后的auto-imaotai-server-1.0.0-SNAPSHOT.jar和docker目录下的文件放置于同一个文件夹
chmod 777 rebuild.sh
./rebuild.sh
# 根据具体的mysql配置修改docker-compose.yml下的mysql配置信息
# 查看服务启动日志
docker logs -f auto-imaotai
```

由于目前没有前端页面，仅支持接口方式调用

接口调用顺序

1.选择合适门店

```shell
# 根据所在地区搜索离你最近的门店
curl --location 'http://localhost:30013/api/imaotai/shop/page?districtName=西湖区'
```

2.前往PushPlus获取推送的token

3.调用发送验证码接口

```shell
curl --location 'http://localhost:30013/api/imaotai/user/code/你的手机号'
# 接口返回数据是本次发送验证码前生成的设备id，需要保存下来，后续添加用户及预定时会用到
```

4.调用添加用户接口

```shell
curl --location 'http://localhost:30013/api/imaotai/user/addUser' \
--header 'Content-Type: application/json' \
--data '{
    "mobile":"刚刚发送验证码的手机号",
    "code": "i茅台发送给你的验证码",
    "deviceId": "发送验证码接口返回的设备id",
    "pushPlusToken": "PushPlus网站获取的token",
    "bindShopId": "绑定的门店"
}'
```

