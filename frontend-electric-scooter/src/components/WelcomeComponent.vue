<template>
  <div class="welcome">
  <div class="left">
    <div>
      <h3>Pick a brand</h3>
      <select class="brand">
        <option value="e-scooter1">scooter1</option>
        <option value="e-scooter2">scooter2</option>
        <option value="e-scooter3">scooter3</option>
      </select>
    </div>
    <div>
      <div>
        <h1>Availability</h1>
        <div>
          <h3>Leidseplein</h3>
          <h3>Rembrandtplein</h3>
          <h3>Centraal station</h3>
        </div>
      </div>
    </div>
  </div>
  <div class="middle">
    <h3>photo gallery</h3>
    <div class="middle-middle">
    <img src="../assets/icons8-scooter-78.png" alt="Hawk logo">
    <img src="../assets/icons8-scooter-78.png" alt="Hawk logo">
    <img src="../assets/icons8-scooter-78.png" alt="Hawk logo">
    <img src="../assets/icons8-scooter-78.png" alt="Hawk logo">
    </div>
  </div>
  <div class="right">
    <h1>City tours</h1>
    <h3>Lorem ipsum dolor sit amet, consectetur adipiscing elit. I
      nteger gravida congue felis, nec luctus leo consectetur at.
      Vestibulum eget purus imperdiet neque porttitor consectetur.
      Sed et eros dictum, aliquet lorem quis, luctus purus. Vestibulum euis
      mod id velit id lacinia. Suspendisse erat neque, consectetur ac ligula nec, condimentum bibendum lorem. Maecenas
      pretium pulvinar eleifend. Duis molestie dolor id purus cursus accumsan. Nulla
      malesuada ligula non tempus consectetur. Etiam nec pretium nunc. Fusce a commodo turpis. Morbi sed nibh
      nunc. Mauris ante urna, luctus eget bibendum sit amet, fringilla at metus. Cra</h3>
  </div>
  </div>
</template>

<script>
export default {
  name: 'WelcomeComponent'
}
</script>

<style>
.welcome{
  display: flex;
  flex-direction: row;
}

.right{
  padding: 10px;
}

.middle{
  display: flex;
  flex-direction: column;
  padding-right: 100px;
  padding: 10px;
}

.left{
  padding: 10px;
}

.middle-middle{
  display: flex;
  flex-direction: row;
}
</style>