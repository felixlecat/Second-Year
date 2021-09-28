#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define WALL 'w'
#define POTION '#'
#define NEEDED_POTIONS 3

struct maze{
    char **a; // 2D array supporting maze
    unsigned int w; // width
    unsigned int h; // height
    unsigned int cell_size; // number of chars per cell; walls are 1 char
};

/**
 * Represents a cell in the 2D matrix.
 */
struct cell{
    unsigned int x;
    unsigned int y;
};

/**
 * Stack structure using a list of cells.
 * At element 0 in the list we have NULL.
 * Elements start from 1 onwards.
 * top_of_stack represents the index of the top of the stack
 * in the cell_list.
 */
struct stack{
    struct cell *cell_list;
    unsigned int top_of_stack;
    unsigned int capacity;
};

/**
 * Initialises the stack by allocating memory for the internal list
 */
void init_stack(struct stack *stack, unsigned int capacity){
    stack->cell_list = (struct cell*)malloc(sizeof(struct cell)*(capacity+1));
    stack->top_of_stack = 0;
    stack->capacity = capacity;
}

void free_stack(struct stack *stack){
    free(stack->cell_list);
}

/**
 * Returns the element at the top of the stack and removes it
 * from the stack.
 * If the stack is empty, returns NULL
 */
struct cell stack_pop(struct stack *stack){
    struct cell cell = stack->cell_list[stack->top_of_stack];
    if (stack->top_of_stack > 0) stack->top_of_stack --;
    return cell;
}

/**
 * Pushes an element to the top of the stack.
 * If the stack is already full (reached capacity), returns -1.
 * Otherwise returns 0.
 */
int stack_push(struct stack *stack, struct cell cell){
    if (stack->top_of_stack == stack->capacity) return -1;
    stack->top_of_stack ++;
    stack->cell_list[stack->top_of_stack] = cell;
    return 0;
}

int stack_isempty(struct stack *stack){
    return stack->top_of_stack == 0;
}

//-----------------------------------------------------------------------------

void mark_visited(struct maze *maze, struct cell cell){
    maze->a[cell.y][cell.x] = 'v';
}

/**
 * Convert a cell coordinate to a matrix index.
 * The matrix also contains the wall elements and a cell might span
 * multiple matrix cells.
 */
int cell_to_matrix_idx(struct maze *m, int cell){
    return (m->cell_size+1)*cell+(m->cell_size/2)+1;
}

/**
 * Convert maze dimension to matrix dimension.
 */
int maze_dimension_to_matrix(struct maze *m, int dimension){
    return (m->cell_size+1)*dimension+1;
}

/**
 * Returns the index of the previous cell (cell - 1)
 */
int matrix_idx_prev_cell(struct maze *m, int cell_num){
    return cell_num - (m->cell_size+1);
}

/**
 * Returns the index of the next cell (cell + 1)
 */
int matrix_idx_next_cell(struct maze *m, int cell_num){
    return cell_num + (m->cell_size+1);
}

/**
 * Returns into neighbours the unvisited neighbour cells of the given cell.
 * Returns the number of neighbours.
 * neighbours must be able to hold 4 cells.
 */
int get_available_neighbours(struct maze *maze, struct cell cell, struct cell *neighbours){
    int num_neighbrs = 0;

    // Check above
    if ((cell.y > cell_to_matrix_idx(maze,0)) && (maze->a[matrix_idx_prev_cell(maze, cell.y)][cell.x] != 'v')){
        neighbours[num_neighbrs].x = cell.x;
        neighbours[num_neighbrs].y = matrix_idx_prev_cell(maze, cell.y);
        num_neighbrs ++;
    }

    // Check left
    if ((cell.x > cell_to_matrix_idx(maze,0)) && (maze->a[cell.y][matrix_idx_prev_cell(maze, cell.x)] != 'v')){
        neighbours[num_neighbrs].x = matrix_idx_prev_cell(maze, cell.x);
        neighbours[num_neighbrs].y = cell.y;
        num_neighbrs ++;
    }

    // Check right
    if ((cell.x < cell_to_matrix_idx(maze,maze->w-1)) && (maze->a[cell.y][matrix_idx_next_cell(maze, cell.x)] != 'v')){
        neighbours[num_neighbrs].x = matrix_idx_next_cell(maze, cell.x);
        neighbours[num_neighbrs].y = cell.y;
        num_neighbrs ++;
    }

    // Check below
    if ((cell.y < cell_to_matrix_idx(maze,maze->h-1)) && (maze->a[matrix_idx_next_cell(maze, cell.y)][cell.x] != 'v')){
        neighbours[num_neighbrs].x = cell.x;
        neighbours[num_neighbrs].y = matrix_idx_next_cell(maze, cell.y);
        num_neighbrs ++;
    }

    return num_neighbrs;
}


