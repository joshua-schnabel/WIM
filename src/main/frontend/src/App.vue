<script setup lang="ts">
import { RouterView } from 'vue-router'
import NavBar from './components/NavBar.vue'
</script>

<template>
  <NavBar />
  <main class="flex-shrink-0">
    <div class="container">
      <RouterView />
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
</style>
