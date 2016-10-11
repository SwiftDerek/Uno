package uno;

public class UnoNumber extends UnoCard{
    
    private int number;
    
    public UnoNumber(String color, int number){
        this.color = color;
        this.number = number;
    }
    
    public int getNumber(){
        return this.number;
    }
    
}
