package blackhole;
import blackhole.view.MainWindow;

public class Blackhole {
    public static void main (String[] args) {
        MainWindow mainWindow = new MainWindow(new int[] {5,7,9});
        mainWindow.setVisible(true);
    }
}
