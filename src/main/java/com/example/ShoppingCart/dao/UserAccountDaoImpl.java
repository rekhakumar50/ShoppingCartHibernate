package com.example.ShoppingCart.dao;

import com.example.ShoppingCart.model.UserAccount;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
@Transactional
public class UserAccountDaoImpl implements UserAccountDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<UserAccount> findByUserName(String userName) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserAccount> cr = cb.createQuery(UserAccount.class);
        Root<UserAccount> root = cr.from(UserAccount.class);
        cr.select(root).where(cb.equal(root.get("userName"), userName));

        Query<UserAccount> query = session.createQuery(cr);

        return Optional.ofNullable(query.getSingleResult());

    }
}
