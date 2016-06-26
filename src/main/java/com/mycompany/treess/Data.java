/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.treess;

import java.io.File;
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
import weka.core.converters.ArffSaver;

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
     * Przpisuje dane z obiektu instance do tablicy dwuwyimarowej
     *
     * @param data
     * @return
     */
    public int[][] dataFromInnstancesToTable(Instances data) {
        int[][] table = new int[data.numInstances()][data.instance(0).toDoubleArray().length];
        double[] temp;
        for (int i = 0; i < data.numInstances(); i++) {
            temp = data.instance(i).toDoubleArray();
            for (int j = 0; j < temp.length; j++) {
                table[i][j] = (int) temp[j];
            }
        }
        dataInTable = table;
        return table;
    }

    /**
     * Przpisuje dane z tablicy dwuwyimaroweje do obiektu instances
     *
     * @param dataTable
     * @return
     * @throws java.io.IOException
     */
    public Instances dataFromTableToInstances(int[][] dataTable) throws IOException {

        FastVector attributes = new FastVector(); //utworzenie listy atrybutow dla nowej tablicy
        for (int i = 0; i < data.numAttributes() - 1; i++) {
            attributes.addElement(new Attribute(data.attribute(i).name()));
        }
        FastVector labels = new FastVector(); //Utworzenie wartosci nowego atrybutu symbolicznego
        //for ( is : data.attribute(data.numAttributes()-1)) {
        //   for (int i = 0; i < listOfAtribValue.get(data.numAttributes() - 1).size(); i++) {
        System.out.println("****************************************************************");
        listOfAtribValue.stream().forEach((set) -> {
            //          System.out.println(set);
        });
        // System.out.println(listOfAtribValue.get(data.numAttributes()-1));
        Set<List> ss = new HashSet();
        //System.out.println(
        listOfAtribValue.get(3).stream().forEach((a)
                -> {
            System.out.println(a);
        });
        for (Object object : listOfAtribValue.get(0)) {
            ss.add(new ArrayList((int) object));
        }
        int tab[][] = setsToTab(listOfAtribValue.get(0));
        System.out.println(tab[0].length);
        for (int s : tab[0]) {
            labels.addElement(String.valueOf(s));
        }
        Attribute dec = new Attribute(data.attribute(data.numAttributes() - 1).name(), labels); //Utworzernie atrybutu symbolicznego
        attributes.addElement(dec);
        Instances dataInstances = new Instances("Dopełnienie", attributes, 0);

        for (int[] dataTable1 : dataTable) {
            Instance instance = new Instance(attributes.size());
            dataInstances.add(instance);
        }

        for (int i = 0; i < dataInstances.numInstances(); i++) {
            Instance instance = dataInstances.instance(i);

            for (int k = 0; k < dataTable[0].length; k++) {
                instance.setValue(k, dataTable[i][k]);
            }
        }

        saveData(dataInstances, "./kombinacje.arff"); //Zapis utworzonej tablicy
        // dataWithNewInstances = dataInstances;

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
        ArffSaver saver = new ArffSaver(); //Utworzenie obiektu zapisujacego dane
        saver.setFile(new File(fileName));
        saver.setInstances(data);
        saver.writeBatch();
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
}
