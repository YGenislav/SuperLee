package workers;

import PresentationLayer.UIConsole;

public class Main {
    public static void main(String[] args) {
        try {
            UIConsole uiConsole = new UIConsole();
            uiConsole.start();
        } catch (Exception e) {
            System.out.println("Error initializing HRService: " + e.getMessage());
        }
    }
}