package uno;

import java.util.ArrayList;
import java.util.Stack;

public class UnoDeck {
    private ArrayList<UnoCard> deck = new ArrayList<>();
    private Stack<UnoCard> discardPile = new Stack<>();
    private int topOfDeck;
    
    private final int numberCards = 76;
    private final int drawCards = 8;
    private final int reverseCards = 8;
    private final int skipCards = 8;
    private final int wildCards = 4;
    private final int wildDrawCards = 4;
    
    private final String colorRed = "red";
    private final String colorBlue = "blue";
    private final String colorYellow = "yellow";
    private final String colorGreen = "green";
    
    public UnoDeck(){
        addNumbers();
        addDraws();
        addReverses();
        addSkips();
        addWilds();
        addWildDraws();
        shuffle();
        shuffle();
        topOfDeck = deck.size() - 1;
    }
    
    private void shuffle(){
        deck.forEach((card) -> {
            int randIndex = (int)(Math.random() * deck.size());
            UnoCard temp = card;
            deck.set(deck.indexOf(card), deck.get(randIndex));
            deck.set(deck.indexOf(deck.get(randIndex)), temp);
        });
    }
    
    public void discardFirstCard(){
        discardPile.push(deck.get(topOfDeck));
        deck.remove(topOfDeck);
        topOfDeck--;
    }
    
    public UnoCard draw(){
        if(deck.isEmpty()){
            discardPile.forEach((card) -> {
                deck.add(discardPile.pop());
            });
            shuffle();
            shuffle();
            topOfDeck = deck.size() - 1;
            discardFirstCard();
        }
        UnoCard card = deck.get(topOfDeck);
        deck.remove(topOfDeck);
        topOfDeck--;
        return card;
    }
    
    public void discard(UnoCard card){
        discardPile.push(card);
    }
    
    public UnoCard getTopOfDiscardPile(){
        return discardPile.peek();
    }
    
    public void setTopOfDiscardPile(UnoCard card){
        this.discardPile.push(card);
    }
    
    private void addWildDraws() {
        for(int i = 0; i < wildDrawCards; i++){
            deck.add(new UnoWildDraw());
        }
    }

    private void addWilds() {
        for (int i = 0; i < wildCards; i++){
            deck.add(new UnoWild());
        }
    }

    private void addSkips() {
        for (int i = 0; i < skipCards / 4; i++){
            deck.add(new UnoSkip(colorRed));
        }
        for (int i = 0; i < skipCards / 4; i++){
            deck.add(new UnoSkip(colorBlue));
        }
        for (int i = 0; i < skipCards / 4; i++){
            deck.add(new UnoSkip(colorYellow));
        }
        for (int i = 0; i < skipCards / 4; i++){
            deck.add(new UnoSkip(colorGreen));
        }
    }

    private void addReverses() {
        for (int i = 0; i < reverseCards / 4; i++){
            deck.add(new UnoReverse(colorRed));
        }
        for (int i = 0; i < reverseCards / 4; i++){
            deck.add(new UnoReverse(colorBlue));
        }
        for (int i = 0; i < reverseCards / 4; i++){
            deck.add(new UnoReverse(colorYellow));
        }
        for (int i = 0; i < reverseCards / 4; i++){
            deck.add(new UnoReverse(colorGreen));
        }
    }

    private void addDraws() {
        for (int i = 0; i < drawCards / 4; i++){
            deck.add(new UnoDraw(colorRed));
        }
        for (int i = 0; i < drawCards / 4; i++){
            deck.add(new UnoDraw(colorBlue));
        }
        for (int i = 0; i < drawCards / 4; i++){
            deck.add(new UnoDraw(colorYellow));
        }
        for (int i = 0; i < drawCards / 4; i++){
            deck.add(new UnoDraw(colorGreen));
        }
    }

    private void addNumbers() {
        addRedNumbers();
        addBlueNumbers();
        addYellowNumbers();
        addGreenNumbers();
    }

    private void addGreenNumbers() {
        for (int i = 0; i < (numberCards / 8) + 1; i++) {
            deck.add(new UnoNumber(colorGreen, i));
        }
        for (int i = 0; i < (numberCards / 8) + 1; i++) {
            deck.add(new UnoNumber(colorGreen, i));
        }
    }

    private void addYellowNumbers() {
        for (int i = 0; i < (numberCards / 8) + 1; i++) {
            deck.add(new UnoNumber(colorYellow, i));
        }
        for (int i = 0; i < (numberCards / 8) + 1; i++) {
            deck.add(new UnoNumber(colorYellow, i));
        }
    }

    private void addBlueNumbers() {
        for (int i = 0; i < (numberCards / 8) + 1; i++) {
            deck.add(new UnoNumber(colorBlue, i));
        }
        for (int i = 0; i < (numberCards / 8) + 1; i++) {
            deck.add(new UnoNumber(colorBlue, i));
        }
    }

    private void addRedNumbers() {
        for (int i = 0; i < (numberCards / 8) + 1; i++) {
            deck.add(new UnoNumber(colorRed, i));
        }
        for (int i = 0; i < (numberCards / 8) + 1; i++) {
            deck.add(new UnoNumber(colorRed, i));
        }
    }
}
