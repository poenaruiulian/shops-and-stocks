package main.files;

import main.classes.*;
import main.enums.UserType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This class holds all the file processing functions that a user could have access to.
 */
public class FileProcessing {

    /**
     * @param newShop - shop object that will be written in the shops.csv file
     */
    static public void addNewShopToFile(Shop newShop) {
        try {
            FileWriter csvWriter = new FileWriter("./src/main/files/shops.csv", true);
            String employees = "";
            for (Employee employee : newShop.getEmployeesList()) {
                employees += employee.getUserID() + ";";
            }
            String products = "";
            for (Product product : newShop.getProductList()) {
                products += product.getProductId() + ";";
            }
            String shopToString = newShop.getShopID() + "," + newShop.getOwnerID() + "," + newShop.getShopManagerID() + "," + employees + "," + products + "\n";
            csvWriter.append(shopToString);
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param newPerson An object of type Employee, ShopManager or CEO
     *                  The function is writing data to corresponding file depending on what type of user its gets as param
     */
    static public void addNewUser(Person newPerson) {

        try {
            FileWriter csvWriter = new FileWriter("./src/main/files/users.csv", true);
            String userToWrite = "";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d uuuu", Locale.ENGLISH);
            String dateOfBirth = newPerson.getDateOfBirth().format(formatter);
            String address = newPerson.getAddress().replace(",", " ");


            if (newPerson instanceof ShopManager) {
                String dateOfEmployment = ((ShopManager) newPerson).getDateOfEmployment().format(formatter);
                String employees = "";
                for (Employee employee : ((ShopManager) newPerson).getEmployeeList()) {
                    employees += employee.getUserID() + ";";
                }
                userToWrite = newPerson.getUserID() + "," + newPerson.getUserType() + "," + newPerson.getFirstName() + "," + newPerson.getLastName() + "," + dateOfBirth + "," + newPerson.getPhoneNumber() + "," + address + "," + ((ShopManager) newPerson).getShopID() + "," + dateOfEmployment + "," + employees + ",-\n";
            } else if (newPerson instanceof Employee) {
                String dateOfEmployment = ((Employee) newPerson).getDateOfEmployment().format(formatter);
                userToWrite = newPerson.getUserID() + "," + newPerson.getUserType() + "," + newPerson.getFirstName() + "," + newPerson.getLastName() + "," + dateOfBirth + "," + newPerson.getPhoneNumber() + "," + address + "," + ((Employee) newPerson).getShopID() + "," + dateOfEmployment + ",-,-\n";
            } else if (newPerson instanceof CEO) {
                String employees = "";
                for (Employee employee : ((CEO) newPerson).getEmployeeList()) {
                    employees += employee.getUserID() + ";";
                }
                String shops = "";
                for (Shop shop : ((CEO) newPerson).getShopList()) {
                    shops += shop.getShopID() + ";";
                }
                userToWrite = newPerson.getUserID() + "," + newPerson.getUserType() + "," + newPerson.getFirstName() + "," + newPerson.getLastName() + "," + dateOfBirth + "," + newPerson.getPhoneNumber() + "," + address + ",-,-," + employees + "," + shops + "\n";
            }

            csvWriter.append(userToWrite);
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param newProduct An object of type Product that will be written in the file products.csv
     */
    static public void addNewProductToFile(Product newProduct) {

        try {
            FileWriter csvWriter = new FileWriter("./src/main/files/products.csv", true);
            String productToString = newProduct.toString();
            csvWriter.append(productToString);
//            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param shopID The id of the shop to get from the file.
     * @return An object of type Shop that has the id specified in params.
     */
    static public Shop getShopFromFile(int shopID) {
        Shop responseShop = new Shop();
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("./src/main/files/shops.csv"));
            String fileRow = csvReader.readLine();
            while ((fileRow = csvReader.readLine()) != null) {
                String[] data = fileRow.split(",");
                if (Integer.parseInt(data[0]) == shopID) {
                    List<Employee> employees = new ArrayList<>();
                    List<Product> products = new ArrayList<>();

                    String[] employeesIDS = data[3].split(";");
                    String[] productsIDS = data[4].split(";");

                    for (String employeesID : employeesIDS) {
                        Employee employee = (Employee) getUserFromFile(Integer.parseInt(employeesID));
                        employees.add(employee);
                    }

                    for (String productsID : productsIDS) {
                        Product product = getProductFromFile(Integer.parseInt(productsID));
                        products.add(product);
                    }

                    responseShop = new Shop(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), employees, products);
                }
            }

            csvReader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return responseShop;
    }

    /**
     * @param employeeID The id of the employee to get from the file.
     * @return An object of type Employee or ShopManager (which is extended from Employee) that has the id specified in the params.
     */
    static public Person getUserFromFile(int employeeID) {
        Person responseEmployee = new Person();

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("./src/main/files/users.csv"));
            String fileRow = csvReader.readLine();
            while ((fileRow = csvReader.readLine()) != null) {
                String[] data = fileRow.split(",");
                if (Integer.parseInt(data[0]) == employeeID) {
                    if (Objects.equals(data[1], UserType.employee)) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d yyyy", Locale.ENGLISH);
                        LocalDate dateOfBirth = LocalDate.parse(data[4], formatter);
                        LocalDate dateOfEmployment = LocalDate.parse(data[8], formatter);
                        responseEmployee = new Employee(Integer.parseInt(data[0]), UserType.employee, data[2], data[3], dateOfBirth, data[5], data[6], Integer.parseInt(data[7]), dateOfEmployment);
                    } else if (Objects.equals(data[1], UserType.manager)) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d yyyy", Locale.ENGLISH);
                        LocalDate dateOfBirth = LocalDate.parse(data[4], formatter);
                        LocalDate dateOfEmployment = LocalDate.parse(data[8], formatter);

                        List<Employee> employees = new ArrayList<>();
                        for (String string : data[9].split(";")) {
                            employees.add((Employee) getUserFromFile(Integer.parseInt(string)));
                        }
                        responseEmployee = new ShopManager(Integer.parseInt(data[0]), UserType.manager, data[2], data[3], dateOfBirth, data[5], data[6], Integer.parseInt(data[7]), dateOfEmployment, employees);
                    } else if (Objects.equals(data[1], UserType.ceo)) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d yyyy", Locale.ENGLISH);
                        LocalDate dateOfBirth = LocalDate.parse(data[4], formatter);
                        List<Employee> employees = new ArrayList<>();
                        for (String string : data[9].split(";")) {
                            employees.add((Employee) getUserFromFile(Integer.parseInt(string)));
                        }
                        List<Shop> shops = new ArrayList<>();
                        for (String string : data[10].split(";")) {
                            shops.add(getShopFromFile(Integer.parseInt(string)));
                        }

                        responseEmployee = new CEO(Integer.parseInt(data[0]), UserType.ceo, data[2], data[3], dateOfBirth, data[5], data[6], employees, shops);

                    }
                }
            }

            csvReader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return responseEmployee;
    }

    /**
     * @param productID The id of the product to get from the file.
     * @return An object of type Product that has the id specified in params.
     */
    static public Product getProductFromFile(int productID) {
        Product responseProduct = new Product();


        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("./src/main/files/products.csv"));
            String fileRow = csvReader.readLine();
            while ((fileRow = csvReader.readLine()) != null) {
                String[] data = fileRow.split(",");
                if (Integer.parseInt(data[0]) == productID) {


                    responseProduct = new Product(Integer.parseInt(data[0]), data[1], Double.parseDouble(data[2]), data[3], Double.parseDouble(data[4]));
                }
            }
            csvReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return responseProduct;

    }

    /**
     * @param filename the name of the file we want to work in : products, shops or users
     * @return the last id used in the specified file
     */
    static public int getLastIDUsed(String filename) {
        int lastIdUsed = -1;
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("./src/main/files/" + filename + ".csv"));
            String fileRow = csvReader.readLine();
            while ((fileRow = csvReader.readLine()) != null) {
                String[] data = fileRow.split(",");
                lastIdUsed = Integer.parseInt(data[0]);
            }
            csvReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lastIdUsed;
    }
}
