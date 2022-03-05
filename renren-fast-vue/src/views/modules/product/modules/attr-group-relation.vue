<template>
  <div>
    <el-dialog :close-on-click-modal="false" :visible.sync="visible" @closed="dialogClose">
      <el-row>
        <el-col :span="24">
          <el-button type="primary" @click="addRelation">新建关联</el-button>
          <el-button
            type="danger"
            @click="batchDeleteRelation"
            :disabled="dataListSelections.length <= 0"
          >批量删除</el-button>
          <!--  -->
          <el-table
            :data="relationAttrs"
            style="width: 100%"
            @selection-change="selectionChangeHandle"
            border
          >
            <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
            <el-table-column prop="attrId" label="#"></el-table-column>
            <el-table-column prop="attrName" label="属性名"></el-table-column>
            <el-table-column prop="valueSelect" label="可选值">
              <template slot-scope="scope">
                <el-tooltip placement="top">
                  <div slot="content">
                    <span v-for="(i,index) in scope.row.valueSelect.split(';')" :key="index">
                      {{i}}
                      <br />
                    </span>
                  </div>
                  <el-tag>{{scope.row.valueSelect.split(";")[0]+" ..."}}</el-tag>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column fixed="right" header-align="center" align="center" label="操作">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="relationRemove(scope.row.attrId)">移除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </el-dialog>
    <add-attr-group-relation ref="addAttrGroupRelation" @refreshData="back2Parent"/>
  </div>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import《组件名称》from'《组件路径》';

import AddAttrGroupRelation from "./add-attr-group-relation";
export default {
  //import引入的组件需要注入到对象中才能使用
  components: {AddAttrGroupRelation},
  props: {},
  data() {
    //这里存放数据
    return {
      attrGroupId: 0,
      visible: false,
      relationAttrs: [],
      dataListSelections: [],
    };
  },
  //计算属性类似于data概念
  computed: {},
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {
    selectionChangeHandle(val) {
      this.dataListSelections = val;
    },
    addRelation() {
      this.$refs.addAttrGroupRelation.init(this.attrGroupId)
    },
    batchDeleteRelation(val) {
      let postData = [];
      this.dataListSelections.forEach(item => {
        postData.push({ attrId: item.attrId, attrGroupId: this.attrGroupId });
      });
      this.$http({
        url: this.$http.adornUrl("/product/attrgroup/attr/relation/delete"),
        method: "post",
        data: this.$http.adornData(postData, false)
      }).then(({ data }) => {
        if (data.code == 0) {
          this.$message({ type: "success", message: "删除成功" });
          this.init(this.attrGroupId);
        } else {
          this.$message({ type: "error", message: data.msg });
        }
      });
    },
    //移除关联
    relationRemove(attrId) {
      let data = [];
      data.push({ attrId, attrGroupId: this.attrGroupId });
      this.$http({
        url: this.$http.adornUrl("/product/attrgroup/attr/relation/delete"),
        method: "post",
        data: this.$http.adornData(data, false)
      }).then(({ data }) => {
        if (data.code == 0) {
          this.$message({ type: "success", message: "删除成功" });
          this.init(this.attrGroupId);
        } else {
          this.$message({ type: "error", message: data.msg });
        }
      });
    },
    init(id) {
      this.attrGroupId = id || 0;
      this.visible = true;
      this.$http({
        url: this.$http.adornUrl(
          "/product/attrgroup/" + this.attrGroupId + "/attr/relation"
        ),
        method: "get",
        params: this.$http.adornParams({})
      }).then(({ data }) => {
        this.relationAttrs = data.data;
      });
    },
    back2Parent(parma){
      this.init(parma)
      this.$emit("refreshData")
    },
    dialogClose() {},
  }
};
</script>
<style scoped>
</style>
