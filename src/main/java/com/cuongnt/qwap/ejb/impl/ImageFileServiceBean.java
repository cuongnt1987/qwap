/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.ejb.impl;

import com.cuongnt.qwap.ejb.ImageFileService;
import com.cuongnt.qwap.entity.ImageFile;
import com.cuongnt.qwap.entity.Product;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richard
 */
@Stateless
public class ImageFileServiceBean extends AbstractFacadeBean<ImageFile> implements ImageFileService {

    private static final long serialVersionUID = -6238795633281162196L;
    private static final Logger logger = LoggerFactory.getLogger(ImageFileServiceBean.class);

    public ImageFileServiceBean() {
        super(ImageFile.class);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public List<ImageFile> getByProduct(Product product) {
        TypedQuery<ImageFile> q = em.createQuery("SELECT img FROM ImageFile img WHERE img.product = :product", entityClass);
        q.setParameter("product", product);
        return q.getResultList();
    }

}
