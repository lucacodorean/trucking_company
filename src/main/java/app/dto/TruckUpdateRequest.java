package app.dto;

import java.util.Date;

import javax.annotation.Nullable;
import lombok.Data;

@Data
public class TruckUpdateRequest {
    @Nullable private String numberPlate;
    @Nullable private Date lastPVI;
    @Nullable private String addressKey;
}
