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
          <li class="nav-item">
            <a class="nav-link" href="#">Mytrips</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" aria-disabled="true">Myaccount</a>
          </li>
        </ul>
        <form class="d-flex">
          <router-link to="/sign-up"
                       class="btn btn-outline-primary">
            Sign up
          </router-link>
          <router-link to="/sign-in"
                       class="btn btn-outline-primary">
            Log in
          </router-link>
        </form>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: 'NavBar'
}
</script>

<style>

</style>
