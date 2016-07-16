
import java.awt.HeadlessException;
import java.util.Random;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Derek
 */
public class Simulation implements SimulationEventListener {

    // Global Variables to hold GUI and Simulation Grid
    AntSimGUI Driver = new AntSimGUI();
    ColonyView View = new ColonyView(27, 27);
    ColonyNodeView[][] ViewArray = new ColonyNodeView[27][27];
    // Data Structure for Ants
    ArrayList<Ant> Ants = new ArrayList<>();
    ArrayList<Ant> deadAnts = new ArrayList<>();
    // Time Variables for each step
    int Turns = 1;
    int Days = 0;
    int id = 1;
    // Random Static class
    static Random r = new Random();

    public static int randomAttack() {
        // Determines if bala/soldier will attack
        return r.nextInt(2);
    }

    public static ColonyNodeView randomNode(ArrayList list) {
        // Determines which node will be picked for forager/soldier movement
        int size = list.size();
        int randomValue = r.nextInt(size);

        return (ColonyNodeView) list.get(randomValue);
    }

    public static int randomDirection(int low, int high) {
        // Determines random direction of ants
        if (low < 0) {
            low = 0;
        }
        if (high > 26) {
            high = 26;
        }
        return r.nextInt(high - low + 1) + low;
    }

    public static int randomFoodDrop() {
        // Determines if node will contain food
        if (r.nextInt(4) == 3) {
            return r.nextInt(1000 - 500 + 1) + 500;
        } else {
            return 0;
        }
    }

    public static int randomBalaDrop() {
        // Bala Drop to 1% to increase longevity 
        if (r.nextInt(100) < 1) {
            return 1;
        } else {
            return 0;
        }
    }

    public static Ant randomAntDrop() {
        // 55% soldiers, 35% foragers, 10% scouts
        // Determines probability of ants spawned from queen
        int antDrop = r.nextInt(100);
        
        if (antDrop < 45){
            Ant soldier = new Soldier();
            return soldier;
        }
        else if (antDrop >= 45 && antDrop < 90){
            Ant forager = new Forager();
            return forager;
        }
        else {
            Ant scout = new Scout();
            return scout;
        }
    }

    // Constructor for Simulation
    public Simulation() {

        Driver.initGUI(View);
        Driver.addSimulationEventListener(this);

    }

    // Listener for Simulation
    @Override
    public void simulationEventOccurred(SimulationEvent simEvent) {
        switch (simEvent.getEventType()) {
            // Set up for Normal Setup
            case SimulationEvent.NORMAL_SETUP_EVENT:

                initializeGrid();

                initializeSquares();

                initializeQueenSquare();

                break;
            // Set up for Run
            case SimulationEvent.RUN_EVENT:
                while ((Ants.get(0).lifespan != 0) && (ViewArray[13][13].getFoodCount() > 0) && (Ants.get(0).alive == true)) {
                    if (Turns == 11) {
                        Days += 1;
                        Turns = 1;
                    }
                    if (Turns == 1) {
                        halvePheromone();
                        Ants.get(0).spawnAnt(Ants, ViewArray, id);
                    }
                    Driver.setTime("Day " + Days + ", Turn " + Turns);
                    randomBala();

                    Ants.stream().map((Ant) -> {
                        if (Ant.lifespan < 0) {
                            deadAnts.add(Ant);
                        }
                        return Ant;
                    }).forEach((Ant) -> {
                        Ant.takeTurn(ViewArray, Ants);
                    });
                    deadAnts.stream().forEach((Ant) -> {
                        Ants.remove(Ant);
                    });
                    Turns++;

                }
                if ((Ants.get(0).lifespan == 0)) {
                    JOptionPane.showMessageDialog(null, "Your queen has died of old age!");
                } else if ((ViewArray[13][13].getFoodCount() == 0)) {
                    JOptionPane.showMessageDialog(null, "Your queen has ran out of food!");
                } else if (Ants.get(0).alive == false) {
                    JOptionPane.showMessageDialog(null, "Your queen has been killed by a bala ant!");
                }
                System.exit(0);
                break;
            // Set up for Step
            case SimulationEvent.STEP_EVENT:
                if ((Ants.get(0).lifespan != 0) && (ViewArray[13][13].getFoodCount() > 0) && (Ants.get(0).alive == true)) {
                    if (Turns == 11) {
                        Days += 1;
                        Turns = 1;
                    }
                    if (Turns == 1) {
                        halvePheromone();
                        Ants.get(0).spawnAnt(Ants, ViewArray, id);
                    }
                    Driver.setTime("Day " + Days + ", Turn " + Turns);
                    randomBala();
                    
                    // Function to iterate through each ant in the master ArrayList Directory
                    Ants.stream().map((Ant) -> {
                        if (Ant.lifespan < 0) {
                            deadAnts.add(Ant);
                        }
                        return Ant;
                    }).forEach((Ant) -> {
                        Ant.takeTurn(ViewArray, Ants);
                    });
                    // Stores the dead ants with lifespan < 0 to remove from master directory
                    deadAnts.stream().forEach((Ant) -> {
                        Ants.remove(Ant);
                    });
                    Turns++;
                }
                displayEndMessage(ViewArray[13][13]);
                break;
            default:
                break;
        }
    }

