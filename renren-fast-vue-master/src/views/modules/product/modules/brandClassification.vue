<template>
  <el-dialog title="关联分类" :visible.sync="dialogVisible" width="30%">
    <el-popover placement="right-end" v-model="popCatelogSelectVisible">
      <category-cascader :catelogPath.sync="catelogPath"/>
      <div style="text-align: right; margin: 0">
        <el-button size="mini" type="text" @click="popCatelogSelectVisible = false">取消</el-button>
        <el-button type="primary" size="mini" @click="addCatelogSelect">确定</el-button>
      </div>
      <el-button slot="reference">新增关联</el-button>
    </el-popover>
    <el-table :data="cateRelationTableData" style="width: 100%">
      <el-table-column prop="brandName" label="品牌名"></el-table-column>
      <el-table-column prop="catelogName" label="分类名"></el-table-column>
      <el-table-column fixed="right" header-align="center" align="center" label="操作">
        <template slot-scope="scope">
          <el-button
            type="text"
            size="small"
            @click="deleteCateRelationHandle(scope.row.id,scope.row.brandId)"
          >移除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
  </el-dialog>
</template>

<script>
import categoryCascader from '../../common/category-cascader'
export default {
  name: 'brandClassification',
  data () {
    return {
      dialogVisible: false,
      popCatelogSelectVisible: false,
      catelogPath: [],
      cateRelationTableData: [],
      brandId: ''
    }
  },
  components: {
    categoryCascader
  },
  methods: {
    initDialog (brandId) {
      this.dialogVisible = true
      this.brandId = brandId
      this.getCateRelation()
    },
    getCateRelation () {
      this.$http({
        url: this.$http.adornUrl('/product/categorybrandrelation/brandClassificationAssociation'),
        method: 'get',
        params: this.$http.adornParams({
          brandId: this.brandId
        })
      }).then(({data}) => {
        this.cateRelationTableData = data.data
      })
    },
    addCatelogSelect () {
      this.popCatelogSelectVisible = false
      this.$http({
        url: this.$http.adornUrl('/product/categorybrandrelation/save'),
        method: 'post',
        data: this.$http.adornData({
          brandId: this.brandId,
          catelogId: this.catelogPath[this.catelogPath.length - 1]
        }, false)
      }).then(({data}) => {
        this.getCateRelation()
      })
    },
    deleteCateRelationHandle (id, brandId) {
      this.$http({
        url: this.$http.adornUrl('/product/categorybrandrelation/delete'),
        method: 'post',
        data: this.$http.adornData([id], false)
      }).then(({data}) => {
        this.getCateRelation()
      })
    }
  }
}
</script>

<style scoped>

</style>
