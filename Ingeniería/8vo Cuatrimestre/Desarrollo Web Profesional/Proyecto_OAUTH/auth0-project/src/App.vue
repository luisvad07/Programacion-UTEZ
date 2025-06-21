<template>
  <div id="app">
    <div id="nav">
      <router-link to="/">Home</router-link>|
      <router-link to="/about">About</router-link>|
      <router-link v-if="$auth.isAuthenticated" to="/profile"
        >Profile</router-link
      >
    </div>
    <router-view />
  </div>
</template>

<script>
export default {

};
</script>

<style>
</style>