/**
 * Removes a wall between two cells.
 */
void remove_wall(struct maze *maze, struct cell a, struct cell b){
    int i;
    if (a.y == b.y){
        for (i=0;i<maze->cell_size;i++)
            maze->a[a.y-maze->cell_size/2+i][a.x-(((int)a.x-(int)b.x))/2] = ' ';
    }else{
        for (i=0;i<maze->cell_size;i++)
            maze->a[a.y-(((int)a.y-(int)b.y))/2][a.x-maze->cell_size/2+i] = ' ';
    }
}

/**
 * Fill all matrix elements corresponding to the cell
 */
void fill_cell(struct maze *maze, struct cell c, char value){
    int i,j;
    for (i=0;i<maze->cell_size;i++)
        for (j=0;j<maze->cell_size;j++)
            maze->a[c.y-maze->cell_size/2+i][c.x-maze->cell_size/2+j] = value;
}

/**
 * This function generates a maze of width x height cells.
 * Each cell is a square of cell_size x cell_size characters.
 * The maze is randomly generated based on the supplied rand_seed.
 * Use the same rand_seed value to obtain the same maze.
 *
 * The function returns a struct maze variable containing:
 * - the maze represented as a 2D array (field a)
 * - the width (number of columns) of the array (field w)
 * - the height (number of rows) of the array (field h).
 * In the array, walls are represented with a 'w' character, while
 * pathways are represented with spaces (' ').
 * The edges of the array consist of walls, with the exception
 * of two openings, one on the left side (column 0) and one on
 * the right (column w-1) of the array. These should be used
 * as entry and exit.
 */
struct maze generate_maze(unsigned int width, unsigned int height, unsigned int cell_size, int rand_seed){
    int row, col, i;
    struct stack stack;
    struct cell cell;
    struct cell neighbours[4];  // to hold neighbours of a cell
    int num_neighbs;
    struct maze maze;
    maze.w = width;
    maze.h = height;
    maze.cell_size = cell_size;
    maze.a = (char**)malloc(sizeof(char*)*maze_dimension_to_matrix(&maze, height));

    // Initialise RNG
    srandom(rand_seed);

    // Initialise stack
    init_stack(&stack, width*height);

    // Initialise the matrix with walls
    for (row=0;row<maze_dimension_to_matrix(&maze, height);row++){
        maze.a[row] = (char*)malloc(maze_dimension_to_matrix(&maze, width));
        memset(maze.a[row], WALL, maze_dimension_to_matrix(&maze, width));
    }

    // Select a random position on a border.
    // Border means x=0 or y=0 or x=2*width+1 or y=2*height+1
    cell.x = cell_to_matrix_idx(&maze,0);
    cell.y = cell_to_matrix_idx(&maze,random()%height);
    mark_visited(&maze, cell);
    stack_push(&stack, cell);

    while (! stack_isempty(&stack)){
        // Take the top of stack
        cell = stack_pop(&stack);
        // Get the list of non-visited neighbours
        num_neighbs = get_available_neighbours(&maze, cell, neighbours);
        if (num_neighbs > 0){
            struct cell next;
            // Push current cell on the stack
            stack_push(&stack, cell);
            // Select one random neighbour
            next = neighbours[random()%num_neighbs];
            // Mark it visited
            mark_visited(&maze, next);
            // Break down the wall between the cells
            remove_wall(&maze, cell, next);
            // Push new cell on the stack
            stack_push(&stack, next);
        }
    }

    // Finally, replace 'v' with spaces
    for (row=0;row<maze_dimension_to_matrix(&maze, height);row++)
        for (col=0;col<maze_dimension_to_matrix(&maze, width);col++)
            if (maze.a[row][col] == 'v'){
                cell.y = row;
                cell.x = col;
                fill_cell(&maze, cell, ' ');
            }

    // Select an entry point in the top right corner.
    // The first border cell that is available.
    for (row=0;row<maze_dimension_to_matrix(&maze, height);row++)
        if (maze.a[row][1] == ' ') { maze.a[row][0] = ' '; break; }

    // Select the exit point
    for (row=maze_dimension_to_matrix(&maze, height)-1;row>=0;row--)
        if (maze.a[row][cell_to_matrix_idx(&maze,width-1)] == ' ') {
            maze.a[row][maze_dimension_to_matrix(&maze, width)-1] = ' ';
            break;
        }

