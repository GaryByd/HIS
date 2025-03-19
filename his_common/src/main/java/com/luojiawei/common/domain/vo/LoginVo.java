package com.luojiawei.common.domain.vo;



import com.luojiawei.common.domain.dto.UserDTO;
import lombok.Data;

@Data
public class LoginVo {
    private String token;
    private User user;

    public LoginVo(String token, UserDTO userDTO) {
        this.token = token;
        this.user = new User();
        this.user.setId(Math.toIntExact(userDTO.getId()));
        this.user.setPhone(userDTO.getPhone());
        this.user.setName(userDTO.getName());
        this.user.setVerified(userDTO.getVerified());
    }

    @Data
    public static class User {
        private int id;
        private String phone;
        private String name;
        private boolean verified;
    }
}