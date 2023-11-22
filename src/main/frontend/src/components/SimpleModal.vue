<template>
    <div :id="id" class="modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">{{ title }}</h5>
                    <button v-if="closeable" type="button" class="btn-close" @click="close()"></button>
                </div>
                <div class="modal-body">
                    {{ text }}
                </div>
                <div class="modal-footer">
                    <SimpleButton text="Schließen" @click="close()" />
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { Modal } from 'bootstrap';
import { onMounted, ref, type Ref } from 'vue';

defineProps({
    title: {
        type: String,
        required: true
    },
    text: {
        type: String,
        required: true
    },
    closeable: {
        type: Boolean,
        default: false
    }
})

defineExpose({ show })

const emit = defineEmits(['close'])

var modal: Modal;
var id = ref("sm-"+crypto.randomUUID());

onMounted(() => {
    modal = new Modal('#' + id.value, { backdrop: true });
})

function show() {
    modal.show();
}

function close() {
    modal.hide();
    emit("close");
}
</script>

<style lang="scss" scoped></style>