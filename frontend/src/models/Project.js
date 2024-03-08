export class Project {
    key;
    name;
    bucket;
    description;


    constructor(key, name, bucket, description) {
        this.key = key;
        this.name = name;
        this.bucket = bucket;
        this.description = description;
    }

    static copyConstructor(project) {
        if (project == null) return null;
        let copy = Object.assign(new Project(0), project);

        copy.key = project.key;
        copy.name = project.name;
        copy.bucket = project.bucket;
        copy.description = project.description;

        return copy;
    }
}