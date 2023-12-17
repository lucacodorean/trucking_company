package app.dto;

import org.springframework.lang.Nullable;
import lombok.Data;


@Data
public class UserUpdateRequest {
    @Nullable private String name;
    @Nullable private String email;
    @Nullable private String password;
    @Nullable private String phoneNumber;
}
