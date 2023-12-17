package app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name="roles", schema = "trucking_company")
@Data
@NamedQueries({ 
    @NamedQuery(name = "findRoleByKey", query =  " from Role role where role.modelKey = :key"),
    @NamedQuery(name = "findRoleById",  query =  " from Role role where role.id = :id"),
    @NamedQuery(name = "findAllRoles",  query =  " from Role")
})

public class Role implements Serializable, Model{
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @JsonIgnore 
    @Id 
    private Integer id;
    
    @Column(unique = true)  private String position;
    @Column                 private String salary;
    @Column @JsonIgnore     private String modelKey = generateKey(this);
}
