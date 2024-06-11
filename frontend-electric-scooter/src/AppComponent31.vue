 <template>
  <HeaderComponent></HeaderComponent>
  <NavBar></NavBar>
  <WelcomeComponent></WelcomeComponent>
  <ScootersOverview31></ScootersOverview31>
   <ScootersOverview32></ScootersOverview32>
</template>

<script>
import HeaderComponent from './components/HeaderComponent.vue';
import NavBar from "./components/NavBar.vue";
import ScootersOverview32 from "@/components/scooters/ScootersOverview32.vue";
import ScootersOverview31 from "@/components/scooters/ScootersOverview31.vue";
import WelcomeComponent from "@/components/WelcomeComponent.vue";


export default {
  name: 'AppComponent31',
  components: {
    ScootersOverview31,
    HeaderComponent,
    ScootersOverview32,
    WelcomeComponent,
    NavBar,
  }
}
</script>

<style>

</style>
