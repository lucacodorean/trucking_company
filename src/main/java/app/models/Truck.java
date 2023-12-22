package app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "trucks", schema =  "trucking_company")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
    @NamedQuery(name = "findTruckByKey", query =  " from Truck trk where trk.modelKey = :key"),
    @NamedQuery(name = "findTruckById",  query =  " from Truck trk where trk.id = :id"),
    @NamedQuery(name = "findAllTrucks",  query =  " from Truck"),
})

public class Truck implements Serializable, Model{
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    @JsonIgnore
    @Id
    private Integer id;

    @Column                 private String  brand;
    @Column                 private String  model;
    @Column(unique =  true) private String  numberPlate;
    @Column(unique =  true) private String  vin;
    @Column                 private String  modelKey  = generateKey(this);
    @Column                 private Date    aquiredAt = generateDate();
    @Column                 private Date    lastPVI   = generateDate();

    @OneToOne private Address hubAddress;

}
