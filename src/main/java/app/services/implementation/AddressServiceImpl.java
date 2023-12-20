package app.services.implementation;

import app.single_point_access.RepositorySinglePointAccess;
import org.springframework.stereotype.Service;
import app.repositories.AddressRepository;
import app.services.AddressService;
import app.models.Address;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    private AddressRepository addressRepository = RepositorySinglePointAccess.getAddressRepository();

    @Override public Address save(Address entity)      { return addressRepository.save(entity);   }
    @Override public List<Address> findAll()           { return addressRepository.findAll();      }
    @Override public Address findByKey(String key)     { return addressRepository.findByKey(key); }
    @Override public Address findById(Integer id)      { return addressRepository.findById(id);   }
    @Override public Address update(Address entity)    { return addressRepository.update(entity); }
    @Override public boolean delete(Address entity)    { return addressRepository.delete(entity); } 
}
