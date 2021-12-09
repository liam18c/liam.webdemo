<template>
  <div style="padding: 10px">
  <div style="height: 50px; line-height: 50px; border-bottom:1px solid #ccc;display: flex">
    <div style="width:200px; padding-left: 10px; font-size:20px;font-weight: bold;color: cornflowerblue">个人信息</div>
    <div style="flex: 1"></div>
  </div>
  <el-card style="width: 50%;padding:40px;margin: 30px 10px" >
    <el-form ref="form" :model="form" label-width="80px" :rules="rules">
      <el-form-item label="用户名" >
        <el-input v-model="form.username" disabled></el-input>
      </el-form-item >
      <el-form-item prop="email" label="邮箱地址">
        <el-input v-model="form.email" ></el-input>
      </el-form-item>
      <el-form-item label="年龄">
        <el-input v-model="form.age"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-input v-model="form.sex"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="form.address"></el-input>
      </el-form-item>
      <el-form-item prop="password" label="密码" >
        <el-input v-model="form.password" show-password ></el-input>
      </el-form-item>
    </el-form>
    <div style="text-align: center">
      <el-button type="primary" @click="update">保存</el-button>
    </div>
  </el-card>
  </div>

</template>

<script>
import request from "@/utils/request";

export default {
  name: "Person",
  data() {
    return {
      form: {},
      rules: {
        email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
      },
    }
   },
  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.form = JSON.parse(str)
  },
  methods:
      {
        update(){{
          this.$refs["form"].validate((valid) => {
            if (valid) {
              request.put("/user/update", this.form).then(res => {
                if (res.code === '0') {
                  this.$message({
                    type: "success",
                    message: "保存成功"
                  })
                  sessionStorage.setItem("user", JSON.stringify(res.data))
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

  }
</script>

<style scoped>

</style>