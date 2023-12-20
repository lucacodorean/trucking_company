package app.models;

import java.io.Serializable;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="addresses", schema = "trucking_company")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({ 
    @NamedQuery(name = "findAddressByKey",  query =  " from Address addr where addr.modelKey = :key"),
    @NamedQuery(name = "findAddressById",   query =  " from Address addr where addr.id = :id"),
    @NamedQuery(name = "findAllAddresses",  query =  " from Address"),
})
public class Address implements Serializable, Model {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true) 
    @JsonIgnore
    @Id 
    private Integer id;

    @Column private String city;
    @Column private String region;
    @Column private String street;
    @Column private String zipCode;
    @Column private String modelKey = generateKey(this);
}
