package main.classes;

import main.enums.Unit;

public class Product {
    private String productName;
    private double quantity;
    private Unit unit;
    private double pricePerUnit;

    public Product(){
        this.productName = "";
        this.quantity = 0.0;
        this.unit = Unit.necunoscut;
        this.pricePerUnit=0;
    }

    public Product(String productName, double quantity, Unit unit, double pricePerUnit){
        this.productName = productName;
        this.quantity = quantity;
        this.unit = unit;
        this.pricePerUnit = pricePerUnit;
    }

    public String getProductName() {
        return productName;
    }
    public Unit getUnit() {
        return unit;
    }
    public double getQuantity() {
        return quantity;
    }
    public double getPricePerUnit() {
        return pricePerUnit;
    }


    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

}
