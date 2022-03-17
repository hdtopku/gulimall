<template>
    <el-cascader
      filterable
      clearable
      placeholder="试试搜索：手机"
      v-model="paths"
      :options="categorys"
      :props="{ expandTrigger: 'hover', label: 'name', value: 'catId', children: 'children' }"
    ></el-cascader>
</template>

<script>
export default {
  // 接受父组件传来的值
  props: {
    catelogPath: {
      type: Array,
      default () {
        return []
      }
    }
  },
  data () {
    return {
      paths: this.catelogPath,
      categorys: this.catelogPath,
      subscribe: null
    }
  },
  watch: {
    catelogPath (v) {
      this.paths = this.catelogPath
    },
    paths (newVal) {
      this.$emit('update:catelogPath', newVal)
      // 也可用pubsub-js模块中的PubSub发布订阅paths的变化
      this.PubSub.publish('catPath', newVal)
    }
  },
  methods: {
    getCategorys () {
      this.$http({
        url: this.$http.adornUrl('/pms/category/list/tree'),
        method: 'get'
      }).then(({ data }) => {
        this.categorys = data.data
      })
    }
  },
  created () {
    this.getCategorys()
  },
  mounted () {
    // 监听三级分类消息的变化
    this.subscribe = this.PubSub.subscribe('catPath', (msg, val) => {
      if (val != null) {
        this.catId = val[val.length - 1]
      }
    })
  },
  beforeDestroy () {
    this.PubSub.unsubscribe(this.subscribe) // 销毁订阅
  }
}
</script>

<style scoped></style>
