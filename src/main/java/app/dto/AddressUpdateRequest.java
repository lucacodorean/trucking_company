package app.dto;

import javax.annotation.Nullable;
import lombok.Data;

@Data
public class AddressUpdateRequest {
    @Nullable private String   region;
    @Nullable private String     city;
    @Nullable private String   street;
    @Nullable private String  zipCode;
}

