package labirinto;

import coordenada.Coordenada;
import pilha.Pilha;
import fila.Fila;
import java.io.*;

/**
    * A classe Labirinto possui m�todos de como resolver o labirinto.
	*	
    * @author Caio Petrucci Rosa, F�bio Fa�ndes e Lu�s Saben�a
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
		* carregando os dados em uma matriz, e guardando as coordenadas da entrada e da sa�da
		* do labirinto.
		*
		* @param nomeArquivo � uma string que indicar� o diret�rio do arquivo texto que possui o labirinto
		*
		* @throws FileNotFoundException O construtor lan�a esta exce��o caso o arquivo n�o seja encontrado pelo objeto.
		* @throws IOException O construtor lan�a esta exce��o caso ocorra algum erro na leitura do arquivo, por exemplo, caso os dados n�o estejam adequadamente formatados
		* @throws Exception O construtor lan�a esta exce��o caso um arquivo que n�o existe seja passado como par�metro, caso o n�meros de linhas e colunas n�o sejam v�lidos, ou caso o n�meros de entradas e sa�das n�o sejam v�lidos.
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
		* O m�todo resolverCaminho() acha um caminho at� a sa�da do labirinto e retorna uma pilha
		* contendo o caminho da entrada at� a sa�da do labirinto em forma de coordenadas.
		*
		* @return O m�todo retorna uma pilha contendo objetos da classe Coordenada, indicando o caminho
		* da entrada at� a sa�da do labirinto.
		*
		* @throws Exception O m�todo lan�a uma exce��o caso o labirinto n�o possa ser resolvido, ou seja, n�o possua uma sa�da.
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
        * M�todo que cria um novo arquivo texto contendo o labirinto resolvido.
        * @throws Exception O m�todo lan�a uma exce��o caso o par�metro passado seja nulo ou vazio, ou se ocorre algum erro na grava��o do arquivo.
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
        * M�todo que retorna um objeto de Coordenada indicando a entrada do labirinto
        * @return Retorna uma coordenada que indica a entrada do labirinto
        *
    */
	public Coordenada getEntrada() {
		Coordenada entrada = (Coordenada)this.entrada.clone();
		return entrada;
	}

	/**
        * M�todo que retorna um objeto de Coordenada indicando a sa�da do labirinto
        * @return Retorna uma coordenada que indica a sa�da do labirinto
        *
    */
	public Coordenada getSaida() {
		Coordenada saida = (Coordenada)this.saida.clone();
		return saida;
	}

	/**
        * M�todo que retorna uma representa��o escrita do objeto na forma de uma String
        * @return Retorna a representa��o escrita do objeto
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
        * M�todo que compara um objeto � pr�pria inst�ncia, determinando se s�o ou n�o iguais
        * @param obj � um objeto que ser� comparado � pr�pria inst�ncia, podendo ser ou n�o da mesma classe
        * @return Retorna o resultado da compara��o, podendo ter um resultado verdadeiro ou falso
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
		* M�todo que retorna um c�digo praticamente �nico do objeto, que � calculado a partir de seus atributos
		* @return Retorna um c�digo praticamente �nico do objeto, que � calculado a partir de seus atributos
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
        * M�todo construtor que utiliza um modelo da classe Labirinto como par�mtro para definir os valores do objeto
        * @param modelo � o objeto que ser� utilizado como modelo para a cria��o do novo objeto
        * @throws Exception O m�todo lan�a uma exce��o caso o objeto da classe Labirinto passado seja nulo
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
        * M�todo que retorna um clone id�ntico da pr�pria inst�ncia, tendo os mesmos valores
        * @return Retorna um clone que possui as mesmas caracter�sticas do objeto
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