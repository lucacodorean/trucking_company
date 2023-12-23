package app.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
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
import app.dto.ShipmentUpdateRequest;
import app.dto.ShipmentStoreRequest;
import app.services.ShipmentService;
import app.services.AddressService;
import org.modelmapper.ModelMapper;
import app.models.Shipment;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shipments/")
public class ShipmentController {
    private ShipmentService shipmentService  = ServiceSinglePointAccess.getshShipmentService();
    private AddressService  addressService   = ServiceSinglePointAccess.getAddressService();
    private ModelMapper     modelMapper = new ModelMapper();

    @GetMapping("/")
    public ResponseEntity<List<Shipment>> index() {
        return ResponseEntity.status(HttpStatus.OK).body(shipmentService.findAll());
    }

    @GetMapping("/{shipmentKey}")
    ResponseEntity<Shipment> show(@PathVariable String shipmentKey) {
        return ResponseEntity.status(HttpStatus.OK).body(shipmentService.findByKey(shipmentKey));
}

    @PostMapping("/")
    ResponseEntity<Shipment> create(@RequestBody ShipmentStoreRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(shipmentService.save(
            convertDtoToEntity(request)
        ));
    }

    @PatchMapping("/{shipmentKey}")
    ResponseEntity<Shipment> update(
        @PathVariable String shipmentKey, @RequestBody ShipmentUpdateRequest request) {
            Shipment shipment = shipmentService.findByKey(shipmentKey);

            if(request.getPickUpLocationKey() != null) 
                shipment.setPickUpPoint(addressService.findByKey(request.getPickUpLocationKey()));

            if(request.getDestinationKey() != null) 
                shipment.setDestination(addressService.findByKey(request.getDestinationKey()));

            if(request.getPrice() != null)        shipment.setPrice(request.getPrice());
            if(request.getCargoHeight()  != null) shipment.setCargoHeight(request.getCargoHeight());
            if(request.getDescription()  != null) shipment.setDescription(request.getDescription());
            if(request.getDistanceInKM() != null) shipment.setDistanceInKM(request.getDistanceInKM());
            
            Shipment updatedShipment = shipmentService.update(shipment);
            return ResponseEntity.status(HttpStatus.OK).body(updatedShipment);
    }

    @DeleteMapping("/{shipmentKey}")
    public boolean delete(@PathVariable String shipmentKey) {
        return shipmentService.delete(shipmentService.findByKey(shipmentKey));
    }

    private Shipment convertDtoToEntity(ShipmentStoreRequest request) {
        if(request == null) return null;
        
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Shipment shipment = modelMapper.map(request, Shipment.class);
        shipment.setPickUpPoint(addressService.findByKey(request.getPickUpLocationKey()));
        shipment.setDestination(addressService.findByKey(request.getDestinationKey()));
        return shipment;
    }
}
