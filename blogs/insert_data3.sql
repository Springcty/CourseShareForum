use blogs;
INSERT INTO users VALUES (1, 'Huhu', '4fb0dd2f73f84cbb8e5624097a856be8', NULL, 1);
INSERT INTO users VALUES (2, 'AJin', '4120f8008f36ac64e23b2d9417b5611c', NULL, 1);
INSERT INTO users VALUES (3, 'Jiahe Xu', '00fad950824ccb2e86fd70452182d9da', NULL, 1);
INSERT INTO users VALUES (4, 'hii', '7a22afdb187e7d7ae833110441d7fcac', NULL, 1);
INSERT INTO users VALUES (5, 'qyf', '6dbb62284f98f6227658c8e253abba99', NULL, 1);
INSERT INTO users VALUES (6, '泡泡柚子茶', '8628c2e5f396184228d3619a22457f00', NULL, 0);
INSERT INTO users VALUES (7, '牛油王者', '4271c4cb674c8f4b17f5569b3b9c835f', NULL, 0);
INSERT INTO users VALUES (8, 'livinginwater', '27f9d80b98bb2b7620254cf1c5623305', NULL, 0);
INSERT INTO users VALUES (9, 'Guanine', 'a44b4a9f78f9a1fd0b52f80f3e51b8b3', NULL, 0);
INSERT INTO users VALUES (10, 'Moli', '7f1fea79c6a223f627614815fb999eb7', NULL, 0);
INSERT INTO users VALUES (11, '爱吃萝卜爱吃菜', '35abb24572162b3897ae5e5573468712', NULL, 0);
INSERT INTO users VALUES (12, '装修摆烂日记', '1849496fd85c957d90798f9a03ad84da', NULL, 0);
INSERT INTO users VALUES (13, 'momo', '2ebf4b755e3e99fde2834731cf4c3247', NULL, 0);
INSERT INTO users VALUES (14, 'kan-w', '76e21abd7a907c2330f2842a04ec8950', NULL, 0);
INSERT INTO users VALUES (15, '天気小雨', 'f47251de85cdc2b7b6011f5a861478a0', NULL, 0);
INSERT INTO users VALUES (16, '红糖小糍粑', '569620190b847d9cc4742674c96ff862', NULL, 0);
INSERT INTO users VALUES (17, 'Laure', 'ba3da36af2a164b914e6a314da860962', NULL, 0);
INSERT INTO users VALUES (18, '阿蕾莎de咖啡渣', 'a1a73889a8287788037e983d5683eeb8', NULL, 0);
INSERT INTO users VALUES (19, '站在赤道上烫脚', '4719ac209e0b414b6e8659ad6329620c', NULL, 0);
INSERT INTO users VALUES (20, '哆啦A面', '8f0354edeb36abd7bc792fca1fe883c9', NULL, 0);


