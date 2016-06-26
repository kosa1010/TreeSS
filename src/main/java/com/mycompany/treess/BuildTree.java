package com.mycompany.treess;

import weka.core.Instances;

/**
 *
 * @author kosa1010
 */
public class BuildTree {

    public static enum TREE {
        C45, CART
    };
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
            System.out.println(ANSI_GREEN + "\n\n\tDRZEWA DECYZYJNE ZBUDOWANE "
                    + "ALGORYTMEM J48 DLA POSZCZEGÓLNYCH DECYZJI" + ANSI_RESET);
            for (int i = 0; i < data.numAttributes(); i++) {
                data.setClass(data.attribute(i));
                System.out.println(ANSI_PURPLE + "\t\tAtrybut decyzyjny  "
                        + data.attribute(i).name() + "\n" + ANSI_RESET);
                C45 c45 = new C45();
                c45.buildJ48(data, options);
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
            }
        }
    }
}
