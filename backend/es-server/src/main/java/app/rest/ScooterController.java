package app.rest;

import app.exceptions.PreConditionFailed;
import app.exceptions.ResourceNotFoundException;
import app.models.Scooter;
import app.models.Trip;
import app.models.ViewClasses;
import app.repositories.EntityRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/scooters")
public class ScooterController {

    @Autowired
    EntityRepository<Scooter> scootersRepo;

    @Autowired
    EntityRepository<Trip> tripsRepo;

    @GetMapping(path = "test", produces = "application/json")
    public List<Scooter> getTestScooter() {
        return List.of(
                new Scooter("Test-Scooter-A"),
                new Scooter("Test-Scooter-B"));
    }

//    @GetMapping(path = "", produces = "application/json")
//    public List<Scooter> getAllScooters() {
//        return this.scootersRepo.findAll();
//    }

    @GetMapping(path = "", produces = "application/json")
    public List<Scooter> getAllScooters(@RequestParam(required = false) String status,
                                        @RequestParam(name = "battery", required = false) Double battery) {

        if (status != null &&
                !status.equalsIgnoreCase(Scooter.Status.IDLE.toString()) &&
                !status.equalsIgnoreCase(Scooter.Status.INUSE.toString()) &&
                !status.equalsIgnoreCase(Scooter.Status.MAINTENANCE.toString())) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status=" + status +
                    " is not a valid scooter status value");
        }

        int paramsCount =
                (status != null ? 1 : 0) +
                        (battery != null ? 2 : 0);

        if (paramsCount == 0) {
            return scootersRepo.findAll();
        } else if (paramsCount == 1) {
            return scootersRepo.findByQuery("Scooters_by_status", Scooter.statusValue(status));
        } else if (paramsCount == 2) {
            return scootersRepo.findByQuery("Scooters_by_battery", battery);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Combination of status=" + status +
                    ", battery=" + battery + " are invalid query parameters");
        }
    }

    @GetMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<Scooter> getScooterByid(@PathVariable long id) {
        return ResponseEntity.ok().body(scootersRepo.findIdById(id));
    }

    @DeleteMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<Scooter> deleteOneScooter(@PathVariable long id) {

        Scooter scooter = scootersRepo.deleteById(id);

        return ResponseEntity.ok().body(scooter);
    }

    @PostMapping(path = "", produces = "application/json")
    public ResponseEntity<Scooter> createUser(
            @RequestBody Scooter scooter) {

        Scooter savedScooter = scootersRepo.save(scooter);

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/{id}").
                buildAndExpand(savedScooter.getId()).toUri();

        return ResponseEntity.created(location).body(savedScooter);
    }

    @PutMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<Scooter> updateScooter(@PathVariable long id, @RequestBody Scooter scooter) {
        if (id != scooter.getId()) {
            throw new PreConditionFailed("Scooter-Id=" + scooter.getId() + " does not match path parameter=" + id);
        }

        Scooter existingScooter = scootersRepo.findIdById(scooter.getId());
        if (existingScooter == null) {
            throw new ResourceNotFoundException("Cannot delete a scooter with id=" + id);
        }
        existingScooter.setTag(scooter.getTag());
        existingScooter.setStatus(scooter.getStatus());
        existingScooter.setGpsLocation(scooter.getGpsLocation());
        existingScooter.setBatteryCharge(scooter.getBatteryCharge());
        existingScooter.setMileage(scooter.getMileage());
        existingScooter.setTrips(scooter.getTrips());

        this.scootersRepo.save(existingScooter);

        return ResponseEntity.ok().body(existingScooter);
    }

    @JsonView(ViewClasses.Summary.class)
    @GetMapping(path = "summary", produces = "application/json")
    public List<Scooter> getScootersSummary() {
        // Retrieve the list of scooters
        return this.scootersRepo.findAll();
    }

    @Transactional
    @PostMapping(path = "{id}/trips", produces = "application/json")
    public ResponseEntity<Scooter> addANewTrip(@PathVariable() long id,
                                               @RequestBody Trip newTrip) {
        Scooter scooter = this.scootersRepo.findIdById(id);

        // save the trip into its repository
        if (newTrip.getStart() == null) {
            newTrip.setStart(LocalDateTime.now());
        }
        Trip savedTrip = this.tripsRepo.save(newTrip);

        // associate the savedTrip with the scooter
        // both objects are managed, so the transaction should handle the update
        if (savedTrip == null || !Objects.equals(scooter.getStatus(), Scooter.Status.IDLE)) {
            throw new PreConditionFailed(
                    String.format(" Scooter with id=%d with status %s cannot be claimed for another trip",
                            scooter.getId(), scooter.getStatus()));
        } else if (scooter.getBatteryCharge() < 10) {
            throw new PreConditionFailed(
                    String.format(" Scooter with id=%d with batteryCharge of %s cannot be claimed for another trip",
                            scooter.getId(), scooter.getBatteryCharge()));
        }
        scooter.setStatus(Scooter.Status.valueOf("INUSE"));
        scooter.associateTrip(savedTrip);

        // if an exception occured, the newTrip should not have been persisted
        return ResponseEntity.ok().body(scooter);
    }

    @GetMapping(path = "{scooterId}/trips", produces = "application/json")
    public List<Trip> getScooterTrips(@PathVariable() long scooterId,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to)
    {
        String stringFrom = from.toString();
        String stringTo = to.toString();
        LocalDateTime valueFrom = LocalDateTime.parse(stringFrom);
        LocalDateTime valueTo = LocalDateTime.parse(stringTo);

        if (valueFrom != null && valueTo != null && valueFrom.isAfter(valueTo)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Combination of scooterId=" + scooterId +
                    ", from=" + valueFrom + ", to=" + valueTo + " are invalid query parameters");
        } else {
            return tripsRepo.findByQuery("Trip_find_by_scooterId_and_period", scooterId, valueFrom, valueTo);
        }
    }
}
