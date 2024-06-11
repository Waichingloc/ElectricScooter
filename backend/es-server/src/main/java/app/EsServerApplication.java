package app;

import app.models.Scooter;
import app.models.Trip;
import app.repositories.ScootersRepositoryJpa;
import app.repositories.TripsRepositoryJpa;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.logging.Level;

@SpringBootApplication
public class EsServerApplication implements CommandLineRunner {

    @Autowired
    private TripsRepositoryJpa tripsRepositoryJpa;

    @Autowired
    private ScootersRepositoryJpa scootersRepositoryJpa;

    public static void main(String[] args) {
        SpringApplication.run(EsServerApplication.class, args);
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Running CommandLine StartUp");
        this.createInitialScooters();
    }

    private void createInitialScooters() {
        try {
            List<Scooter> scooters = this.scootersRepositoryJpa.findAll();
            List<Trip> trips = this.tripsRepositoryJpa.findAll();
            if (!scooters.isEmpty() || !trips.isEmpty()) return;
            System.out.println("Configuring some initial scooter and trips data");

            for (int i = 0; i < 10; i++) {
                //create scooter
                Scooter scooter = Scooter.createSampleScooter(0);

                //set first trip
                Trip trip1 = Trip.createSampleTrip(0);
                scooter.associateTrip(trip1);
                trip1.associateScooter(scooter);
                trip1.setScooter(scooter);
                this.tripsRepositoryJpa.save(trip1);

                //set second trip
                Trip trip2 = Trip.createSampleTrip(0); // You might want to adjust the parameters
                scooter.associateTrip(trip2);
                trip2.associateScooter(scooter);
                trip2.setScooter(scooter);
                this.tripsRepositoryJpa.save(trip2);

                this.scootersRepositoryJpa.save(scooter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
