## Springboot 整合Oauth2 实现API 关联

### 密码模式
curl -i -X POST --user user_client:123456 -d "grant_type=password&username=project_admin&password=123456" http://localhost:8080/oauth/token

### 客户端模式

1. 在“oauth_client_details” 表中添加一个grant_type等于client_credentials的用户

2. curl -X POST "http://localhost:8080/oauth/token" --user client_2:123456 -d "grant_t ype=client_credentials"

注意：client_credentials客户端模式没有refresh_token更新令牌！！！


