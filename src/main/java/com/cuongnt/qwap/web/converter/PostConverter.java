/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.converter;

import com.cuongnt.qwap.ejb.BaseService;
import com.cuongnt.qwap.ejb.PostService;
import com.cuongnt.qwap.entity.Post;
import javax.ejb.EJB;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author richard
 */
@FacesConverter("postConverter")
public class PostConverter extends AbstractEntityConverter<Post> {

    @EJB
    private PostService service;

    @Override
    protected BaseService<Post> getBaseService() {
        return service;
    }

    @Override
    protected Class<Post> getEntityClass() {
        return Post.class;
    }

}
