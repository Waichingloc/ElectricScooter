<template>
  <div class="container mt-5">
    <form @submit.prevent="saveForm">
      <div class="form-group">
        <table>
          <thead>
          <tr>
            <th colspan="2" class="scooterOverviewDetailTitle">Scooter details: id= {{ selectedScooter.id }}</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td>Tag:</td>
            <td><input type="text" v-model="selectedScooter.tag" @keyup.self="change"></td>
          </tr>
          <tr>
            <td>Status:</td>
            <td><select class="form-select" aria-label="Default select example" v-model="selectedScooter.status">
              <option selected>{{ selectedScooter.status }}</option>
              <option value="INUSE">INUSE</option>
              <option value="MAINTENANCE">MAINTENANCE</option>
              <option value="IDLE">IDLE</option>
            </select></td>
          </tr>
          <tr>
            <td>Battery Charge %:</td>
            <td><input type="number" v-model="selectedScooter.batteryCharge"></td>
          </tr>
          <tr>
            <td>GPS Location:</td>
            <td><input type="text" v-model="selectedScooter.gpsLocation" v-if="selectedScooter.status !== 'INUSE'"></td>
          </tr>
          <tr>
            <td>Mileage:</td>
            <td><input type="number" v-model="selectedScooter.mileage"></td>
          </tr>
          </tbody>
        </table>
        <button type="button"
                class="btn btn-danger"
                @click="$emit('delete', selectedScooter.id)">
          Delete
        </button>

        <button type="button"
                :class="{disabled: !hasChanged}"
                class="mx-1 btn btn-primary"
                @click="resetScooterData()">
          Reset
        </button>

        <button type="button"
                class="mx-1 btn btn-primary"
                @click="clearScooterData()">
          Clear
        </button>

        <button type="button"
                class="mx-1 btn btn-primary"
                @click="cancelScooterForm()">
          Cancel
        </button>


        <button :class="{disabled: !hasChanged}" type="submit"
                class="mx-1 btn btn-primary">
          Save
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import {Scooter} from "@/models/scooter";

export default {
  name: "ScootersDetail37",
  inject: ['scootersService'],
  emits: ['delete', 'update-scooter', 'cancel-scooter'],

  created() {
    const scooterId = this.$route.params.scootersId;
    this.selectedScooter = new Scooter();
    this.createCopyScooter(scooterId);
    this.loadScooterDetails(scooterId);
  },

  data() {
    return {
      selectedScooter: null,
      loadedScooter: null,
    }
  },

  methods: {
    async loadScooterDetails(id) {
      return this.scootersService.asyncFindById(id)
          .then(response => {
            console.log('Fetched Scooter Details:', response);
            this.selectedScooter = Scooter.copyConstructor(response);
          })
          .catch(error => {
            console.error("Error fetching scooter details:", error);
          });
    },

    createCopyScooter(id) {
      this.scootersService.asyncFindById(id)
          .then((scooter) => {
            this.loadedScooter = Scooter.copyConstructor(scooter);
          })
          .catch((error) => {
            console.error("Error fetching scooter:", error);
          })
    },

    saveForm() {
      this.scootersService.asyncSave(this.selectedScooter)
          .then((savedScooter) => {
            this.$emit('update-scooter', savedScooter);
          })
          .catch((error) => {
            console.error("Error saving scooter:", error);
          });
    },

    resetScooterData() {
      const scooterId = this.$route.params.scootersId;
      // by making a fresh copy, you will revert any edits that have been made.
      return this.loadScooterDetails(scooterId);
    },

    clearScooterData() {
      this.selectedScooter.tag = "";
      this.selectedScooter.status = "";
      this.selectedScooter.batteryCharge = "";
      this.selectedScooter.gpsLocation = "";
      this.selectedScooter.mileage = "";

    },

    cancelScooterForm() {
      // go to the scooterOverview37.vue page to change the selected scooter assigned value
      this.$emit('cancel-scooter');

    }
  },

  watch: {
    '$route.params.scootersId'(scootersId) {
      this.loadScooterDetails(scootersId)
    },
  },
  computed: {

    hasChanged() {
      if (!this.loadedScooter) return false;

      const stringValueSelectedScooter = String(this.selectedScooter.gpsLocation);
      const stringValueOriginalScooter = String(this.loadedScooter.gpsLocation);

      return (
          this.selectedScooter.tag !== this.loadedScooter.tag ||
          this.selectedScooter.status !== this.loadedScooter.status ||
          this.selectedScooter.batteryCharge !== this.loadedScooter.batteryCharge ||
          stringValueSelectedScooter !== stringValueOriginalScooter ||
          this.selectedScooter.mileage !== this.loadedScooter.mileage
      );
    }
  }

}
</script>

<style>
</style>
