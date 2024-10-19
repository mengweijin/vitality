import { defineStore } from "pinia";
import { store } from "../utils";
import { getDictDataList } from "@/api/system/dict";
import { storageLocal } from "@pureadmin/utils";

export const useDictStore = defineStore({
  id: "pure-dict",
  state: () => ({
    // 所有字典数据
    dicts: storageLocal().getItem("dicts") ?? []
  }),
  actions: {
    /** 字典 */
    INIT_DICTS() {
      getDictDataList().then(dictList => {
        this.dicts = Object.groupBy(dictList, ({ code }) => code);
        storageLocal().setItem("dicts", this.dicts);
      });
    },
    getDicts(code: string) {
      return this.dicts[code] || [];
    }
  }
});

export function useDictStoreHook() {
  return useDictStore(store);
}
