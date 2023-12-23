package app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import lombok.*;

@Entity
@Table(name = "activity_logs", schema =  "trucking_company")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
    @NamedQuery(name = "findActivityByKey", query =  " from ActivityLog act where act.modelKey = :key"),
    @NamedQuery(name = "findActivityById",  query =  " from ActivityLog act where act.id = :id"),
    @NamedQuery(name = "findAllActivities", query =  " from ActivityLog"),
})

public class ActivityLog implements Serializable, Model{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true) 
    @JsonIgnore
    @Id 
    private Integer id;

    @Column private Date    dateOfDelivery = generateDate();
    @Column private String  modelKey       = generateKey(this);

    @ManyToOne 
    @JoinColumn(name = "user", nullable = false) private User user;
    
    @OneToOne  private Truck     truck;
    @OneToOne  private Trailer   trailer;
    @OneToOne  private Shipment  shipment;
}
