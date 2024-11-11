package com.restaurant.users.infraestructur.driven_rp.adapter;

import com.restaurant.users.domain.interfaces.IEncoderPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
@Service
public class EncoderImpl implements IEncoderPort {

    private final PasswordEncoder passwordEncoder;


    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {

        return false;
    }
    @Override
    public String encode(CharSequence rawPassword) {
        String password= passwordEncoder.encode(rawPassword);
        return password;
    }


}