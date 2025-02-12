package org.agromarket.agro_server.model.dto.admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InforUserDTO {
    private long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDateTime dateOfBirth;
    private String avatarUrl;
    private Integer orders;
    private Boolean isActive;
    private Boolean isDeleted;
}
