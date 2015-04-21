/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.ejb;

import com.cuongnt.qwap.entity.AppConfig;

/**
 *
 * @author richard
 */
public interface AppConfigService extends BaseService<AppConfig> {

    public AppConfig getCurrentConfig();

}
