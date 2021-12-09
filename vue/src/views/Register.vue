<template>
  <div style="width: 100%;height: 100vh;background-color: #cccccc;overflow: hidden">
  <div style="width: 400px; margin: 50px auto">
    <div style="font-size: 30px; text-align: center; padding: 30px 0; color: darkslategrey">欢迎注册</div>
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
      <el-form-item prop="confirm">
        <el-input  v-model="form.confirm" show-password placeholder="请确认密码">
          <template #prefix>
            <el-icon class="el-input__icon"><lock /></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="age">
        <el-input  v-model="form.age"  placeholder="请输入你的年龄">
        <template #prefix>
          <el-icon class="el-input__icon"><calendar /></el-icon>
        </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="email">
        <el-input  v-model="form.email"  placeholder="请输入你的邮箱地址">
          <template #prefix>
            <el-icon class="el-input__icon"><stamp /></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="address">
        <el-input  v-model="form.address"  placeholder="请输入你的住址">
          <template #prefix>
            <el-icon class="el-input__icon"><location /></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="sex" style="margin-left: 130px">
        <div>
          <el-radio v-model="form.sex" label="男">男</el-radio>
          <el-radio v-model="form.sex" label="女">女</el-radio>
        </div>
      </el-form-item>
      <el-form-item>
        <el-button style="width: 100%" type="primary" @click="register">注 册</el-button>
      </el-form-item>
      <el-form-item>
        <el-button style="width: 100%" type="primary" @click="$router.push('/login')">返回登录</el-button>
      </el-form-item>
    </el-form>
    </div>
  </div>
</template>

<script>

import {Lock,User,Calendar,Location,Stamp} from '@element-plus/icons';
import request from "@/utils/request";

export default {
  name: "Register",
  components: {
    User,
    Lock,
    Location,
    Calendar,
    Stamp,
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
        confirm: [
          {required: true, message: '请确认密码', trigger: 'blur'},
        ],
        email: [
          {required: true, message: '请输入邮箱地址', trigger: 'blur'},
        ],
      },
    }
  },
  methods:
      {
        register() {

          this.$refs["form"].validate((valid) => {
            if (valid) {
              if(this.form.password!==this.form.confirm){
                this.$message({
                  type: "error",
                  message: "两次密码不一致"
                })
                return;
              }
              request.post("/user/register", this.form).then(res => {
                if (res.code === '0') {
                  this.$message({
                    type: "success",
                    message: "注册成功"
                  })
                  this.$router.push("/login")
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