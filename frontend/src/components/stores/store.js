import {ref} from "vue";

export const store = ref({
    long: 0,
    lat: 0,
    setLong(long) {
        this.long = long;
    },
    setLat(lat) {
        this.lat = lat
    }

})