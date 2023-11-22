<!-- eslint-disable vue/no-unused-vars -->
<template>
  <VErrorBoundary :on-error="handleError">
    <template #boundary="{ hasError }">
      <NavBar />
      <main class="flex-shrink-0">
        <div class="container">
          <RouterView v-slot="{ Component }">
            <component ref="view" :is="Component" />
          </RouterView>
        </div>
      </main>
      <footer class="footer mt-auto py-3 bg-light">
        <div class="container justify-content-center">
          <div class="col-auto text-muted text-center">
            <img src="@/assets/images/zweig-l.png" alt="" class="deko" />
            <span class="text-space"><a href="https://jule-und-joshua.de/">jule-und-joshua.de</a></span>
            <img src="@/assets/images/zweig-r.png" alt="" class="deko" />
          </div>
        </div>
      </footer>
    </template>
  </VErrorBoundary>
</template>

<script setup lang="ts">
import VErrorBoundary from "vue-error-boundary"
import { RouterView } from 'vue-router'
import NavBar from './components/NavBar.vue'
import { ref } from 'vue';
import { ServerError, APIError, AuthError } from './services/Axios';
var view = ref();

function handleError(error: Error, _vm: any, _info: string) {
  if (error instanceof ServerError || error instanceof APIError) {
    view.value.serverError(error);
    return;
  } else if (error instanceof AuthError) {
    view.value.authError(error);
    return;
  }
  throw error;
}

</script>

<style lang="scss">
main {
  padding-bottom: 24vh;
}

.container {
  padding-top: 5rem;
}

.main-content {
  background-color: $white;
  background: url('@/assets/images/wimpel.png') right top/50% no-repeat, url('@/assets/images/background-1.jpg') right top repeat;
  box-shadow: $default-shadow-wim;
}


.main-content {
  padding: 3rem !important;
}


@include media-breakpoint-down(md) {
  .main-content {
    padding-top: 3rem !important;
    padding-bottom: 3rem !important;
    padding-left: 2rem !important;
    padding-right: 2rem !important;
  }
}

@include media-breakpoint-down(sm) {
  .main-content {
    padding-top: 3rem !important;
    padding-bottom: 3rem !important;
    padding-left: 1rem !important;
    padding-right: 1rem !important;
  }
}

@keyframes loading {
  0% {
    transform: rotate(0deg);
  }

  100% {
    transform: rotate(360deg);
  }
}

.loading-animation>.material-design-icon__svg {
  animation: loading 1.2s linear infinite;
}

@media print {
  footer {
    display: none;
  }

  .navbar {
    display: none;
  }

  body * {
    visibility: hidden;
  }

  html,
  body {
    width: 210mm;
    height: 297mm;
  }
}

@page {
  size: A4;
  margin-top: 9mm;
  margin-bottom: 1mm;
  margin-left: 2mm;
  margin-right: 6mm;
}
</style>
