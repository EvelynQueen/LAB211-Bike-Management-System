package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CategoryList {
    private ArrayList<Category> categories;

    public CategoryList() {
        categories = new ArrayList<>();
    }

    public void loadFromFile(String filePath) {
        categories.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    categories.add(new Category(id, name));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading categories from file: " + e.getMessage());
        }
    }

    public boolean exists(String categoryId) {
        for (Category category : categories) {
            if (category.getId().equalsIgnoreCase(categoryId)) {
                return true;
            }
        }
        return false;
    }

    public Category getCategoryById(String categoryId) {
        for (Category category : categories) {
            if (category.getId().equalsIgnoreCase(categoryId)) {
                return category;
            }
        }
        return null;
    }

    public void displayCategories() {
        for (Category category : categories) {
            System.out.println(category);
        }
    }
}
