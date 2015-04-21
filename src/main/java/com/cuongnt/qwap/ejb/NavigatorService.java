/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.ejb;

import com.cuongnt.qwap.entity.Navigator;
import com.cuongnt.qwap.entity.Post;
import java.util.List;

/**
 *
 * @author richard
 */
public interface NavigatorService extends BaseService<Navigator> {

    public List<Navigator> getAppNavigators();

    public Navigator findBySlug(String slug);

    public int countBySlug(String slug);
}
