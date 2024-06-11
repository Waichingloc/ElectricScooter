<template>
  <div class="container mt-5">
    <div class="form-group">
    <table>
      <thead>
      <tr>
        <th colspan="2" class="scooterOverviewDetailTitle">Scoorter details: id= {{selectedScooter.id}}</th>
      </tr>
      </thead>
      <tbody>
      <tr>
        <td>Tag:</td>
        <td><input type="text" :value="selectedScooter.tag"></td>
      </tr>
      <tr>
        <td>Status:</td>
        <td><select class="form-select" aria-label="Default select example">
          <option selected>{{selectedScooter.status}}</option>
          <option :value="selectedScooter.status.INUSE">INUSE</option>
          <option :value="selectedScooter.status.MAINTENANCE">MAINTENANCE</option>
          <option :value="selectedScooter.status.IDLE">IDLE</option>
        </select></td>
      </tr>
      <tr>
        <td>Battery Charge %:</td>
        <td><input type="text" :value="selectedScooter.batteryCharge"></td>
      </tr>
      <tr>
        <td>GPS Location:</td>
        <td><input type="text" :value="selectedScooter.gpsLocation"></td>
      </tr>
      <tr>
        <td>Milage:</td>
        <td><input type="text" :value="selectedScooter.mileage"></td>
      </tr>
      </tbody>
      <button type="button"
              class="btn btn-danger"
              @click="$emit('delete', selectedScooter.id)">
        Delete
      </button>
    </table>
    </div>
  </div>
</template>

<script>
import {Scooter} from "@/models/scooter.js";
// import ScootersOverview32 from "@/components/scooters/ScootersOverview32.vue";

export default {
  name: "ScootersDetail32",
  props: ['selectedScooter'],
  emits: ['delete'],
  data() {
    return {
      ScooterStatus: Scooter.scooterStatus,
    }
  },
  methods: {
  },
  computed: {

  },
}
</script>

<style>

</style>
