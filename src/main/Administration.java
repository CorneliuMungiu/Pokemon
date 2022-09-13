package main;

import java.io.FileWriter;
import java.io.IOException;

public class Administration {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    /**
     * Testeaza metodele.
     */
    public static void main(String[] args) throws IOException {

        Arena arena = new Arena();
        //Apeleaza fiecare fisier de input
        for(int i = 1; i <= 10; i++){
            System.out.println(ANSI_YELLOW + "OUTPUT OF FILE " + i + ".in" + ANSI_RESET);
            String inputFileName = new String("test/in/" + i + ".in");
            String outputFileName = new String("test/out/" + i + " .out");
            FileWriter myWriter = new FileWriter(outputFileName);
            //Extrage antrenorii din clasa ReadFromFile (Coach[] coaches va fi un array de 2 antrenori)
            Coach[] coaches = ReadFromFile.readFile(inputFileName);
            //Apeleaza arenaBattle unde se vor lupta cei 2 antrenori.
            arena.arenaBattle(coaches,myWriter);
            myWriter.close();
        }
    }
}
