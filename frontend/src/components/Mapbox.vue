<template>
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href='https://api.mapbox.com/mapbox-gl-js/v2.10.0/mapbox-gl.css' rel='stylesheet'/>
  </head>
  <div>
    <div id="buttons">
      <button class="button rounded-5" @click="mapLoader(null)"><i class="fa-regular fa-chevron-up"></i>No filter
      </button>

      <button class="button rounded-5" @click="onClickFilterUser(this.map.getCenter(),this.map.getZoom())">Contribution
        User
      </button>
      <td>
        <button class="button rounded-5" @click="dropIt">Bucket</button>
        <transition>
          <ul class="list" v-if="isDropped">
            <li @click="mapLoader('Sequester Carbon')">Sequester carbon</li>
            <li @click="mapLoader('Restore Wildlife')">Restore wildlife</li>
            <li @click="mapLoader('Fight Desertification')">Fight desertification</li>
            <li @click="mapLoader('Remove Plastic Pollution')">Remove plastic pollution</li>
          </ul>
        </transition>
      </td>
      <td><input type="text" placeholder="Search" v-model="searchInput"
                 class="rounded-5 button search-bar" @keyup.enter="handleSearch(null)">
        <tr class="search-suggest"
            v-for="contribution in searchContributions" :key="contribution.contributionKey">
          <td v-if="searchingDrop"
              @click="this.flyToContributionNoStore(contribution)">
            Contribution: {{ contribution.contributionKey }}
          </td>
        </tr>
        <tr class="search-suggest"
            v-for="project in searchProjects" :key="project.projectKey">
          <td v-if="searchingDrop" @click="handleSearch(project.name)">{{ project.name }}</td>
        </tr>
      </td>
      <button class="button rounded-5" @click="this.flyToContribution()">Find Contribution</button>

    </div>
    <div class="mapbox-settings">
    </div>
    <div class="mapbox-container">
      <div id='map'>
      </div>
    </div>
  </div>


</template>

<script>
import mapboxgl from "mapbox-gl";
import treeImage from './../assets/images/41406.png';
import graphImage from './../assets/images/graph.png';
import coralImage from './../assets/images/coral.png';
import forestImage from './../assets/images/forest-icon-png-5.png';
import tempImage from './../assets/images/tree.jpg';
import {store} from "@/components/stores/store";


