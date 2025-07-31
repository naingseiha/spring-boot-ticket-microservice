package com.borntocodeme.user.dto.request;

import com.borntocodeme.common.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateRoleRequestDTO extends BaseDTO {
    private Long id;
    private String name;
    private String description;
    private String status;
}
