"""
file: ava.py
language: python3
description: A Cassio bot which can only be described as "Beast Mode"
"""
__author__ = 'Michael Canning @ RIT CS'

from cassio_API import Player, Move, N_ROWS, N_COLS

class Ava( Player ):
    """
    The class for the Ava AI. This responds to calls from the Cassio engine. This stores the state of the game, and can
    make smart decisions about where to place pieces. As of testing the win rate is over 90%.

    ### The Strategy ###
    Ava uses a weight based system to determine where it will place a piece. First it measures the number of tiles it
    will gain from a specific move. It puts this into a list of possible moves and their weights. It picks
    the move which will grant it the most points generally.

    All moves are not equal, and so it has a built in preference for moves such as corners, and will automatically
    grab these points as they become available. The win rate increased dramatically (Over 20%) when different points
    became weighted differently.
    """

    __slots__ = "player", "board", "next_r", "next_c"

    def __init__( self, player: int ) -> None:
        """
        Initializes the player with a number, and creates a blank board to store state in.

        :param player: The player id number
        """
        super( Ava, self ).__init__()
        self.player = player
        self.board = [ [ None ] * N_COLS for _ in range( N_ROWS ) ]


    def print_my_board(self) -> None:
        """
        This is an internal function for debugging only, it prints the board as Ava sees it. This is not
        enabled by default, and will not display.
        :return:
        """
        print("### START AVA BOARD ###")
        sep_to_print = " "
        for _ in range(0, N_COLS):
            sep_to_print = sep_to_print + "----"
        print(sep_to_print)
        for x in range(0, N_ROWS):
            middle_to_print = "|"
            for y in range(0, N_COLS):
                piece = self.board[x][y]
                if piece == None:
                    piece = " "
                middle_to_print = middle_to_print + " " + piece + " |"
            print(middle_to_print)
            print(sep_to_print)
        print("### END AVA BOARD ###")
        print("")

    def your_move( self ) -> Move:
        """
        This function will call the get_best_move function and return the move Ava sees as best to the engine.
        :return: The move Ava thinks is best.
        """
        movement = self.get_best_move()
        return Move(self.player, movement[0], movement[1])


    def move_was( self, move: Move ) -> None:
        """
        This recieves a move made from the engine, and updates the board accordingly.
        :param move: The move made which needs to be updated in the internal board.
        :return:
        """
        if move.player != self.player:
            self.board[move.row][move.column] = "O"
        else:
            self.board[move.row][move.column] = "M"
        self.check_for_flips(move.row, move.column)

    def has_won( self ) -> bool:
        """
        This counts the number of spaces each player controls based on the internal board, and returns True
        if Ava won, or False if it did not.
        :return: True if Ava won, False if Ava lost
        """
        my_points = 0
        other_player_points = 0
        for x in self.board:
            for y in x:
                if y == "M":
                    my_points += 1
                else:
                    other_player_points += 1
        if my_points > other_player_points:
            return True
        else:
            return False


    def check_for_flips(self, start_x, start_y) -> None:
        """
        This checks around a flipped piece to see if any pieces around it need to be flipped based
        on the game rules.
        :param start_x: The X coordinate of the flipped point
        :param start_y: The Y coordinate of the flipped point
        :return:
        """
        piece_changed_to = self.board[start_x][start_y]
        for x_direction, y_direction in [[0, 1], [1, 1], [1, 0], [1, -1], [0, -1], [-1, -1], [-1, 0], [-1, 1]]:
            x = start_x
            y = start_y
            first_move = True
            while(self.tile_is_on_board(x + x_direction, y + y_direction)):
                x += x_direction
                y += y_direction
                if self.board[x][y] == None:
                    break
                if first_move and self.board[x][y] == piece_changed_to:
                    break
                first_move = False
                if self.board[x][y] == piece_changed_to:
                    while(x != start_x or y != start_y):
                        x -= x_direction
                        y -= y_direction
                        self.board[x][y] = piece_changed_to
                    break

    def tile_is_on_board(self, x, y) -> bool:
        """
        Checks to see if a given coordinate pair is a valid point on the board
        :param x: The X coordinate of the point to check
        :param y: The Y coordinate of the point to check
        :return: True is the point exists on the board, False if it does not
        """
        if x >= 0 and y >= 0:
            if x < N_ROWS and y < N_COLS:
                return True
            else:
                return False
        else:
            return False

    def is_legal_move(self, x, y) -> bool:
        """
        Determines if a movement is legal based on the rules of the game.
        :param x: The X coordinate of the point to check
        :param y: The Y coordinate of the point to check
        :return: True if the movement is legal, False if it is not.
        """
        if not self.tile_is_on_board(x, y):
            return False
        if self.board[x][y] is not None:
            return False
        for x_direction, y_direction in [[0, 1], [1, 1], [1, 0], [1, -1], [0, -1], [-1, -1], [-1, 0], [-1, 1]]:
            if self.tile_is_on_board(x_direction + x, y_direction + y):
                if self.board[x_direction + x][y_direction + y] is not None:
                    return True
        return False

    def get_best_move(self) -> list:
        """
        This is where Ava makes a decision regarding where to place a piece.

        ### The Strategy ###
        Ava uses a weight based system to determine where it will place a piece. First it measures the number of tiles it
        will gain from a specific move. It puts this into a list of possible moves and their weights. It picks
        the move which will grant it the most points generally.

        All moves are not equal, and so it has a built in preference for moves such as corners, and will automatically
        grab these points as they become available. The win rate increased dramatically (Over 20%) when different points
        became weighted differently.
        :return: The point determined to be the best place to put a tile. This is a list of 2 values ([x, y])
        """
        moves = {}
        last_legal_move = []
        # Begin preferred moves
        if self.is_legal_move(0, 0):
            return([0, 0])
        if self.is_legal_move(7, 7):
            return([7, 7])
        if self.is_legal_move(0, 0):
            return([7, 0])
        if self.is_legal_move(0, 0):
            return([0, 7])
        # End preferred moves
        for x in range(0, N_ROWS):
            for y in range(0, N_COLS):
                if self.is_legal_move(x, y):
                    last_legal_move = [x, y]
                    start_y = y
                    start_x = x
                    total_value_of_move = 0
                    for x_direction, y_direction in [[0, 1], [1, 1], [1, 0], [1, -1], [0, -1], [-1, -1], [-1, 0], [-1, 1]]:
                        x = start_x
                        y = start_y
                        first_move = True
                        while (self.tile_is_on_board(x + x_direction, y + y_direction)):
                            x += x_direction
                            y += y_direction
                            if self.board[x][y] == None:
                                break
                            if first_move and self.board[x][y] == "M":
                                break
                            first_move = False
                            if self.board[x][y] == "M":
                                while (x != start_x or y != start_y):
                                    x -= x_direction
                                    y -= y_direction
                                    total_value_of_move += 1
                                break
                else:
                    moves[0] = [x, y]
        if max(moves) == 0:
            return last_legal_move
        else:
            return moves[max(moves)]