    public void displayEndMessage(ColonyNodeView QueenSquareStep) throws HeadlessException {
        if ((Ants.get(0).lifespan == 0)) {
            JOptionPane.showMessageDialog(null, "Your queen has died of old age!");
            System.exit(0);
        } else if ((QueenSquareStep.getFoodCount() <= 0)) {
            JOptionPane.showMessageDialog(null, "Your queen has ran out of food!");
            System.exit(0);
        } else if (Ants.get(0).alive == false) {
            JOptionPane.showMessageDialog(null, "Your queen has been killed by a bala ant!");
            System.exit(0);
        }
    }

    public void halvePheromone() {
        for (int y = 0; y < 27; y++) {
            for (int x = 0; x < 27; x++) {
                if (ViewArray[x][y].getPheromoneCount() > 0) {
                    ViewArray[x][y].setPheromoneLevel(ViewArray[x][y].getPheromoneCount() / 2);
                }
            }
        }
    }

    public void randomBala() {
        if (randomBalaDrop() == 1) {
            Bala bala = new Bala();
            bala.ID = id;
            bala.x = 0;
            bala.y = 0;
            ViewArray[0][0].balaID.add(bala);
            Ants.add(bala);
            ViewArray[0][0].setBalaCount(ViewArray[0][0].getBalaCount());
            id++;
        }
    }

    public void initializeGrid() {
        // Initialize 729 Nodes in a Two-Dimensional Array
        for (int y = 0; y < 27; y++) {
            for (int x = 0; x < 27; x++) {
                ViewArray[x][y] = new ColonyNodeView();
                ViewArray[x][y].x = x;
                ViewArray[x][y].y = y;
                ViewArray[x][y].setID("" + x + "," + y);
                ViewArray[x][y].setForagerCount(0);
                ViewArray[x][y].setBalaCount(0);
                ViewArray[x][y].setScoutCount(0);
                ViewArray[x][y].setSoldierCount(0);
                ViewArray[x][y].setFoodAmount(randomFoodDrop());
                ViewArray[x][y].setPheromoneLevel(0);
                View.addColonyNodeView(ViewArray[x][y], x, y);
            }
        }
    }

    public void initializeSquares() {
        // Initialize 9 squares in middle of grid and show them.
        Driver.setTime("Day 0, Turn 0");
        for (int y = 12; y < 15; y++) {
            for (int x = 12; x < 15; x++) {
                ViewArray[x][y].setID("" + x + "," + y);
                ViewArray[x][y].setForagerCount(0);
                ViewArray[x][y].setBalaCount(0);
                ViewArray[x][y].setScoutCount(0);
                ViewArray[x][y].setSoldierCount(0);
                ViewArray[x][y].setFoodAmount(0);
                ViewArray[x][y].setPheromoneLevel(0);
                ViewArray[x][y].showNode();
            }
        }
    }

    public void initializeQueenSquare() {
        // Initialize Queen in middle square
        Queen queen = new Queen();
        queen.ID = 0;
        Ants.add(queen);
        ViewArray[13][13].setQueen(true);
        ViewArray[13][13].hasQueen = true;
        for (int i = 0; i < 1; i++) {
            Soldier soldier = new Soldier();
            soldier.ID = id;
            soldier.x = 13;
            soldier.y = 13;
            ViewArray[13][13].soldierID.add(soldier);
            Ants.add(soldier);
            id++;
        }
        ViewArray[13][13].setSoldierCount(ViewArray[13][13].getSoldierCount());
        for (int i = 0; i < 1; i++) {
            Forager forager = new Forager();
            forager.ID = id;
            forager.x = 13;
            forager.y = 13;
            ViewArray[13][13].foragerID.add(forager);
            Ants.add(forager);
            id++;
        }
        ViewArray[13][13].setForagerCount(ViewArray[13][13].getForagerCount());
        for (int i = 0; i < 1; i++) {
            Scout scout = new Scout();
            scout.ID = id;
            scout.x = 13;
            scout.y = 13;
            ViewArray[13][13].scoutID.add(scout);
            Ants.add(scout);
            id++;
        }
        ViewArray[13][13].setScoutCount(ViewArray[13][13].getScoutCount());
        ViewArray[13][13].setFoodAmount(1000);
    }

}
