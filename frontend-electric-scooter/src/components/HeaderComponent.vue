<template>
  <div class="header">
    <div class="container text-center">
      <div class="row">
        <div class="col-1 col-ms-6">
          <img src="../assets/icons8-hawk-64.png" class="rounded float-start" alt="Hawk logo">
        </div>
        <div class="col-3">
          <div class>today is</div>
          <div class="header-date">{{ getCurrentDate() }}</div>
        </div>
        <div class="col-4 text-center">
          <h2 class="header-title">E-scooters Amsterdam</h2>
        </div>
        <div class="col-3">
          <span class="header-slogan">Slogan in the make (pls dont complain)</span>
        </div>
        <div class="col-1">
          <img src="../assets/icons8-scooter-78.png" class="rounded float-end" alt="scooter">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HeaderComponent',
  methods: {
    getCurrentDate() {
      // let date = new Date().toLocaleString().slice(5, 16);
      // return date.toLocaleString();
      const date = new Date();

      const options = {
        weekday: 'long',
        year: 'numeric',
        month: 'long',
        day: 'numeric',
      };

      // const result = Math.random().toString(36).substring(2,10);
      // console.log(result);
      console.log(date.toLocaleString('en-EN', options));
      return date.toLocaleString('en-EN', options);
      // return date.toLocaleString();
    }

  }
}
</script>

<style>


</style>
