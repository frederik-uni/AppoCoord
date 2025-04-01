<script lang="ts" setup>
import {defineEmits} from 'vue';

const emit = defineEmits<{
  (e: 'submit'): void;
}>();

const handleSubmit = (event: Event) => {
  event.preventDefault();
  emit('submit');
};
</script>

<template>
  <form class="max-w-3xl w-full mx-auto p-4 bg-white rounded-md" @submit.prevent="handleSubmit">
    <slot/>
  </form>
</template>
