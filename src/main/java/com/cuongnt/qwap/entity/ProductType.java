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
public enum ProductType {

    GAME, APP;

    @Override
    public String toString() {
        switch (this) {
            case APP:
                return "application";
            case GAME:
                return "game";
            default:
                return "unknow";
        }
    }
}
