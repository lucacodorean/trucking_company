package app.dto;

import java.util.Date;
import lombok.NonNull;
import lombok.Data;

@Data
public class ActivityStoreRequest {
    @NonNull private String userKey;
    @NonNull private String truckKey;
    @NonNull private String trailerKey;
    @NonNull private String shipmentKey;
    @NonNull private Date   dateOfDelivery;
}