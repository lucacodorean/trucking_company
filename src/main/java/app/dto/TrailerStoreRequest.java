package app.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class TrailerStoreRequest {
    @NonNull    private String  type;
    @NonNull    private Integer maximumCapacity;
    @NonNull    private String  plateNumber;
}
