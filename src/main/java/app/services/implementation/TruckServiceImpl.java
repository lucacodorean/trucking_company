package app.services.implementation;

import java.util.List;
import app.models.Truck;
import app.services.TruckService;
import app.repositories.TruckRepository;
import org.springframework.stereotype.Service;
import app.single_point_access.RepositorySinglePointAccess;

@Service
public class TruckServiceImpl implements TruckService{
    private TruckRepository truckRepository = RepositorySinglePointAccess.getTruckRepository();

    @Override public Truck save(Truck entity)        { return truckRepository.save(entity);   }
    @Override public List<Truck> findAll()           { return truckRepository.findAll();      }
    @Override public Truck findByKey(String key)     { return truckRepository.findByKey(key); }
    @Override public Truck findById(Integer id)      { return truckRepository.findById(id);   }
    @Override public Truck update(Truck entity)      { return truckRepository.update(entity); }
    @Override public boolean delete(Truck entity)    { return truckRepository.delete(entity); } 
}
