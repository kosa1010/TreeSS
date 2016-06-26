package com.mycompany.treess;

import static com.mycompany.treess.Data.getCombinations;
import java.util.Scanner;
import weka.core.Instances;

/**
 *
 * @author kosa1010
 */
public class MainClass {

    public static String path;///home/kosa1010/NetBeansProjects/TreeS/data.arff

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Data data = new Data();
        System.out.println("Podaj ścieżkę do pliku z danymi ");
        // path = sc.next();
        data.loadData("/home/kosa1010/NetBeansProjects/TreeSS/data.arff");//(path);

        data.dataFromInnstancesToTable(data.getData()); //przepisanie instancji do tablicy dwuwymiarowej
        data.setListOfAtribValue(Data.dataInTable);

        data.lSetsToLList(Data.listOfAtribValue);

        Data.permutate = getCombinations(Data.listOfAtributesValue);

//        Data.permutate.stream().forEach((list) -> {
//            System.out.println(list.toString());
//        });
        Data.permutation = data.setsToTab(Data.permutate);
        System.out.println("gh");
        data.delTheSameObj();
        data.setDataComplement(data.dataFromTableToInstances(Data.dataPerm));

        Instances newI = new Instances(data.getDataComplement());
//        for (int i = 0; i < newI.numInstances(); i++) {
//            System.out.println("objekt  " + i + " " + newI.instance(i).toString());
//        }
        // System.out.println("ilośc elementów w dopełnieniu "+Data.dataPerm.length); System.out.println("\n\n Podaj parametry dla uworzenia drzewa algorytmem J48 ");

        //String optionsJ48 = sc.next();
        BuildTree.setAllAtrribAsDecision(data.getData(), BuildTree.TREE.C45, "-U");// optionsJ48);
        System.out.println("\n\n Podaj parametry dla uworzenia drzewa algorytmem CART ");
        //String optionsCART = sc.next();
        BuildTree.setAllAtrribAsDecision(data.getData(), BuildTree.TREE.CART, "-U");// optionsCART);

        System.out.println(" dla nowych");
        C45 c45 = new C45();
        c45.buildJ48(newI, "-U");
    }
}