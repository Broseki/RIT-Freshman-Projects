"""
file: cassio_board.py
language: python3
description: A board printer for the Cassio game engine
"""
__author__ = "Michael Canning @ RIT CS"

from cassio_API import BoardView, Board, N_ROWS, N_COLS

class U( BoardView ):
    """
    This is the class which displays the board when requested by the engine. It simply recieves a board object, and
    loops through it to print it in a nice way.
    """

    def update(self, the_board: Board, row: int = -1, column: int = -1) -> None:
        """
        This is called by the engine whenever the board changed. This is what actually prints the board to the user.
        It is extremely simple. It does not save state, and simply loops through the board to print it.

        :param the_board: The board to print
        :param row: The row with the changed point
        :param column: The column with the changed point.
        :return:
        """
        sep_to_print = " "
        for _ in range(0, N_COLS):
            sep_to_print = sep_to_print + "----"
        print(sep_to_print)
        for x in range(0, N_ROWS):
            middle_to_print = "|"
            for y in range(0, N_COLS):
                if (the_board.get_piece(x, y) == 0):
                    piece = "O"
                elif(the_board.get_piece(x, y) == 1):
                    piece = "@"
                else:
                    piece = " "

                middle_to_print = middle_to_print + " " + piece + " |"
            print(middle_to_print)
            print(sep_to_print)
        print("")