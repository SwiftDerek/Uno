package uno;

public final class UnoModel {
    
    private UnoPlayer[] players;
    private UnoPlayer currentPlayer;
    private final int numCardsDrawn = 7;
    private UnoDeck deck;
    private int currentPlayerIndex = 0;
    private boolean reverse = false;
    private int numPlayers = 0;
    
    public UnoModel(){
            
    }
    
    
    public void startGame(int numPlayers){
        deck = new UnoDeck();
        deck.discardFirstCard();
        this.numPlayers = numPlayers;
        players = new UnoPlayer[numPlayers];
        addPlayers();
    }
    
    public void addPlayers(){
        for (int i = 0; i < numPlayers; i++){
            players[i] = new UnoPlayer();
            setCurrentPlayer(players[i]);
            draw(numCardsDrawn);
        }
        setCurrentPlayer(players[0]);
    }
    
    public void draw(int cards){
        for (int i = 0; i < cards; i++){
            currentPlayer.addCard(deck.draw());
        }
    }
    
    public void discard(UnoCard card){
        if(card instanceof UnoNumber){
            compareNumbers((UnoNumber)card);
        }
        else if (card instanceof UnoWild){
            discardCard(card);
        } else if (card instanceof UnoWildDraw){
            discardCard(card);
            draw(4);
            nextPlayer();
        }
        else {
            compareColors(card);
        }
    }
    
    public void setCurrentPlayer(UnoPlayer player){
        currentPlayer = player;
    }
    
    public UnoPlayer getCurrentPlayer(){
        return currentPlayer;
    }
    
    public UnoPlayer[] getPlayers(){
        return players;
    }
    
    public int getCurrentPlayerIndex(){
        return currentPlayerIndex;
    }
    
    public void setCurrentPlayerIndex(){
        this.currentPlayerIndex = 0;
    }
    
    public void nextPlayer(){
        if (!reverse){
            currentPlayerIndex++;
            if(currentPlayerIndex > (numPlayers - 1)){
                currentPlayerIndex = 0;
            }
            setCurrentPlayer(players[currentPlayerIndex]);
        } else {
            currentPlayerIndex--;
            if(currentPlayerIndex < 0){
                currentPlayerIndex = numPlayers - 1;
            }
            setCurrentPlayer(players[currentPlayerIndex]); 
        }
    }

    
    public void compareNumbers(UnoNumber card){
        UnoCard topOfPile = deck.getTopOfDiscardPile();
        if (topOfPile instanceof UnoNumber){
            if((card.getNumber() == ((UnoNumber)topOfPile).getNumber()) || (card.getColor().equals(topOfPile.getColor()))){
                discardCard(card);
            } else {
                    // Display error message
            }
        } else {
            // Display error message
        }
    }
    
    public void compareColors(UnoCard card){
        UnoCard topOfPile = deck.getTopOfDiscardPile();
        if(card.getColor().equals(topOfPile.getColor())){
            if(card instanceof UnoReverse){
                reverse = !reverse;
                discardCard(card);
            } else if (card instanceof UnoSkip){
                discardCard(card);
                nextPlayer();
            } else {
                discardCard(card);
                draw(2);
                nextPlayer();
            }
        } else {
            // Display error message
        }
    }
    
    public void discardCard(UnoCard card){
        deck.setTopOfDiscardPile(card);
        currentPlayer.removeCard(card);
        nextPlayer();
    }
    
    public boolean UNO(){
        return currentPlayer.getHandSize() == 1;
    }
    
    public UnoCard getTopOfDeck(){
        return deck.getTopOfDiscardPile();
    }
}
