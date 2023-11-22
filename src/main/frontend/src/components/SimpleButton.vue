<template>
    <button type="button" class="btn btn-secondary" :disabled="working || disabled" @click="emit('click')">
        {{ text }}
        <Loading class="loading-animation" v-if="working" />
    </button>
</template>

<script lang="ts" setup>
import Loading from 'vue-material-design-icons/Loading.vue';

defineProps({
    text: {
        type: String,
        required: true
    },
    disabled: {
        type: Boolean,
        default: false
    },
    working: {
        type: Boolean,
        default: false
    }
})

const emit = defineEmits(['click'])

</script>

<style lang="scss" scoped></style>