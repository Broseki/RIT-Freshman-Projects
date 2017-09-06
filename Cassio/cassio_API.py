"""
The abstractions for a grid-based board game
"""

from abc import abstractmethod, ABCMeta
from collections import namedtuple

N_COLS = 25
N_ROWS = 25

class Board( metaclass=ABCMeta ):
    """
    Classes that inherit this class must somehow represent the game
    board for some game.

    Conventions

    - No I/O is done here.
    - For output, a BoardView class instance is used.
    """

    @abstractmethod
    def get_piece( self, row: int, column: int ) -> int:
        """
        Fetch the piece, if any, stored at the square with
        the requested coordinates.
        This method is defined because BoardView instances will use
        it to get the information they need.

        :param row: the board row number
        :param column: the board column number
        :return: an integer representing the piece. There must be some
                 other unused integer that represents an empty square.

        :pre: row >= 0 and column >= 0
        """
        raise NotImplementedError


class BoardView( metaclass=ABCMeta ):
    """
    This abstraction represents the family of classes that can display to the
    user the contents of a simple board game.
    """

    @abstractmethod
    def update( self, board: Board, row: int = -1, column: int = -1 ) -> None:
        """
        The contents of the board have changed. Make sure the user display
        is up to date. Board.get_piece is used to get the board state.
        If coordinates are (-1,-1), there is no detailed information so the
        entire board must be checked and redisplayed.

        :param board: The actual board instance whose state should be displayed
        :param row: The row where a change happened, if it is just one change,
                    -1 otherwise
        :param column: The column where a change happened, if it is just one
                    change, -1 otherwise
        :return:
        """
        raise NotImplementedError


Move = namedtuple( "Move", ( "player", "row", "column" ) )

class Player( metaclass=ABCMeta ):
    """
    A 2D grid - based game player that conforms to the expectations of a
    provided game engine

    The methods below must be implemented in any subclass in order for
    that class to work.
    """

    def is_student_player( self ) -> bool:
        """
        Allows engine to treat provided players differently.
        Student players are not allowed to override this method.
        :return: True
        """
        return True

    @abstractmethod
    def your_move( self ) -> Move:
        """
        This method should be seen as the game engine telling this player
        that it is its turn to move. It is a request for the player to
        compute its next move.
        When the engine receives the move, it is required to notify all
        other players, including this one, about that move.
        By convention, the player does not update its state based on the
        chosen move. It will do so when move_was gets called immediately
        afterward.
        :return: a description of the move chosen by this player
        """
        raise NotImplemented( str( self.__class__ ) + ": your_move" )

    @abstractmethod
    def move_was( self, move: Move ) -> None:
        """
        Record a move. Each player is responsible for maintaining its own
        game state, and this is where the state gets changed, even for a
        player's own moves.
        :param move: a description of the move just made by some player
        """
        raise NotImplemented( str( self.__class__ ) + ": move_was" )

    @abstractmethod
    def has_won( self ) -> bool:
        """
        Does this player realize it has won the game?
        :return: True iff this player has won the game.
        """
        raise NotImplemented( str( self.__class__ ) + ": has_won" )

