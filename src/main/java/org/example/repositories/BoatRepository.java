package org.example.repositories;

import org.example.models.entities.Boat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BoatRepository extends CrudRepository<Boat, String> {

    /**
     * Find a boat by his ID.
     *
     * @param id the boat id
     * @return an optional boat
     */
    Optional<Boat> findById(String id);

    /**
     * Return all boats.
     * TODO: Add pagination
     *
     * @return a list of boats
     */
    List<Boat> findAll();
    
}
