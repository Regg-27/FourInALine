FourInALine — Reginald "Reggie" William Meeks II

Compile:
javac FourInALine.java

Run:
java FourInALine

Output:

8x8 board printed to standard output after each move
Human plays as O, computer plays as X
Moves are entered in letter number format such as D4 where the letter is the row (A to H) and the number is the column (1 to 8)
Game ends when a player gets 4 in a row or column
Algorithms:
Alpha Beta Pruning  – The computer searches the game tree by maintaining
two values, alpha and beta, representing the best outcomes found for each
player. Branches that cannot affect the final decision are pruned, allowing
the search to go deeper within the time limit.
Iterative Deepening Search  – The computer searches to increasing depths
starting at 1, saving the best move after each completed depth. When time
expires the best move from the last fully completed depth is played.

Evaluation Function:
A sliding window of 4 cells is applied across all rows and columns.
Each window is scored based on its contents:
3 X's and 1 empty  = +100
2 X's and 2 empty  = +10
3 O's and 1 empty  = 100
2 O's and 2 empty  = 10
Mixed windows score 0. Positive scores favor the computer, negative favor the human.

Board representation:
8x8 two dimensional character array.
Each cell holds either a dash for empty, O for human, or X for computer.