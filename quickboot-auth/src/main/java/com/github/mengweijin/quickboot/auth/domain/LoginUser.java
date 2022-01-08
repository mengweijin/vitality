package com.github.mengweijin.quickboot.auth.domain;

import com.github.mengweijin.quickboot.auth.data.entity.User;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.Authentication;
import java.io.Serializable;

/**
 * @author mengweijin
 * @date 2022/1/8
 */
@Data
@Accessors(chain = true)
public class LoginUser implements Serializable {

    private String username;

    private String nickname;

    public LoginUser convert(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        return this;
    }

}
