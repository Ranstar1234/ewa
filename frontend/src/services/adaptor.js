import {Project} from "@/models/Project.js";
import {Contribution} from "@/models/Contribution";
import {Partner} from "@/models/Partner";

export class Adaptor {
    resourcesUrl;

    constructor(resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
    }

    async fetchJson(url, options = null) {
        let res = await fetch(url, options);
        if (res.ok) {
            return res.json();
        }

        return null
    }

    async getAllProjects() {
        try {
            const items = await this.fetchJson(this.resourcesUrl + "/sorted", {
                method: 'GET'
            })
            return items?.map(Project.copyConstructor);
        } catch (e) {
            console.log(e)
        }
    }

    /** retrieves all projects sorted and filtered
     *
     * @param filterType what data type the data is being filtered on
     * @param filterVariable the filter variable
     * @returns all filtered sorted projects
     */
    async getFilteredDataProjects(filterType, filterVariable) {
        try {
            const items = await this.fetchJson(this.resourcesUrl + "/filter/" + filterType + "/" + filterVariable, {
                    method: 'GET'
                }
            )
            return items?.map(Project.copyConstructor);
        } catch (e) {
            console.log(e)
        }
    }

    /** retrieves all keys sorted and filtered
     *
     * @param filterType what data type the data is being filtered on
     * @param filterVariable the filter variable
     * @returns all filtered sorted projects
     */
    async getFilteredDataKeys(filterType, filterVariable) {
        try {
            const items = await this.fetchJson(this.resourcesUrl + "/filter/keys/" + filterType + "/" + filterVariable, {
                    method: 'GET'
                }
            )
            return items?.map(Project.copyConstructor);
        } catch (e) {
            console.log(e)
        }
    }

    // TODO Get all Contributions (First create new models if it doesn't exist yet)
    async getAllContributions() {
        try {
            const items = await this.fetchJson(this.resourcesUrl + "/detail", {
                method: 'GET'
            })
            return items?.map(Contribution.copyConstructor);
        } catch (e) {
            console.log(e)
        }
    }

    /**
     * Async method which DELETES the contribution from the database.
     * @param contributionKey - Contribution key of the contribution that is desired to get deleted.
     * @returns {Promise<any|null>} Deletes the contribution from the database.
     * @author Basra
     */
    async deleteContribution(contributionKey) {

        console.log("Deleted contribution with id:" + contributionKey)
        console.log(contributionKey);
        return await this.fetchJson(this.resourcesUrl + "/" + contributionKey, {
            method: "DELETE",
        });


    }

    /**
     * This retrieves all the contributions of a partner based on the ID of the partner
     * @param partnerKey given partner key
     * @returns {Promise<*>} list of all contributions by partner
     *  @author Basra
     */
    async getAllContributionsByPartner(partnerKey) {
        try {

            const items = await this.fetchJson(this.resourcesUrl + "/partner/" + partnerKey + "", {
                method: 'GET'
            })
            return items?.map(Contribution.copyConstructor);
        } catch (e) {
            console.log(e)
        }
    }

    /**
     * Async This method GETS the desired contribution identified by the contributionKey
     * @param contributionKey - Contribution Key of the desired contribution
     * @returns {Promise<Contribution & *>} The specific contribution
     *  @author Basra
     */
    async getContributionByKey(contributionKey) {
        try {
            const contribution = await this.fetchJson(this.resourcesUrl + "/" + contributionKey, {
                method: 'GET'
            })
            return Contribution.copyConstructor(contribution)
        } catch (e) {
            console.log(e)
        }
    }

    // TODO Get all Partners (First create new models if it doesn't exist yet)
    async getAllPartners() {
        try {
            const items = await this.fetchJson(this.resourcesUrl + "/partners/", {
                method: 'GET'
            })
            return items?.map(Partner.copyConstructor);
        } catch (e) {
            console.log(e)
        }
    }


    /**
     * Async method which creates a contribution and POSTING it to the database with a specified body.
     * @param contribution Contribution that is about to be created by using the Create contribution form.
     * @returns {Promise<any|null>} Creates the contribution and adds it to the database
     * @author Basra
     */
    async createContribution(contribution) {
        return await this.fetchJson(this.resourcesUrl + "/create/", {
            method: "POST",
            body: JSON.stringify(contribution),
            headers: {'Content-type': 'application/json; charset=UTF-8'}
        });
    }

    /**
     * Async method which updates a contribution and PUTS it to the databse with the specfied body.
     * @param contribution Contribution that is about to get updated using the edit contribiution form.
     * @returns {Promise<Contribution & *>} Edits the contribution and saves the made changes to the database.
     * @author Basra
     */
    async updateContribution(contribution) {
        console.log(contribution)
        const updatedContribution = await this.fetchJson(this.resourcesUrl + "/" + contribution.contributionKey, {
            method: "PUT",
            body: JSON.stringify(contribution),
            headers: {'Content-type': 'application/json; charset=UTF-8'}
        });
        return Contribution.copyConstructor(updatedContribution)

    }


    async updatePartner(partner, partnerKey) {
        try {
            let items = await this.fetchJson(this.resourcesUrl + "/" + partnerKey + "", {
                method: 'put',
                body: JSON.stringify(partner),
                headers: {'Content-type': 'application/json; charset=UTF-8'}
            })
            return items?.map(Partner.copyConstructor);
        } catch (e) {
            console.log(e)
        }
    }

    async createPartner(partner) {
        console.log('createParner .asyncSave()...');
        try {
            console.log(JSON.stringify(partner))
            console.log("voor items")
            let items = await this.fetchJson(this.resourcesUrl, {
                method: 'POST',
                body: JSON.stringify(partner),
                headers: {'Content-type': 'application/json; charset=UTF-8'}
            })
            return items?.map(Partner.copyConstructor);
        } catch (e) {
            console.log(e)
        }
    }

    async deletePartnerByKey(partnerKey) {
        return await this.fetchJson(this.resourcesUrl + "/" + partnerKey, {
            method: "DELETE",
        });
    }
}