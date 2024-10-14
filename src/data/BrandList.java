package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BrandList {
    private ArrayList<Brand> brands;

    public BrandList() {
        brands = new ArrayList<>();
    }

    public void loadFromFile(String filePath) {
        brands.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    String origin = parts[2].trim();
                    brands.add(new Brand(id, name, origin));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading brands from file: " + e.getMessage());
        }
    }

    public boolean exists(String brandId) {
        for (Brand brand : brands) {
            if (brand.getId().equalsIgnoreCase(brandId)) {
                return true;
            }
        }
        return false;
    }

    public Brand getBrandById(String brandId) {
        for (Brand brand : brands) {
            if (brand.getId().equalsIgnoreCase(brandId)) {
                return brand;
            }
        }
        return null;
    }

    public void displayBrands() {
        for (Brand brand : brands) {
            System.out.println(brand);
        }
    }
}
