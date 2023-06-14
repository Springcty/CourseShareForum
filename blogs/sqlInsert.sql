use blogs;
INSERT INTO users VALUES (1, 'user1', '3dc5bd4dc9a639199fc6214126ffbb08', NULL, 1);
INSERT INTO users VALUES (2, 'user2', 'password2', NULL, 0);
INSERT INTO users VALUES (3, 'user3', 'password3', NULL, 0);
INSERT INTO users VALUES (4, 'hii', '7a22afdb187e7d7ae833110441d7fcac', NULL, 0);


select * from users;
INSERT INTO articles VALUES (1, 0, 0, '2020-01-01 00:00:01', 1, 'title1', 'hello1', 'descri1', 0, 0, 0, 0, 1);
INSERT INTO articles VALUES (2, 1, 1, '2020-01-01 00:00:02', 1, 'title1', 'hello1', 'descri2', 1, 1, 1, 1, 1);
INSERT INTO articles VALUES (3, 0, 0, '2020-01-01 00:00:03', 2, 'title2', 'hello2', 'descri3', 0, 0, 2, 0, 2);
INSERT INTO articles VALUES (4, 1, 1, '2020-01-01 00:00:04', 2, 'title2', 'hello2', 'descri4', 1, 1, 3, 1, 2);
INSERT INTO articles VALUES (5, 0, 0, '2020-01-01 00:00:05', 3, 'title3', 'hello3', 'descri5', 0, 0, 4, 0, 3);
INSERT INTO articles VALUES (6, 1, 1, '2020-01-01 00:00:06', 3, 'title3', 'hello3', 'descri6', 1, 1, 5, 1, 3);

INSERT INTO blog_label VALUES(1, 1);
INSERT INTO blog_label VALUES(1, 2);
INSERT INTO blog_label VALUES(2, 3);
INSERT INTO blog_label VALUES(2, 1);
INSERT INTO blog_label VALUES(3, 2);

INSERT INTO labels VALUES (1, 'label1');
INSERT INTO labels VALUES (2, 'label2');
INSERT INTO labels VALUES (3, 'label3');

INSERT INTO categories VALUES (1, '真题');
INSERT INTO categories VALUES (3, '课件');
INSERT INTO categories VALUES (2, '作业');

INSERT INTO like_items VALUES (1, 2, 1);
INSERT INTO like_items VALUES (2, 4, 2);
INSERT INTO like_items VALUES (3, 6, 3);

INSERT INTO star_items VALUES (1, 2, 1);
INSERT INTO star_items VALUES (2, 4, 2);
INSERT INTO star_items VALUES (3, 6, 3);

INSERT INTO comment_items VALUES (1, 2, 1, '2020-01-01 00:00:02', 'helloworld1');
INSERT INTO comment_items VALUES (2, 4, 2, '2020-01-01 00:00:04', 'helloworld2');
INSERT INTO comment_items VALUES (3, 6, 3, '2020-01-01 00:00:06', 'helloworld3');

INSERT INTO follow_items VALUES (1, 1, 2);
INSERT INTO follow_items VALUES (2, 2, 3);
INSERT INTO follow_items VALUES (3, 3, 1);

