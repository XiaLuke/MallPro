<template>
  <div>
    <el-tree
      :data="data"
      :props="defaultProps"
      @node-click="handleNodeClick"
      :expand-on-click-node="false"
      :default-expanded-keys="expandKye"
      show-checkbox
      draggable
      :allow-drop="allowDrop"
      node-key="catId">
      <!--  slot-scope="{ 当前节点, 当前节点数据 }"  -->
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <span>
          <!-- 是父节点时可以添加 -->
          <el-button
            v-if="node.level <= 2"
            type="text"
            size="mini"
            @click="() => append_edit(data)">
            添加
          </el-button>
          <!-- 没有子节点时可以删除 -->
          <el-button
            v-if="node.childNodes.length==0"
            type="text"
            size="mini"
            @click="() => remove(node, data)">
            删除
          </el-button>
          <el-button
            type="text"
            size="mini"
            @click="() => append_edit(data, node)">
            修改
          </el-button>
        </span>
      </span>

    </el-tree>
    <category_add_form ref="addForm" @reload="getAndReload"/>
  </div>
</template>

<script>
import Category_add_form from "./modules/category_add_form";

export default {
  components: {Category_add_form},
  data() {
    return {
      data: [],
      // 树状结构展示数据的字段
      defaultProps: {
        // children：指定子树为节点对象的某个属性值
        children: 'childrenList',
        // label：指定节点标签为节点对象的某个属性值
        label: 'name'
      },
      // 默认展开树节点
      expandKye: [],
      maxLevel: 0
    };
  },
  // 页面被激活就发送请求
  activated() {
  },
  // 组件创建完成就触发
  created() {
    this.getDataList()
  },
  methods: {
    getDataList() {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/product/category/tree'),
        method: 'get'
        // 使用{}是获取返回结果中的对象，可以进行解构
      }).then(({data}) => {
        console.log(data.tree)
        this.data = data.tree
        this.dataListLoading = false
      })
    },
    handleNodeClick(data) {
      console.log(data);
    },
    // 添加
    append_edit(data, node) {
      this.$refs.addForm.show(data, node);
    },
    // 删除
    remove(node, data) {
      const that = this
      let ids = [data.catId]
      this.$confirm(`是否删除${data.name}菜单`, "提示", {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/product/category/delete'),
          method: 'post',
          data: this.$http.adornData(ids, false)
        }).then(({data}) => {
          this.$message.success(`成功删除菜单`)
          // 刷新
          that.getDataList()
          // 设置默认展开菜单
          this.expandKye = [node.parent.data.catId]
        })
      }).catch(() => {
        this.$message.info(`取消删除${data.name}菜单`)
      })
    },

    // tree是否可拖拽，draggable：开启可拖拽方法，返回结果为boolean
    // draggingNode：被拖动节点， dropNode：拖拽到那个节点， type
    allowDrop(draggingNode, dropNode, type) {
      console.log(draggingNode, dropNode, type)
      // 拖拽结果不能大于三级层级
      // 被拖动节点的层级数
      this.countNodeLevel(draggingNode.data)
      // 当前节点与父节点深度相加<=3即可
      let currentLevel = this.maxLevel - draggingNode.data.catLevel + 1
      console.log(currentLevel)
      if (type == 'inner') {
        return (currentLevel + dropNode.level) <= 3
      } else {
        return (currentLevel + dropNode.parent.level) <= 3
      }
    },
    countNodeLevel(node) {
      console.log(node)
      // 找到所有子节点，求出最大深度
      if (node.childrenList !== null && node.childrenList.length > 0) {
        let children = node.childrenList
        for (let i = 0; i < children.length; i++) {
          if (children[i].catLevel > this.maxLevel) {
            this.maxLevel = children[i].catLevel
          }
          this.countNodeLevel(children[i])
        }
      }
    },

    // 获取子组件传递的数据
    getAndReload(value) {
      // 重新加载列表
      this.getDataList()
      // 展开父级
      this.expandKye = [value]
    }
  }
};
</script>

<style scoped>

</style>
