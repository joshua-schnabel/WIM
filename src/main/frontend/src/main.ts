import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './style/main.scss'
import 'vue-material-design-icons/styles.css';
import * as bootstrap from 'bootstrap'

const app = createApp(App)

app.use(router)
app.mount('#app')
