package app.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserStoreRequest {
   @NonNull private String  name; 
   @NonNull private String  email;
   @NonNull private String  password;
   @NonNull private String  phoneNumber;
   @NonNull private String  roleKey;
   @NonNull private String  addressKey;
}
 