# cas-server-support-logging-core 参考手册


## 配置属性


#### 通用

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| cas.logging.business-type         | String    | Login  | 业务类型        |
| cas.logging.event                 | String    | Login  | 业务事件        |
| cas.logging.description           | String    | Login  | 业务描述        |
| cas.logging.id-field-name         | String    | --     | ID 字段名称     |
| cas.logging.username-field-name   | String    | --     | 用户名字段名称   |
| cas.logging.real-name-field-name  | String    | --     | 真实姓名字段名称 |


#### 控制台日志

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| cas.logging.console.[index].template         | String    | ${id} login success at: ${time}(IP: ${clientIp}), User-Agent: ${User-Agent}, OS: " +
			"${os_name} ${os_version}, Device: ${device_type}, Browser: ${browser_name} ${browser_version}.     | 日志模板     |
| cas.logging.console.[index].formatter-class  | String    | com.buession.logging.console.formatter.DefaultConsoleLogDataFormatter     | 格式化类     |


#### Elasticsearch 日志

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| cas.logging.elasticsearch.[index].urls         | java.util.List<String>    | http://localhost:9200     | Elasticsearch URL 地址     |
| cas.logging.elasticsearch.[index].username     | String                    | -                         | Elasticsearch 用户名       |
| cas.logging.elasticsearch.[index].password     | String                    | -                         | Elasticsearch 密码         |
| cas.logging.elasticsearch.[index].connection-timeout     | java.time.Duration                    | 1000 ms                         | 连接超时       |
| cas.logging.elasticsearch.[index].read-timeout     | java.time.Duration                    | 30000 ms                         | 读取超时       |
| cas.logging.elasticsearch.[index].path-prefix     | String                    | -                         | 路径前缀       |
| cas.logging.elasticsearch.[index].headers     | java.util.Map<String, String>                    | -                         | 请求头，请求头名称作为 key，请求头值作为 value       |
| cas.logging.elasticsearch.[index].parameters     | java.util.Map<String, String>                    | -                         | 请求参数，参数名称作为 key，参数值作为 value       |
| cas.logging.elasticsearch.[index].index-name     | String                    | -                         | 索引名称       |
| cas.logging.elasticsearch.[index].auto-create-ndex     | Boolean                    | -                         | 是否自动创建索引       |
| cas.logging.elasticsearch.[index].refresh-policy     | org.springframework.data.elasticsearch.core.RefreshPolicy                    | -                         | 刷新策略       |
| cas.logging.elasticsearch.[index].entity-callbacks-class     | org.springframework.data.mapping.callback.EntityCallbacks                    | -                         |         |


#### 文件日志

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| cas.logging.file.[index].path         | String    | -     | 日志路径     |
| cas.logging.file.[index].formatter-class  | String    | com.buession.logging.core.formatter.DefaultLogDataFormatter     | 格式化类     |


#### JDBC 日志

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| cas.logging.jdbc.[index].sql                                    | String    | -                                                           | SQL                    |
| cas.logging.jdbc.[index].id-generator-class                     | String    | -                                                           | ID 生成器类名            |
| cas.logging.jdbc.[index].date-time-format                       | String    | -                                                           | 日期时间格式             |
| cas.logging.jdbc.[index].request-parameters-formatter-class     | String    | com.buession.logging.jdbc.formatter.JsonMapFormatter        | 请求参数格式化为字符串     |
| cas.logging.jdbc.[index].geo-parameters-formatter-class         | String    | com.buession.logging.jdbc.formatter.DefaultGeoFormatter     | Geo 格式化               |
| cas.logging.jdbc.[index].extra-formatter-class                  | String    | com.buession.logging.jdbc.formatter.JsonMapFormatter        | 附加参数格式化为字符串     |
| cas.logging.jdbc.[index].data-formatter-class                   | String    | com.buession.logging.jdbc.converter.DefaultLogDataConverter | 日志数据转换器            |


