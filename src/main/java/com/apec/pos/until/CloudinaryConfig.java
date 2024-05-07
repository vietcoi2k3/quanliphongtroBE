package com.apec.pos.until;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    private final String CLOUD_NAME = "ddmwuvynh";
    private final String API_KEY = "819869879146171";
    private final String API_SECRET = "DUdbpwykA7yDvuHf8fjIg-Nd4p8";
    // Định nghĩa các hằng số chứa thông tin cấu hình để kết nối với Cloudinary

    @Bean
    // Đánh dấu phương thức này sẽ trả về một Bean được quản lý bởi Spring Container
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        // Tạo một Map để chứa cấu hình kết nối với Cloudinary
        config.put("cloud_name", CLOUD_NAME);
        config.put("api_key", API_KEY);
        config.put("api_secret", API_SECRET);
        // Thêm thông tin cấu hình vào Map

        return new Cloudinary(config);
        // Khởi tạo và trả về một đối tượng Cloudinary với cấu hình đã cung cấp
    }

}
