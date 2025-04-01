<script setup lang="ts">
const model = defineModel<string>()
const props = defineProps<{
  type: string
  required: boolean
}>();
</script>

<template>
  <input v-model="model" :type="type" class="border p-2 rounded w-full"  :required="required"/>
</template>
