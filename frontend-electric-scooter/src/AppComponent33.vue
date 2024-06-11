<template>
  <HeaderComponent></HeaderComponent>
  <NavBar></NavBar>
  <RouterView></RouterView>
<!--  <WelcomeComponent></WelcomeComponent>-->
<!--  <ScootersOverview31></ScootersOverview31>-->
<!--  <ScootersOverview32></ScootersOverview32>-->
</template>

<script>
import HeaderComponent from './components/HeaderComponent.vue';
import NavBar from "./components/NavBar.vue";
// import WelcomeComponent from "@/components/WelcomeComponent.vue";
// import ScootersOverview31 from "@/components/scooters/ScootersOverview31.vue";
// import ScootersOverview32 from "@/components/scooters/ScootersOverview32.vue";

export default {
  name: "AppComponent33",
  components: {
    HeaderComponent,
    NavBar,
    // WelcomeComponent,
    // ScootersOverview31,
    // ScootersOverview32,
  }
}
</script>

<style>

</style>
