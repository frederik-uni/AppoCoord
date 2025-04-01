<script lang="ts" setup>
import {computed, onMounted, reactive, ref, watch} from "vue";
import FingerprintJS from "@fingerprintjs/fingerprintjs";
import Title from "@/components/Title.vue";
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

const availableTimesAdmin = computed(() => {
  console.log(Array.from(selectedTimes.value.values()))
  const timeCounts = new Map();
  const times = props.data.users.filter((v: any) => v.user.fingerprint.length == 0).flatMap((user: any) => user.timeInfo);
  [...times, ...selectedTimes.value.values()].forEach((time: any) => {
    const prev = timeCounts.get(`${time.start}-${time.end}`);
    const v = [time, (prev ? prev[1] : 0) + 1];
    timeCounts.set(`${time.start}-${time.end}`, v);
  });
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
  <Title>{{ data.title }}</Title>
  <p v-if="data.description">{{ data.description }}</p>
  <p v-if="data.location">{{ data.location }}</p>
  <template v-if="timeOver">
    <li v-for="[availableTime, count] in availableTimesAdmin">
      {{ count }} {{ displayDate(availableTime) }}
    </li>
  </template>
  <template v-else>
    <p>{{ date.toLocaleString() }}</p>
    <div v-if="data.creator.fingerprint.length != 0">
      <ul>
        <li v-for="[availableTime, count] in availableTimesAdmin">
          {{ count }} {{ displayDate(availableTime) }}
        </li>
      </ul>
    </div>
    <div v-else>
      <form v-if="userInfo.submitted" @submit="() => userInfo.submitted = true">
        <Input v-model="userInfo.name" :required="true" name="Name" type="text"/>
        <Input v-model="userInfo.email" :required="true" name="Email" type="text"/>
        <Submit :disabled="disabled">Set Info</Submit>
      </form>
      <div v-else>
        <div v-for="[availableTime, count] in availableTimesAdmin">
          <label>
            <input
              :checked="selectedTimes.has( `${availableTime.start}-${availableTime.end}`)"
              :value="availableTime"
              type="checkbox"
              @change="toggleSelection(availableTime)"
            />
            {{ count }} {{ displayDate(availableTime) }}
          </label>
        </div>
      </div>

    </div>
  </template>

</template>

<style scoped>

</style>
