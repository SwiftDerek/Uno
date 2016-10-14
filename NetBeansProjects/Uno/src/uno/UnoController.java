package uno;

import javax.swing.ImageIcon;

public class UnoController {
    
    private UnoView View;
    private UnoModel Model;
    
    public UnoController(UnoView view, UnoModel model){
        this.View = view;
        this.Model = model;
        
        View.setVisible(true);
        View.hidePanel();
    }
    
    public void setNumPlayers(int numPlayers){
        Model.setCurrentPlayerIndex();
        View.clearPlayersLabel();
        Model.startGame(numPlayers);
        View.setDiscardPile(View.getImage(Model.getTopOfDeck()));
        View.showPanel();
        startTurn();
    }
    
    public void changePlayerLabel(UnoPlayer[] players){
        View.setPlayers1Label(players[0].getHandSize());
        View.setPlayers2Label(players[1].getHandSize());
        if (players.length == 3){
            View.setPlayers3Label(players[2].getHandSize());
        } 
        if (players.length == 4){
            View.setPlayers3Label(players[2].getHandSize());
            View.setPlayers4Label(players[3].getHandSize());
        }
    }
    
    public void startTurn(){
        changePlayerLabel(Model.getPlayers());
        View.setCurrentPlayerLabel(Model.getCurrentPlayerIndex());
        View.showDrawButton();
        displayHandCards();
    }
    
    public void endTurn(){
        Model.nextPlayer();
        startTurn();
    }
    
    public boolean checkUNO(){
        return Model.UNO();
    }
    
    public void draw(){
        Model.draw(1);
        View.hideDrawButton();
        changePlayerLabel(Model.getPlayers());
        displayHandCards();
    }
    
    public void displayHandCards(){
        int h = Model.getCurrentPlayer().getHand().size();
        if (h > 25){ // Handle if hand is bigger than the screen allows
            h = 25;
        }
        ImageIcon image;
        for (int i = 0; i < h; i++){
            image = View.getImage(Model.getCurrentPlayer().getHand().get(i));
            View.labelArray[i].setIcon(image);
            View.labelArray[i].setVisible(true);
            View.currentHand[i] = Model.getCurrentPlayer().getHand().get(i);
        }
        for (int i = h; i < 26; i++){
            View.labelArray[i].setVisible(false);
        }
    }
    
    public void discardCard(UnoCard card){
        Model.discard(card);
        if (wildCardPopUp()){
            Model.getTopOfDeck().setColor(View.chooseColor());
            Model.setWildCardPopUp();
        }
        if(!Model.getCantPlay()){
            startTurn();
            Model.setCantPlay();
        }
    }
    
    public boolean invalidPlay(){
        return Model.getCantPlay();
    }
    
    public UnoCard getTopOfDeck(){
        return Model.getTopOfDeck();
    }
    
    public boolean wildCardPopUp(){
        return Model.getWildCardPopUp();
    }
    
}
