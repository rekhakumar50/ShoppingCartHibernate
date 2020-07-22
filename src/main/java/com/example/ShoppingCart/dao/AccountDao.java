package com.example.ShoppingCart.dao;

import com.example.ShoppingCart.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AccountDao extends JpaRepository<UserAccount, String> {

    UserAccount findByUserName(String userName);

}
