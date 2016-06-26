/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.treess;

import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.Utils;

/**
 *
 * @author kosa1010
 */
public class C45 {

    public static String options;
    public static String tree;

    /**
     * Metoda budująca drzewo algorytmem J48
     *
     * @param data
     * @param optionsSet
     * @throws Exception
     */
    public void buildJ48(Instances data, String optionsSet) throws Exception {
        String[] options = Utils.splitOptions(optionsSet);
        //   data.setClass("sdkfmwkmfcwkcm");
        data.setClassIndex(data.numAttributes() - 1);
        J48 j48 = new J48();
        j48.setOptions(options); //Ustawienie opcji
        j48.buildClassifier(data);  // Tworzenie klasyfikatora (drzewa)
        tree = j48.toString();
        j48.subtreeRaisingTipText();
        System.out.println(j48.toString());
    }
   
}
