package main.libs;

import main.data.*;
import main.helpers.Auth;
import main.helpers.FileProcessing;
import main.libs.constants.Constants;
import main.types.ResponseType;
import main.types.TransactionType;
import main.types.UnitType;
import main.types.UserType;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class App extends JFrame {

    Person currentUser = null;
    JPanel authScreen;
    JPanel responseScreen;


    public App() throws IOException {

        this.setTitle("Shops & Stocks");
        this.setSize(new Dimension(Constants.MAX_WIDTH, Constants.MAX_HEIGHT));
        this.setLayout(new BorderLayout());

        this.authScreen = new JPanel();
        this.responseScreen = new JPanel();


        // initialize screens
        handleAuth();

        if (!Auth.isUserLogged()) {
            this.add(authScreen);
        } else {
            this.remove(authScreen);
            currentUser = Auth.getCurrentUser();
            System.out.println(currentUser.getLastName());
            if (currentUser != null) {
                handleOptionsScreen();
                handleEmployees();
                this.setTitle("Shops & Stocks -" + currentUser.getFirstName() + " " + currentUser.getLastName());
                this.add(handleOptionsScreen());
                this.revalidate();
                this.repaint();
            }
        }
    }

    void handleAuth() {

        final Person[] currentUser = new Person[1];
        currentUser[0] = null;

        JPanel firstNamePanel = new JPanel();
        JPanel lastNamePanel = new JPanel();

        JLabel title = new JLabel("Login:");
        JLabel firstName = new JLabel("First name:");
        JLabel lastName = new JLabel("Last name");
        JTextField firstNameField = new JTextField(20);
        JTextField lastNameField = new JTextField(20);
        JButton submit = new JButton("Submit");
        JLabel warnings = new JLabel("");

        this.authScreen.setSize(new Dimension(Constants.MAX_WIDTH, Constants.MAX_HEIGHT));
        this.authScreen.setLayout(new FlowLayout());

        firstNamePanel.setSize(new Dimension(Constants.MAX_WIDTH, 24));
        firstNamePanel.setLayout(new FlowLayout());

        lastNamePanel.setSize(new Dimension(Constants.MAX_WIDTH, 24));
        lastNamePanel.setLayout(new FlowLayout());

        title.setPreferredSize(new Dimension(Constants.MAX_WIDTH, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        firstName.setPreferredSize(new Dimension(120, 24));
        firstName.setHorizontalAlignment(SwingConstants.RIGHT);
        firstNameField.setPreferredSize(new Dimension(Constants.MAX_WIDTH / 3, 24));

        lastName.setPreferredSize(new Dimension(120, 24));
        lastName.setHorizontalAlignment(SwingConstants.RIGHT);
        lastNameField.setPreferredSize(new Dimension(Constants.MAX_WIDTH - 100, 24));

        submit.setPreferredSize(new Dimension(100, 24));
        submit.setHorizontalAlignment(SwingConstants.CENTER);

        warnings.setPreferredSize(new Dimension(Constants.MAX_WIDTH, 24));
        warnings.setHorizontalAlignment(SwingConstants.CENTER);


        submit.addActionListener((e) -> {
            System.out.println("1");
            String firstNameString = firstNameField.getText();
            String lastNameString = lastNameField.getText();

            if (firstNameString.isEmpty() || lastNameString.isEmpty()) {
                warnings.setText("Something went wrong");

            } else {
                currentUser[0] = Auth.loggInUser(firstNameString, lastNameString);
                if (currentUser[0] != null) {
                    System.out.println("2");

                    this.currentUser = currentUser[0];
                    this.setTitle("Shops & Stocks -" + this.currentUser.getFirstName() + " " + this.currentUser.getLastName());

                    this.remove(authScreen);
                    this.add(handleOptionsScreen());
                    this.revalidate();
                    this.repaint();

                    warnings.setText("");
                    firstNameField.setText("");
                    lastNameField.setText("");
                } else {
                    warnings.setText("No user found!");
                }
            }
        });


        lastNamePanel.add(lastName);
        lastNamePanel.add(lastNameField);

        firstNamePanel.add(firstName);
        firstNamePanel.add(firstNameField);


        this.authScreen.add(title);
        this.authScreen.add(firstNamePanel);
        this.authScreen.add(lastNamePanel);
        this.authScreen.add(submit);
    }

    JPanel handleOptionsScreen() {
        JPanel optionsScreen = new JPanel();
        optionsScreen.setLayout(new GridLayout(2, 3));
        optionsScreen.setPreferredSize(new Dimension(Constants.MAX_WIDTH, Constants.MAX_HEIGHT));

        JButton employees = new JButton("Employees");
        JButton shops = new JButton("Shops");
        JButton products = new JButton("Products");
        JButton logout = new JButton(new ImageIcon("./src/main/libs/assets/logout.png"));
        JButton quit = new JButton(new ImageIcon("./src/main/libs/assets/exit.png"));

        employees.setFont(Constants.monospacedBig);
        shops.setFont(Constants.monospacedBig);
        products.setFont(Constants.monospacedBig);


        logout.addActionListener((e) -> {
            Auth.logOut();
            this.remove(optionsScreen);
            this.add(authScreen);
            this.revalidate();
            this.repaint();
            this.currentUser = null;
            this.setTitle("Shops & Stocks");
        });
        quit.addActionListener((e) -> {
            System.exit(0);
        });
        employees.addActionListener((e) -> {
            this.remove(optionsScreen);
            this.add(handleEmployees());
            this.revalidate();
            this.repaint();
        });

        JPanel exitOptions = new JPanel();
        exitOptions.setLayout(new GridLayout(1, 2));
        exitOptions.setPreferredSize(new Dimension(Constants.MAX_WIDTH, 80));

        if (Objects.equals(currentUser.getUserType(), UserType.ceo) || Objects.equals(currentUser.getUserType(), UserType.manager)) {
            optionsScreen.add(employees);
        }

        if (Objects.equals(currentUser.getUserType(), UserType.ceo)) {
            optionsScreen.add(shops);
        }

        products.addActionListener((e) -> {
            this.remove(optionsScreen);
            this.add(handleProducts());
            this.revalidate();
            this.repaint();
        });
        optionsScreen.add(products);

        exitOptions.add(logout);
        exitOptions.add(quit);

        optionsScreen.add(exitOptions);

        return optionsScreen;
    }

    JPanel handleEmployees() {

        JPanel employeesScreen = new JPanel();
        employeesScreen.setLayout(new BorderLayout());

        JPanel grid = new JPanel();

        grid.setLayout(new GridLayout(2, 3));
        grid.setPreferredSize(new Dimension(Constants.MAX_WIDTH, Constants.MAX_HEIGHT - 60));

        JButton allEmployees = new JButton();
        allEmployees.setLayout(new GridLayout(3, 1));
        allEmployees.add(new JLabel(""));
        JLabel allEmployeesIcon = new JLabel(new ImageIcon("./src/main/libs/assets/allemployees.png"));
        allEmployeesIcon.setHorizontalAlignment(SwingConstants.CENTER);
        allEmployees.add(allEmployeesIcon);
        JLabel allEmployeesLabel = new JLabel("See all employees");
        allEmployeesLabel.setFont(Constants.monospacedSmall);
        allEmployeesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        allEmployees.add(allEmployeesLabel);
        allEmployees.addActionListener((e) -> {
            this.remove(employeesScreen);
            handleResponse(ResponseType.allEmployees, "employees");
            this.revalidate();
            this.repaint();
        });

        JButton hireAnEmployee = new JButton();
        hireAnEmployee.setLayout(new GridLayout(3, 1));
        hireAnEmployee.add(new JLabel(""));
        JLabel hireAnEmployeeIcon = new JLabel(new ImageIcon("./src/main/libs/assets/hire.png"));
        hireAnEmployeeIcon.setHorizontalAlignment(SwingConstants.CENTER);
        hireAnEmployee.add(hireAnEmployeeIcon);
        JLabel hireAnEmployeeLabel = new JLabel("Hire an employee");
        hireAnEmployeeLabel.setFont(Constants.monospacedSmall);
        hireAnEmployeeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hireAnEmployee.add(hireAnEmployeeLabel);
        hireAnEmployee.addActionListener((e) -> {
            this.remove(employeesScreen);
            handleResponse(ResponseType.hireEmployee, "employees");
            this.revalidate();
            this.repaint();
        });

        JButton fireAnEmployee = new JButton("");
        fireAnEmployee.setLayout(new GridLayout(3, 1));
        fireAnEmployee.add(new JLabel(""));
        JLabel fireAnEmployeeIcon = new JLabel(new ImageIcon("./src/main/libs/assets/fire.png"));
        fireAnEmployeeIcon.setHorizontalAlignment(SwingConstants.CENTER);
        fireAnEmployee.add(fireAnEmployeeIcon);
        JLabel fireAnEmployeeLabel = new JLabel("Fire an employee");
        fireAnEmployeeLabel.setFont(Constants.monospacedSmall);
        fireAnEmployeeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fireAnEmployee.add(fireAnEmployeeLabel);
        fireAnEmployee.addActionListener((e) -> {
            this.remove(employeesScreen);
            handleResponse(ResponseType.fireEmployee, "employees");
            this.revalidate();
            this.repaint();
        });

        JButton hireAManager = new JButton("");
        hireAManager.setLayout(new GridLayout(3, 1));
        hireAManager.add(new JLabel(""));
        JLabel hireAManagerIcon = new JLabel(new ImageIcon("./src/main/libs/assets/hiremanager.png"));
        hireAManagerIcon.setHorizontalAlignment(SwingConstants.CENTER);
        hireAManager.add(hireAManagerIcon);
        JLabel hireAManagerLabel = new JLabel("Hire a manager");
        hireAManagerLabel.setFont(Constants.monospacedSmall);
        hireAManagerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hireAManager.add(hireAManagerLabel);
        hireAManager.addActionListener((e) -> {
            this.remove(employeesScreen);
            handleResponse(ResponseType.hireManager, "employees");
            this.revalidate();
            this.repaint();
        });

        JButton fireAManager = new JButton("");
        fireAManager.setLayout(new GridLayout(3, 1));
        fireAManager.add(new JLabel(""));
        JLabel fireAManagerIcon = new JLabel(new ImageIcon("./src/main/libs/assets/firemanager.png"));
        fireAManagerIcon.setHorizontalAlignment(SwingConstants.CENTER);
        fireAManager.add(fireAManagerIcon);
        JLabel fireAManagerLabel = new JLabel("Fire a manager");
        fireAManagerLabel.setFont(Constants.monospacedSmall);
        fireAManagerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fireAManager.add(fireAManagerLabel);
        fireAManager.addActionListener((e) -> {
            this.remove(employeesScreen);
            handleResponse(ResponseType.fireManager, "employees");
            this.revalidate();
            this.repaint();
        });

        JButton transferAnEmployee = new JButton("");
        transferAnEmployee.setLayout(new GridLayout(3, 1));
        transferAnEmployee.add(new JLabel(""));
        JLabel transferAnEmployeeIcon = new JLabel(new ImageIcon("./src/main/libs/assets/transfer.png"));
        transferAnEmployeeIcon.setHorizontalAlignment(SwingConstants.CENTER);
        transferAnEmployee.add(transferAnEmployeeIcon);
        JLabel transferAnEmployeeLabel = new JLabel("Transfer");
        transferAnEmployeeLabel.setFont(Constants.monospacedSmall);
        transferAnEmployeeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        transferAnEmployee.add(transferAnEmployeeLabel);
        transferAnEmployee.addActionListener((e) -> {
            this.remove(employeesScreen);
            handleResponse(ResponseType.transferEmployee, "employees");
            this.revalidate();
            this.repaint();
        });

        JMenuBar bar = new JMenuBar();
        JMenuItem back = new JMenuItem("←");
        back.setFont(Constants.backButton);
        back.addActionListener((e) -> {
            this.remove(employeesScreen);
            this.add(handleOptionsScreen());
            this.revalidate();
            this.repaint();
        });
        bar.add(back);
        employeesScreen.add(bar, BorderLayout.NORTH);


        grid.add(allEmployees);
        grid.add(hireAnEmployee);
        grid.add(fireAnEmployee);

        if (currentUser.getUserType().equals(UserType.ceo)) {
            grid.add(hireAManager);
            grid.add(fireAManager);
            grid.add(transferAnEmployee);
        }

        employeesScreen.add(grid, BorderLayout.SOUTH);
        return employeesScreen;
    }

    JPanel handleProducts() {
        JPanel productsScreen = new JPanel();
        productsScreen.setLayout(new BorderLayout());
        this.setSize(new Dimension(Constants.MAX_WIDTH + 200, Constants.MAX_HEIGHT));


        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(1, 3));
        grid.setPreferredSize(new Dimension(Constants.MAX_WIDTH, Constants.MAX_HEIGHT - 60));

        JButton showAllStock = new JButton();
        showAllStock.setLayout(new GridLayout(3, 1));
        showAllStock.add(new JLabel(""));
        JLabel showAllStockIcon = new JLabel(new ImageIcon("./src/main/libs/assets/stock.png"));
        showAllStockIcon.setHorizontalAlignment(SwingConstants.CENTER);
        showAllStock.add(showAllStockIcon);
        JLabel showAllStockLabel = new JLabel("See stock of shop");
        showAllStockLabel.setFont(Constants.monospacedSmall);
        showAllStockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        showAllStock.add(showAllStockLabel);
        showAllStock.addActionListener((e) -> {
            this.remove(productsScreen);
            this.setSize(new Dimension(Constants.MAX_WIDTH, Constants.MAX_HEIGHT));
            handleResponse(ResponseType.stockOfShop, "products");
            this.revalidate();
            this.repaint();
        });
        grid.add(showAllStock);

        JButton modifyShopsStock = new JButton();
        modifyShopsStock.setLayout(new GridLayout(3, 1));
        modifyShopsStock.add(new JLabel(""));
        JLabel modifyShopsStockIcon = new JLabel(new ImageIcon("./src/main/libs/assets/edit.png"));
        modifyShopsStockIcon.setHorizontalAlignment(SwingConstants.CENTER);
        modifyShopsStock.add(modifyShopsStockIcon);
        JLabel modifyShopsStockLabel = new JLabel("Modify shop stock");
        modifyShopsStockLabel.setFont(Constants.monospacedSmall);
        modifyShopsStockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        modifyShopsStock.add(modifyShopsStockLabel);
        modifyShopsStock.addActionListener((e) -> {
            this.remove(productsScreen);
            this.setSize(new Dimension(Constants.MAX_WIDTH, Constants.MAX_HEIGHT));
            handleResponse(ResponseType.addItem, "products");
            this.revalidate();
            this.repaint();
        });
        grid.add(modifyShopsStock);


        JButton modifyProductQuantity = new JButton();
        modifyProductQuantity.setLayout(new GridLayout(3, 1));
        modifyProductQuantity.add(new JLabel(""));
        JLabel modifyProductQuantityIcon = new JLabel(new ImageIcon("./src/main/libs/assets/quantity.png"));
        modifyProductQuantityIcon.setHorizontalAlignment(SwingConstants.CENTER);
        modifyProductQuantity.add(modifyProductQuantityIcon);
        JLabel modifyProductQuantityLabel = new JLabel("Modify products quantities");
        modifyProductQuantityLabel.setFont(Constants.monospacedSmall);
        modifyProductQuantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        modifyProductQuantity.add(modifyProductQuantityLabel);
        modifyProductQuantity.addActionListener((e) -> {
            this.remove(productsScreen);
            this.setSize(new Dimension(Constants.MAX_WIDTH, Constants.MAX_HEIGHT));
            handleResponse(ResponseType.addStockItem, "products");
            this.revalidate();
            this.repaint();
        });
        grid.add(modifyProductQuantity);

        JMenuBar bar = new JMenuBar();
        JMenuItem back = new JMenuItem("←");
        back.setFont(Constants.backButton);
        back.addActionListener((e) -> {
            this.setSize(new Dimension(Constants.MAX_WIDTH, Constants.MAX_HEIGHT));
            this.remove(productsScreen);
            this.add(handleOptionsScreen());
            this.revalidate();
            this.repaint();
        });
        bar.add(back);
        productsScreen.add(bar, BorderLayout.NORTH);

        productsScreen.add(grid, BorderLayout.SOUTH);
        return productsScreen;
    }

    void handleResponse(String response, String fromWhere) {
        this.responseScreen.setSize(new Dimension(Constants.MAX_WIDTH, Constants.MAX_HEIGHT));
        this.responseScreen.setLayout(new BorderLayout());

        JMenuBar bar = new JMenuBar();
        JMenuItem back = new JMenuItem("←");
        back.setFont(Constants.backButton);
        back.addActionListener((e) -> {
            this.setSize(new Dimension(Constants.MAX_WIDTH, Constants.MAX_HEIGHT));
            this.setTitle("Shops & Stocks -" + currentUser.getFirstName() + " " + currentUser.getLastName());
            this.remove(responseScreen);
            if (Objects.equals(fromWhere, "employees")) {
                this.add(handleEmployees());
            } else if (Objects.equals(fromWhere, "products")) {
                this.add(handleProducts());
            } else {
                //TODO handle shops
            }
            this.revalidate();
            this.repaint();
        });
        bar.add(back);

        if (response.equals(ResponseType.allEmployees)) {

            this.responseScreen.setSize(new Dimension(Constants.MAX_WIDTH * 2 + 100, Constants.MAX_HEIGHT));
            this.setSize(new Dimension(Constants.MAX_WIDTH * 2 + 100, Constants.MAX_HEIGHT));
            this.setTitle("Shops & Stocks - All Employees");

            int numberOfEmployees;
            List<Employee> employeeList = new ArrayList<>();
            if (Objects.equals(currentUser.getUserType(), UserType.ceo)) {
                employeeList.addAll(((CEO) currentUser).getEmployeeList());
            } else {
                employeeList.addAll(((ShopManager) currentUser).getEmployeeList());

            }
            numberOfEmployees = employeeList.size();
            JPanel allEmployees = new JPanel(new GridLayout(numberOfEmployees + 1, 7, 10, 10));
            JLabel titleID = new JLabel("ID");
            titleID.setFont(Constants.monospacedSmall);
            allEmployees.add(titleID);
            JLabel titleRole = new JLabel("ROLE");
            titleRole.setFont(Constants.monospacedSmall);
            allEmployees.add(titleRole);
            JLabel firstNameTitle = new JLabel("First Name");
            firstNameTitle.setFont(Constants.monospacedSmall);
            allEmployees.add(firstNameTitle);
            JLabel lastNameTitle = new JLabel("Last Name");
            lastNameTitle.setFont(Constants.monospacedSmall);
            allEmployees.add(lastNameTitle);
            JLabel addressTitle = new JLabel("Address");
            addressTitle.setFont(Constants.monospacedSmall);
            allEmployees.add(addressTitle);
            JLabel phoneNumberTitle = new JLabel("Phone number");
            phoneNumberTitle.setFont(Constants.monospacedSmall);
            allEmployees.add(phoneNumberTitle);
            JLabel employmentDateTitle = new JLabel("Employment Date");
            employmentDateTitle.setFont(Constants.monospacedSmall);
            allEmployees.add(employmentDateTitle);
            JLabel birthDateTitle = new JLabel("Birth Date");
            birthDateTitle.setFont(Constants.monospacedSmall);
            allEmployees.add(birthDateTitle);
            for (Employee employee : employeeList) {
                allEmployees.add(new JLabel(String.valueOf(employee.getUserID())));
                allEmployees.add(new JLabel(employee.getUserType()));
                allEmployees.add(new JLabel(employee.getFirstName()));
                allEmployees.add(new JLabel(employee.getLastName()));
                allEmployees.add(new JLabel(employee.getAddress()));
                allEmployees.add(new JLabel(employee.getPhoneNumber()));
                allEmployees.add(new JLabel(employee.getDateOfEmployment().toString()));
                allEmployees.add(new JLabel(employee.getDateOfBirth().toString()));
            }


            responseScreen.removeAll();
            responseScreen.add(allEmployees);
            responseScreen.add(bar, BorderLayout.NORTH);
            responseScreen.revalidate();
            responseScreen.repaint();

        } else if (response.equals(ResponseType.hireEmployee)) {
            this.setTitle("Shops & Stocks - Hire An Employee");


            JTextField firstNameField = new JTextField();
            JTextField lastNameField = new JTextField();
            JTextField addressField = new JTextField();
            JTextField phoneNumberField = new JTextField();
            SpinnerNumberModel dayModel = new SpinnerNumberModel(15, 1, 21, 1);
            SpinnerNumberModel monthModel = new SpinnerNumberModel(3, 1, 12, 1);
            SpinnerNumberModel yearModel = new SpinnerNumberModel(2000, 1940, 2006, 1);
            JSpinner daySpinner = new JSpinner(dayModel);
            JSpinner monthSpinner = new JSpinner(monthModel);
            JSpinner yearSpinner = new JSpinner(yearModel);
            JLabel firstNameLabel = new JLabel("First name: ");
            firstNameLabel.setFont(Constants.monospacedSmall);
            JLabel lastNameLabel = new JLabel("Last name: ");
            lastNameLabel.setFont(Constants.monospacedSmall);
            JLabel addressLabel = new JLabel("Address: ");
            addressLabel.setFont(Constants.monospacedSmall);
            JLabel phoneNumberLabel = new JLabel("Phone number: ");
            phoneNumberLabel.setFont(Constants.monospacedSmall);
            JLabel birthDateLabel = new JLabel("Birth date: ");
            birthDateLabel.setFont(Constants.monospacedSmall);

            JPanel firstNamePanel = new JPanel();
            firstNamePanel.setSize(new Dimension(Constants.MAX_WIDTH, 40));
            firstNameField.setPreferredSize(new Dimension(Constants.MAX_WIDTH / 3, 24));
            firstNamePanel.add(firstNameLabel);
            firstNamePanel.add(firstNameField);

            JPanel lastNamePanel = new JPanel();
            lastNamePanel.setSize(new Dimension(Constants.MAX_WIDTH, 40));
            lastNameField.setPreferredSize(new Dimension(Constants.MAX_WIDTH / 3, 24));
            lastNamePanel.add(lastNameLabel);
            lastNamePanel.add(lastNameField);

            JPanel addressPanel = new JPanel();
            addressPanel.setSize(new Dimension(Constants.MAX_WIDTH, 40));
            addressField.setPreferredSize(new Dimension(Constants.MAX_WIDTH / 3, 24));
            addressPanel.add(addressLabel);
            addressPanel.add(addressField);

            JPanel phoneNumberPanel = new JPanel();
            phoneNumberPanel.setSize(new Dimension(Constants.MAX_WIDTH, 40));
            phoneNumberField.setPreferredSize(new Dimension(Constants.MAX_WIDTH / 3, 24));
            phoneNumberPanel.add(phoneNumberLabel);
            phoneNumberPanel.add(phoneNumberField);

            JPanel birthDatePanel = new JPanel();
            birthDatePanel.add(birthDateLabel);
            birthDatePanel.add(daySpinner);
            birthDatePanel.add(monthSpinner);
            birthDatePanel.add(yearSpinner);


            JButton addUser = new JButton("Hire Employee");
            addUser.setPreferredSize(new Dimension(100, 24));
            addUser.setFont(Constants.monospacedRegular);


            JPanel grid = new JPanel();
            grid.setLayout(new GridLayout(7, 1));
            grid.add(firstNamePanel);
            grid.add(lastNamePanel);
            grid.add(addressPanel);
            grid.add(phoneNumberPanel);
            grid.add(birthDatePanel);

            JComboBox<Integer> cb;
            if (Objects.equals(currentUser.getUserType(), UserType.ceo)) {
                Integer[] listOfIds = new Integer[((CEO) currentUser).getShopList().size()];
                List<Shop> shopList = ((CEO) currentUser).getShopList();
                int k = 0;
                for (Shop shop : shopList) {
                    listOfIds[k] = shop.getShopID();
                    k++;
                }

                cb = new JComboBox<>(listOfIds);

                JPanel shopIdPanel = new JPanel();
                JLabel selectShopLabel = new JLabel("Select an available shop: ");
                selectShopLabel.setFont(Constants.monospacedSmall);
                shopIdPanel.add(selectShopLabel);
                shopIdPanel.add(cb);

                grid.add(shopIdPanel);
            } else {
                cb = null;
            }

            grid.add(addUser);

            addUser.addActionListener((e) -> {
                if (Objects.equals(currentUser.getUserType(), UserType.ceo)) {
                    ((CEO) currentUser).hireEmployee(firstNameField.getText(), lastNameField.getText(), LocalDate.of((int) yearSpinner.getValue(), (int) monthSpinner.getValue(), (int) daySpinner.getValue()), phoneNumberField.getText(), addressField.getText(), (Integer) cb.getSelectedItem());
                } else {
                    ((ShopManager) currentUser).hireEmployee(firstNameField.getText(), lastNameField.getText(), LocalDate.of((int) yearSpinner.getValue(), (int) monthSpinner.getValue(), (int) daySpinner.getValue()), phoneNumberField.getText(), addressField.getText());
                }

                this.remove(responseScreen);
                if (Objects.equals(fromWhere, "employees")) {
                    this.add(handleEmployees());
                } else if (Objects.equals(fromWhere, "products")) {
                    this.add(handleProducts());
                } else {
                    //TODO handle shops
                }
                this.revalidate();
                this.repaint();
            });

            responseScreen.removeAll();
            responseScreen.add(grid);
            responseScreen.add(bar, BorderLayout.NORTH);
            responseScreen.revalidate();
            responseScreen.repaint();

        } else if (response.equals(ResponseType.fireEmployee)) {
            this.setTitle("Shops & Stocks - Fire An Employee");

            JPanel grid = new JPanel();
            grid.setLayout(new FlowLayout());
            if (Objects.equals(currentUser.getUserType(), UserType.ceo)) {
                JComboBox<Integer> cb;
                Integer[] listOfShopIds = new Integer[((CEO) currentUser).getShopList().size()];
                List<Shop> shopList = ((CEO) currentUser).getShopList();
                int k = 0;
                for (Shop shop : shopList) {
                    listOfShopIds[k] = shop.getShopID();
                    k++;
                }
                cb = new JComboBox<>(listOfShopIds);
                JPanel shopIdPanel = new JPanel();
                JLabel availableShopLabel = new JLabel("Select an available shop: ");
                availableShopLabel.setFont(Constants.monospacedSmall);
                shopIdPanel.add(availableShopLabel);
                shopIdPanel.add(cb);

                grid.add(shopIdPanel);

                cb.addItemListener((e) -> {
                    grid.removeAll();
                    grid.add(shopIdPanel);

                    Shop selectedShop = FileProcessing.getShopFromFile((Integer) cb.getSelectedItem());
                    JComboBox<Integer> cb2;
                    Integer[] listOfEmployeeIds = new Integer[selectedShop.getEmployeesList().size()];
                    int h = 0;
                    for (Employee employee : selectedShop.getEmployeesList()) {
                        listOfEmployeeIds[h] = employee.getUserID();
                        h++;
                    }
                    cb2 = new JComboBox<>(listOfEmployeeIds);
                    JPanel userIdPanel = new JPanel();
                    JLabel userIDLabel = new JLabel("Select an employee to fire: ");
                    userIDLabel.setFont(Constants.monospacedSmall);
                    userIdPanel.add(userIDLabel);
                    userIdPanel.add(cb2);
                    grid.add(userIdPanel);

                    JButton fireUser = new JButton("Fire Employee");
                    fireUser.setPreferredSize(new Dimension(200, 32));
                    fireUser.setFont(Constants.monospacedSmall);
                    fireUser.addActionListener((f) -> {
                        ((CEO) currentUser).fireEmployee((Integer) cb2.getSelectedItem(), (Integer) cb.getSelectedItem());

                        this.remove(responseScreen);
                        if (Objects.equals(fromWhere, "employees")) {
                            this.add(handleEmployees());
                        } else if (Objects.equals(fromWhere, "products")) {
                            this.add(handleProducts());
                        } else {
                            //TODO handle shops
                        }
                        this.revalidate();
                        this.repaint();
                    });
                    grid.add(fireUser);
                    grid.revalidate();
                    grid.repaint();
                });


            } else {
                Shop selectedShop = FileProcessing.getShopFromFile(((ShopManager) currentUser).getShopID());
                JComboBox<Integer> cb2;
                Integer[] listOfEmployeeIds = new Integer[selectedShop.getEmployeesList().size()];
                int h = 0;
                for (Employee employee : selectedShop.getEmployeesList()) {
                    listOfEmployeeIds[h] = employee.getUserID();
                    h++;
                }
                cb2 = new JComboBox<>(listOfEmployeeIds);
                JPanel userIdPanel = new JPanel();
                JLabel userIDLabel = new JLabel("Select an employee to fire: ");
                userIDLabel.setFont(Constants.monospacedSmall);
                userIdPanel.add(userIDLabel);
                userIdPanel.add(cb2);
                grid.add(userIdPanel);

                JButton fireUser = new JButton("Fire Employee");
                fireUser.addActionListener((f) -> {
                    ((ShopManager) currentUser).fireEmployee((Integer) cb2.getSelectedItem());

                    this.remove(responseScreen);
                    if (Objects.equals(fromWhere, "employees")) {
                        this.add(handleEmployees());
                    } else if (Objects.equals(fromWhere, "products")) {
                        this.add(handleProducts());
                    } else {
                        //TODO handle shops
                    }
                    this.revalidate();
                    this.repaint();
                });
                grid.add(fireUser);
                grid.revalidate();
                grid.repaint();
            }
            responseScreen.removeAll();
            responseScreen.add(grid);
            responseScreen.add(bar, BorderLayout.NORTH);
            responseScreen.revalidate();
            responseScreen.repaint();
        } else if (response.equals(ResponseType.hireManager)) {

            this.setTitle("Shops & Stocks - Hire A Manager");

            JTextField firstNameField = new JTextField();
            JTextField lastNameField = new JTextField();
            JTextField addressField = new JTextField();
            JTextField phoneNumberField = new JTextField();
            SpinnerNumberModel dayModel = new SpinnerNumberModel(15, 1, 21, 1);
            SpinnerNumberModel monthModel = new SpinnerNumberModel(3, 1, 12, 1);
            SpinnerNumberModel yearModel = new SpinnerNumberModel(2000, 1940, 2006, 1);
            JSpinner daySpinner = new JSpinner(dayModel);
            JSpinner monthSpinner = new JSpinner(monthModel);
            JSpinner yearSpinner = new JSpinner(yearModel);
            JLabel firstNameLabel = new JLabel("First name: ");
            firstNameLabel.setFont(Constants.monospacedSmall);
            JLabel lastNameLabel = new JLabel("Last name: ");
            lastNameLabel.setFont(Constants.monospacedSmall);
            JLabel addressLabel = new JLabel("Address: ");
            addressLabel.setFont(Constants.monospacedSmall);
            JLabel phoneNumberLabel = new JLabel("Phone number: ");
            phoneNumberLabel.setFont(Constants.monospacedSmall);
            JLabel birthDateLabel = new JLabel("Birth date: ");
            birthDateLabel.setFont(Constants.monospacedSmall);

            JPanel firstNamePanel = new JPanel();
            firstNamePanel.setSize(new Dimension(Constants.MAX_WIDTH, 40));
            firstNameField.setPreferredSize(new Dimension(Constants.MAX_WIDTH / 3, 24));
            firstNamePanel.add(firstNameLabel);
            firstNamePanel.add(firstNameField);

            JPanel lastNamePanel = new JPanel();
            lastNamePanel.setSize(new Dimension(Constants.MAX_WIDTH, 40));
            lastNameField.setPreferredSize(new Dimension(Constants.MAX_WIDTH / 3, 24));
            lastNamePanel.add(lastNameLabel);
            lastNamePanel.add(lastNameField);

            JPanel addressPanel = new JPanel();
            addressPanel.setSize(new Dimension(Constants.MAX_WIDTH, 40));
            addressField.setPreferredSize(new Dimension(Constants.MAX_WIDTH / 3, 24));
            addressPanel.add(addressLabel);
            addressPanel.add(addressField);

            JPanel phoneNumberPanel = new JPanel();
            phoneNumberPanel.setSize(new Dimension(Constants.MAX_WIDTH, 40));
            phoneNumberField.setPreferredSize(new Dimension(Constants.MAX_WIDTH / 3, 24));
            phoneNumberPanel.add(phoneNumberLabel);
            phoneNumberPanel.add(phoneNumberField);

            JPanel birthDatePanel = new JPanel();
            birthDatePanel.add(birthDateLabel);
            birthDatePanel.add(daySpinner);
            birthDatePanel.add(monthSpinner);
            birthDatePanel.add(yearSpinner);


            JButton addUser = new JButton("Hire manager");
            addUser.setFont(Constants.monospacedRegular);


            JPanel grid = new JPanel();
            grid.setLayout(new GridLayout(7, 1));
            grid.add(firstNamePanel);
            grid.add(lastNamePanel);
            grid.add(addressPanel);
            grid.add(phoneNumberPanel);
            grid.add(birthDatePanel);

            JComboBox<Integer> cb;
            Integer[] listOfIds = new Integer[((CEO) currentUser).getShopList().size()];
            List<Shop> shopList = ((CEO) currentUser).getShopList().stream().filter(shop -> shop.getShopManagerID() == -1).toList();
            int k = 0;
            for (Shop shop : shopList) {
                listOfIds[k] = shop.getShopID();
                k++;
            }

            cb = new JComboBox<>(listOfIds);

            JPanel shopIdPanel = new JPanel();
            JLabel shopIDLabel = new JLabel("Select an available shop: ");
            shopIDLabel.setFont(Constants.monospacedSmall);
            shopIdPanel.add(shopIDLabel);
            shopIdPanel.add(cb);

            grid.add(shopIdPanel);

            grid.add(addUser);

            addUser.addActionListener((e) -> {
                ((CEO) currentUser).hireManager(firstNameField.getText(), lastNameField.getText(), LocalDate.of((int) yearSpinner.getValue(), (int) monthSpinner.getValue(), (int) daySpinner.getValue()), phoneNumberField.getText(), addressField.getText(), (Integer) cb.getSelectedItem());


                this.remove(responseScreen);
                if (Objects.equals(fromWhere, "employees")) {
                    this.add(handleEmployees());
                } else if (Objects.equals(fromWhere, "products")) {
                    this.add(handleProducts());
                } else {
                    //TODO handle shops
                }
                this.revalidate();
                this.repaint();
            });

            responseScreen.removeAll();
            responseScreen.add(grid);
            responseScreen.add(bar, BorderLayout.NORTH);
            responseScreen.revalidate();
            responseScreen.repaint();

        } else if (response.equals(ResponseType.fireManager)) {

            this.setTitle("Shops & Stocks - Fire A Manager");


            JPanel grid = new JPanel();
            grid.setLayout(new FlowLayout());

            JComboBox<Integer> cb;
            Integer[] listOfShopIds = new Integer[((CEO) currentUser).getShopList().size()];
            List<Shop> shopList = ((CEO) currentUser).getShopList().stream().filter(shop -> shop.getShopManagerID() != -1).toList();
            int k = 0;
            for (Shop shop : shopList) {
                listOfShopIds[k] = shop.getShopID();
                k++;
            }
            cb = new JComboBox<>(listOfShopIds);
            JPanel shopIdPanel = new JPanel();
            JLabel shopIDLabel = new JLabel("Select an available shop: ");
            shopIDLabel.setFont(Constants.monospacedSmall);
            shopIdPanel.add(shopIDLabel);
            shopIdPanel.add(cb);

            grid.add(shopIdPanel);

            cb.addItemListener((e) -> {
                grid.removeAll();
                grid.add(shopIdPanel);

                Shop selectedShop = FileProcessing.getShopFromFile((Integer) cb.getSelectedItem());

                JButton fireUser = new JButton("Fire Manager");
                fireUser.setFont(Constants.monospacedSmall);
                fireUser.setPreferredSize(new Dimension(200, 32));
                fireUser.addActionListener((f) -> {
                    ((CEO) currentUser).fireManager(selectedShop.getShopManagerID(), (Integer) cb.getSelectedItem());

                    this.remove(responseScreen);
                    if (Objects.equals(fromWhere, "employees")) {
                        this.add(handleEmployees());
                    } else if (Objects.equals(fromWhere, "products")) {
                        this.add(handleProducts());
                    } else {
                        //TODO handle shops
                    }
                    this.revalidate();
                    this.repaint();
                });
                grid.add(fireUser);
                grid.revalidate();
                grid.repaint();
            });


            responseScreen.removeAll();
            responseScreen.add(grid);
            responseScreen.add(bar, BorderLayout.NORTH);
            responseScreen.revalidate();
            responseScreen.repaint();
        } else if (response.equals(ResponseType.transferEmployee)) {

            this.setTitle("Shops & Stocks - Transfer An Employee");


            JPanel grid = new JPanel();
            grid.setLayout(new FlowLayout());

            JComboBox<Integer> cb;
            Integer[] listOfEmployeesIDs = new Integer[((CEO) currentUser).getEmployeeList().size()];
            List<Employee> employeeList = ((CEO) currentUser).getEmployeeList().stream().filter(employee -> !Objects.equals(employee.getUserType(), UserType.manager)).toList();
            int k = 0;
            for (Employee employee : employeeList) {
                listOfEmployeesIDs[k] = employee.getUserID();
                k++;
            }
            cb = new JComboBox<>(listOfEmployeesIDs);
            JPanel employeeIDPanel = new JPanel();
            JLabel employeeIDLabel = new JLabel("Select an employee to transfer: ");
            employeeIDLabel.setFont(Constants.monospacedSmall);
            employeeIDPanel.add(employeeIDLabel);
            employeeIDPanel.add(cb);

            grid.add(employeeIDPanel);

            cb.addItemListener((e) -> {
                grid.removeAll();
                grid.add(employeeIDPanel);

                Employee selectedEmployee = (Employee) FileProcessing.getUserFromFile((Integer) cb.getSelectedItem());
                JComboBox<Integer> cb2;
                Integer[] listOfShopIDs = new Integer[((CEO) currentUser).getShopList().size()];
                int h = 0;

                List<Integer> listOfIDsToTransferEmployee = new ArrayList<>();
                for (Shop shop : ((CEO) currentUser).getShopList()) {
                    boolean ok = true;
                    for (Employee employee : shop.getEmployeesList()) {
                        if (employee.getUserID() == selectedEmployee.getUserID()) {
                            ok = false;
                        }
                    }
                    if (ok) {
                        listOfIDsToTransferEmployee.add(shop.getShopID());
                    }
                }
                for (Integer shopID : listOfIDsToTransferEmployee) {
                    listOfShopIDs[h] = shopID;
                    h++;
                }
                cb2 = new JComboBox<>(listOfShopIDs);
                JPanel userIdPanel = new JPanel();
                JLabel userIDLabel = new JLabel("Select a shop to transfer employee to: ");
                userIDLabel.setFont(Constants.monospacedSmall);
                userIdPanel.add(userIDLabel);
                userIdPanel.add(cb2);
                grid.add(userIdPanel);

                JButton fireUser = new JButton("Transfer Employee");
                fireUser.setPreferredSize(new Dimension(200, 32));
                fireUser.setFont(Constants.monospacedSmall);
                fireUser.addActionListener((f) -> {
                    ((CEO) currentUser).transferEmployee((Integer) cb.getSelectedItem(), (Integer) cb2.getSelectedItem());

                    this.remove(responseScreen);
                    if (Objects.equals(fromWhere, "employees")) {
                        this.add(handleEmployees());
                    } else if (Objects.equals(fromWhere, "products")) {
                        this.add(handleProducts());
                    } else {
                        //TODO handle shops
                    }
                    this.revalidate();
                    this.repaint();
                });
                grid.add(fireUser);
                grid.revalidate();
                grid.repaint();
            });


            responseScreen.removeAll();
            responseScreen.add(grid);
            responseScreen.add(bar, BorderLayout.NORTH);
            responseScreen.revalidate();
            responseScreen.repaint();
        } else if (response.equals(ResponseType.stockOfShop)) {
            this.responseScreen.setSize(new Dimension(Constants.MAX_WIDTH + 200, Constants.MAX_HEIGHT));
            this.setSize(new Dimension(Constants.MAX_WIDTH + 200, Constants.MAX_HEIGHT));
            this.setTitle("Shops & Stocks - Shop's Stock");

            if (Objects.equals(currentUser.getUserType(), UserType.ceo)) {
                JPanel ceoStockScreen = new JPanel();
                ceoStockScreen.setLayout(new GridLayout(2, 1));
                JComboBox<Integer> cb;
                Integer[] listOfIds = new Integer[((CEO) currentUser).getShopList().size()];
                List<Shop> shopList = ((CEO) currentUser).getShopList();
                int k = 0;
                for (Shop shop : shopList) {
                    listOfIds[k] = shop.getShopID();
                    k++;
                }

                cb = new JComboBox<>(listOfIds);

                JPanel shopIdPanel = new JPanel();
                JLabel selectShopLabel = new JLabel("Select a shop: ");
                selectShopLabel.setFont(Constants.monospacedSmall);
                shopIdPanel.add(selectShopLabel);
                shopIdPanel.add(cb);
                ceoStockScreen.add(shopIdPanel);

                cb.addItemListener((e) -> {
                    ceoStockScreen.removeAll();
                    ceoStockScreen.add(shopIdPanel);
                    Shop shop = FileProcessing.getShopFromFile((Integer) cb.getSelectedItem());
                    List<Product> products = new ArrayList<>();
                    products.addAll(shop.getProductList());
                    int numberOfProducts = products.size();
                    JPanel allEmployees = new JPanel(new GridLayout(numberOfProducts + 1, 5));
                    JLabel titleID = new JLabel("ID");
                    titleID.setFont(Constants.monospacedSmall);
                    allEmployees.add(titleID);
                    JLabel titleRole = new JLabel("Name");
                    titleRole.setFont(Constants.monospacedSmall);
                    allEmployees.add(titleRole);
                    JLabel firstNameTitle = new JLabel("Quantity");
                    firstNameTitle.setFont(Constants.monospacedSmall);
                    allEmployees.add(firstNameTitle);
                    JLabel lastNameTitle = new JLabel("Unit Type");
                    lastNameTitle.setFont(Constants.monospacedSmall);
                    allEmployees.add(lastNameTitle);
                    JLabel addressTitle = new JLabel("Price/Unit");
                    addressTitle.setFont(Constants.monospacedSmall);
                    allEmployees.add(addressTitle);
                    for (Product product : products) {
                        allEmployees.add(new JLabel(String.valueOf(product.getProductId())));
                        allEmployees.add(new JLabel(product.getProductName()));
                        allEmployees.add(new JLabel(String.valueOf(product.getQuantity())));
                        allEmployees.add(new JLabel(product.getUnit()));
                        allEmployees.add(new JLabel(String.valueOf(product.getPricePerUnit())));
                    }
                    ceoStockScreen.add(allEmployees);
                    ceoStockScreen.revalidate();
                    ceoStockScreen.repaint();
                });

                responseScreen.removeAll();
                responseScreen.add(ceoStockScreen);
            } else {
                Shop shop = FileProcessing.getShopFromFile(((Employee) currentUser).getShopID());
                List<Product> products = new ArrayList<>();
                products.addAll(shop.getProductList());
                int numberOfProducts = products.size();
                JPanel allEmployees = new JPanel(new GridLayout(numberOfProducts + 1, 5));
                JLabel titleID = new JLabel("ID");
                titleID.setFont(Constants.monospacedSmall);
                allEmployees.add(titleID);
                JLabel titleRole = new JLabel("Name");
                titleRole.setFont(Constants.monospacedSmall);
                allEmployees.add(titleRole);
                JLabel firstNameTitle = new JLabel("Quantity");
                firstNameTitle.setFont(Constants.monospacedSmall);
                allEmployees.add(firstNameTitle);
                JLabel lastNameTitle = new JLabel("Unit Type");
                lastNameTitle.setFont(Constants.monospacedSmall);
                allEmployees.add(lastNameTitle);
                JLabel addressTitle = new JLabel("Price/Unit");
                addressTitle.setFont(Constants.monospacedSmall);
                allEmployees.add(addressTitle);
                for (Product product : products) {
                    allEmployees.add(new JLabel(String.valueOf(product.getProductId())));
                    allEmployees.add(new JLabel(product.getProductName()));
                    allEmployees.add(new JLabel(String.valueOf(product.getQuantity())));
                    allEmployees.add(new JLabel(product.getUnit()));
                    allEmployees.add(new JLabel(String.valueOf(product.getPricePerUnit())));
                }
                responseScreen.removeAll();
                responseScreen.add(allEmployees);
            }


            responseScreen.add(bar, BorderLayout.NORTH);
            responseScreen.revalidate();
            responseScreen.repaint();
        } else if (response.equals(ResponseType.addItem)) {
            this.setTitle("Shops & Stocks - Modify Stock");

            JTextField productNameField = new JTextField();
            SpinnerNumberModel quantityModel = new SpinnerNumberModel(10, 0, 100000, 1);
            SpinnerNumberModel priceModel = new SpinnerNumberModel(10, 0, 100000, 1);
            JSpinner quantitySpinner = new JSpinner(quantityModel);
            JSpinner priceSpinner = new JSpinner(priceModel);
            JLabel productNameLabel = new JLabel("Product name: ");
            productNameLabel.setFont(Constants.monospacedSmall);
            JLabel quantityLabel = new JLabel("Quantity: ");
            quantityLabel.setFont(Constants.monospacedSmall);
            JLabel priceLabel = new JLabel("Price/Unit: ");
            priceLabel.setFont(Constants.monospacedSmall);
            JLabel unitLabel = new JLabel("Unit Type: ");
            unitLabel.setFont(Constants.monospacedSmall);

            JPanel firstNamePanel = new JPanel();
            firstNamePanel.setSize(new Dimension(Constants.MAX_WIDTH, 40));
            productNameField.setPreferredSize(new Dimension(Constants.MAX_WIDTH / 3, 24));
            firstNamePanel.add(productNameLabel);
            firstNamePanel.add(productNameField);

            JPanel lastNamePanel = new JPanel();
            lastNamePanel.setSize(new Dimension(Constants.MAX_WIDTH, 40));
            lastNamePanel.add(quantityLabel);
            lastNamePanel.add(quantitySpinner);

            JPanel addressPanel = new JPanel();
            addressPanel.setSize(new Dimension(Constants.MAX_WIDTH, 40));
            addressPanel.add(unitLabel);

            String[] unitTypes = {UnitType.bucata, UnitType.litru, UnitType.kilogram};
            JComboBox<String> cb = new JComboBox<>(unitTypes);
            addressPanel.add(cb);

            JPanel phoneNumberPanel = new JPanel();
            phoneNumberPanel.setSize(new Dimension(Constants.MAX_WIDTH, 40));
            phoneNumberPanel.add(priceLabel);
            phoneNumberPanel.add(priceSpinner);

            JButton addUser = new JButton("Add Product To Stock");
            addUser.setSize(new Dimension(100, 24));
            addUser.setFont(Constants.monospacedRegular);


            JPanel grid = new JPanel();
            grid.setLayout(new GridLayout(7, 1));
            grid.add(firstNamePanel);
            grid.add(lastNamePanel);
            grid.add(addressPanel);
            grid.add(phoneNumberPanel);

            JComboBox<Integer> cb2;
            if (Objects.equals(currentUser.getUserType(), UserType.ceo)) {
                Integer[] listOfIds = new Integer[((CEO) currentUser).getShopList().size()];
                List<Shop> shopList = ((CEO) currentUser).getShopList();
                int k = 0;
                for (Shop shop : shopList) {
                    listOfIds[k] = shop.getShopID();
                    k++;
                }

                cb2 = new JComboBox<>(listOfIds);

                JPanel shopIdPanel = new JPanel();
                JLabel selectShopLabel = new JLabel("Select an available shop: ");
                selectShopLabel.setFont(Constants.monospacedSmall);
                shopIdPanel.add(selectShopLabel);
                shopIdPanel.add(cb2);

                grid.add(shopIdPanel);
            } else {
                cb2 = null;
            }

            grid.add(addUser);

            addUser.addActionListener((e) -> {

                if (Objects.equals(currentUser.getUserType(), UserType.ceo)) {
                    ((CEO) currentUser).addItem(productNameField.getText(), Double.parseDouble(String.valueOf(quantitySpinner.getValue())), (String) cb.getSelectedItem(), Double.parseDouble(String.valueOf(priceSpinner.getValue())), (Integer) cb2.getSelectedItem());
                } else {
                    ((Employee) currentUser).addItem(productNameField.getText(), Double.parseDouble(String.valueOf(quantitySpinner.getValue())), (String) cb.getSelectedItem(), Double.parseDouble(String.valueOf(priceSpinner.getValue())));


                }

                this.remove(responseScreen);
                if (Objects.equals(fromWhere, "employees")) {
                    this.add(handleEmployees());
                } else if (Objects.equals(fromWhere, "products")) {
                    this.add(handleProducts());
                } else {
                    //TODO handle shops
                }
                this.revalidate();
                this.repaint();
            });

            responseScreen.removeAll();
            responseScreen.add(grid);
            responseScreen.add(bar, BorderLayout.NORTH);
            responseScreen.revalidate();
            responseScreen.repaint();
        } else if (response.equals(ResponseType.addStockItem)) {
            this.setTitle("Shops & Stocks - Modify Quantity Of Product");
            SpinnerNumberModel quantityModel = new SpinnerNumberModel(10, 0, 100000, 1);
            JSpinner quantitySpinner = new JSpinner(quantityModel);
            JLabel quantityLabel = new JLabel("Quantity: ");
            quantityLabel.setFont(Constants.monospacedSmall);
            JLabel unitLabel = new JLabel("Select the ID of product:");
            unitLabel.setFont(Constants.monospacedSmall);

            JPanel lastNamePanel = new JPanel();
            lastNamePanel.setSize(new Dimension(Constants.MAX_WIDTH, 40));
            String[] typeOfOperation = {TransactionType.adding, TransactionType.substracing};
            JComboBox<String> cb = new JComboBox<>(typeOfOperation);
            lastNamePanel.add(quantityLabel);
            lastNamePanel.add(cb);
            lastNamePanel.add(quantitySpinner);

            JPanel addressPanel = new JPanel();
            addressPanel.setSize(new Dimension(Constants.MAX_WIDTH, 40));
            addressPanel.add(unitLabel);

            JButton addUser = new JButton("Modify Quantity Of Product");
            addUser.setSize(new Dimension(100, 24));
            addUser.setFont(Constants.monospacedRegular);


            JPanel grid = new JPanel();
            grid.setLayout(new GridLayout(7, 1));

            JComboBox<Integer> cb2;
            if (Objects.equals(currentUser.getUserType(), UserType.ceo)) {
                Integer[] listOfIds = new Integer[((CEO) currentUser).getShopList().size()];
                List<Shop> shopList = ((CEO) currentUser).getShopList();
                int k = 0;
                for (Shop shop : shopList) {
                    listOfIds[k] = shop.getShopID();
                    k++;
                }

                cb2 = new JComboBox<>(listOfIds);

                JPanel shopIdPanel = new JPanel();
                JLabel selectShopLabel = new JLabel("Select an available shop: ");
                selectShopLabel.setFont(Constants.monospacedSmall);
                shopIdPanel.add(selectShopLabel);
                shopIdPanel.add(cb2);

                cb2.addActionListener((e) -> {
                    Shop shop = FileProcessing.getShopFromFile((Integer) cb2.getSelectedItem());
                    Integer[] idsOfProducts = new Integer[shop.getProductList().size() + 1];
                    int l = 0;
                    for (Product product : shop.getProductList()) {
                        idsOfProducts[l] = product.getProductId();
                        l++;
                    }
                    JComboBox<Integer> cb3 = new JComboBox<>(idsOfProducts);

                    addUser.addActionListener((f) -> {

                        ((CEO) currentUser).modifyItem((Integer) cb3.getSelectedItem(), (String) cb.getSelectedItem(), Double.valueOf(String.valueOf(quantitySpinner.getValue())), (Integer) cb2.getSelectedItem());
                        this.remove(responseScreen);
                        if (Objects.equals(fromWhere, "employees")) {
                            this.add(handleEmployees());
                        } else if (Objects.equals(fromWhere, "products")) {
                            this.add(handleProducts());
                        } else {
                            //TODO handle shops
                        }
                        this.revalidate();
                        this.repaint();
                    });

                    addressPanel.removeAll();
                    addressPanel.add(unitLabel);
                    addressPanel.add(cb3);
                    addressPanel.revalidate();
                    addressPanel.repaint();

                });


                grid.add(shopIdPanel);
            } else {
                Shop shop = FileProcessing.getShopFromFile(((Employee) currentUser).getShopID());
                Integer[] idsOfProducts = new Integer[shop.getProductList().size() + 1];
                int l = 0;
                for (Product product : shop.getProductList()) {
                    idsOfProducts[l] = product.getProductId();
                    l++;
                }
                JComboBox<Integer> cb3 = new JComboBox<>(idsOfProducts);

                addressPanel.add(cb3);

                addUser.addActionListener((f) -> {

                    ((Employee) currentUser).modifyItem((Integer) cb3.getSelectedItem(), (String) cb.getSelectedItem(), Double.valueOf(String.valueOf(quantitySpinner.getValue())));
                    this.remove(responseScreen);
                    if (Objects.equals(fromWhere, "employees")) {
                        this.add(handleEmployees());
                    } else if (Objects.equals(fromWhere, "products")) {
                        this.add(handleProducts());
                    } else {
                        //TODO handle shops
                    }
                    this.revalidate();
                    this.repaint();
                });

                cb2 = null;
            }

            grid.add(addressPanel);
            grid.add(lastNamePanel);
            grid.add(addUser);


            responseScreen.removeAll();
            responseScreen.add(grid);
            responseScreen.add(bar, BorderLayout.NORTH);
            responseScreen.revalidate();
            responseScreen.repaint();
        }
        this.add(responseScreen);
    }
}
