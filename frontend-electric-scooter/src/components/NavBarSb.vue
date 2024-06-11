<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container">
      <router-link to="/"
                   active-class="active"
                   class="navbar-brand">
        Electric scooter
      </router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarScroll"
              aria-controls="navbarScroll" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarScroll">
        <ul class="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll" style="--bs-scroll-height: 100px;">
          <li class="nav-item">
            <router-link to="/home"
                         active-class="active"
                         class="nav-link active"
                         aria-current="page">
              Home
            </router-link>
          </li>
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              Scooter
            </a>
            <ul class="dropdown-menu">
              <li>
                <router-link to="/scooters/overview31"
                             active-class="active"
                             class="dropdown-item">
                  Scooter overview
                </router-link>
              </li>
              <li>
                <router-link to="/scooters/overview32"
                             active-class="active"
                             class="dropdown-item">
                  Scooter details
                </router-link>
              </li>
              <li>
                <router-link to="/scooters/overview33"
                             active-class="active"
                             class="dropdown-item">
                  Scooters edit (router)
                </router-link>
              </li>
              <li>
                <router-link to="/scooters/overview34"
                             active-class="active"
                             class="dropdown-item">
                  Scooters edit (managed)
                </router-link>
              </li>
              <li>
                <router-link to="/scooters/overview37"
                             active-class="active"
                             class="dropdown-item">
                  All Scooters (REST-backend)
                </router-link>
              </li>
              <li><a class="dropdown-item" href="#">Available Scooter</a></li>
              <li><a class="dropdown-item" href="#">Abandoned Scooter</a></li>
            </ul>
          </li>
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              MyTrips
            </a>
            <ul class="dropdown-menu">
              <li>
                <router-link to="/trips/overview45"
                             active-class="active"
                             class="dropdown-item">
                  history
                </router-link>
              </li>
              <li>
                <router-link to="/test"
                             active-class="active"
                             class="dropdown-item">
                  availability map
                </router-link>
              </li>
            </ul>
          </li>

          <li class="nav-item">
            <a class="nav-link" aria-disabled="true">Myaccount</a>
          </li>
        </ul>
        <router-link to="/sign-up"
                     class="btn btn-outline-primary"
                     v-if="!isAuthenticated">
          Sign up
        </router-link>
        <router-link to="/sign-in"
                     class="btn btn-outline-primary"
                     v-if="!isAuthenticated">
          Log in
        </router-link>
        <!--        <button @click="signUp" class="btn btn-outline-primary">Sign up</button>-->
        <!--        <button v-if="!isAuthenticated" @click="signIn" class="btn btn-outline-primary">Log in</button>-->
        <!--        <button v-else @click="signOut" class="btn btn-outline-primary">Log out</button>-->
        <router-link to="/sign-out"
                     class="btn btn-outline-primary"
                     @click="signOut"
                     v-else>
          Log out
        </router-link>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: 'NavBarSb',
  inject: ['sessionService'],
  methods: {
    // signUp() {
    //   this.$router.push({path: '/sign-up'})
    // },
    // signIn() {
    //   this.$router.push({path: '/sign-in'})
    // },
    signOut() {
      this.sessionService.signOut()
      // this.$router.push({path: '/'})
    }
  },
  computed: {
    isAuthenticated() {
      return this.sessionService.isAuthenticated()
    }
  }
}
</script>

<style>

</style>