export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Mapbox',
  components: {},
  props: {
    msg: String
  },
  inject: ['contributionService', 'partnerService', 'projectService'],
  data() {
    return {
      geojson: [],
      projects: [],
      map: null,
      colors: ['#E56576', '#60597A', '#856578', '#EAAC88'],
      isDropped: false,
      images: [treeImage, graphImage, forestImage, coralImage],
      long: 0,
      lat: 0,
      searchInput: '',
      searchingDrop: false,
      searchProjects: [],
      searchContributions: []
    }
  },
  async created() {
    this.projects = await this.projectService.getAllProjects();
    this.contributions = await this.contributionService.getAllContributions();
  },
  watch: {
    'searchInput': {
      immediate: false,
      handler: async function () {
        if (this.searchInput.trim() == null || this.searchInput.trim().toString() === '') {
          this.searchProjects = null;
          this.searchContributions = null
          this.searchingDrop = false;
        } else if (this.searchInput.toString() === parseInt(this.searchInput.toString()).toString()) {
          this.searchProjects = null;
          this.searchContributions = null
          this.searchContributions = await this.contributionService.getFilteredDataKeys("contributionKey", this.searchInput)
          this.searchingDrop = true;
        } else if (this.searchInput.toString() !== parseInt(this.searchInput.toString()).toString() && this.searchInput.trim() !== null && this.searchInput.trim() !== "") {
          this.searchProjects = null;
          this.searchContributions = null
          this.searchProjects = await this.projectService.getFilteredDataProjects("name", this.searchInput.trim());
          this.searchingDrop = true;
        }
      }
    }
  },

  async mounted() {
    mapboxgl.accessToken = 'pk.eyJ1Ijoic3VtdGluZyIsImEiOiJja3licjF4NXEwaHc2MnFvODJkOXp5M2ZkIn0.-WciZf0vNJTZcJ2vxueTQg';
    await this.mapLoader(null)
  },
  methods: {
    store() {
      return store
    },
    flyToContribution() {
      this.map.flyTo({
        center: [store.value.long, store.value.lat],
        zoom: 10
      })
    },
    /** mapBox needs you to load in images if you want to display them on the map, that is what this function does.
     *
     * @param image image file
     * @param imageName image name (so that the image can be called in other functions)
     */
    async imageLoader(image, imageName) {
      this.map.loadImage(image, (error, image) => {
        if (error) throw error;
        this.map.addImage(imageName, image);
      });

    },
    /** searchbar helper function that makes sure that a contribution with the same key as the typed in number exists, to prevent bugs.
     *
     * @param contributionNumber the typed in number
     * @returns contribution (if it exists)
     */
    async contributionFinder(contributionNumber) {
      const vari = await this.contributionService.getFilteredDataKeys("contributionKey", contributionNumber)
      for (const contribution of vari) {
        if (contribution.contributionKey.toString() === contributionNumber.toString()) {
          console.log(contribution.contributionKey.toString())
          return contribution;
        }
      }
    },
    /** searchbar helper function that makes sure that a project with the same name (or something similar) as the typed in name exists, to prevent bugs.
     *
     * @param projectName the typed in name
     * @returns Project (if it exists)
     */
    async projectFinder(projectName) {
      const vari = await this.projectService.getFilteredDataProjects("name", projectName);
      for (const project of vari) {
        if (project.name.trim().toLowerCase().includes(projectName.trim().toLowerCase())) {
          return project;
        }
      }
    },
    /**
     * A function that ads to the flytocontribution function, so that it can be used in the searchbar.
     * This is also a handleSearch helper function.
     * @param contribution
     */
    async flyToContributionNoStore(contribution) {
      await this.mapLoader(null);
      store.value.setLong(contribution.longitude);
      store.value.setLat(contribution.latitude);
      await this.flyToContribution();
    }
    ,
    /**
     * A function that handles the search bar. First the function checks if a project name is selected from the dropdown, then if the user typed a number
     * (to look up a specific contribution),
     * if it does, the mapLoads in all the data to ensure the contribution is on screen and then jumps to the given contribution.
     * if not, the method checks if the typed in project name is a project that exists and if it does,
     * the maploader loads in the data for the given project name.
     *
     * @param projectName given project name to load in, meant to be given when somebody clicks on a project name in the dropdown.
     */
    async handleSearch(projectName) {
      if (projectName != null) {
        await this.mapLoader(projectName)
        this.searchInput = '';
      } else if (this.searchInput.trim().toString() === '') {
        await this.mapLoader(null);
        this.searchInput = '';
      } else if (this.searchInput.toString().trim() === parseInt(this.searchInput.toString().trim()).toString()) {
        let contributionTemp = await this.contributionFinder(this.searchInput.toString());
        if (contributionTemp !== null && contributionTemp !== undefined) {
          await this.flyToContributionNoStore(contributionTemp);
          this.searchInput = '';
        }
      } else {
        let projectTemp = await this.projectFinder(this.searchInput.toString().trim())
        if (projectTemp !== null && projectTemp !== undefined) {
          await this.mapLoader(this.searchInput.trim());
          this.searchInput = '';
        }
      }
    }
    ,
    dropIt() {
      this.isDropped = !this.isDropped
    }
    ,
    /**
     * the mapLoader loads the map, data, then lets the geojsonLoader helper function sort it into geojson and then displays it as markers and clusters on the just loaded
     * @param name
     * @returns {Promise<void>}
     */
    async mapLoader(name) {
      this.map = new mapboxgl.Map({
        container: 'map', // container ID
        style: 'mapbox://styles/sumting/ckzgwchr2000116lcvusqd79j', // style URL
        center: [30, 40], // starting position [lng, lat]
        zoom: 2, // starting zoom
      })
      this.map.on('style.load', () => {
        this.map.setFog(); // Set the default atmosphere style
      });
      this.map.on('load', async () => {
        if (name == null) {
          await this.geojsonLoader(await this.projectService.getAllProjects(), await this.contributionService.getAllProjects())
        } else if (name == "Sequester Carbon" || name == "Restore Wildlife" || name == "Fight Desertification" || name == "Remove Plastic Pollution") {
          await this.geojsonLoader(await this.projectService.getFilteredDataProjects('bucket', name), await this.contributionService.getFilteredDataProjects('bucket', name))
        } else {
          await this.geojsonLoader(await this.projectService.getFilteredDataProjects('name', name), await this.contributionService.getFilteredDataProjects('name', name))
        }

        //loads the 4 different icons into the map.
        this.imageLoader(this.images[0], 'Fight Desertification')
        this.imageLoader(this.images[1], 'Sequester Carbon')
        this.imageLoader(this.images[2], 'Restore Wildlife')
        this.imageLoader(this.images[3], 'Remove Plastic Pollution')


        for (let i = 0; i < this.geojson.length; i++) {
          this.map.addSource('places' + i + '', {
            type: 'geojson',
            data: this.geojson[i],
            cluster: true,
            clusterMaxZoom: 2,
            clusterRadius: 50

          });
          let color
          if (i < 4) {
            color = this.colors[i].toString()
          } else {
            color = this.colors[Math.floor(Math.random() * 4)].toString()
          }
          this.map.addLayer({
            id: 'clusters' + i + '',
            type: 'circle',
            source: 'places' + i + '',
            filter: ['has', 'point_count'],
            paint: {
              'circle-color':
              color
              ,
              'circle-radius': [
                'step',
                ['get', 'point_count'],
                20,
                100,
                30,
                750,
                40
              ]
            }
          });

          this.map.addLayer({
            id: 'cluster-count' + i + '',
            type: 'symbol',
            source: 'places' + i + '',
            filter: ['has', 'point_count'],
            layout: {
              'icon-image': "" + this.projects[i].bucket + "",
              'icon-size': 0.15,
              'text-field': '\n\n{point_count_abbreviated}',
              'text-font': ['DIN Offc Pro Medium', 'Arial Unicode MS Bold'],
              'text-size': 12
            }
          });
          this.map.addLayer({
            id: 'unclustered-point' + i + '',
            type: 'circle',
            source: 'places' + i + '',
            filter: ['!', ['has', 'point_count']],
            paint: {
              'circle-color': this.colors[i].toString(),
              'circle-radius': 4,
              'circle-stroke-width': 1,
              'circle-stroke-color': '#fff'
            }
          });


          this.map.on('click', 'clusters' + i + '', (e) => {
            const features = this.map.queryRenderedFeatures(e.point, {
              layers: ['clusters' + i + '']
            });
            const clusterId = features[0].properties.cluster_id;
            this.map.getSource('places' + i + '').getClusterExpansionZoom(
                clusterId,
                (err, zoom) => {
                  if (err) return;

                  this.map.easeTo({
                    center: features[0].geometry.coordinates,
                    zoom: zoom
                  });
                }
            );
          });

          this.map.on('click', 'unclustered-point' + i + '', (e) => {
            const coordinates = e.features[0].geometry.coordinates.slice();
            e.features[0].properties.image = tempImage;
            this.imageLoader(this.map, e.features[0].properties.image, "image" + e.features[0].properties.description)

            while (Math.abs(e.lngLat.lng - coordinates[0]) > 180) {
              coordinates[0] += e.lngLat.lng > coordinates[0] ? 360 : -360;
            }
            new mapboxgl.Popup()
                .setLngLat(coordinates)
                .setHTML(
                    `<h3>${e.features[0].properties.description}</h3><img width="50%" length="50%" id="imageMarker" src="${e.features[0].properties.image}" alt="Project image"><p> deel van ${e.features[0].properties.title} project</p><p> geplant op ${e.features[0].properties.created} </p>`
                )
                .addTo(this.map);
          });


          this.map.on('mouseenter', 'clusters' + i + '', () => {
            this.map.getCanvas().style.cursor = 'pointer';
          });
          this.map.on('mouseleave', 'clusters' + i + '', () => {
            this.map.getCanvas().style.cursor = '';
          });

        }
      })
    }
    ,
    /** geojsonLoader is a helper function for the mapLoader, it makes sure that all the given projects and contributions are properly sorted and turned into geojson
     * so that the mapLoader can easily display all the geojson
     *
     * @param projectsArray the given projects that need to be sorted.
     * @param contributionsArray the given contributions that need to be sorted.
     * @returns this.geojson.
     */
    async geojsonLoader(projectsArray, contributionsArray) {

      this.geojson = []
      for (let i = 0; i < contributionsArray.length; i++) {

        let dataArray = new Array();
        dataArray.push(contributionsArray[i])
        let filteredData = {
          type: 'FeatureCollection',
          features: []
        }
        for (let j = 0; j < 100000; j++) {
          try {
            filteredData.features.push({
              type: 'Feature',
              geometry: {
                type: 'Point',
                coordinates: [dataArray[0][j].longitude, dataArray[0][j].latitude]
              },
              properties: {
                created: dataArray[0][j].createdAt,
                title: projectsArray[i].name,
                description: projectsArray[i].bucket + ' #' + dataArray[0][j].projectKey + "-" + dataArray[0][j].contributionKey
              }
            })
          } catch (e) {
            break;
          }
        }
        this.geojson.push(filteredData)
      }

    }

  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.mapboxgl-popup {
  max-width: 200px;
}

.search-suggest {
  z-index: 1;
  margin: 0;
  padding: 0;
  list-style-type: none;
  transform-origin: top;
  transition: transform .4s ease-in-out;
  background-color: white;
  opacity: 0.9;
  position: relative;
  right: -25%;
}

.list {
  z-index: 1;
  margin: 0;
  padding: 0;
  list-style-type: none;
  transform-origin: top;
  transition: transform .4s ease-in-out;
  background-color: white;
  opacity: 0.9;
  position: relative;
}

li {
  padding: 10px;
  background: white;
  border-bottom: solid thin #eee;
  border-left: solid medium #cbc;

}

#imageMarker {
  width: 5px;
  length: 5px;
}

