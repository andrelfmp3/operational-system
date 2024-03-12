import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
        String appName = teclado.next();

        try {
            Process process = new ProcessBuilder(appName).start();
            long pid = process.pid();
            System.out.println("PID do processo " + appName + ": " + pid);
        } catch (IOException e) {
            e.printStackTrace();
        }

        teclado.close();

    }
}
