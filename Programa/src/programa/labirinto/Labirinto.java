package labirinto;

import coordenada.Coordenada;
import pilha.Pilha;
import fila.Fila;
import java.io.*;

/**
    * A classe Labirinto possui métodos de como resolver o labirinto.
	*	
    * @author Caio Petrucci Rosa, Fábio Faúndes e Luís Sabença
*/
public class Labirinto implements Cloneable {
	protected char[][] matriz;
	protected Coordenada entrada;
	protected Coordenada saida;
	protected Coordenada atual;
	protected int linhas;
	protected int colunas;

	/**
		* O construtor da classe carrega os dados do labirinto a partir de um arquivo texto,
		* carregando os dados em uma matriz, e guardando as coordenadas da entrada e da saída
		* do labirinto.
		*
		* @param nomeArquivo É uma string que indicará o diretório do arquivo texto que possui o labirinto
		*
		* @throws FileNotFoundException O construtor lança esta exceção caso o arquivo não seja encontrado pelo objeto.
		* @throws IOException O construtor lança esta exceção caso ocorra algum erro na leitura do arquivo, por exemplo, caso os dados não estejam adequadamente formatados
		* @throws Exception O construtor lança esta exceção caso um arquivo que não existe seja passado como parâmetro, caso o números de linhas e colunas não sejam válidos, ou caso o números de entradas e saídas não sejam válidos.
	*/
	public Labirinto(String nomeArquivo) throws Exception {
		if (nomeArquivo == null || nomeArquivo == "")
			throw new Exception("Nome do arquivo inexistente");
        
        BufferedReader arquivo = new BufferedReader(new FileReader(nomeArquivo));
        
		this.preencherMatriz(arquivo);
		this.acharEntradasESaidas();
        
        this.atual = (Coordenada)this.entrada.clone();
	} 

	protected void preencherMatriz(BufferedReader arquivo) throws Exception {
        if (arquivo == null)
            throw new Exception("Arquivo nao existe!");
        
		this.linhas = Integer.parseInt(arquivo.readLine());
		this.colunas = Integer.parseInt(arquivo.readLine());

		this.matriz = new char[this.linhas][this.colunas];

		for (int i = 0; i < this.linhas; i++) {
			if (arquivo.ready()) {
				String linha = arquivo.readLine().trim();
				for (int j = 0; j < this.colunas; j++)
					this.matriz[i][j] = linha.charAt(j);
			}
		}
		arquivo.close();
                
                if ((this.linhas < 1 || this.colunas < 1) || (this.linhas < 2 && this.colunas < 2))
                    throw new Exception("Numeros de linhas e colunas invalidos");
	}

	protected void acharEntradasESaidas() throws Exception {
		int qtsE = 0;
		int qtsS = 0;

		this.entrada = null;
		this.saida = null;

                try {
                    for(int i = 0; i < this.linhas; i++) {
                        if(this.matriz[i][0] == 'E') {
                            this.entrada = new Coordenada(i,0);
                            qtsE++;
                        } else if (this.matriz[i][0] == 'S') {
                            this.saida = new Coordenada(i,0);
                            qtsS++;
                        }                 
                    }

                    for(int i = 0; i < this.linhas; i++) {
                        if(this.matriz[i][this.colunas-1] == 'E') {
                            this.entrada = new Coordenada(i,this.colunas-1);
                            qtsE++;
                        } else if (this.matriz[i][this.colunas-1] == 'S') {
                            this.saida = new Coordenada(i,this.colunas-1);
                            qtsS++;
                        }  
                    }

                    for(int i = 0; i < this.colunas; i++) {
                        if(this.matriz[0][i] == 'E') {
                            this.entrada = new Coordenada(0,i);
                            qtsE++;
                        } else if (this.matriz[0][i] == 'S') {
                            qtsS++;
                        }  
                    }

                    for(int i = 0; i < this.colunas; i++) {
                        if(this.matriz[this.linhas-1][i] == 'E') {
                            this.entrada = new Coordenada(this.linhas-1,i);
                            qtsE++;
                        } else if (this.matriz[this.linhas-1][i] == 'S') {
                            this.saida = new Coordenada(this.linhas-1,i);
                            qtsS++;
                        }  
                    }
                } 
                catch(Exception e) {}

		if (qtsE != 1 && qtsS !=1) 
			throw new Exception("Numeros de saidas e entradas invalidos");
	}

