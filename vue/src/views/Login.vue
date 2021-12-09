<template>
  <div style="width: 100%;height: 100vh;background-color: cadetblue;overflow: hidden">
  <div style="width: 400px; margin: 150px auto">
    <div style="font-size: 30px; text-align: center; padding: 30px 0; color: darkslategrey">欢迎登录</div>
    <el-form ref="form" :model="form" size="normal" :rules="rules">
      <el-form-item prop="username">
        <el-input  v-model="form.username" placeholder="请输入账号" >
          <template #prefix>
            <el-icon class="el-input__icon"><user /></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input  v-model="form.password" show-password placeholder="请输入密码">
          <template #prefix>
            <el-icon class="el-input__icon"><lock /></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button style="width: 100%" type="primary" @click="login">登 录</el-button>
      </el-form-item>
      <el-form-item>
        <el-button style="width: 100%" type="primary" @click="$router.push('/register')">注册账号</el-button>
      </el-form-item>
    </el-form>
    </div>
  </div>
</template>

<script>

import {Lock,User} from '@element-plus/icons';
import request from "@/utils/request";

export default {
  name: "Login",
  components: {
    User,
    Lock
  },
  data() {
    return {
      form: {},
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
      },
    }
  },
  methods:
      {
        login() {
          this.$refs["form"].validate((valid) => {
            if (valid) {
              request.post("/user/login", this.form).then(res => {
                if (res.code === '0') {
                  this.$message({
                    type: "success",
                    message: "登录成功"
                  })
                  sessionStorage.setItem("user", JSON.stringify(res.data))
                  this.$router.push("/home")
                } else {
                  this.$message({
                    type: "error",
                    message: res.msg
                  })
                }
              })
            }
          })
        }
      }
}
</script>

<style scoped>

</style>