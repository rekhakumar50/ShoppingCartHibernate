package com.example.ShoppingCart.dao;

import com.example.ShoppingCart.model.OrderDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class OrderDetailDaoImpl implements OrderDetailDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public List<OrderDetail> findOrderDetailByOrderId(Long orderId) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<OrderDetail> cr = cb.createQuery(OrderDetail.class);
        Root<OrderDetail> root = cr.from(OrderDetail.class);
        cr.select(root).where(cb.equal(root.get("order").get("id"), orderId));

        Query<OrderDetail> query = session.createQuery(cr);
        return query.getResultList();
    }

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Optional<OrderDetail> findByOrderIdAndProductCode(Long orderId, String productCode) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<OrderDetail> cr = cb.createQuery(OrderDetail.class);
        Root<OrderDetail> root = cr.from(OrderDetail.class);

        Predicate orderIdEquals = cb.equal(root.get("order").get("id"), orderId);
        Predicate productCodeEquals = cb.equal(root.get("product").get("code"), productCode);

        cr.select(root).where(cb.and(orderIdEquals, productCodeEquals));

        Query<OrderDetail> query = session.createQuery(cr);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public void deleteByOrderIdAndProduct_Code(Long orderId, String productCode) {
        Session session = sessionFactory.getCurrentSession();

        OrderDetail orderDetail = findByOrderIdAndProductCode(orderId,productCode)
                .orElseThrow(() -> new EntityNotFoundException("product Code or orderId is not found!"));

        session.delete(orderDetail);
    }

    @Override
    public void deleteByOrderId(Long orderId) {
        Session session = sessionFactory.getCurrentSession();
        List<OrderDetail> orderDetails = findOrderDetailByOrderId(orderId);
        orderDetails.forEach(session::delete);
    }

    @Override
    public void save(OrderDetail orderDetail) {
        Session session = sessionFactory.getCurrentSession();
        session.save(orderDetail);
    }

    @Override
    public void update(OrderDetail orderDetail) {
        Session session = sessionFactory.getCurrentSession();
        session.update(orderDetail);
    }

}
