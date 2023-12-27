package main.data;

import main.types.UnitType;

public class Product {
    private int productId;
    private String productName;
    private double quantity;
    private String unit;
    private double pricePerUnit;

    public Product() {
        this.productId=-1;
        this.productName = "";
        this.quantity = 0.0;
        this.unit = UnitType.necunoscut;
        this.pricePerUnit = 0;
    }

    public Product(int id, String productName, double quantity, String unit, double pricePerUnit) {
        this.productId = id;
        this.productName = productName;
        this.quantity = quantity;
        this.unit = unit;
        this.pricePerUnit = pricePerUnit;
    }

    public int getProductId() {
        return productId;
    }
    public String getProductName() {
        return productName;
    }

    public String getUnit() {
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

    @Override
    public String toString(){
        return productId+","+productName+","+quantity+","+unit+","+pricePerUnit+"\n";
    }

}
