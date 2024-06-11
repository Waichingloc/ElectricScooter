<template>
  <HeaderComponent></HeaderComponent>
  <NavBar></NavBar>
  <RouterView></RouterView>
</template>

<script>
import HeaderComponent from './components/HeaderComponent.vue';
import NavBar from "./components/NavBar.vue";
import CONFIG from '@/app-config';
import {ScootersAdaptor} from "@/services/ScootersAdaptor";
export default {
  name: "AppComponent37",
  components: {
    HeaderComponent,
    NavBar,
  },
  provide() {
    return {
      scootersService: new ScootersAdaptor(CONFIG.BACKEND_URL+"/scooters")
    }
  }
}
</script>

<style>

</style>
