export class Partner {
    partnerKey;
    firstname;
    lastname;
    description
    createdAt
    country;
    address;
    email;
    zipcode;


    constructor(partnerKey, firstname, lastname, description, createdAt, country, address, email, zipcode) {
        this.partnerKey = partnerKey;
        this.firstname = firstname;
        this.lastname = lastname;
        this.description = description;
        this.createdAt = createdAt;
        this.country = country;
        this.address = address;
        this.email = email;
        this.zipcode = zipcode;
    }

    static copyConstructor(partner) {
        if (partner == null) return null;
        let copy = Object.assign(new Partner(0), partner);
        copy.partnerKey = partner.partnerKey;
        copy.firstname = partner.firstname;
        copy.lastname = partner.lastname;
        copy.description = partner.description;
        copy.createdAt = partner.createdAt;
        copy.country = partner.country;
        copy.address = partner.address;
        copy.email = partner.email;
        copy.zipcode = partner.zipcode;
        return copy;
    }

    static createSample(id) {
        let partner = new Partner(id);
        partner.firstname = Math.trunc(Partner.firstNames.length * Math.random());
        partner.lastname = Math.trunc(Partner.lastNames.length * Math.random());
        partner.description = Math.trunc(Partner.descriptions.length * Math.random());
        partner.createdAt = Date.now();
        partner.country = Math.trunc(Partner.countries.length * Math.random());
        partner.address = Math.trunc(Partner.addresses.length * Math.random());
        partner.email = Math.trunc(Partner.emails.length * Math.random());
        partner.zipcode = Math.trunc(Partner.zipcodes.length * Math.random());
    }

    static firstNames = ["Edward", "Philip", "Stewart", "George", "Stephen"]
    static lastNames = ["Shin", "Anderson", "de Lange", "Ström ", "Oswell"]
    static descriptions = ["Company", "Activist", "Non-profit"]
    static countries = ["Chile ", "Peru ", "Zambia ", "Myanmar ", "Tanzania "]
    static addresses = ["Street1", "Street2", "Road1", "Road2"]
    static emails = ["jup1@gmail.com", "pup2@hotmail.com", "jager4@hotmail.com", "pub9@gmail.com"]
    static zipcodes = ["1000AA", "2000BB", "3000CC", "4000DD", "5000EE"]
}