package app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;

import lombok.*;

@Entity
@Table(name="users", schema = "trucking_company")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({ 
    @NamedQuery(name = "findUserByKey",  query =  " from User usr where usr.modelKey = :key"),
    @NamedQuery(name = "findUserById",   query =  " from User usr where usr.id = :id"),
    @NamedQuery(name = "findAllUsers",   query =  " from User")
})

public class User implements Serializable, Model {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true) 
    @JsonIgnore
    @Id 
    private Integer id;

    @Column                 private String name;
    @Column(unique = true)  private String email;
    @Column                 private String password;
    @Column(unique = true)  private String phoneNumber;
    @Column                 private String modelKey         = generateKey(this);
    @Column @JsonIgnore     private Date   employmentDate   = generateDate();

    @OneToOne  private Role role;
    @OneToOne  private Address address;
    @OneToMany(mappedBy =  "user") @JsonIgnore private Set<ActivityLog> activities;
}