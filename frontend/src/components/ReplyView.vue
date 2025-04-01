<script setup lang="ts">
import {computed, onMounted, ref} from "vue";
import FingerprintJS from "@fingerprintjs/fingerprintjs";
import Title from "@/components/Title.vue";

const props = defineProps<{ data: any }>();
const fingerprint = ref<string | null>(null);
onMounted(async () => {
  const fp = await FingerprintJS.load();
  const fp_ = await fp.get();
  fingerprint.value = fp_.visitorId;
});
const date = computed(() => {
  return new Date(props.data.end * 1000);
})

const availableTimesAdmin = computed(() => {
  const timeCounts = new Map();

  props.data.users.forEach((user: any) => {
    user.timeInfo.forEach((time: any) => {
      const timeRange = new Date(time.start * 1000).toLocaleString() + " - " + new Date(time.end * 1000).toLocaleString();
      timeCounts.set(timeRange, (timeCounts.get(timeRange) || 0) + 1);
    });
  });
  return Array.from(timeCounts.entries());
});

const timeOver = computed(() => {
  return props.data.end * 1000 < Date.now()
})

</script>

<template>
  <Title>{{ data.title }}</Title>
  <p v-if="data.description">{{ data.description }}</p>
  <p v-if="data.location">{{ data.location }}</p>
  <template v-if="timeOver">
    <li v-for="[availableTime, count] in availableTimesAdmin">
      {{ count }} {{ availableTime }}
    </li>
  </template>
  <template v-else>
    <p>{{ date.toLocaleString() }}</p>
    <div v-if="data.creator.fingerprint.length != 0">
      <ul>
        <li v-for="[availableTime, count] in availableTimesAdmin">
          {{ count }} {{ availableTime }}
        </li>
      </ul>
    </div>
    <ul v-else>
      <li v-for="[availableTime, count] in availableTimesAdmin">
        {{ availableTime }}
      </li>
    </ul>
  </template>

</template>

<style scoped>

</style>
