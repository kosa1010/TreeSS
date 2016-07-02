package com.mycompany.treess;

//import static com.mycompany.treess.Data.getCombinations;
import static com.mycompany.treess.Data.getCombinations;
import java.util.Scanner;
import weka.core.Instances;

/**
 *
 * @author kosa1010
 */
public class MainClass {

    public static String path;///home/kosa1010/NetBeansProjects/TreeSS/data.arff

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Data data = new Data();
        System.out.println("Podaj ścieżkę do pliku z danymi ");
      //  path = sc.next();
        Data.setData(Data.loadData("/home/kosa1010/NetBeansProjects/TreeSS/data.arff"));
        Data.numOfInstances = data.getData().numInstances();

        for (int i = 0; i < Data.numOfInstances; i++) {
            System.out.println("\t\t\t" + data.getData().instance(i).toString());
        }

        data.dataFromInnstancesToTable(data.getData()); //przepisanie instancji do tablicy dwuwymiarowej
        data.setListOfAtribValue(Data.dataInTable);

        data.lSetsToLList(Data.listOfAtribValue);

        Data.permutate = getCombinations(Data.listOfAtributesValue);

        Data.permutation = data.setsToTab(Data.permutate);
        data.delTheSameObj();
        data.setDataComplement(data.dataFromTableToInstances(Data.dataPerm));//////// tu się krzaczy

        Instances newI = new Instances(data.getDataComplement());

        System.out.println("ilośc elementów w dopełnieniu " + Data.dataPerm.length);
        System.out.println("\n\n Podaj parametry dla uworzenia drzewa algorytmem J48 ");
       // String optionsJ48 = sc.next();
        BuildTree.setAllAtrribAsDecision(data.getData(), BuildTree.TREE.C45, "-U");
        System.out.println("\n\n Podaj parametry dla uworzenia drzewa algorytmem CART ");
       // String optionsCART = sc.next();
        BuildTree.setAllAtrribAsDecision(data.getData(), BuildTree.TREE.CART, "-U");

        //  C45 c45 = new C45();
//        c45.buildJ48(newI, "-U");
//        List<TreeCARTj48> listClasyficators = BuildTree.listOfTrees;
//        for (TreeCARTj48 tree : listClasyficators) {
//            System.out.println(tree.toString());
//            System.out.println("drzewko" + listClasyficators.indexOf(tree));
//
//        }
    }
}
