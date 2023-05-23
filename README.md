# SearchGeeks - 极客搜 聚合搜索平台

> 前后端分离项目 By程序员Chic
一行代码，无限可能！欢迎探索我的项目，喜欢请Star ⭐！
>

## 项目介绍

🔍 基于 Spring Boot + Elastic Stack + Vue3 的一站式信息聚合搜索平台 。

👨🏻‍🚀 用户可以在同一页面集中搜索出不同来源、不同类型的内容，比如：文 章、图片、用户、ChatGPT的回复等，提升搜索体验。

👨🏻‍💻 企业也可以直接将项目的数据源接入搜索平台，复用同一套搜索后端，提升开发效率、降低系统维护成本。

> 线上地址：[https://](https://github.com/ZZC-cc/SearchGeeks-frontend)xxxxxx.com(暂未上线)
前端项目地址：[https://github.com/ZZC-cc/SearchGeeks-frontend](https://github.com/ZZC-cc/SearchGeeks-frontend)
后端项目地址：[https://github.com/ZZC-cc/SearchGeeks-backend](https://github.com/ZZC-cc/SearchGeeks-backend)
>

![https://github.com/ZZC-cc/SearchGeeks-backend/blob/master/doc/01-2post.png?raw=true](https://github.com/ZZC-cc/SearchGeeks-backend/blob/master/doc/01-2post.png?raw=true)

## 项目架构

> 一图胜千言~
>

![https://github.com/ZZC-cc/SearchGeeks-backend/blob/master/doc/00project.png?raw=true](https://github.com/ZZC-cc/SearchGeeks-backend/blob/master/doc/00project.png?raw=true)

## 技术栈

### 前端

- Vue3
- Ant Design Vue
- 页面状态同步

### 后端

- SpringBoot2.7框架 + springboot-init脚手架
- MySQL数据库（8.x版本）
- Elastic Stack
    - ElasticSearch搜索引擎（重点）
    - Logstash数据管道
    - Kibana数据可视化
- 数据抓取（jsoup、HttpClient爬虫）
    - 离线
    - 实时
- 设计模式
    - 门面模式
    - 适配器模式
    - 注册器模式
- 数据同步
    - 定时
    - 双写
    - Logstash
    - Canal
- JMeter压力测试

## 快速上手

### MySQL 数据库

（1）修改 `application.yml` 的数据库配置为你自己的：

```
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/my_db
    username: root
    password: 123456

```

（2）执行 `sql/create_table.sql` 中的数据库语句，自动创建库表

（3）启动项目，访问 `http://localhost:8101/api/doc.html` 即可打开接口文档，不需要写前端就能在线调试接口了

### OpenAI APIKey

（2）修改 `application.yml` 的APIKey配置为你自己的：

```
chatgpt:
  apiKey: "your APIkey..."

```

### Elasticsearch 搜索引擎

（1）修改 `application.yml` 的 Elasticsearch 配置为你自己的：

```
spring:
  elasticsearch:
    uris: http://localhost:9200
    username: root
    password: 123456
```

（2）复制 `sql/post_es_mapping.json` 文件中的内容，通过调用 Elasticsearch 的接口或者 Kibana Dev Tools 来创建索引（相当于数据库建表）

```
PUT post_v1
{
 参数见 sql/post_es_mapping.json 文件
}
```

这步不会操作的话需要补充下 Elasticsearch 的知识，或者自行百度一下~

（3）开启同步任务，将数据库的帖子同步到 Elasticsearch

找到 job 目录下的 `FullSyncPostToEs` 和 `IncSyncPostToEs` 文件，取消掉 `@Component` 注解的注释，再次执行程序即可触发同步（默认开启了，需要无ES启动请注释，不然会报错）：

```
// todo 取消注释开启任务
//@Component
```

（4）运行 Elasticsearch服务

详见参考文档：[https://www.elastic.co/guide/en/elastic-stack/current/index.html](https://www.elastic.co/guide/en/elastic-stack/current/index.html)

> 以上配置完成后就可以正常启动后端项目啦~
>

## 致谢

感谢[鱼皮](https://github.com/liyupi)大佬的指导