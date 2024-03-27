/*

ANDRÉ LYRA FERNANDES - bv303139x
VICTORIA CAROLINA FERREIRA DA SILVA - bv3033848

OBS: todas as classes foram feitas em um único arquivo .java visando correção da atividade.

*/

import java.util.Random; 

// Classe para DEPÓSITO, sem Thread (dica do exercício)
class Deposito {

    boolean depositoEmUso = false; // Variável mostra se o depósito está em uso, parte-se do livre (false+)

    // Método que usa o depósito
        //"synchronized", para que seja executado apenas uma Thread por vez
    synchronized void usar() throws InterruptedException {
        while (depositoEmUso) {
            wait(); // Aguarda até que o depósito esteja livre
        }

        depositoEmUso = true; // Marca o depósito como em uso (dentro da função "usar()")

    }

    // Método para liberar o depósito para uso
    synchronized void liberar() {
        depositoEmUso = false; // Marca o depósito como livre
        notifyAll(); // Notifica as threads que estão aguardando
    }
}

// Classe que representa o fornecedor, extends Thread (dica do exercício)
class Fornecedor extends Thread { // REVER ANTES DE ENVIAR

    Deposito deposito;

    // Construtor do fornecedor
    public Fornecedor(Deposito deposito) {
        this.deposito = deposito;
    }

    // Método que será executado quando a thread do fornecedor for iniciada
    @Override // Sobrecarga do método "run()"
    public void run() {

        Random aleatorio = new Random();

        while (true) {
            try {
                Thread.sleep(aleatorio.nextInt(9000) + 1000); // Tempo de produção aleatório entre 1 e 10 segundos
                System.out.println("Fornecedor finalizou a produção para loja");
                deposito.usar(); // Usa o depósito
                System.out.println("Fornecedor entrou para desembarcar o item no depósito");
                Thread.sleep(5000); // Tempo de espera no depósito (5 segundos)
                System.out.println("Fornecedor finalizou o desembarque do item no depósito");
                deposito.liberar(); // Libera o depósito
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Erro de tratamento de excessão");
            }
        }
    }
}

// Classe que representa a loja, extends Thread (dica do exercício)
class Loja extends Thread {

    int numero;
    Deposito deposito;

    // Construtor da loja
    public Loja(int numero, Deposito deposito) {
        this.numero = numero;
        this.deposito = deposito;
    }

    // Método que será executado quando a thread da loja for iniciada
    @Override
    public void run() {

        Random aleatorio = new Random();

        while (true) {
            try {
                Thread.sleep(aleatorio.nextInt(15000) + 5000); // Tempo de solicitação aleatório entre 5 e 20 segundos
                System.out.println("Solicitacao da Loja " + numero + " para fornecedor");
                deposito.usar(); // Usa o depósito
                System.out.println("Loja " + numero + " carregando item no depósito");
                Thread.sleep(5000); // Tempo de espera no depósito
                System.out.println("Loja " + numero + " finalizou a carga no depósito");
                deposito.liberar(); // Libera o depósito
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Erro de tratamento de excessão");
            }
        }
    }
}

// Classe principal que contém o método main
public class Main {
    public static void main(String[] args) {

        //instancia deposito, fornecedor, e ljas
        Deposito deposito = new Deposito(); // Instancia o depósito
        Fornecedor fornecedor = new Fornecedor(deposito); // Instancia o fornecedor
        Loja loja1 = new Loja(1, deposito); // Instancia a primeira loja
        Loja loja2 = new Loja(2, deposito); // Instancia a segunda loja

        // Inicia as threads do fornecedor e das lojas
        fornecedor.start();
        loja1.start();
        loja2.start();
    }
}
