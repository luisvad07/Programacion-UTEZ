<template>
  <div>
    <b-row>
      <b-col >
        <user-registration/>
      </b-col>
      <b-col >
        <user-login/>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <user-auth/>
      </b-col>
    </b-row>
    
  </div>
</template>

<script>
import UserAuth from './components/UserAuth.vue'
import UserLogin from './components/UserLogin.vue'
import UserRegistration from './components/UserRegistration.vue'
export default {
  components: { UserRegistration, UserLogin, UserAuth },

}
</script>

<style>

</style>