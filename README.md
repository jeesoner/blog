<h1 style="text-align: center">Blog</h1>

#### 项目简介
项目基于 Spring Boot 2.3.1、Spring、Spring MVC、Jpa、MySQL、Thymeleaf的个人博客系统。博客编辑支持Markdown语法，支持评论功能

**预览地址：**  [http://www.cijee.top/](http://www.cijee.top/)

#### 主要特性
- 使用Jpa减少了写重复的SQL
- 前后端统一异常拦截处理
- 支持Markdown语法编辑博客

####  系统功能
- 博客管理：编辑，删除博客，使用Markdown语法
- 分类管理：新增，修改，删除分类
- 标签管理：新增，修改，删除标签
- 分类展示：按分类展示博客
- 标签展示：按标签展示博客
- 归档展示：按时间线展示博客
- 关于我页：个人信息
- 游客评论：游客可以对博客进行评论
- 搜索：全文搜索博客

#### 详细结构

```
- blog
	- config 配置静态资源
	- controller 控制器
	- core 配置全局异常，日志处理
	- exception 自定义异常类
	- interceptor 配置拦截器，实现普通用户和管理员分权
	- model 提供PO和Param类
	- repository 为service提供持久化支持
	- service 为controller提供服务支持
	- util 博客工具包
```
