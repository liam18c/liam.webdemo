<template>
  <div>
    <el-menu
        style="width: 200px;min-height: 100%"
        background-color="#57fdff"
        text-color="#000000"
        default-active="all"
        class="el-menu-vertical-demo">
      <el-menu-item index="all" @click="$router.push('/home')">
        <template #title>
          <span>全部商品</span>
        </template>

      </el-menu-item>
      <el-sub-menu index="1">
        <template #title>
          <span>商品分类</span>
        </template>
        <el-menu-item index="1-1" @click="$router.push('/basketball')">篮球</el-menu-item>
        <el-menu-item index="1-2" @click="$router.push('/football')">足球</el-menu-item>
        <el-menu-item index="1-3" @click="$router.push('/badminton')">羽毛球</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>

<script>


import request from "@/utils/request";

export default {
  name: "Aside",



}
</script>

<style scoped>

</style>