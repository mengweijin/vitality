package com.github.mengweijin.quickboot.auth.domain;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author mengweijin
 * @date 2022/1/8
 */
@Data
@Accessors(chain = true)
public class LoginUser implements Serializable {

    private String username;

    private String uuid;

    /**
     * 如：ROLE_user_add, ROLE_user_delete
     */
    private List<String> roleList;

    public UserDetails createUserDetails() {
        return new LoginUserDetails().setUsername(this.username).setRoleList(this.roleList);
    }

    @Data
    @Accessors(chain = true)
    public static class LoginUserDetails implements UserDetails {

        private String username;

        private List<String> roleList;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
            if(CollectionUtil.isNotEmpty(this.roleList)) {
                roleList.forEach(role -> authorityList.add(new SimpleGrantedAuthority(role)));
            }
            return authorityList;
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public String getUsername() {
            return this.username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return false;
        }

        @Override
        public boolean isAccountNonLocked() {
            return false;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return false;
        }

        @Override
        public boolean isEnabled() {
            return false;
        }
    }

}
