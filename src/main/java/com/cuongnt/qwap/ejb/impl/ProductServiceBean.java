/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.ejb.impl;

import com.cuongnt.qwap.entity.Product;
import com.cuongnt.qwap.ejb.ProductService;
import com.cuongnt.qwap.entity.ProductType;
import com.cuongnt.qwap.entity.Product_;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richard
 */
@Stateless
public class ProductServiceBean extends AbstractFacadeBean<Product> implements ProductService {

    private static final long serialVersionUID = 8729142584799323263L;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceBean.class);

    public ProductServiceBean() {
        super(Product.class);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public Product findBySlug(String slug) {
        TypedQuery<Product> q = em.createNamedQuery("Product.findBySlug", Product.class);
        q.setParameter("slug", slug);
        return q.getSingleResult();
    }

    @Override
    public int countBySlug(String slug) {
        TypedQuery<Long> q = em.createNamedQuery("Product.countBySlug", Long.class);
        q.setParameter("slug", slug);
        return q.getSingleResult().intValue();
    }

    @Override
    public List<Product> getTopDownload(int numberOfItems, ProductType type) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);

        if (type != null) {
            cq.where(cb.equal(root.get(Product_.type), type));
        }

        cq.orderBy(cb.desc(root.get(Product_.downCount)));

        TypedQuery<Product> q = em.createQuery(cq);
        q.setMaxResults(numberOfItems);
        return q.getResultList();
    }

    @Override
    public List<Product> getTopHot(int numberOfItems, ProductType type) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get(Product_.hot), true));
        if (type != null) {
            predicates.add(cb.equal(root.get(Product_.type), type));
        }

        cq.where(predicates.toArray(new Predicate[]{}));

        cq.orderBy(cb.desc(root.get(Product_.createDate)));

        TypedQuery<Product> q = em.createQuery(cq);
        q.setMaxResults(numberOfItems);
        return q.getResultList();
    }

    @Override
    public List<Product> getTopLike(int numberOfItems, ProductType type) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);

        if (type != null) {
            cq.where(cb.equal(root.get(Product_.type), type));
        }

        cq.orderBy(cb.desc(root.get(Product_.likeCount)));

        TypedQuery<Product> q = em.createQuery(cq);
        q.setMaxResults(numberOfItems);
        return q.getResultList();
    }

    @Override
    public List<Product> getTopNew(int numberOfItems, ProductType type) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);

        if (type != null) {
            cq.where(cb.equal(root.get(Product_.type), type));
        }

        cq.orderBy(cb.desc(root.get(Product_.createDate)));

        TypedQuery<Product> q = em.createQuery(cq);
        q.setMaxResults(numberOfItems);
        return q.getResultList();
    }

    @Override
    public List<Product> getTopView(int numberOfItems, ProductType type) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);

        if (type != null) {
            cq.where(cb.equal(root.get(Product_.type), type));
        }

        cq.orderBy(cb.desc(root.get(Product_.viewCount)));

        TypedQuery<Product> q = em.createQuery(cq);
        q.setMaxResults(numberOfItems);
        return q.getResultList();
    }

    @Override
    public void updateViewCount(Long productId) {
        Product product = em.find(Product.class, productId);
        if (product != null) {
            product.setViewCount(product.getViewCount() + 1);
            em.merge(product);
            em.flush();
        }

    }

    @Override
    public void updateLikeCount(Long productId) {
        Product product = em.find(Product.class, productId);
        if (product != null) {
            product.setLikeCount(product.getLikeCount() + 1);
            em.merge(product);
            em.flush();
        }
    }

    @Override
    public void updateDownCount(Long productId) {
        Product product = em.find(Product.class, productId);
        if (product != null) {
            product.setDownCount(product.getDownCount() + 1);
            em.merge(product);
            em.flush();
        }
    }

    @Override
    public List<Product> getRelateProduct(Product product, int numberOfItems) {
        TypedQuery<Product> q = em.createQuery("SELECT p FROM Product p WHERE p.type = :type AND p.id != :id", Product.class);
        q.setParameter("type", product.getType());
        q.setParameter("id", product.getId());
        q.setMaxResults(numberOfItems);
        return q.getResultList();
    }
}