	/**
		* O método resolverCaminho() acha um caminho até a saída do labirinto e retorna uma pilha
		* contendo o caminho da entrada até a saída do labirinto em forma de coordenadas.
		*
		* @return O método retorna uma pilha contendo objetos da classe Coordenada, indicando o caminho
		* da entrada até a saída do labirinto.
		*
		* @throws Exception O método lança uma exceção caso o labirinto não possa ser resolvido, ou seja, não possua uma saída.
	*/
	public Pilha resolverCaminho() throws Exception{
		int qtsCoord = this.linhas*this.colunas;
                
                Pilha<Coordenada> caminho = null;
                Pilha<Fila<Coordenada>> possibilidades = null;
                try {
                    caminho = new Pilha(qtsCoord);
                    possibilidades = new Pilha(qtsCoord);
                } catch(Exception e) {}

		boolean modoProgressivo = true;
		boolean achouSaida = false;

		Resolva:while(!achouSaida) {
                    Fila<Coordenada> fila = null;
                    try {
			fila = new Fila(3);
                    } catch(Exception e) {}

		    if(modoProgressivo) {
		        Coordenada novaCoordenada = null;
                
                //Usamos um try, em cada coordenada possivel, para evitar indexacao invalida na matriz
		        try {
		            if(this.matriz[this.atual.getX() + 1][this.atual.getY()] == ' ' || this.matriz[this.atual.getX() + 1][this.atual.getY()] == 'S') {
		            	novaCoordenada = new Coordenada((this.atual.getX() + 1),this.atual.getY());
		                fila.enfileirar(novaCoordenada);
		            }
		        } catch(Exception e) { e.printStackTrace();} //Nao tratamos pois so seguiremos em frente

                //
                try {
		            if(this.matriz[this.atual.getX() - 1][this.atual.getY()] == ' ' || this.matriz[this.atual.getX() - 1][this.atual.getY()] == 'S') {
		            	novaCoordenada = new Coordenada((this.atual.getX() - 1),this.atual.getY());
		                fila.enfileirar(novaCoordenada);
		            }
		        } catch(Exception e) {} //

                //
                try {
		            if(this.matriz[this.atual.getX()][this.atual.getY() + 1] == ' ' || this.matriz[this.atual.getX()][this.atual.getY() + 1] == 'S') {
		            	novaCoordenada = new Coordenada(this.atual.getX(),(this.atual.getY() + 1));
		                fila.enfileirar(novaCoordenada);
		            }
		        } catch(Exception e) {} //
                
                //
                try {
		            if(this.matriz[this.atual.getX()][this.atual.getY() - 1] == ' ' || this.matriz[this.atual.getX()][this.atual.getY() - 1] == 'S') {
		            	novaCoordenada = new Coordenada(this.atual.getX(),(this.atual.getY() - 1));
		                fila.enfileirar(novaCoordenada);
		            }

		        } catch(Exception e) {} //
                
		        try {
			        this.atual = fila.getElemento();
			        fila.desenfileirar();

			        try {
				        if (!this.atual.equals(this.saida))
			            	this.matriz[this.atual.getX()][this.atual.getY()] = '*';

				        caminho.empilhar(this.atual);
				        possibilidades.empilhar(fila);   
			        } 
			        catch(Exception e) {} 
		        } 
		        catch(Exception e) {
		        	modoProgressivo = false;
		        }
		    } else { //Modo Regressivo
		        if (possibilidades.isVazia() && caminho.isVazia())
                	throw new Exception("Nao existe saida para o labirinto!");
                        
                try {
                    this.atual = caminho.getElemento();
                    caminho.desempilhar();

                    this.matriz[this.atual.getX()][this.atual.getY()] = ' ';

                    fila = possibilidades.getElemento();
                    possibilidades.desempilhar();

                    this.atual = fila.getElemento();
                    fila.desenfileirar();

                    this.matriz[this.atual.getX()][this.atual.getY()] = '*';
                    caminho.empilhar(this.atual);
                    possibilidades.empilhar(fila);

                    modoProgressivo = true;
		        } catch(Exception e) {}
		    }

            if (this.atual.equals(this.saida))
	            achouSaida = true;
		}

        Pilha<Coordenada> inverso = new Pilha();

        while (!caminho.isVazia()) {
	        try {
	            inverso.empilhar(caminho.getElemento());
	            caminho.desempilhar();
	        } 
	        catch(Exception e) {}
	    }

        return inverso;            
	}

	/**
        * Método que cria um novo arquivo texto contendo o labirinto resolvido.
        * @throws Exception O método lança uma exceção caso o parâmetro passado seja nulo ou vazio, ou se ocorre algum erro na gravação do arquivo.
    */
	public void criarArquivoComSolucao(String nomeArquivo) throws Exception {
		if (nomeArquivo == null || nomeArquivo == "")
			throw new Exception("Nome do arquivo nao informado");

		Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:/Temp/" + nomeArquivo), "utf-8"));

	    writer.write(new Integer(this.linhas).toString());
	    writer.write(System.lineSeparator());
	    writer.write(new Integer(this.colunas).toString());
	    writer.write(System.lineSeparator());

	    for (int i = 0; i < this.linhas; i++) {
			for (int j = 0; j < this.colunas; j++)
				writer.write(this.matriz[i][j]);
			writer.write(System.lineSeparator());
		}
	    writer.close();
	}

