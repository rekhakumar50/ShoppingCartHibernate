package com.example.ShoppingCart.service;

import com.example.ShoppingCart.dao.AccountDao;
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

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountDao accountDAO;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserAccount account = accountDAO.findByUserName(userName);
        if (account == null) {
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }
        List<GrantedAuthority> grantList = new ArrayList<>();
        // EMPLOYEE,MANAGER,..
        String role = account.getUserRole();
        // ROLE_EMPLOYEE, ROLE_MANAGER
        GrantedAuthority authority = new SimpleGrantedAuthority(role);
        grantList.add(authority);

        return new User(account.getUserName(),
                account.getPassword(), account.isActive(), true,
                true, true, grantList);
    }
}
