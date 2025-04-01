<script setup lang="ts">
import {reactive} from 'vue'
import Container from "@/components/Container.vue";
import Title from "@/components/Title.vue";
import Submit from "@/components/Submit.vue";
import Input from "@/components/Input.vue";

const formData = reactive({
  title: '',
  description: '',
  location: '',
  end: '',
  uploader: {
    name: '',
    email: ''
  },
  available_times: []
})
</script>

<template>
  <Container>
    <Title>Create Poll</Title>
    <Input name="title" type="text" v-model="formData.title" :required="true"/>
    <Input name="description" type="text" v-model="formData.description" :required="false"/>
    <Input name="location" type="text" v-model="formData.location" :required="false"/>
    <Input name="End Poll" type="date" v-model="formData.end" :required="true"/>
    <Input name="Username" type="text" v-model="formData.uploader.name" :required="true"/>
    <Input name="Email" type="text" v-model="formData.uploader.email" :required="true"/>
    <Submit>Share Poll</Submit>
  </Container>
</template>
