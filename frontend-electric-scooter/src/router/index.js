import {createRouter, createWebHashHistory} from "vue-router";
import WelcomeComponent from "@/components/WelcomeComponent";
import ScootersOverview31 from "@/components/scooters/ScootersOverview31";
import ScootersOverview32 from "@/components/scooters/ScootersOverview32";
import UnknownRoute from "@/components/UnknownRoute";
import ScootersOverview33 from "@/components/scooters/ScootersOverview33";
import ScootersDetail33 from "@/components/scooters/ScootersDetail33";
import ScootersOverview34 from "@/components/scooters/ScootersOverview34.vue";
import ScootersDetail34 from "@/components/scooters/ScootersDetail34.vue";
import ScootersOverview37 from "@/components/scooters/ScootersOverview37.vue";
import ScootersDetail37 from "@/components/scooters/ScootersDetail37.vue";
import SignInComponent from "@/components/SignInComponent.vue";
import TripsOverview45 from "@/components/trips/TripsOverview45.vue";

const routes = [
    {path: '/', redirect: '/home'},
    {path: '/home', component: WelcomeComponent},
    {path: '/scooters/overview31', component: ScootersOverview31},
    {path: '/scooters/overview32', component: ScootersOverview32},
    {
        path: '/scooters/overview33',
        component: ScootersOverview33,
        children: [{
            path: ':scootersId', component: ScootersDetail33,
        }]

    },
    {path: '/sign-up', component: UnknownRoute},
    {path: '/sign-in', component: SignInComponent},
    {path: '/sign-out', redirect: () => ({path: '/sign-in', query: {signOut: true}})},
    {
        path: '/scooters/overview34',
        component: ScootersOverview34,
        children: [{
            path: ':scootersId', component: ScootersDetail34,
        }]

    },
    {path: '/unknownRoute', component: UnknownRoute},
    {path: '/:pathMatch(.*)', redirect: '/unknownRoute'},
    {
        path: '/scooters/overview37',
        component: ScootersOverview37,
        children: [{
            path: ':scootersId', component: ScootersDetail37,
        }]

    },
    {path: '/trips/overview45', component: TripsOverview45},

];


export const router = createRouter({
    history: createWebHashHistory(),
    routes
})
