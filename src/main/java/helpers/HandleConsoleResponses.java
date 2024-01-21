package main.java.helpers;

import main.java.data.*;
import main.java.types.ResponseType;
import main.java.types.TransactionType;
import main.java.types.UnitType;
import main.java.types.UserType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class HandleConsoleResponses {
    /**
     * Handles the responses of the CEO.
     * @param currentUser The current user that uses the app.
     * @param response The response of the user gave in the options tab.
     * @return The modified current user.
     */
    public static CEO handleCEOResponse(CEO currentUser, String response) {
        Scanner scanner = new Scanner(System.in);
        switch (response) {
            case ResponseType.openShop:
                currentUser.openShop();
                System.out.println("Shop successfully opened!");
                break;
            case ResponseType.closeShop:
                System.out.print("The id of the shop you want to close: ");
                int shopID = scanner.nextInt();
                if (FileProcessing.getShopFromFile(shopID) != null) {
                    currentUser.closeShop(shopID);
                    System.out.println("Shop successfully deleted!");
                } else {
                    System.out.println("There is no shop with the ID: " + shopID);
                }
                break;
            case ResponseType.allShops:
                for (Shop shop : currentUser.getShopList()) {
                    String firstName = "None", lastName = "None";
                    if (shop.getShopManagerID() != -1) {
                        ShopManager shopManager = (ShopManager) FileProcessing.getUserFromFile(shop.getShopManagerID());
                        lastName = shopManager.getLastName();
                        firstName = shopManager.getLastName();
                    }
                    System.out.println(
                            " Shop ID: " + shop.getShopID() +
                                    " Number of employees: " + shop.getEmployeesList().size() +
                                    " Manager: " + firstName + " " + lastName +
                                    " Number of products: " + shop.getProductList().size()
                    );
                }
                break;
            case ResponseType.hireManager:
                String firstName;
                String lastName;
                String address;
                String phoneNumber;
                LocalDate dateOfBirth;
                int year, month, day;
                int idOfSelectedShop;
                List<Integer> listOfIDs = new ArrayList<>();
                for (Shop shop : currentUser.getShopList().stream().filter(shop -> shop.getShopManagerID() == -1).toList()) {
                    listOfIDs.add(shop.getShopID());
                }

                System.out.println("Available shops IDs to assign a manager to: " + listOfIDs);
                System.out.print("ID of the shop: ");
                idOfSelectedShop = Integer.parseInt(scanner.nextLine());
                System.out.print("First Name: ");
                firstName = scanner.nextLine();
                System.out.print("Last Name: ");
                lastName = scanner.nextLine();
                System.out.print("Address: ");
                address = scanner.nextLine();
                System.out.print("Phone number: ");
                phoneNumber = scanner.nextLine();
                System.out.print("Year of birth:");
                year = Integer.parseInt(scanner.nextLine());
                System.out.print("Month of birth:");
                month = Integer.parseInt(scanner.nextLine());
                System.out.print("Day of birth:");
                day = Integer.parseInt(scanner.nextLine());
                dateOfBirth = LocalDate.of(year, month, day);

                currentUser.hireManager(firstName, lastName, dateOfBirth, phoneNumber, address, idOfSelectedShop);
                System.out.println("Manager hired with success!");
                break;
            case ResponseType.fireManager:
                int idOfSelectedShopFire;
                List<Integer> listOfIDsToFire = new ArrayList<>();
                for (Shop shop : currentUser.getShopList().stream().filter(shop -> shop.getShopManagerID() != -1).toList()) {
                    listOfIDsToFire.add(shop.getShopID());
                }
                System.out.println("Available shops IDs to fire a manager from: " + listOfIDsToFire);
                System.out.print("ID of the shop: ");
                idOfSelectedShopFire = Integer.parseInt(scanner.nextLine());
                currentUser.fireManager(FileProcessing.getShopFromFile(idOfSelectedShopFire).getShopManagerID(), idOfSelectedShopFire);
                System.out.println("Manager fired with success!");
                break;
            case ResponseType.hireEmployee:
                String firstNameEmployee;
                String lastNameEmployee;
                String addressEmployee;
                String phoneNumberEmployee;
                LocalDate dateOfBirthEmployee;
                int yearEmployee, monthEmployee, dayEmployee;
                int idOfSelectedShopEmployee;
                List<Integer> listOfIDsEmployee = new ArrayList<>();
                for (Shop shop : currentUser.getShopList()) {
                    listOfIDsEmployee.add(shop.getShopID());
                }

                System.out.println("Available shops IDs: " + listOfIDsEmployee);
                System.out.print("ID of the shop: ");
                idOfSelectedShopEmployee = Integer.parseInt(scanner.nextLine());
                System.out.print("First Name: ");
                firstNameEmployee = scanner.nextLine();
                System.out.print("Last Name: ");
                lastNameEmployee = scanner.nextLine();
                System.out.print("Address: ");
                addressEmployee = scanner.nextLine();
                System.out.print("Phone number: ");
                phoneNumberEmployee = scanner.nextLine();
                System.out.print("Year of birth:");
                yearEmployee = Integer.parseInt(scanner.nextLine());
                System.out.print("Month of birth:");
                monthEmployee = Integer.parseInt(scanner.nextLine());
                System.out.print("Day of birth:");
                dayEmployee = Integer.parseInt(scanner.nextLine());
                dateOfBirthEmployee = LocalDate.of(yearEmployee, monthEmployee, dayEmployee);
                currentUser.hireEmployee(firstNameEmployee, lastNameEmployee, dateOfBirthEmployee, phoneNumberEmployee, addressEmployee, idOfSelectedShopEmployee);
                System.out.println("Employee hired with success");
                break;
            case ResponseType.fireEmployee:
                int idOfSelectedShopFireEmployee;
                int idOfSelectedEmployee;
                List<Integer> listOfIDsToFireEmployee = new ArrayList<>();
                for (Shop shop : currentUser.getShopList()) {
                    listOfIDsToFireEmployee.add(shop.getShopID());
                }
                System.out.println("Available shops IDs to fire a manager from: " + listOfIDsToFireEmployee);
                System.out.print("ID of the shop: ");
                idOfSelectedShopFireEmployee = Integer.parseInt(scanner.nextLine());
                System.out.println("Available employees to fire from selected shop:");
                for (Employee employee : FileProcessing.getShopFromFile(idOfSelectedShopFireEmployee).getEmployeesList().stream().filter(employee -> Objects.equals(employee.getUserType(), UserType.employee)).toList()) {
                    System.out.println("Name: " + employee.getFirstName() + " " + employee.getLastName() + " ID: " + employee.getUserID());
                }
                System.out.print("ID of the employee: ");
                idOfSelectedEmployee = Integer.parseInt(scanner.nextLine());
                currentUser.fireEmployee(idOfSelectedEmployee, idOfSelectedShopFireEmployee);
                System.out.println("Employee fired with success!");
                break;
            case ResponseType.transferEmployee:
                int idOfSelectedShopTransferEmployee;
                int idOfSelectedEmployeeTransfer;

                System.out.println("Available employees to transfer:");
                for (Employee employee : currentUser.getEmployeeList().stream().filter(employee -> Objects.equals(employee.getUserType(), UserType.employee)).toList()) {
                    System.out.println("Name: " + employee.getFirstName() + " " + employee.getLastName() + " ID: " + employee.getUserID());
                }
                System.out.print("ID of the employee: ");
                idOfSelectedEmployeeTransfer = Integer.parseInt(scanner.nextLine());

                List<Integer> listOfIDsToTransferEmployee = new ArrayList<>();
                for (Shop shop : currentUser.getShopList()) {
                    boolean ok = true;
                    for (Employee employee : shop.getEmployeesList()) {
                        if (employee.getUserID() == idOfSelectedEmployeeTransfer) {
                            ok = false;
                        }
                    }
                    if (ok) {
                        listOfIDsToTransferEmployee.add(shop.getShopID());
                    }
                }
                System.out.println("Available shops IDs to fire a manager from: " + listOfIDsToTransferEmployee);
                System.out.print("ID of the shop: ");
                idOfSelectedShopTransferEmployee = Integer.parseInt(scanner.nextLine());

                currentUser.transferEmployee(idOfSelectedEmployeeTransfer, idOfSelectedShopTransferEmployee);
                System.out.println("Employee transferred with success!");
            case ResponseType.allEmployees:
                for (Employee employee : currentUser.getEmployeeList()) {
                    System.out.println(
                            "ID: " + employee.getUserID() +
                                    " NAME: " + employee.getFirstName() + " " + employee.getLastName() +
                                    " ROLE: " + employee.getUserType() +
                                    " SHOP ID: " + employee.getShopID() +
                                    "\nADDRESS:" + employee.getAddress() +
                                    " PHONE NUMBER: " + employee.getPhoneNumber() +
                                    " DATE OF BIRTH: " + employee.getDateOfBirth() +
                                    " DATE OF HIRING: " + employee.getDateOfEmployment()
                    );
                    System.out.println();
                }
                break;
            case ResponseType.addItem:
                String newProductName;
                double newProductQuantity;
                String newProductUnitType;
                double newProductPricePerUnit;
                int newProductShopID;
                System.out.print("Product name: ");
                newProductName = scanner.nextLine();
                System.out.print("Quantity: ");
                newProductQuantity = Double.parseDouble(scanner.nextLine());
                System.out.print("Unit type: kilo [k] , litre [l] , piece [p]: ");
                newProductUnitType = switch (scanner.nextLine()) {
                    case "k" -> UnitType.kilogram;
                    case "l" -> UnitType.litru;
                    case "p" -> UnitType.bucata;
                    default -> UnitType.necunoscut;
                };
                System.out.print("Price per unit: ");
                newProductPricePerUnit = Double.parseDouble(scanner.nextLine());
                List<Integer> listOfAvailableShops = new ArrayList<>();
                for (Shop shop : currentUser.getShopList()) {
                    listOfAvailableShops.add(shop.getShopID());
                }
                System.out.println("List of available shops: " + listOfAvailableShops);
                newProductShopID = Integer.parseInt(scanner.nextLine());
                currentUser.addItem(newProductName, newProductQuantity, newProductUnitType, newProductPricePerUnit, newProductShopID);
                break;
            case ResponseType.removeItem:
                List<Integer> listOfAvailableShopsToRemoveItems = new ArrayList<>();
                for (Shop shop : currentUser.getShopList()) {
                    listOfAvailableShopsToRemoveItems.add(shop.getShopID());
                }
                System.out.println("List of available shops: " + listOfAvailableShopsToRemoveItems);
                int productToRemoveShopID = Integer.parseInt(scanner.nextLine());
                System.out.println("List of available products to remove: ");

                for (Product product : FileProcessing.getShopFromFile(productToRemoveShopID).getProductList()) {
                    System.out.println("Name: " + product.getProductName() + " ID: " + product.getProductId());
                }
                int idOfProductToRemove = Integer.parseInt(scanner.nextLine());
                currentUser.removeItem(idOfProductToRemove, productToRemoveShopID);
                break;
            case ResponseType.addStockItem:
                List<Integer> listOfAvailableShopsToAddStock = new ArrayList<>();
                for (Shop shop : currentUser.getShopList()) {
                    listOfAvailableShopsToAddStock.add(shop.getShopID());
                }
                System.out.println("List of available shops: " + listOfAvailableShopsToAddStock);
                int idOfShopToAddStock = Integer.parseInt(scanner.nextLine());
                for (Product product : FileProcessing.getShopFromFile(idOfShopToAddStock).getProductList()) {
                    System.out.println(
                            "ID: " + product.getProductId() +
                                    " Name: " + product.getProductName() +
                                    " Quantity:  " + product.getQuantity() +"\n"
                    );
                }
                System.out.print("Select a product ID: ");
                int idOfProductToAddStock = Integer.parseInt(scanner.nextLine());
                System.out.print("Quantity to add:");
                double quantityToAdd = Double.parseDouble(scanner.nextLine());
                currentUser.modifyItem(idOfProductToAddStock, TransactionType.adding,quantityToAdd,idOfShopToAddStock);
                break;
            case ResponseType.removeStockItem:
                List<Integer> listOfAvailableShopsToRemoveStock = new ArrayList<>();
                for (Shop shop : currentUser.getShopList()) {
                    listOfAvailableShopsToRemoveStock.add(shop.getShopID());
                }
                System.out.println("List of available shops: " + listOfAvailableShopsToRemoveStock);
                int idOfShopToRemoveStock = Integer.parseInt(scanner.nextLine());
                for (Product product : FileProcessing.getShopFromFile(idOfShopToRemoveStock).getProductList()) {
                    System.out.println(
                            "ID: " + product.getProductId() +
                                    " Name: " + product.getProductName() +
                                    " Quantity:  " + product.getQuantity() +"\n"
                    );
                }
                System.out.print("Select a product ID: ");
                int idOfProductToRemoveStock = Integer.parseInt(scanner.nextLine());
                System.out.print("Quantity to subtract:");
                double quantityToRemove = Double.parseDouble(scanner.nextLine());
                currentUser.modifyItem(idOfProductToRemoveStock, TransactionType.substracing,quantityToRemove,idOfShopToRemoveStock);
                break;
            case ResponseType.stockOfShop:
                List<Integer> listOfAvailableShopsToShowStock = new ArrayList<>();
                for (Shop shop : currentUser.getShopList()) {
                    listOfAvailableShopsToShowStock.add(shop.getShopID());
                }
                System.out.println("List of available shops: " + listOfAvailableShopsToShowStock);
                int idOfShopToShowStock = Integer.parseInt(scanner.nextLine());
                if(FileProcessing.getShopFromFile(idOfShopToShowStock).getProductList().isEmpty()){
                    System.out.println("No products here");
                }
                for (Product product : FileProcessing.getShopFromFile(idOfShopToShowStock).getProductList()) {
                    System.out.println(
                            "ID: " + product.getProductId() +
                                    " Name: " + product.getProductName() +
                                    " Quantity:  " + product.getQuantity() +
                                    " Unit: " + product.getUnit() +
                                    " Price/Unit: " + product.getPricePerUnit()
                    );
                }
                break;
            case ResponseType.logOut:
                Auth.logOut();
                return null;
            case ResponseType.quit:
                System.exit(0);
            case ResponseType.back:
                return currentUser;
        }

        System.out.println("Press any key to continue...");
        String exitKey = "";
        while (exitKey.isEmpty()) {
            exitKey = scanner.nextLine();
        }
        return currentUser;
    }
    /**
     * Handles the responses of the Shop Manager.
     * @param currentUser The current user that uses the app.
     * @param response The response of the user gave in the options tab.
     * @return The modified current user.
     */
    public static ShopManager handleShopManagerResponse(ShopManager currentUser, String response) {
        Scanner scanner = new Scanner(System.in);
        switch (response) {
            case ResponseType.hireEmployee:
                String firstNameEmployee;
                String lastNameEmployee;
                String addressEmployee;
                String phoneNumberEmployee;
                LocalDate dateOfBirthEmployee;
                int yearEmployee, monthEmployee, dayEmployee;
                System.out.print("First Name: ");
                firstNameEmployee = scanner.nextLine();
                System.out.print("Last Name: ");
                lastNameEmployee = scanner.nextLine();
                System.out.print("Address: ");
                addressEmployee = scanner.nextLine();
                System.out.print("Phone number: ");
                phoneNumberEmployee = scanner.nextLine();
                System.out.print("Year of birth:");
                yearEmployee = Integer.parseInt(scanner.nextLine());
                System.out.print("Month of birth:");
                monthEmployee = Integer.parseInt(scanner.nextLine());
                System.out.print("Day of birth:");
                dayEmployee = Integer.parseInt(scanner.nextLine());
                dateOfBirthEmployee = LocalDate.of(yearEmployee, monthEmployee, dayEmployee);
                currentUser.hireEmployee(firstNameEmployee, lastNameEmployee, dateOfBirthEmployee, phoneNumberEmployee, addressEmployee);
                System.out.println("Employee hired with success");
                break;
            case ResponseType.fireEmployee:
                int idOfSelectedEmployee;
                System.out.println("Available employees to fire from selected shop:");
                for (Employee employee : FileProcessing.getShopFromFile(currentUser.getShopID()).getEmployeesList().stream().filter(employee -> Objects.equals(employee.getUserType(), UserType.employee)).toList()) {
                    System.out.println("Name: " + employee.getFirstName() + " " + employee.getLastName() + " ID: " + employee.getUserID());
                }
                System.out.print("ID of the employee: ");
                idOfSelectedEmployee = Integer.parseInt(scanner.nextLine());
                currentUser.fireEmployee(idOfSelectedEmployee);
                System.out.println("Employee fired with success!");
                break;
            case ResponseType.allEmployees:
                for (Employee employee : currentUser.getEmployeeList()) {
                    System.out.println(
                            "ID: " + employee.getUserID() +
                                    " NAME: " + employee.getFirstName() + " " + employee.getLastName() +
                                    " ROLE: " + employee.getUserType() +
                                    " SHOP ID: " + employee.getShopID() +
                                    "\nADDRESS:" + employee.getAddress() +
                                    " PHONE NUMBER: " + employee.getPhoneNumber() +
                                    " DATE OF BIRTH: " + employee.getDateOfBirth() +
                                    " DATE OF HIRING: " + employee.getDateOfEmployment()
                    );
                    System.out.println();
                }
                break;
            case ResponseType.addItem:
                String newProductName;
                double newProductQuantity;
                String newProductUnitType;
                double newProductPricePerUnit;
                int newProductShopID;
                System.out.print("Product name: ");
                newProductName = scanner.nextLine();
                System.out.print("Quantity: ");
                newProductQuantity = Double.parseDouble(scanner.nextLine());
                System.out.print("Unit type: kilo [k] , litre [l] , piece [p]: ");
                newProductUnitType = switch (scanner.nextLine()) {
                    case "k" -> UnitType.kilogram;
                    case "l" -> UnitType.litru;
                    case "p" -> UnitType.bucata;
                    default -> UnitType.necunoscut;
                };
                System.out.print("Price per unit: ");
                newProductPricePerUnit = Double.parseDouble(scanner.nextLine());
                currentUser.addItem(newProductName, newProductQuantity, newProductUnitType, newProductPricePerUnit);
                break;
            case ResponseType.removeItem:
                System.out.println("List of available products to remove: ");

                for (Product product : FileProcessing.getShopFromFile(currentUser.getShopID()).getProductList()) {
                    System.out.println("Name: " + product.getProductName() + " ID: " + product.getProductId());
                }
                int idOfProductToRemove = Integer.parseInt(scanner.nextLine());
                currentUser.removeItem(idOfProductToRemove);
                break;
            case ResponseType.addStockItem:
                for (Product product : FileProcessing.getShopFromFile(currentUser.getShopID()).getProductList()) {
                    System.out.println(
                            "ID: " + product.getProductId() +
                                    " Name: " + product.getProductName() +
                                    " Quantity:  " + product.getQuantity() +"\n"
                    );
                }
                System.out.print("Select a product ID: ");
                int idOfProductToAddStock = Integer.parseInt(scanner.nextLine());
                System.out.print("Quantity to add:");
                double quantityToAdd = Double.parseDouble(scanner.nextLine());
                currentUser.modifyItem(idOfProductToAddStock, TransactionType.adding,quantityToAdd);
                break;
            case ResponseType.removeStockItem:
                for (Product product : FileProcessing.getShopFromFile(currentUser.getShopID()).getProductList()) {
                    System.out.println(
                            "ID: " + product.getProductId() +
                                    " Name: " + product.getProductName() +
                                    " Quantity:  " + product.getQuantity() +"\n"
                    );
                }
                System.out.print("Select a product ID: ");
                int idOfProductToRemoveStock = Integer.parseInt(scanner.nextLine());
                System.out.print("Quantity to subtract:");
                double quantityToRemove = Double.parseDouble(scanner.nextLine());
                currentUser.modifyItem(idOfProductToRemoveStock, TransactionType.substracing,quantityToRemove);
                break;
            case ResponseType.stockOfShop:
                if(FileProcessing.getShopFromFile(currentUser.getShopID()).getProductList().isEmpty()){
                    System.out.println("No products here");
                }
                for (Product product : FileProcessing.getShopFromFile(currentUser.getShopID()).getProductList()) {
                    System.out.println(
                            "ID: " + product.getProductId() +
                                    " Name: " + product.getProductName() +
                                    " Quantity:  " + product.getQuantity() +
                                    " Unit: " + product.getUnit() +
                                    " Price/Unit: " + product.getPricePerUnit()
                    );
                }
                break;
            case ResponseType.logOut:
                Auth.logOut();
                return null;
            case ResponseType.quit:
                System.exit(0);
            case ResponseType.back:
                return currentUser;
        }

        System.out.println("Press any key to continue...");
        String exitKey = "";
        while (exitKey.isEmpty()) {
            exitKey = scanner.nextLine();
        }
        return currentUser;
    }
    /**
     * Handles the responses of the Employee.
     * @param currentUser The current user that uses the app.
     * @param response The response of the user gave in the options tab.
     * @return The modified current user.
     */
    public static Employee handleEmployeeResponse(Employee currentUser, String response){
        Scanner scanner = new Scanner(System.in);
        switch (response) {
            case ResponseType.addItem:
                String newProductName;
                double newProductQuantity;
                String newProductUnitType;
                double newProductPricePerUnit;
                int newProductShopID;
                System.out.print("Product name: ");
                newProductName = scanner.nextLine();
                System.out.print("Quantity: ");
                newProductQuantity = Double.parseDouble(scanner.nextLine());
                System.out.print("Unit type: kilo [k] , litre [l] , piece [p]: ");
                newProductUnitType = switch (scanner.nextLine()) {
                    case "k" -> UnitType.kilogram;
                    case "l" -> UnitType.litru;
                    case "p" -> UnitType.bucata;
                    default -> UnitType.necunoscut;
                };
                System.out.print("Price per unit: ");
                newProductPricePerUnit = Double.parseDouble(scanner.nextLine());
                currentUser.addItem(newProductName, newProductQuantity, newProductUnitType, newProductPricePerUnit);
                break;
            case ResponseType.removeItem:
                System.out.println("List of available products to remove: ");

                for (Product product : FileProcessing.getShopFromFile(currentUser.getShopID()).getProductList()) {
                    System.out.println("Name: " + product.getProductName() + " ID: " + product.getProductId());
                }
                int idOfProductToRemove = Integer.parseInt(scanner.nextLine());
                currentUser.removeItem(idOfProductToRemove);
                break;
            case ResponseType.addStockItem:
                for (Product product : FileProcessing.getShopFromFile(currentUser.getShopID()).getProductList()) {
                    System.out.println(
                            "ID: " + product.getProductId() +
                                    " Name: " + product.getProductName() +
                                    " Quantity:  " + product.getQuantity() +"\n"
                    );
                }
                System.out.print("Select a product ID: ");
                int idOfProductToAddStock = Integer.parseInt(scanner.nextLine());
                System.out.print("Quantity to add:");
                double quantityToAdd = Double.parseDouble(scanner.nextLine());
                currentUser.modifyItem(idOfProductToAddStock, TransactionType.adding,quantityToAdd);
                break;
            case ResponseType.removeStockItem:
                for (Product product : FileProcessing.getShopFromFile(currentUser.getShopID()).getProductList()) {
                    System.out.println(
                            "ID: " + product.getProductId() +
                                    " Name: " + product.getProductName() +
                                    " Quantity:  " + product.getQuantity() +"\n"
                    );
                }
                System.out.print("Select a product ID: ");
                int idOfProductToRemoveStock = Integer.parseInt(scanner.nextLine());
                System.out.print("Quantity to subtract:");
                double quantityToRemove = Double.parseDouble(scanner.nextLine());
                currentUser.modifyItem(idOfProductToRemoveStock, TransactionType.substracing,quantityToRemove);
                break;
            case ResponseType.stockOfShop:
                if(FileProcessing.getShopFromFile(currentUser.getShopID()).getProductList().isEmpty()){
                    System.out.println("No products here");
                }
                for (Product product : FileProcessing.getShopFromFile(currentUser.getShopID()).getProductList()) {
                    System.out.println(
                            "ID: " + product.getProductId() +
                                    " Name: " + product.getProductName() +
                                    " Quantity:  " + product.getQuantity() +
                                    " Unit: " + product.getUnit() +
                                    " Price/Unit: " + product.getPricePerUnit()
                    );
                }
                break;
            case ResponseType.logOut:
                Auth.logOut();
                return null;
            case ResponseType.quit:
                System.exit(0);
            case ResponseType.back:
                return currentUser;
        }

        System.out.println("Press any key to continue...");
        String exitKey = "";
        while (exitKey.isEmpty()) {
            exitKey = scanner.nextLine();
        }
        return currentUser;
    }
}
