##动态数据源实现
Demo 请求示例
```Shell
curl --location --request POST 'localhost:56045/dynamic/ds' \
--header 'User-Agent: Apipost client Runtime/+https://www.apipost.cn/' \
--header 'tenant: master' \
--header 'Content-Type: application/json' \
--data '{
  "openId": "xxxxx",
  "appid": "xxxxx",
  "tenant":"master"
}'
```