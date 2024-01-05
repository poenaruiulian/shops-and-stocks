# Shops & Stock 
#### Managing the stock, shops and employees was never easier.

This app provides access to three types of employees: CEO, Shop Manager and Employee. Every type of user has different options that he can select from the menu provided in the console. For the beta version of the app, the user will navigate through it and do its business using a console interface, later to be changed to a more intuitive graphical one.

When the app starts, if no ID can be found in the file `current_user.txt`, the user will be asked to log in using its credentials:
<img width="207" alt="login" src="https://github.com/poenaruiulian/shops-and-stocks/assets/54375582/800a7e8a-5739-40fb-b687-a229a60ba0bd">

After the login, depending on what type of user he is, the app will display a specific menu. Each menu has different options presented below:

<img width="148" alt="ceo_menu" src="https://github.com/poenaruiulian/shops-and-stocks/assets/54375582/f96e23e6-4fbf-4699-b1c1-00085bb75d83">
<img width="156" alt="managers_menu" src="https://github.com/poenaruiulian/shops-and-stocks/assets/54375582/c34cc917-ee58-470e-b02b-3077d6155569">
<img width="146" alt="employee_menu" src="https://github.com/poenaruiulian/shops-and-stocks/assets/54375582/470472b9-d064-4999-aea5-c259d45d3e8a">

The CEO will have the option to modify the shops he owns, as well as manage the employees and products of his shops. A shop manager can manage the employees of the shop he is hired to manage, in comparison with the CEO who will need to choose which shop he is making modifications to. Also, a shop manager can modify the shop's products. Last but not least, an employee can access the products section, as well as the CEO and Shop Manager, where he can modify the shop's stock by changing the quantities of the products (when selling for example) and also add or remove products from the shop.

As mentioned above, the CEO and Shop Manager have the options to modify the employee's situation:

<img width="236" alt="ceo_emp_menu" src="https://github.com/poenaruiulian/shops-and-stocks/assets/54375582/abfab08e-34e5-4d1c-83cb-8f5237fdc618">
<img width="192" alt="ceo_shop_menu" src="https://github.com/poenaruiulian/shops-and-stocks/assets/54375582/a87f634b-a68d-4873-a0e2-675c0b3865cb">

The CEO has the option to hire or fire a manager and also transfer employees to another shop. 

All the users have access to the products menu:

<img width="355" alt="products_menu" src="https://github.com/poenaruiulian/shops-and-stocks/assets/54375582/c09b1325-69bc-4d60-b4d2-c673a646c46a">

The shop menu mentioned above, to which only the CEO has access looks like this:

<img width="192" alt="ceo_shop_menu" src="https://github.com/poenaruiulian/shops-and-stocks/assets/54375582/baddb4ff-b3b3-43c4-8719-fe7361439663">


The data used for the application is held in four files located in the `files` folder: `users.csv`, `shops.csv`, `products.csv`. The simulation of the **CRUD** operations is present in the `FileProccessing.java` file. All the classes are being stored in the `data` folder.

This project showcases the ability to work with classes, I/O operations, and Java programming language overall. For the final version I will improve the quality of the code by refactoring things that could be done in a better manner, adding a more intuitive interface using Java Swing and fixing various bugs I have found or will be found in the testing period before the presentation.

P.S. The official Java documentation, Java Doc, is being stored in the `javadoc` folder.
