package app.dto;

import javax.annotation.Nullable;
import lombok.Data;

@Data
public class RoleUpdateRequest {
    @Nullable private String salary;
    @Nullable private String position;
}
