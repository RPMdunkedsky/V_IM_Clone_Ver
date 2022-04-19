### 需要购买的阿里云的同学 请点击支持 [阿里云优惠券2000元](https://chuangke.aliyun.com/invite?userCode=d4l0ykh3)
### 务必记得点赞哦
### 加微:zkp_java 务必备注：v-im 并且附上点赞的 gitee 用户名，不点赞不让加哦
### Create by  [webstorm and intellij IDEA]
### 说明
>   系统是在RuoYi-vue(https://gitee.com/y_project/RuoYi-Vue) 的基础上开发的，但是把数据库操作改成mybatis-plus,原先的是mybatis（如果你想完全迁移到RuoYi系统里面，可能还需要一定的工作量）。
>   老版本在 old-master 分支。
### 结构
>   1. V-IM-PC 是客户端
>   2. V-IM-Server 是服务端代码
>   2. ruoyi-ui-vue3 是管理系统的前端代码
>   3. doc 下面有数据库。

### 常见问题
>   1. 如果出现 Parsing error: x-invalid-end-tag  vue/no-parsing-error 类似的错误，可用用 webstorm 右键src 目录 Fix eslint problems，
>   2. 安装不成功，请先执行 npm install node-sass。如果还是错误请多重试下！
>   3. 很多同学安装electron失败，导致不能打包，可以尝试用cnpm或者yarn安装，或者多重试几次。


#### 文档
1. 安装依赖命令：yarn 。
2. 开发环境命令：npm run serve 和 npm run electron:serve。
3. 打包安装文件：npm run electron:build，打包完成的文件在/dist_electron 下。
4. 打包web文件：npm run build，打包完成的文件在/dist 下。

### 注意事项

> 1. 打包时候项目路径不能有中文，包括你 windows 用户都不能有中文字符，因为npm 缓存都是在用户目录下（如果原先的用户名是中文，再修改成英文也不好用，因为原先的npm包都还在中文目录下，可以新建个window 英文账号，登录新账号打包）。
> 2. 使用yarn 安装依赖，npm 不是很好用，尝试过，都不能打包成功。如果yarn 不能安装依赖成功，可以多试几次！
> 3. 基于 t-io websocket 协议， 据说能支持百万级并发，但是此项目并没有进行此方面的测试，还请知晓 ！
> 4. 数据库mysql,缓存redis，消息聊天记录在redis里面存着，私聊key:message-{minUserID}-{maxUserId}（两个人的id肯定是大小值不一样），群聊key:message-{groupId},表im-message暂时没有用。

### 截图

![消息列表/聊天](https://gitee.com/lele-666/V-IM/raw/master/doc/img/1.png)
![好友](https://gitee.com/lele-666/V-IM/raw/master/doc/img/2.png)
![组织](https://gitee.com/lele-666/V-IM/raw/master/doc/img/3.png)
![群组](https://gitee.com/lele-666/V-IM/raw/master/doc/img/4.png)
![添加好友](https://gitee.com/lele-666/V-IM/raw/master/doc/img/5.png)
 

### 功能点
1. 文本聊天
2. 聊天表情
3. 发送图片（http）
4. 发送文件（http）
5. 单聊
6. 群聊
7. 用户分组（后端支持）
8. 离线消息（单聊+群聊，支持消息提醒）
9. 聊天记录（单聊、群聊）
10. 支持心跳检测，断线重连
11. 使用SpringBoot security oauth2.0 支持单点登录。
12. 好友添加。(新增)
13. 群管理(新增)
14. 带有管理后台（原ruoyi-vue）
15. 树状组织机构


### 登录测试
> 1. admin/admin123,ry/admin123。

### 参考项目及技术
> 1. RuoYi-vue（https://gitee.com/y_project/RuoYi-Vue）
> 2. layIM（主要是聊天表情，文件处理方面）。
> 3. 使用SpringBoot、oauth2.0、t-io 开发后端服务。
> 4. vue3.0、element-plus、typescript开发前端。
> 5. 界面高仿微信，如有侵权请告知。
> 6. 其他：使用 fetch 发送ajax 请求，支持跨域，electron 支持打包成为exe，也支持linux 和 mac 目前还没测试，有条件的同学可以测试。

### 交流授权
>  1. 如果您觉得好用，可以给点个star，或者给个捐赠。
>  2. 如需定制或者私有化部署，请加微:zkp_java。
