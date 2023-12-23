package app.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.dto.ActivityStoreRequest;
import app.dto.ActivityUpdateRequest;
import app.models.ActivityLog;
import app.services.ActivityLogService;
import app.services.ShipmentService;
import app.services.TrailerService;
import app.services.TruckService;
import app.services.UserService;
import app.single_point_access.ServiceSinglePointAccess;

@RestController
@RequestMapping("/api/v1/activities/")
public class ActivityLogController {
    private UserService     userService         = ServiceSinglePointAccess.getUserService();
    private TruckService    truckService        = ServiceSinglePointAccess.getTruckService();
    private TrailerService  trailerService      = ServiceSinglePointAccess.getTrailerService();
    private ShipmentService shipmentService     = ServiceSinglePointAccess.getshShipmentService();
    private ActivityLogService activityService  = ServiceSinglePointAccess.getaActivityLogService();
    private ModelMapper     modelMapper      = new ModelMapper();

    @GetMapping("/")
    public ResponseEntity<List<ActivityLog>> index() {
        return ResponseEntity.status(HttpStatus.OK).body(activityService.findAll());
    }

    @GetMapping("/{activityKey}")
    public ResponseEntity<ActivityLog> show(@PathVariable String activityKey) {
        return ResponseEntity.status(HttpStatus.OK).body(activityService.findByKey(activityKey));
    }

    @PostMapping("/")
    public ResponseEntity<ActivityLog> create(@RequestBody ActivityStoreRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(
            activityService.save(convertDtoToEntity(request)
        ));
    }

    @PatchMapping("/{activityKey}")
    ResponseEntity<ActivityLog>update(
        @PathVariable String activityKey, @RequestBody ActivityUpdateRequest request) {
        ActivityLog activityLog = activityService.findByKey(activityKey);
        if(activityLog == null) return null;

        if(request.getShipmentKey() != null) 
            activityLog.setShipment(shipmentService.findByKey(request.getShipmentKey()));

        if(request.getTrailerKey()  != null) 
            activityLog.setTrailer(trailerService.findByKey(request.getTrailerKey()));

        if(request.getTruckKey()    != null) 
            activityLog.setTruck(truckService.findByKey(request.getTruckKey()));
            
        if(request.getUserKey()     != null) 
            activityLog.setUser(userService.findByKey(request.getUserKey()));
      
        ActivityLog activityLogUpdated = activityService.update(activityLog);
        return ResponseEntity.status(HttpStatus.OK).body(activityLogUpdated);
    }

    @DeleteMapping("/{userKey}")
    public boolean delete(@PathVariable String userKey) {
        return userService.delete(userService.findByKey(userKey));
    }

    private ActivityLog convertDtoToEntity(ActivityStoreRequest request) {
        if( request == null || 
            userService.findByKey(request.getUserKey()) == null ||
            truckService.findByKey(request.getTruckKey()) == null ||
            trailerService.findByKey(request.getTrailerKey()) == null ||
            shipmentService.findByKey(request.getShipmentKey()) == null) return null;
        
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ActivityLog activityLog = modelMapper.map(request, ActivityLog.class);

        activityLog.setShipment(shipmentService.findByKey(request.getShipmentKey()));
        activityLog.setTrailer(trailerService.findByKey(request.getTrailerKey()));
        activityLog.setTruck(truckService.findByKey(request.getTruckKey()));
        activityLog.setUser(userService.findByKey(request.getUserKey()));
        return activityLog;
    }
}
