<template>
  <div class="blogs">
    <div class="content-header">
      <h1>用户管理<small>个人信息</small></h1>
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb">
        <el-breadcrumb-item :to="{ path: '/' }">后台管理</el-breadcrumb-item>
        <el-breadcrumb-item>用户管理</el-breadcrumb-item>
        <el-breadcrumb-item>个人信息</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <!--中间内容-->
    <div class="app-container">
      <div class="box">
        <el-form :model="formData" :rules="userRules" ref="userForm" label-width="100px">
          <el-row>
            <el-col :span="8">
              <el-form-item prop="username" label="用户名">
                <el-input v-model="formData.username"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="password" label="密码">
                <el-input v-model="formData.password"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
              <el-col :span="8">
                <el-form-item prop="avatar" label="头像地址">
                  <div>
                    <el-input v-model="formData.avatar"></el-input>
                  </div>
                </el-form-item>
              </el-col>
          </el-row>
          <el-upload
            style="margin-left: 10%!important;"
            class="avatar-uploader"
            action="serverApi/file/userAvatar/"
            accept="image/png,.jpg"
            multiple
            :limit="1"
            :on-exceed="masterFileMax"
            :show-file-list="false"
            :http-request="uploadPic"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <el-form-item>

            <el-button type="primary" @click="submitForm()">立即修改</el-button>
<!--            <el-button @click="resetForm('formData')">重置</el-button>-->
          </el-form-item>
        </el-form>
      </div>
    </div>
    <br>
    <br>
  </div>
</template>

<script>
export default {
  data () {
    return {
      flag: false,
      codeMsg: "发送",
      time: 60, // 邮箱验证码倒数时间
      imageUrl: '',
      formData: {
        id: '',
        username: '',
        password: '',
        oldpassword: '',
        avatar: '',
        priviledged: null
      },
      userRules: {
        // 验证用户名是否合法
        username: [
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ],
        // 验证密码是否合法
        password: [
          // { required: true, message: '请输入登录密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        avatar: [
          { required: true, message: '请输入头像地址', trigger: 'blur' },
          { min: 1, max: 150, message: '长度在 1 到 150 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.formData.id = this.$store.state.id
    this.formData.oldpassword = this.$store.state.password
    this.formData.avatar = this.$store.state.avatar
    // this.formData.nickname = this.$store.state.nickname
    // this.formData.email = this.$store.state.email
    const loginType = this.$store.state.loginType
    // this.formData.loginType = this.$store.state.loginType
    if (loginType === 1) { this.formData.username = this.$store.state.username }
  },
  methods: {
    masterFileMax (files, fileList) {
      this.$message.warning('请最多上传一张图片')
    },
    async uploadPic (param) {
      var fileObj = param.file
      var form = new FormData()
      // 文件对象
      form.append('file', fileObj)
      const { data: res } = await this.$http.post('/serverApi/avatar/set', form)
      if (res.flag) {
        // 弹出提示信息
        this.$message.success('上传头像成功')
        this.formData.avatar = res.data.url
      } else { // 执行失败
        this.$message.error(res.message)
      }
    },
    handleAvatarSuccess (res, file) {
      this.imageUrl = URL.createObjectURL(file.raw)
    },
    beforeAvatarUpload (file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt5M = file.size / 1024 / 1024 < 5

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt5M) {
        this.$message.error('上传头像图片大小不能超过5MB!')
      }
      return isJPG && isLt5M
    },
    submitForm () {
      // 进行表单校验
      this.$refs.userForm.validate((valid) => {
        if (valid) {
          // 表单校验通过，发ajax请求，把数据录入至后台处理
          const param = this.$encrypTion(JSON.stringify(this.formData))
          this.$http.put('/api/users/updateUser', param).then((res) => {
            // 关闭新增窗口
            if (res.data.flag) {
              // 弹出提示信息
              this.$message({
                message: '修改成功！',
                type: 'success'
              })
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
    resetForm (formName) {
      this.$refs[formName].resetFields()
    }
  }
}
</script>

<style lang="less" scoped>
  @import "../../../assets/css/me.css";
  @import "../../../assets/css/style.css";
</style>
