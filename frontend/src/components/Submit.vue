<script setup lang="ts">
const props = defineProps<{
  disabled: boolean;
}>();
</script>

<template>
  <button
    :disabled="disabled"
    class="mt-4 bg-blue-500 text-white font-semibold py-2 px-4 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-600 hover:bg-blue-600 disabled:bg-gray-300 disabled:cursor-not-allowed"
  >
   <slot />
  </button>
</template>
