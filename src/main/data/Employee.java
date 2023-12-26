package main.data;

import main.files.FileProcessing;
import main.types.TransactionType;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Employee extends Person {
    protected int shopID;
    protected LocalDate dateOfEmployment;

    public Employee() {
        super();
        shopID = -1;
        dateOfEmployment = null;
    }

    public Employee(int userID, String userType, String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String address, int shopID, LocalDate dateOfEmployment) {
        super(userID, userType, firstName, lastName, dateOfBirth, phoneNumber, address);
        this.shopID = shopID;
        this.dateOfEmployment = dateOfEmployment;
    }

    public int getShopID() {
        return shopID;
    }
    public void setShopID(int shopID) {
        this.shopID = shopID;
    }
    public LocalDate getDateOfEmployment() {
        return dateOfEmployment;
    }

    /**
     * Add a new item to the shop's list and to the file.
     * @param productName The name of the product.
     * @param quantity Quantity received at the insertion of the product.
     * @param unit The unit of the product is added.
     * @param pricePerUnit The price per unit of the product added by an employee.
     */
    public void addItem(String productName, double quantity, String unit, double pricePerUnit) {
        int lastIdOfProduct = FileProcessing.getLastIDUsed("products");
        Product newProduct = new Product(lastIdOfProduct + 1, productName, quantity, unit, pricePerUnit);
        FileProcessing.addNewProductToFile(newProduct);

        Shop shop = FileProcessing.getShopFromFile(this.shopID);
        List<Product> listOfShopsProducts = shop.getProductList();
        listOfShopsProducts.add(newProduct);
        shop.setProductList(listOfShopsProducts);

        FileProcessing.updateShopInFile(shop);
    }

    /**
     * Removes the product from file and from the shop's product list.
     * @param productId The id of the product the employee wants to remove from the shop's stock.
     */
    public void removeItem(int productId) {
        Product productToRemove = FileProcessing.getProductFromFile(productId);
        Shop shop = FileProcessing.getShopFromFile(this.shopID);
        List<Product> updatedProductList = shop.getProductList();
        updatedProductList = updatedProductList.stream().filter(product -> product.getProductId() != productToRemove.getProductId()).toList();
        shop.setProductList(updatedProductList);

        FileProcessing.updateShopInFile(shop);
        FileProcessing.deleteProductFromFile(productToRemove.getProductId());
    }

    /**
     * Modifies a specified item accordingly.
     * @param productId The id of the product the employee wants to modify its quantity
     * @param transactionType The type of the transaction that is being made for the product specified by the id. The transaction could be a subtract or addition.
     * @param quantity The quantity the modification is being made.
     */
    public void modifyItem(int productId, String transactionType, double quantity) {
        Product productToUpdate = FileProcessing.getProductFromFile(productId);
        double productUpdatedQuantity = productToUpdate.getQuantity();
        productUpdatedQuantity += Objects.equals(transactionType, TransactionType.adding) ? quantity : (quantity * (-1));
        productToUpdate.setQuantity(productUpdatedQuantity);
        FileProcessing.updateProductInFile(productToUpdate);

        Shop shop = FileProcessing.getShopFromFile(this.shopID);
        List<Product> listOfProducts = shop.getProductList();
        for (Product product : listOfProducts) {
            if(product.getProductId() == productToUpdate.getProductId()){
                product = productToUpdate;
            }
        }
        shop.setProductList(listOfProducts);

        FileProcessing.updateShopInFile(shop);
    }
}
