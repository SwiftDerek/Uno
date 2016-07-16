
import java.util.ArrayList;

/**
 *
 * @author Derek
 */
public class Queen extends Ant {

    public Queen() {
        lifespan = 10 * 7300;
        alive = true;
    }

    @Override
    public void takeTurn(ColonyNodeView[][] Array, ArrayList<Ant> Ants) {

        Array[13][13].setFoodAmount(Array[13][13].getFoodCount() - 1);
        lifespan -= 1;
    }

    @Override
    public void spawnAnt(ArrayList<Ant> Ants, ColonyNodeView[][] Array, int id) {
        // Spawn random ant and add to master list
        Ant ant = Simulation.randomAntDrop();
        if (ant instanceof Forager) {
            ant.ID = id;
            Array[13][13].foragerID.add(ant);
            Array[13][13].setForagerCount(Array[13][13].getForagerCount());
        }
        else if (ant instanceof Scout) {
            ant.ID = id;
            Array[13][13].scoutID.add(ant);
            Array[13][13].setScoutCount(Array[13][13].getScoutCount());
        }
        else if (ant instanceof Soldier) {
            ant.ID = id;
            Array[13][13].soldierID.add(ant);
            Array[13][13].setSoldierCount(Array[13][13].getSoldierCount());
        }
        ant.x = 13;
        ant.y = 13;
        Ants.add(ant);
        id++;
    }

}
