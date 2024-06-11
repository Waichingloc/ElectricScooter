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
                <th>
                  <button @:click="onNewScooter();" type="button" class="btn btn-primary table-btn">New Scooter</button>
                </th>
              </div>
            </div>
            </thead>
            <tbody v-for="scooter in scooters" :key="scooter.id">
            <tr @click="setCurrentScooter(scooter);" :class="{selectedActive : selectedScooter === scooter}"
                ref="scooter">
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

export default {
  name: "ScootersOverview37",
  inject: ['scootersService'],

  async created() {
    this.scooters = await this.scootersService.asyncFindAll();
  },
  data() {
    return {
      selectedScooter: null,
      scooters: [],
      classScooter: false,
    }
  },

  beforeMount() {
    this.$router
        .push({path: '/scooters/overview37'})
  },
  methods: {
    async refreshScooters() {
      this.scooters = await this.scootersService.asyncFindAll();
    },
    setCurrentScooter(scooter) {
      console.log(scooter);
      console.log(this.selectedScooter)
      if (this.selectedScooter !== null && this.selectedScooter === scooter) {
        // If the clicked scooter is already selected, deselect it
        this.selectedScooter = null;
        this.$router.push({path: '/scooters/overview37'});
      } else if (this.selectedScooter !== null) {
        this.selectedScooter = scooter;
        this.$router.push({path: '/scooters/overview37/' + scooter.id});
      } else {
        this.selectedScooter = scooter;
        this.$router.push({path: '/scooters/overview37/' + scooter.id});
      }
    },

    nextId() {
      const min = 3;
      const max = 10;
      let increment = Math.floor((Math.random() * max) + min);
      return this.lastId += increment;
    },
    async onNewScooter() {
      const newScooter = Scooter.createSampleScooter(0);
      await this.scootersService.asyncSave(newScooter)
          .then(savedScooter => {
            this.scooters.push(savedScooter);
            this.selectedScooter = savedScooter;
            this.setCurrentScooter(savedScooter);
          })
          .catch(error => {
            console.error("Error saving new scooter:", error);
          });
      await this.refreshScooters();
    },

    getScooter() {
      return this.selectedScooter;
    },

    async deleteScooter(scooterId) {
      try {
        await this.scootersService.asyncDeleteById(scooterId)
            .then(async () => {
              console.log("Scooter deleted successfully");
              this.scooters = this.scooters.filter((scooter) => scooter.id !== scooterId);

              // it is necessary to set the selected scooter to null. this will not show any more details of the already deleted scooter
              this.selectedScooter = null;

              await this.refreshScooters();

              this.$router
                  .push({path: '/scooters/overview37'})
            })
            .catch((error) => {
              console.error("Error deleting scooter:", error);
            });
      } catch (error) {
        console.error("Error deleting scooter: ", error);
      }
    },

    async updateScooter(updatedScooter) {
      this.scootersService.asyncSave(updatedScooter)
          .then(async savedScooter => {
            const index = this.scooters.findIndex(scooter => scooter.id === savedScooter.id);
            if (index !== -1) {
              this.scooters[index] = updatedScooter;
              this.selectedScooter = null;
              this.$router
                  .push({path: '/scooters/overview37'})
            } else {
              this.scooters = await this.scootersService.asyncFindAll();
            }
          })
          .catch(error => {
            console.error("Error updating scooter: ", error);
          });
      await this.refreshScooters();
    },

    cancelScooter() {
      this.$router
          .push({path: '/scooters/overview37'})


      this.selectedScooter = null;
    },
  },
  watch: {
    '$route.params.scootersId'(id) {
      const selectedScooter = this.scooters.find(scooter => scooter.id === parseInt(id))
      this.selectedScooter = selectedScooter;
    },
  }
}
</script>

<style>

</style>
