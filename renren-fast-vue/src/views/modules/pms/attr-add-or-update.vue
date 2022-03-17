<template>
  <el-dialog
    :title="!dataForm.attrId ? '新增' : '修改'"
    :visible.sync="visible"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="80px"
    >
      <el-form-item label="属性名" prop="attrName">
        <el-input v-model="dataForm.attrName" placeholder="属性名"></el-input>
      </el-form-item>
      <el-form-item label="属性类型" prop="attrType">
        <el-select v-model="dataForm.attrType" placeholder="请选择">
          <el-option label="销售属性" :value="0"></el-option>
          <el-option label="基本属性" :value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="值类型" prop="valueType">
        <el-switch
          v-model="dataForm.valueType"
          inactive-text="只能单个值"
          active-text="允许多个值"
          active-color="#13ce66"
          inactive-color="#ff4949"
          :inactive-value="0"
          :active-value="1"
        ></el-switch>
      </el-form-item>
      <el-form-item label="可选值" prop="valueSelect">
        <el-select
          v-model="dataForm.valueSelect"
          filterable
          allow-create
          multiple
          placeholder="可输入后创建"
        >
        </el-select>
      </el-form-item>
      <el-form-item label="属性图标" prop="icon">
        <el-input v-model="dataForm.icon" placeholder="属性图标"></el-input>
      </el-form-item>
      <el-form-item label="所属分类" prop="catelogId">
        <CategoryCascader :catelogPath.sync="catelogPath"></CategoryCascader>
      </el-form-item>
      <el-form-item label="所属分组" prop="attrGroupId">
        <el-select
          v-model="dataForm.attrGroupId"
          placeholder="先选择分类，再选分组"
        >
          <el-option
            v-for="item in attrGroup"
            :key="item.attrGroupId"
            :value="item.attrGroupId"
            :label="item.attrGroupName"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="是否检索" prop="searchType">
        <el-switch
          v-model="dataForm.searchType"
          :inactive-value="0"
          :active-value="1"
          active-color="#13ce66"
          inactive-color="#ff4949"
        ></el-switch>
      </el-form-item>
      <el-form-item label="推荐展示" prop="showDesc">
        <el-switch
          v-model="dataForm.showDesc"
          :inactive-value="0"
          :active-value="1"
          active-color="#13ce66"
          inactive-color="#ff4949"
        ></el-switch>
      </el-form-item>
      <el-form-item label="是否启用" prop="enable">
        <el-switch
          v-model="dataForm.enable"
          :inactive-value="0"
          :active-value="1"
          active-color="#13ce66"
          inactive-color="#ff4949"
        ></el-switch>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import CategoryCascader from '../common/category-cascader.vue'
export default {
  components: { CategoryCascader },
  props: {
    attrType: {
      type: Number,
      default: 1
    } // 0-销售属性，1-基本属性
  },
  data () {
    return {
      
      visible: false,
      catelogPath: [],
      attrGroup: [],
      dataForm: {
        attrId: 0,
        attrName: '',
        searchType: '',
        icon: '',
        valueSelect: '',
        attrType: '',
        valueType: 0,
        enable: '',
        catelogId: '',
        showDesc: '',
        attrGroupId: ''
      },
      dataRule: {
        attrName: [
          { required: true, message: '属性名不能为空', trigger: 'blur' }
        ],
        attrType: [
          { required: true, message: '属性类型[0-销售属性，1-基本属性不能为空]', trigger: 'blur' }
        ],
        valueType: [
          { required: true, message: '值类型不能为空', trigger: 'blur' }
        ],
        valueSelect: [
          { required: true, message: '可选值列表[用逗号分隔]不能为空', trigger: 'blur' }
        ],
        searchType: [
          { required: true, message: '是否需要检索[0-不需要，1-需要]不能为空', trigger: 'blur' }
        ],
        icon: [
          { required: true, message: '属性图标不能为空', trigger: 'blur' }
        ],
        enable: [
          { required: true, message: '启用状态[0 - 禁用，1 - 启用]不能为空', trigger: 'blur' }
        ],
        catelogId: [
          { required: true, message: '所属分类不能为空', trigger: 'blur' }
        ],
        showDesc: [
          { required: true, message: '快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    catelogPath (path) {
      console.log(path)
      if (path && path.length === 3) {
        this.dataForm.catelogId = path[path.length - 1]
        this.$http({
          url: this.$http.adornUrl(`/pms/attrgroup/list/${path[2]}`),
          method: 'get',
          params: this.$http.adornParams({ page: 1, limit: 10000000 })
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.attrGroup = data.page.list
          } else {
            this.$message.error('分组信息获取失败：', data.msg)
          }
        })
      }
    }
  },
  methods: {
    init (id) {
      this.dataForm.attrId = id || 0
      this.dataForm.attrType = this.attrType
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.attrId) {
          this.$http({
            url: this.$http.adornUrl(`/pms/attr/info/${this.dataForm.attrId}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.attrName = data.attr.attrName
              this.dataForm.searchType = data.attr.searchType
              this.dataForm.icon = data.attr.icon
              this.dataForm.valueSelect = data.attr.valueSelect.split(';')
              this.dataForm.attrType = data.attr.attrType
              this.dataForm.valueType = data.attr.valueType
              this.dataForm.enable = data.attr.enable
              this.dataForm.catelogId = data.attr.catelogId
              this.dataForm.showDesc = data.attr.showDesc
              this.catelogPath = data.attr.catelogPath
              this.dataForm.attrGroupId = data.attr.attrGroupId
            }
          })
        }
      })
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/pms/attr/${!this.dataForm.attrId ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'attrId': this.dataForm.attrId || undefined,
              'attrName': this.dataForm.attrName,
              'searchType': this.dataForm.searchType,
              'valueType': this.dataForm.valueType,
              'icon': this.dataForm.icon,
              'valueSelect': this.dataForm.valueSelect.join(';'),
              'attrType': this.dataForm.attrType,
              'enable': this.dataForm.enable,
              'catelogId': this.dataForm.catelogId,
              'showDesc': this.dataForm.showDesc,
              'attrGroupId': this.dataForm.attrGroupId
            })
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.visible = false
                  this.$emit('refreshDataList')
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }
      })
    }
  }
}
</script>
