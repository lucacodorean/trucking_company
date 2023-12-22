package app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import app.single_point_access.ServiceSinglePointAccess;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import app.services.AddressService;
import org.modelmapper.ModelMapper;
import app.dto.TruckUpdateRequest;
import app.services.TruckService;
import app.dto.TruckStoreRequest;
import app.models.Truck;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trucks/")
public class TruckController {
    private TruckService    truckService     = ServiceSinglePointAccess.getTruckService();
    private AddressService  addressService   = ServiceSinglePointAccess.getAddressService();
    private ModelMapper     modelMapper = new ModelMapper();

    @GetMapping("/")
    public ResponseEntity<List<Truck>> index() {
        return ResponseEntity.status(HttpStatus.OK).body(truckService.findAll());
    }

    @GetMapping("/{truckKey}")
    ResponseEntity<Truck> show(@PathVariable String truckKey) {
        return ResponseEntity.status(HttpStatus.OK).body(truckService.findByKey(truckKey));
    }

    @PostMapping("/")
    ResponseEntity<Truck> create(@RequestBody TruckStoreRequest request) {
        if(request.getVin().length() != 17) return null;

        return ResponseEntity.status(HttpStatus.OK).body(truckService.save(
            convertDtoToEntity(request)
        ));
    }

    @PatchMapping("/{truckKey}")
    ResponseEntity<Truck> update(
        @PathVariable String truckKey, 
        @RequestBody TruckUpdateRequest request) {
            Truck truck = truckService.findByKey(truckKey);

            if(request.getAddressKey() != null) 
                truck.setHubAddress(addressService.findByKey(request.getAddressKey()));
            
            if(request.getLastPVI() != null)     truck.setLastPVI(request.getLastPVI());
            if(request.getNumberPlate() != null) truck.setNumberPlate(request.getNumberPlate());

            Truck updatedTruck = truckService.update(truck);
            return ResponseEntity.status(HttpStatus.OK).body(updatedTruck);
    }

    @DeleteMapping("/{truckKey}")
    public boolean delete(@PathVariable String truckKey) {
        return truckService.delete(truckService.findByKey(truckKey));
    }

    private Truck convertDtoToEntity(TruckStoreRequest request) {
        if(request == null) return null;
        
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Truck truck = modelMapper.map(request, Truck.class);
        truck.setHubAddress(addressService.findByKey(request.getAddressKey()));
        return truck;
    }
}
