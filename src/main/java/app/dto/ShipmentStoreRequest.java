package app.dto;

import javax.annotation.Nonnull;
import lombok.Data;

@Data
public class ShipmentStoreRequest {
    @Nonnull  private Integer price;
    @Nonnull  private Float   cargoHeight;
    @Nonnull  private Float   distanceInKM;
    @Nonnull  private String  description;
    @Nonnull  private String  pickUpLocationKey;
    @Nonnull  private String  destinationKey;
}
