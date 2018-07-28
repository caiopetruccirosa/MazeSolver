package coordenada;

/**
    * A classe Coordenada é uma classe de objetos que guardam um X e um Y, ou seja, um coordenada de determinado ponto.
    *
    * @author Caio Petrucci Rosa, Fábio Faúndes e Luís Sabença
*/
public class Coordenada implements Cloneable
{
    protected int x;
    protected int y;
    
    /**
        * Método construtor que inicia as variáveis do objeto, de acordo com os valores passados por parâmetro
        * @param X Esse parâmetro irá definir o valor X da Coordenada
        * @param Y Esse parâmetro irá definir o valor Y da Coordenada
        * @throws Exception O construtor lança uma exceção caso algum dos valores seja inválido, ou seja, menor do que 0
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
        * Método que retorna a posicão horizontal do objeto
        * @return Retorna um valor que representa a posição horizontal do objeto 
        *
    */
    public int getX()
    {
        return this.x;
    }
    
    /**
        * Método que retorna a posição vertical do objeto
        * @return Retorna um valor que representa a posição vertical do objeto 
        *
    */
    public int getY()
    {
        return this.y;
    }
    
    /**
        * Método que muda o valor da posição horizontal do objeto a partir do valor passado por parâmetro
        * @param X É um valor usado para definir a nova posição horizontal do objeto
        * @throws Exception O método lançará a exceção caso o valor passado por parâmetro seja menor que 0, representando uma posição inválida
    */
    public void setX(int X) throws Exception
    {
        if (X < 0)
            throw new Exception ("Novo valor de x invalido!");
            
        this.x = X;
    }
    
    /**
        * Método que muda o valor da posição vertical do objeto a partir do valor passado por parâmetro
        * @param Y É um valor usado para definir a nova posição vertical do objeto
        * @throws Exception O método lançará a exceção caso o valor passado por parâmetro seja menor que 0, representando uma posição inválida
    */
    public void setY(int Y) throws Exception
    {
        if (Y < 0)
         throw new Exception ("Novo valor de y invalido!");
         
        this.y = Y;
    }
    
    /**
        * Método construtor que utiliza um modelo da classe Coordenada para definir as posições do objeto
        * @param modelo É o objeto que será utilizado como modelo para a criação do novo objeto
        * @throws Exception O método lançará a exceção caso o objeto da classe Coordenada passado seja nulo
    */
    public Coordenada (Coordenada modelo) throws Exception
    {
        if (modelo == null)
            throw new Exception("Modelo inexistente");
    
        this.x = modelo.x;
        this.y = modelo.y;
    }
    
    /**
        * Método que retorna um clone idêntico da própria instância, tendo os mesmos valores
        * @return Retorna um clone que possui as mesmas características do objeto
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
        * Método que retorna uma representação escrita do objeto na forma de uma String
        * @return Retorna a representação escrita do objeto
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
        * Método que compara um objeto à própria instância, determinando se são ou não iguais
        * @param obj É um objeto que será comparado à própria instância, podendo ser ou não da mesma classe
        * @return Retorna o resultado da comparação, podendo ter um resultado verdadeiro ou falso
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
        * Método que retorna um código praticamente único do objeto, que é calculado a partir de seus atributos
        * @return Retorna um código praticamente único do objeto, que é calculado a partir de seus atributos
    */
    public int hashCode()
    {
        int ret = 24;
        
        ret += ret * 3 + new Integer(this.x).hashCode();
        ret += ret * 3 + new Integer(this.y).hashCode();
        
        return ret;
    }
}