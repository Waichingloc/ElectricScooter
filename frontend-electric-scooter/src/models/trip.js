export class Trip {
    id;
    startTime
    endTime
    startLocation
    endLocation
    mileage
    cost
    scooter
    constructor(id) {
        this.id = id;
        this.startTime = this.generateStartTime();
        this.endTime = this.generateEndTime();
        this.startLocation = this.generateGpsLocation();
        this.endLocation = this.generateGpsLocation();
        this.mileage = this.generateMileage();
        this.cost = this.generateCost();
        this.scooter = null;
    }

    static copyConstructor(trip) {
        if(trip === null || trip === undefined) return null;
        return Object.assign(new Trip(0), trip);
    }

    static scooterStatus = {
        IDLE: "IDLE",
        INUSE: "INUSE",
        MAINTENANCE: "MAINTENANCE"
    };

    generateStartTime() {
        const startDate = new Date(2022, 0, 1);
        const endDate = new Date(2023, 11, 31);
        const randomDays = Math.floor(startDate.getTime() + Math.random() * (endDate.getTime() - startDate.getTime() + 1));
        const randomDate = new Date(randomDays);

        const randomHour = Math.floor(Math.random() * 15);
        const randomMinute = Math.floor(Math.random() * 60);
        const randomSecond = Math.floor(Math.random() * 60);

        randomDate.setHours(randomHour, randomMinute, randomSecond);

        return randomDate;
    }

    generateEndTime() {
        const startTime = this.startTime;
        const randomHour = startTime.getHours() + Math.floor(Math.random() * 10);
        const randomMinute = Math.floor(Math.random() * 60);
        const randomSecond = Math.floor(Math.random() * 60);

        const endTime = new Date(startTime.getTime());
        endTime.setHours(randomHour, randomMinute, randomSecond);

        if (endTime < startTime) {
            endTime.setHours(endTime.getHours() + 10);
        }

        return endTime;
    }

    generateLatCoord() {
        const min = 523680;
        const max = 524312;
        const divide = 10000;
        const diff = Math.floor(Math.random() * (max - min)) + min / divide;
        return diff;
    }

    generateLonCoord() {
        const min = 48055;
        const max = 50686;
        const divide = 10000;
        const diff = Math.floor(Math.random() * (max - min)) + min / divide;
        return diff;
    }

    generateGpsLocation() {
        const latCoord = this.generateLatCoord();
        const lonCoord = this.generateLonCoord();
        return latCoord + "°N, " + lonCoord + "°E";
    }

    generateMileage() {
        const randomMileage = Math.random() * 50;
        return Math.round(randomMileage * 100) / 100;
    }

    generateCost() {
        const randomCost = Math.random() * 5;
        return Math.round(randomCost * 100) / 100;
    }

    getScooter() {
        return this.scooter;
    }
}
