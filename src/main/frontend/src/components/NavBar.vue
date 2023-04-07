<template>
    <nav class="navbar navbar-expand-md navbar-light fixed-top bg-light fw-bold">
        <div class="container-fluid">
            <a class="navbar-brand lovingu ps-4" href="#">Jule & Joshua</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <router-link class="nav-link" :class="{ active: currentRouteName === 'home' }" aria-current="page" :to="{ name: 'home'}">Home</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link class="nav-link" :class="{ active: currentRouteName === 'guests' }" aria-current="page" :to="{ name: 'guests'}">Gästebereich</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link class="nav-link" :class="{ active: currentRouteName === 'admin' }" aria-current="page" :to="{ name: 'admin'}">Administration</router-link>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</template>

<script lang="ts" setup>
import { RouterLink, useRouter  } from 'vue-router'
import { computed } from 'vue'

const currentRouteName = computed(() => {
    return useRouter().currentRoute.value.name;
})

</script>

<style lang="scss" scoped>
.navbar {
    box-shadow: 0 0 15px 0px #555;
}
</style>
  