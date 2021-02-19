package tictactoe;

import java.util.Scanner;

public class Main {
    static char[][] field = new char[3][3];
    static Scanner scanner = new Scanner(System.in);
    static GameState gamestate = GameState.NEXT_MOVE;
    static boolean nextTurn = true;
    static int turn = 0;

    public static void main(String[] args) {
        fill();
        while (nextTurn && turn < 9) {
            answer();
            move('X');
            turn++;
            check();
            if (nextTurn && turn < 9) {
                answer();
                move('O');
                turn++;
                check();
            }
        }
        answer();
    }

    private static void move(char player) {
        String move;
        int r;
        int c;
        System.out.println("Enter the coordinates: ");
        move = scanner.nextLine();
        move = move.replace(" ", "");

        if (move.length() != 2 || !(Character.isDigit(move.charAt(0))) || !(Character.isDigit(move.charAt(1)))) {
            System.out.println("Wrong coordinates! (length or !digit)");
            move(player);
            return;
        }

        r = Character.getNumericValue(move.charAt(1));
        c = Character.getNumericValue(move.charAt(0));

        if (r > 3 || c > 3 || r < 1 || c < 1) {
            System.out.println("Wrong coordinates! (> 3)");
            move(player);
            return;
        }

        if (field[3 - r][c - 1] == ' ') {
            field[3 - r][c - 1] = player;
        } else {
            System.out.println("This cell is occupied! Choose another one!");
            move(player);
        }
    }

    private static void check() {
        int x = 0;
        int o = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j] == 'X') {
                    x++;
                } else if (field[i][j] == 'O') {
                    o++;
                }
            }
        }
        if (Math.abs(x-o) > 1) {
            gamestate = GameState.IMPOSSIBLE;
        } else {
            whoWin();
        }
    }

    private static void whoWin() {
        horizon();
        vertical();
        slash();
        backslash();

        if (gamestate == GameState.NEXT_MOVE) {
            gamestate = GameState.DRAW;
        }

    }

    private static void slash() {
        if (field[2][0] == 'X' && field[1][1] == 'X'
                && field[0][2] == 'X' && gamestate != GameState.IMPOSSIBLE) {
            if (gamestate == GameState.O_WINS) {
                gamestate = GameState.IMPOSSIBLE;
            } else {
                gamestate = GameState.X_WINS;
                nextTurn = false;
                return;
            }
        }
        if (field[2][0] == 'O' && field[1][1] == 'O'
                && field[0][2] == 'O' && gamestate != GameState.IMPOSSIBLE) {
            if (gamestate == GameState.X_WINS) {
                gamestate = GameState.IMPOSSIBLE;
            } else {
                gamestate = GameState.O_WINS;
                nextTurn = false;
            }
        }
    }

    private static void backslash() {
        if (field[0][0] == 'X' && field[1][1] == 'X'
                && field[2][2] == 'X' && gamestate != GameState.IMPOSSIBLE) {
            if (gamestate == GameState.O_WINS) {
                gamestate = GameState.IMPOSSIBLE;
            } else {
                gamestate = GameState.X_WINS;
                nextTurn = false;
                return;
            }
        }
        if (field[0][0] == 'O' && field[1][1] == 'O'
                && field[2][2] == 'O' && gamestate != GameState.IMPOSSIBLE) {
            if (gamestate == GameState.X_WINS) {
                gamestate = GameState.IMPOSSIBLE;
            } else {
                gamestate = GameState.O_WINS;
                nextTurn = false;
            }
        }
    }

    private static void vertical() {
        for (int i = 0; i < 3; i++) {
            if (field[0][i] == 'X' && field[1][i] == 'X'
                    && field[2][i] == 'X' && gamestate != GameState.IMPOSSIBLE) {
                if (gamestate == GameState.O_WINS) {
                    gamestate = GameState.IMPOSSIBLE;
                    break;
                } else {
                    gamestate = GameState.X_WINS;
                    nextTurn = false;
                    return;
                }
            }
            if (field[0][i] == 'O' && field[1][i] == 'O'
                    && field[2][i] == 'O' && gamestate != GameState.IMPOSSIBLE) {
                if (gamestate == GameState.X_WINS) {
                    gamestate = GameState.IMPOSSIBLE;
                    break;
                } else {
                    gamestate = GameState.O_WINS;
                    nextTurn = false;
                    return;
                }
            }
        }
    }

    private static void horizon() {
        for (int i = 0; i < 3; i++) {
            if (field[i][0] == 'X' && field[i][1] == 'X'
                    && field[i][2] == 'X' && gamestate != GameState.IMPOSSIBLE) {
                if (gamestate == GameState.O_WINS) {
                    gamestate = GameState.IMPOSSIBLE;
                    break;
                } else {
                    gamestate = GameState.X_WINS;
                    nextTurn = false;
                    return;
                }
            }
            if (field[i][0] == 'O' && field[i][1] == 'O'
                    && field[i][2] == 'O' && gamestate != GameState.IMPOSSIBLE) {
                if (gamestate == GameState.X_WINS) {
                    gamestate = GameState.IMPOSSIBLE;
                    break;
                } else {
                    gamestate = GameState.O_WINS;
                    nextTurn = false;
                    return;
                }
            }
        }
    }

    private static void fill() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = ' ';
            }
        }
    }

    private static void answer() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
        System.out.print(gamestate.toString());
    }

    enum GameState{
        NEXT_MOVE(""),
        DRAW("Draw"),
        X_WINS("X wins"),
        O_WINS("O wins"),
        IMPOSSIBLE("Impossible");

        String state;

        GameState(String state) {
            this.state = state;
        }

        @Override
        public String toString() {
            return this.state;
        }
    }
}
