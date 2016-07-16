
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Derek
 */
public class Soldier extends Ant {

    private int prevx;
    private int nextx;
    private int prevy;
    private int nexty;
    private ArrayList<ColonyNodeView> randomList = new ArrayList<>();

    public Soldier() {

    }

    @Override
    public void takeTurn(ColonyNodeView[][] Array, ArrayList<Ant> Ants) {
        if (lifespan < 0) {
            Array[x][y].soldierID.remove(this);
            Array[x][y].setSoldierCount(Array[x][y].getSoldierCount());
        } else if (Array[x][y].getBalaCount() == 0) {
            prevx = x - 1;
            if (prevx < 0) {
                prevx = 0;
            }
            nextx = x + 1;
            if (nextx > 26) {
                nextx = 26;
            }
            prevy = y - 1;
            if (prevy < 0) {
                prevy = 0;
            }
            nexty = y + 1;
            if (nexty > 26) {
                nexty = 26;
            }
            
            // Checks each adjacent square for bala. If none, move randomly. 
            if (Array[prevx][prevy].getBalaCount() > 0) {
                Array[prevx][prevy].soldierID.add(this);
                Array[prevx][prevy].setSoldierCount(Array[prevx][prevy].getSoldierCount());
                Array[x][y].soldierID.remove(this);
                Array[x][y].setSoldierCount(Array[x][y].getSoldierCount());
                this.x = prevx;
                this.y = prevy;
            } else if (Array[x][prevy].getBalaCount() > 0) {
                Array[x][prevy].soldierID.add(this);
                Array[x][prevy].setSoldierCount(Array[x][prevy].getSoldierCount());
                Array[x][y].soldierID.remove(this);
                Array[x][y].setSoldierCount(Array[x][y].getSoldierCount());
                this.y = prevy;
            } else if (Array[nextx][prevy].getBalaCount() > 0) {
                Array[nextx][prevy].soldierID.add(this);
                Array[nextx][prevy].setSoldierCount(Array[nextx][prevy].getSoldierCount());
                Array[x][y].soldierID.remove(this);
                Array[x][y].setSoldierCount(Array[x][y].getSoldierCount());
                this.x = nextx;
                this.y = prevy;
            } else if (Array[prevx][y].getBalaCount() > 0) {
                Array[prevx][y].soldierID.add(this);
                Array[prevx][y].setSoldierCount(Array[prevx][y].getSoldierCount());
                Array[x][y].soldierID.remove(this);
                Array[x][y].setSoldierCount(Array[x][y].getSoldierCount());
                this.x = prevx;
            } else if (Array[nextx][y].getBalaCount() > 0) {
                Array[nextx][y].soldierID.add(this);
                Array[nextx][y].setSoldierCount(Array[nextx][y].getSoldierCount());
                Array[x][y].soldierID.remove(this);
                Array[x][y].setSoldierCount(Array[x][y].getSoldierCount());
                this.x = nextx;
            } else if (Array[prevx][nexty].getBalaCount() > 0) {
                Array[prevx][nexty].soldierID.add(this);
                Array[prevx][nexty].setSoldierCount(Array[prevx][nexty].getSoldierCount());
                Array[x][y].soldierID.remove(this);
                Array[x][y].setSoldierCount(Array[x][y].getSoldierCount());
                this.x = prevx;
                this.y = nexty;
            } else if (Array[x][nexty].getBalaCount() > 0) {
                Array[x][nexty].soldierID.add(this);
                Array[x][nexty].setSoldierCount(Array[x][nexty].getSoldierCount());
                Array[x][y].soldierID.remove(this);
                Array[x][y].setSoldierCount(Array[x][y].getSoldierCount());
                this.y = nexty;
            } else if (Array[nextx][nexty].getBalaCount() > 0) {
                Array[nextx][nexty].soldierID.add(this);
                Array[nextx][nexty].setSoldierCount(Array[nextx][nexty].getSoldierCount());
                Array[x][y].soldierID.remove(this);
                Array[x][y].setSoldierCount(Array[x][y].getSoldierCount());
                this.x = nextx;
                this.y = nexty;
            } else {

                if (Array[prevx][prevy].isVisible == true) {
                    randomList.add(Array[prevx][prevy]);
                }
                if (Array[x][prevy].isVisible == true) {
                    randomList.add(Array[x][prevy]);
                }
                if (Array[nextx][prevy].isVisible == true) {
                    randomList.add(Array[nextx][prevy]);
                }
                if (Array[prevx][y].isVisible == true) {
                    randomList.add(Array[prevx][y]);
                }
                if (Array[nextx][y].isVisible == true) {
                    randomList.add(Array[nextx][y]);
                }
                if (Array[prevx][nexty].isVisible == true) {
                    randomList.add(Array[prevx][nexty]);
                }
                if (Array[x][nexty].isVisible == true) {
                    randomList.add(Array[x][nexty]);
                }
                if (Array[nextx][nexty].isVisible == true) {
                    randomList.add(Array[nextx][nexty]);
                }

                ColonyNodeView Node = Simulation.randomNode(randomList);

                xmove = Node.x;
                ymove = Node.y;

                Array[xmove][ymove].soldierID.add(this);
                Array[xmove][ymove].setSoldierCount(Array[xmove][ymove].getSoldierCount());
                Array[x][y].soldierID.remove(this);
                Array[x][y].setSoldierCount(Array[x][y].getSoldierCount());
                Array[xmove][ymove].showNode();

                this.x = xmove;
                this.y = ymove;

                randomList.clear();
            }

        } else {
            int attack = Simulation.randomAttack();
            
            // If attack is success, remove from master list and update grid.
            if (attack == 1) {
                Ants.remove(Array[x][y].balaID.get(0));
                Array[x][y].balaID.remove(0);
                Array[x][y].setBalaCount(Array[x][y].getBalaCount());
            }
        }
        lifespan -= 1;

    }

}
