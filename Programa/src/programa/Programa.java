import pilha.Pilha;
import labirinto.Labirinto;

import java.io.*;
import java.nio.file.Files;

/**
    * <h1>Primeiro Projeto de POO</h1> 
    * O Programa encontra a saída de um labirinto a partir do uso de filas e pilhas.
    *
    * @author Caio Petrucci Rosa, Fábio Faúndes e Luís Sabença
*/
public class Programa {

    public static void main (String[] args) {
        char continuar = 'S';
        String respostaDada = "";
        String nomeArquivo = "";

        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

        while(continuar == 'S') {
            System.out.println("Por favor, digite o nome do arquivo a ser lido:");
            
            try {
                nomeArquivo = teclado.readLine();
            } 
            catch(IOException e) {
                e.printStackTrace();
            }
            System.out.println();

            //

            Labirinto labirinto = null;
            try {
                labirinto = new Labirinto(nomeArquivo);
                
                Pilha caminho = labirinto.resolverCaminho();
                System.out.println("Caminho:"+ System.lineSeparator() + caminho);
                System.out.println();
                System.out.println("Labirinto:"+ System.lineSeparator() + labirinto);
                System.out.println();

                if (new File("C:/Temp/").exists()) {
                    System.out.println("Deseja gravar a solucao do labirinto em um arquivo na pasta 'C:/Temp/'?(S/N)");
                    respostaDada = teclado.readLine().toUpperCase();
                    if (respostaDada.length() == 1)
                        continuar = respostaDada.charAt(0);
                    else
                        continuar = 'N';

                    if (continuar == 'S') {
                        System.out.println();
                        System.out.println("Digite o nome do arquivo texto:");
                        nomeArquivo = teclado.readLine();
                        labirinto.criarArquivoComSolucao(nomeArquivo);
                    }
                }
            } 
            catch(FileNotFoundException e) {
                System.out.println("Erro: O arquivo "+ nomeArquivo +" nao foi encontrado!");
            } 
            catch(IOException e) {
                e.printStackTrace();
                System.out.println("Erro: Dados invalidos");
            }
            catch(Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
            System.out.println();

            //

            System.out.println("Deseja continuar?(S/N)"); 
            try {
                respostaDada = teclado.readLine().toUpperCase();
                System.out.println();

                if (respostaDada.length() == 1)
                    continuar = respostaDada.charAt(0);
                else
                    continuar = 'N';
            } 
            catch(IOException e) {
                e.printStackTrace();
            }
        }            
    }
}