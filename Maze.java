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

        robotRow = 2;
        robotCol = 1;
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
        Robot r = new Robot();
        r.navigate();
    }
}
class Robot {
    String[][] sight = new String[2000][2000];
    ArrayStack<String> stack;

    private void initSight() {
        for (int i = 0; i < 2000; i++) {
            for (int j = 0; j < 2000; j++) {
                sight[i][j] = "";
            }
        }
    }

    private void clearStack() {
        if (stack.isEmpty() == false) {
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

        String direction = "RIGHT"; // Right - Left - Down - Up = Right - Down - Left - Up
        result = maze.go(direction);
        System.out.println(direction);
        exploreCol++;

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

            // Calculate direction: Right - Left - Down - Up order
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
            result = maze.go(direction);
        }
    }
}

// Array-based implementation of stack
class ArrayStack<T> {
    private int size;
    private static int MAX_SIZE = 100 * 100;
    private T[] items;

    public ArrayStack() {
        size = 0;
        items = (T[]) new Object[MAX_SIZE];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean push(T item) {
        // make sure the stack still have empty slot
        if (size < MAX_SIZE) {
            items[size] = item;
            size++;
            return true;
        }
        return false;
    }

    public boolean pop() {
        // make sure the stack is not empty
        if (isEmpty()) {
            return false;
        }
        size--;
        return true;
    }

    public T peek() {
        // make sure the stack is not empty
        if (isEmpty()) {
            return null;
        }
        return items[size - 1];
    }
}