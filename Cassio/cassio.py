""" 
file: cassio.py
language: python3
description: Runs the Cassio game
"""
__author__ = 'James Heliotis'

from engine import T as Engine

from config import *

def main( ) -> None:
    """
    Play a game between two players
    """
    engine = Engine( [ Player0( 0 ), Player1( 1 ) ] )
    view = BoardView()
    engine.view = view

    engine.run_game()



if __name__ == '__main__':
    main( )
