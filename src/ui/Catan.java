package ui;

public class Catan {
    public Catan() {
    }

    public static void main(String[] args) {
        CatanWindow catanWindow = new CatanWindow();
        catanWindow.setDefaultCloseOperation(CatanWindow.EXIT_ON_CLOSE);
        catanWindow.setVisible(true);
        catanWindow.getCatanBoard().getGame().play(catanWindow);
    }
}