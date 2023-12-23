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
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import app.dto.TrailerUpdateRequest;
import app.services.TrailerService;
import org.modelmapper.ModelMapper;
import app.dto.TrailerStoreRequest;
import app.models.Trailer;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trailers/")
public class TrailerController {

    private TrailerService trailerService = ServiceSinglePointAccess.getTrailerService();
    private ModelMapper    modelMapper    = new ModelMapper();

    @GetMapping("/")
    public ResponseEntity<List<Trailer>> index() {
        return ResponseEntity.status(HttpStatus.OK).body(trailerService.findAll());
    }

    @GetMapping("/{trailerKey}")
    public ResponseEntity<Trailer> show(@PathVariable String trailerKey) {
        return ResponseEntity.status(HttpStatus.OK).body(trailerService.findByKey(trailerKey));
    }

    
    @PostMapping("/")
    public ResponseEntity<Trailer> create(@RequestBody TrailerStoreRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(trailerService.save(convertDtoToEntity(request)));
    }

    @PatchMapping("/{trailerKey}")
    public ResponseEntity<Trailer> update(
        @PathVariable String trailerKey, 
        @RequestBody TrailerUpdateRequest request) {

        Trailer trailer = trailerService.findByKey(trailerKey);
        
        if(request.getMaximumCapacity() != null) trailer.setMaximumCapacity(request.getMaximumCapacity());
        if(request.getPlateNumber() != null) trailer.setPlateNumber(request.getPlateNumber());

        return ResponseEntity.status(HttpStatus.OK).body(trailerService.update(trailer));
    }

    @DeleteMapping("/{tailerKey}")
    public boolean delete(@PathVariable String trailerKey) {
        return trailerService.delete(trailerService.findByKey(trailerKey));
    }

    private Trailer convertDtoToEntity(TrailerStoreRequest request) {
        if(request == null) return null;
    
        return modelMapper.map(request, Trailer.class);
    }
}
