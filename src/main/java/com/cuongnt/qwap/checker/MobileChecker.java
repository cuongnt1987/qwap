/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.checker;

import java.io.Serializable;

/**
 *
 * @author richard
 */
public interface MobileChecker extends Serializable {

    public boolean isMobile();

    public boolean isIos();

    public boolean isAndroid();

    public boolean isWindowPhone();

    public int getOsCode();
}
