/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.treess;

import weka.classifiers.trees.J48;
import weka.classifiers.trees.SimpleCart;

/**
 *
 * @author kosa1010
 */
public class TreeCARTj48 {

    private CART cart;
    private C45 c45;

    public CART getCart() {
        return cart;
    }

    public void setCart(CART cart) {
        this.cart = cart;
    }

    public C45 getC45() {
        return c45;
    }

    public void setC45(C45 c45) {
        this.c45 = c45;
    }
}
