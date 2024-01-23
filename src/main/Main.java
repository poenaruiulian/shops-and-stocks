package main;


import main.java.libs.App;
import main.java.libs.BetaApp;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        boolean isBetaVerison = false;

        if(isBetaVerison){
            BetaApp.main();
        }else{
            App GUI = null;
            try {
                GUI = new App();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            GUI.setVisible(true);
        }

    }
}