package com.luojiawei.common.domain.dto;

import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class UserDTO {
    private Long id;
    private String openId;
    private String phone;
    private String name;
    private boolean verified;
    private Long hospitalPid;

    public static UserDTO parse(String str) {
        // If the input string is null or empty, return null
        if (str == null || str.trim().isEmpty()) {
            return null;
        }

        // Define the regular expression
        String regex = "\\{phone=(.*?), openId=(.*?), name=(.*?), verified=(.*?), hospitalPid=(.*?), id=(.*?)}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            // Extract field values
            String idStr = matcher.group(1);
            String openId = matcher.group(2);
            String phone = matcher.group(3);
            String name = matcher.group(4);
            String verifiedStr = matcher.group(5);
            String hospitalPidStr = matcher.group(6);

            // Create UserDTO instance
            UserDTO userDTO = new UserDTO();

            // Set id field
            if (idStr != null && !idStr.trim().isEmpty() && !"null".equals(idStr.trim())) {
                userDTO.setId(Long.parseLong(idStr.trim()));
            } else {
                userDTO.setId(null);
            }

            // Set other fields
            userDTO.setOpenId(openId != null && !openId.trim().isEmpty() ? openId.trim() : null);
            userDTO.setPhone(phone != null && !phone.trim().isEmpty() ? phone.trim() : null);
            userDTO.setName(name != null && !name.trim().isEmpty() ? name.trim() : null);
            userDTO.setVerified(verifiedStr != null && !verifiedStr.trim().isEmpty() && Boolean.parseBoolean(verifiedStr.trim()));
            userDTO.setHospitalPid(hospitalPidStr != null && !hospitalPidStr.trim().isEmpty() && !"null".equals(hospitalPidStr.trim()) ? Long.parseLong(hospitalPidStr.trim()) : null);

            return userDTO;
        }
        throw new IllegalArgumentException("Invalid input string");
    }

}