<script setup lang="ts">
import {computed, onMounted, ref} from "vue";
import FingerprintJS from "@fingerprintjs/fingerprintjs";
import Title from "@/components/Title.vue";

const props = defineProps<{ data: any }>();
const fingerprint = ref<string | null>(null);
const selectedTimes = ref<Set<string>>(new Set());

onMounted(async () => {
  const fp = await FingerprintJS.load();
  const fp_ = await fp.get();
  fingerprint.value = fp_.visitorId;
});
const date = computed(() => {
  return new Date(props.data.end * 1000);
})

const toggleSelection = (timeRange: string) => {
  if (selectedTimes.value.has(timeRange)) {
    selectedTimes.value.delete(timeRange);
  } else {
    selectedTimes.value.add(timeRange);
  }
};


const availableTimesAdmin = computed(() => {
  const timeCounts = new Map();
  const times = props.data.users.flatMap((user: any) => user.timeInfo);
  [...times, ...selectedTimes.value].forEach((time: any) => {
    timeCounts.set(time, (timeCounts.get(time) || 0) + 1);
  });
  return Array.from(timeCounts.entries());
});

const timeOver = computed(() => {
  return props.data.end * 1000 < Date.now()
})

const displayDate = (time: any) => {
  return new Date(time.start * 1000).toLocaleString() + " - " + new Date(time.end * 1000).toLocaleString();
}

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
        <label>
          <input
            type="checkbox"
            :value="availableTime"
            :checked="selectedTimes.has(availableTime)"
            @change="toggleSelection(availableTime)"
          />
          {{ count }} {{ displayDate(availableTime) }}
        </label>
      </li>
    </ul>
  </template>

</template>

<style scoped>

</style>
