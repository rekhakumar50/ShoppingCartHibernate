package com.example.ShoppingCart.dao;

import com.example.ShoppingCart.model.UserAccount;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountDao {

    Optional<UserAccount> findByUserName(String userName);

}
