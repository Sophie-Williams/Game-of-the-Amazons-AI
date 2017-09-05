A minimax algorithm based bot for the Game of the Amazons
-----

![alt text](http://www.solitairelaboratory.com/amazons2.gif "Game of the Amazons")

Approach Used
-----
1. **Game Tree & Game Tree Nodes** are constructed in order to store possible states of the game for evaluation

2. **Minimax Algorithm** used in order to pick the best possible move. Done by maximizing
your best possible move, while minizming the opponent's best move.

3. **Alpha Beta Pruning** in order to eliminte nodes that do not need to be evaluated due to their
ranking from the heuristic value given by the [Relative Territory Heuristic](http://library.msri.org/books/Book42/files/muller.pdf)

4. **Frontier Expansion/Trimming** to allow for better moves generated by Minimax, frontier expansion and trimming is performed to populate, or reduce the amount of game tree nodes present within the game tree.

Heuristic
-----
* The [Relative Territory Heuristic](http://library.msri.org/books/Book42/files/muller.pdf) was used as the heuristic function. This heuristic decides who owns a block on the game board by looking at which player can reach a square faster with a queen, and ignores the arrow shots while performing the search. The heuristic follows this structure:
  1. For each block, B, on the gameboard, compare `dist_black = dist(B, Black)` and `dist_white = dist(B, White)` where Black represents the black colored game piece, and White represents the white colored game piece.
  2. If dist_black is greater than dist_white, Black owns that block of the board
  3. Else if, dist_white is greater than dist_black, White owns that block of the board
  4. Else, the block is neutral

Reflection
-----
* Using an Iterative Deepening approach proved to be much more effective due to hardware constraints with the minimax approach. Due to the exponential increase in nodes at each level, the minimax approach struggled in the early stages of the game to analyze anything beyond depth = 2 with 8GB of ram. The implementation for Iterative Deepening can be found in `src/IterativeDeepening.java`
