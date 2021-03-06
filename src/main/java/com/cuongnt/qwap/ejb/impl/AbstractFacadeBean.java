/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.ejb.impl;

import com.cuongnt.qwap.entity.BaseEntity;
import com.cuongnt.qwap.ejb.BaseService;
import com.cuongnt.qwap.entity.File;
import com.cuongnt.qwap.exception.AppException;
import com.cuongnt.qwap.util.AppConfig;
import com.cuongnt.qwap.util.AppUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;

/**
 *
 * @author richard
 * @param <T>
 */
public abstract class AbstractFacadeBean<T extends BaseEntity> implements BaseService<T> {

    private static final long serialVersionUID = -3193947671333109472L;

    @PersistenceContext
    protected EntityManager em;

    protected final Class<T> entityClass;

    public AbstractFacadeBean(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T find(Object id) {
        return em.find(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        cq.select(cq.from(entityClass));
        TypedQuery<T> q = em.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public int countAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(entityClass)));
        TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult().intValue();
    }

    /* Callback method */
    protected void onBeforePersist(T entity) {
    }

    /* Callback method */
    protected void onAfterPersist(T entity) {
    }

    @Override
    public T persist(T entity) {
        onBeforePersist(entity);
        em.persist(entity);
        em.flush();
        onAfterPersist(entity);
        return entity;
    }

    /* Callback method */
    protected void onBeforeUpdate(T entity) {
    }

    /* Callback method */
    protected void onAfterUpdate(T entity) {
    }

    @Override
    public T update(T entity) {
        onBeforeUpdate(entity);
        em.merge(entity);
        em.flush();
        onAfterUpdate(entity);
        return entity;
    }

    /* Callback method */
    protected void onBeforeRemove(T entity) {
    }

    /* Callback method */
    protected void onAfterRemove(T entity) {
    }

    @Override
    public void remove(T entity) {
        onBeforeRemove(entity);
        em.remove(em.merge(entity));
        onAfterRemove(entity);
    }

    @Override
    public int count(Map<String, Object> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> root = cq.from(entityClass);
        cq.select(cb.count(root));

        List<Predicate> predicates = buildConditions(filters, root, cb);

        if (predicates != null && !predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[]{}));
        }

        TypedQuery<Long> q = em.createQuery(cq);
        return q.getSingleResult().intValue();
    }

    @Override
    public List<T> search(int start, int range, String sortField,
            boolean asc, Map<String, Object> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        cq.select(root);

        List<Predicate> predicates = buildConditions(filters, root, cb);

        if (predicates != null && !predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[]{}));
        }

        Order[] orders = buildOrder(sortField, asc, cb, root);

        if (orders != null && orders.length > 0) {
            cq.orderBy(orders);
        }

        TypedQuery<T> q = em.createQuery(cq);

        q.setFirstResult(start);
        q.setMaxResults(range);

        return q.getResultList();
    }

    protected List<Predicate> buildConditions(Map<String, Object> filters, Root<T> root, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (filters != null) {
            filters.entrySet().stream().map((entry) -> buildCondition(entry, root, cb)).filter((p) -> (p != null)).forEach((p) -> {
                predicates.add(p);
            });
        }

        return predicates;
    }

    protected Predicate buildCondition(Map.Entry<String, Object> entry, Root<T> root, CriteriaBuilder cb) {
        return cb.equal(root.get(entry.getKey()), entry.getValue());
    }

    protected Order[] buildOrder(String sortField, boolean asc, CriteriaBuilder cb, Root<T> root) {
        List<Order> orders = new ArrayList<>();

        if (sortField == null || sortField.isEmpty()) {
            orders.add(cb.desc(root.get("modifiedDate")));
        } else {
            if (asc) {
                orders.add(cb.asc(root.get(sortField)));
            } else {
                orders.add(cb.desc(root.get(sortField)));
            }
        }
        return orders.toArray(new Order[]{});
    }

    protected void saveFile(File file) {
        
        if (file.getPart() != null) {
            if (file.getTitle() != null) {
                FileUtils.deleteQuietly(new java.io.File(AppConfig.getFileStorePath()
                        + file.getId()));
            }

            file.setContentType(file.getPart().getContentType());
            file.setFileSize(file.getPart().getSize());
            file.setTitle(file.getPart().getSubmittedFileName());
            
            if (file.getId() != null) {
                em.merge(file);
            } else {
                em.persist(file);
            }
            
            em.flush();
            
            try {
                FileUtils.copyInputStreamToFile(file.getPart().getInputStream(), 
                        new java.io.File(AppConfig.getFileStorePath() + 
                                file.getId() + java.io.File.separator + 
                                file.getTitle()));
            } catch (IOException ex) {
                throw new AppException(null, "Cannot save file", ex);
            }
        }

    }

    protected abstract Logger getLogger();

}
