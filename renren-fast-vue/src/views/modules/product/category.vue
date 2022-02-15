<template>
  <div>
    <el-switch v-model="draggable" active-text="开启拖拽" inactive-text="不可拖拽"/>
    <el-button v-if="draggable" @click="batchSave">批量保存</el-button>
    <el-button type="danger" @click="batchDelete">批量删除</el-button>
    <el-tree
      :data="data"
      :props="defaultProps"
      @node-click="handleNodeClick"
      :expand-on-click-node="false"
      :default-expanded-keys="expandKye"
      show-checkbox
      :draggable="draggable"
      :allow-drop="allowDrop"
      @node-drop="handleDrop"
      node-key="catId"
      ref="menuTree">
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
      maxLevel: 0,
      tempChange: [],
      // 是否开启拖拽
      draggable: false,
      parentId: []
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

    batchSave() {
      // 保存拖拽后层级关系
      this.$http({
        url: this.$http.adornUrl("/product/category/reSort"),
        method: 'post',
        data: this.$http.adornData(this.tempChange, false)
      }).then(({data}) => {
        this.$message({
          message: "菜单顺序等修改成功",
          type: "success"
        });
        //刷新出新的菜单
        this.getDataList();
        //设置需要默认展开的菜单
        this.expandKye = this.parentId;
        // 清空到默认值
        this.tempChange = [];
        this.maxLevel = 0;
      });
    },

    batchDelete() {
      let catIds = [];
      let nodeName = [];
      let checkedNodes = this.$refs.menuTree.getCheckedNodes();
      console.log("被选中的元素", checkedNodes);
      for (let i = 0; i < checkedNodes.length; i++) {
        catIds.push(checkedNodes[i].catId);
        nodeName.push(checkedNodes[i].name)
      }
      this.$confirm(`是否批量删除【${nodeName}】菜单?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl("/product/category/delete"),
          method: "post",
          data: this.$http.adornData(catIds, false)
        }).then(({data}) => {
          this.$message({
            message: "菜单批量删除成功",
            type: "success"
          });
          this.getDataList();
        });
      }).catch(() => {
        });
    },

    // tree是否可拖拽，draggable：开启可拖拽方法，返回结果为boolean
    // draggingNode：被拖动节点， dropNode：拖拽到那个节点， type
    allowDrop(draggingNode, dropNode, type) {
      console.log(draggingNode, dropNode, type)
      // 拖拽结果不能大于三级层级
      // 被拖动节点的层级数
      this.countNodeLevel(draggingNode)
      // 当前节点与父节点深度相加<=3即可
      // let currentLevel = this.maxLevel - draggingNode.data.catLevel + 1
      // 若一直拖动，需要获取当前树的层级与当前节点的深度
      let currentLevel = Math.abs(this.maxLevel - draggingNode.level) + 1;
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
      if (node.childNodes !== null && node.childNodes.length > 0) {
        let children = node.childNodes
        for (let i = 0; i < children.length; i++) {
          if (children[i].level > this.maxLevel) {
            this.maxLevel = children[i].level
          }
          this.countNodeLevel(children[i])
        }
      }
    },
    /**
     * draggingNode：被拖拽节点
     * dropNode：推拽至那个节点前或后或inner
     * type：拖拽方式（before，after，inner）
     * */
    handleDrop(draggingNode, dropNode, type, event) {
      console.log(draggingNode, dropNode, type)
      // 父节点id
      let parentId = 0
      // 重新排序的列表
      let reSortList = null;
      //1.当前节点最新的父节点
      if (type === 'before' || type === 'after') {
        // 获取当前节点现在所在位置的父id--根据当前所在位置的兄弟节点获取--如果推拽到了一级节点，一级父级分类id会出现undefined
        parentId = dropNode.parent.data.catId === undefined ? 0 : dropNode.parent.data.catId
        reSortList = dropNode.parent.childNodes
      } else {
        // 如果拖拽方式是inner，则获取当前所在节点的id
        parentId = dropNode.data.catId
        reSortList = dropNode.childNodes
      }
      this.parentId.push(parentId)
      //2.当前拖拽节点最新的排序（从当前父级下可获取到）
      for (let i = 0; i < reSortList.length; i++) {
        // 如果是当前拖拽的节点--修改顺序和父Id
        if (reSortList[i].data.catId === draggingNode.data.catId) {
          //3.当前拖拽节点最新层级
          // 如果层级发生变化--当前拖动的节点与正在遍历的节点的层级不一致
          let catLevel = draggingNode.data.catLevel
          if (reSortList[i].level != draggingNode.level) {
            // 当前节点层级变化修改
            catLevel = reSortList[i].level
            // 子节点层级变化
            this.updateChildNode(reSortList[i]);
          }
          this.tempChange.push({catId: reSortList[i].data.catId, sort: i, parentCid: parentId, catLevel: catLevel});
        } else {
          this.tempChange.push({catId: reSortList[i].data.catId, sort: i})
        }
      }
      console.log(this.tempChange)

    },
    // 递归子节点，修改层级
    updateChildNode(node) {
      if (node.childNodes.length > 0) {
        for (let i = 0; i < node.childNodes.length; i++) {
          // 当前数据
          var cNode = node.childNodes[i].data;
          this.tempChange.push({
            catId: cNode.catId,
            catLevel: node.childNodes[i].level
          });
          this.updateChildNode(node.childNodes[i]);
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
  },
}
</script>

<style scoped>

</style>
