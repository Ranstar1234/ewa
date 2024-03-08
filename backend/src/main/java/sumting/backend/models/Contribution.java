package sumting.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Entity(name = "S2_Contribution")
//@SequenceGenerator(name = "seqContribution", allocationSize = 1)
public class Contribution extends AbstractEntityModel<Contribution> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int contributionKey;
    @JsonView(CustomViews.Shallow.class)
    private LocalDate createdAt;
    @JsonView(CustomViews.Shallow.class)
    private String contributionImg;
    @JsonView(CustomViews.Shallow.class)
    private double latitude;
    @JsonView(CustomViews.Shallow.class)
    private double longitude;
    @JsonView(CustomViews.Shallow.class)
    private int projectKey;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "partner_key")
    private Partner partnerKey;
    @JsonBackReference
    @Transient
    private static final Random RANDOM = new Random();

    // Default constructor
    public Contribution() {

    }

    public Contribution(int contributionKey, LocalDate createdAt, String contributionImg, double latitude, double longitude, Partner partnerKey, int projectKey) {
        this.contributionKey = contributionKey;
        this.createdAt = createdAt;
        this.contributionImg = contributionImg;
        this.latitude = latitude;
        this.longitude = longitude;
        this.partnerKey = partnerKey;
        this.projectKey = projectKey;
    }

    public static Contribution createSampleContribution(Partner partner, double minLat, double maxLat, double minLong, double maxLong, int point) {
        //List<String> name = List.of("Greenery Goal", "Save the Planet ", "Me Eco You Eco ", "Eden Project ", "Greenhost");
        List<Integer> points = List.of(1, 2, 3, 4);
//        int randomNum = (int) Math.floor(Math.random() * points.size());
        double randomLatitude;
        double randomLongitude;

        randomLatitude = RANDOM.nextDouble(minLat, maxLat);
        randomLongitude = RANDOM.nextDouble(minLong, maxLong);


        Contribution contribution = new Contribution();

        contribution.setKey(RANDOM.nextInt(100) + 1);
        contribution.setCreatedAt(contribution.createRandomDate(2015, 2022));
        //contribution.setName(name.get(randomNum));
        contribution.setContributionImg("https://upload.wikimedia.org/wikipedia/commons/e/eb/Ash_Tree_-_geograph.org.uk_-_590710.jpg");
        contribution.setLatitude(randomLatitude);
        contribution.setLongitude(randomLongitude);
        contribution.setPartnerKey(partner);
        contribution.setProjectKey(points.get(point));

        return new Contribution(contribution.getKey(), contribution.getCreatedAt(),
                contribution.getContributionImg(), contribution.getLatitude(),
                contribution.getLongitude(), contribution.getPartnerKey(), contribution.getProjectKey());
    }

    /**
     * Random number generator
     *
     * @param start start number
     * @param end   end number
     * @return random number between start and end
     */
    private int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    /**
     * Generating random date
     *
     * @param startYear start year
     * @param endYear   end year
     * @return random data between start year and end year
     */
    private LocalDate createRandomDate(int startYear, int endYear) {
        int year = createRandomIntBetween(startYear, endYear);
        int month = createRandomIntBetween(1, 12);
        int day;
        // Leap year
        if (year % 4 == 0 && month == 2) {
            day = createRandomIntBetween(1, 29);
        } else {
            day = createRandomIntBetween(1, 28);
        }

        return LocalDate.of(year, month, day);
    }


    @Override
    public int getKey() {
        return this.contributionKey;
    }

    @Override
    public void setKey(int key) {
        this.contributionKey = key;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getContributionImg() {
        return contributionImg;
    }

    public void setContributionImg(String projectImg) {
        this.contributionImg = projectImg;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Partner getPartnerKey() {
        return partnerKey;
    }

    public void setPartnerKey(Partner partnerKey) {
        this.partnerKey = partnerKey;
    }

    public int getContributionKey() {
        return contributionKey;
    }

    public void setContributionKey(int contributionKey) {
        this.contributionKey = contributionKey;
    }

    public int getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(int projectKey) {
        this.projectKey = projectKey;
    }
}
