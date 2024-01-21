package main;


import main.java.data.CEO;
import main.java.data.Employee;
import main.java.data.Person;
import main.java.data.ShopManager;
import main.java.helpers.Auth;
import main.java.helpers.HandleConsoleOptions;
import main.java.helpers.HandleConsoleResponses;
import main.java.types.ResponseType;
import main.java.types.UserType;

import java.util.Objects;
import java.util.Scanner;

public class Main {

    /**
     * Clearing the screen in a hacky way.
     */
    public static void clearScreen() {
        for (int clear = 0; clear < 1000; clear++) {
            System.out.println("\b");
        }
    }

    /**
     * @return a Person object if a user is logged in or a user loges in or null if the user doesn't log
     */
    public static Person handleConsoleAuth() {
        Person authUser = null;
        Scanner scanner = new Scanner(System.in);

        if (Auth.isUserLogged()) {
            authUser = Auth.getCurrentUser();
            System.out.println(" --- Hello, " + authUser.getFirstName() + " --- ");
        } else {
            System.out.println("No user is logged in. Would you like to log in? [y/n]");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y")) {
                while (authUser == null) {
                    clearScreen();
                    System.out.println("Login: ");
                    System.out.print("First name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Last name: ");
                    String lastName = scanner.nextLine();

                    authUser = Auth.loggInUser(firstName, lastName);

                    if (authUser == null) {
                        System.out.println("No user found. Try again? [y/n]");
                        response = scanner.nextLine();
                        if (response.equalsIgnoreCase("n")) {
                            break;
                        }
                    }
                }
                if (authUser != null) {
                    clearScreen();
                    System.out.println(" --- Hello, " + authUser.getFirstName() + " --- ");
                    return authUser;
                }
            }
            clearScreen();
            System.out.println("If you don't have an account contact your supervisor to create one for you.");
        }
        return authUser;
    }

    /**
     * Handles the beta version of the app.
     *
     * @param currentUser Current logged user.
     */
    public static Person handleConsoleApp(Person currentUser) {
        if (Objects.equals(currentUser.getUserType(), UserType.ceo)) {
            CEO ceoCurrentUser = (CEO) currentUser;
            String response = HandleConsoleOptions.handleCEOOptions();
            while (!Objects.equals(response, ResponseType.quit)) {
                ceoCurrentUser = HandleConsoleResponses.handleCEOResponse(ceoCurrentUser, response);
                if(ceoCurrentUser==null){
                    clearScreen();
                    return null;
                }
                response = HandleConsoleOptions.handleCEOOptions();
            }
            System.exit(0);
        }else if(Objects.equals(currentUser.getUserType(), UserType.manager)){
            ShopManager shopManagerCurrentUser = (ShopManager) currentUser;
            String response = HandleConsoleOptions.handleShopManagerOptions();
            while (!Objects.equals(response, ResponseType.quit)) {
                shopManagerCurrentUser = HandleConsoleResponses.handleShopManagerResponse(shopManagerCurrentUser, response);
                if(shopManagerCurrentUser==null){
                    clearScreen();
                    return null;
                }
                response = HandleConsoleOptions.handleShopManagerOptions();
            }
            System.exit(0);
        }else if(Objects.equals(currentUser.getUserType(), UserType.employee)){
            Employee employeeCurrentUser = (Employee) currentUser;
            String response = HandleConsoleOptions.handleEmployeeOptions();
            while (!Objects.equals(response, ResponseType.quit)) {
                employeeCurrentUser = HandleConsoleResponses.handleEmployeeResponse(employeeCurrentUser, response);
                if(employeeCurrentUser==null){
                    clearScreen();
                    return null;
                }
                response = HandleConsoleOptions.handleEmployeeOptions();
            }
            System.exit(0);
        }
        return null;
    }

    public static void main(String[] args) {
        Person currentUser;

        while(true){
            currentUser = handleConsoleAuth();
            if (currentUser != null) {
                handleConsoleApp(currentUser);
            }
        }



    }
}