package com.example.ShoppingCart.dao;

import com.example.ShoppingCart.model.UserAccount;

import java.util.Optional;

public interface UserAccountDao {

    Optional<UserAccount> findByUserName(String userName);

}
