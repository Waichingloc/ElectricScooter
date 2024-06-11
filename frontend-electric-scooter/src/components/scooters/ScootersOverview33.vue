<template>
  <div class="container">
    <div class="row">
      <div class="col-md-5">
        <div class="table-responsive">
          <table class="table table-striped table-md">
            <thead>
            <tr>
              <th>Tag:</th>
            </tr>
            </thead>
            <tbody v-for="scooter in scooters" :key="scooter.id">
            <tr @click="setCurrentScooter(scooter.id);  setHighlight(scooter.id);" :class="{selectedActive : selectedScooter === scooter.id}" ref="scooter">
              <td>{{ scooter.tag }}</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
      <div class="col-md-6">
        <router-view
            v-if="selectedScooter"
            @delete="deleteScooter"
            :scooters="scooters">
        </router-view>
        <router-view
            v-else
            class="col-md-6 scooter-overview-detail mt-5">
          Click on a scooter for more details
        </router-view>
      </div>
    </div>
    <div class="">
      <button @:click="onNewScooter();" type="button" class="btn btn-primary table-btn">New Scooter</button>
      <button @click="redirectReload">A</button>
    </div>
  </div>
</template>

<script>
import {Scooter} from "@/models/scooter";
// import ScootersDetail33 from "@/components/scooters/ScootersDetail33.vue";
import {ref} from "vue";

export default {
  name: "ScootersOverview33",
  // components: {ScootersDetail32},
  created() {
    this.lastId = 30000;
    for (let i = 0; i < 8; i++) {
      this.scooters.push(
          Scooter.createSampleScooter(this.nextId()));
    }
  },
  data() {
    return {
      selectedScooter: null,
      scooters: [],
      toggle: false,
      classScooter: "inActive",
    }
  },
  methods: {
    ref,
    setCurrentScooter(id) {
      // if (id != null && id !== this.selectedScooter ) {
      //   this.$router.push(this.$route.matched[0].path + "/" + id.id)
      // } else if (this.selectedScooter != null) {
      //   this.$router.push("/scooters/overview33/")
      // }
      if (this.selectedScooter == null) {
        this.selectedScooter = id;
        this.$router.push("/scooters/overview33/" + id)

      } else if (this.selectedScooter === id) {
        this.selectedScooter = null;
        this.$router.push("/scooters/overview33/")
      } else if (this.selectedScooter != null) {
        this.selectedScooter = id;
        this.$router.push("/scooters/overview33/" + id)
      }

    },
    nextId() {
      const min = 3;
      const max = 10;
      let increment = Math.floor((Math.random() * max) + min);
      return this.lastId += increment;
    },
    onNewScooter() {
      const newScooter = Scooter.createSampleScooter(this.nextId());
      this.scooters.push(newScooter);

      this.selectedScooter = newScooter.id;
    },
    // getCurrentScooter() {
    //   return this.scooters.find((val) => val.id === this.selectedScooter);
    // },

    deleteScooter(scooterId) {
      console.log(scooterId);
      this.scooters = this.scooters.filter((scooter) => scooter.id !== scooterId);

      // it is necessary to set the selected scooter to null. this will not show any more details of the already deleted scooter
      this.selectedScooter = null;
    },
    redirectReload() {
      this.$router
          .push({ path: '/scooters/overview33/' })
          .then(() => { this.$router.go() })
    },
    setHighlight() {

      // const array = this.$refs.scooter;
      //
      // console.log(array);
      //
      // for(let i=0; i < array.length; i++){
      //   if((this.$refs.scooter[i].id) === id){
      //     return console.log(this.$refs.scooter[i]);
      //   }
      //

      /**
       * TODO: highlight the border of the selected scooter
       */
    },
    computed: {}
  }

}
</script>

<style>

</style>
