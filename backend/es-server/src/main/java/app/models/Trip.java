package app.models;

import app.repositories.Identifiable;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@NamedQueries({
        @NamedQuery(name = "Trip_find_by_scooterId_and_period",
                query = "SELECT t FROM Trip t WHERE t.scooter.id = ?1 AND t.startTime >= ?2 AND t.endTime <= ?3"),
        @NamedQuery(name = "Trip_find_by_scooterId_and_status",
                query = "SELECT t FROM Trip t WHERE t.scooter.status = ?1"),
})
@Entity
public class Trip implements Identifiable {
    @SequenceGenerator(name = "Trips_ids", initialValue = 100001)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Trips_ids")
    @Id
    private long id = 0L;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
    private String startLocation;
    private String endLocation;
    private double mileage;
    private double cost;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "scooter_id")
    @JsonIncludeProperties({"id", "tag", "status"})
//    @JsonBackReference
    private Scooter scooter;

    public Trip() {
    }

    public Trip(long id) {
        this.id = id;
        this.startTime = generateStartTime();
        this.endTime = generateEndTime();
        this.startLocation = generateGpsLocation();
        this.endLocation = generateGpsLocation();
        this.mileage = generateMileage();
        this.cost = generateCost();
    }

    public static Trip createSampleTrip(long id) {
        Trip trip = new Trip(id);
        trip.setStart(trip.generateStartTime());
        trip.setEnd(trip.generateEndTime());
        trip.setStartLocation(trip.generateGpsLocation());
        trip.setEndLocation(trip.generateGpsLocation());
        trip.setMileage(trip.generateMileage());
        trip.setCost(trip.generateCost());
        return trip;
    }


    public boolean associateScooter(Scooter scooter) {
        if (scooter != null && this.getScooter() == null) {
            // update both sides of the association
            scooter.associateTrip(this);
            this.setScooter(scooter);
            return true;
        }
        return false;
    }

    public boolean dissociateScooter(Scooter scooter) {
        if (scooter != null && scooter.equals(this.getScooter())) {
            // Dissociate the trip from the scooter
            this.setScooter(null);
            return true;
        }
        return false;
    }

    @Transient
    public boolean isActive(){
        Scooter scooter = this.getScooter();
        if(Objects.equals(scooter.getStatus(), Scooter.Status.INUSE)){
            return true;
        }

        return false;
    }

    public LocalDateTime generateStartTime() {
        // Generate a random date within the years 2022 and 2023
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31); // Fix typo in endDate
        long randomDays = startDate.toEpochDay() + new Random().nextInt((int) (endDate.toEpochDay() - startDate.toEpochDay() + 1));
        LocalDate randomDate = LocalDate.ofEpochDay(randomDays);

        // Generate random time values
        int randomHour = new Random().nextInt(15); // Random hour between 0 and 14 (max 10 hours difference)
        int randomMinute = new Random().nextInt(60); // Random minute between 0 and 59
        int randomSecond = new Random().nextInt(60); // Random second between 0 and 59

        // Combine the random date and time values
        LocalTime randomTime = LocalTime.of(randomHour, randomMinute, randomSecond);
        return LocalDateTime.of(randomDate, randomTime);
    }

    public LocalDateTime generateEndTime() {
        LocalDateTime startTime = this.startTime;

        // Generate random time values for the end time, ensuring it's within 10 hours from the start time
        int randomHour = startTime.getHour() + new Random().nextInt(10); // Random hour within 10 hours from start hour
        int randomMinute = new Random().nextInt(60); // Random minute between 0 and 59
        int randomSecond = new Random().nextInt(60); // Random second between 0 and 59

        // Combine the date from the start time with the random time values
        LocalTime randomTime = LocalTime.of(randomHour, randomMinute, randomSecond);
        LocalDateTime endTime = LocalDateTime.of(LocalDate.from(startTime), randomTime);


        // Ensure endTime is after startTime
        if (endTime.isBefore(startTime)) {
            endTime = endTime.plusHours(10); // Add up to 10 hours to the start time
        }

        return endTime;
    }

    public double generateLatCoord() {
        double min = 523680;
        double max = 524312;
        double divide = 10000;
        double diff = Math.floor((Math.random() * (max - min)) + min) / divide;
        return diff;
    }

    public double generateLonCoord() {
        double min = 48055;
        double max = 50686;
        double divide = 10000;
        double diff = Math.floor((Math.random() * (max - min)) + min) / divide;
        return diff;

    }

    public String generateGpsLocation() {
        double latCoord = generateLatCoord();
        double lonCoord = generateLonCoord();
        return latCoord + "°N, " + lonCoord + "°E";
    }

    public double generateMileage() {
        // Generate a random mileage value between 0 and 50 with two decimal places
        double randomMileage = new Random().nextDouble() * 50;
        return Math.round(randomMileage * 100.0) / 100.0; // Round to two decimal places
    }

    public double generateCost() {
        // Generate a random cost value between 0 and 5 with two decimal places
        double randomCost = new Random().nextDouble() * 5;
        return Math.round(randomCost * 100.0) / 100.0; // Round to two decimal places
    }

    public Scooter getScooter() {
        return scooter;
    }

    public void setScooter(Scooter scooter) {
        this.scooter = scooter;
        if (!scooter.getTrips().contains(this)) {
            scooter.getTrips().add(this);
        }
    }

    public LocalDateTime getStart() {
        return startTime;
    }

    public void setStart(LocalDateTime start) {
        this.startTime = start;
    }

    public LocalDateTime getEnd() {
        return endTime;
    }

    public void setEnd(LocalDateTime end) {
        this.endTime = end;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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
        if (!(o instanceof Trip)) return false;
        return id == ((Trip) o).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
