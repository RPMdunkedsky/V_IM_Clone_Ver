package com.v.im.user.conf;

import com.v.im.user.entity.ImUser;
import com.v.im.user.service.IImUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    @Qualifier(value = "imUserService")
    private IImUserService iImUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ImUser user = iImUserService.getByLoginName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return new org.springframework.security.core.userdetails.User(username,
                user.getPassword(),
                true,
                true,
                true,
                true,
                getGrantedAuthority(user));
    }

    /**
     * 获取用户的权限  Authority
     *
     * @param user user
     * @return List
     */
    public List<GrantedAuthority> getGrantedAuthority(ImUser user) {
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("user"));
        return list;
    }


}
