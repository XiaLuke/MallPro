<template>
  <el-dialog width="40%" title="选择属性" :visible.sync="dialogVisible" append-to-body>
    <div>
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
        <el-form-item>
          <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="getDataList()">查询</el-button>
        </el-form-item>
      </el-form>
      <el-table
        :data="dataList"
        border
        v-loading="dataListLoading"
        @selection-change="innerSelectionChangeHandle"
        style="width: 100%;"
      >
        <el-table-column type="selection" header-align="center" align="center"/>
        <el-table-column prop="attrId" header-align="center" align="center" label="属性id"/>
        <el-table-column prop="attrName" header-align="center" align="center" label="属性名"/>
        <el-table-column prop="icon" header-align="center" align="center" label="属性图标"/>
        <el-table-column prop="valueSelect" header-align="center" align="center" label="可选值列表"/>
      </el-table>
      <el-pagination
        @size-change="sizeChangeHandle"
        @current-change="currentChangeHandle"
        :current-page="pageIndex"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="pageSize"
        :total="totalPage"
        layout="total, sizes, prev, pager, next, jumper"
      ></el-pagination>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitAddRealtion">确认新增</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: 'add-attr-group-relation',
  data () {
    return {
      dialogVisible: false,
      dataForm: {
        key: ''
      },
      dataList: [],
      dataListLoading: false,
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      attrGroupId: 0
    }
  },
  methods: {
    init (attrGroupId) {
      this.attrGroupId = attrGroupId
      this.getDataList()
      this.dialogVisible = true
    },
    innerSelectionChangeHandle (val) {
      this.innerdataListSelections = val
    },
    getDataList () {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl(
          '/product/attrgroup/' + this.attrGroupId + '/noattr/relation'
        ),
        method: 'get',
        params: this.$http.adornParams({
          page: this.pageIndex,
          limit: this.pageSize,
          key: this.dataForm.key
        })
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList = data.page.list
          this.totalPage = data.page.totalCount
        } else {
          this.dataList = []
          this.totalPage = 0
        }
        this.dataListLoading = false
      })
    },
    submitAddRealtion () {
      this.dialogVisible = false
      // 准备数据
      console.log('准备新增的数据', this.innerdataListSelections)
      if (this.innerdataListSelections.length > 0) {
        let postData = []
        this.innerdataListSelections.forEach(item => {
          postData.push({attrId: item.attrId, attrGroupId: this.attrGroupId})
        })
        this.$http({
          url: this.$http.adornUrl('/product/attrgroup/attr/relation'),
          method: 'post',
          data: this.$http.adornData(postData, false)
        }).then(({data}) => {
          if (data.code == 0) {
            this.$message({type: 'success', message: '新增关联成功'})
          }
          this.$emit('refreshData', this.attrGroupId)
        })
      } else {
      }
    },
    // 每页数
    sizeChangeHandle (val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle (val) {
      this.pageIndex = val
      this.getDataList()
    }
  }
}
</script>

<style scoped>

</style>
