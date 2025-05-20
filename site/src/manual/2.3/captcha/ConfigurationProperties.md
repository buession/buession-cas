# buession-cas-captcha 参考手册


## 配置属性


#### 通用

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| cas.captcha.javascript   | String[]    | --  | 前端 JavaScript 库地址      |


#### 阿里云

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| cas.captcha.aliyun.enabled           | boolean                                              | true                   | 是否启用阿里云行为验证码      |
| cas.captcha.aliyun.access-key-id     | String                                               | --                     | AccessKey ID      |
| cas.captcha.aliyun.access-key-secret | String                                               | --                      | AccessKey Secret      |
| cas.captcha.aliyun.app-key           | String                                               | --                      | 服务使用的 App Key      |
| cas.captcha.aliyun.region-id         | String                                               | --                      | 区域 ID      |
| cas.captcha.aliyun.parameter         | [AliyunParameter](https://javadoc.io/doc/com.buession.security/buession-security-captcha/2.3.0/com/buession/security/captcha/aliyun/AliyunParameter.html) | AliyunParameter 默认值   | 前端提交参数名称      |


#### 极验

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| cas.captcha.geetest.enabled       | boolean                                                          | true                   | 是否启极验行为验证码      |
| cas.captcha.geetest.app-id        | String                                                           | --                     | 应用 ID      |
| cas.captcha.geetest.secret-key    | String                                                           | --                     | 密钥      |
| cas.captcha.geetest.version       | String                                                           | v4                    | 版本      |
| cas.captcha.geetest.v3.parameter  | [GeetestV3Parameter](https://javadoc.io/doc/com.buession.security/buession-security-captcha/2.3.0/com/buession/security/captcha/geetest/api/v3/GeetestV3Parameter.html)  | GeetestV3Parameter 默认值                     | 前端提交参数名称      |
| cas.captcha.geetest.v4.parameter  | [GeetestV4Parameter](https://javadoc.io/doc/com.buession.security/buession-security-captcha/2.3.0/com/buession/security/captcha/geetest/api/v4/GeetestV4Parameter.html)  | GeetestV4Parameter 默认值                     | 前端提交参数名称      |


#### 腾讯

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| cas.captcha.tencent.enabled    | boolean                                                | true                   | 是否启腾讯云行为验证码      |
| cas.captcha.tencent.app-id     | String                                                 | --                     | 应用 ID      |
| cas.captcha.tencent.secret-key | String                                                 | --                     | 密钥      |
| cas.captcha.tencent.parameter  | [TencentParameter](https://javadoc.io/doc/com.buession.security/buession-security-captcha/2.3.0/com/buession/security/captcha/tencent/TencentParameter.html) | TencentParameter 默认值               | 前端提交参数名称      |