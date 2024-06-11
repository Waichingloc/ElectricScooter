package app.rest;

import app.exceptions.PreConditionFailed;
import app.exceptions.ResourceNotFoundException;
import app.models.Scooter;
import app.models.Trip;
import app.repositories.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripsController {

    @Autowired
    EntityRepository<Trip> tripsRepo;

    @Autowired
    EntityRepository<Scooter> scootersRepo;


    @GetMapping(path = "", produces = "application/json")
    public List<Trip> getAllTrips() {
        return this.tripsRepo.findAll();
    }

    @GetMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<Trip> getTripByid(@PathVariable long id) {
        return ResponseEntity.ok().body(tripsRepo.findIdById(id));
    }

    @PutMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<Trip> updateTrip(@PathVariable long id, @RequestBody Trip trip, @RequestParam(name = "finish", required = false) Boolean finish) {
        if (id != trip.getId()) {
            throw new PreConditionFailed("Trip-Id=" + trip.getId() + " does not match path parameter=" + id);
        }

        Trip existingTrip = tripsRepo.findIdById(trip.getId());

        Scooter existingScooter = scootersRepo.findIdById(existingTrip.getScooter().getId());


        if (existingTrip == null) {
            throw new ResourceNotFoundException("Cannot delete a trip with id=" + id);
        }
        existingTrip.setStartLocation(trip.getStartLocation());
        existingTrip.setEndLocation(trip.getEndLocation());
        existingTrip.setMileage(trip.getMileage());
        existingTrip.setCost(trip.getCost());
        existingTrip.setScooter(trip.getScooter());
        existingTrip.setStart(trip.getStart());
        existingTrip.setEnd(trip.getEnd());
        if (finish != null && finish) {
            existingTrip.setEnd(trip.getEnd());
            existingScooter.setStatus(Scooter.statusValue("IDLE"));
        }
        this.scootersRepo.save(existingScooter);
        Trip savedTrip = this.tripsRepo.save(existingTrip);

        return ResponseEntity.ok().body(savedTrip);
    }
}
