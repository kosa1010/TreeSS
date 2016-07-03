package com.mycompany.treess;

import static com.mycompany.treess.Data.getCombinations;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author kosa1010
 */
public class MainClass {

    public static String path;///home/kosa1010/NetBeansProjects/TreeSS/data.arff

    public static void main(String[] args) throws Exception {
        try {
            Scanner sc = new Scanner(System.in);
            Scanner scc = new Scanner(System.in);
            Data data = new Data();
            System.out.println("Podaj ścieżkę do pliku z danymi ");
            path = sc.next();
            data.setData(Data.loadData(path));
            Data.numOfInstances = data.getData().numInstances();
            System.out.println("\nWczytane dane");
            for (int i = 0; i < Data.numOfInstances; i++) {
                System.out.println("\t" + data.getData().instance(i).toString());
            }

            data.dataFromInnstancesToTable(data.getData()); //przepisanie instancji do tablicy dwuwymiarowej
            data.setListOfAtribValue(Data.dataInTable);

            data.lSetsToLList(Data.listOfAtribValue);

            Data.permutate = getCombinations(Data.listOfAtributesValue);

            Data.permutation = data.setsToTab(Data.permutate);
            data.delTheSameObj();

            data.setDataComplement(data.dataFromTableToInstances(data.getDataPerm()));

            System.out.println("\n\n Podaj parametry dla uworzenia drzewa algorytmem C4.5 ");
            String optionsJ48 = sc.next();
            BuildTree.setAllAtrribAsDecision(data.getData(), BuildTree.TREE.C45, optionsJ48);
            System.out.println("\n\n Podaj parametry dla uworzenia drzewa algorytmem CART ");
            String optionsCART = scc.next();
            BuildTree.setAllAtrribAsDecision(data.getData(), BuildTree.TREE.CART, optionsCART);
        } catch (IOException e) {
            System.out.println(BuildTree.ANSI_RED + "Nie można odnaleźć pliku" + BuildTree.ANSI_RESET);
        } catch (Exception e) {
            System.out.println(BuildTree.ANSI_RED + "Nie można zbudować klasyfikatora. "
                    + "Błędne opcje budowy klasyfikatora" + BuildTree.ANSI_RESET);
        }
    }
}
