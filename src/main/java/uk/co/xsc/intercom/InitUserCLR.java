package uk.co.xsc.intercom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uk.co.xsc.intercom.entity.PhoneNumber;
import uk.co.xsc.intercom.entity.User;
import uk.co.xsc.intercom.repo.PhoneNumberRepo;
import uk.co.xsc.intercom.repo.UserRepo;

@Component
public class InitUserCLR implements CommandLineRunner {

    private final UserRepo userRepo;
    private final PhoneNumberRepo numberRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitUserCLR(UserRepo userRepo, PhoneNumberRepo numberRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.numberRepo = numberRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        System.out.println("InitUserCLR.run - user");

        String username = "info@xsc.co.uk";
        String password = "password";
            User user;
        if (!userRepo.findByEmail(username).isPresent()) {
            System.out.println("InitUserCLR.run - creating user");
            user = userRepo.save(new User(username, passwordEncoder.encode(password)));


        } else {
            user = userRepo.findByEmail(username).get();
        }


        System.out.println("InitUserCLR.run - number");



        String sid = "PN9e3e71642a5a28b7d7d2f81b056ab093";
        String number = "442045380800";

        if (!numberRepo.existsById(number)) {
            System.out.println("InitUserCLR.run - creating number");
            PhoneNumber pn = numberRepo.save(new PhoneNumber(number, user, sid));
            userRepo.findAndPushPhoneNumberByEmail(user.getEmail(), pn);
        }
    }
}