.mapboxgl-popup.img {
  width: 5px;
}

.mapboxgl-popup-content.img {
  width: 5px;
}

.mapboxgl-popup-content {
  text-align: center;
  font-family: 'Open Sans', sans-serif;
}

.mapbox-container {
  display: flex;
  height: 100%;
  width: 70%;
  margin-left: auto;
}

#map {
  height: 100vh;
  width: 70vw;
}

.mapbox-settings {
  height: 100%;
  width: 30%;
}

#buttons {
  display: flex;
  position: fixed;
  align-content: end;
  z-index: 1;
  left: 30%;
  flex-direction: row;
  max-width: 200px;
}

.button {
  background-color: darkgrey;
  opacity: 0.7;
  color: black;
  border-style: solid;
  border-color: black;
  padding: 3px 22px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 13px;
  margin: 4px 19px;
  cursor: pointer;
  position: relative;
  z-index: 1;
  height: 50px;
}

.search-bar {
  max-width: 150px;
  min-width: 150px;
}

input {
  width: 100%;
  height: 100%;
  border: none;
  border-radius: 4px;
  font-size: 15px;
  opacity: 0.7;
  background-color: darkgrey;
  border-style: solid;
  border-color: black;
  font-weight: 500;
  padding: 0 20px 0 40px;
  box-shadow: 0 0 0 2px rgb(134 140 160 / 2%);
  background-size: 14px;
  background-repeat: no-repeat;
  background-position: 16px 48%;
}


