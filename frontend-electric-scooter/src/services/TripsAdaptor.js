import {Trip} from "@/models/trip";

export class TripsAdaptor {
    resourcesUrl;

    constructor(resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
        console.log("Created TripsAdaptor for " + resourcesUrl);
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

    async asyncFindAll(queryParams = null) {
        console.log('TripsAdaptor.asyncFindAll()...');
        console.log(this.fullURL("", queryParams));
        const TripsData = await this.fetchJson(this.fullURL("", queryParams));
        return TripsData?.map(Trip.copyConstructor);
    }

    async asyncFindById(id, queryParams = null) {
        console.log('TripsAdaptor.asyncFindById(' + id + ')...');
        const tripsData = await this.fetchJson(this.fullURL("/" + id, queryParams));
        return Trip.copyConstructor(tripsData);
    }

    async asyncSave(trip, queryParams = null) {
        console.log('TripsAdaptor.asyncSave(book)...');
        console.log(trip);
        console.log(queryParams);
        if (!trip.id || trip.id === 0) {
            const response = await this.fetchJson(this.resourcesUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(trip),
            });
            console.log("Post " + response);
            return Trip.copyConstructor(response);
        } else {
            const response = await this.fetchJson(this.fullURL("/" + trip.id, queryParams), {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(trip),
            });
            console.log("Put " + response);
            return Trip.copyConstructor(response);
        }
    }
}
