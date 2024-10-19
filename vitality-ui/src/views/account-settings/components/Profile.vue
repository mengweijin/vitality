<script setup lang="ts">
import { reactive, ref } from "vue";
import { message } from "@/utils/message";
import { type UserInfo } from "@/api/user";
import userAvatar from "@/assets/user.jpg";
import { getMine, setUserAvatar } from "@/api/system/user";
import type { FormInstance, FormRules } from "element-plus";
import ReCropperPreview from "@/components/ReCropperPreview";
import { createFormData, deviceDetection } from "@pureadmin/utils";
import uploadLine from "@iconify-icons/ri/upload-line";
import { UserAvatarBO, UserVO } from "@/views/system/user/utils/types";
import { useUserStoreHook } from "@/store/modules/user";
import { useDictStoreHook } from "@/store/modules/dict";

defineOptions({
  name: "Profile"
});

const imgSrc = ref("");
const avatarInfo = ref();
const cropRef = ref();
const uploadRef = ref();
const isShow = ref(false);
const userInfoFormRef = ref<FormInstance>();

const userInfos = reactive<UserVO>({});

const rules = reactive<FormRules<UserInfo>>({
  nickname: [{ required: true, message: "昵称必填", trigger: "blur" }]
});

function queryEmail(queryString, callback) {
  const emailList = [
    { value: "@qq.com" },
    { value: "@foxmail.com" },
    { value: "@gmail.com" },
    { value: "@aliyun.com" },
    { value: "@126.com" },
    { value: "@163.com" }
  ];
  let results = [];
  let queryList = [];
  emailList.map(item =>
    queryList.push({ value: queryString.split("@")[0] + item.value })
  );
  results = queryString
    ? queryList.filter(
        item =>
          item.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
      )
    : queryList;
  callback(results);
}

const onChange = uploadFile => {
  const reader = new FileReader();
  reader.onload = e => {
    imgSrc.value = e.target.result as string;
    isShow.value = true;
  };
  reader.readAsDataURL(uploadFile.raw);
};

const handleClose = () => {
  cropRef.value.hidePopover();
  uploadRef.value.clearFiles();
  isShow.value = false;
};

// const onCropper = ({ blob }) => (avatarBlob.value = blob);
const onCropper = (info: any) => (avatarInfo.value = info);

const handleSubmitImage = () => {
  // const formData = createFormData({
  //   files: new File([cropperBlob.value], "avatar")
  // });
  const userAvatarBO = {
    userId: userInfos.id,
    avatar: avatarInfo.value.base64
  } as UserAvatarBO;
  setUserAvatar(userAvatarBO).then((r: R) => {
    if (r.code == 200) {
      userInfos.avatar = userAvatarBO.avatar;
      useUserStoreHook().SET_AVATAR(userAvatarBO.avatar);
      handleClose();
    }
  });
};

// 更新信息
const onSubmit = async (formEl: FormInstance) => {
  await formEl.validate((valid, fields) => {
    if (valid) {
      console.log(userInfos);
      message("更新信息成功", { type: "success" });
    } else {
      console.log("error submit!", fields);
    }
  });
};

getMine().then(res => {
  Object.assign(userInfos, res);
});
</script>

<template>
  <div
    :class="[
      'min-w-[180px]',
      deviceDetection() ? 'max-w-[100%]' : 'max-w-[70%]'
    ]"
  >
    <h3 class="my-8">个人信息</h3>
    <el-form
      ref="userInfoFormRef"
      label-position="top"
      :rules="rules"
      :model="userInfos"
    >
      <el-form-item label="头像">
        <el-avatar :size="80" :src="userInfos.avatar || userAvatar" />
        <el-upload
          ref="uploadRef"
          accept="image/*"
          action="#"
          :limit="1"
          :auto-upload="false"
          :show-file-list="false"
          :on-change="onChange"
        >
          <el-button plain class="ml-4">
            <IconifyIconOffline :icon="uploadLine" />
            <span class="ml-2">更新头像</span>
          </el-button>
        </el-upload>
      </el-form-item>
      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="userInfos.nickname" placeholder="请输入昵称" />
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-select
          v-model="userInfos.gender"
          placeholder="请选择性别"
          clearable
          class="!w-[180px]"
          style="width: 100% !important"
        >
          <el-option
            v-for="(item, index) in useDictStoreHook().getDicts(
              'vtl_user_gender'
            )"
            :key="index"
            :label="item.label"
            :value="item.val"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-autocomplete
          v-model="userInfos.email"
          :fetch-suggestions="queryEmail"
          :trigger-on-focus="false"
          placeholder="请输入邮箱"
          clearable
          class="w-full"
        />
      </el-form-item>
      <el-form-item label="联系电话">
        <el-input
          v-model="userInfos.mobile"
          placeholder="请输入联系电话"
          clearable
        />
      </el-form-item>
      <el-form-item label="简介">
        <el-input
          v-model="userInfos.remark"
          placeholder="请输入简介"
          type="textarea"
          :autosize="{ minRows: 6, maxRows: 8 }"
          maxlength="56"
          show-word-limit
        />
      </el-form-item>
      <el-button type="primary" @click="onSubmit(userInfoFormRef)">
        更新信息
      </el-button>
    </el-form>
    <el-dialog
      v-model="isShow"
      width="40%"
      title="编辑头像"
      destroy-on-close
      :closeOnClickModal="false"
      :before-close="handleClose"
      :fullscreen="deviceDetection()"
    >
      <ReCropperPreview ref="cropRef" :imgSrc="imgSrc" @cropper="onCropper" />
      <template #footer>
        <div class="dialog-footer">
          <el-button bg text @click="handleClose">取消</el-button>
          <el-button bg text type="primary" @click="handleSubmitImage">
            确定
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>
