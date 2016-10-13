package uno;

import java.util.ArrayList;

public class UnoPlayer {
    private ArrayList<UnoCard> hand = new ArrayList<>();
    
    public UnoPlayer(){
        
    }
    
    public void addCard(UnoCard card){
        hand.add(card);
    }
    
    public void removeCard(UnoCard card){
        hand.remove(card);
    }
    
    public int getHandSize(){
        return hand.size();
    }
    
    public ArrayList<UnoCard> getHand(){
        return hand;
    }
}   
