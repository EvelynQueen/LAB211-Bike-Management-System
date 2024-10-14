package utils;

import java.util.Scanner;
import java.time.Year;

public class Validator {
    private static Scanner scanner = new Scanner(System.in);
//  Ép người dùng nhập vào 1 số nguyên chuẩn
    public static int getAnInteger(String inpMSG, String errorMsg) {
        int result;
        while (true) {
            System.out.print(inpMSG + ": ");
            try {
                result = Integer.parseInt(scanner.nextLine().trim());
                return result;
            } catch (NumberFormatException e) {
                System.out.println(errorMsg);
            }
        }
    }
//  Ép người dùng nhập vào 1 số thực chuẩn
    public static double getADouble(String inpMSG, String errorMsg) {
        double result;
        while (true) {
            System.out.print(inpMSG + ": ");
            try {
                result = Double.parseDouble(scanner.nextLine().trim());
                return result;
            } catch (NumberFormatException e) {
                System.out.println(errorMsg);
            }
        }
    }
//  Ép người dùng nhập vòa 1 chuỗi không rỗng
    public static String getNonEmptyString(String inpMSG, String errorMsg) {
        String input;
        while (true) {
            System.out.print(inpMSG + ": ");
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println(errorMsg);
        }
    }
//  Nhận chuỗi từ người dùng
    public static String getString(String inpMSG) {
        System.out.print(inpMSG + ": ");
        return scanner.nextLine().trim();
    }
//  Lấy năm hiện tại 
    public static int getCurrentYear() {
        return Year.now().getValue();
    }
}
