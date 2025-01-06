package com.example.fs;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordEncoderTest {

    @Test
    public void testPasswordEncoding() {
        // 创建 BCryptPasswordEncoder 实例
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        // 要加密的密码
        String rawPassword = "123456";
        
        // 对密码进行加密
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        // 输出加密后的密码
        System.out.println("Encoded Password: " + encodedPassword);
        
        // 验证密码是否正确
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }
}
