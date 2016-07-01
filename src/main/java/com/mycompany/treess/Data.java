/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.treess;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

/**
 * @author kosa1010
 */
public class Data {

    private static Instances data;
    private static Instances dataWithNewInstances;
    public static int[][] dataInTable;
    public static int[][] permutation;
    public static int[][] dataPerm;
    public static List<Set> listOfAtribValue;
    public static List<List<Integer>> listOfAtributesValue;
    public static Set<List<Integer>> permutate;
    public static int numOfInstances;

    /**
     * Zwraca dane
     *
     * @return
     */
    public Instances getData() {
        return data;
    }

    /**
     * Zwraca dopełnienie do pierwotnie wczytanych danych
     *
     * @return
     */
    public Instances getDataComplement() {
        return dataWithNewInstances;
    }

    /**
     * Wpisuje instancje będące dopełnieniem wczytanych danych
     *
     * @param instances
     */
    public void setDataComplement(Instances instances) {
        dataWithNewInstances = instances;
    }

    /**
     * Ładuje dane z pliku
     *
     * @param filePath
     * @throws IOException
     */
    public void loadData(String filePath) throws IOException {
        ArffLoader loader = new ArffLoader();
        loader.setFile(new File(filePath));
        data = loader.getDataSet();
        numOfInstances = data.numInstances();
        System.out.println("\t\t\tWczytane dane\n");
        for (int i = 0; i < data.numInstances(); i++) {
            System.out.println("\t\t\t" + data.instance(i).toString());
        }
    }

    /**
     * Przpisuje dane z obiektu instances do tablicy dwuwymiarowej
     *
     * @param data
     * @return
     */
    public int[][] dataFromInnstancesToTable(Instances data) {
        String buf[];
        int[] obj;
        List<int[]> listObj = new ArrayList<>();
        for (int i = 0; i < data.numInstances(); i++) {
            buf = data.instance(i).toString().split(",");
            obj = new int[buf.length];
            for (int k = 0; k < buf.length; k++) {
                obj[k] = Integer.valueOf(buf[k]);
            }
            listObj.add(obj);
        }
        dataInTable = listObj.toArray(new int[][]{});
        return listObj.toArray(new int[][]{});
    }

    /**
     * Przpisuje dane z tablicy dwuwyimaroweje do obiektu instances
     *
     * @param dataTable
     * @return
     * @throws java.io.IOException
     */
    public Instances dataFromTableToInstances(int[][] dataTable) throws IOException, Exception {

        FastVector attributes = new FastVector(); //utworzenie listy atrybutow dla nowej tablicy
        for (int i = 0; i < data.numAttributes() - 1; i++) {
            attributes.addElement(new Attribute(data.attribute(i).name()));
        }
        FastVector labels = new FastVector(); //Utworzenie wartosci nowego atrybutu symbolicznego

        System.out.println("****************************************************************");

        int tab[] = listToTab(listOfAtribValue.get(data.numAttributes() - 1));//zamiana niepowtzarzalnych wartości atrybutu decyzyjnego na tablice

        for (int s : tab) {
            labels.addElement(String.valueOf(s));
        }
        Attribute dec = new Attribute(data.attribute(data.numAttributes() - 1).name(), labels); //Utworzernie atrybutu symbolicznego
        attributes.addElement(dec);
        Instances dataInstances = new Instances("Dopełnienie", attributes, 71);
        //dodawanie ilości instancji takiej jak ilośc wierszy w tablicy
        for (int[] dataTable1 : dataTable) {
            Instance instance = new Instance(attributes.size());
            dataInstances.add(instance);
        }
        //wybieranie kolejnych nowych instancji
        int r = 0;
        for (int i = 0; i < dataInstances.numInstances(); i++) {
            Instance instance = dataInstances.instance(i);
            //    System.out.print(r + " ");
            //ustawianie poszczególnych wartości dla nowych instancji
            for (int k = 0; k < dataTable[0].length; k++) {
                instance.setValue(k, dataTable[i][k]);
                //      System.out.print(dataTable[i][k]);
            }
            //     System.out.println("");
            r++;
        }

        //saveData(dataInstances, "./kombinacje.arff"); //Zapis utworzonej tablicy
        dataWithNewInstances = dataInstances;
        List<Instance> mojaSyuperLista = new ArrayList();
        for (int i = 0; i < dataInstances.numInstances(); i++) {
            //System.out.println("\t\t\t" + Arrays.toString(dataInstances.instance(i).toDoubleArray()));
            Instance ist = new Instance(dataInstances.instance(i));//
            System.out.println(ist.toString());
            mojaSyuperLista.add(ist);

        }
        for (int k = 0; k < mojaSyuperLista.size(); k++) {
              System.out.println("moja " + mojaSyuperLista.get(k).toString());

        }
//        
//         for (int i = 0; i < data.numInstances(); i++) {
//            System.out.println("\t\t\t" + data.instance(i).toString());
////        }
//        Classifier cls_co = (Classifier) weka.core.SerializationHelper
//                .read("/CO_J48Model.model");

        //    System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        return dataInstances;
    }

    /**
     * Zapis zbioru danych do formatu ARFF
     *
     * @param data
     * @param fileName
     * @throws java.io.IOException
     */
    public void saveData(Instances data, String fileName)
            throws IOException {
        try (//        ArffSaver saver = new ArffSaver(); //Utworzenie obiektu zapisujacego dane
                //        saver.setFile(new File(fileName));
                //        saver.setInstances(data);
                //        saver.writeBatch();
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(data.toString());
            writer.flush();
        }
    }