INSERT INTO articles VALUES (1, 0, 0, '2020-06-12 20:28:41', 3, 'IDEA乱码问题', '# IDEA乱码问题

### 为啥出现乱码

简单解释：语言不通，你用英语编写一篇文章，并且告诉我这是中文写的，然后我翻译错误，没有正确翻译出来，所以这些看不懂的译文就是”乱码“
不过还有个注意点，这里的编码跟语言还是有些不同，比如gbk编码能够正常显示utf-8编码的文章
但是反过来可能就不行了，gbk编写的内容转为utf-8就会出现乱码', '浅聊IDEA乱码问题', 0, 0, 0, 0, 4);
INSERT INTO articles VALUES (2, 1, 0, '2022-03-07 00:13:25', 2, 'Python如何判断是文件还是文件夹', '# Python如何判断是文件还是文件夹

怎样使用 Python 来判断一个路径是否存在判断一个路径是文件还是目录

- 判断一个路径是否存在，可以判断一个文件或目录(文件夹)是否存在

```
import os.path
os.path.exists(path)
```

- 判断一个文件是否存在

```
import os.path
os.path.isfile(path)
```

- 判断一个目录(文件夹)是否存在


```
import os.path
os.path.isdir(path)
```', 'Python如何判断是文件还是文件夹', 0, 0, 0, 0, 7);
INSERT INTO articles VALUES (3, 0, 0, '2023-04-16 07:15:37', 4, 'Java面试题（一）', '# Java面试题

### 1.什么是可变参数？

​	可变参数允许调用参数数量不同的方法。请看下面例子中的求和方法。此方法可以调用1个int参数，或2个int参数，或多个int参数。

```java
//int(type) followed ... (three dots) is syntax of a variable argument. 
    public int sum(int... numbers) {
        //inside the method a variable argument is similar to an array.
        //number can be treated as if it is declared as int[] numbers;
        int sum = 0;
        for (int number: numbers) {
            sum += number;
        }
        return sum;
    }
    public static void main(String[] args) {
    VariableArgumentExamples example = new VariableArgumentExamples();
    //3 Arguments
    System.out.println(example.sum(1, 4, 5));//10
    //4 Arguments
    System.out.println(example.sum(1, 4, 5, 20));//30
    //0 Arguments
    System.out.println(example.sum());//0
}
```
', '什么是可变参数？', 0, 0, 0, 0, 11);
INSERT INTO articles VALUES (4, 1, 1, '2022-09-24 16:56:01', 4, 'Java面试题（二）', '# Java面试题

### 2.断言的用途？

​	断言是在Java 1.4中引入的。它能让你验证假设。如果断言失败（即返回false），就会抛出AssertionError（如果启用断言）。基本断言如下所示。

```java
private int computerSimpleInterest(int principal,float interest,int years){
    assert(principal>0);
    return 100;
}
```
', '断言的用途？', 0, 0, 0, 0, 15);
INSERT INTO articles VALUES (5, 1, 0, '2021-05-18 10:08:54', 4, 'Java面试题（三）', '# Java面试题


### 3.什么时候使用断言？

断言不应该用于验证输入数据到一个public方法或命令行参数。`IllegalArgumentException`会是一个更好的选择。在public方法中，只用断言来检查它们根本不应该发生的情况。
', '什么时候使用断言？', 0, 0, 0, 0, 9);
INSERT INTO articles VALUES (6, 1, 1, '2023-11-19 09:04:39', 4, 'Java面试题（四）', '# Java面试题

### 4.什么是垃圾回收？

垃圾回收是Java中自动内存管理的另一种叫法。垃圾回收的目的是为程序保持尽可能多的可用堆（heap）。 JVM会删除堆上不再需要从堆引用的对象。', '什么是垃圾回收？', 0, 0, 0, 0, 15);



INSERT INTO labels VALUES (1, 'JAVA');
INSERT INTO labels VALUES (2, '数据库');
INSERT INTO labels VALUES (3, '软件工程');
INSERT INTO labels VALUES (4, '编译原理');
INSERT INTO labels VALUES (5, '数值分析');
INSERT INTO labels VALUES (6, 'C++');
INSERT INTO labels VALUES (7, '计算理论');
INSERT INTO labels VALUES (8, '人工智能');
INSERT INTO labels VALUES (9, '计算机科学思想史');
INSERT INTO labels VALUES (10, '计算机视觉');
INSERT INTO labels VALUES (11, '计算机图形学');
INSERT INTO labels VALUES (12, 'Python');


INSERT INTO categories VALUES (1, '真题');
INSERT INTO categories VALUES (3, '课件');
INSERT INTO categories VALUES (2, '作业');
INSERT INTO categories VALUES (4, '经验');
INSERT INTO categories VALUES (5, '求助');
INSERT INTO categories VALUES (6, '其他');


INSERT INTO blog_label VALUES(1, 1);
INSERT INTO blog_label VALUES(1, 6);
INSERT INTO blog_label VALUES(2, 12);
INSERT INTO blog_label VALUES(3, 1);
INSERT INTO blog_label VALUES(4, 1);
INSERT INTO blog_label VALUES(5, 1);
INSERT INTO blog_label VALUES(6, 1);


INSERT INTO like_items VALUES (1, 2, 1);
INSERT INTO like_items VALUES (2, 4, 1);
INSERT INTO like_items VALUES (3, 5, 3);
INSERT INTO like_items VALUES (4, 6, 4);
INSERT INTO like_items VALUES (5, 1, 19);
INSERT INTO like_items VALUES (6, 1, 17);
INSERT INTO like_items VALUES (7, 6, 4);
INSERT INTO like_items VALUES (8, 3, 5);
INSERT INTO like_items VALUES (9, 5, 7);
INSERT INTO like_items VALUES (10, 1, 16);

INSERT INTO star_items VALUES (1, 2, 1);
INSERT INTO star_items VALUES (2, 4, 4);
INSERT INTO star_items VALUES (3, 6, 3);
INSERT INTO star_items VALUES (4, 3, 3);
INSERT INTO star_items VALUES (5, 1, 7);
INSERT INTO star_items VALUES (6, 6, 18);
INSERT INTO star_items VALUES (7, 1, 15);
INSERT INTO star_items VALUES (8, 4, 9);
INSERT INTO star_items VALUES (9, 5, 14);

INSERT INTO comment_items VALUES (1, 2, 1, '2023-01-21 03:40:02', '你的文章写得太好了！');
INSERT INTO comment_items VALUES (2, 4, 2, '2023-06-07 10:35:04', '学到了，感恩。');
INSERT INTO comment_items VALUES (3, 6, 3, '2023-05-29 14:29:43', '原来是这样的，感谢作者的分享');

INSERT INTO follow_items VALUES (1, 1, 2);
INSERT INTO follow_items VALUES (2, 2, 4);
INSERT INTO follow_items VALUES (3, 3, 1);
INSERT INTO follow_items VALUES (4, 4, 18);
INSERT INTO follow_items VALUES (5, 11, 20);
INSERT INTO follow_items VALUES (6, 3, 11);
INSERT INTO follow_items VALUES (7, 17, 8);
INSERT INTO follow_items VALUES (8, 12, 3);
INSERT INTO follow_items VALUES (9, 16, 10);


INSERT INTO menu VALUES (1, '用户管理', '/users', 'Layout', 'el-icon-s-custom', 1, null, 0, 1);
INSERT INTO menu VALUES (2, '个人信息', '/users', '/admin/users/Users.vue', 'el-icon-user-solid',  1, 1, 0, 1);
INSERT INTO menu VALUES (3, '博客管理', '/PostBlogs', 'Layout', 'el-icon-edit-outline', 2, null, 0, 1);
INSERT INTO menu VALUES (4, '发布博客', '/PostBlogs', '/admin/blogs/PostBlogs.vue', 'el-icon-edit',  1, 3, 0, 1);
INSERT INTO menu VALUES (5, '查看博客', '/Blogs', '/admin/blogs/Blogs.vue', 'el-icon-view',  2, 3, 0, 1);
INSERT INTO menu VALUES (6, '收藏夹',  '/FavoriteBlogs', '/admin/blogs/FavoriteBlogs.vue', 'el-icon-star-on', 3, 3, 0, 1);

INSERT INTO menu VALUES (7, '首页', '/', '/home/Home.vue', 'el-icon-myshouye', 1, null, 0, 2);
INSERT INTO menu VALUES (8, '文章管理', '/article-submenu', 'Layout', 'el-icon-mywenzhang-copy', 2, null, 0, 2);
INSERT INTO menu VALUES (9, '消息管理', '/message-submenu', 'Layout', 'el-icon-myxiaoxi', 3, null, 0, 2);
INSERT INTO menu VALUES (10, '用户管理', '/users-submenu', 'Layout', 'el-icon-myyonghuliebiao', 4, null, 0, 2);
INSERT INTO menu VALUES (11, '文章列表', '/article-list', '/article/ArticleList.vue', 'el-icon-mywenzhangliebiao', 1, 8, 0, 2);
INSERT INTO menu VALUES (12, '分类管理', '/categories', '/category/Category.vue', 'el-icon-myfenlei', 2, 8, 0, 2);
INSERT INTO menu VALUES (13, '标签管理', '/tags', '/tag/Tag.vue', 'el-icon-myicontag', 3, 8, 0, 2);
INSERT INTO menu VALUES (14, '评论管理', '/comments', '/comment/Comment.vue', 'el-icon-mypinglunzu', 4, 9, 0, 2);
INSERT INTO menu VALUES (15, '用户列表', '/users', '/user/User.vue', 'el-icon-myyonghuliebiao', 5, 10, 0, 2);

