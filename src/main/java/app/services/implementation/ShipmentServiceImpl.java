package app.services.implementation;

import app.single_point_access.RepositorySinglePointAccess;
import org.springframework.stereotype.Service;
import app.repositories.ShipmentRepository;
import app.services.ShipmentService;
import app.models.Shipment;
import java.util.List;

@Service
public class ShipmentServiceImpl implements ShipmentService {
    private ShipmentRepository shipmentRepository = RepositorySinglePointAccess.getShipmentRepository();

    @Override public Shipment save(Shipment entity)     { return shipmentRepository.save(entity);  }
    @Override public List<Shipment> findAll()           { return shipmentRepository.findAll();     }
    @Override public Shipment findByKey(String key)     { return shipmentRepository.findByKey(key);}
    @Override public Shipment findById(Integer id)      { return shipmentRepository.findById(id);  }
    @Override public Shipment update(Shipment entity)   { return shipmentRepository.update(entity);}
    @Override public boolean delete(Shipment entity)    { return shipmentRepository.delete(entity);} 
}
