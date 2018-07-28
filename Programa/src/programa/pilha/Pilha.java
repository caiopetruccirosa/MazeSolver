package pilha;

import java.lang.reflect.Method;

/**
    * A classe Pilha � uma classe de vetor que guarda dados de maneira last-in-first-out(LIFO).
    *
    * @author Caio Petrucci Rosa, F�bio Fa�ndes e Lu�s Saben�a
*/
public class Pilha <X> implements Cloneable {
	
	protected Object[] pilha;
	protected int topo;
	protected double taxaCresc;

    /**
        * M�todo construtor que instancia o vetor da Pilha e inicia suas vari�veis, com 10 posi��es iniciais, e uma taxa de crescimento de 10%.
	*/
	public Pilha() {
		this.iniciacao(10, 0.1);
	}

	/**
		* M�todo construtor que instancia o objeto com determinado n�mero de posi��es no vetor, e uma taxa de crescimento de 10%.
		* @param tam � um valor inteiro que indicar� o tamanho do vetor da pilha.
		* @throws Exception O construtor lan�a uma exce��o caso o tamanho passado como par�metro seja inv�lido, ou seja, menor do que 1.
	*/
	public Pilha(int tam) throws Exception {
		if (tam < 1)
            throw new Exception ("Tamanho de pilha invalido");
        
        this.iniciacao(tam, 0.1);
	}

	/**
		* M�todo construtor que instancia o vetor a partir do valor passado por par�metro, define a taxa de crescimento por par�metro e inicia as vari�veis da pilha.
		* @param tam � um valor inteiro que indicar� o tamanho do vetor da pilha.
		* @param tc � um valor real que indicar� a taxa de crescimento do vetor da pilha.
		* @throws Exception O construtor lan�a uma exce��o caso algum valor passado seja inv�lido.
	*/
	public Pilha(int tam, double tc) throws Exception {
		if (tam < 1)
			throw new Exception ("Tamanho de pilha invalido");

		if (tc <= 0)
			throw new Exception ("Taxa de crescimento invalida");
        
        this.iniciacao(tam, tc);
	}

    ////////////////////////////
    
    protected void iniciacao (int tam, double tc) {
        this.pilha = new Object[tam];
		this.topo = -1;
		this.taxaCresc = tc;
    }

	protected void cresca() {
		int novoTamanho = (int)Math.ceil((this.pilha.length)*(1+this.taxaCresc));
		Object[] novaPilha = new Object[novoTamanho];

		for (int i = 0; i <= this.topo; i++) {
			if (this.pilha[i] instanceof Cloneable)
				novaPilha[i] = this.cloneDe((X)this.pilha[i]); 
			else
				novaPilha[i] = this.pilha[i];
		}

		this.pilha = novaPilha;
	}
    
    protected X cloneDe(X x) {
		X ret = null;

		try {
			Class<?> classe = x.getClass();
			Class<?>[] tiposParametros = null;
			Method metodo = classe.getMethod("clone", tiposParametros);
			Object[] valoresParametros = null;
			ret = (X)metodo.invoke(x, valoresParametros);
		} catch(Exception e) {}
			
		return ret;
	}
    
    ///////////////////////////

	/**
		* M�todo que retorna o elemento no topo da pilha
		* @return Retorna o elemento na �ltima posi��o do vetor da pilha
		* @throws Exception M�todo lan�a exce��o caso a pilha esteja vazia
	*/
	public X getElemento() throws Exception {
		if (this.topo < 0)
			throw new Exception("Pilha inexistente");

		X elemento = null;

		if (this.pilha[this.topo] instanceof Cloneable)
			elemento = this.cloneDe((X)this.pilha[this.topo]);
		else 
			elemento = (X)this.pilha[this.topo];

		return elemento;
	}

	/**
		* M�todo que informa se a o vetor da pilha est� vazio ou n�o.
		* @return Retorna true ou false, indicando se a pilha est� vazia ou n�o.
	*/
	public boolean isVazia() {
		if (this.topo < 0)
			return true;

		return false;
	}

