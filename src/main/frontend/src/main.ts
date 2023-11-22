import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './style/main.scss'
import 'vue-material-design-icons/styles.css';
import 'leaflet/dist/leaflet.css';
import { createPopper } from '@popperjs/core';
import * as bootstrap from 'bootstrap'
import * as leaflet from 'leaflet'
import SimpleButton from "@/components/SimpleButton.vue";
import SimpleModal from "@/components/SimpleModal.vue";
import SimpleInput from "@/components/SimpleInput.vue";
import SimpleRadioButtonGroup from "@/components/SimpleRadioButtonGroup.vue";
import VErrorBoundary from 'vue-error-boundary';

const app = createApp(App)
app.use(router)
app.mount('#app')
app.component('SimpleButton', SimpleButton);
app.component('SimpleModal', SimpleModal);
app.component('SimpleInput', SimpleInput);
app.component('SimpleRadioButtonGroup', SimpleRadioButtonGroup);
app.component('VErrorBoundary', VErrorBoundary);