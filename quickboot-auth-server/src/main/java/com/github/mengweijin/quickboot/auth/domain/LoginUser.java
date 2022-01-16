package com.github.mengweijin.quickboot.auth.domain;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mengweijin
 * @date 2022/1/8
 */
@Data
@Accessors(chain = true)
public class LoginUser implements Serializable {

    private String username;

    /**
     * 如：ROLE_user_add, ROLE_user_delete
     */
    private List<String> roleList;

    public UserDetails createUserDetails() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(this.roleList)) {
            roleList.forEach(role -> authorityList.add(new SimpleGrantedAuthority(role)));
        }
        return new User(this.username, null, authorityList);
    }

}
