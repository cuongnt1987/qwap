/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.ejb.impl;

import com.cuongnt.qwap.checker.MobileChecker;
import com.cuongnt.qwap.ejb.ImageFileService;
import com.cuongnt.qwap.entity.Product;
import com.cuongnt.qwap.ejb.ProductService;
import com.cuongnt.qwap.entity.AppFile;
import com.cuongnt.qwap.entity.AppFile_;
import com.cuongnt.qwap.entity.BaseEntity_;
import com.cuongnt.qwap.entity.File_;
import com.cuongnt.qwap.entity.ImageFile;
import com.cuongnt.qwap.entity.MobileType;
import com.cuongnt.qwap.entity.ProductCategory;
import com.cuongnt.qwap.entity.ProductType;
import com.cuongnt.qwap.entity.Product_;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
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

    @EJB
    private ImageFileService imageService;

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
    public List<Product> getTopDownload(int numberOfItems, ProductType type, MobileChecker mobileChecker) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        Join<Product, AppFile> appFileJoin = root.join(Product_.appFiles, JoinType.LEFT);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.isTrue(root.get(Product_.enable)));

        if (type != null) {
            predicates.add(cb.equal(root.get(Product_.type), type));
        }

        // Check ios
        if (mobileChecker.isMobile()) {
            if (mobileChecker.isAndroid()) {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.Android));
            } else if (mobileChecker.isIos()) {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.Ios));
            } else if (mobileChecker.isWindowPhone()) {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.WindowPhone));
            } else {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.Java));
            }
        }
        predicates.add(cb.or(cb.isNotNull(appFileJoin.get(BaseEntity_.title)), cb.isNotNull(appFileJoin.get(File_.url))));

        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[]{}));
        }

        cq.distinct(true);

        cq.orderBy(cb.desc(root.get(Product_.downCount)));

        TypedQuery<Product> q = em.createQuery(cq);
        q.setMaxResults(numberOfItems);
        return q.getResultList();
    }

    @Override
    public List<Product> getTopHot(int numberOfItems, ProductType type, MobileChecker mobileChecker) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        Join<Product, AppFile> appFileJoin = root.join(Product_.appFiles, JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.isTrue(root.get(Product_.enable)));

        predicates.add(cb.isTrue(root.get(Product_.hot)));
        if (type != null) {
            predicates.add(cb.equal(root.get(Product_.type), type));
        }

        // Check ios
        if (mobileChecker.isMobile()) {
            if (mobileChecker.isAndroid()) {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.Android));
            } else if (mobileChecker.isIos()) {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.Ios));
            } else if (mobileChecker.isWindowPhone()) {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.WindowPhone));
            } else {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.Java));
            }
        }

        predicates.add(cb.or(cb.isNotNull(appFileJoin.get(BaseEntity_.title)), cb.isNotNull(appFileJoin.get(File_.url))));

        cq.where(predicates.toArray(new Predicate[]{}));
        cq.distinct(true);

        List<Order> orders = new ArrayList<>();

        cq.orderBy(cb.desc(root.get(Product_.priority)), cb.asc(root.get(BaseEntity_.createDate)));

        TypedQuery<Product> q = em.createQuery(cq);
        q.setMaxResults(numberOfItems);
        return q.getResultList();
    }

    @Override
    public List<Product> getTopLike(int numberOfItems, ProductType type, MobileChecker mobileChecker) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        Join<Product, AppFile> appFileJoin = root.join(Product_.appFiles, JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.isTrue(root.get(Product_.enable)));
        if (type != null) {
            predicates.add(cb.equal(root.get(Product_.type), type));
        }

        // Check ios
        if (mobileChecker.isMobile()) {
            if (mobileChecker.isAndroid()) {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.Android));
            } else if (mobileChecker.isIos()) {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.Ios));
            } else if (mobileChecker.isWindowPhone()) {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.WindowPhone));
            } else {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.Java));
            }
        }
        predicates.add(cb.or(cb.isNotNull(appFileJoin.get(BaseEntity_.title)), cb.isNotNull(appFileJoin.get(File_.url))));

        cq.where(predicates.toArray(new Predicate[]{}));
        cq.distinct(true);

        cq.orderBy(cb.desc(root.get(Product_.likeCount)));

        TypedQuery<Product> q = em.createQuery(cq);
        q.setMaxResults(numberOfItems);
        return q.getResultList();
    }

    @Override
    public List<Product> getTopNew(int numberOfItems, ProductType type, MobileChecker mobileChecker) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        Join<Product, AppFile> appFileJoin = root.join(Product_.appFiles, JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.isTrue(root.get(Product_.enable)));

        if (type != null) {
            predicates.add(cb.equal(root.get(Product_.type), type));
        }

        // Check ios
        if (mobileChecker.isMobile()) {
            if (mobileChecker.isAndroid()) {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.Android));
            } else if (mobileChecker.isIos()) {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.Ios));
            } else if (mobileChecker.isWindowPhone()) {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.WindowPhone));
            } else {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.Java));
            }
        }

        predicates.add(cb.or(cb.isNotNull(appFileJoin.get(BaseEntity_.title)), cb.isNotNull(appFileJoin.get(File_.url))));

        cq.where(predicates.toArray(new Predicate[]{}));
        cq.distinct(true);

        cq.orderBy(cb.desc(root.get(BaseEntity_.createDate)));

        TypedQuery<Product> q = em.createQuery(cq);
        q.setMaxResults(numberOfItems);
        return q.getResultList();
    }

    @Override
    public List<Product> getTopView(int numberOfItems, ProductType type, MobileChecker mobileChecker) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        Join<Product, AppFile> appFileJoin = root.join(Product_.appFiles, JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.isTrue(root.get(Product_.enable)));

        if (type != null) {
            predicates.add(cb.equal(root.get(Product_.type), type));
        }

        // Check ios
        if (mobileChecker.isMobile()) {
            if (mobileChecker.isAndroid()) {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.Android));
            } else if (mobileChecker.isIos()) {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.Ios));
            } else if (mobileChecker.isWindowPhone()) {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.WindowPhone));
            } else {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.Java));
            }
        }

        predicates.add(cb.or(cb.isNotNull(appFileJoin.get(BaseEntity_.title)), cb.isNotNull(appFileJoin.get(File_.url))));

        cq.where(predicates.toArray(new Predicate[]{}));
        cq.distinct(true);

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
    public List<Product> getRelateProduct(Product product, int numberOfItems, MobileChecker mobileChecker) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        Join<Product, AppFile> appFileJoin = root.join(Product_.appFiles, JoinType.LEFT);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.isTrue(root.get(Product_.enable)));
        predicates.add(cb.equal(root.get(Product_.type), product.getType()));
        predicates.add(cb.notEqual(root.get(BaseEntity_.id), product.getId()));

        // Check ios
        if (mobileChecker.isMobile()) {
            if (mobileChecker.isAndroid()) {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.Android));
            } else if (mobileChecker.isIos()) {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.Ios));
            } else if (mobileChecker.isWindowPhone()) {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.WindowPhone));
            } else {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.Java));
            }
        }
        predicates.add(cb.or(cb.isNotNull(appFileJoin.get(BaseEntity_.title)), cb.isNotNull(appFileJoin.get(File_.url))));
        cq.distinct(true);

        cq.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Product> q = em.createQuery(cq);
        q.setMaxResults(numberOfItems);
        return q.getResultList();
    }

    @Override
    protected void onAfterUpdate(Product entity) {
        super.onAfterUpdate(entity);
        saveFile(entity.getThumbnail());
        saveFile(entity.getBgFile());

        // Screenshort
        List<ImageFile> currents = null;
        try {
            currents = imageService.getByProduct(entity);
        } catch (Exception e) {
        }

        if (currents != null) {
            for (ImageFile image : currents) {
                if (!entity.getScreenshots().contains(image)) {
                    em.remove(image);
                }
            }
        }

        for (ImageFile image : entity.getScreenshots()) {
            saveFile(image);
        }

        // App file
        for (AppFile app : entity.getAppFiles()) {
            saveFile(app);
        }
    }

    @Override
    public List<Product> getByCategory(ProductCategory category, int numberOfItems, MobileChecker mobileChecker) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        Join<Product, AppFile> appFileJoin = root.join(Product_.appFiles, JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.isTrue(root.get(Product_.enable)));
        
        if (category != null) {
            predicates.add(cb.equal(root.get(Product_.category), category));
        }

        // Check ios
        if (mobileChecker.isMobile()) {
            if (mobileChecker.isAndroid()) {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.Android));
            } else if (mobileChecker.isIos()) {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.Ios));
            } else if (mobileChecker.isWindowPhone()) {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.WindowPhone));
            } else {
                predicates.add(cb.equal(appFileJoin.get(AppFile_.type), MobileType.Java));
            }
        }

        predicates.add(cb.or(cb.isNotNull(appFileJoin.get(BaseEntity_.title)), cb.isNotNull(appFileJoin.get(File_.url))));

        cq.where(predicates.toArray(new Predicate[]{}));
        cq.distinct(true);

        cq.orderBy(cb.desc(root.get(BaseEntity_.createDate)));

        TypedQuery<Product> q = em.createQuery(cq);
        q.setMaxResults(numberOfItems);
        return q.getResultList();
    }

    @Override
    public void toggleHot(Long productId) {
        Product product = em.find(Product.class, productId);
        if (product != null) {
            product.setHot(!product.isHot());
            em.merge(product);
            em.flush();
        }
    }

    @Override
    public void decreatePriority(Long productId) {
        Product product = em.find(Product.class, productId);
        if (product != null && product.getPriority() > 0) {
            product.setPriority(product.getPriority() - 1);
            em.merge(product);
            em.flush();
        }
    }

    @Override
    public void increatePriority(Long productId) {
        Product product = em.find(Product.class, productId);
        if (product != null) {
            product.setPriority(product.getPriority() + 1);
            em.merge(product);
            em.flush();
        }
    }

    @Override
    public void toogleEnable(Long productId) {
        Product product = em.find(Product.class, productId);
        if (product != null) {
            product.setEnable(!product.isEnable());
            em.merge(product);
            em.flush();
        }
    }
}
