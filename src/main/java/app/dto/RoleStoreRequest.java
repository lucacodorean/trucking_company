package app.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class RoleStoreRequest {
    @NonNull private Integer salary;
    @NonNull private String  position; 
}
