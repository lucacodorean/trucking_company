package app.models;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.NamedQueries;

@Entity
@Table(name="users", schema = "trucking_company")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({ 
    @NamedQuery(name = "findUserByKey", query =  " from User usr where usr.modelKey = :key"),
    @NamedQuery(name = "findUserById",  query =  " from User usr where usr.id = :id"),
    @NamedQuery(name = "findAllUsers",  query =  " from User")
})

public class User implements Serializable, Model {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true) 
    @Id 
    private Integer id;

    @Column private String name;
    @Column private String email;
    @Column private String password;
    @Column private String phoneNumber;
    @Column private String modelKey;
    @Column private Date   employmentDate;

    @OneToOne (cascade = CascadeType.ALL) private Role role;
}

