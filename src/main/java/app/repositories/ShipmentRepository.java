package app.repositories;

import org.springframework.stereotype.Repository;

import app.models.Shipment;

@Repository
public interface ShipmentRepository extends ModelRepository<Shipment, Integer> { }