-- INSERT INTO menu VALUES ('1', '首页', '/', '/home/Home.vue', 'el-icon-myshouye', '2021-01-26 17:06:51', '2021-01-26 17:06:53', '1', null, '0', '2');
-- INSERT INTO menu VALUES ('2', '文章管理', '/article-submenu', 'Layout', 'el-icon-mywenzhang-copy', '2021-01-25 20:43:07', null, '2', null, '0', '2');
-- INSERT INTO menu VALUES ('3', '消息管理', '/message-submenu', 'Layout', 'el-icon-myxiaoxi', '2021-01-25 20:44:17', '2021-01-25 20:44:20', '3', null, '0', '2');
-- INSERT INTO menu VALUES ('4', '系统管理', '/system-submenu', 'Layout', 'el-icon-myshezhi', '2021-01-25 20:45:57', '2021-01-25 20:45:59', '5', null, '0', '2');
-- INSERT INTO menu VALUES ('6', '发布文章', '/articles', '/article/Article.vue', 'el-icon-myfabiaowenzhang', '2021-01-26 14:30:48', null, '1', '2', '0', '2');
-- INSERT INTO menu VALUES ('7', '修改文章', '/articles/*', '/article/Article.vue', 'el-icon-myfabiaowenzhang', '2021-01-26 14:31:32', '2021-01-26 14:31:34', '2', '2', '1', '2');
-- INSERT INTO menu VALUES ('8', '文章列表', '/article-list', '/article/ArticleList.vue', 'el-icon-mywenzhangliebiao', '2021-01-26 14:32:13', '2021-01-26 14:32:16', '3', '2', '0', '2');
-- INSERT INTO menu VALUES ('9', '分类管理', '/categories', '/category/Category.vue', 'el-icon-myfenlei', '2021-01-26 14:33:42', '2021-01-26 14:33:43', '4', '2', '0', '2');
-- INSERT INTO menu VALUES ('10', '标签管理', '/tags', '/tag/Tag.vue', 'el-icon-myicontag', '2021-01-26 14:34:33', '2021-01-26 14:34:36', '5', '2', '0', '2');
-- INSERT INTO menu VALUES ('11', '评论管理', '/comments', '/comment/Comment.vue', 'el-icon-mypinglunzu', '2021-01-26 14:35:31', '2021-01-26 14:35:34', '1', '3', '0', '2');
-- INSERT INTO menu VALUES ('12', '留言管理', '/messages', '/message/Message.vue', 'el-icon-myliuyan', '2021-01-26 14:36:09', '2021-01-26 14:36:13', '2', '3', '0', '2');
-- INSERT INTO menu VALUES ('13', '用户列表', '/users', '/user/User.vue', 'el-icon-myyonghuliebiao', '2021-01-26 14:38:09', '2021-01-26 14:38:12', '1', '22', '0', '2');
-- INSERT INTO menu VALUES ('14', '角色管理', '/roles', '/role/Role.vue', 'el-icon-myjiaoseliebiao', '2021-01-26 14:39:01', '2021-01-26 14:39:03', '2', '23', '0', '2');
-- INSERT INTO menu VALUES ('15', '接口管理', '/resources', '/resource/Resource.vue', 'el-icon-myjiekouguanli', '2021-01-26 14:40:14', '2021-08-07 20:00:28', '2', '23', '0', '2');
-- INSERT INTO menu VALUES ('16', '菜单管理', '/menus', '/menu/Menu.vue', 'el-icon-mycaidan', '2021-01-26 14:40:54', '2021-08-07 10:18:49', '2', '23', '0', '2');
-- INSERT INTO menu VALUES ('17', '友链管理', '/links', '/friendLink/FriendLink.vue', 'el-icon-mydashujukeshihuaico-', '2021-01-26 14:41:35', '2021-01-26 14:41:37', '3', '4', '0', '2');
-- INSERT INTO menu VALUES ('18', '关于我', '/about', '/about/About.vue', 'el-icon-myguanyuwo', '2021-01-26 14:42:05', '2021-01-26 14:42:10', '4', '4', '0', '2');
-- INSERT INTO menu VALUES ('19', '日志管理', '/log-submenu', 'Layout', 'el-icon-myguanyuwo', '2021-01-31 21:33:56', '2021-01-31 21:33:59', '6', null, '0', '2');
-- INSERT INTO menu VALUES ('20', '操作日志', '/operation/log', '/log/Operation.vue', 'el-icon-myguanyuwo', '2021-01-31 15:53:21', '2021-01-31 15:53:25', '1', '19', '0', '2');
-- INSERT INTO menu VALUES ('21', '在线用户', '/online/users', '/user/Online.vue', 'el-icon-myyonghuliebiao', '2021-02-05 14:59:51', '2021-02-05 14:59:53', '7', '22', '0', '2');
-- INSERT INTO menu VALUES ('22', '用户管理', '/users-submenu', 'Layout', 'el-icon-myyonghuliebiao', '2021-02-06 23:44:59', '2021-02-06 23:45:03', '4', null, '0', '2');
-- INSERT INTO menu VALUES ('23', '权限管理', '/permission-submenu', 'Layout', 'el-icon-mydaohanglantubiao_quanxianguanli', '2021-08-07 19:56:55', '2021-08-07 19:59:40', '4', null, '0', '2');
-- INSERT INTO menu VALUES ('101', '用户管理', '/users', 'Layout', 'el-icon-s-custom', '2022-03-15 20:31:19', null, '1', null, '0', '1');
-- INSERT INTO menu VALUES ('102', '个人信息', '/users', '/admin/users/Users.vue', 'el-icon-user-solid', '2022-03-15 20:33:25', null, '1', '101', '0', '1');
-- INSERT INTO menu VALUES ('103', '博客管理', '/PostBlogs', 'Layout', 'el-icon-edit-outline', '2022-03-16 16:14:14', null, '2', null, '0', '1');
-- INSERT INTO menu VALUES ('104', '发布博客', '/PostBlogs', '/admin/blogs/PostBlogs.vue', 'el-icon-edit', '2022-03-16 16:15:12', null, '1', '103', '0', '1');
-- INSERT INTO menu VALUES ('105', '查看博客', '/blogs', '/admin/blogs/Blogs.vue', 'el-icon-view', '2022-03-16 16:16:09', null, '2', '103', '0', '1');
-- INSERT INTO menu VALUES ('106', '收藏夹', '/FavoriteBlogs', '/admin/blogs/FavoriteBlogs.vue', 'el-icon-star-on', '2022-03-16 16:17:30', null, '3', '103', '0', '1');
-- INSERT INTO menu VALUES ('107', '数据统计', '/Report', 'Layout', 'el-icon-s-marketing', '2022-03-16 16:18:33', null, '3', null, '0', '1');
-- INSERT INTO menu VALUES ('108', '博文数据', '/Report', '/admin/dataWatch/Report.vue', 'el-icon-s-data', '2022-03-16 16:19:40', null, '1', '107', '0', '1');
-- INSERT INTO menu VALUES ('109', '单篇博客', '/Report2', '/admin/dataWatch/Report2.vue', 'el-icon-s-management', '2022-03-16 16:20:19', null, '2', '107', '0', '1');

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

