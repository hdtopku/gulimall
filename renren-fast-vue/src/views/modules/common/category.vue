<template>
  <el-tree
    :data="menus"
    node-key="catId"
    :props="defaultProps"
    ref="tree"
    @node-click="nodeClick"
  >
  </el-tree>
</template>

<script>
export default {
  data () {
    return {
      menus: [],
      expandedKeys: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      }
    }
  },
  methods: {
    getMenus () {
      this.$http({
        url: this.$http.adornUrl('/pms/category/list/tree'),
        method: 'get'
      }).then(({ data }) => {
        this.menus = data.data
      })
    },
    nodeClick (data, node, component) {
      this.$emit('tree-node-click', data, node, component)
    }
  },
  created () {
    this.getMenus()
  }
}
</script>

<style scoped></style>
