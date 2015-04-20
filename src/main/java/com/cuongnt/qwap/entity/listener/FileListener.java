/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.entity.listener;

import com.cuongnt.qwap.entity.File;
import javax.persistence.PostUpdate;

/**
 *
 * @author richard
 */
public class FileListener {
    
    @PostUpdate
    private void postUpdate(File file) {
        System.out.println("Fuck you");
    }
}
