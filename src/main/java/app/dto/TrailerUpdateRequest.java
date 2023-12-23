package app.dto;


import javax.annotation.Nullable;
import lombok.Data;

@Data
public class TrailerUpdateRequest {
    @Nullable   private Integer maximumCapacity;
    @Nullable   private String plateNumber;
}
