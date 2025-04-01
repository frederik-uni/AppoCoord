<script setup lang="ts">
const model = defineModel<string>()
const props = defineProps<{
  name: string,
  type: string,
  required: boolean,
}>();
</script>

<template>
  <div class="flex flex-col gap-1 my-1 mb-2">
    <label :for="name" class="text-gray-700 font-medium">
      {{ name }}
      <span v-if="required" class="text-red-500">*</span>
    </label>
    <input v-model="model" :type="type" :id="name" :name="name"
           class="border p-2 w-full px-3 py-2 rounded-md focus:outline-none focus:ring-1 focus:ring-blue-500 border-gray-200 text-gray-600"
           :required="required"/>
  </div>
</template>

