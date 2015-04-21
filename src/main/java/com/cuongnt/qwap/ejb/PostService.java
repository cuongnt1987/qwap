/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.ejb;

import com.cuongnt.qwap.entity.Post;
import com.cuongnt.qwap.entity.PostCategory;
import java.util.List;

/**
 *
 * @author richard
 */
public interface PostService extends BaseService<Post> {

    public Post findBySlug(String slug);

    public int countBySlug(String slug);

    public List<Post> findByCategory(PostCategory category, int numberOfPosts);
    
    public List<Post> getRelatePost(Post post, int numberOfPost);
}
