<template>
  <ReplyView v-if="data" :data="data" />
  <div v-else>
    <div class="w-16 h-16 border-t-4 border-blue-500 border-solid rounded-full animate-spin" />
  </div>
</template>

<script setup>
import {useRoute} from 'vue-router'
import {onMounted, ref} from "vue";
import ReplyView from "@/components/ReplyView.vue";

const route = useRoute()
const id = route.params.id;
const data = ref(null);
onMounted(async () => {
  const v = await fetch(`http://127.0.0.1:9091/api/info/${id}`)
  data.value = await v.json();
})
</script>
