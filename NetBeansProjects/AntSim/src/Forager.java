
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Derek
 */
public class Forager extends Ant {

    private int prevx;
    private int nextx;
    private int prevy;
    private int nexty;
    private int max = 0;
    private int upperleftsquare = 0;
    private int uppersquare = 0;
    private int upperrightsquare = 0;
    private int middleleftsquare = 0;
    private int middlerightsquare = 0;
    private int bottomleftsquare = 0;
    private int bottomsquare = 0;
    private int bottomrightsquare = 0;
    private int food = 0;
    private ArrayList<ColonyNodeView> maxList = new ArrayList<>();
    private ArrayList<ColonyNodeView> randomList = new ArrayList<>();

    // Stack x and y to store directions of ant for return to nest mode
    Stack stackx = new Stack();
    Stack stacky = new Stack();

    public Forager() {

    }

    @Override
    public void takeTurn(ColonyNodeView[][] Array, ArrayList<Ant> Ants) {
        // 
        if (lifespan < 0) {
            Array[x][y].foragerID.remove(this);
            Array[x][y].setForagerCount(Array[x][y].getForagerCount());
        } else if (food == 0) {
            // Store directions for movement; 0 if it goes beyond the grid. 
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
            // Store pheromone count to check each node for search mode
            upperleftsquare = Array[prevx][prevy].pheromoneCount;
            uppersquare = Array[x][prevy].pheromoneCount;
            upperrightsquare = Array[nextx][prevy].pheromoneCount;
            middleleftsquare = Array[prevx][y].pheromoneCount;
            middlerightsquare = Array[nextx][y].pheromoneCount;
            bottomleftsquare = Array[prevx][nexty].pheromoneCount;
            bottomsquare = Array[x][nexty].pheromoneCount;
            bottomrightsquare = Array[nextx][nexty].pheromoneCount;

            // If all squares don't contain pheromone or is the initial queen square, move randomly. After the ants drop food at the queen square I have it so they move randomly after so it helps prevent looping.
            if (upperleftsquare == 0 && uppersquare == 0 && upperrightsquare == 0 && middleleftsquare == 0 && middlerightsquare == 0 && bottomleftsquare == 0
                    && bottomsquare == 0 && bottomrightsquare == 0 || Array[x][y].hasQueen) {

                // Store directional movement in array list then choose randomly
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
                
                // Checks to make sure ant doesn't move to square it was previously on
                if (stackx.empty() == false && stacky.empty() == false) {
                    while (randomList.size() > 1 && xmove == (Integer) stackx.peek() && ymove == (Integer) stacky.peek()) {
                        Node = Simulation.randomNode(randomList);
                        xmove = Node.x;
                        ymove = Node.y;
                    }
                }

                // Move pattern
                Array[xmove][ymove].foragerID.add(this);
                Array[xmove][ymove].setForagerCount(Array[xmove][ymove].getForagerCount());
                Array[x][y].foragerID.remove(this);
                Array[x][y].setForagerCount(Array[x][y].getForagerCount());

                // Increase food of queen
                if (Array[xmove][ymove].getFoodCount() > 0 && Array[xmove][ymove].hasQueen == false) {
                    this.food = 1;
                    Array[xmove][ymove].setFoodAmount(Array[xmove][ymove].getFoodCount() - 1);
                    Array[xmove][ymove].setFood();
                }

                stackx.push(x);
                stacky.push(y);

                x = xmove;
                y = ymove;

                randomList.clear();

                // Check each square for pheremone level. If so, store max, check multiple squares with max, store in ArrayList, then choose randomly.
            } else if (upperleftsquare > 0 || uppersquare > 0 || upperrightsquare > 0 || middleleftsquare > 0 || middlerightsquare > 0 || bottomleftsquare > 0
                    || bottomsquare > 0 || bottomrightsquare > 0) {
                if (upperleftsquare > max) {
                    max = upperleftsquare;
                }

                if (uppersquare > max) {
                    max = uppersquare;
                }
                if (upperrightsquare > max) {
                    max = upperrightsquare;
                }
                if (middleleftsquare > max) {
                    max = middleleftsquare;
                }
                if (middlerightsquare > max) {
                    max = middlerightsquare;
                }
                if (bottomleftsquare > max) {
                    max = bottomleftsquare;
                }
                if (bottomsquare > max) {
                    max = bottomsquare;
                }
                if (bottomrightsquare > max) {
                    max = bottomrightsquare;
                }

                if (bottomrightsquare == max) {
                    maxList.add(Array[nextx][nexty]);
                }

                if (bottomsquare == max) {
                    maxList.add(Array[x][nexty]);
                }

                if (bottomleftsquare == max) {
                    maxList.add(Array[prevx][nexty]);
                }

                if (middlerightsquare == max) {
                    maxList.add(Array[nextx][y]);
                }

                if (middleleftsquare == max) {
                    maxList.add(Array[prevx][y]);
                }

                if (upperrightsquare == max) {
                    maxList.add(Array[nextx][prevy]);
                }

                if (uppersquare == max) {
                    maxList.add(Array[x][prevy]);
                }

                if (upperleftsquare == max) {
                    maxList.add(Array[prevx][prevy]);
                }

                ColonyNodeView nextNode = Simulation.randomNode(maxList);
                xmove = nextNode.x;
                ymove = nextNode.y;

                // Checks to prevent looping and going to original square it was on. 
                if (stackx.empty() == false && stacky.empty() == false) {
                    if  (maxList.size() > 0 && xmove == (Integer) stackx.peek() && ymove == (Integer) stacky.peek()) {
                        if (Array[prevx][prevy].isVisible == true && (Array[prevx][prevy].x != (Integer)stackx.peek() && Array[prevx][prevy].y != (Integer)stacky.peek())); {
                            randomList.add(Array[prevx][prevy]);
                        }
                        if (Array[x][prevy].isVisible == true && (Array[x][prevy].x != (Integer)stackx.peek() && Array[x][prevy].y != (Integer)stacky.peek())) {
                            randomList.add(Array[x][prevy]);
                        }
                        if (Array[nextx][prevy].isVisible == true && (Array[nextx][prevy].x != (Integer)stackx.peek() && Array[nextx][prevy].y != (Integer)stacky.peek())) {
                            randomList.add(Array[nextx][prevy]);
                        }
                        if (Array[prevx][y].isVisible == true && (Array[prevx][y].x != (Integer)stackx.peek() && Array[prevx][y].y != (Integer)stacky.peek())) {
                            randomList.add(Array[prevx][y]);
                        }
                        if (Array[nextx][y].isVisible == true && (Array[nextx][y].x != (Integer)stackx.peek() && Array[nextx][y].y != (Integer)stacky.peek())) {
                            randomList.add(Array[nextx][y]);
                        }
                        if (Array[prevx][nexty].isVisible == true && (Array[prevx][nexty].x != (Integer)stackx.peek() && Array[prevx][nexty].y != (Integer)stacky.peek())) {
                            randomList.add(Array[prevx][nexty]);
                        }
                        if (Array[x][nexty].isVisible == true && (Array[x][nexty].x != (Integer)stackx.peek() || Array[x][nexty].y != (Integer)stacky.peek())) {
                            randomList.add(Array[x][nexty]);
                        }
                        if (Array[nextx][nexty].isVisible == true && (Array[nextx][nexty].x != (Integer)stackx.peek() || Array[nextx][nexty].y != (Integer)stacky.peek())) {
                            randomList.add(Array[nextx][nexty]);
                        }

                        nextNode = Simulation.randomNode(randomList);
                        xmove = nextNode.x;
                        ymove = nextNode.y;

                        randomList.clear();
                    }
                }
                

                nextNode.foragerID.add(this);
                nextNode.setForagerCount(nextNode.getForagerCount());
                Array[x][y].foragerID.remove(this);
                Array[x][y].setForagerCount(Array[x][y].getForagerCount());

                stackx.push(x);
                stacky.push(y);

                if (nextNode.getFoodCount() > 0 && nextNode.hasQueen == false) {
                    this.food = 1;
                    nextNode.setFoodAmount(nextNode.getFoodCount() - 1);
                    nextNode.setFood();
                }

                x = nextNode.x;
                y = nextNode.y;

                maxList.clear();
                max = 0;
            }
        } else if (food == 1) {
            // If ant contains food it drops off pheremone on square, pops the stack and moves to back to queen square with original movement history.
            if (Array[x][y].getPheromoneCount() < 1000 && Array[x][y].hasQueen == false) {
                Array[x][y].setPheromoneLevel(Array[x][y].getPheromoneCount() + 10);
            }
            int xSt = (Integer) stackx.pop();
            int ySt = (Integer) stacky.pop();

            Array[xSt][ySt].foragerID.add(this);
            Array[xSt][ySt].setForagerCount(Array[xSt][ySt].getForagerCount());
            Array[x][y].foragerID.remove(this);
            Array[x][y].setForagerCount(Array[x][y].getForagerCount());

            x = xSt;
            y = ySt;

            if (Array[xSt][ySt].hasQueen) {
                Array[13][13].setFoodAmount(Array[13][13].getFoodCount() + 1);
                food = 0;
            }
        }
        lifespan -= 1;
    }

}
