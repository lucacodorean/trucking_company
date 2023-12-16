package app.dto;

import app.models.Role;
import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String  name; 
    private String  phoneNumber;
    private Role    role;
    private String  key;
}
