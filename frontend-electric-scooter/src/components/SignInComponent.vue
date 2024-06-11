<template>
  <div class="container">
    <div class="row justify-content-center align-items-center mt-5">
      <div class="logInContainer">
        <div class="col-md-12">
          <form v-on:submit.prevent="signIn">
            <div class="input">
              <h3>Please provide your login credentials:</h3>
              <table class="tableContainer">
                <tr>
                  <td>Email:</td>
                  <td><input type="text" class="form-control" v-model="emailInput"></td>
                </tr>
                <tr>
                  <td>Password:</td>
                  <td><input type="password" class="form-control" v-model="passwordInput"></td>
                </tr>
              </table>
            </div>
            <div class="popupButton">
              <button type="submit" @click="signIn" class="btn btn-outline-primary">Sign In</button>
            </div>
            <div class="mt-3">
              <p v-if="failedSignIn" class="failedSignIn">Could not authenticate with provided credentials</p>
            </div>
            <h4 class="mt-3">Current token:</h4>
            <div class="token-box">{{ this.sessionService.currentToken }}</div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SignInComponent',
  inject: ['sessionService'],
  data() {
    return {
      emailInput: '',
      passwordInput: '',
      failedSignIn: false
    }
  },
  created() {
    const signOutParam = this.$route.query.signOut;
    if (signOutParam) {
      this.sessionService.signOut();
    }
  },
  methods: {
    async signIn() {
      this.failedSignIn = false
      try {
        let response = await this.sessionService.asyncSignIn(this.emailInput, this.passwordInput)
        if (response) {
          this.$router.push({path: '/'})
        } else {
          this.failedSignIn = true
        }
      } catch (error) {
        console.log(error)
      }
    }
  }
}
</script>

<style>

</style>