    maze.w = maze_dimension_to_matrix(&maze, maze.w);
    maze.h = maze_dimension_to_matrix(&maze, maze.h);

    // Add the potions inside the maze at three random locations
    for (i=0;i<NEEDED_POTIONS;i++){
        do{
            row = random()%(maze.h-1);
            col = random()%(maze.w-1);
        }while (maze.a[row][col] != ' ');
        maze.a[row][col] = POTION;
    }

    return maze;
}

// Index to a maze location
struct maze_location{
    int x_pos;
    int y_pos;
};

// Print maze function
void print_maze(struct maze myMaze, int potionsFound, int fogRadius, struct maze_location currentPos){
    int row, col;
    int startRow, endRow, startCol, endCol;

    // If fog radius is 0
    if(fogRadius == 0){
        for (row = 0; row < myMaze.h; row++){
            for (col = 0; col < myMaze.w; col++){
                printf("%c", myMaze.a[row][col]);
            }
            printf("\n");
        }
    }
    // If fog radius is more than 0
    else if(fogRadius > 0){
    	// Assign values to the start and the end of the rows and columns to be printed
        startRow = currentPos.x_pos - fogRadius;
        startCol = currentPos.y_pos - fogRadius;
        endRow = currentPos.x_pos + fogRadius;
        endCol = currentPos.y_pos + fogRadius;

        // Assign fixed values if they exceed the maze dimensions
        if(startRow < 0){
            startRow = 0;
        }
        if(startCol < 0){
            startCol = 0;
        }
        if(endRow > myMaze.h){
            endRow = myMaze.h;
        }
        if(endCol > myMaze.w){
            endCol = myMaze.w;
        }

        // Print out the maze according to the values
        for(row = startRow; row < endRow; row++){
            for(col = startCol; col < endCol; col++){
                printf("%c", myMaze.a[row][col]);
            }
            printf("\n");
        }

    }

};


