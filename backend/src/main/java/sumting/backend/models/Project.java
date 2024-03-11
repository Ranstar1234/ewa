package sumting.backend.models;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.List;
import java.util.Random;

@Entity(name = "S2_Project")
@SequenceGenerator(name = "seqProject", allocationSize = 1)
public class Project extends AbstractEntityModel<Project> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqProject")
    @Column(name = "project_key")
    private int projectKey;
    @JsonView(CustomViews.Shallow.class)
    private String name;
    @JsonView(CustomViews.Shallow.class)
    private String bucket;
    private String description;
    @Transient
    private final List<String> bucketOptions = List.of("Sequester Carbon", "Restore Wildlife", "Fight Desertification", "Remove Plastic Pollution");
    @Transient
    private final List<String> descriptionDetailList = List.of("Planting trees to save the world!",
            "Clearing plastic off the ocean.",
            "Cleaning the coral reefs!",
            "Fighting desertification!");

    @Transient
    private static final Random RANDOM = new Random();

    public Project() {

    }

    public Project(int projectKey, String name, String bucket, String description) {
        this.projectKey = projectKey;
        this.name = name;
        this.bucket = bucket;
        this.description = description;

    }

    public static Project createSampleProject() {
        List<String> name_first_half = List.of("Green", "Save the", "Better world, better", "Eden", "Ecologic");
        List<String> name_second_half = List.of("Goal", "Planet ", "Earth", "Project ", "Host");

        Project project = new Project();
        project.keyAndDescription(RANDOM.nextInt(3) + 1);

        project.setName(name_first_half.get((int) Math.floor(Math.random() * name_first_half.size())) + " " + name_second_half.get((int) Math.floor(Math.random() * name_second_half.size())));

        project.setKey(project.getKey());
        project.setBucket(project.getBucket());
        project.setDescription(project.descriptionDetailList.get((int) Math.floor(Math.random() * project.descriptionDetailList.size())));

        return new Project(project.getKey(), project.getName(), project.getBucket(),
                project.getDescription());
    }

    private void keyAndDescription(int key) {
        switch (key) {
            case 0, 1, 2, 3 -> {
                keyAndDescriptionSetter(key + 1, bucketOptions.get(key));
            }
        }
    }

    private void keyAndDescriptionSetter(int key, String description) {
        this.setKey(key);
        this.setBucket(description);
    }

    public int getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(int projectKey) {
        this.projectKey = projectKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getBucketOptions() {
        return bucketOptions;
    }

    public List<String> getDescriptionDetailList() {
        return descriptionDetailList;
    }
}
