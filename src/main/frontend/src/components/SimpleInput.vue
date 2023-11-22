<template>
    <div>
        <input class="form-control" :class="{ 'is-invalid': error || regexError }" :type="type" :placeholder="placeholder"
            v-model="value" :id="id" :aria-describedby="id + '-feedback'">
        <div class="invalid-feedback" :id="id + '-feedback'">
            {{ errorText }}
        </div>
    </div>
</template>

<script lang="ts" setup>
import { computed, ref } from 'vue';

var props = defineProps({
    modelValue: {
        type: String,
        required: true
    },
    type: {
        type: String,
        default: "text"
    },
    placeholder: {
        type: String,
        default: ""
    },
    error: {
        type: Boolean,
        default: false
    },
    regex: {
        type: String,
        default: ".*"
    },
    testRegex: {
        type: Boolean,
        default: true
    },
    errorText: {
        type: String,
        default: ""
    }
})

const emit = defineEmits(['update:modelValue', 'regexStatus'])

var id = ref("si-" + crypto.randomUUID());

var regexError = computed(() => {
    if (!props.testRegex)
        return false;
    var status = !(new RegExp("^" + props.regex + "$")).test(value.value);
    emit('regexStatus', status)
    return status;
});

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