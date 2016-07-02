/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.treess;

import weka.classifiers.trees.SimpleCart;
import weka.core.Instances;
import weka.core.Utils;

/**
 *
 * @author kosa1010
 */
public class CART extends SimpleCart {

    public static String options;
    public static String tree;

    /**
     * Metoda budująca drzewo algorytmem J48
     *
     * @param data
     * @param optionsSet
     * @throws Exception
     */
    public void buildCART(Instances data, String optionsSet) throws Exception {
        String[] options = Utils.splitOptions(optionsSet);
        data.setClassIndex(data.numAttributes() - 1);
        SimpleCart cart = new SimpleCart();
        cart.setOptions(options); //Ustawienie opcji
        cart.buildClassifier(data);  // Tworzenie klasyfikatora (drzewa)
        tree = cart.toString();
        //cart.subtreeRaisingTipText();
        System.out.println(cart.toString());
    }

}
