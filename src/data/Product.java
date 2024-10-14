package data;

public class Product {
    
    private String id;
    private String name;
    private String brandId;
    private String categoryId;
    private int modelYear;
    private double listPrice;

    public Product(String id, String name, String brandId, String categoryId, int modelYear, double listPrice) {
        this.id = id;
        this.name = name;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.modelYear = modelYear;
        this.listPrice = listPrice;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrandId() {
        return brandId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public int getModelYear() {
        return modelYear;
    }

    public double getListPrice() {
        return listPrice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + brandId + ", " + categoryId + ", " + modelYear + ", " + listPrice;
    }
}