    /**
     * Uzupełnia listę zbiorami parametrów
     *
     * @param data
     */
    public void setListOfAtribValue(int[][] data) {
        listOfAtribValue = new ArrayList<>();
        Set<Integer> setKolumna1;
        for (int i = 0; i < data[0].length; i++) {
            setKolumna1 = new HashSet<>();
            for (int[] data1 : data) {
                setKolumna1.add(data1[i]);

            }
            listOfAtribValue.add(setKolumna1);
        }

//        List<Set> listaKolumn = new ArrayList();
//        List<Integer> wszystkieLiczbyZKolumn = new ArrayList();
//        for (Set s : listOfAtribValue) {
//            listaKolumn.add(s);
//            Iterator it = s.iterator();
//            while (it.hasNext()) {
//                wszystkieLiczbyZKolumn.add(Integer.parseInt(it.next().toString()));
//
//            }
//        }
    }

    /**
     * Przepisuje listę zbiorów zniepowtazralnymi elementami atrybutów na listę
     * list
     *
     * @param ls
     */
    public void lSetsToLList(List<Set> ls) {
        listOfAtributesValue = new ArrayList<>();
        List<Integer> listInt;
        for (Set s : ls) {
            listInt = new ArrayList<>();
            for (Object str : s) {
                listInt.add((Integer) str);
            }
            listOfAtributesValue.add(listInt);
        }
    }

    /**
     * Permutacje zbiorów
     *
     * @param <T>
     * @param lists
     * @return
     */
    public static <T> Set<List<T>> getCombinations(List<List<T>> lists) {
        Set<List<T>> combinations = new HashSet<>();
        Set<List<T>> newCombinations;
        int index = 0;
        //wyodrębnia każdą z liczb całkowitych pierwszej listy
        // i dodaje każdego inta jako nową listę
        for (T i : lists.get(0)) {
            List<T> newList = new ArrayList<>();
            newList.add(i);
            combinations.add(newList);
        }
        index++;
        while (index < lists.size()) {
            List<T> nextList = lists.get(index);
            newCombinations = new HashSet<>();
            for (List<T> first : combinations) {
                for (T second : nextList) {
                    List<T> newList = new ArrayList<>();
                    newList.addAll(first);
                    newList.add(second);
                    newCombinations.add(newList);
                }
            }
            combinations = newCombinations;
            index++;
        }
        return combinations;
    }

    /**
     * Przepisuje dane ze zbioru list do tablicy dwuwymiarowej
     *
     * @param sl
     * @return
     */
    public int[][] setsToTab(Set<List<Integer>> sl) {
        int numAtrib = 0;
        for (List l : sl) {
            numAtrib = l.size();
        }
        Iterator it = sl.iterator();

        List<Integer> list;
        // System.out.println(sl.size() + " " + numAtrib);
        int tab[][] = new int[sl.size()][numAtrib];
        for (int i = 0; i < sl.size(); i++) {
            list = (List<Integer>) it.next();
            for (int j = 0; j < numAtrib; j++) {
                tab[i][j] = list.get(j);
            }
        }
        return tab;
    }

    /**
     * Zlicza jakie elementy trzeba uzunąć z permutaji (tj w tablicy wejściowej)
     */
    public void delTheSameObj() {

        List<String> perm = changeArrayToListOfString(permutation);

        List<String> dataSet = changeArrayToListOfString(dataInTable);

        List<Integer> objToDel = new ArrayList<>();

        for (int j = 0; j < perm.size(); j++) {

            for (int k = 0; k < dataSet.size(); k++) {
                if (perm.get(j).equals(dataSet.get(k))) {
                    objToDel.add(j);
                }
            }
        }
        // Data.objToDel = objToDel;
        dataPerm = removeRow(permutation, objToDel);
//        int h = 0;
//        for (int[] i : dataPerm) {
//            System.out.print(h + "\t");
//            for (int k : i) {
//                System.out.print(k + " ");
//            }
//            System.out.println("");
//            h++;
//        }
    }

    /**
     * Zamienia tablice na liste stringów
     *
     * @param s
     * @return
     */
    public List<String> changeArrayToListOfString(int[][] s) {
        StringBuilder sb;
        List<String> listOfPermutationTab = new ArrayList<>();
        for (int[] item : s) {
            sb = new StringBuilder();
            for (int j = 0; j < item.length; j++) {
                sb.append(item[j]);
            }
            listOfPermutationTab.add(String.valueOf(sb));
        }

        return listOfPermutationTab;
    }

    /**
     * Usówa wiersze będące take same jak w tablicy z wczytanego pliku z tablicy
     * permutacji.
     *
     * @param tab
     * @param lRowToDel
     * @return
     */
    private int[][] removeRow(int[][] tab, List<Integer> lRowToDel) {
        int[][] table = tab;
        List<int[]> l = new ArrayList<>(Arrays.asList(table));
        int[] tempTab = new int[lRowToDel.size()];
        for (int i = 0; i < tempTab.length; i++) {
            tempTab[i] = lRowToDel.get(i);
        }
        for (int i = tempTab.length - 1; i >= 0; i--) {
            l.remove(tempTab[i]);
        }
        return l.toArray(new int[][]{});
    }

    /**
     * Zamienia listę na tablicę
     *
     * @param sl
     * @return
     */
    public int[] listToTab(Set<Integer> sl) {
        Iterator it = sl.iterator();
        int tab[] = new int[sl.size()];
        for (int i = 0; i < sl.size(); i++) {
            tab[i] = Integer.valueOf(String.valueOf(it.next()));
        }
        return tab;
    }
}
