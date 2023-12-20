package app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import app.single_point_access.ServiceSinglePointAccess;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.modelmapper.ModelMapper;
import app.dto.AddressUpdateRequest;
import app.services.AddressService;
import app.dto.AddressStoreRequest;
import app.models.Address;
import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses/")
public class AddressController {
    
    private AddressService addressService = ServiceSinglePointAccess.getAddressService();
    private ModelMapper    modelMapper    = new ModelMapper();

     @GetMapping("/")
    public ResponseEntity<List<Address>> index() {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findAll());
    }

    @GetMapping("/{addressKey}")
    public ResponseEntity<Address> show(@PathVariable String addressKey) {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findByKey(addressKey));
    }

    @PostMapping("/")
    public ResponseEntity<Address> create(@RequestBody AddressStoreRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(
            addressService.save(convertDtoToEntity(request))
        );
    }

    @PatchMapping("/{addressKey}")
    public ResponseEntity<Address> update(
        @PathVariable String addressKey, 
        @RequestBody AddressUpdateRequest request) {
        
            Address address = addressService.findByKey(addressKey);
            if(address == null) return null;

            if(request.getCity() != null)   address.setCity(request.getCity());
            if(request.getRegion() != null) address.setRegion(request.getRegion());
            if(request.getStreet() != null) address.setStreet(request.getStreet());
            if(request.getZipCode() != null) address.setZipCode(request.getZipCode());

            Address updatedAddress = addressService.update(address);
            return ResponseEntity.status(HttpStatus.OK).body(updatedAddress);
    }

    @DeleteMapping("/{addressKey}")
    public boolean delete(@PathVariable String addressKey) {
        return addressService.delete(addressService.findByKey(addressKey));
    }

    private Address convertDtoToEntity(AddressStoreRequest request) {
        if(request == null) return null;
        
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(request, Address.class);
    }
}
