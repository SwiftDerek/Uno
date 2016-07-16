
import java.util.ArrayList;

/**
 *
 * @author Derek
 */
public class Bala extends Ant {

    public Bala() {

    }

    @Override
    public void takeTurn(ColonyNodeView[][] Array, ArrayList<Ant> Ants) {
        
        // Check if current square contains enemy. Precidence is queen, then soldier, forager, and scout. If none, move randomly. 
        if (lifespan < 0) {
            Array[x][y].balaID.remove(this);
            Array[x][y].setBalaCount(Array[x][y].getBalaCount());
        } else if (Array[x][y].hasQueen == true) {

            int attack = Simulation.randomAttack();

            if (attack == 1) {
                Ants.get(0).alive = false;
            }
        } else if (Array[x][y].getSoldierCount() > 0) {
            int attack = Simulation.randomAttack();

            if (attack == 1) {
                Ants.remove(Array[x][y].soldierID.get(0));
                Array[x][y].soldierID.remove(0);
                Array[x][y].setSoldierCount(Array[x][y].getSoldierCount());
            }
        } else if (Array[x][y].getForagerCount() > 0) {
            int attack = Simulation.randomAttack();

            if (attack == 1) {
                if (Array[x][y].hasFood == true) {
                    Array[x][y].setFoodAmount(Array[x][y].getFoodCount() + 1);
                }
                Ants.remove(Array[x][y].foragerID.get(0));
                Array[x][y].foragerID.remove(0);
                Array[x][y].setForagerCount(Array[x][y].getForagerCount());
            }
        } else if (Array[x][y].getScoutCount() > 0) {
            int attack = Simulation.randomAttack();

            if (attack == 1) {
                Ants.remove(Array[x][y].scoutID.get(0));
                Array[x][y].scoutID.remove(0);
                Array[x][y].setScoutCount(Array[x][y].getScoutCount());
            }
        } else {
            xmove = Simulation.randomDirection(x - 1, x + 1);
            ymove = Simulation.randomDirection(y - 1, y + 1);

            while (xmove == x && ymove == y) {
                xmove = Simulation.randomDirection(x - 1, x + 1);
                ymove = Simulation.randomDirection(y - 1, y + 1);
            }

            Array[xmove][ymove].balaID.add(this);
            Array[xmove][ymove].setBalaCount(Array[xmove][ymove].getBalaCount());
            Array[x][y].balaID.remove(this);
            Array[x][y].setBalaCount(Array[x][y].getBalaCount());

            x = xmove;
            y = ymove;

        }
        lifespan -= 1;

    }
}
