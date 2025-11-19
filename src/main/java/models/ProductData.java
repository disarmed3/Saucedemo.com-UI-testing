package models;

import java.util.Objects;

public class ProductData {
    private String name;
    private String price;

    public ProductData(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {return name;}
    public String getPrice() {return price;}

    @Override
    public boolean equals (Object o) {
        if (this ==o) return true;
        if (o == null || getClass() !=o.getClass()) return false;
        ProductData that = (ProductData) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(price, that.price);
    }
    @Override
    public String toString() {
        return "Product{name='"+name+"', price'"+price+"'}";
    }






}
