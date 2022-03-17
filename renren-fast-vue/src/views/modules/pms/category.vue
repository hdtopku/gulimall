<template>
  <div>
    <el-button type="danger" @click="batchDel">批量删除</el-button>
    <el-switch
      v-model="draggable"
      active-text="开启拖拽"
      inactive-text="关闭拖拽"
    >
    </el-switch>
    <el-button v-if="draggable" @click="batchSave" type="primary"
      >保存拖拽</el-button
    >
    <hr />
    <el-tree
      show-checkbox
      :data="menus"
      node-key="catId"
      :props="defaultProps"
      :expand-on-click-node="false"
      :default-expanded-keys="expandedKeys"
      :draggable="draggable"
      :allow-drop="allowDrop"
      @node-drop="handleDrop"
      ref="tree"
    >
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <span>
          <el-button type="text" size="mini" @click="() => edit(data)">
            edit
          </el-button>
          <el-button
            v-if="node.level <= 2"
            type="text"
            size="mini"
            @click="() => append(data)"
          >
            Append
          </el-button>
          <el-button
            v-if="node.isLeaf"
            type="text"
            size="mini"
            @click="() => remove(node, data)"
          >
            Delete
          </el-button>
        </span>
      </span>
    </el-tree>
    <el-dialog :title="title" :visible.sync="dialogVisible" width="30%">
      <el-form :model="category">
        <el-form-item label="分类名称">
          <el-input v-model="category.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="category.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="计量单位">
          <el-input
            v-model="category.productUnit"
            autocomplete="off"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitData">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
