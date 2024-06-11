import {Scooter} from "@/models/scooter"

export class ScootersAdaptor {
    resourcesUrl;

    constructor(resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
        console.log("Created ScootersAdaptor for " + resourcesUrl);
    }

    async fetchJson(url, options = null) {
        let response = await fetch(url, options)
        if (response.ok) {
            return await response.json();
        } else {
            console.log(response, !response.bodyUsed ? await response.text() : "");
            return null;
        }
    }

    fullURL(target, queryParams = null) {
        let url = this.resourcesUrl + target;
        if (queryParams != null) {
            let newUrl = new URL(url);
            newUrl.search = new URLSearchParams(queryParams);
            url = newUrl.toString();
        }
        //console.log(url);
        return url;
    }

    async asyncFindAll() {
        console.log('ScootersAdaptor.asyncFindAll()...');
        const scootersData = await this.fetchJson(this.resourcesUrl);
        return scootersData?.map(Scooter.copyConstructor);
    }

    async asyncFindById(id) {
        console.log('ScootersAdaptor.asyncFindById(' + id + ')...');
        const scootersData = await this.fetchJson(this.resourcesUrl + "/" + id);
        return Scooter.copyConstructor(scootersData);
    }

    async asyncSave(scooter) {
        console.log('ScootersAdaptor.asyncSave(scooter)...');
        if (!scooter.id || scooter.id === 0) {
            const response = await this.fetchJson(this.resourcesUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(scooter),
            });
            return Scooter.copyConstructor(response);
        } else {
            const response = await this.fetchJson(this.resourcesUrl + "/" + scooter.id, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(scooter),
            });
            return Scooter.copyConstructor(response);
        }
    }

    async asyncDeleteById(id) {
        console.log('ScootersAdaptor.asyncDeleteById(' + id + ')...');
        const response = await this.fetchJson(this.resourcesUrl + "/" + id,
            {
                method: 'DELETE'
            });
        if (response && response.status === 204) {
            console.log("Scooter deleted succesfully.");
            return true;
        } else {
            console.log("Error deleting scooter: ", response);
            return false;
        }
    }
}
