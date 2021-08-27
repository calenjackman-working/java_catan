package ui;

import java.util.List;
import java.util.Scanner;

public class CLInterface {
    public CLInterface() {
        super();
    }

    public String getMCAnswer(List<String> options) {
        Scanner scanner = MyScanner.getInstance();
        String returnVar = "";
        Boolean validInput = false;
        do {
            returnVar = scanner.nextLine().strip();
            if (!(options.contains(returnVar))) {
                System.out.println("Input invalid, please try again");
                continue;
            }
            validInput = true;
        } while (!(validInput));

        return returnVar;
    }
}
