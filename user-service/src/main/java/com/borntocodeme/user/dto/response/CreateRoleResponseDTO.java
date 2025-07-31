package com.borntocodeme.user.dto.response;

import com.borntocodeme.common.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateRoleResponseDTO extends BaseDTO {
    private Long id;
    private String name;
    private String description;
    private String status;
}
