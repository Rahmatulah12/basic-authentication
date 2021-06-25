package id.latihan.democrud.services;

import id.latihan.democrud.entities.User;
import id.latihan.democrud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    // call bcryptpasword
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(
                        String.format("User with '%s' not found.", email)
                )
        );
    }

    public User registration(User user)
    {
        // encrypt user password
        String passwordEncrypt = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncrypt);
        //save data user
        return userRepository.save(user);
    }

    public User findByEmail(String email){
        User user = userRepository.findByEmail(email).orElse(null);
        return user;
    }
}
