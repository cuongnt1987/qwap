/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.entity;

/**
 *
 * @author richard
 */
public enum MobileType {

    Android, Ios, WindowPhone, Java;

    @Override
    public String toString() {
        switch (this) {
            case Android:
                return "android";
            case Ios:
                return "ios";
            case WindowPhone:
                return "window-phone";
            case Java:
                return "java";
            default:
                return "unknow";
        }
    }
}
