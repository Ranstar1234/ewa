import { shallowMount } from '@vue/test-utils'
import Sidebar from "@/components/Sidebar.vue";

jest.setTimeout(10000);
describe('Form', () => {
    let wrapper

    beforeEach(() => {
        wrapper = shallowMount(Sidebar)
    })

    it('renders', () => {
        expect(wrapper.exists()).toBe(true)
    })

        // it('displays the create partner button when isSelected is false', () => {
        //     wrapper.setData({ isSelected: true })
        //     expect(wrapper.find('button[@click="isHiddenParner = !isHiddenParner"]').isVisible()).toBe(true)
        // })

        // it('displays the edit partner button when isEdit is true', () => {
        //     wrapper.setData({ isEdit: true })
        //     expect(wrapper.find('button[v-on:click="isHidden = !isHidden"]').isVisible()).toBe(true)
        // })


        // it('displays the create contribution button when isEdit is true', () => {
        //     wrapper.setData({ isEdit: true })
        //     expect(wrapper.find('button[v-on:click="isHiddenCreate = !isHiddenCreate"]').isVisible()).toBe(true)
        // })
        //
        // it('displays the delete partner button when isDelete is true', () => {
        //     wrapper.setData({ isDelete: true })
        //     expect(wrapper.find('button[@click="isHiddenDelete = !isHiddenDelete, deletePartnerByKey()"]').isVisible()).toBe(true)
        // })

        it('displays the create partner button when isSelected is false', () => {
            wrapper.setData({ isSelected: false })
            expect(wrapper.find('button[@click="isHiddenParner = !isHiddenParner"]').isVisible()).toBe(true)
        })

    it('sets the initial data property newPartner to its correct value', () => {
        expect(wrapper.vm.newPartner).toBe(null)    })

    it('sets the initial data property createdPartner to its correct value', () => {
        expect(wrapper.vm.createdPartner).toEqual({
            name: '',
            lastname: '',
            email: '',
            address: '',
            description: '',
            zipcode: '',
            country: '',
        })
    })
    it('sets the initial data property savedPartners to its correct value', () => {
        expect(wrapper.vm.savedPartners).toEqual([])
    })

    it('sets the initial data property isHiddenParner to its correct value', () => {
        expect(wrapper.vm.isHiddenParner).toBe(true)
    })

    it('sets the initial data property isHidden to its correct value', () => {
        expect(wrapper.vm.isHidden).toBe(true)
    })

    it('sets the initial data property isHiddenCreate to its correct value', () => {
        expect(wrapper.vm.isHiddenCreate).toBe(true)
    })

    it('sets the initial data property isHiddenDelete to its correct value', () => {
        expect(wrapper.vm.isHiddenDelete).toBe(true)
    })


})
