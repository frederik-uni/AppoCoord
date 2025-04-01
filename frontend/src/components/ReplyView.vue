<script setup lang="ts">
const props = defineProps<{ data: any }>();
</script>

<template>
  {{ JSON.stringify(data) }}
</template>

<style scoped>

</style>