void main()
{
    int width, height, cellSize, randomSeed, fogRadius;
    int i, j, potion;
    int potionsFound = 0;
    struct maze myMaze;

    struct maze_location initialPos;
    struct maze_location currentPos;
    struct maze_location finalPos;
    char movement;

    // Introduction and instructions of maze
    printf("Welcome to my maze! Use characters 'wasd' to move the avatar.");

    // Assign maze width
    printf("\nEnter maze width: ");
    scanf("%d", &width);

    // Assign maze height
    printf("Enter maze height: ");
    scanf("%d", &height);

    // Assign cell size - even integer does not have a viable path to complete the maze
    printf("Enter cell size(odd integer only!): ");
    scanf("%d", &cellSize);

    // Assign random seed
    printf("Enter random seed: ");
    scanf("%d", &randomSeed);

    // Assign fog radius - 0 for none and >0 would apply fog of sight
    printf("Enter fog radius: ");
    scanf("%d", &fogRadius);

    printf("\n");

    // Generate maze
    myMaze = generate_maze(width, height, cellSize, randomSeed);

    // Assigning initial position and current position, with the avatar '@' at the starting position
    for (i = 0; i < myMaze.h; i++){
        if(myMaze.a[i][0] != WALL){
            myMaze.a[i][0] = '@';
            initialPos.x_pos = i;
            initialPos.y_pos = 0;
            currentPos.x_pos = i;
            currentPos.y_pos = 0;
            break;
        }
    }

    // Assigning final position - exit of the maze
    for (j = 0; j < myMaze.h; j++){
        if(myMaze.a[j][myMaze.w - 1] != WALL){
            finalPos.x_pos = j;
            finalPos.y_pos = myMaze.w - 1;
            break;
        }
    }

    // (draft)Check if maze is printed as assigned
    //print_maze(myMaze, potionsFound, fogRadius, currentPos);

    // User navigates through the maze
    while(1){
    	// If avatar reach the final position(exit) with all potions collected
        if(currentPos.x_pos == finalPos.x_pos && currentPos.y_pos == finalPos.y_pos && potionsFound == NEEDED_POTIONS){
            print_maze(myMaze, potionsFound, 0, currentPos);
            printf("Congratulation, you win the maze!");
            break;
        }

        // Read keyboard input from user for avatar movement
        scanf("%c", &movement);
        printf("\n");

        // Display potions found in maze
        if(potionsFound < NEEDED_POTIONS){
            printf("Potions collected: ");
            for(potion = 0; (NEEDED_POTIONS - potionsFound) - potion; potion++){
                putchar(POTION);
            }
            printf("\n");
        }
        else{
            printf("Great, you have collected all the potions! Now head to the exit.\n");
        }

        // Assign movement instructions for characters 'w', 'a', 's', 'd'
        // Comments only in one character as they share the same function besides different movement direction
        switch(movement){
            case 'w':
            	// If the index above is not a wall
                if(myMaze.a[currentPos.x_pos - 1][currentPos.y_pos] != WALL){
                	// If the index above is a potion
                    if(myMaze.a[currentPos.x_pos - 1][currentPos.y_pos] == POTION){
                        potionsFound++;
                    }
                    // If the index below is not a wall nor a potion - then assign the index to be a blank
                    if(myMaze.a[currentPos.x_pos + 1][currentPos.y_pos] != WALL && myMaze.a[currentPos.x_pos + 1][currentPos.y_pos] != POTION){
                        myMaze.a[currentPos.x_pos + 1][currentPos.y_pos] = ' ';
                    }
                    // Assign the previous index to be a blank
                    myMaze.a[currentPos.x_pos][currentPos.y_pos] = ' ';
                    // Assign the current index to be the avatar
                    myMaze.a[currentPos.x_pos - 1][currentPos.y_pos] = '@';
                    // Update the current index
                    currentPos.x_pos = currentPos.x_pos - 1;
                }
                // Print out the maze
                print_maze(myMaze, potionsFound, fogRadius, currentPos);
                break;

            case 'a':
                if(myMaze.a[currentPos.x_pos][currentPos.y_pos - 1] != WALL){
                    if(myMaze.a[currentPos.x_pos][currentPos.y_pos - 1] == POTION){
                        potionsFound++;
                    }
                    if(myMaze.a[currentPos.x_pos][currentPos.y_pos + 1] != WALL && myMaze.a[currentPos.x_pos][currentPos.y_pos + 1] != POTION){
                        myMaze.a[currentPos.x_pos][currentPos.y_pos + 1] = ' ';
                    }
                    if(currentPos.x_pos == initialPos.x_pos && currentPos.y_pos - 1 == initialPos.y_pos - 1){
                        print_maze(myMaze, potionsFound, fogRadius, currentPos);
                        printf("This exit is blocked.");
                        break;
                    }
                    myMaze.a[currentPos.x_pos][currentPos.y_pos] = ' ';
                    myMaze.a[currentPos.x_pos][currentPos.y_pos - 1] = '@';
                    currentPos.y_pos = currentPos.y_pos - 1;
                }
                print_maze(myMaze, potionsFound, fogRadius, currentPos);
                break;


            case 's':
                if(myMaze.a[currentPos.x_pos + 1][currentPos.y_pos] != WALL){
                    if(myMaze.a[currentPos.x_pos + 1][currentPos.y_pos] == POTION){
                        potionsFound++;
                    }
                    if(myMaze.a[currentPos.x_pos - 1][currentPos.y_pos] != WALL && myMaze.a[currentPos.x_pos - 1][currentPos.y_pos] != POTION){
                        myMaze.a[currentPos.x_pos - 1][currentPos.y_pos] = ' ';
                    }
                    myMaze.a[currentPos.x_pos][currentPos.y_pos] = ' ';
                    myMaze.a[currentPos.x_pos + 1][currentPos.y_pos] = '@';
                    currentPos.x_pos = currentPos.x_pos + 1;
                }
                print_maze(myMaze, potionsFound, fogRadius, currentPos);
                break;


            case 'd':
                if(myMaze.a[currentPos.x_pos][currentPos.y_pos + 1] != WALL){
                    if(myMaze.a[currentPos.x_pos][currentPos.y_pos + 1] == POTION){
                        potionsFound++;
                    }
                    if(myMaze.a[currentPos.x_pos][currentPos.y_pos - 1] != WALL && myMaze.a[currentPos.x_pos][currentPos.y_pos - 1] != POTION){
                        myMaze.a[currentPos.x_pos][currentPos.y_pos - 1] = ' ';
                    }
                    if(currentPos.x_pos == finalPos.x_pos && currentPos.y_pos + 1 == finalPos.y_pos && potionsFound != NEEDED_POTIONS){
                        print_maze(myMaze, potionsFound, fogRadius, currentPos);
                        printf("Please collect all the potions before exiting.");
                        break;
                    }
                    myMaze.a[currentPos.x_pos][currentPos.y_pos] = ' ';
                    myMaze.a[currentPos.x_pos][currentPos.y_pos + 1] = '@';
                    currentPos.y_pos = currentPos.y_pos + 1;
                }
                print_maze(myMaze, potionsFound, fogRadius, currentPos);
                break;

            default:
                print_maze(myMaze, potionsFound, fogRadius, currentPos);
                break;

        }

    }


}
