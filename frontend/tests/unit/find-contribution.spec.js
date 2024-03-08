import {shallowMount} from '@vue/test-utils';
import Sidebar from "@/components/Sidebar.vue";
import Mapbox from "@/components/Mapbox.vue";
import Store from "@/components/stores/store.js"
import {InMemoryEntitiesService} from "@/services/in-memory-entities-service";
import {Partner} from "@/models/Partner";
import {reactive} from "vue";

jest.setTimeout(10000);

describe("tests related to finding a contribution", () => {
    let wrapper;

    beforeEach(async () => {
        let partnerService = new InMemoryEntitiesService(0, Partner.createSample)
        let contributionService = new InMemoryEntitiesService(0, Partner.createSample)

        wrapper = await shallowMount(Sidebar, {
            global: {
                provide: {
                    "partnerService": reactive(partnerService)
                }
            }
        })
    })

    it("Partners are rendered", () => {
        const partnerContainer = wrapper.find(".partner-container");

        expect(partnerContainer.exists()).toBe(true)

    })
})
