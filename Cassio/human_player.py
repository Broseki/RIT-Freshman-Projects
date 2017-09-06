""" 
file: human_player.py
language: python3
description: Allow the user to be one of the players.
"""
__author__ = 'James Heliotis'

from cassio_API import Player, Move, N_ROWS, N_COLS

class Human( Player ):

    __slots__ = "player", "board", "next_r", "next_c"

    def __init__( self, player: int ):
        super( Human, self ).__init__()
        self.player = player
        self.board = [ [ None ] * N_COLS for _ in range( N_ROWS ) ]
        self.next_r = 0
        self.next_c = 0

    def your_move( self ) -> Move:
        good = False
        while not good:
            line = input( "Enter row and column numbers separated by a space: " )
            try:
                row, col = [ int( word ) for word in line.split() ]
                if row < 0 or col < 0 or row >= N_ROWS or col >= N_COLS:
                    raise ValueError( "Bad input " + str(row) + ' ' + str(col) )
                good = True
            except ValueError as ve:
                print( ve )
        return Move( self.player, row, col )

    def move_was( self, move: Move ):
        pass

    def has_won( self ) -> bool:
        return "y" == input( "Do you think you won? (y/n) " )


def test( ):
    pass


if __name__ == '__main__':
    test( )
