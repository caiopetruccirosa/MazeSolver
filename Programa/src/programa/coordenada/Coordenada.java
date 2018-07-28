package coordenada;

/**
    * A classe Coordenada � uma classe de objetos que guardam um X e um Y, ou seja, um coordenada de determinado ponto.
    *
    * @author Caio Petrucci Rosa, F�bio Fa�ndes e Lu�s Saben�a
*/
public class Coordenada implements Cloneable
{
    protected int x;
    protected int y;
    
    /**
        * M�todo construtor que inicia as vari�veis do objeto, de acordo com os valores passados por par�metro
        * @param X Esse par�metro ir� definir o valor X da Coordenada
        * @param Y Esse par�metro ir� definir o valor Y da Coordenada
        * @throws Exception O construtor lan�a uma exce��o caso algum dos valores seja inv�lido, ou seja, menor do que 0
    */
    public Coordenada (int X, int Y) throws Exception
    {   
        if (X < 0)
            throw new Exception("Coordenada invalida!");

        if (Y < 0)
            throw new Exception("Coordenada invalida!");

        this.x = X;
        this.y = Y;
    }
    
    /**
        * M�todo que retorna a posic�o horizontal do objeto
        * @return Retorna um valor que representa a posi��o horizontal do objeto 
        *
    */
    public int getX()
    {
        return this.x;
    }
    
    /**
        * M�todo que retorna a posi��o vertical do objeto
        * @return Retorna um valor que representa a posi��o vertical do objeto 
        *
    */
    public int getY()
    {
        return this.y;
    }
    
    /**
        * M�todo que muda o valor da posi��o horizontal do objeto a partir do valor passado por par�metro
        * @param X � um valor usado para definir a nova posi��o horizontal do objeto
        * @throws Exception O m�todo lan�ar� a exce��o caso o valor passado por par�metro seja menor que 0, representando uma posi��o inv�lida
    */
    public void setX(int X) throws Exception
    {
        if (X < 0)
            throw new Exception ("Novo valor de x invalido!");
            
        this.x = X;
    }
    
    /**
        * M�todo que muda o valor da posi��o vertical do objeto a partir do valor passado por par�metro
        * @param Y � um valor usado para definir a nova posi��o vertical do objeto
        * @throws Exception O m�todo lan�ar� a exce��o caso o valor passado por par�metro seja menor que 0, representando uma posi��o inv�lida
    */
    public void setY(int Y) throws Exception
    {
        if (Y < 0)
         throw new Exception ("Novo valor de y invalido!");
         
        this.y = Y;
    }
    
    /**
        * M�todo construtor que utiliza um modelo da classe Coordenada para definir as posi��es do objeto
        * @param modelo � o objeto que ser� utilizado como modelo para a cria��o do novo objeto
        * @throws Exception O m�todo lan�ar� a exce��o caso o objeto da classe Coordenada passado seja nulo
    */
    public Coordenada (Coordenada modelo) throws Exception
    {
        if (modelo == null)
            throw new Exception("Modelo inexistente");
    
        this.x = modelo.x;
        this.y = modelo.y;
    }
    
    /**
        * M�todo que retorna um clone id�ntico da pr�pria inst�ncia, tendo os mesmos valores
        * @return Retorna um clone que possui as mesmas caracter�sticas do objeto
    */
    public Object clone()
    {
        Coordenada ret = null;
    
        try
        {
            ret = new Coordenada(this);
            
        }
        catch(Exception e)
        {}
    
        return ret;
    }
    
    /**
        * M�todo que retorna uma representa��o escrita do objeto na forma de uma String
        * @return Retorna a representa��o escrita do objeto
    */
    public String toString()
    {
        String ret = "";
        
        ret += "(";
        ret += this.x;
        ret += ",";
        ret += this.y;
        ret += ")";

        return ret;
    }
    
    /**
        * M�todo que compara um objeto � pr�pria inst�ncia, determinando se s�o ou n�o iguais
        * @param obj � um objeto que ser� comparado � pr�pria inst�ncia, podendo ser ou n�o da mesma classe
        * @return Retorna o resultado da compara��o, podendo ter um resultado verdadeiro ou falso
    */
    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        
        if(this == obj)
            return true;
        
        if (obj.getClass() != this.getClass())
            return false;
        
        Coordenada c = (Coordenada)obj;
        
        if(c.x != this.x)
            return false;
        
        if(c.y != this.y)
            return false;

        return true;
    }
    
    /**
        * M�todo que retorna um c�digo praticamente �nico do objeto, que � calculado a partir de seus atributos
        * @return Retorna um c�digo praticamente �nico do objeto, que � calculado a partir de seus atributos
    */
    public int hashCode()
    {
        int ret = 24;
        
        ret += ret * 3 + new Integer(this.x).hashCode();
        ret += ret * 3 + new Integer(this.y).hashCode();
        
        return ret;
    }
}