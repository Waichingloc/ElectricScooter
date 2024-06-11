package app.repositories;

import app.exceptions.ResourceNotFoundException;
import app.models.Scooter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

@Repository("SCOOTERS")
public class ScootersRepositoryMock
        implements EntityRepository<Scooter> {

    private long lastId = 30000L;
    private List<Scooter> scooters;

    static Random randomizer = new Random();

    // manage the generation of a semi-random sequence of id-s
    private long getNextId() {
        this.lastId += 1 + randomizer.nextInt(3);
        return this.lastId;
    }

    public ScootersRepositoryMock() {

        this.scooters = new ArrayList<>();
        // Get the available authors from the authors repo
        for (int i = 0; i < 8; i++) {
            this.scooters.add(Scooter.createSampleScooter(getNextId()));
        }

    }

    @Override
    public List<Scooter> findAll() {
        return this.scooters;
    }

    @Override
    public Scooter findIdById(long id) {
        Predicate<? super Scooter> predicate = scooter -> Long.valueOf(scooter.getId()).equals(id);
        return scooters.stream().filter(predicate).findFirst().orElseThrow(() -> new ResourceNotFoundException("Scooter with id " + id + " does not exist"));
    }

    @Override
    public Scooter deleteById(long id) {
        Scooter scooterToRemove = findIdById(id);
        scooters.remove(scooterToRemove);

        return scooterToRemove;
    }

    @Override
    public List<Scooter> findByQuery(String jpqlName, Object... params) {
        return null;
    }

    @Override
    public Scooter save(Scooter scooter) {
        if (scooter.getId() == 0) {
            scooter.setId(getNextId());
            scooters.add(scooter);
            return scooter;
        }

        Scooter existingScooter = findIdById(scooter.getId());
        int index = scooters.indexOf(existingScooter);
        scooters.set(index, scooter);
        return scooter;
    }
}
