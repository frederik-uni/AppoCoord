<template>
  <div class="space-y-4 mt-6">
    <div v-for="(range, index) in ranges" :key="index" class="flex gap-2 items-center">
      <input
        type="datetime-local"
        v-model="range.start"
        class="flex-1 border p-2 w-full px-3 py-2 rounded-md focus:outline-none focus:ring-1 focus:ring-blue-500 border-gray-200 text-gray-600"
        :min="currentMinDate"
      />
      <input
        type="datetime-local"
        v-model="range.end"
        class="flex-1 border p-2 w-full px-3 py-2 rounded-md focus:outline-none focus:ring-1 focus:ring-blue-500 border-gray-200 text-gray-600"
        :min="range.start || currentMinDate"
      />
      <button
        v-if="ranges.length > 1"
        @click="removeRange(index)"
        class="p-2 text-red-500 hover:text-red-700"
      >
        ✕
      </button>
    </div>

    <button
      @click="addRange"
      class="mt-2 px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
    >
      Add Range
    </button>
  </div>
</template>

<script>
import Input from "@/components/Input.vue";

export default {
  components: {Input},
  data() {
    return {
      ranges: [{ start: '', end: '' }],
      currentMinDate: this.getCurrentDateTime()
    };
  },
  computed: {
    availableTimes() {
      const now = new Date().getTime();
      const result = [];

      for (const range of this.ranges) {
        if (!range.start || !range.end) return [];

        const startTime = `${range.start}:00`;
        const endTime = `${range.end}:00`;

        const startDate = new Date(startTime).getTime();
        const endDate = new Date(endTime).getTime();

        if (startDate >= endDate || startDate <= now) return [];

        result.push([startTime, endTime]);
      }

      return result;
    }
  },
  watch: {
    availableTimes(newVal) {
      this.$emit('update:modelValue', newVal);
    }
  },
  emits: ["update:modelValue"],
  methods: {
    addRange() {
      this.ranges.push({ start: '', end: '' });
    },
    removeRange(index) {
      this.ranges.splice(index, 1);
    },
    getCurrentDateTime() {
      const now = new Date();
      const pad = num => String(num).padStart(2, '0');

      return `${now.getFullYear()}-${pad(now.getMonth() + 1)}-${pad(now.getDate())}T${pad(now.getHours())}:${pad(now.getMinutes())}`;
    }
  }
};
</script>
