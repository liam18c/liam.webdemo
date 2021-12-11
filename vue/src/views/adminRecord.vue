<template>
  <div style="padding: 10px">
    <div style="height: 50px; line-height: 50px; border-bottom:1px solid #ccc;display: flex">
      <div style="width:100%; padding-left: 10px; font-size:20px;font-weight: bold;color: cornflowerblue">订单管理</div>
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
    <el-table-column  label="发货" width="160">
      <template #default=scope>
        <el-popconfirm title="确定已发货吗" @confirm="sendgood(scope.row.id)" v-if="scope.row.recordstatus==='等待发货'">
          <template #reference>
            <el-button size="small" type="primary" v-if="scope.row.recordstatus==='等待发货'">发货</el-button>
          </template>
        </el-popconfirm>

      </template>
    </el-table-column>
  </el-table>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Adminrecord",
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
       request.get("/record").then(res => {
         this.tableData = res.data.records
       })
     },
       sendgood(id)
       {
         request.put("/record/"+id).then(res=>{
           if (res.code === '0') {
             this.$message({
               type: "success",
               message: "更新成功"
             })
           } else {
             this.$message({
               type: "error",
               message: "更新失败"
             })
           }
           this.load() // 刷新表格的数据
         })
       },
     }
}
</script>

<style scoped>

</style>