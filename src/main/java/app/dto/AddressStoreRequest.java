package app.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class AddressStoreRequest {
    @NonNull private String   region;
    @NonNull private String     city;
    @NonNull private String   street;
    @NonNull private String  zipCode;
}

