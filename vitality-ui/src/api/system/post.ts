import { http } from "@/utils/http";
import type {
  FormProps,
  PostVO
} from "@/views/vitality/system/post/utils/types";

/** 查询 */
export const getPostPage = (params?: PostVO) => {
  return http.get<Page, PostVO>("/system/post/page", { params });
};

/** 创建 */
export const createPost = (data: FormProps) => {
  return http.post<R, FormProps>("/system/post/create", { data });
};

/** 修改 */
export const updatePost = (data: FormProps) => {
  return http.post<R, FormProps>("/system/post/update", { data });
};

/** 删除 */
export const deletePost = (ids: String) => {
  return http.post<R, any>("/system/post/delete/" + ids);
};
