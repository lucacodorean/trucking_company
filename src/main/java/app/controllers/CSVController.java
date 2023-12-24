package app.controllers;

import org.assertj.core.error.ShouldBeIn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.single_point_access.ServiceSinglePointAccess;
import net.bytebuddy.utility.RandomString;
import java.text.SimpleDateFormat;
import java.lang.reflect.Field;
import app.dto.CSVRequest;
import app.util.FileUtil;
import app.services.*;
import app.models.*;
import java.util.*;
import java.io.*;

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

    @PostMapping("/import")
    public ResponseEntity<Boolean> importModelfromCSV(@RequestBody CSVRequest request) {
        try {

            getFields(request.getClassName());

            File file = FileUtil.getAndCreateFileFromResourcesDirectory(request.getFileName());
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            String line = "";
            String[] attributes = {};
            String[] headers    = {};
            HashMap<String, String> dictionary = new HashMap<>();
    

            boolean header = true;
            while((line = bufferedReader.readLine()) != null) {
                attributes = line.split(",");
                
                if(header) {
                    headers = Arrays.copyOf(attributes, attributes.length);
                    header = false;
                    continue;
                }
                
                for(int i = 0; i<attributes.length; i++) dictionary.put(headers[i], attributes[i]);        
                System.out.println(dictionary.entrySet());
                

                if(objectClass instanceof User) {
                    ((User)objectClass).setName(dictionary.get("name"));
                    ((User)objectClass).setEmail(dictionary.get("email"));
                    ((User)objectClass).setPassword(dictionary.get("password"));
                    ((User)objectClass).setPhoneNumber(dictionary.get("phoneNumber"));
                    ((User)objectClass).setRole(roleService.findByKey(dictionary.get("role")));
                    ((User)objectClass).setAddress(addressService.findByKey(dictionary.get("address")));
                    ((User)objectClass).setEmploymentDate(new SimpleDateFormat("dd-mm-yyyy").parse(dictionary.get("employmentDate")));
                    ((User)objectClass).setModelKey(((User)objectClass).getClass().getSimpleName().toLowerCase().substring(0, 3) + "_" + RandomString.make(25));
                    userService.save((User)objectClass);
                }

                if(objectClass instanceof Role) {
                    ((Role)objectClass).setModelKey(((Role)objectClass).getClass().getSimpleName().toLowerCase().substring(0, 3) + "_" + RandomString.make(25));
                    ((Role)objectClass).setPosition(dictionary.get("position"));
                    ((Role)objectClass).setSalary(dictionary.get("salary"));
                    roleService.save((Role)objectClass);
                }

                if(objectClass instanceof Address) {
                    ((Address)objectClass).setCity(dictionary.get("city"));
                    ((Address)objectClass).setRegion(dictionary.get("region"));
                    ((Address)objectClass).setStreet(dictionary.get("street"));
                    ((Address)objectClass).setZipCode(dictionary.get("zipcode"));
                    ((Address)objectClass).setModelKey(((Address)objectClass).getClass().getSimpleName().toLowerCase().substring(0, 3) + "_" + RandomString.make(25));
                    addressService.save((Address)objectClass);
                }

                if(objectClass instanceof ActivityLog) {
                    ((ActivityLog)objectClass).setDateOfDelivery(new SimpleDateFormat("dd-mm-yyyy").parse(dictionary.get("dateOfDelivery")));
                    ((ActivityLog)objectClass).setModelKey(((ActivityLog)objectClass).getClass().getSimpleName().toLowerCase().substring(0, 3) + "_" + RandomString.make(25));
                    ((ActivityLog)objectClass).setShipment(shipmentService.findByKey(dictionary.get("shipment")));
                    ((ActivityLog)objectClass).setTrailer(trailerService.findByKey(dictionary.get("trailer")));
                    ((ActivityLog)objectClass).setTruck(truckService.findByKey(dictionary.get("truck")));
                    ((ActivityLog)objectClass).setUser(userService.findByKey(dictionary.get("user")));
                    activityService.save((ActivityLog)objectClass);
                }

                if(objectClass instanceof Trailer) {
                    ((Trailer)objectClass).setMaximumCapacity(Integer.parseInt(dictionary.get("maximumCapacity")));
                    ((Trailer)objectClass).setPlateNumber(dictionary.get("plateNumber"));
                    ((Trailer)objectClass).setType(dictionary.get("type"));
                    ((Trailer)objectClass).setModelKey(((Trailer)objectClass).getClass().getSimpleName().toLowerCase().substring(0, 3) + "_" + RandomString.make(25));
                    trailerService.save((Trailer)objectClass);
                }

                if(objectClass instanceof Truck) {
                    ((Truck)objectClass).setBrand(dictionary.get("brand"));
                    ((Truck)objectClass).setVin(dictionary.get("vin"));
                    ((Truck)objectClass).setNumberPlate(dictionary.get("numberPlate"));
                    ((Truck)objectClass).setAquiredAt(new SimpleDateFormat("dd-mm-yyyy").parse(dictionary.get("aquiredAt")));
                    ((Truck)objectClass).setLastPVI(new SimpleDateFormat("dd-mm-yyyy").parse(dictionary.get("lastPVI")));
                    ((Truck)objectClass).setHubAddress(addressService.findByKey(dictionary.get("hubAddress")));
                    ((Truck)objectClass).setModelKey(((Truck)objectClass).getClass().getSimpleName().toLowerCase().substring(0, 3) + "_" + RandomString.make(25));
                    truckService.save((Truck)objectClass);
                }
            
                if(objectClass instanceof Shipment) {
                    ((Shipment)objectClass).setDescription(dictionary.get("description"));
                    ((Shipment)objectClass).setPrice(Integer.parseInt(dictionary.get("price")));
                    ((Shipment)objectClass).setCargoHeight(Float.parseFloat(dictionary.get("cargoHeight")));
                    ((Shipment)objectClass).setDistanceInKM(Float.parseFloat(dictionary.get("distanceInKm")));
                    ((Shipment)objectClass).setDestination(addressService.findByKey(dictionary.get("destination")));
                    ((Shipment)objectClass).setPickUpPoint(addressService.findByKey(dictionary.get("pickUpPoint")));
                    ((Shipment)objectClass).setModelKey(((Shipment)objectClass).getClass().getSimpleName().toLowerCase().substring(0, 3) + "_" + RandomString.make(25));
                    shipmentService.save((Shipment)objectClass);
                }
            }
            
            bufferedReader.close();
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch(Exception ex) {
            System.out.println(ex.toString());
            return ResponseEntity.status(HttpStatus.OK).body(false);
        }
    }
}
