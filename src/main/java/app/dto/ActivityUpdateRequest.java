package app.dto;

import javax.annotation.Nullable;
import java.util.Date;
import lombok.Data;

@Data
public class ActivityUpdateRequest {
    @Nullable private String userKey;
    @Nullable private String truckKey;
    @Nullable private String trailerKey;
    @Nullable private String shipmentKey;
    @Nullable private Date   dateOfDelivery;
}
