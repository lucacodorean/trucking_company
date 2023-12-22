package app.repositories;

import org.springframework.stereotype.Repository;
import app.models.Truck;

@Repository
public interface TruckRepository extends ModelRepository<Truck, Integer> { }
