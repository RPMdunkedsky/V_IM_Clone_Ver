<template>
  {{ formatDate }}
</template>

<script setup lang="ts">
import { defineProps, onMounted, ref } from "vue";

const props = defineProps({
  time: {
    type: [Number, Date, String],
    required: true,
    default: new Date(),
  },
});
const formatDate = ref("");
onMounted(() => {
  const type = typeof props.time;
  let time;
  if (type === "number") {
    const timestamp =
      props.time.toString().length > 10 ? props.time : props.time * 1000;
    time = new Date(timestamp).getTime();
  } else if (type === "object") {
    time = props.time.getTime();
  } else if (type === "string") {
    time = new Date(props.time).getTime();
  }
  formatDate.value = dateStr(time);
});

const format = (date: Date, format: string) => {
  const o = {
    "M+": date.getMonth() + 1, //month
    "d+": date.getDate(), //day
    "h+": date.getHours(), //hour
    "m+": date.getMinutes(), //minute
    "s+": date.getSeconds(), //second
    "q+": Math.floor((date.getMonth() + 3) / 3), //quarter
    S: date.getMilliseconds(), //millisecond
  };
  if (/(y+)/.test(format))
    format = format.replace(
      RegExp.$1,
      (date.getFullYear() + "").substr(4 - RegExp.$1.length)
    );
  for (let k in o)
    if (new RegExp("(" + k + ")").test(format))
      format = format.replace(
        RegExp.$1,
        RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length)
      );
  return format;
};

const dateStr = (date: number): string => {
  // 获取js 时间戳
  let time = new Date().getTime();
  // 去掉 js 时间戳后三位
  time = (time - date) / 1000;

  // 存储转换值
  let s;
  if (time < 60 * 2) {
    // 十分钟内
    return "刚刚";
  } else if (time < 60 * 60 && time >= 60 * 2) {
    // 超过十分钟少于1小时
    s = Math.floor(time / 60);
    return s + "分钟前";
  } else if (time < 60 * 60 * 24 && time >= 60 * 60) {
    // 超过1小时少于24小时
    s = Math.floor(time / 60 / 60);
    return s + "小时前";
  } else if (time < 60 * 60 * 24 * 3 && time >= 60 * 60 * 24) {
    // 超过1天少于3天内
    s = Math.floor(time / 60 / 60 / 24);
    return s + "天前";
  } else {
    // 超过3天
    let temp = new Date(date);
    return format(temp, "yyyy-MM-dd hh:mm:ss");
  }
};
</script>

<style scoped></style>
