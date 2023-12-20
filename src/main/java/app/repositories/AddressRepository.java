package app.repositories;

import org.springframework.stereotype.Repository;
import app.models.Address;

@Repository
public interface AddressRepository extends ModelRepository<Address, Integer> {}
