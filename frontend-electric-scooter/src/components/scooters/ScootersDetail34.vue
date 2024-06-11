<template>
  <div class="container mt-5">
    <form @submit.prevent="saveForm">
      <div class="form-group">
        <table>
          <thead>
          <tr>
            <th colspan="2" class="scooterOverviewDetailTitle">Scoorter details: id= {{ selectedScooter.id }}</th>
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
            <td><input type="text" v-model="selectedScooter.gpsLocation"></td>
          </tr>
          <tr>
            <td>Milage:</td>
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
//import {Scooter} from "@/models/scooter.js";
// import ScootersOverview32 from "@/components/scooters/ScootersOverview32.vue";

import {Scooter} from "@/models/scooter";

export default {
  name: "ScootersDetail34",
  props: ['scooters'],
  emits: ['delete', 'update-scooter', 'cancel-scooter'],

  created() {
    const scooterId = this.$route.params.scootersId;

    this.createCopyScooter(scooterId);
  },

  data() {
    return {
      selectedScooter: null,
    }
  },

  methods: {
    getScooter(id) {
      return this.scooters.find((scooter) => parseInt(scooter.id) === parseInt(id));
    },

    createCopyScooter(id) {
      const scooter = this.getScooter(id);

      this.selectedScooter = Scooter.copyConstructor(scooter)//copy which is changeable by v-mode
      return this.selectedScooter;
    },

    saveForm() {
      this.$emit('update-scooter', this.selectedScooter);

    },

    resetScooterData() {
      const scooterId = this.$route.params.scootersId;
      // by making a fresh copy, you will revert any edits that have been made.
      return this.createCopyScooter(scooterId);
    },

    clearScooterData() {
      this.selectedScooter.tag = "";
      this.selectedScooter.status = "";
      this.selectedScooter.batteryCharge = "";
      this.selectedScooter.gpsLocation = "";
      this.selectedScooter.mileage = "";

    },

    cancelScooterForm() {
      // go to the scooterOverview34.vue page to change the selected scooter assigned value
      this.$emit('cancel-scooter');

    }
  },

  watch: {
    '$route.params.scootersId'(scootersId) {

      this.createCopyScooter(scootersId);
    },
  },
  computed: {

    hasChanged() {

      // Check if there are changes by comparing the properties of the original item with the edited item
      //TODO: ^^
      const scooterId = this.$route.params.scootersId;
      let scooter = this.getScooter(scooterId);

      let stringValueSelectedScooter = String(this.selectedScooter.gpsLocation);
      let stringValueOriginalScooter = String(scooter.gpsLocation);

      switch (this.selectedScooter !== scooter) {
        case (this.selectedScooter.tag !== scooter.tag):
        case (this.selectedScooter.status !== scooter.status):
        case (this.selectedScooter.batteryCharge !== scooter.batteryCharge):
        case (stringValueSelectedScooter !== stringValueOriginalScooter):
        case (this.selectedScooter.mileage !== scooter.mileage):
          return true;
        default:
          return false;
      }
    }
  }

}
</script>

<style>

</style>
