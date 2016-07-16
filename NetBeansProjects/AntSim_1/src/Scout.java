
import java.util.ArrayList;

/**
 *
 * @author Derek
 */
public class Scout extends Ant {

    public Scout() {

    }

    @Override
    public void takeTurn(ColonyNodeView[][] Array, ArrayList<Ant> Ants) {

        if (lifespan < 0) {
            Array[x][y].scoutID.remove(this);
            Array[x][y].setScoutCount(Array[x][y].getScoutCount());
        } else {
            // Random movement pattern
            xmove = Simulation.randomDirection(x - 1, x + 1);
            ymove = Simulation.randomDirection(y - 1, y + 1);

            while (xmove == x && ymove == y) {
                xmove = Simulation.randomDirection(x - 1, x + 1);
                ymove = Simulation.randomDirection(y - 1, y + 1);
            }

            Array[xmove][ymove].scoutID.add(this);
            Array[xmove][ymove].setScoutCount(Array[xmove][ymove].getScoutCount());
            Array[x][y].scoutID.remove(this);
            Array[x][y].setScoutCount(Array[x][y].getScoutCount());
            Array[xmove][ymove].showNode();

            x = xmove;
            y = ymove;
            lifespan -= 1;
        }
    }

}
