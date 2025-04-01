<script setup lang="ts">
import { defineEmits } from 'vue';

const emit = defineEmits<{
  (e: 'submit'): void;
}>();

const handleSubmit = (event: Event) => {
  event.preventDefault();
  emit('submit');
};
</script>

<template>
  <form @submit.prevent="handleSubmit" class="max-w-7xl mx-auto p-4 bg-white rounded-md">
    <slot />
  </form>
</template>
