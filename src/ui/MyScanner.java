package ui;

import java.util.Scanner;

public final class MyScanner {
    private static final Scanner scanner = new Scanner(System.in);

    public MyScanner() {
    }

    public static Scanner getInstance() {
        return scanner;
    }
}
