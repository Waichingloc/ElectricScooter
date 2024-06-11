<template>
  <div class="container">
    <div>
      <h3>All scooters:</h3>
    </div>
    <table class="table">
      <thead>
      <tr>
        <th>Id:</th>
        <th>Tag:</th>
        <th>Status:</th>
        <th>Battery Charge:</th>
        <th>GPSLocation:</th>
        <th>Mileage:</th>
      </tr>
      </thead>
      <tbody v-for="scooter in scooters" :key="scooter.id">
      <tr>
        <td>{{scooter.id}}</td>
        <td>{{scooter.tag}}</td>
        <td>{{scooter.status}}</td>
        <td>{{scooter.batteryCharge}}%</td>
        <td>{{scooter.gpsLocation}}</td>
        <td>{{scooter.mileage}}</td>
      </tr>
      </tbody>
    </table>
  </div>
  <div class="container">
  <div class="table-add-btn">
    <button v-on:click="onNewScooter() " type="button" class="btn btn-primary table-btn">New Scooter</button>
  </div>
  </div>
</template>

<script>
import {Scooter} from "@/models/scooter";

export default {
  name: "ScootersOverview31",
  computed: {
    Scooter() {
      return Scooter
    }
  },
  created() {
    this.lastId = 30000;
    for (let i = 0; i < 8; i++) {
      this.scooters.push(
          Scooter.createSampleScooter(this.nextId()));
    }
  },
  data() {
    return {
      id: null,
      scooters: [],
    }
  },
  methods: {
    nextId() {
      const min = 3;
      const max = 10;
      let increment = Math.floor((Math.random() * max) + min);
      return this.lastId += increment;
    },
    onNewScooter() {
      this.scooters.push(
      Scooter.createSampleScooter(this.nextId()));
    }
  }
}
</script>

<style>

</style>