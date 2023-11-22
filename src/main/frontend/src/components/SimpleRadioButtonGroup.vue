<template>
    <div class="btn-group" role="group" aria-label="Basic radio toggle button group" v-for="(option, index) in options"
        :key="index">
        <input type="radio" class="btn-check" :name="id + '-' + index" :value="option.value" :id="id + '-' + index"
            autocomplete="off" v-model="value">
        <label class="btn btn-outline-primary" :for="id + '-' + index">{{ option.text }}</label>
    </div>
</template>

<script lang="ts" setup>
import { computed, ref } from 'vue';

var props = defineProps({
    modelValue: {
        required: true
    },
    options: {
        type: Array<{ text: String, value: any }>,
        default: []
    }
})

const emit = defineEmits(['update:modelValue'])

var id = ref("srbg-"+crypto.randomUUID());

const value = computed({
    get() {
        return props.modelValue
    },
    set(value) {
        emit('update:modelValue', value)
    }
})
</script>

<style lang="scss" scoped></style>