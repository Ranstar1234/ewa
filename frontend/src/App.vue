<template>
  <Sidebar></Sidebar>
  <Mapbox/>
</template>

<script>
import Mapbox from './components/Mapbox.vue'
import Sidebar from "@/components/Sidebar";
import {Adaptor} from "@/services/adaptor";
import CONFIG from "../app-config";

export default {
  name: 'App',
  components: {
    Sidebar,
    Mapbox
  },
  provide() {
    return {
      contributionService: new Adaptor(CONFIG.BACKEND_URL + "/contributions"),
      partnerService: new Adaptor(CONFIG.BACKEND_URL + "/partners"),
      projectService: new Adaptor(CONFIG.BACKEND_URL + "/projects")
    }
  },
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  height: 100%;
  width: 100%;
}
</style>
