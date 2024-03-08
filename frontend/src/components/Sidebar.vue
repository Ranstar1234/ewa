<template>
  <nav class="sidebar">
    <div class="logo header">
      <img src="../assets/images/Group_203_1.png">

      <ul class="flex" id="CRUD">
        <button type="submit" @click="isHiddenParner = !isHiddenParner" v-if="!isSelected">Create Partner</button>
        <!--        <button type="button" v-on:click="isHidden = false" v-if="isCreate">Create Partner</button>-->
        <button type="button" v-on:click="isHidden = !isHidden" v-if="isEdit">Edit Partner</button>
        <button type="button" v-on:click="isHiddenCreate = !isHiddenCreate" v-if="isEdit">Create Contribution</button>
                <button type="button" @click="isHiddenDelete = !isHiddenDelete, deletePartnerByKey()" v-if="isDelete">Delete Partner</button>

      </ul>
    </div>

    <!--------Create Update for Partner crud to do set requests------>
    <div class="flex" id="form" v-if="!isHidden">
      <form>
        <div class="form-group row" v-for="p in this.partners" v-bind:key="p.key">
          <label for="inputEmail3" class="col-sm-2 col-form-label">Name:</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="inputEmail3" v-model="p.firstname">
          </div>
        </div>
        <div class="form-group row" v-for="p in this.partners" v-bind:key="p.key">
          <label for="inputEmail3" class="col-sm-2 col-form-label">Lastname:</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="lastname" v-model="p.lastname">
          </div>
        </div>
        <div class="form-group row" v-for="p in this.partners" v-bind:key="p.key">
          <label for="inputEmail3" class="col-sm-2 col-form-label">Address:</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="latitude" v-model="p.address">
          </div>
        </div>
        <div class="form-group row" v-for="p in this.partners" v-bind:key="p.key">
          <label for="inputEmail3" class="col-sm-2 col-form-label">Description:</label>
          <select class="drop" v-model="p.description">
            <option disabled selected value></option>
            <option value="Activist">Activist</option>
            <option value="Company">Company</option>
            <option value="Non-profit">Non-profit</option>
          </select>
        </div>
        <div class="form-group row" v-for="p in this.partners" v-bind:key="p.key">
          <label for="inputEmail3" class="col-sm-2 col-form-label">Country:</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="partnerKey" v-model="p.country">
          </div>
        </div>
        <div class="form-group row" v-for="p in this.partners" v-bind:key="p.key">
          <label for="inputEmail3" class="col-sm-2 col-form-label">Email:</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="projectKey" v-model="p.email">
          </div>
        </div>
        <div class="form-group row" v-if="isCreate">
          <div class="col-sm-10">
            <button type="submit" class="btn btn-primary">Create Project</button>
            <button type="reset" v-on:click="isHidden = true" class="btn btn-primary">Cancel</button>
          </div>
        </div>
        <div class="form-group row" v-if="isEdit">
          <div class="col-sm-10">
            <button type="submit" v-for="p in this.partners" v-bind:key="p.key"
                    @click="onSave(p)" class="btn btn-primary topBut">Edit Project
            </button>
            <button type="reset" v-on:click="isHidden = true" class="btn btn-primary topBut">Cancel</button>
          </div>
        </div>
      </form>
    </div>


    <!---CRUD-Form for Contribution entity to do GET requests and put requests---->
    <div class="flex" v-if="!isHiddenContribution">
      <form id="contribution-crud" v-for="c in filteredContribution" v-bind:key="c.contributionKey">
        <div class="form-group row">
          <label for="contribution-key" class="col-sm-2 col-form-label">Contribution key</label>
          <div class="col-sm-10">
            <input type="number" class="form-control" id="contribution-key-CRUD" v-model="c.contributionKey">
          </div>
        </div>
        <div class="form-group row">
          <label for="created-at" class="col-sm-2 col-form-label">Created At</label>
          <div class="col-sm-10">
            <input type="date" class="form-control" id="created-at" v-model="c.createdAt">
          </div>
        </div>
        <div class="form-group row">
          <label for="contribution-image" class="col-sm-2 col-form-label">Contribution Image</label>
          <div class="col-sm-10">
            <input type="image" class="form-control" id="contribution-image" v-model="c.contributionImg">
          </div>
        </div>
        <div class="form-group row">
          <label for="latitude-contribution" class="col-sm-2 col-form-label">Latitude</label>
          <div class="col-sm-10">
            <input type="number" class="form-control" id="latitude-contribution" v-model="c.latitude">
          </div>
        </div>
        <div class="form-group row">
          <label for="longtitude-contribution" class="col-sm-2 col-form-label">Longtitude</label>
          <div class="col-sm-10">
            <input type="number" class="form-control" id="longtitude-contribution" v-model="c.longitude">
          </div>
        </div>
        <div class="form-group row">
          <label for="project-key-contribution" class="col-sm-2 col-form-label">Project Key</label>
          <div class="col-sm-10">
            <input type="number" class="form-control" id="project-key-contribution" v-model="c.projectKey">
          </div>
        </div>

        <div class="form-group row" v-if="!isHiddenParner">
          <label for="project-key" class="col-sm-2 col-form-label">Partner Key</label>
          <div class="col-sm-10">
            <input type="number" class="form-control" placeholder="Partner Key">
          </div>
        </div>
        <button type="button" class="btn btn-primary" v-on:click=" updateContribution(this.filteredContribution)">Save
          changes
        </button>
        <button type="reset" class="btn btn-primary" v-on:click="this.isHiddenContribution = true">Cancel</button>
        <button type="button" v-on:click="deleteContributionByKey(c.key)" v-if="!isHiddenContribution">Delete
          Contribution
        </button>
      </form>
    </div>

    <!--------Create form for contribution crud to do post requests------>
    <div class="flex" v-if="!isHiddenCreate">
      <form id="contribution-crud-create">
        <div class="form-group row">
          <label for="contribution-key" class="col-sm-2 col-form-label">Contribution key</label>
          <div class="col-sm-10">
            <input type="number" class="form-control" id="contribution-key-CRUD-create" v-model="contribution.key"
                   disabled>
          </div>
        </div>
        <div class="form-group row">
          <label for="created-at" class="col-sm-2 col-form-label">Created At</label>
          <div class="col-sm-10">
            <input type="date" class="form-control" id="created-at-create" v-model="contribution.createdAt">
          </div>
        </div>
        <div class="form-group row">
          <label for="contribution-image" class="col-sm-2 col-form-label">Contribution Image</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="contribution-image-create"
                   v-model="contribution.contributionImg">
          </div>
        </div>
        <div class="form-group row">
          <label for="latitude-contribution" class="col-sm-2 col-form-label">Latitude</label>
          <div class="col-sm-10">
            <input type="number" class="form-control" id="latitude-contribution-create" v-model="contribution.latitude">
          </div>
        </div>
        <div class="form-group row">
          <label for="longtitude-contribution" class="col-sm-2 col-form-label">Longtitude</label>
          <div class="col-sm-10">
            <input type="number" class="form-control" id="longtitude-contribution-create"
                   v-model="contribution.longitude">
          </div>
        </div>
        <div class="form-group row">
          <label for="project-key-contribution" class="col-sm-2 col-form-label">Project Key</label>
          <div class="col-sm-10">
            <input type="number" class="form-control" id="project-key-contribution-create"
                   v-model="contribution.projectKey">
          </div>
        </div>

        <div class="form-group row" v-if="!isHiddenParner">
          <label for="project-key" class="col-sm-2 col-form-label">Partner Key</label>
          <div class="col-sm-10">
            <input type="number" class="form-control" id="project-key" placeholder="Partner Key">
          </div>
        </div>
        <button type="button" class="btn btn-primary" v-for="p in this.partners"
                v-on:click="createContribution(contribution)" v-bind:key="p.key">Create Contribution
        </button>
        <button type="reset" class="btn btn-primary" v-on:click="this.isHiddenCreate = true">Cancel</button>
      </form>
    </div>


    <!--------Create form for Partner crud to do post requests------>
    <div class="partner-container d-flex justify-content-between flex-wrap" v-if="createdPartner">
      <form id="partner-form" v-if="!isHiddenParner">
        <div class="form-group row">
          <label for="contribution-key" class="col-sm-2 col-form-label">Name:</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" placeholder="Name" v-model="createdPartner.firstname">
          </div>
        </div>
        <div class="form-group row">
          <label for="contribution-key" class="col-sm-2 col-form-label">Lastname:</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" placeholder="Lastname" v-model="createdPartner.lastname">
          </div>
        </div>
        <div class="form-group row">
          <label for="contribution-key" class="col-sm-2 col-form-label">Email:</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" placeholder="Email" v-model="createdPartner.email">
          </div>
        </div>
        <div class="form-group row">
          <label for="contribution-key" class="col-sm-2 col-form-label">Address:</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" placeholder="Address" v-model="createdPartner.address">
          </div>
        </div>
        <div class="form-group row">
          <label for="contribution-key" class="col-sm-2 col-form-label">Description:</label>
          <div class="col-sm-10">
            <select class="drop" id="parnerdrop" v-model="createdPartner.description">
              <option disabled selected value></option>
              <option value="Activist">Activist</option>
              <option value="Company">Company</option>
              <option value="Non-profit">Non-profit</option>
            </select>
          </div>
        </div>
        <div class="form-group row">
          <label for="contribution-key" class="col-sm-2 col-form-label">zipcode:</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="contribution-key" placeholder="Postcode"
                   v-model="createdPartner.zipcode">
          </div>
        </div>
        <div class="form-group row">
          <label for="contribution-key" class="col-sm-2 col-form-label">Country:</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="contribution-key" placeholder="Country"
                   v-model="createdPartner.country">
          </div>
        </div>
        <div class="col-sm-10">
          <button type="submit" @click="oncreate" class="btn btn-primary">Create Partner</button>
          <button type="reset" v-on:click="isHiddenParner = !isHiddenParner" class="btn btn-primary">Cancel</button>

        </div>
      </form>


      <div class="partner-container d-flex justify-content-between flex-wrap" v-if="isHiddenContribution">
        <ul class="partner btn btn-primary btn-lg btn-block" v-for="p in this.partners" v-bind:key="p.key"
            @click="selectPartner(p)"
            :class="{selected: isSelected}">
          {{ p.description }}<br>{{ p.firstname }} {{ p.lastname }}<br> ID: {{ p.key }}

          <template v-if="p.key == this.selectedPartnerKey && this.isSelected">
            <div class="contribution-container d-flex justify-content-around flex-wrap">
              <li class="contribution" v-for="c in this.contributionsByPartner"
                  :key="c.key"
                  @click.stop="this.selectContribution(c.longitude, c.latitude)">
                <div class="card" style="width: 12rem;">
                  <img class="card-img-top" :src="c.contributionImg" alt="Card image cap">
                  <div class="card-body">
                    <h5 class="card-title bold">{{ projects[c.projectKey - 1].name }}<br>ID: {{ c.projectKey }}-{{
                        c.key
                      }}</h5>
                    <p class="card-text">
                      Bucket: {{ projects[c.projectKey - 1].bucket }}
                      <br>
                      {{ projects[c.projectKey - 1].description }}
                    </p>
                    <button class="btn btn-primary" v-on:click=" selectedContributionByID(c) ">Edit Contribution
                    </button>
                  </div>
                </div>
              </li>
            </div>
          </template>
        </ul>
      </div>
    </div>

  </nav>
