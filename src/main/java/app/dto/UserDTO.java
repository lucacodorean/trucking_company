package app.dto;

import app.models.Address;
import app.models.Role;
import lombok.Data;

@Data
public class UserDTO {
    private String  name;
    private String  phoneNumber;
    private String  modelKey;
    private Role    role;
    private Address address;
}