#### Kafka 日志

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| cas.logging.kafka.[index].bootstrap-servers     | java.util.List<String>                        | localhost:9092    | Kafka Server 地址    |
| cas.logging.kafka.[index].client-id             | String                                        | -                 | 客户端 ID            |
| cas.logging.kafka.[index].topic                 | String                                        | __cas_log__       | Topic 名称           |
| cas.logging.kafka.[index].acks                  | String                                        | -                 |                      |
| cas.logging.kafka.[index].batch-size            | org.springframework.util.unit.DataSize        | -                 |                      |
| cas.logging.kafka.[index].buffer-memory         | org.springframework.util.unit.DataSize        | -                 |                      |
| cas.logging.kafka.[index].compression-type      | String                                        |                   | 压缩方式              |
| cas.logging.kafka.[index].retries               | Integer                                       | 3                 | 重试次数              |
| cas.logging.kafka.[index].ssl                   | com.buession.logging.core.SslConfiguration    | -                 | SSL 配置             |
| cas.logging.kafka.[index].security.protocol     | String                                        | -                 | 安全协议              |
| cas.logging.kafka.[index].properties            | java.util.Map<String, String>                 | -                 | 其它参数              |


#### MongoDB 日志

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| cas.logging.mongo.[index].client-uri                       | String         | -                 | MongoDB 连接字符串           |
| cas.logging.mongo.[index].port                             | Integer        | 27017             | MongoDB 端口                 |
| cas.logging.mongo.[index].user-id                          | String         | -                 | 用户名                      |
| cas.logging.mongo.[index].password                         | String         | -                 | 密码                        |
| cas.logging.mongo.[index].host                             | String         | -                 | MongoDB 地址                |
| cas.logging.mongo.[index].timeout                          | String         | ACKNOWLEDGED      | -                           |
| cas.logging.mongo.[index].write-concern                    | String         |                   |                             |
| cas.logging.mongo.[index].read-concern                     | String         | -                 |                             |
| cas.logging.mongo.[index].read-preference                  | String         | PRIMARY           |                             |
| cas.logging.mongo.[index].database-name                    | String         | -                 | 数据库名称                    |
| cas.logging.mongo.[index].authentication-database-name     | String         | -                 | 认证数据库名称                 |
| cas.logging.mongo.[index].collection                       | String         | -                 | Collection 名称              |
| cas.logging.mongo.[index].drop-collection                  | String         | -                 | 是否删除 Collection           |
| cas.logging.mongo.[index].socket-keep-alive                | Boolean        | false             |                              |
| cas.logging.mongo.[index].retry-writes                     | Boolean        | false             |                              |
| cas.logging.mongo.[index].ssl-enabled                      | Boolean        | false             | 是否开启 SSL                  |
| cas.logging.mongo.[index].replica-set                      | String         | -                 | Replica Set 名称              |


#### RabbitMQ 日志

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| cas.logging.rabbit.[index].host                        | String                | -        | RabbitMQ 地址           |
| cas.logging.rabbit.[index].port                        | Integer               | 5672     | RabbitMQ 端口                 |
| cas.logging.rabbit.[index].username                    | String                | guest    | 用户名                      |
| cas.logging.rabbit.[index].password                    | String                | guest    | 密码                        |
| cas.logging.rabbit.[index].virtual-host                | String                | /        | 虚拟机                |
| cas.logging.rabbit.[index].exchange                    | String                | -        | Exchange 名称                |
| cas.logging.rabbit.[index].routing-key                 | String                | -        | Routing key 名称                |
| cas.logging.rabbit.[index].connection-timeout          | java.time.Duration    | 1 s      | 连接超时                           |
| cas.logging.rabbit.[index].channel-rpc-timeout         | java.time.Duration    | 10 s     | Continuation timeout for RPC calls in channels. Set it to zero to wait forever.   |
| cas.logging.rabbit.[index].requested-heartbeat        | java.time.Duration    | -        |                            |
| cas.logging.rabbit.[index].requested-channel-max       | Integer               | 2047     | RabbitMQ 端口                 |


#### Rest 日志

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| cas.logging.rest.[index].url                        | String                | -        | Rest Url           |
| cas.logging.rabbit.[index].request-method                        | com.buession.logging.core.RequestMethod               | POST     | 请求方式                 |
| cas.logging.rabbit.[index].request-body-builder-class                    | String                | com.buession.logging.rest.core.JsonRequestBodyBuilder    | 请求体构建器                      |
| cas.logging.rabbit.[index].http-client                    | org.apereo.cas.configuration.model.support.logging.RestLoggingProperties.HttpClientProperties                | -    | HTTP Client 配置                        |