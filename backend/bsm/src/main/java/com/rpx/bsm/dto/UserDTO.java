package com.rpx.bsm.dto;

import com.rpx.bsm.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
public class UserDTO  implements Serializable {
    private Long id;

    public UserDTO(User obj) {
        setId(obj.getId());
    }
}