</template>

<script>
// eslint-disable-next-line no-unused-vars
import {store} from "@/components/stores/store";
import {Contribution} from "@/models/Contribution";


export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Sidebar",
  computed: {
    store() {
      return store
    },
    /**
     * return filtered contribution by only returning the contribution that matches the contributionKey
     * @returns {*[]} Contribution
     */
    filteredContribution() {
      return this.contributionsByPartner.filter(x => x.contributionKey === this.selectedContributionKey);

    }
  },
  inject: ['contributionService', 'partnerService', 'projectService'],

  data() {
    return {
      longitude: 0,
      latitude: 0,
      contributions: [],
      selectedContribution: null,
      partners: [],
      savedPartners:[],
      partner: 1,
      contribution:
      {
        contributionKey: 0,
        projectKey: 0,
        contributionImg: "",
        longitude: 0,
        latitude: 0,
        createdAt: "",
        partnerKey: 0
      },
      contributionKey: 1,
      selectedContributionKey: 0,
      createdPartner: {
        name: '',
        lastname: '',
        email: '',
        address: '',
        description: '',
        zipcode: '',
        country: '',
      },
      projects: [],
      contributionsByPartner: [],
      isCreate: false,
      isEdit: false,
      isDelete: false,
      isSelected: false,
      isSelectedContribution: false,
      partnerKey: 1,
      selectedPartnerKey: null,
      isHidden: true,
      isHiddenContribution: true,
      isHiddenParner: true,
      isHiddenCreate: true,
      isHiddenDelete: true,
      newPartner: null
    }
  },
  async created() {
    this.savedPartners = await this.partnerService.getAllContributions();
    this.contributions = await this.contributionService.getAllContributions();
    this.partners = this.savedPartners;

  },
  async mounted() {
  },
  methods: {
    selectContribution(long, lat) {
      this.selectedContribution = !this.selectedContribution;
      store.value.setLong(long)
      store.value.setLat(lat);
    },
    /**
     * This method handles the selection of a partner in the sidebar
     * @param partner
     * @returns {Promise<void>}
     */
    async selectPartner(partner) {
      // Sets isSelected to true if it was false, and false if it was true, toggle.
      this.isSelected = !this.isSelected;

      if (this.isSelected == true) {
        this.projects = await this.projectService.getAllProjects();
        // Saves the key of the selected partner and finds all contributions belonging to that partner
        this.selectedPartnerKey = partner.key;
        this.contribution.partnerKey = partner.key;
        this.contributionsByPartner = await this.contributionService.getAllContributionsByPartner(partner.key);

        //Empties the array and then fills it with only the selected item.
        this.partners = [];
        this.partners[0] = partner;
        //Makes these buttons visible when a partner is selected
        this.isDelete = true;
        this.isEdit = true;
        this.isCreate = false;
      } else {
        this.isCreate = true;
        this.isDelete = false;
        this.isEdit = false;
        this.partners = this.savedPartners;
      }
    },

    /**
     * This method handles the selection of one contribution.
     * @param contribution
     * @returns {Promise<{date: string, contributionImg: string, projectKey: number, partnerKey: number, latitude: number, description: string, key: number, longitude: number, status: string}>}
     */
    async selectedContributionByID(contribution) {


      this.isSelectedContribution = !this.isSelectedContribution;

      if (this.isSelectedContribution == true) {

        this.isHiddenContribution = false;
        this.selectedContributionKey = contribution.key;
        this.contribution = await this.contributionService.getContributionByKey(contribution.key);


        this.selectedContribution = contribution;
      }


      return this.contribution;
    },
    async onSave(partners) {
      await this.partnerService.updatePartner(this.partners[0], partners.key);
    },

    async oncreate() {
      await this.partnerService.createPartner(this.createdPartner);
    },

    /**
     * Async method which creates a contribution and adds it to the database by using the contribution service.
     * @param contribution - Contribution to be created by filling in the create contribution form
     * @returns {Promise<void>} Creates the contribution and adds it to the database
     */
    async createContribution(contribution) {
      //Awaiting the contribution service and passing in the createContribution method
      await this.contributionService.createContribution(contribution);
      //Reload to that the user does not have to reload the webpage manually to see the made changes
      window.location.reload();
    },

    /**
     * Async method which updates the made changes of a contribution to the database
     * @param contribution - Contribution you would like to update
     * @returns {Promise<void>} Updates the contribution to the database
     */
    async updateContribution(contribution) {
      //Awaiting the contribution service and passing in the the update contribution method using the copyConstructor method
      //for updating the contribution
      await this.contributionService.updateContribution(Contribution.copyConstructor(contribution)[0]);
      //Reload to that the user does not have to reload the webpage manually to see the made changes
      window.location.reload();

    },

    /**
     * Async delete method which deletes the contribution by the given id.
     * @param contribution - Contribution due to be deleted by the user
     * @returns {Promise<void>} Deletes the contribution from the database
     */
    async deleteContributionByKey(contribution) {
      //Awaiting the contribution service and passing in the deleteContribution method
      await this.contributionService.deleteContribution(contribution);
      //Reload to that the user does not have to reload the webpage manually to see the made changes
      window.location.reload();

    },
    async deletePartnerByKey(){
      for (let i = 0; i < this.contributionsByPartner; i++) {
        await this.deleteContributionByKey(i.key)
      }
      await this.partnerService.deletePartnerByKey(this.selectedPartnerKey)
      window.location.reload();
    }
  }
}
</script>

