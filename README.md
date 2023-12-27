# Shops & Stock 

> #### Managing the stock, shops and employees was never easier.

The below classes are accessible by the user, meaning that the user can log in with one of the below roles. The other classes used in association relation by these classes are not described here. 

> ### Employee
> - `addItem` = adds a new item to the hired shop
> - `removeItem` = removes an item from the hired shop
> - `modifyItem` = modify the stock of the item by subtracting/adding to the quantity of the item


> ### Shop Manager
> - `hireEmployee & fireEmployee` = hire or fire an employee from a shop
> 
> and all the **Employee** methods

> ### CEO
> - `openShop` = opens a shop with default values
> - `closeShop` = closes a shop specified by an id
> - `transferEmployee` = transfers an employee to a specified shop
> - `hireManager` & `fireManager` = hire or fire a manager 
> 
> and all the **ShopManager** and **Employee** methods

The data used for the application is held in four files located in `files` folder:
- `curent_user.txt` = holds the id of the current user
- `users.csv` = holds all the user of the app
- `shops.csv` = holds all the shops of the app
- `products.csv` = hold all the products of the app 