const defaultCategory = {
  catId: null,
  name: '',
  parentCid: 0,
  catLevel: 0,
  showStatus: 1,
  sort: 0,
  icon: '',
  productUnit: ''
}
export default {
  data () {
    return {
      draggable: false,
      updateNodes: [],
      menus: [],
      expandedKeys: [],
      expandedPid: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      dialogVisible: false,
      category: Object.assign({}, defaultCategory),
      title: '',
      isEdit: false
    }
  },
  methods: {
    getCategorys () {
      this.$http({
        url: this.$http.adornUrl('/pms/category/list/tree'),
        method: 'get'
      }).then(({ data }) => {
        this.menus = data.data
      })
    },
    submitData () {
      if (this.isEdit) {
        this.editCategory()
      } else {
        this.appendCategory()
      }
    },
    append (menu) {
      this.dialogVisible = true
      this.category = Object.assign({}, defaultCategory)
      this.title = `创建【${menu.name}】子分类`
      this.category.parentCid = menu.catId
      this.category.catLevel = menu.catLevel * 1 + 1 // menu.catLevel * 1 menu.catLevel字符串类型转化为数字
    },
    appendCategory () {
      this.isEdit = false
      this.$http({
        url: this.$http.adornUrl('/pms/category/save'),
        method: 'post',
        data: this.$http.adornData(this.category, false)
      }).then(() => {
        this.$message({
          message: `菜单【${this.category.name}】创建成功`,
          type: 'success',
          duration: 1500
        })
        this.getCategorys()
        this.expandedKeys = [this.category.parentCid]
        this.dialogVisible = false
      })
    },
    edit (menu) {
      this.isEdit = true
      this.title = `修改【${menu.name}】分类`
      this.dialogVisible = true
      this.category.name = menu.name
      // 发送请求获取当前节点最新数据
      this.$http({
        url: this.$http.adornUrl(`/pms/category/info/${menu.catId}`),
        method: 'get',
        params: this.$http.adornParams({})
      }).then(({ data }) => {
        console.log(data)
        this.category = data.category
      })
    },
    editCategory () {
      let { name, catId, icon, productUnit } = this.category
      this.$http({
        url: this.$http.adornUrl('/pms/category/update'),
        method: 'post',
        data: this.$http.adornData({ name, catId, icon, productUnit }, false)
      }).then(({ data }) => {
        this.$message({
          message: '菜单修改成功',
          type: 'success'
        })
        this.getCategorys()
        this.expandedKeys = [this.category.parentCid]
        this.dialogVisible = false
      })
    },
    batchDel () {
      this.$confirm(`确定批量删除菜单?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/pms/category/delete'),
          method: 'post',
          data: this.$http.adornData(this.$refs.tree.getCheckedKeys(), false)
        }).then(({ data }) => {
          this.$message({
            message: '菜单批量删除成功',
            type: 'success',
            duration: 1500
          })
          this.getCategorys()
        })
      })
    },
    batchSave () {
      this.$http({
        url: this.$http.adornUrl('/pms/category/update/sort'),
        method: 'post',
        data: this.$http.adornData(this.updateNodes, false)
      }).then(({ data }) => {
        this.getCategorys()
        this.$message({
          message: '修改成功',
          type: 'success',
          duration: 1500
        })
      })
      this.updateNodes = []
      this.expandedKeys = this.expandedPid
      this.expandedPid = []
    },
    handleDrop (draggingNode, dropNode, dropType, ev) {
      let sibling = [] // 更新完毕后，用于存放draggingNode及其兄弟节点
      // 当前节点的父节点与层级
      let originalLevel = draggingNode.data.catLevel
      if (dropType === 'inner') {
        sibling = dropNode.childNodes
        draggingNode.data.parentCid = dropNode.data.catId
        draggingNode.data.catLevel = dropNode.data.catLevel + 1
      } else {
        sibling = dropNode.parent.childNodes
        draggingNode.data.parentCid = dropNode.data.parentCid
        draggingNode.data.catLevel = dropNode.data.catLevel
      }
      // 当前拖拽节点的最新顺序
      if (draggingNode.data.catLevel !== originalLevel) {
        this.changeChildrenLevel(draggingNode, this.updateNodes)
      }
      sibling.forEach((item, index) => {
        this.updateNodes.push({
          catId: item.data.catId,
          sort: index,
          name: item.data.name,
          catLevel: item.data.catLevel,
          parentCid: item.data.parentCid
        })
      })
      this.expandedPid.push(draggingNode.data.parentCid)
    },
    changeChildrenLevel (node, updateNodes) {
      if (node.childNodes.length > 0) {
        node.childNodes.forEach(item => {
          item.data.catLevel = node.data.catLevel + 1
          this.changeChildrenLevel(item, node.data.catLevel + 1)
          updateNodes.push({
            catId: item.data.catId,
            name: item.data.name,
            catLevel: item.data.catLevel
          })
        })
      }
    },
    allowDrop (draggingNode, dropNode, type) {
      // 被拖动的当前节点以及所在的父节点总层数不大于3
      let deep = this.countNodeLevel(draggingNode.data)
      if (type === 'inner') {
        return deep + dropNode.level < 4
      }
      return deep + dropNode.level < 5
    },
    countNodeLevel (menu) {
      let maxLevel = 1
      if (menu.children == null || menu.children.length < 1) {
        return maxLevel
      }
      for (let i = 0; i < menu.children.length; i++) {
        maxLevel = Math.max(this.countNodeLevel(menu.children[i]) + 1, maxLevel)
        if (maxLevel > 3) {
          return maxLevel
        }
      }
      return maxLevel
    },
    remove (node, menu) {
      let ids = [menu.catId]
      this.$confirm(`确定删除【${menu.name}】菜单?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/pms/category/delete'),
          method: 'post',
          data: this.$http.adornData(ids, false)
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message({
              message: `【${data.name}】菜单删除成功`,
              type: 'success',
              duration: 1500
            })
            this.getCategorys()
            // 设置需要默认展开的菜单
            this.expandedKeys = [menu.parentCid]
          } else {
            this.$message.error(data.msg)
          }
        })
      }).catch(() => { })
    }
  },
  created () {
    this.getCategorys()
  }
}
</script>

<style scoped></style>