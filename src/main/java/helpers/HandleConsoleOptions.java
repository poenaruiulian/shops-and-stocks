package main.java.helpers;

import main.java.libs.BetaApp;
import main.java.types.ResponseType;

import java.util.Scanner;


/**
 * This class is part of the Beta Version of the app and it's purpose is to handle the options presented to the user.
 */
public class HandleConsoleOptions {

    /**
     * Presents the options for an employee, giving the opportunity to the user to select an answer.
     * @return A string corresponding to the response gave by the user.
     */
    public static String handleEmployeeOptions() {
        String response = "";
        BetaApp.clearScreen();
        System.out.println("Products [1]");
        System.out.println("Log out [0]");
        System.out.println("Quit [q]");

        Scanner scanner = new Scanner(System.in);

        switch (scanner.nextLine()) {
            case "1":
                BetaApp.clearScreen();
                System.out.println("Back [b]");
                System.out.println("See stock for shop [1]");
                System.out.println("Add a new product to shop [2]");
                System.out.println("Remove a product from the shop [3]");
                System.out.println("Add more stock to product [4]");
                System.out.println("Subtract stock from product [5]");

                response = switch (scanner.nextLine()) {
                    case "b" -> ResponseType.back;
                    case "1" -> ResponseType.stockOfShop;
                    case "2" -> ResponseType.addItem;
                    case "3" -> ResponseType.removeItem;
                    case "4" -> ResponseType.addStockItem;
                    case "5" -> ResponseType.removeStockItem;
                    default -> ResponseType.def;
                };
                break;
            case "0":
                response = ResponseType.logOut;
                break;
            case "q":
                response = ResponseType.quit;
                break;
            default:
                response = ResponseType.def;
        }

        return response;
    }

    /**
     * Presents the options for a shop manager, giving the opportunity to the user to select an answer.
     * @return A string corresponding to the response gave by the user.
     */
    public static String handleShopManagerOptions() {
        String response = "";
        BetaApp.clearScreen();
        System.out.println("Employees [1]");
        System.out.println("Products [2]");
        System.out.println("Log out [0]");
        System.out.println("Quit [q]");

        Scanner scanner = new Scanner(System.in);

        switch (scanner.nextLine()) {
            case "1":
                BetaApp.clearScreen();
                System.out.println("Back [b]");
                System.out.println("See all employees [1]");
                System.out.println("Hire an employee [2]");
                System.out.println("Fire an employee [3]");

                response = switch (scanner.nextLine()) {
                    case "b" -> ResponseType.back;
                    case "1" -> ResponseType.allEmployees;
                    case "2" -> ResponseType.hireEmployee;
                    case "3" -> ResponseType.fireEmployee;
                    default -> ResponseType.def;
                };
                break;
            case "2":
                BetaApp.clearScreen();
                System.out.println("Back [b]");
                System.out.println("See stock for shop [1]");
                System.out.println("Add a new product to shop [2]");
                System.out.println("Remove a product from the shop [3]");
                System.out.println("Add more stock to product [4]");
                System.out.println("Subtract stock from product [5]");

                response = switch (scanner.nextLine()) {
                    case "b" -> ResponseType.back;
                    case "1" -> ResponseType.stockOfShop;
                    case "2" -> ResponseType.addItem;
                    case "3" -> ResponseType.removeItem;
                    case "4" -> ResponseType.addStockItem;
                    case "5" -> ResponseType.removeStockItem;
                    default -> ResponseType.def;
                };
                break;
            case "0":
                response = ResponseType.logOut;
                break;
            case "q":
                response = ResponseType.quit;
                break;
            default:
                response = ResponseType.def;
        }

        return response;
    }

    /**
     * Presents the options for a ceo, giving the opportunity to the user to select an answer.
     * @return A string corresponding to the response gave by the user.
     */
    public static String handleCEOOptions(){
        String response = "";
        BetaApp.clearScreen();
        System.out.println("Shops [1]");
        System.out.println("Employees [2]");
        System.out.println("Products [3]");
        System.out.println("Log out [0]");
        System.out.println("Quit [q]");

        Scanner scanner = new Scanner(System.in);

        switch (scanner.nextLine()) {
            case "1":
                BetaApp.clearScreen();
                System.out.println("Back [b]");
                System.out.println("See all shops [1]");
                System.out.println("Open a shop [2]");
                System.out.println("Close a shop [3]");

                response = switch (scanner.nextLine()) {
                    case "b" -> ResponseType.back;
                    case "1" -> ResponseType.allShops;
                    case "2" -> ResponseType.openShop;
                    case "3" -> ResponseType.closeShop;
                    default -> ResponseType.def;
                };
                break;
            case "2":
                BetaApp.clearScreen();
                System.out.println("Back [b]");
                System.out.println("See all employees [1]");
                System.out.println("Hire a manager [2]");
                System.out.println("Fire a manager [3]");
                System.out.println("Hire an employee [4]");
                System.out.println("Fire an employee [5]");
                System.out.println("Transfer employee [6]");

                response = switch (scanner.nextLine()) {
                    case "b" -> ResponseType.back;
                    case "1" -> ResponseType.allEmployees;
                    case "2" -> ResponseType.hireManager;
                    case "3" -> ResponseType.fireManager;
                    case "4" -> ResponseType.hireEmployee;
                    case "5" -> ResponseType.fireEmployee;
                    case "6" -> ResponseType.transferEmployee;
                    default -> ResponseType.def;
                };
                break;
            case "3":
                BetaApp.clearScreen();
                System.out.println("Back [b]");
                System.out.println("See stock for a shop [1]");
                System.out.println("Add a new product to a shop [2]");
                System.out.println("Remove a product from the shop [3]");
                System.out.println("Add more stock to a product [4]");
                System.out.println("Subtract stock from a product [5]");

                response = switch (scanner.nextLine()) {
                    case "b" -> ResponseType.back;
                    case "1" -> ResponseType.stockOfShop;
                    case "2" -> ResponseType.addItem;
                    case "3" -> ResponseType.removeItem;
                    case "4" -> ResponseType.addStockItem;
                    case "5" -> ResponseType.removeStockItem;
                    default -> ResponseType.def;
                };
                break;
            case "0":
                response = ResponseType.logOut;
                break;
            case "q":
                response = ResponseType.quit;
                break;
            default:
                response = ResponseType.def;
        }

        return response;
    }

}
