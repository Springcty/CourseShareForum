<template>
  <div class="blogs">
    <div class="content-header">
      <h1>博客管理<small>发布博客</small></h1>
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb">
        <el-breadcrumb-item :to="{ path: '/' }">后台管理</el-breadcrumb-item>
        <el-breadcrumb-item>博客管理</el-breadcrumb-item>
        <el-breadcrumb-item>发布博客</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
      <div class="app-container">
        <div class="blog-box" style="">
        <el-form ref="addForm" :model="formData" :rules="rules" >
          <div class="required field">
            <div class="ui left labeled input">
              <el-form-item prop="title">
                <el-button type="primary">
                  <i class="el-icon-arrow-down el-icon-s-opportunity"></i>
                </el-button>
                <el-input v-model="formData.title" placeholder="请输入标题" style="margin-left: 155px;float:left; width: 790px; margin-right: 8px"></el-input>
              </el-form-item>
            </div>
          </div>
            <div class="mavonEditor" style="margin-top: 10px;">
              <el-form-item prop="content">
                <mavon-editor :codeStyle="markdownOption.codeStyle"
                              ref=md
                              style="max-height: 500px"
                              :scrollStyle="true"
                              :ishljs="true"
                              @imgAdd="handleEditorImgAdd"
                              @imgDel="handleEditorImgDel"
                              :toolbars="markdownOption"
                              v-model="formData.content"/>
              </el-form-item>
          </div>
          <div class="two fields" style="margin-top: 3px">
            <el-form-item prop="categoryId">
              <el-row>
                <el-select v-model="formData.categoryId" placeholder="请选择分类" style="margin-left: 155px;float:left; width: 470px; margin-right: 8px">
                  <el-option
                    v-for="item in typeList"
                    :key="item.id"
                    :label="item.categoryname"
                    :value="item.id">
                  </el-option>
                </el-select>
                <el-select v-model="formData.labelIds" multiple placeholder="请选择标签" style="width: 470px">
                  <el-option
                    v-for="item in tagList"
                    :key="item.id"
                    :label="item.labelname"
                    :value="item.id">
                  </el-option>
                </el-select>
              </el-row>
            </el-form-item>
          </div>

          <div class="field" style="margin-top: 8px;margin-left: 155px">
            <!-- <el-button type="primary">
            <el-upload
              class="avatar-uploader"
              action="serverApi/file/articleImage"
              accept="image/png,.jpg"
              multiple
              :limit="1"
              :on-exceed="masterFileMax"
              :show-file-list="false"
              :http-request="uploadPic"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
              <img v-if="imageUrl" :src="imageUrl" class="avatar">
              <i v-else class="el-icon-arrow-down el-icon-picture"></i>
            </el-upload>
            </el-button> -->
            <!-- <el-input v-model="formData.firstPicture" style="width: 895px"></el-input> -->
            <el-form-item prop="descript" style="margin-top: 8px">
              <el-input
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 5}"
                placeholder="请写一下关于文章的摘要，这将让你的博客显示在首页时，帮助你吸引更多的读者"
                v-model="formData.descript" style="margin-top: 10px;width: 950px">
              </el-input>
            </el-form-item>
          </div>
          <div class="ui right aligned container">
            <button type="button" class="ui button" onclick="window.history.go(-1)" >返回</button>
            <button type="button" id="save-btn" class="ui secondary button">保存</button>
            <button type="button" v-if="buttonFlag" id="publish-btn" class="ui my-blue button" @click="addOrUpdateBlog">发布</button>
            <button type="button" v-if="!buttonFlag" id="update-btn" class="ui my-blue button" @click="addOrUpdateBlog">更新</button>
          </div>
        </el-form>
        </div>
      </div>
    <br>
    <br>
  </div>
</template>

