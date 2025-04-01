<script setup lang="ts">
const props = defineProps<{ data: any }>();
</script>

<template>
  <textarea class="w-screen h-screen">
  {{ JSON.stringify(data, null, 4) }}
  </textarea>
</template>

<style scoped>

</style>
