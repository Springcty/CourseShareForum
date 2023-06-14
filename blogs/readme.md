### 后端工程使用指南：

#### 1 结构框架

​	本文档包含两个模块blog-common和blog-server

#### 2 部分依赖设置

springboot版本：3.0.6

JAVA JDK：18

**技术栈使用：**

springboot

mybatisplus

mybatisplus代码生成器（模版velocity）

mysql

Redis缓冲池

lombok用于简化编写

。。。

#### 3 具体使用

##### 3.1 使用流程

- 数据库中新建一个blogs数据库，然后依次运行两个sql文件
- 在idea中打开我们的项目，采用new project from existing，第二步选择第二个用maven创建
- 打开成功后，配置成本机的maven，并刷新
- 直接运行blog-server中的BlogServerApplication即为开启端口，登录localhost:9001即可使用
- 在网页中所有get方法都可以直接使用，如果是post方法需要配合其他软件如postman，apipost使用

##### 3.2 接口使用（不完整

**后端均用Result结构体传回**

```java
public class Result {
    private Boolean flag;
    private Integer code;
    private String message;
    private Object data;
}
public class QueryPageBean implements Serializable {
    private Integer currentPage;    //页码
    private Integer pageSize;       //每页记录数
    private String queryString;     //查询条件
    private String sort;            //排序方式(new / hot)
    private Integer categoryId;     //分类id
    private Integer labelId;        //标签id
}

```

已实现成功的：

- home（首页模块）
    
    | 功能                           | url                           | 前端数据      | 后端数据                                                     |
    | ------------------------------ | ----------------------------- | ------------- | ------------------------------------------------------------ |
    | 获取首页博客文章（最新，最热） | /home/articles（POST）        | QueryPageBean | Result（Page特定分页的数据）                                 |
    | 获取类别信息，包括文章数       | /home/getCategoryCount（GET） | 无数据        | Result（list of categoryVO，继承于category新添加categorycount） |
    | 获取标签信息，包括文章数       | /home/getLabelCount（GET）    | 无数据        | Result（list of LabelVO，继承于label新添加labelcount）       |
    | 获取最新博客                   | /home/latestList（GET）       | 无数据        | Result（list of articlesVO）                                 |
    | 对博客进行搜索                 | /home/search（POST）          | QueryPageBean | Result（Page）                                               |
    
- articles（文章模块）
    
    | 功能                           | url                        | 前端数据                            | 后端数据                     |
    | ------------------------------ | -------------------------- | ----------------------------------- | ---------------------------- |
    | 根据类别筛选文章，包括分页功能 | /articles/category（POST） | QueryPageBean（设置categoryId）     | Result（page of articlesVO） |
    | 根据标签筛选文章，包括分页功能 | /articles/label（POST）    | QueryPageBean（设置labelId）        | Result（page of articlesVO） |
    | 根据文章号读取文章             | /articles/{id}（GET）      | 传回文章的id                        | Result（articlesVO）         |
    | 创建文章                       | /articles/new（POST）      | newArticleBean类                    | Result（要包含什么？）       |
    | 查询用户文章                   | /articles/findPage（POST） | QueryPageBean                       | Result（page of articlesVO） |
    | 上传附件                       | /articles/attach（POST）   | 通过ModelAttribute传回NewAttackBean | Result（文件路径）           |
    
- likes（点赞模块）

    | 功能         | url                       | 前端数据 | 后端数据                 |
    | ------------ | ------------------------- | -------- | ------------------------ |
    | 点赞博客     | /likes/{blogId}（GET）    | 无数据   | Result信息（成功）       |
    | 取消点赞博客 | /likes/{blogId}（Delete） | 无数据   | Result信息（成功或失败） |

- stars（收藏模块）

    | 功能         | url                       | 前端数据 | 后端数据                 |
    | ------------ | ------------------------- | -------- | ------------------------ |
    | 收藏博客     | /stars/{blogId}（GET）    | 无数据   | Result信息（成功）       |
    | 取消收藏博客 | /stars/{blogId}（Delete） | 无数据   | Result信息（成功或失败） |

- users（用户模块）

    | 功能     | url                        | 前端数据                      | 后端数据                          |
    | -------- | -------------------------- | ----------------------------- | --------------------------------- |
    | 登录     | /users/login（POST）       | Users类                       | Result（hashmap of <token, users> |
    | 登出     | /users/logout（GET）       | (head-Authorization)token     | Result信息                        |
    | 注册     | /users/register（POST）    | Users类                       | Result（users类）                 |
    | 更新     | /users/updateUser（POST）  | UsersVO类                     | Result信息                        |
    | 上传头像 | /users/avatar/set（POST）  | (ModelAttribute)NewAvatarBean | Result信息                        |
    | 获取头像 | /users/avatar/get/{userId} | 无数据                        | Result（图片路径）                |
    
- comments（评论模块）

    | 功能     | url                                  | 前端数据  | 后端数据                    |
    | -------- | ------------------------------------ | --------- | --------------------------- |
    | 获取评论 | /comment/{blogId}（GET）             | 无数据    | Result（List of CommentVO） |
    | 发表评论 | /comment/reply（POST）               | Comment类 | Result（CommentVo）         |
    | 删除评论 | /comment/delete/{blogId}/{commentId} | 无数据    | Result信息                  |





##### 3.3 代码使用

后端框架：

- 数据库原型entity

- 数据层Dao，内部包含大量数据库接口，自定义数据库接口也在其中，还可使用mybatis的xml定义接口

- 服务层service，连接数据层和表现层，用于包装数据库接口，需要修改声明和实现两个文件

- 表现层controller，直接连接前端的接口，采用restful（看教程），其中传入数据包括queryPageBean（其中内置大量问询条件）等类，

    传出数据均为Result类方便使用，具体见代码中。

##### 3.4 编写规范

在进行修改的时候，最好每次修改时都在以下注明：对某某文件做怎么样的处理，对数据库做怎样的处理

另外数据库尽量在这两天完全确定，否则之后对代码的修改会有点麻烦。

#### 4 编写问题

- 在原博客中，分类显示下，如果翻页后，则分类高亮消失