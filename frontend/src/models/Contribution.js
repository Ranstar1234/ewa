export class Contribution {
    contributionKey;
    createdAt;
    contributionImg;
    latitude;
    longitude;
    projectKey;


    constructor(contributionKey, createdAt,contributionImg, latitude, longitude, projectKey) {
        this.contributionKey = contributionKey;
        this.createdAt = createdAt;
        this.contributionImg = contributionImg;
        this.latitude = latitude;
        this.longitude = longitude;
        this.projectKey = projectKey;
    }

    static copyConstructor(contribution) {
        if (contribution == null) return null;
        let copy = Object.assign(new Contribution(0), contribution);

        copy.contributionKey = contribution.contributionKey;
        copy.createdAt = contribution.createdAt;
        copy.contributionImg = contribution.contributionImg;
        copy.latitude = contribution.latitude;
        copy.longitude = contribution.longitude;
        copy.projectKey = contribution.projectKey;


        return copy;
    }
}