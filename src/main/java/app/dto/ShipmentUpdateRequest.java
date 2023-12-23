package app.dto;

import javax.annotation.Nullable;
import lombok.Data;

@Data
public class ShipmentUpdateRequest {
    @Nullable  private Integer price;
    @Nullable  private Float   cargoHeight;
    @Nullable  private Float   distanceInKM;
    @Nullable  private String  description;
    @Nullable  private String  pickUpLocationKey;
    @Nullable  private String  destinationKey;
}
