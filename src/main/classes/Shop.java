package main.classes;

import java.util.List;

public class Shop {
    private int shopID;
    private int ownerID;
    private int shopManagerID;
    private List<Employee> employeesList;
    private List<Product> productList;
//    private List<> stockChanges;

    public Shop(){
        this.shopID=-1;
        this.ownerID=-1;
        this.shopManagerID=-1;
        this.employeesList=null;
        this.productList=null;
    }
    public Shop(int shopID, int ownerID, int shopManagerID, List<Employee> employeesList, List<Product> productList){
        this.shopID=shopID;
        this.ownerID=ownerID;
        this.shopManagerID=shopManagerID;
        this.employeesList=employeesList;
        this.productList=productList;
    }

    public int getOwnerID() {
        return ownerID;
    }
    public int getShopID() {
        return shopID;
    }
    public int getShopManagerID() {
        return shopManagerID;
    }
    public List<Employee> getEmployeesList() {
        return employeesList;
    }
    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
    public void setEmployeesList(List<Employee> employeesList) {
        this.employeesList = employeesList;
    }
    public void setShopManagerID(int shopManagerID) {
        this.shopManagerID = shopManagerID;
    }
}
