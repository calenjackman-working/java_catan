package ui;

public class Catan {
	public Catan() {
		super();
	}

	public static void main(String[] args) {
		CatanWindow catanWindow = new CatanWindow();
		catanWindow.setDefaultCloseOperation(CatanWindow.EXIT_ON_CLOSE);
		catanWindow.setVisible(true);
	}
}
