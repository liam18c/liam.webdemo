<template>
  <div style="padding: 10px">
    <div style="height: 50px; line-height: 50px; border-bottom:1px solid #ccc;display: flex">
      <div style="width:100%; padding-left: 10px; font-size:20px;font-weight: bold;color: cornflowerblue">我的订单</div>
      <div style="flex: 1"></div>
    </div>
  <el-table :data="tableData" border style="width: 100%">
    <el-table-column prop="photo" label="商品图片" >
      <template #default="scope">
        <el-image
            style="width: 100px; height: 100px"
            :src="scope.row.photo"
            :preview-src-list="[scope.row.photo]"
        >
        </el-image>
      </template>
    </el-table-column>
    <el-table-column prop="goodname" label="商品描述"  />
    <el-table-column prop="gaddress" label="发货地址" />
    <el-table-column prop="uaddress" label="收获地址" />
    <el-table-column prop="price" label="价格" />
    <el-table-column prop="count" label="数量" />
    <el-table-column prop="recordstatus" label="订单状态" />
  </el-table>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Record",
  data() {
    return {
      tableData: [],
       user:{},
      form:{},
    }
  },
   created() {
    let userStr = sessionStorage.getItem("user") || "{}"
     this.user = JSON.parse(userStr)
     this.load()
   },
     methods:
     {
       load() {
       request.get("/record/"+this.user.id).then(res => {
         this.tableData = res.data.records
       })
     },
     }
}
</script>

<style scoped>

</style>