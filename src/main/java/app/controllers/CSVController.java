package app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.List;

import app.dto.CSVRequest;
import app.models.*;
import app.services.*;
import app.single_point_access.ServiceSinglePointAccess;
import app.util.FileUtil;

@RestController
@RequestMapping("/api/v1/csv/")
public class CSVController {
    
    private static final Field[] EMPTY_ARRAY = {};
    private static Object objectClass = new Object();

    private UserService         userService      = ServiceSinglePointAccess.getUserService();
    private RoleService         roleService      = ServiceSinglePointAccess.getRoleService();
    private TruckService        truckService     = ServiceSinglePointAccess.getTruckService();
    private AddressService      addressService   = ServiceSinglePointAccess.getAddressService();
    private TrailerService      trailerService   = ServiceSinglePointAccess.getTrailerService();
    private ShipmentService     shipmentService  = ServiceSinglePointAccess.getShipmentService();
    private ActivityLogService  activityService  = ServiceSinglePointAccess.getActivityLogService();

    private static Field[] getFields(String className) {      
        className = className.toLowerCase();
        switch (className) {
            case "user":
                objectClass = new User();
                return User.class.getDeclaredFields();
            case "role":    
                objectClass = new Role();
                return Role.class.getDeclaredFields();
            case "truck":       
                objectClass = new Truck();
                return Truck.class.getDeclaredFields();
            case "trailer":     
                objectClass = new Trailer();
                return Trailer.class.getDeclaredFields();
            case "shipment":
                objectClass = new Shipment();
                return Shipment.class.getDeclaredFields();
            case "address":     
                objectClass = new Address();
                return Address.class.getDeclaredFields();
            case "activitylog": 
                objectClass = new ActivityLog();
                return ActivityLog.class.getDeclaredFields();
            default:
                System.err.println("The class doesn't exist in the project.");
                break;
        }
        return EMPTY_ARRAY;
    }

    public String getCSVFileHeader(String className) {
        StringBuilder result = new StringBuilder();

        Field[] classFields = getFields(className);

        for(int i = 0; i<classFields.length; i++) {
            if(classFields[i].getName().compareTo("id") == 0 || 
               classFields[i].getName().compareTo("modelKey") == 0 ||
               classFields[i].getName().compareTo("activities") == 0
            ) continue;

            result.append(classFields[i].getName() + ',');
        }

        result.deleteCharAt(result.length()-1);

        return result.toString() + '\n';
    }

    private void writeDataInfile(FileWriter fileWriter) {
        try {
            String entityDetails = "";

            if(objectClass instanceof User) {
                List<User> users = userService.findAll();
                for(User currentEntity : users) {
                    entityDetails = currentEntity.getName() + "," + currentEntity.getEmail() + "," + currentEntity.getPassword() + "," +
                                    currentEntity.getPhoneNumber() + "," + currentEntity.getEmploymentDate() + "," + 
                                    currentEntity.getRole().getModelKey() + "," + currentEntity.getAddress().getModelKey() + '\n';
                    fileWriter.write(entityDetails);
                }
            }

            if(objectClass instanceof Role) {
                List<Role> roles = roleService.findAll();
                for(Role currentEntity : roles) {
                    entityDetails = currentEntity.getPosition() + "," + currentEntity.getSalary() + "\n";
                    fileWriter.write(entityDetails);
                }
            }

            if(objectClass instanceof Truck) {
                List<Truck> entityList = truckService.findAll();
                for(Truck currentEntity : entityList) {
                    entityDetails = currentEntity.getBrand() + "," + currentEntity.getModel() + "," + currentEntity.getNumberPlate() + "," +
                        currentEntity.getVin() + "," + currentEntity.getAquiredAt() + "," + currentEntity.getLastPVI() + "," + 
                        currentEntity.getHubAddress().getModelKey() + "\n";
                    fileWriter.write(entityDetails);
                }
            }

            if(objectClass instanceof Trailer) {
                List<Trailer> entityList = trailerService.findAll();
                for(Trailer currentEntity : entityList) {
                    entityDetails = currentEntity.getType() + "," + currentEntity.getMaximumCapacity() + "," + currentEntity.getPlateNumber() + "\n";
                    fileWriter.write(entityDetails);
                }
            }

            if(objectClass instanceof Shipment) {
                List<Shipment> entityList = shipmentService.findAll();
                for(Shipment currentEntity : entityList) {
                    entityDetails = currentEntity.getPrice() + "," + currentEntity.getCargoHeight() + "," + currentEntity.getDistanceInKM() + "," +
                        currentEntity.getDescription() + "," + currentEntity.getPickUpPoint().getModelKey() + "," + currentEntity.getDestination().getModelKey() + "\n";
                    fileWriter.write(entityDetails);
                }
            }

            if(objectClass instanceof Address) {
                List<Address> entityList = addressService.findAll();
                for(Address currentEntity : entityList) {
                    entityDetails = 
                        currentEntity.getCity() + "," + currentEntity.getRegion() + "," + currentEntity.getStreet() + "," + currentEntity.getZipCode() + "\n";
                    fileWriter.write(entityDetails);
                }
            }

            if(objectClass instanceof ActivityLog) {
                List<ActivityLog> entityList = activityService.findAll();
                for(ActivityLog currentEntity : entityList) {
                    entityDetails = 
                        currentEntity.getDateOfDelivery() + "," + currentEntity.getUser().getModelKey() + "," + 
                        currentEntity.getTruck().getModelKey() + "," + currentEntity.getTrailer().getModelKey() + "," +
                        currentEntity.getShipment().getModelKey() + "\n";
                    fileWriter.write(entityDetails);
                }
            }

        } catch (Exception ex) { System.out.println(ex.toString()); }
    }

    @PostMapping("/export")
    public ResponseEntity<Boolean> exportModelToCSV (@RequestBody CSVRequest request) {
        try {

            File file = FileUtil.getAndCreateFileFromResourcesDirectory(request.getFileName());

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(this.getCSVFileHeader(request.getClassName()));
            writeDataInfile(fileWriter);
            fileWriter.close();
            return ResponseEntity.status(HttpStatus.OK).body(true);

        } catch(Exception ex) { 
            System.out.println(ex.toString()); 
             return ResponseEntity.status(HttpStatus.OK).body(false);
        }
    }
}
