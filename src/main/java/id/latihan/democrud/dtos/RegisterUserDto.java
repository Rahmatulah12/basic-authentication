package id.latihan.democrud.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterUserDto {
    private String fullName;

    private String email;

    private String password;

    private String userRole;

}
