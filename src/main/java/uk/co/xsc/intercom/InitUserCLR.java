package uk.co.xsc.intercom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uk.co.xsc.intercom.entity.User;
import uk.co.xsc.intercom.repo.UserRepo;

@Component
public class InitUserCLR implements CommandLineRunner {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitUserCLR(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        System.out.println("InitUserCLR.run");

        String username = "info@xsc.co.uk";
        String password = "password";
        if (userRepo.findByEmail(username).isPresent()) {
            return;
        }

        System.out.println("InitUserCLR.run - creating user");

        User user = userRepo.save(new User(username, passwordEncoder.encode(password)));
    }
}
