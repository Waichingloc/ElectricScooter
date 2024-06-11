<template>
  <div class="container">
    <div class="row">
      <div class="col">
        <h3>Full history of my trips</h3>
        <small>(click an active trip to finish the ride)</small>
      </div>
    </div>
    <table class="table">
      <thead>
      <tr>
        <th>Id:</th>
        <th>ScooterTag:</th>
        <th>Start Date/Time:</th>
        <th>Running</th>
        <th>Start Location</th>
        <th>End Location</th>
        <th>Total mileage</th>
      </tr>
      </thead>
      <tbody v-for="trip in trips" :key="trip.id">
      <tr>
        <td>{{ trip.id }}</td>
        <td>{{ trip.scooter.tag }}</td>
        <td>{{ trip.start }}</td>
        <td>{{ trip.active }}</td>
        <td>{{ trip.startLocation }}</td>
        <td v-if="trip.active === false">{{ trip.endLocation }}</td>
        <td v-else></td>
        <td v-if="trip.active === false">{{ trip.mileage }}</td>
        <td v-else>
          <button class="btn btn-primary" @click="finishTrip(trip)">Finish</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>

// import {Trip} from "@/models/trip";

export default {
  name: "TripsOverview45",
  inject: ['tripsService', 'scootersService'],
  async created() {
    try {
      this.trips = await this.tripsService.asyncFindAll();
      console.log(this.trips)
    } catch (error) {
      console.error("Error fetching trips: ", error);
    }
  },
  data() {
    return {
      trips: [],
    }
  },
  methods: {
    async refreshTrips() {
      try {
        this.trips = await this.tripsService.asyncFindAll();
      } catch (error) {
        console.error("Error refreshing trips: ", error);
      }
    },


    async finishTrip(updatedTrip) {
      try {
        this.tripsService.asyncSave(updatedTrip, {'finish': true})
            .then(async updatedTrip => {
              const index = this.trips.findIndex(trip => trip.id === updatedTrip.id);
              if (index !== -1) {
                this.trips[index] = updatedTrip;
                this.trips = await this.tripsService.asyncFindAll();
              } else {
                this.trips = await this.tripsService.asyncFindAll();
              }
            })
            .catch(error => {
              console.error("Error updating scooter: ", error);
            });
            await this.refreshTrips();
      } catch (error) {
        console.error("Error updating scooter: ", error);
      }
    },
  }
}
</script>

<style>

</style>