	/**
		* M�todo que exclui o elemento localizado no final do pilha
		* @throws Exception M�todo lan�a exce��o caso a pilha j� esteja vazia
	*/
	public void desempilhar() throws Exception {
        if (this.isVazia())
            throw new Exception ("Pilha vazia, nao pode ser desempilhada!");
        
		this.pilha[this.topo] = null;
		this.topo--;
	}

	/**
		* M�todo que inclui um novo elemento no final da pilha, aumentando o tamanho do vetor, caso necess�rio.
		* @param novo � o novo elemento que ser� incluido no vetor
		* @throws Exception M�todo lan�a exce��o caso o par�metro passado seja nulo
	*/
	public void empilhar(X novo) throws Exception {
		if (novo == null)
			throw new Exception("Elemento inexistente");

		if (this.topo == this.pilha.length-1)
			this.cresca();

		this.topo++;

		if (novo instanceof Cloneable)
			this.pilha[this.topo] = this.cloneDe(novo); 
		else
			this.pilha[this.topo] = novo;
	}

	///////////////////////////

	/**
		* M�todo que retorna uma representa��o escrita do objeto
		* @return Retorna a representa��o escrita do objeto
	*/
	public String toString() {
		String str = "";

		str += this.topo + ":";
		str += this.taxaCresc + ":";

		str += "{";

		for (int i = this.topo; i >= 0; i--)
			str += this.pilha[i].toString() + ((i==0)?"":":"); 

		str += "}";

		return str;
	}

	/**
		* M�todo que compara um objeto qualquer com si mesmo, determinando se s�o iguais ou n�o
		* @param obj � o objeto que ser� usado para a compara��o no m�todo
		* @return Retorna o resultado da compara��o, determinando se o objeto comparado � ou n�o igual
	*/
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (obj == null)
			return false;

		if (obj.getClass() != this.getClass())
			return false;

		Pilha p = (Pilha)obj;

		if (p.topo != this.topo)
			return false;

		if (p.taxaCresc != this.taxaCresc)
			return false;

		if (p.pilha.length != this.pilha.length)
			return false;

		for (int i = 0; i <= this.topo; i++)
			if (!(this.pilha[i].equals(p.pilha[i])))
				return false;

		return true;
	}

	/**
		* M�todo que retorna um c�digo praticamente �nico do objeto, que � calculado a partir de seus atributos
		* @return Retorna um c�digo praticamente �nico do objeto, que � calculado a partir de seus atributos
	*/
	public int hashCode() {
		int ret = 666;

		ret = ret*7 + new Integer(this.topo).hashCode();
		ret = ret*7 + new Double(this.taxaCresc).hashCode();

		for (int i = 0; i <= this.topo; i++)
			ret = ret*7 + this.pilha[i].hashCode();

		return ret;
	}
    
	/**
		* M�todo contrutor que instancia o vetor da pilha e atribui suas vari�veis a partir de um objeto passado como modelo.
		* @param modelo � o modelo que ser� usado durante a constru��o da nova pilha.
		* @throws Exception O m�todo lan�a exce��o caso a pilha modelo passada seja nula.
	*/
	public Pilha(Pilha modelo) throws Exception {
		if (modelo == null)
			throw new Exception("Modelo inexistente");

		this.pilha = new Object[modelo.pilha.length];
		this.topo = modelo.topo;
		this.taxaCresc = modelo.taxaCresc;

		for (int i = 0; i <= this.topo; i++) {
			if (modelo.pilha[i] instanceof Cloneable)
				this.pilha[i] = modelo.cloneDe((X)modelo.pilha[i]); 
			else
				this.pilha[i] = modelo.pilha[i];
		}
	}

	/**
		* M�todo que clona sua pr�pria inst�ncia e retorna o clone
		* @return Retorna o clone do objeto, com as mesmas caracter�sticas e valores de vari�veis
	*/
	public Object clone() {
		Pilha ret = null;

		try {
			ret = new Pilha(this);
		} catch(Exception e) {}

		return ret;
	}
}