<style scoped>

nav {
  display: block;
  position: fixed;
  top: 0;
  left: 0;
  height: 100%;
  width: 30%;
  background-color: WHITE;
  font-family: 'Roboto Condensed', sans-serif;
  overflow-x: auto;
  text-align: left;
}

nav .partner {
  margin-left: 0;
  padding-left: 0;
}

.partner {
  color: white;
  margin-bottom: 2vw;
  width: 30%;
  padding: 2%;
  background-color: #e46a6f;
  border-color: #e46a6f;
  min-width: 90px;
}

.partner-container {
  justify-content: space-around;
  padding: 1%;
}

.selected {
  width: 100%
}


nav .dropdown ul.d-menu li a {
  padding: 10px 25px;
  font-size: 12px;
}

.logo img {
  height: 100%;
  width: 50%;
  padding: 3%;
  min-width: 100px;
}

.header {
  background-color: #fdebe3;
  align-content: center;
}

.contribution {
  color: black;
  list-style: none;
  width: auto;
}

.contribution .card {
  width: 40%;
}

.contribution-container {
  background-color: white;
}


#CRUD {
  list-style-type: none;
  justify-content: space-between;
  padding: 0;
}

#CRUD button {
  margin: 0.5vw;
}

.form-control {
  margin-left: 20px;

}

#parnerdrop {
  margin-left: 40px;
}

#form {
  display: block;

}

.row {
  padding: 20px;
}

.topBut {
  margin: 0.5vw;
  background-color: #e46a6f;
  border-color: #e46a6f;
}

.drop {
  width: 50%;
  margin: 0.5vw;
  background-color: #e46a6f;
  border-color: #e46a6f;
}

/*@media screen and (max-width: 900px) {*/
/*  nav {*/
/*    display: none;*/
/*  }*/
/*}*/

/*@media all {*/
/*  .d-menu {*/
/*    display: flex;*/
/*    flex-direction: column;*/
/*  }*/

/*  .d-content {*/
/*    border: solid black 1px;*/
/*    padding: 0;*/
/*    width: 100%;*/
/*  }*/

/*  .d-content a {*/
/*    display: flex;*/
/*    justify-content: space-between;*/
/*    text-align: center;*/
/*    color: #2c3e50;*/
/*  }*/

/*  .d-content p {*/
/*    margin: 0;*/
/*  }*/
/*}*/


</style>