<script>
// import editormd from '../../../src/assets/lib/editormd/editormd.min.js'
export default {
  data () {
    return {
      imageUrl: '',
      imgFile: [],
      buttonFlag: false, // false显示发布,true显示更新
      // 被激活的链接地址
      avatar: '',
      rules: { // 校验规则
        title: [
          { required: true, message: '请输入标题', trigger: 'blur' },
          { min: 2, max: 100, message: '长度在 2 到 100 个字符', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '请输入正文内容', trigger: 'blur' },
          { min: 10, message: '长度最少是 10 个字符', trigger: 'blur' }
        ],
        categoryId: [
          { required: true, message: '至少要有一个分类', trigger: 'blur' }
        ],
        descript: [
          { required: true, message: '麻烦写一个简单的摘要', trigger: 'blur' },
          { min: 10, max: 110, message: '长度在 10 到 110 个字符之间', trigger: 'blur' }
        ]
      },
      formData: {
        categoryId: '', // 分类id
        labelIds: [], // 标签列表
        title: '', // 博客标题  title
        descript: '', // discript
        authorId: '', // author
        content: '#### 使用 markdown 编辑器来开始书写你的博客吧!&emsp;已经支持markdown编辑器上传图片的功能', // 正文文本
      }, // 表单数据
      toolbars: {
        bold: true, // 粗体
        italic: true, // 斜体
        header: true, // 标题
        underline: true, // 下划线
        strikethrough: true, // 中划线
        mark: true, // 标记
        superscript: true, // 上角标
        subscript: true, // 下角标
        quote: true, // 引用
        ol: true, // 有序列表
        ul: true, // 无序列表
        link: true, // 链接
        imagelink: true, // 图片链接
        code: true, // code
        table: true, // 表格
        fullscreen: true, // 全屏编辑
        readmodel: true, // 沉浸式阅读
        htmlcode: true, // 展示html源码
        help: true, // 帮助
        /* 1.3.5 */
        undo: true, // 上一步
        redo: true, // 下一步
        trash: true, // 清空
        save: true, // 保存（触发events中的save事件）
        /* 1.4.2 */
        navigation: true, // 导航目录
        /* 2.1.8 */
        alignleft: true, // 左对齐
        aligncenter: true, // 居中
        alignright: true, // 右对齐
        /* 2.2.1 */
        subfield: true, // 单双栏模式
        preview: true // 预览
      },
      typeList: [],
      tagList: [],
      contentEditor: '',
      markdownOption: {
        bold: true, // 粗体
        italic: true, // 斜体
        header: true, // 标题
        underline: true, // 下划线
        strikethrough: true, // 中划线
        mark: true, // 标记
        superscript: true, // 上角标
        subscript: true, // 下角标
        quote: true, // 引用
        ol: true, // 有序列表
        ul: true, // 无序列表
        link: true, // 链接
        imagelink: true, // 图片链接
        code: true, // code
        table: true, // 表格
        fullscreen: true, // 全屏编辑
        readmodel: true, // 沉浸式阅读
        htmlcode: true, // 展示html源码
        help: true, // 帮助
        /* 1.3.5 */
        undo: true, // 上一步
        redo: true, // 下一步
        trash: true, // 清空
        save: true, // 保存（触发events中的save事件）
        /* 1.4.2 */
        navigation: true, // 导航目录
        /* 2.1.8 */
        alignleft: true, // 左对齐
        aligncenter: true, // 居中
        alignright: true, // 右对齐
        /* 2.2.1 */
        subfield: true, // 单双栏模式
        preview: true, // 预览
        codeStyle: 'monokai-sublime'
      }
    }
  },
  created () {
    this.getTypeList()
    this.getTagList()
    this.getOneBlog()
    this.formData.authorId = this.$store.state.id
  },
  methods: {
    // 获取所有的菜单
    async getOneBlog () {
      const blogId = this.$store.state.adminBlogId
      if (blogId != null) { // 代表是编辑博客, 要回显数据
        this.buttonFlag = false
        const { data: res } = await this.$http.get(`/api/articles/${blogId}`)
        if (!res.flag) {
          return this.$message.error(res.message)
        }
        this.formData.blogId = blogId
        this.formData.categoryId = res.data.categoryId
        this.formData.title = res.data.title
        this.formData.descript = res.data.descript
        this.formData.content = res.data.content
      } else {
        this.buttonFlag = true
      }
    },
    // masterFileMax (files, fileList) {
    //   this.$message.warning('请最多上传一张图片')
    // },
    // async uploadPic (param) {
    //   var fileObj = param.file
    //   var form = new FormData()
    //   // 文件对象
    //   form.append('file', fileObj)
    //   const { data: res } = await this.$http.post('/serverApi/file/articleImage', form)
    //   if (res.flag) {
    //     // 弹出提示信息
    //     this.$message.success('上传图片成功')
    //     this.formData.avatar = res.data.url
    //   } else { // 执行失败
    //     this.$message.error(res.message)
    //   }
    // },
    // handleAvatarSuccess (res, file) {
    //   this.imageUrl = URL.createObjectURL(file.raw)
    // },
    // beforeAvatarUpload (file) {
    //   const isJPG = file.type === 'image/jpeg'
    //   const isLt5M = file.size / 1024 / 1024 < 5

    //   if (!isJPG) {
    //     this.$message.error('上传头像图片只能是 JPG 格式!')
    //   }
    //   if (!isLt5M) {
    //     this.$message.error('上传头像图片大小不能超过5MB!')
    //   }
    //   return isJPG && isLt5M
    // },
    handleEditorImgAdd (pos, $file) {
      var _this = this;
      const formData = new FormData()
      formData.append('file', $file)
      this.imgFile[pos] = $file
      this.$http.post('/api/articles/articleImage', formData).then(res => {
        if (res.data.flag) {
          this.$message.success('上传成功')
          _this.$refs.md.$imglst2Url([[pos, res.data.data.url]])
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    handleEditorImgDel (pos) {
      delete this.imgFile[pos]
      this.$message.error('暂时无法删除图片！')
    },
    addOrUpdateBlog () {
      // 进行表单校验
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          // 表单校验通过，发ajax请求，把数据录入至后台处理
          // const param = this.$encrypTion(JSON.stringify(this.formData))
          // this.formData.flag = '发布'
          const param = {
            content: this.formData.content,
            categoryId: this.formData.categoryId,
            labelIds: this.formData.labelIds,
            title: this.formData.title,
            descript: this.formData.descript,
            authorId: this.formData.authorId,
          }
          this.$http.post('http://101.37.254.247:9001/articles/new', param).then((res) => {
            // 关闭新增窗口
            this.dialogFormVisible = false
            if (res.data.flag) {
              // 弹出提示信息
              this.$message({
                message: res.data.message,
                type: 'success'
              })
              this.$store.state.adminBlogId = null
              this.$router.push('/blogs')
            } else { // 执行失败
              this.$message.error(res.data.message)
            }
          })
        } else { // 校验不通过
          this.$message.error('校验失败，请检查输入格式')
          return false
        }
      })
    },
    // 获取所有的分类并回显
    async getTypeList () {
      const { data: res } = await this.$http.get('/api/home/getCategoryCount')
      this.typeList = res.data
    },
    // 获取所有的标签并回显
    async getTagList () {
      const { data: res } = await this.$http.get('/api/home/getLabelCount')
      this.tagList = res.data
    }
  },
  mounted () {
    $('.menu.toggle').click(function () {
      $('.m-item').toggleClass('m-mobile-hide')
    })

    $('.ui.dropdown').dropdown({
      on: 'hover'
    })
    $('.ui.form').form({
      // fields: {
      //   title: {
      //     identifier: 'title',
      //     rules: [{
      //       type: 'empty',
      //       prompt: '标题：请输入博客标题'
      //     }]
      //   }
      // }
    })
  }
}
</script>

<style scoped>
  @import "../../../assets/css/me.css";
.mavonEditor {
  width: 75%;
  height: 100%;
  margin: 0 auto;
}
  .blog-box {
    position: relative;
    border-radius: 3px;
    background: #ffffff;
    border-top: 3px solid #3c8dbc;
    padding: 10px;
    margin-bottom: 20px;
    width: 100%;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
  }
  .avatar-uploader .el-upload {
    border: 5px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 60px;
    height: 60px;
    display: block;
  }
</style>
