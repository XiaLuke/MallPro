<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" :close-on-click-modal="false">
    <el-form :model="categoryForm">
      <el-form-item label="活动名称">
        <el-input v-model="categoryForm.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="图标">
        <el-input v-model="categoryForm.icon" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="计量单位">
        <el-input v-model="categoryForm.productUnit" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="formSubmit">确 定</el-button>
    </div>
  </el-dialog>

</template>

<script>
export default {
  data () {
    return {
      title: '',
      dialogFormVisible: false,
      categoryForm: {
        name: '',
        parentCid: 0,
        catLevel: 0,
        showStatus: 1,
        sort: 0,
        catId: null,
        icon: '',
        productUnit: ''
      },
      parentId: ''
    }
  },
  methods: {
    // 由父页面调用传递`节点下数据`与`当前节点`,添加时`node`为undefined
    show (data, node) {
      const that = this
      console.log('父组件传递过来的数据', data)
      console.log('父组件传递过来的节点数据', node)
      // 每次打开清空表格数据
      this.formClean()
      this.dialogFormVisible = true
      // 修改时
      if (node !== undefined) {
        this.title = '修改'
        // 修改时需要发送请求，获取最新数据
        this.$http({
          url: this.$http.adornUrl(`/product/category/info/${data.catId}`),
          method: 'GET'
          // 解构，只获取结果中的data值
        }).then(({data}) => {
          that.categoryForm = data.category
        })
      } else {
        this.title = '添加'
        this.categoryForm.parentCid = data.catId
        // data.catLevel*1 + 1：避免catLevel为字符串时为拼接，使用*可将字符串转成数值
        this.categoryForm.catLevel = data.catLevel * 1 + 1
        this.categoryForm.catId = null
      }
    },
    // 添加方法
    formSubmit () {
      const that = this
      const formData = this.categoryForm
      console.log('提交的数据为：', formData)
      let url = ''
      let data = []
      let method = ''
      // 根据商品id判断是添加还是修改
      if (formData.catId !== null) {
        url = this.$http.adornUrl('/product/category/update')
        let {catId, name, icon, productUnit} = formData
        data = {catId, name, icon, productUnit}
        method = 'update'
      } else {
        url = this.$http.adornUrl('/product/category/save')
        data = this.$http.adornData(formData, false)
        method = 'save'
      }

      this.$http({
        url: url,
        method: 'POST',
        data: data
        // 解构，只获取结果中的data值
      }).then(({data}) => {
        if (method === 'update') {
          this.$message.success(`成功修改${this.categoryForm.name}菜单信息`)
        } else if (method === 'save') {
          this.$message.success(`成功添加${this.categoryForm.name}菜单`)
        }
        this.dialogFormVisible = false
        // 调用父组件方法并传递参数
        this.$emit('reload', that.categoryForm.parentCid)
      })
    },
    formClean () {
      this.categoryForm = {
        name: '',
        parentCid: 0,
        catLevel: 0,
        showStatus: 1,
        sort: 0
      }
    }
  }
}
</script>
