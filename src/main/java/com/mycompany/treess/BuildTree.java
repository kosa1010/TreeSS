package com.mycompany.treess;

import java.util.ArrayList;
import java.util.List;
import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.SimpleCart;
import weka.core.Utils;

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
                System.out.println(ANSI_PURPLE + "\t\tAtrybut decyzyjny  "
                        + data.attribute(i).name() + "\n" + ANSI_RESET);
                C45 c45 = new C45();
                J48 j48 = c45.buildJ48(data, options);
                System.out.println(ANSI_RED + "Stan faktyczny a proponowane decyzje "
                        + ANSI_RESET + "" + ANSI_CYAN + " Poprawność klasyfikacji" + ANSI_RESET);
                classificationComplementOfData(TREE.C45, j48, data, complementData, Utils.splitOptions(options), i);
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
                System.out.println(ANSI_RED + "Stan faktyczny a proponowane decyzje "
                        + ANSI_RESET + "" + ANSI_CYAN + " Poprawność klasyfikacji" + ANSI_RESET);
                classificationComplementOfData(TREE.CART, cart, data, complementData, Utils.splitOptions(options), i);
            }
        }
    }

    /**
     * Klasyfikacja nowych obiektow dopełniających zbiór danych wejściowych
     *
     * @param t
     * @param c
     * @param data
     * @param inst
     * @param options
     * @param atr
     * @return
     * @throws Exception
     */
    public static String[] classificationComplementOfData(TREE t, Classifier c, Instances data, Instances inst, String[] options, int atr) throws Exception {

        String[] arrDec = new String[inst.numInstances()];
        Instances trainData = Data.loadData(MainClass.path);
        trainData.setClassIndex(trainData.numAttributes() - 1);
        Instances unlabeledData = Data.loadData("./kombinacje.arff");
        if (t == TREE.C45) {
            J48 tree = new J48();
            tree.setOptions(options);
            tree.buildClassifier(trainData);
            unlabeledData.setClassIndex(unlabeledData.numAttributes() - 1);
            Instances labeled = new Instances(unlabeledData);
            for (int i = 0; i < unlabeledData.numInstances(); i++) {
                //Klasyfikacja obiektu
                double decision = tree.classifyInstance(unlabeledData.instance(i));
                labeled.instance(i).setClassValue(decision);
                int decClasyfier = (int) decision;
                arrDec[i] = String.valueOf(decClasyfier);
                int decFromTable = (int) (unlabeledData.instance(i).toDoubleArray())[atr];
                String correct = "X";
                if (decClasyfier == decFromTable) {
                    correct = "OK";
                }
                System.out.println("Prop. decyzja = " + decClasyfier + "\tDEC = "
                        + decFromTable + "\t\t\t" + ANSI_CYAN + correct + ANSI_RESET);
            }
        } else {
            SimpleCart tree = new SimpleCart();
            tree.setOptions(options);
            tree.buildClassifier(trainData);
            unlabeledData.setClassIndex(unlabeledData.numAttributes() - 1);
            Instances labeled = new Instances(unlabeledData);
            for (int i = 0; i < unlabeledData.numInstances(); i++) {
                //Klasyfikacja obiektu
                double decision = tree.classifyInstance(unlabeledData.instance(i));
                labeled.instance(i).setClassValue(decision);
                int decClasyfier = (int) decision;
                arrDec[i] = String.valueOf(decClasyfier);
                int decFromTable = (int) (unlabeledData.instance(i).toDoubleArray())[atr];
                String correct = "X";
                if (decClasyfier == decFromTable) {
                    correct = "OK";
                }
                System.out.println("Prop. decyzja = " + decClasyfier + "\tDEC = "
                        + decFromTable + "\t\t\t" + ANSI_CYAN + correct + ANSI_RESET);
            }
        }
        return arrDec;
    }
}
