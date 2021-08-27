package ui;

public class Animate implements Runnable {
    private CatanBoard catanBoard;

    public Animate(CatanBoard cb) {
        this.catanBoard = cb;
    }

    @Override
    public void run() {
        while (true) {
            catanBoard.update();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
