package uno;

import java.util.ArrayList;

public class UnoPlayer {
    private ArrayList<UnoCard> hand;
    
    public UnoPlayer(){
        
    }
    
    public void draw(int cards, UnoDeck deck){
        for (int i = 0; i < cards; i++){
            hand.add(deck.draw());
        }
    }
    
    public void discard(UnoCard card, UnoDeck deck){
        if(card instanceof UnoNumber){
            compareNumbers((UnoNumber)card, deck);
        }
        else if (card instanceof UnoWild || card instanceof UnoWildDraw){
            addWild(card, deck);
        }
        else {
            compareColors(card, deck);
        }
    }
    
    public void compareNumbers(UnoNumber card, UnoDeck deck){
        UnoCard topOfPile = deck.getTopOfDiscardPile();
        if (topOfPile instanceof UnoNumber){
            if((card.getNumber() == ((UnoNumber)topOfPile).getNumber()) || (card.getColor().equals(topOfPile.getColor()))){
                deck.setTopOfDiscardPile(card);
                hand.remove(card);
            } else {
                    // Display error message
            }
        } else if (card.getColor().equals(topOfPile.getColor())){
            deck.setTopOfDiscardPile(card);
            hand.remove(card);
        } else {
            // Display error message
        }
    }
    
    public void compareColors(UnoCard card, UnoDeck deck){
        UnoCard topOfPile = deck.getTopOfDiscardPile();
        if(card.getColor().equals(topOfPile.getColor())){
            deck.setTopOfDiscardPile(card);
            hand.remove(card);
        } else {
            // Display error message
        }
    }
    
    public void addWild(UnoCard card, UnoDeck deck){
        deck.setTopOfDiscardPile(card);
        hand.remove(card);
    }
}   
