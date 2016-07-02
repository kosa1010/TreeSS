package com.mycompany.treess;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import weka.classifiers.Classifier;
import weka.core.Instances;
import java.lang.String;
import weka.classifiers.trees.J48;
import weka.core.Instance;

/**
 *
 * @author kosa1010
 */
public class BuildTree {

    public static enum TREE {
        C45, CART
    };

    private static final Instances complementData = new Data().getDataComplement();
    public static Instances instances;
    public static List<String[]> listPredictionsC45;
    public static List<TreeCARTj48> listOfTrees = new ArrayList<>();
    public static final String ANSI_RESET = "\u001B[0m";//OK
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";//OK
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";//OK
    public static final String ANSI_PURPLE = "\u001B[35m";//OK
    public static final String ANSI_CYAN = "\u001B[36m";//OK
    public static final String ANSI_WHITE = "\u001B[38m";

    /**
     * Wywołuje metode budującą dzewo dla ustawien wszystkich artybutów po kolei
     * jako decyzyjne dla wybranego ab]lgorytmu i podanych parametrów
     *
     * @param data
     * @param t
     * @param options
     * @throws Exception
     */
    public static void setAllAtrribAsDecision(Instances data, TREE t, String options) throws Exception {
        if (t == TREE.C45) {
            listPredictionsC45 = new ArrayList<>();
            System.out.println(ANSI_GREEN + "\n\n\tDRZEWA DECYZYJNE ZBUDOWANE "
                    + "ALGORYTMEM J48 DLA POSZCZEGÓLNYCH DECYZJI" + ANSI_RESET);
            for (int i = 0; i < data.numAttributes(); i++) {
                data.setClassIndex(i);
                System.out.print("Nowy atrybut decyzyjny " + data.classAttribute().name() + " o id " + i);
                System.out.println();
                System.out.println(ANSI_PURPLE + "\t\tAtrybut decyzyjny  "
                        + data.attribute(i).name() + "\n" + ANSI_RESET);
                C45 c45 = new C45();
//               
                
                c45.buildJ48(data, options);

                System.out.println("********************************************");
                classificationComplementOfData(c45, instances);
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
//                c45.TreeCARTj48 obj = new TreeCARTj48();
//                obj.setC45(c45);
//                listOfTrees.add(obj);
            }
        } else {
            System.out.println(ANSI_GREEN + "\n\n\tDRZEWA DECYZYJNE ZBUDOWANE"
                    + " ALGORYTMEM CART DLA POSZCZEGÓLNYCH DECYZJI" + ANSI_RESET);
            for (int i = 0; i < data.numAttributes(); i++) {
                data.setClass(data.attribute(i));
                System.out.println(ANSI_PURPLE + "\t\tAtrybut decyzyjny  "
                        + data.attribute(i).name() + "\n" + ANSI_RESET);
                CART cart = new CART();
                cart.buildCART(data, options);
                TreeCARTj48 obj = new TreeCARTj48();
                obj.setCart(cart);
                listOfTrees.add(obj);
            }
        }
    }

    /**
     * Klasyfikacja nowych obiektow dopełniających zbiór danych wejściowych
     *
     * @param c
     * @param inst
     * @return List<String[]> liste tablic stringów zawierającą
     * @throws Exception
     */
    public static String[] classificationComplementOfData(Classifier c, Instances inst) throws Exception {

        Instances unlabeledData = Data.loadData("./kombinacje.arff");
        unlabeledData.setClassIndex(unlabeledData.numAttributes() - 1);
        System.out.println("Liczba obikektów " + unlabeledData.numInstances());
        System.out.println("Liczba atrybutów " + unlabeledData.numAttributes());
        String[] arrDec = new String[unlabeledData.numInstances()];
        for (int i = 0; i < unlabeledData.numInstances(); i++) {
            //Klasyfikacja obiektu i wypisanie proponowanej przez drzewo decyzji
            System.out.println(c.classifyInstance(unlabeledData.instance(i)));

        }
        return arrDec;
    }

} 