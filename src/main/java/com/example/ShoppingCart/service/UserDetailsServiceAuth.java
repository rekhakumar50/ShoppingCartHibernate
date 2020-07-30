package com.example.ShoppingCart.service;

import com.example.ShoppingCart.dao.UserAccountDao;
import com.example.ShoppingCart.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceAuth implements UserDetailsService {

    @Autowired
    private UserAccountDao userAccountDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<UserAccount> account = userAccountDao.findByUserName(userName);

        if (account.isEmpty()) {
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }
        UserAccount userAccount = account.get();
        List<GrantedAuthority> grantList = new ArrayList<>();
        // EMPLOYEE,MANAGER,..
        String role = userAccount.getUserRole();
        // ROLE_EMPLOYEE, ROLE_MANAGER
        GrantedAuthority authority = new SimpleGrantedAuthority(role);
        grantList.add(authority);

        return new User(userAccount.getUserName(),
                userAccount.getPassword(), userAccount.isActive(), true,
                true, true, grantList);
    }
}
