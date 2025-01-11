<script setup lang="ts">
import { ref, computed } from "vue";
import { useDetailColumns } from "./utils/detail-columns";
import "vue-json-pretty/lib/styles.css";
import VueJsonPretty from "vue-json-pretty";
import type { SaSessionVO, Props } from "./utils/types";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";
import Delete from "@iconify-icons/ep/delete";
import { kickOutByToken } from "@/api/monitor/user-online";

const props = withDefaults(defineProps<Props>(), {
  data: () => ({})
});

const vo = ref<SaSessionVO>(props.data);

const { detailColumns } = useDetailColumns();

const dataList = ref([
  {
    title: "ID",
    name: "id",
    json: false,
    data: vo.value.id
  }
]);

const tableData = vo.value.tokenSignList;

const showErrorMsg = computed(() => {
  return false;
});

function handleDelete(row, index) {
  kickOutByToken({ value: row.value }).then(() => {
    this.tableData.splice(index, 1);
  });
}
</script>

<template>
  <div>
    <el-scrollbar>
      <PureDescriptions
        border
        :data="[vo]"
        :columns="detailColumns"
        :column="2"
      />
    </el-scrollbar>
    <el-tabs
      v-if="showErrorMsg"
      :modelValue="'errorMsg'"
      type="border-card"
      class="mt-4"
    >
      <template v-for="(item, index) in dataList" :key="index">
        <el-tab-pane :name="item.name" :label="item.title">
          <el-scrollbar max-height="calc(100vh - 240px)">
            <template v-if="item.data != null">
              <vue-json-pretty v-if="item.json" :data="JSON.parse(item.data)" />
              <div v-else>{{ item.data }}</div>
            </template>
          </el-scrollbar>
        </el-tab-pane>
      </template>
    </el-tabs>
    <el-divider content-position="left">账号登录设备</el-divider>
    <el-table
      :data="tableData"
      show-overflow-tooltip
      border
      style="width: 100%"
    >
      <el-table-column prop="device" label="设备类型" width="120" />
      <el-table-column prop="token" label="Token" />
      <el-table-column prop="tag" label="Tag" width="80" />
      <el-table-column fixed="right" label="操作" width="80">
        <template #default="{ row, $index }">
          <el-popconfirm
            :width="500"
            :title="`是否从设备类型为【${row.device}】的设备【强制下线】当前用户？`"
            @confirm="handleDelete(row, $index)"
          >
            <template #reference>
              <el-button
                class="reset-margin"
                link
                type="primary"
                size="default"
                :icon="useRenderIcon(Delete)"
              >
                下线
              </el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
