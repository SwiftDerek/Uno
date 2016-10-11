package uno;

public final class UnoModel {
    
    private UnoPlayer[] players;
    private UnoPlayer currentPlayer;
    private final int numCardsDrawn = 7;
    private boolean UNO = false;
    
    public UnoModel(){
            UnoDeck deck = new UnoDeck();
            deck.discardFirstCard();
            addPlayers(4, deck);
            while (!UNO){
                
            }
    }
    
    
    
    public void addPlayers(int number, UnoDeck deck){
        for (int i = 0; i < number; i++){
            players[i] = new UnoPlayer();
            players[i].draw(numCardsDrawn, deck);
        }
        currentPlayer = players[0]; 
    }
}
