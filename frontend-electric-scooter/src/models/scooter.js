export class Scooter {
    id;
    tag;
    status;
    gpsLocation;
    mileage;
    batteryCharge;

    MAX_MILEAGE = 10000;
    MIN = 1;
    MAX_CHARGE = 100;
    MIN_CHARGE = 5;

    constructor(id) {
        this.id = id;
        this.tag = this.makeTag(8);
        this.status = this.generateRandomStatus(Scooter.scooterStatus);
        this.gpsLocation = this.generateGpsLocation();
        this.mileage = Math.floor((Math.random() * this.MAX_MILEAGE) + this.MIN);
        this.batteryCharge = Math.floor((Math.random() * (this.MAX_CHARGE-this.MIN_CHARGE)) + this.MIN_CHARGE);
    }

    static copyConstructor(scooter) {
        if(scooter === null || scooter === undefined) return null;
        return Object.assign(new Scooter(0), scooter);
    }

    static scooterStatus = {
        IDLE: "IDLE",
        INUSE: "INUSE",
        MAINTENANCE: "MAINTENANCE"
    };

    generateRandomStatus(obj) {
        let randomProp = obj;
        if (randomProp) {
            const stat = Math.floor(Math.random() * Object.keys(randomProp).length);
            const keys = Object.keys(randomProp);
            return Scooter.scooterStatus[keys[stat]];
        }
    }

    generateLatCoord(){
        let min = 523680;
        let max = 524312;
        const divide = 10000;
        let diff = Math.floor((Math.random() * (max - min)) + min) / divide;
        return diff;
    }

    generateLonCoord() {
        let min = 48055;
        let max = 50686;
        const divide = 10000;
        let diff = Math.floor((Math.random() * (max - min)) + min) / divide;
        return diff;
    }


    static createSampleScooter(id) {
        if (id != null) {
            const scooter = new Scooter(id);
            return scooter;
        }
    }

    makeTag(length) {
        let result = '';
        const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        const charactersLength = characters.length;
        let counter = 0;
        while (counter < length) {
            result += characters.charAt(Math.floor(Math.random() * charactersLength));
            counter += 1;
        }
        return result;
    }

    // generateGpsLocation() {
    //     if(this.status !== Scooter.scooterStatus.INUSE){
    //         return this.generateLatCoord() + "°N, " + this.generateLonCoord() + "°E";
    //     }
    //     return ""
    // }
    generateGpsLocation() {
            return this.generateLatCoord() + "°N, " + this.generateLonCoord() + "°E";
    }

    generateBatteryCharge() {

    }

    // getStatus() {
    //     return this.status;
    // }
}
