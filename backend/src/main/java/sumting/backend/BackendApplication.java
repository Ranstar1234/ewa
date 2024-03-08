package sumting.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sumting.backend.models.Contribution;
import sumting.backend.models.Partner;
import sumting.backend.models.Project;
import sumting.backend.repo.EntityRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);

    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        createInitialPartners();
        createInitialProjects();
        createInitialContributions();

    }

    /**
     * This is for partners
     */
    @Autowired
    @Qualifier("PartnersRepo")
    private EntityRepository<Partner> partnersRepo;

    private void createInitialPartners() {
        List<Partner> partners = this.partnersRepo.findAll();
        if (partners.size() > 0) return;
        System.out.println("Configuring some initial partners in repository");
        for (int i = 0; i < 20; i++) {
            this.partnersRepo.save(Partner.createSamplePartner());
        }
    }


    /**
     * This is for contributions
     */
    @Autowired
    @Qualifier("ContributionsRepo")
    private EntityRepository<Contribution> contributionsRepo;

    private void createInitialContributions() {
        List<Contribution> contributions = this.contributionsRepo.findAll();
        List<Partner> partners = this.partnersRepo.findAll();
        List<Project> projects = this.projectsRepo.findAll();

        if (contributions.size() > 0) return;
        System.out.println("Configuring some initial contributions in repository");
        for (int i = 0; i < 50; i++) {
            double longitude = RANDOM.nextDouble(-180, 180);
            double latitude = RANDOM.nextDouble(-85, 85);
            int randomPoint = RANDOM.nextInt(4);

            for (int j = 0; j < RANDOM.nextInt(5,15); j++) {
                this.contributionsRepo.save(Contribution.createSampleContribution(partners.get(RANDOM.nextInt(partners.size())),
                        latitude, latitude + 0.5, longitude, longitude + 0.5, randomPoint));
            }
        }


    }

    /**
     * This is for Project
     */
    @Autowired
    @Qualifier("ProjectsRepo")
    private EntityRepository<Project> projectsRepo;

    private void createInitialProjects() {
        List<Project> projects = this.projectsRepo.findAll();

        if (projects.size() > 0) return;
        System.out.println("Configuring some initial projects in repository");
        for (int i = 0; i < 10; i++) {
            this.projectsRepo.save(Project.createSampleProject());
        }
    }
}
