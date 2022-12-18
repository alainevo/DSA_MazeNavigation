public class Maze {
    int rows;
    int cols;
    String[] map;
    int robotRow;
    int robotCol;
    int steps;

    public Maze() {
        // Note: in my real test, I will create much larger
        // and more complicated map
        rows = 4;
        cols = 5;
        map = new String[rows];
        map[0] = ".....";
        map[1] = ".   X";
        map[2] = ".   .";
        map[3] = ".....";
        robotRow = 2;
        robotCol = 1;

        // rows = 4;
        // cols = 7;
        // map = new String[rows];
        // map[0] = ".......";
        // map[1] = ".  .  .";
        // map[2] = ".X   ..";
        // map[3] = ".......";
        // robotRow = 1;
        // robotCol = 5;

        // rows = 5;
        // cols = 5;
        // map = new String[rows];
        // map[0] = ".....";
        // map[1] = ".   X";
        // map[2] = ". . .";
        // map[3] = ". . .";
        // map[4] = ".....";
        // robotRow = 2;
        // robotCol = 1;

        // rows = 7;
        // cols = 7;
        // map = new String[rows];
        // map[0] = ".......";
        // map[1] = "..  . .";
        // map[2] = ". .  ..";
        // map[3] = ". .  ..";
        // map[4] = ". . . .";
        // map[5] = ".X    .";
        // map[6] = ".......";
        // robotRow = 3;
        // robotCol = 3;
                
        // rows = 3;
        // cols = 5;
        // map = new String[rows];
        // map[0] = ".....";
        // map[1] = ".   X";
        // map[2] = ".....";

        // Test Case 2
        // rows = 30;
        // map = new String[rows];
        // cols = 100;
        // map[0] = "....................................................................................................";
        // map[1] = ".                                              ..                                                  .";
        // map[2] = ".                                              ..                          ..                      .";
        // map[3] = ".                                              ..                          ..                      .";
        // map[4] = ".      ..............                          ..                  ...     ..                      .";
        // map[5] = ".            .............                     ..                  ...     ..   ....................";
        // map[6] = ".                                              ..                  ...     ..   ....................";
        // map[7] = ".       ....  .   .  .  .....                                                   ...     ..         .";
        // map[8] = ".       .  .  .. ..  .    .            ........                     .........   ...     ..         .";
        // map[9] = ".       ....  . . .  .    .            ........                    .........    ...     ..         .";
        // map[10] = ".       ..    .   .  .    .            ........                                 ...     ..         .";
        // map[11] = ".       . .   .   .  .    .        .   ........                        ..                       ....";
        // map[12] = ".       .  .  .   .  .    .        .   ........                        ..                       ....";
        // map[13] = ".                                  .                                   ..  ..           .......    .";
        // map[14] = ".    ..       ..    ..       ..    .        ..      ..    ...          ..  ..           .......    .";
        // map[15] = ".    ....     ..    ....     ..    .        ..      ..    .....        ..  ..                      .";
        // map[16] = ".    .. ..    ..    .. ..    ..        .    ..      ..    ..  ...  ..      ..                      .";
        // map[17] = ".    ..  ..   ..    ..  ..   ..   .......   ..........    ..   ..  ..      ..                      .";
        // map[18] = ".    ..   ..  ..    ..   ..  ..   .......   ..........    ..   ..  ..      ..                      .";
        // map[19] = ".    ..    .. ..    ..    .. ..        .    ..      ..    ..  ...          ..                      .";
        // map[20] = ".    ..     ....    ..     ....             ..      ..    .....            ..    ..........        .";
        // map[21] = ".    ..       ..    ..       ..             ..      ..    ...              ..    ..........        .";
        // map[22] = ".                                                                          ..                      .";
        // map[23] = ".      .            .           .            .                             ..          X           .";
        // map[24] = ".      .     .      .           .            .                             ..                      .";
        // map[25] = ".      .     .      .                        .        .                    ..                      .";
        // map[26] = ".      .     .      .                        .        .                    ..                      .";
        // map[27] = ".      .     .      .           .            .        .                    ..                      .";
        // map[28] = ".            .                  .                     .                    ..                      .";
        // map[29] = "....................................................................................................";
        // Test Case End

        steps = 0;
    }

    public String go(String direction) {
        if (!direction.equals("UP") &&
                !direction.equals("DOWN") &&
                !direction.equals("LEFT") &&
                !direction.equals("RIGHT")) {
            // invalid direction
            steps++;
            return "false";
        }
        int currentRow = robotRow;
        int currentCol = robotCol;
        if (direction.equals("UP")) {
            currentRow--;
        } else if (direction.equals("DOWN")) {
            currentRow++;
        } else if (direction.equals("LEFT")) {
            currentCol--;
        } else {
            currentCol++;
        }

        // check the next position
        if (map[currentRow].charAt(currentCol) == 'X') {
            // Exit gate
            steps++;
            System.out.println("Steps to reach the Exit gate " + steps);
            return "win";
        } else if (map[currentRow].charAt(currentCol) == '.') {
            // Wall
            steps++;
            return "false";
        } else {
            // Space => update robot location
            steps++;
            robotRow = currentRow;
            robotCol = currentCol;
            return "true";
        }
    }

    public static void main(String[] args) {
        // Create an instance of the robot class
        Robot r = new Robot();
        r.navigate();
    }
}
class Robot {
    int mazeMaxSize = 1000;
    int sightMaxSize = 2*mazeMaxSize;
    String[][] sight = new String[sightMaxSize][sightMaxSize];
    ArrayStack<String> stack;

