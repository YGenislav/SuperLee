package Main;
import java.util.Scanner;
import PresentationLayer.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("███████╗██╗   ██╗██████╗ ███████╗██████╗       ██╗     ███████╗███████╗");
        System.out.println("██╔════╝██║   ██║██╔══██╗██╔════╝██╔══██╗      ██║     ██╔════╝██╔════╝");
        System.out.println("███████╗██║   ██║██████╔╝█████╗  ██████╔╝█████╗██║     █████╗  █████╗  ");
        System.out.println("╚════██║██║   ██║██╔═══╝ ██╔══╝  ██╔══██╗╚════╝██║     ██╔══╝  ██╔══╝  ");
        System.out.println("███████║╚██████╔╝██║     ███████╗██║  ██║      ███████╗███████╗███████╗");
        System.out.println("╚══════╝ ╚═════╝ ╚═╝     ╚══════╝╚═╝  ╚═╝      ╚══════╝╚══════╝╚══════╝");
        System.out.println("\n\n");
        Scanner scanner = new Scanner(System.in);
        boolean ExitProgram = false;
        System.out.println("Choose Supplier Menu or Workers Menu");
        while (!ExitProgram) {
            System.out.println("_________________________________________________ ");
            System.out.println("1. Suppliers Menu.");
            System.out.println("2. Workers Menu.");
            System.out.println("3. Exit From The System.");
            System.out.println("_________________________________________________ ");
            System.out.print("Please enter the number of your choice here: ");
            int choice = scanner.nextInt();
            System.out.println();
            switch (choice) {
                case 1:
                    start_suppliers_menu();
                    System.out.println("Is there anything else you would like to accomplish?");

                    break;
                case 2:
                    start_workers_menu();
                    System.out.println("Is there anything else you would like to accomplish?");

                    break;
                case 3:
                    ExitProgram = true;
                    System.out.println("It was a pleasure working with you today.\nHave a nice day!");
                    exitdata();
                    break;
                default:
                    System.out.println("\n\nOops... It's seems like you entered an invalid choice number.");
                    System.out.println("Let's Try again.");

                    break;
            }

        }
        System.out.println("\n\n\n\nTermination Notice:\n" +
                "\n" +
                "This system was developed by Gil Eden, Lidor Mashiach and two more. Usage is governed by terms agreed with \"Super-Lee\". Unauthorized use, reproduction, or distribution is prohibited and may result in legal action.\n" +
                "\n" +
                "All intellectual property rights are owned by Lidor Mashiach , Gil Eden and Yuval and Orel. Any copyright infringement will be pursued to the fullest extent of the law.\n" +
                "\n" +
                "Instructions for use and basic assumptions are detailed in the attached PDF.");
    }
    public static void start_workers_menu(){
        try{
            UIConsole uiConsole = new UIConsole();
            uiConsole.start();
        } catch (Exception e) {
            System.out.println("Error initializing HRService: " + e.getMessage());
        }
    }
    public static void start_suppliers_menu(){
        try{
            CLI cli = new CLI();
            cli.login();
        } catch (Exception e) {
            System.out.println("Error initializing suppliers: " + e.getMessage());
        }
    }
    public static void exitdata(){
        try {
            CLI.exitdata();
        }
        catch (Exception e){
            System.out.println("Error exiting data: " + e.getMessage());
        }
    }

}
