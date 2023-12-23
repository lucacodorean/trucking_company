package app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "trailers", schema =  "trucking_company")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
    @NamedQuery(name = "findTrailerByKey", query =  " from Trailer tra where tra.modelKey = :key"),
    @NamedQuery(name = "findTrailerById",  query =  " from Trailer tra where tra.id = :id"),
    @NamedQuery(name = "findAllTrailers",  query =  " from Trailer"),
})

public class Trailer implements Serializable, Model {
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @JsonIgnore 
    @Id 
    private Integer id;

    @Column                     private String  type;
    @Column                     private Integer maximumCapacity;
    @Column                     private String  plateNumber;
    @Column                     private String  modelKey = generateKey(this);
}