    private void initSight() {
        for (int i = 0; i < 2*mazeMaxSize; i++) {
            for (int j = 0; j < 2*mazeMaxSize; j++) {
                sight[i][j] = "";
            }
        }
    }

    private void clearStack() {
        while (stack.isEmpty() == false) {
            stack.pop();
        }
    }

    // A very simple implementation
    // where the robot just go randomly
    public void navigate() {
        // Create a new Maze object
        Maze maze = new Maze();
        String result = "";

        // Initialize stack for further backtracking
        stack = new ArrayStack<String>();

        // Clear array and stack to make sure the stack and array is empty at first
        initSight();
        clearStack();

        // Initialize default value
        boolean isBacktracking = false;
        int exploreRow = 1000, exploreCol = 1000; // store the position of explored cell
        int curRow = 1000, curCol = 1000; // current position of the robot
        sight[curRow][curCol] = "visited"; // mark the current cell value of robot as "visited"

        String direction = "RIGHT"; // Right - Down - Left - Up
        result = maze.go(direction);
        exploreCol++;
        System.out.println(direction);

        // DFS - Backtracking - Find the deepest path
        while (!result.equals("win")) {
            if (result.equals("false")) {
                sight[exploreRow][exploreCol] = ".";
                exploreRow = curRow;
                exploreCol = curCol;
            } else {
                curRow = exploreRow;
                curCol = exploreCol;
                sight[curRow][curCol] = "visited";
                if (isBacktracking == false) {
                    stack.push(direction);
                }
            }

            isBacktracking = false;

            // Check direction to explore: Right - Down - Left - Up order
            if (sight[curRow][curCol + 1].isEmpty()) {
                direction = "RIGHT";
                exploreCol++;
            } else if (sight[curRow + 1][curCol].isEmpty()) {
                direction = "DOWN";
                exploreRow++;
            } else if (sight[curRow][curCol - 1].isEmpty()) {
                direction = "LEFT";
                exploreCol--;
            }  else if (sight[curRow - 1][curCol].isEmpty()) {
                direction = "UP";
                exploreRow--;
            } else {
                // If the robot reaches the cell that it cannot go R, D, L or U, it will then backtrack to its previous position
                isBacktracking = true;
                direction = stack.peek();
                if (direction.equals("RIGHT")) {
                    direction = "LEFT";
                    exploreCol--;
                } else if (direction.equals("DOWN")) {
                    direction = "UP";
                    exploreRow--;
                } else if (direction.equals("LEFT")) {
                    direction = "RIGHT";
                    exploreCol++;
                } else if (direction.equals("UP")) {
                    direction = "DOWN";
                    exploreRow++;
                }
                stack.pop();
            }

            System.out.println(direction);
            // System.out.println("cur x " + curRow + " cur Y " + curCol);
            result = maze.go(direction);
        }
    }
}

// Array-based implementation of stack
class ArrayStack<T> {
    private int size;
    private static int MAX_SIZE = 1000 * 1000;
    private T[] items;

    public ArrayStack() {
        size = 0;
        items = (T[]) new Object[MAX_SIZE];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public boolean push(T item) {
        // check if the stack still have empty slot
        if (size < MAX_SIZE) {
            items[size] = item; // assign value
            size++; // increase size
            return true;
        }
        return false;
    }

    public boolean pop() {
        // check if the stack has any item to pop out or not
        if (isEmpty()) {
            return false;
        }
        size--; // decrease stack size
        return true;
    }

    public T peek() {
        // check if the stack is empty or not to guarantee it has at least an item to return
        if (isEmpty()) {
            return null;
        }
        return items[size - 1];
    }
}