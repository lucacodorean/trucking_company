package app.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class TruckStoreRequest {
    @NonNull private String brand;
    @NonNull private String model;
    @NonNull private String numberPlate;
    @NonNull private String vin;
    @NonNull private String addressKey;
}
