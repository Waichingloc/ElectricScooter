<template>
  <HeaderSbComponent></HeaderSbComponent>
  <NavBarSb></NavBarSb>
  <RouterView></RouterView>
</template>

<script>
import HeaderSbComponent from "@/components/HeaderSbComponent.vue";
import NavBarSb from "@/components/NavBarSb.vue";
import CONFIG from '@/app-config';
import {ScootersAdaptor} from "@/services/ScootersAdaptor";
import {shallowReactive} from "vue";
import {SessionSbService} from "@/services/SessionSbService";
import {FetchInterceptor} from "@/services/FetchInterceptor";
import {TripsAdaptor} from "@/services/TripsAdaptor";

export default {
  name: "AppComponent44",
  components: {
    HeaderSbComponent,
    NavBarSb
  },
  provide() {
    this.theSessionService = shallowReactive(
        new SessionSbService(CONFIG.BACKEND_URL + "/authentication", CONFIG.JWT_STORAGE_ITEM));
    this.FetchInterceptor =
        new FetchInterceptor(this.theSessionService, this.$router);
    return {
      scootersService: new ScootersAdaptor(CONFIG.BACKEND_URL+"/scooters"),
      tripsService: new TripsAdaptor(CONFIG.BACKEND_URL+"/trips"),
      sessionService: this.theSessionService,
    }
  },
  unmounted() {
    this.FetchInterceptor.unregister();
  }
}
</script>

<style>

</style>
