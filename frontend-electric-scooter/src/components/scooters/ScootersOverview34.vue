<template>
  <div class="container">
    <div class="row">
      <div class="col-md-6">
        <div class="table-responsive-md">
          <table class="table table-striped table">
            <thead class="container text-center">
            <div class="row justify-content-between">
              <div class="col-4">
                <th>Tag:</th>
              </div>
              <div class="col-3">
                <th><button @:click="onNewScooter();" type="button" class="btn btn-primary table-btn">New Scooter</button></th>
              </div>
            </div>
            </thead>
            <tbody v-for="scooter in scooters" :key="scooter.id">
            <tr @click="setCurrentScooter(scooter.id);" :class="{selectedActive : selectedScooter === scooter.id}" ref="scooter">
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
            @update-scooter="updateScooter"
            @cancel-scooter="cancelScooter"
            :scooters="scooters">
        </router-view>
        <router-view
            v-else
            class="col-md-6 scooter-overview-detail mt-5">
          Click on a scooter for more details
        </router-view>
      </div>
    </div>
  </div>
</template>

<script>
import {Scooter} from "@/models/scooter";
// import ScootersDetail33 from "@/components/scooters/ScootersDetail33.vue";
import {onBeforeMount, ref} from "vue";

export default {
  name: "ScootersOverview34",
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
      classScooter: false,
    }
  },

  beforeMount(){
    this.$router
        .push({ path: '/scooters/overview34' })
  },
  methods: {
    onBeforeMount,
    ref,
    setCurrentScooter(id) {
      // if (id != null && id !== this.selectedScooter ) {
      //   this.$router.push(this.$route.matched[0].path + "/" + id.id)
      // } else if (this.selectedScooter != null) {
      //   this.$router.push("/scooters/overview33/")
      // }
      if (this.selectedScooter == null) {
        this.selectedScooter = id;
        this.$router.push("/scooters/overview34/" + id)

      } else if (this.selectedScooter === id) {
        this.selectedScooter = null;
        this.$router.push("/scooters/overview34")
      } else if (this.selectedScooter != null) {
        this.selectedScooter = id;
        this.$router.push("/scooters/overview34/" + id)
      }

      this.classScooter = true;

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
      this.setCurrentScooter(newScooter.id);
    },

    getScooter(){
      return this.selectedScooter;
    },

    deleteScooter(scooterId) {
      this.scooters = this.scooters.filter((scooter) => scooter.id !== scooterId);


      // it is necessary to set the selected scooter to null. this will not show any more details of the already deleted scooter
      this.selectedScooter = null;

      this.$router
          .push({path: '/scooters/overview34'})
    },

    updateScooter(updatedScooter) {
      console.log(updatedScooter)
      const index = this.scooters.findIndex(scooter => scooter.id === updatedScooter.id);

      if (index !== -1) {
        console.log(index);
        // Update the item at the found index
        this.scooters[index] = updatedScooter;
      }

      this.selectedScooter = null;

      this.$router
          .push({ path: '/scooters/overview34' })
    },

    cancelScooter() {
      this.$router
          .push({ path: '/scooters/overview34' })


      this.selectedScooter = null;
    }
  }
}
</script>

<style>

</style>
