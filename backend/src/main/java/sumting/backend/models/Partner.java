package sumting.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@Entity(name = "S2_Partner")
@SequenceGenerator(name = "seqPartner", allocationSize = 1)
public class Partner extends AbstractEntityModel<Partner>{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqPartner")
    @Column(name = "partner_key")
    private int partnerKey;
    @JsonView(CustomViews.Shallow.class)
    private String firstname;
    @JsonView(CustomViews.Shallow.class)
    private String lastname;
    @JsonView(CustomViews.Shallow.class)
    private String description;
    @JsonView(CustomViews.Shallow.class)
    private LocalDate createdAt;
    @JsonView(CustomViews.Shallow.class)
    private String country;
    private String address;
    @JsonView(CustomViews.Shallow.class)
    private String email;
    private String zipcode;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partnerKey", cascade = CascadeType.ALL)
    private List<Contribution> contributions = new ArrayList<>();
    @Transient
    private static final Random RANDOM = new Random();

    public Partner() {
    }

    public Partner(int partnerKey, String firstname,String lastname, String description, LocalDate createdAt,
                   String country, String address, String email, String zipcode) {
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

    public static Partner createSamplePartner() {
        List<String> firstname = List.of("Edward", "Philip", "Stewart", "George", "Stephen");
        List<String> lastname = List.of("Shin", "Anderson", "de Lange", "Ström ", "Oswell");
        List<String> description = List.of("Company", "Activist", "Non-profit");
        List<String> country = List.of("Chile ", "Peru ", "Zambia ", "Myanmar ", "Tanzania ");
        List<String> address = List.of("Street1", "Street2", "Road1", "Road2");
        List<String> email = List.of("jup1@gmail.com", "pup2@hotmail.com", "jager4@hotmail.com", "pub9@gmail.com");
        char c = (char) ('a' + RANDOM.nextInt(26));
        Partner partner = new Partner();

        partner.partnerKey = RANDOM.nextInt(100) + 1;
        partner.firstname = firstname.get((int) Math.floor(Math.random() * firstname.size()));
        partner.lastname = lastname.get((int) Math.floor(Math.random() * lastname.size()));
        partner.description = description.get((int) Math.floor(Math.random() * description.size()));
        partner.createdAt = LocalDate.now().minusYears(1);
        partner.country = country.get((int) Math.floor(Math.random() * country.size()));
        partner.address = address.get((int) Math.floor(Math.random() * address.size()));
        partner.email = email.get((int) Math.floor(Math.random() * email.size()));
        partner.zipcode = String.format("%d%d%d%d%s%s",
                RANDOM.nextInt(9) + 1, RANDOM.nextInt(9), RANDOM.nextInt(9), RANDOM.nextInt(9),
                Character.toUpperCase(c), Character.toUpperCase(c));

        return new Partner(partner.getKey(), partner.getFirstname(), partner.lastname, partner.getDescription(), partner.getCreatedAt(),
                partner.getCountry(), partner.getAddress(), partner.getEmail(), partner.getZipcode());
    }

    @Override
    public int getKey() {
        return this.partnerKey;
    }

    @Override
    public void setKey(int key) {
        this.partnerKey = key;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
