<template>
  <div id="app">
    <Navbar v-if="!isAdminRoute" />
    <router-view></router-view>
    <Footer v-if="!isAdminRoute" />
  </div>
</template>

<script>
import Navbar from './components/Navbar.vue'
import Footer from './components/Footer.vue'

export default {
  name: 'App',
  components: {
    Navbar,
    Footer
  },
  computed: {
    isAdminRoute() {
      return this.$route.path.startsWith('/admin');
    }
  }
}
</script>

<style>
/* Global styles */
#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}
</style>
