package ui;

import java.util.ArrayList;
import utils.Validator;

public class Menu {
    // Danh sách menu
    private ArrayList<String> optList;
    // Menu title
    private String title;

    public Menu(String title) {
        this.title = title;
        optList = new ArrayList<>();
    }

//  thêm lựa chọn cho menu
    public void addNewOpt(String newOption) {
        optList.add(newOption);
    }

//  in ra menu kèm số thứ tự
    public void showMenu() {
        int count = 1;
        System.out.println("________" + title + "_________");
        for (String op : optList) {
            System.out.println(count + ". " + op);
            count++;
        }
        System.out.println("0. Quit");
    }

    public int getChoice() {
        int choice;
        do {
            choice = Validator.getAnInteger("Please enter your choice", "It must be a number");
            if (choice < 0 || choice > optList.size()) {
                System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice < 0 || choice > optList.size());
        return choice;
    }
}
