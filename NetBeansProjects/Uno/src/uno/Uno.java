package uno;


public class Uno {

    public static void main(String[] args) {
        UnoView View = new UnoView();
        UnoModel Model = new UnoModel();
        UnoController Controller = new UnoController(View, Model);
        View.setController(Controller);
    }
    
}
