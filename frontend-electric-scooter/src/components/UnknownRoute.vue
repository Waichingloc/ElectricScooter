<template>
  <h1 class="text-danger">Oeps, an error has occured...</h1>
  <h4 class="text-danger">There is no component available at {{routeName}}</h4>
</template>

<script>
export default {
  name: "UnknownRoute",
  data() {
    return {
      routeName: null
    }
  },
  created () {
    this.routeName = this.$route.fullPath;
  },

}
</script>

<style>

</style>
