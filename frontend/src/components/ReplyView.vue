<script lang="ts" setup>
import {computed, onMounted, reactive, ref, watch} from "vue";
import FingerprintJS from "@fingerprintjs/fingerprintjs";
import Input from "@/components/Input.vue";
import Submit from "@/components/Submit.vue";

const props = defineProps<{ data: any }>();
const fingerprint = ref<string | null>(null);
const selectedTimes = ref<Map<string, { start: number; end: number }>>(new Map());
const userInfo = reactive({
  name: "",
  email: "",
  submitted: false,
});

onMounted(async () => {
  const fp = await FingerprintJS.load();
  const fp_ = await fp.get();
  fingerprint.value = fp_.visitorId;
});

const date = computed(() => {
  return new Date(props.data.end * 1000);
})

const toggleSelection = (timeRange: { start: number; end: number }) => {
  const timeKey = `${timeRange.start}-${timeRange.end}`;
  if (selectedTimes.value.has(timeKey)) {
    selectedTimes.value.delete(timeKey);
  } else {
    selectedTimes.value.set(timeKey, timeRange);
  }

  fetch("http://127.0.0.1:9091/api/reply/" + props.data.id, {
    method: "POST",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify({
      "user": {
        "fingerprint": fingerprint.value,
        "name": userInfo.name,
        "email": userInfo.email,
      },
      "timeInfo": Array.from(Array.from(selectedTimes.value.values()).flat())
    })
  })
};

watch(
  props.data,
  (newValue, _) => {
    newValue.users
      .filter((v: any) => v.user.fingerprint.length != 0)
      .forEach((v: any) => {
        v.timeInfo.forEach((v: any) => selectedTimes.value.set(`${v.start}-${v.end}`, v));
      });
  },
  {immediate: true}
);
const max_count = ref(0);
const availableTimesAdmin = computed(() => {
  console.log(Array.from(selectedTimes.value.values()))
  const timeCounts = new Map();
  const times = props.data.users.filter((v: any) => v.user.fingerprint.length == 0).flatMap((user: any) => user.timeInfo);
  [...times, ...selectedTimes.value.values()].forEach((time: any) => {
    const prev = timeCounts.get(`${time.start}-${time.end}`);
    const v = [time, (prev ? prev[1] : 0) + 1];
    timeCounts.set(`${time.start}-${time.end}`, v);
  });
  max_count.value = Array.from(timeCounts.values()).map(v => v[1]).reduce((max, num) => (num > max ? num : max), 0);
  return Array.from(timeCounts.values());
});

const timeOver = computed(() => {
  return props.data.end * 1000 < Date.now()
})

const displayDate = (time: any) => {
  return new Date(time.start * 1000).toLocaleString() + " - " + new Date(time.end * 1000).toLocaleString();
}

const disabled = computed(() => {
  return userInfo.email.length == 0 || userInfo.name.length == 0;
})
</script>

<template>
  <div class="flex items-center justify-center h-screen w-screen">
    <div class="p-6 max-w-3xl mx-auto bg-white rounded-xl shadow-md space-y-4 block">
      <h1 class="text-2xl font-bold text-gray-900">{{ data.title }}</h1>
      <p v-if="data.description" class="text-gray-700">{{ data.description }}</p>
      <p v-if="data.location" class="text-gray-600 italic">{{ data.location }}</p>

      <template v-if="timeOver">
        <ul class="space-y-2">
          <li v-for="[availableTime, count] in availableTimesAdmin" :key="availableTime.start"
              class="bg-gray-100 p-3 rounded-md relative">
            <div class="absolute h-full rounded-md bg-blue-100 left-0 top-0 z-0"
                 :class="{'bg-green-100': count == max_count && count > 0}"
                 :style="{ width: (count / max_count) * 100 + '%' }"/>
            <label class="relative flex-grow cursor-default z-10">{{ displayDate(availableTime) }}</label>
          </li>
        </ul>
      </template>

      <template v-else>
        <p class="text-sm text-gray-500">{{ date.toLocaleString() }}</p>
        <div v-if="data.creator.fingerprint.length !== 0" class="mt-4">
          <ul class="space-y-2">
            <li v-for="[availableTime, count] in availableTimesAdmin" :key="availableTime.start"
                class="bg-gray-100 p-3 rounded-md relative">
              <div class="absolute h-full rounded-md bg-blue-100 left-0 top-0 z-0"
                   :style="{ width: (count / max_count) * 100 + '%' }"/>
              <label class="relative flex-grow cursor-default z-10">{{ displayDate(availableTime) }}</label>
            </li>
          </ul>
        </div>
        <div v-else>
          <form v-if="userInfo.submitted" @submit.prevent="userInfo.submitted = true"
                class="space-y-4 w-120" >
            <Input v-model="userInfo.name" :required="true" name="Name" type="text" class="w-full"/>
            <Input v-model="userInfo.email" :required="true" name="Email" type="text"
                   class="w-full"/>
            <Submit :disabled="disabled"
                    class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">Set
              Info
            </Submit>
          </form>
          <div v-else class="space-y-2">
            <div v-for="[availableTime, count] in availableTimesAdmin" :key="availableTime.start"
                 class="flex items-center space-x-2 bg-gray-100 p-3 rounded-md relative"
                 @click="toggleSelection(availableTime)"

            >
              <div class="absolute h-full w-full rounded-md bg-blue-100 left-0"
                   :style="{ width: (count / max_count) * 100 + '%' }"/>
            <input
              :checked="selectedTimes.has(`${availableTime.start}-${availableTime.end}`)"
              :value="availableTime"
              type="checkbox"
              class="w-4 h-4 text-blue-600 border-gray-300 rounded z-2"
            />
              <label class="flex-grow cursor-pointer z-2"> {{
                  displayDate(availableTime)
                }}</label>
            </div>
          </div>
        </div>
      </template>
    </div>
  </div>
</template>
