package app.models;

import app.repositories.Identifiable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

import java.security.SecureRandom;
import java.util.*;

@NamedQueries({
        @NamedQuery(name = "Scooters_by_status",
                query = "SELECT s FROM Scooter s WHERE s.status = ?1"),
        @NamedQuery(name = "Scooters_by_battery",
                query = "SELECT s FROM Scooter s WHERE s.batteryCharge >= ?1"),
})
@Entity
public class Scooter implements Identifiable {

    public enum Status {
        IDLE, INUSE, MAINTENANCE
    }

    private static final int MAX_CHARGE = 100;
    private static final int MIN_CHARGE = 5;
    private static final int MAX_MILEAGE = 10000;
    private static final int MIN = 1;

    @JsonView({ViewClasses.Summary.class})
    @SequenceGenerator(name = "Scooter_ids", initialValue = 30001)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Scooter_ids")
    @Id
    private long id = 0L;
    @JsonView({ViewClasses.Summary.class})
    private String tag;
    @JsonView({ViewClasses.Summary.class})
    @Enumerated(EnumType.STRING)
    private Status status;
    //    private String status;
    //    @JsonView(ViewClasses.Shallow.class)
    private String gpsLocation;
    @JsonView({ViewClasses.Summary.class})
    private int batteryCharge;
    //    @JsonView(ViewClasses.Shallow.class)
    private double mileage;

    @JsonView({ViewClasses.Summary.class})
    @OneToMany(mappedBy = "scooter", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JsonManagedReference
//    @JsonIgnoreProperties("scooter")
    private Set<Trip> trips;

//    @JsonView(ViewClasses.Shallow.class)
//    private String currentTrip;

    public Scooter() {
        trips = new HashSet<>();
    }

    public Scooter(String tag) {
        this.tag = tag;
    }

    public Scooter(long id) {
        this.id = id;
        this.tag = makeTag(8);
        this.status = Status.valueOf(generateRandomStatus());
        this.gpsLocation = generateGpsLocation();
        this.batteryCharge = generateRandomCharge();
        this.mileage = generateRandomMileage();
        this.trips = new HashSet<>();
    }

    public boolean associateTrip(Trip trip) {
        if (trip != null && trip.getScooter() == null && Objects.equals(this.getStatus(), Status.INUSE)) {
            this.getTrips().add(trip);
            trip.setScooter(this);
            return true;
        }
        return false;
    }

    public Set<Trip> getTrips() {
        return trips;
    }

    public void setTrips(Set<Trip> trips) {
        this.trips = trips;
    }

    public static Scooter createSampleScooter(long id) {
        Scooter scooter = new Scooter(id);
        scooter.setTag(makeTag(8));
        scooter.setStatus(Status.valueOf(generateRandomStatus()));
        scooter.setGpsLocation(generateGpsLocation());
        scooter.setBatteryCharge(generateRandomCharge());
        scooter.setMileage(generateRandomMileage());
        scooter.setTrips(scooter.getTrips());
        return scooter;
    }

    public static String makeTag(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        String result = "";
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int charactersLength = characters.length();

        SecureRandom random = new SecureRandom();
        for (int counter = 0; counter < length; counter++) {
            int randomIndex = random.nextInt(charactersLength);
            result += String.valueOf(characters.charAt(randomIndex));
        }
        stringBuilder.append(result);
        return stringBuilder.toString();
    }

//    private static final Map<String, String> scooterStatus = Map.of(
//            "IDLE", "IDLE",
//            "INUSE", "INUSE",
//            "MAINTENANCE", "MAINTENANCE"
//    );

    public static Status statusValue(String status) {
        String statusString = status.toUpperCase();
        return Status.valueOf(statusString);
    }

    public static String generateRandomStatus() {
        Random random = new Random();

        int randomIndex = random.nextInt(Status.values().length);
        return Status.values()[randomIndex].toString();
//        String[] keys = scooterStatus.keySet().toArray(new String[0]);

//        return scooterStatus.get(keys[randomIndex]);
    }

    public static double generateLatCoord() {
        double min = 523680;
        double max = 524312;
        double divide = 10000;
        double diff = Math.floor((Math.random() * (max - min)) + min) / divide;
        return diff;
    }

    public static double generateLonCoord() {
        double min = 48055;
        double max = 50686;
        double divide = 10000;
        double diff = Math.floor((Math.random() * (max - min)) + min) / divide;
        return diff;
    }

    public static String generateGpsLocation() {
        double latCoord = generateLatCoord();
        double lonCoord = generateLonCoord();
        return latCoord + "°N, " + lonCoord + "°E";
    }

    public static int generateRandomCharge() {
        Random random = new Random();
        int randomCharge = random.nextInt(MAX_CHARGE - MIN_CHARGE + 1) + MIN_CHARGE;
        return randomCharge;
    }

    public static int generateRandomMileage() {
        Random random = new Random();
        int randomMileage = (int) Math.floor((Math.random() * MAX_MILEAGE) + MIN);
        return randomMileage;
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    //    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }

    public String getGpsLocation() {
        return gpsLocation;
    }

    public void setGpsLocation(String gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    public int getBatteryCharge() {
        return batteryCharge;
    }

    public void setBatteryCharge(int batteryCharge) {
        this.batteryCharge = batteryCharge;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Scooter)) return false;
        return id == ((Scooter) o).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
