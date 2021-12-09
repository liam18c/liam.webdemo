<template>
  <div style="height: 50px; line-height: 50px; border-bottom:1px solid #ccc;display: flex">
    <div style="width:200px; padding-left: 30px; font-weight: bold;color: cornflowerblue">商品列表</div>
    <div  style="flex: 1;"></div>
    <div style="width:100px">
      <el-dropdown>
    <span class="el-dropdown-link">
      我的
      <el-icon class="el-icon--right">
        <arrow-down />
      </el-icon>
    </span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="$router.push('/person')">个人信息</el-dropdown-item>
          <el-dropdown-item @click="$router.push('/cart')" v-if="user.username !== 'admin'">购物车</el-dropdown-item>
          <el-dropdown-item @click="$router.push('/record')" v-if="user.username !== 'admin'">我的订单</el-dropdown-item>
          <el-dropdown-item @click="$router.push('/adminrecord')" v-if="user.username === 'admin'">订单管理</el-dropdown-item>
          <el-dropdown-item @click="$router.push('/login')" >退出账号</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown></div>
  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      user:{},
    }
  },
  created() {
      let userStr = sessionStorage.getItem("user") || "{}"
      this.user = JSON.parse(userStr)
    },
}
</script>

<style scoped>

</style>