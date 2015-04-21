/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.ejb.impl;

import com.cuongnt.qwap.ejb.ImageFileService;
import com.cuongnt.qwap.ejb.PostService;
import com.cuongnt.qwap.entity.Post;
import com.cuongnt.qwap.entity.PostCategory;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richard
 */
@Stateless
public class PostServiceBean extends AbstractFacadeBean<Post> implements PostService {

    private static final long serialVersionUID = -8540715854095525708L;
    private static final Logger logger = LoggerFactory.getLogger(PostServiceBean.class);

    @EJB
    private ImageFileService imageService;

    public PostServiceBean() {
        super(Post.class);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public Post findBySlug(String slug) {
        TypedQuery<Post> q = em.createNamedQuery("Post.findBySlug", Post.class);
        q.setParameter("slug", slug);
        return q.getSingleResult();
    }

    @Override
    public int countBySlug(String slug) {
        TypedQuery<Long> q = em.createNamedQuery("Post.countBySlug", Long.class);
        q.setParameter("slug", slug);
        return q.getSingleResult().intValue();
    }

    @Override
    protected void onAfterUpdate(Post entity) {
        super.onAfterUpdate(entity);
        saveFile(entity.getThumbnail());
    }

    @Override
    public List<Post> findByCategory(PostCategory category, int numberOfPosts) {
        TypedQuery<Post> q = em.createQuery("SELECT p FROM Post p WHERE p.category = :category", Post.class);
        EntityGraph<Post> graph = (EntityGraph<Post>) em.getEntityGraph("Post.showListEntityGraph");
        q.setParameter("category", category);
        q.setHint("javax.persistence.fetchgraph", graph);
        q.setMaxResults(numberOfPosts);
        return q.getResultList();
    }

    @Override
    public List<Post> getRelatePost(Post post, int numberOfPost) {
        TypedQuery<Post> q = em.createQuery("SELECT p FROM Post p WHERE p.category = :category AND p.id <> :id", Post.class);
        q.setParameter("category", post.getCategory());
        q.setParameter("id", post.getId());
        q.setMaxResults(numberOfPost);
        return q.getResultList();
    }

}
