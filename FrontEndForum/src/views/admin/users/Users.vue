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
        <el-form
        :model="formData"
        :rules="userRules" ref="userForm" label-width="100px"
        enctype="Multipart/form-data">
          <el-row>
            <el-col :span="8">
              <el-form-item prop="username" label="用户名">
                <el-input v-model="formData.username"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item prop="password" label="新密码">
                <el-input v-model="formData.password" show-password></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item prop="oldpassword" label="旧密码">
                <el-input v-model="formData.oldpassword" show-password></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- <el-row>
              <el-col :span="8">
                <el-form-item prop="avatar" label="头像地址">
                  <div>
                    <el-input v-model="formData.avatar"></el-input>
                  </div>
                </el-form-item>
              </el-col>
          </el-row> -->
          <!-- <el-row>
            <el-col :span="8">
              <el-form-item prop="username" label="头像">
                <el-upload
                  class="upload-demo"
                  ref="upload"
                  action="/"
                  accept="image/png,.jpg"
                  multiple
                  :limit="1"
                  :on-change="uploadFile"
                  :auto-upload="false"
                  :show-file-list="true"
                  :on-exceed="masterFileMax">
                  <el-button slot="trigger" size="small" type="default">上传图片</el-button>
                  <div slot="tip" class="el-upload__tip">请上传JPG, JEPG, PNG, 大小不超过1M</div>
                </el-upload>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-button @click="submitFile" type="default">确认</el-button>
            </el-col>
          </el-row> -->
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
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        oldpassword: [
          { required: true, message: '请输入登录密码', trigger: 'blur' },
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
    const loginType = this.$store.state.loginType
    if (loginType === 1) {
      this.formData.username = this.$store.state.username
    }
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
      const { data: res } = await this.$http.post('/api/avatar/set', form)
      if (res.flag) {
        // 弹出提示信息
        this.$message.success('上传头像成功')
        this.formData.avatar = res.data.url
      } else { // 执行失败
        this.$message.error(res.message)
      }
    },
    async uploadFile (file) {
      console.log(file.raw)
      this.imageUrl = file.raw
    },
    submitFile  () {
      // eslint-disable-next-line camelcase, prefer-const
      let form_data = new FormData()
      form_data.append("content", this.imageUrl)
      this.$http({
        method: 'POST',
        data: form_data,
        url: "http://101.37.254.247:9001/users/avatar/set",
        headers: { 'Content-Type': 'multipart/form-data' }
      }).then((res) => {
        console.log(res)
        if (res.data.flag) {
          this.$message.success('上传头像成功')
          this.formData.avatar = res.data.data
          this.$store.state.avatar = "http://101.37.254.247:9001" + res.data.data
          // this.$store.state.avatar = "http://img.daimg.com/uploads/allimg/210916/3-210916110348.jpg"
          console.log(this.$store.state.avatar)
        } else { // 执行失败
          this.$message.error(res.message)
        }
      })
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
          // const param = this.$encrypTion(JSON.stringify(this.formData))
          const param = {
            id: this.formData.id,
            username: this.formData.username,
            password: this.formData.password,
            old_password: this.formData.oldpassword,
            avatar: this.formData.avatar,
            priviledged: this.formData.priviledged
          }
          this.$http.post('/api/users/updateUser', param).then((res) => {
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
