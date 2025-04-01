<script setup lang="ts">
import {computed, onMounted, reactive, ref} from 'vue'
import Container from "@/components/Container.vue";
import Title from "@/components/Title.vue";
import Submit from "@/components/Submit.vue";
import Input from "@/components/Input.vue";
import FingerprintJS from '@fingerprintjs/fingerprintjs';
import ReplyView from "@/components/ReplyView.vue";

const formData = reactive({
  title: '',
  description: '',
  location: '',
  end: '',
  uploader: {
    name: '',
    email: '',
    fingerprint: ''
  },
  available_times: [["2025-04-11T15:30:00", "2025-04-11T16:30:00"], ["2025-04-11T16:30:00", "2025-04-11T17:30:00"], ["2025-04-11T17:30:00", "2025-04-11T18:30:00"]]
})

const loading = ref(false);
const data = ref(null);

onMounted(async () => {
  const fp = await FingerprintJS.load();
  const fp_ = await fp.get();
  formData.uploader.fingerprint = fp_.visitorId;
});

const send = async () => {
  loading.value = true;
  const resp = await fetch("http://127.0.0.1:9091/api/create", {
    method: 'POST',
    body: JSON.stringify({
      title: formData.title,
      description: formData.description,
      location: formData.location,
      end: Math.floor(new Date(formData.end).getTime() / 1000),
      uploader: {
        fingerprint: formData.uploader.fingerprint,
        name: formData.uploader.name,
        email: formData.uploader.email,
      },
      available_times: formData.available_times.map(v => {
        return {
          "start": Math.floor(new Date(v[0]).getTime() / 1000),
          "end": Math.floor(new Date(v[1]).getTime() / 1000),
        };
      })
    }),
    headers: {"Content-Type": "application/json"}
  });
  data.value = await resp.json();
  loading.value = false;
  history.pushState(null, '', `/reply/${data.value!["id"]}`);
};

const isDisabled = computed(() => {
  //TODO: check if email is valid, end is valid date in the future, validate available_times
  return formData.title.length == 0 || formData.end.length == 0 || formData.uploader.email.length == 0 || formData.uploader.name.length == 0 || formData.available_times.length == 0 || formData.uploader.fingerprint.length == 0

})

</script>

<template>
  <template v-if="data">
    <ReplyView :data="data" />
  </template>
  <template v-else>
    <Container @submit="send" v-if="!loading">
      <Title>Create Poll</Title>
      <Input name="Title" type="text" v-model="formData.title" :required="true"/>
      <Input name="Description" type="text" v-model="formData.description" :required="false"/>
      <Input name="Location" type="text" v-model="formData.location" :required="false"/>
      <Input name="End Poll" type="date" v-model="formData.end" :required="true"/>
      <Input name="Username" type="text" v-model="formData.uploader.name" :required="true"/>
      <Input name="Email" type="text" v-model="formData.uploader.email" :required="true"/>
      <Submit :disabled="isDisabled">Share Poll</Submit>
    </Container>
    <Container v-else>
      <div class="w-16 h-16 border-t-4 border-blue-500 border-solid rounded-full animate-spin" />
    </Container>
  </template>
</template>
