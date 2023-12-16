package app.models;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="roles", schema = "trucking_company")
@Data
@NamedQueries({ 
    @NamedQuery(name = "findRoleByKey", query =  " from Role role where role.modelKey = :key"),
    @NamedQuery(name = "findRoleById",  query =  " from Role role where role.id = :id"),
    @NamedQuery(name = "findAllRoles",  query =  " from Role")
})

public class Role implements Serializable, Model{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer id;
    @Column private String position;
    @Column private String salary;
    @Column private String modelKey;
}
