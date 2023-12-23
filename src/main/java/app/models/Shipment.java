package app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "shipments", schema =  "trucking_company")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
    @NamedQuery(name = "findShipmentByKey", query =  " from Shipment shi where shi.modelKey = :key"),
    @NamedQuery(name = "findShipmentById",  query =  " from Shipment shi where shi.id = :id"),
    @NamedQuery(name = "findAllShipments",  query =  " from Shipment"),
})

public class Shipment implements Serializable, Model {
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @JsonIgnore 
    @Id 
    private Integer id;
    
    @Column private String  modelKey = generateKey(this);
    @Column private Integer price;
    @Column private Float   cargoHeight;
    @Column private Float   distanceInKM;
    @Column private String  description;
    
    @OneToOne private Address pickUpPoint;
    @OneToOne private Address destination;
}
