
import java.util.ArrayList;

/**
 *
 * @author Derek
 */
public abstract class Ant {
    public int ID;
    public int x;
    public int y;
    public int xmove;
    public int ymove;
    public boolean alive = true;
    public int lifespan = 10 * 365;
    
    public void takeTurn(ColonyNodeView[][] Array, ArrayList<Ant> Ants){
        
    }
    
    public int getTimeAlive(){
        return lifespan;
    }
    
    public void spawnAnt(ArrayList<Ant> Ants, ColonyNodeView[][] Array, int id){
    }
}
