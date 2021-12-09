<template>
  <div style="padding: 10px">
    <div style="height: 50px; line-height: 50px; border-bottom:1px solid #ccc;display: flex">
      <div style="width:100%; padding-left: 10px; font-size:20px;font-weight: bold;color: cornflowerblue">我的购物车</div>
      <div >已选金额
        <el-input v-model="this.totalnum" style="width: 30%; font-size:15px"  disabled/>
        <el-button type="primary" style="margin-left: 10px " @click="payready">支付</el-button>
      </div>
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
    <el-table-column prop="address" label="发货地址" />
    <el-table-column prop="price" label="价格" />
    <el-table-column prop="count" label="数量" />
    <el-table-column  label="操作" width="160">
      <template #default=scope>
        <el-button size="mini"  @click="handleEdit(scope.row)" >编辑</el-button>
        <el-popconfirm title="确定删除该商品吗" @confirm="handleDelete(scope.row.id)">
          <template #reference>
            <el-button size="mini" type="danger" >删除</el-button>
          </template>
        </el-popconfirm>
        <el-switch v-model="scope.row.status"  active-text="结算" active-value="1"
                   inactive-text="待支付" inactive-value="0" @click="updatetotal(scope.row)"/>
      </template>
    </el-table-column>
  </el-table>
  <el-dialog v-model="dialogVisible1" title="数量修改" width="20%" >
    <el-input-number size="small" v-model="form.count" :min="1" :max="99"  >数量</el-input-number>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible1 = false">取消</el-button>
        <el-button type="primary" @click="save">确定</el-button>
      </span>
    </template>
  </el-dialog>
    <el-dialog v-model="dialogVisible2" title="支付确认" style="color: cornflowerblue" width="20%" >
      本次金额<el-input  size="small" style="width: 30%;" v-model="this.totalnum" disabled ></el-input>是否支付
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible2 = false">取消</el-button>
        <el-button type="primary" @click="pay">确定</el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Cart",
  data() {
    return {
      tableData: [],
       user:{},
      form:{},
      paylist:[],
      recordlist:[],
      totalnum:0,
      dialogVisible1:false,
      dialogVisible2:false,
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
       request.get("/cart/"+this.user.id).then(res => {
         this.tableData = res.data.records
         this.totalnum=0
       })
     },
       handleEdit(row){
         this.form = JSON.parse(JSON.stringify(row))
         this.dialogVisible1 = true
       } ,
       save(){
         request.put("/cart/update",this.form).then(res=>{
           if (res.code === '0') {
             this.$message({
               type: "success",
               message: "修改成功"
             })
           } else {
             this.$message({
               type: "error",
               message: "修改失败"
             })
           }
           this.load() // 刷新表格的数据
           this.dialogVisible1 = false  // 关闭弹窗
         })
       },
       handleDelete(id){
         request.delete("/cart/" + id).then(res => {
           if (res.code === '0') {
             this.$message({
               type: "success",
               message: "删除成功"
             })
           } else {
             this.$message({
               type: "error",
               message: res.msg
             })
           }
           this.load() // 刷新表格的数据
         })
       },
       updatetotal(row){
         this.form = JSON.parse(JSON.stringify(row))
         if(this.form.status==='0')
         {
           this.totalnum -=this.form.count*this.form.price
           delete this.recordlist[this.paylist.indexOf(this.form.id)]
           delete this.paylist[this.paylist.indexOf(this.form.id)]
         }
         else {
           this.totalnum +=this.form.count*this.form.price
           this.paylist.push(this.form.id)
           this.form.uaddress=this.user.address
           this.form.gaddress=this.form.address
           this.recordlist.push(this.form)
         }
         console.log(this.paylist)
         console.log(this.recordlist)
       },
       payready(){
         if(this.totalnum===0)
         {
           this.$message({
             type: "error",
             message: "请选择商品进行支付"
           })
         }
         else {
           this.dialogVisible2 = true
         }
       },
       pay(){
         request.post("/cart/paylist",this.paylist).then(res=> {
           if (res.code === '0') {
             request.post("/record/add",this.recordlist).then(res=>{
               if (res.code === '0') {
                 this.$message({
                   type: "success",
                   message: "支付成功"
                 })
               } else {
                 this.$message({
                   type: "error",
                   message: res.msg
                 })
               }
             })
           } else {
             this.$message({
               type: "error",
               message: res.msg
             })
           }
           this.dialogVisible2 = false
           this.load() // 刷新表格的数据
         })
       },
     }
}
</script>

<style scoped>

</style>