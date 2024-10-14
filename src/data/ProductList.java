package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import utils.Validator;

public class ProductList {
    private ArrayList<Product> products;
    private BrandList brandList;
    private CategoryList categoryList;

    public ProductList(BrandList brandList, CategoryList categoryList) {
        this.brandList = brandList;
        this.categoryList = categoryList;
        products = new ArrayList<>();
    }

    public void loadFromFile(String filePath) {
        products.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    String brandId = parts[2].trim();
                    String categoryId = parts[3].trim();
                    int modelYear = Integer.parseInt(parts[4].trim());
                    double listPrice = Double.parseDouble(parts[5].trim());
                    products.add(new Product(id, name, brandId, categoryId, modelYear, listPrice));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading products from file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing product data: " + e.getMessage());
        }
    }

    public void saveToFile(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Product product : products) {
                bw.write(product.toString());
                bw.newLine();
            }
            System.out.println("Products saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving products to file: " + e.getMessage());
        }
    }

    public void addProduct() {
        String id = generateUniqueId();
        String name = Validator.getNonEmptyString("Enter product name: ", "Name cannot be empty");
        String brandId;
        do {
            brandList.displayBrands();
            brandId = Validator.getNonEmptyString("Enter brand ID: ", "Brand ID cannot be empty");
            if (!brandList.exists(brandId)) {
                System.out.println("Brand ID does not exist. Please try again.");
            }
        } while (!brandList.exists(brandId));

        String categoryId;
        do {
            categoryList.displayCategories();
            categoryId = Validator.getNonEmptyString("Enter category ID: ", "Category ID cannot be empty");
            if (!categoryList.exists(categoryId)) {
                System.out.println("Category ID does not exist. Please try again.");
            }
        } while (!categoryList.exists(categoryId));

        int modelYear = Validator.getAnInteger("Enter model year: ", "Invalid year");
        while (modelYear < 1900 || modelYear > Validator.getCurrentYear()) {
            System.out.println("Model year must be between 1900 and " + Validator.getCurrentYear());
            modelYear = Validator.getAnInteger("Enter model year: ", "Invalid year");
        }

        double listPrice = Validator.getADouble("Enter list price: ", "Invalid price");
        while (listPrice <= 0) {
            System.out.println("List price must be a positive number.");
            listPrice = Validator.getADouble("Enter list price: ", "Invalid price");
        }

        Product newProduct = new Product(id, name, brandId, categoryId, modelYear, listPrice);
        products.add(newProduct);
        System.out.println("Product added successfully.");
    }

    private String generateUniqueId() {
        String prefix = "P";
        int maxId = 0;
        for (Product product : products) {
            String numPart = product.getId().replaceAll("[^0-9]", "");
            try {
                int num = Integer.parseInt(numPart);
                if (num > maxId) {
                    maxId = num;
                }
            } catch (NumberFormatException e) {
            }
        }
        return prefix + String.format("%03d", maxId + 1);
    }

    public void searchedProductName() {
        String searchStr = Validator.getNonEmptyString("Enter product name to search: ", "Search string cannot be empty");
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(searchStr.toLowerCase())) {
                result.add(product);
            }
        }
        if (result.isEmpty()) {
            System.out.println("Have no any Product.");
        } else {
            Collections.sort(result, Comparator.comparingInt(Product::getModelYear));
            for (Product product : result) {
                displayProduct(product);
            }
        }
    }

    private Product getProductById(String id) {
        for (Product product : products) {
            if (product.getId().equalsIgnoreCase(id)) {
                return product;
            }
        }
        return null;
    }
    
    public void update() {
        String id = Validator.getNonEmptyString("Enter product ID to update: ", "Product ID cannot be empty");
        Product product = getProductById(id);
        if (product == null) {
            System.out.println("Product does not exist.");
            return;
        }

        System.out.println("Leave input blank to keep the current value.");

        String name = Validator.getString("Enter new product name (" + product.getName() + "): ");
        if (!name.isEmpty()) {
            product.setName(name);
        }

        String brandId = Validator.getString("Enter new brand ID (" + product.getBrandId() + "): ");
        if (!brandId.isEmpty()) {
            if (brandList.exists(brandId)) {
                product.setBrandId(brandId);
            } else {
                System.out.println("Brand ID does not exist. Keeping the old value.");
            }
        }

        String categoryId = Validator.getString("Enter new category ID (" + product.getCategoryId() + "): ");
        if (!categoryId.isEmpty()) {
            if (categoryList.exists(categoryId)) {
                product.setCategoryId(categoryId);
            } else {
                System.out.println("Category ID does not exist. Keeping the old value.");
            }
        }

        String modelYearStr = Validator.getString("Enter new model year (" + product.getModelYear() + "): ");
        if (!modelYearStr.isEmpty()) {
            try {
                int modelYear = Integer.parseInt(modelYearStr);
                if (modelYear >= 1900 && modelYear <= Validator.getCurrentYear()) {
                    product.setModelYear(modelYear);
                } else {
                    System.out.println("Invalid model year. Keeping the old value.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Keeping the old model year.");
            }
        }

        String listPriceStr = Validator.getString("Enter new list price (" + product.getListPrice() + "): ");
        if (!listPriceStr.isEmpty()) {
            try {
                double listPrice = Double.parseDouble(listPriceStr);
                if (listPrice > 0) {
                    product.setListPrice(listPrice);
                } else {
                    System.out.println("List price must be positive. Keeping the old value.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Keeping the old list price.");
            }
        }

        System.out.println("Product updated successfully.");
    }

    public void delete() {
        String id = Validator.getNonEmptyString("Enter product ID to delete: ", "Product ID cannot be empty");
        Product product = getProductById(id);
        if (product == null) {
            System.out.println("Product does not exist.");
            return;
        }

        String confirm = Validator.getNonEmptyString("Are you sure you want to delete this product? (Y/N): ", "Please enter Y or N");
        if (confirm.equalsIgnoreCase("Y")) {
            products.remove(product);
            System.out.println("Product deleted successfully.");
        } else {
            System.out.println("Delete operation cancelled.");
        }
    }

    public void showProductList() {
        if (products.isEmpty()) {
            System.out.println("No products to display.");
            return;
        }
        for (Product product : products) {
            displayProduct(product);
        }
    }

    public void printProductsFromFile(String filePath) {
        loadFromFile(filePath);
        if (products.isEmpty()) {
            System.out.println("No products found in the file.");
            return;
        }

        // Sắp xếp
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                if (Double.compare(p2.getListPrice(), p1.getListPrice()) != 0) {
                    return Double.compare(p2.getListPrice(), p1.getListPrice());
                } else {
                    return p1.getName().compareToIgnoreCase(p2.getName());
                }
            }
        });

        for (Product product : products) {
            displayProductWithNames(product);
        }
    }

    private void displayProduct(Product product) {
        System.out.println(product);
    }

    private void displayProductWithNames(Product product) {
        Brand brand = brandList.getBrandById(product.getBrandId());
        Category category = categoryList.getCategoryById(product.getCategoryId());
        String brandName = (brand != null) ? brand.getName() : "Unknown";
        String categoryName = (category != null) ? category.getName() : "Unknown";
        System.out.println(product.getId() + ", " + product.getName() + ", " + brandName + ", " + categoryName + ", " + product.getModelYear() + ", " + product.getListPrice());
    }

}
