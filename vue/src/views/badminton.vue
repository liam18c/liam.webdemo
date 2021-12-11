<template>
  <div style="padding: 10px">
    <div style="margin: 5px 0">
      <el-button type="primary" @click= "add" v-if="user.username === 'admin'">新增商品</el-button>
    </div>
    <div style="margin: 15px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable/>
      <el-button type="primary" style="margin-left: 10px " @click="load">查询</el-button>
    </div>
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="photo" label="图片" >
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
      <el-table-column prop="price" label="价格" />
      <el-table-column prop="address" label="发货地址" />
      <el-table-column  label="操作" width="160">
        <template #default=scope>
          <el-button size="mini"  @click="handleEdit(scope.row)" v-if="user.username === 'admin'">编辑</el-button>
          <el-popconfirm title="确定下架该商品吗" @confirm="handleDelete(scope.row.id)" v-if="user.username === 'admin'">
            <template #reference>
              <el-button size="mini" type="danger" v-if="user.username === 'admin'">下架</el-button>
            </template>
          </el-popconfirm>
          <el-button size="mini"  type="primary" @click="detail(scope.row)" v-if="user.username !== 'admin'">详情</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog v-model="dialogVisible1" title="提示" width="30%" >
      <el-form ref="form" :model="form" label-width="120px" :rules="rules">
        <el-form-item prop="goodname" label="商品描述" >
          <el-input v-model="form.goodname"></el-input>
        </el-form-item>
        <el-form-item prop="price" label="商品价格" >
          <el-input v-model="form.price"></el-input>
        </el-form-item>
        <el-form-item label="商品图片">
          <el-upload ref="upload" action="http://8.134.128.229:9090/files/upload" :on-success="Fileuploadsuccess">
            <el-button  type="primary">上传图片</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item prop="address" label="商品发货地">
          <el-input type="textarea" v-model="form.address"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">确定</el-button>
      </span>
      </template>
    </el-dialog>
    <el-dialog v-model="dialogVisible2" title="商品详情" width="30%" >
      <el-form ref="form" :model="form" label-width="120px" >
        <el-form-item  label="商品描述" >
          <el-input v-model="form.goodname" disabled></el-input>
        </el-form-item>
        <el-form-item  label="商品价格" >
          <el-input v-model="form.price" disabled></el-input>
        </el-form-item>
        <el-form-item  label="商品发货地">
          <el-input type="textarea" v-model="form.address" disabled></el-input>
        </el-form-item>
        <el-form-item  label="数量">
          <el-input-number size= mini v-model="num" :min="1" :max="99" @change="handleChange" />
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible2 = false">返回</el-button>
        <el-button type="primary" @click="addshopcart">加入购物车</el-button>
      </span>
      </template>
    </el-dialog>
  </div>

</template>

<script>
// @ is an alias to /src


import request from "@/utils/request";

export default {
  name: 'Home',
  components: {},
  data() {
    return {
      user: {},
      form: {},
      form2:{},
      num: '1',
      rules: {
        goodname: [
          {required: true, message: '请输入商品描述', trigger: 'blur'},
        ],
        price: [
          {required: true, message: '请输入商品价格', trigger: 'blur'},
        ],
        address: [
          {required: true, message: '请输入发货地', trigger: 'blur'},
        ],
      },
      dialogVisible1: false,
      dialogVisible2: false,
      search: '羽毛球',
      tableData: [],
    }
  },
  created() {
    this.load()
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
  },
  methods:
      {
        Fileuploadsuccess(res)
        {
          this.form.photo=res.data
        },
        load() {
          request.get("/good", {
            params: {
              search: this.search
            }
          }).then(res => {
            this.tableData = res.data.records
          })
        },
        add() {
          this.dialogVisible1 = true
          this.form = {}
          this.$refs['upload'].clearFiles()
        },
        save() {
          this.$refs["form"].validate((valid) => {
            if (valid) {
              if (this.form.id) {  // 更新
                request.put("/good", this.form).then(res => {
                  if (res.code === '0') {
                    this.$message({
                      type: "success",
                      message: "更新成功"
                    })
                  } else {
                    this.$message({
                      type: "error",
                      message: res.msg
                    })
                  }
                  this.load() // 刷新表格的数据
                  this.dialogVisible1 = false  // 关闭弹窗
                })
              } else {  // 新增
                request.post("/good", this.form).then(res => {
                  console.log(res)
                  if (res.code === '0') {
                    this.$message({
                      type: "success",
                      message: "新增成功"
                    })
                  } else {
                    this.$message({
                      type: "error",
                      message: res.msg
                    })
                  }
                  this.load() // 刷新表格的数据
                  this.dialogVisible1 = false  // 关闭弹窗
                })
              }
            }
          })
        },
        detail(row){
          this.form = JSON.parse(JSON.stringify(row))
          this.num = 1
          this.dialogVisible2 = true
        },
        addshopcart(row){
          this.form2.count=this.num;
          this.form2.uid=this.user.id;
          this.form2.gid=this.form.id;
          this.form2.price=this.form.price;
          this.form2.goodname=this.form.goodname;
          this.form2.address=this.form.address;
          this.form2.photo=this.form.photo;
          this.form2.status=0;
          console.log(this.form2);
          request.post("cart/add",this.form2).then(res=> {
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "加购成功"
              })
            } else {
              this.$message({
                type: "error",
                message: "加购失败"
              })
            }
            this.dialogVisible2=false
            // 刷新表格的数据
          })

        },
        handleEdit(row) {
          this.form = JSON.parse(JSON.stringify(row))
          this.dialogVisible1 = true
          this.$refs['upload'].clearFiles()
        },
        handleDelete(id) {
          request.delete("/good/" + id).then(res => {
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
        handleChange()
        {

        }
      }
}
</script>
