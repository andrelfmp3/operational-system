import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        if (args.length > 0) { //verifica se tem algo no args
            try {
                Process process = new ProcessBuilder(args[0]).start();
                long pid = process.pid();
                System.out.println("PID do processo " + args[0] + ": " + pid);
            } catch (IOException e) { //programa não existe
                System.out.println("Erro");
            }
        } else { //args vazio, retorna processo atual
            long pid = ProcessHandle.current().pid();
            System.out.println("PID do processo atual: " + pid);
        }
    }
}

