package id.latihan.democrud.controllers;

import id.latihan.democrud.dtos.RegisterUserDto;
import id.latihan.democrud.dtos.ResponseDataDto;
import id.latihan.democrud.entities.User;
import id.latihan.democrud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public ResponseEntity<ResponseDataDto<User>> register(@RequestBody RegisterUserDto dto)
    {
        ResponseDataDto<User> response = new ResponseDataDto<>();
        // cek email / user didatabase
        User userExists = userService.findByEmail(dto.getEmail());

        // jika user sudah ada
        if(userExists != null)
        {
            response.setPayload(null);
            response.setStatus(false);
            response.getMessage().add(String.format("User with email '%s' already exists",
                    dto.getEmail()));
            return ResponseEntity.ok(response);
        }
        // mapping data dto to entity user
        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.getUserRole().valueOf(dto.getUserRole());
        response.setPayload(userService.registration(user));
        response.setStatus(true);
        response.getMessage().add("Registration user has success.");
        return ResponseEntity.ok(response);
    }

}