	/**
        * Método que retorna um objeto de Coordenada indicando a entrada do labirinto
        * @return Retorna uma coordenada que indica a entrada do labirinto
        *
    */
	public Coordenada getEntrada() {
		Coordenada entrada = (Coordenada)this.entrada.clone();
		return entrada;
	}

	/**
        * Método que retorna um objeto de Coordenada indicando a saída do labirinto
        * @return Retorna uma coordenada que indica a saída do labirinto
        *
    */
	public Coordenada getSaida() {
		Coordenada saida = (Coordenada)this.saida.clone();
		return saida;
	}

	/**
        * Método que retorna uma representação escrita do objeto na forma de uma String
        * @return Retorna a representação escrita do objeto
        *
    */
	public String toString() {
		String ret = "";

		ret += "entrada=" + this.entrada + ":";
		ret += "saida=" + this.saida + ":";
		ret += "atual=" + this.atual + ":";

		ret += "linhas=" + this.linhas + ":";
		ret += "colunas=" + this.colunas + ":";

		ret += System.lineSeparator() + "{";

		for (int i = 0; i < this.linhas; i++) {
			ret += System.lineSeparator();
			for (int j = 0; j < this.colunas; j++)
				ret += this.matriz[i][j];
		}

		ret += System.lineSeparator() + "}";

		return ret;
	}

	/**
        * Método que compara um objeto à própria instância, determinando se são ou não iguais
        * @param obj É um objeto que será comparado à própria instância, podendo ser ou não da mesma classe
        * @return Retorna o resultado da comparação, podendo ter um resultado verdadeiro ou falso
    */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (obj == null)
			return false;

		if (obj.getClass() != this.getClass())
			return false;

		Labirinto l = (Labirinto)obj;

		if (l.entrada.equals(this.entrada))
			return false;

		if (l.saida.equals(this.saida))
			return false;

		if (l.atual.equals(this.atual))
			return false;

		if (l.linhas != this.linhas)
			return false;

		if (l.colunas != this.colunas)
			return false;

		for (int i = 0; i < this.linhas; i++)
			for (int j = 0; j < this.colunas; j++)
				if (!(this.matriz[i][j] == l.matriz[i][j]))
					return false;

		return true;
	}

	/**
		* Método que retorna um código praticamente único do objeto, que é calculado a partir de seus atributos
		* @return Retorna um código praticamente único do objeto, que é calculado a partir de seus atributos
	*/
	public int hashCode() {
		int ret = 666;

		ret = ret*7 + this.entrada.hashCode();
		ret = ret*7 + this.saida.hashCode();
		ret = ret*7 + this.atual.hashCode();

		ret = ret*7 + this.linhas;
		ret = ret*7 + this.colunas;

		for (int i = 0; i < this.linhas; i++)
			for (int j = 0; j < this.colunas; j++)
				ret = ret*7 + new Character(this.matriz[i][j]).hashCode();

		return ret;
	}

	/**
        * Método construtor que utiliza um modelo da classe Labirinto como parâmtro para definir os valores do objeto
        * @param modelo É o objeto que será utilizado como modelo para a criação do novo objeto
        * @throws Exception O método lança uma exceção caso o objeto da classe Labirinto passado seja nulo
    */
	public Labirinto(Labirinto modelo) throws Exception {
		if (modelo == null)
			throw new Exception("Modelo inexistente");

		this.entrada = (Coordenada)modelo.entrada.clone();
		this.saida = (Coordenada)modelo.saida.clone();
		this.atual = (Coordenada)modelo.atual.clone();

		this.linhas = modelo.linhas;
		this.colunas = modelo.colunas;

		this.matriz = new char[modelo.linhas][modelo.colunas];

		for (int i = 0; i < modelo.linhas; i++)
			for (int j = 0; j < modelo.colunas; j++)
				this.matriz[i][j] = modelo.matriz[i][j];
	}

	/**
        * Método que retorna um clone idêntico da própria instância, tendo os mesmos valores
        * @return Retorna um clone que possui as mesmas características do objeto
        *
    */
	public Object clone() {
		Labirinto ret = null;

		try {
			ret = new Labirinto(this);
		} 
		catch(Exception e) {}

		return ret;
	}
}