/*@media screen {*/
/*  .mobile-navigation {*/
/*    display: none;*/
/*  }*/
/*}*/

/*@media screen and (max-width: 900px) {*/
/*  nav {*/
/*    display: none;*/
/*  }*/


/*  .card {*/
/*    width: 18rem;*/
/*    margin-bottom: 15px;*/
/*    margin-left: 15px;*/
/*  }*/

/*  .status-text {*/
/*    position: relative;*/
/*    font-weight: bold;*/
/*    top: 16px;*/
/*    right: 75px;*/
/*    font-size: 30px;*/
/*  }*/

/*  .card-text {*/
/*    position: relative;*/
/*    left: 42px;*/
/*    bottom: 28px;*/
/*    font-size: 24px;*/

/*  }*/

/*  .mobile-navigation {*/
/*    justify-content: space-around;*/
/*    display: flex;*/


/*  }*/


/*  .card-body {*/
/*    justify-content: space-between;*/
/*  }*/

/*  .mr-auto {*/
/*    justify-content: space-between;*/
/*    background-color: #f5f5dc;*/
/*  }*/

/*  .mapbox-container {*/
/*    height: 100%;*/
/*    width: 50%;*/
/*    margin-left: auto;*/
/*  }*/

/*  #map {*/
/*    height: 60vh;*/
/*    width: 415%;*/
/*    margin-left: -312px;*/

/*  }*/


/*  .mapboxgl-canvas {*/
/*    position: absolute;*/
/*    width: 430px;*/
/*    height: 538px;*/
/*  }*/

/*  #completed {*/
/*    background-color: #98FB98;*/
/*  }*/

/*  #in-progress {*/
/*    background-color: #ABC7E3;*/

/*  }*/

/*  #planned {*/
/*    background-color: #F1B162;*/
/*  }*/

/*  #planned-1 {*/
/*    background-color: #F1B162;*/
/*  }*/


/*}*/


</style>
