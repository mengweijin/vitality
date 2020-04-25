<template>
  <div>
      <el-alert
        title="提示"
        type="error"
        :closable="false" 
        description="数据会每天清理，请及时保存。">
    </el-alert>
    <br>
    <el-row>
        <el-button @click="handleAddClick()" type="primary" plain  icon="el-icon-plus">创建任务</el-button>
    </el-row>
    <el-table 
        v-loading="loading" 
        element-loading-text="拼命加载中" 
        element-loading-spinner="el-icon-loading" 
        element-loading-background="rgba(0, 0, 0, 0.5)" 
        :data="tableData" 
        :row-style="{height:'40px'}" 
        :cell-style="{padding:'5px 0'}">
        <el-table-column prop="id" label="任务ID" min-width="180" sortable v-show="false"></el-table-column>
        <el-table-column prop="name" label="任务名称" min-width="180" sortable v-show="false"></el-table-column>
        <el-table-column prop="url" label="URL" min-width="300">
            <template slot-scope="scope">
                <a :href="scope.row.url" target="_blank">{{ scope.row.url }}</a>
            </template>
        </el-table-column>
        <el-table-column prop="status" label="任务状态" min-width="120">
            <template slot-scope="scope">
                <el-tag v-if="scope.row.status==='QUEUING'" type="warning" size="medium" effect="dark">{{ scope.row.status }}</el-tag>
                <el-tag v-if="scope.row.status==='RUNNING'" type="info" size="medium" effect="dark">{{ scope.row.status }}</el-tag>
                <el-tag v-if="scope.row.status==='SUCCESS'" type="success" size="medium" effect="dark">{{ scope.row.status }}</el-tag>
                <el-tag v-if="scope.row.status==='FAILED'" type="danger" size="medium" effect="dark">{{ scope.row.status }}</el-tag>
            </template>
        </el-table-column>
        <el-table-column prop="attachmentName" label="文件名称" min-width="260"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" min-width="200" :formatter="dateTimeFormat"></el-table-column>
        <el-table-column prop="updateTime" label="最后修改时间" min-width="200" :formatter="dateTimeFormat" sortable></el-table-column>
        <el-table-column prop="errorMessage" label="失败信息" min-width="200"></el-table-column>
        <el-table-column fixed="right" label="操作" width="120">
            <template slot-scope="scope">
                <router-link tag="a" target="_blank" :to="{path: '/video-downloader/task/play/' + scope.row.id}" style="margin-right:10px">
                    <el-button v-if="scope.row.status==='SUCCESS'" type="text" size="medium" icon="el-icon-video-play"></el-button>
                </router-link>
                <el-button v-if="scope.row.status==='SUCCESS'" @click="handleDownloadClick(scope.row)" type="text" size="medium" icon="el-icon-download">
                </el-button>
                <el-button v-if="scope.row.status==='SUCCESS' || scope.row.status==='FAILED'" @click="handleDeleteClick(scope.$index, scope.row)" type="text" size="medium" icon="el-icon-delete" style="color:red">
                </el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 30, 50, 100]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalCount">
    </el-pagination>

    <!-- 添加任务对话框 -->
    <el-dialog title="新增" :visible.sync="open" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="任务名称" prop="name" v-show="true">
          <el-input v-model="form.name" placeholder="请输入任务名称" />
        </el-form-item>
        <el-form-item label="URL" prop="url">
          <el-input v-model="form.url" placeholder="请输入视频URL地址" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
          <!-- 
              native-type="button"
              在Internet Explorer 表单提交的默认类型是 “button”，而其他浏览器中（包括 W3C 规范）的默认值是 “submit”
              否则：java.io.IOException: 你的主机中的软件中止了一个已建立的连接
           -->
        <el-button type="primary" native-type="button" @click="submitForm('form')">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script type="text/ecmascript-6">
    export default {
        data: function() {
            return {
                loading: true,
                tableData: [],
                currentPage: 1,
                totalCount: 0,
                pageSize: 10,
                // 是否显示弹层
                open: false,
                // 表单参数
                form: {},
                // 表单校验
                rules: {
                    name: [
                        {required: false, message: "不能为空", trigger: "blur"}
                    ],
                    url: [
                        {required: true, message: "不能为空", trigger: "blur"}
                    ]
                }
            }
        },
        methods: {
            loadTableData(current, size) {
                let _this = this;
                this.$get('/video-downloader/task/page', {current: current, size: size})
                .then(function (response) {
                    _this.tableData = response.dataList
                    _this.totalCount = response.total
                    _this.loading = false
                })
            },
            handleSizeChange(val) {
                this.currentPage = 1
                this.pageSize = val
                this.loadTableData(this.currentPage, this.pageSize)
            },
            handleCurrentChange(val) {
                this.currentPage = val
                this.loadTableData(this.currentPage, this.pageSize)
            },
            handleDownloadClick(row) {
                window.location.href = this.$axios.defaults.baseURL + '/video-downloader/task/download/' + row.id
            },
            handleDeleteClick(index, row) {
                let _this = this
                this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    _this.$delete('/video-downloader/task/delete/' + row.id, {deleteVideo: true})
                        .then(function (res) {
                            _this.tableData.splice(index, 1)
                            _this.totalCount--
                            _this.$message({ message: '操作成功！', type: 'success'});
                        })
                }).catch(() => {

                });
            },
            dateTimeFormat(row, column) {
                let date = row[column.property]
                if(date == undefined){return ''}
                return this.$dayjs(date).format("YYYY-MM-DD HH:mm:ss")
            },
            handleAddClick() {
                this.open = true
            },
            cancel() {
                this.open = false
            },
            submitForm(formName) {
                let _this = this
                this.$refs[formName].validate(valid => {
                    if(valid) {
                        this.$post('/video-downloader/task/add', this.form)
                        .then(function (response) {
                            _this.$message({ message: '操作成功！', type: 'success'})
                            _this.open = false
                            _this.loadTableData(1, _this.pageSize)
                            this.form = {}
                        })
                    }
                })
            }
        },
        created: function() {
            this.loadTableData(this.currentPage, this.pageSize)
        }
    }
</script>