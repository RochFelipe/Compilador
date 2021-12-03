package Main;

import Lexico.*;
import Parser.*;
import java.io.File;

public class Compilador {

    public static void main(String[] args) {
        File arq = new File("C:\\Users\\germa\\Documents\\NetBeansProjects\\Compilador\\src\\Main\\Script.txt");
        if (!arq.exists()) {
            arq.mkdir();
        }
        try {
            PpScanner sc = new PpScanner(arq.getPath());
            Parser pa = new Parser(sc);
            pa.Entry();

        } catch (InterruptedException ex) {
            System.out.println("FIM!");
        }

    }

}
