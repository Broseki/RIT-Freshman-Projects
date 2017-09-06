"""
file: cassio.py
language: python3
description: Runs the Cassio game
"""
__author__ = 'James Heliotis'

from engine import T as Engine

from config import *

import time

global games_played

games_played = 0

global games_won

games_won = 0

def main( ) -> bool:
    """
    Play a game between two players
    """
    global games_played
    global games_won
    player = Player0(0)
    engine = Engine( [ player, Player1( 1 ) ] )
    view = BoardView()
    engine.view = view

    engine.run_game()

    if player.has_won():
        games_won += 1
    games_played += 1

    print("Played: " + str(games_played) + " | Won: " + str(games_won) + " | Win Percentage: " + str((games_won / games_played) * 100))



if __name__ == '__main__':
    while True:
        win = main()
        time.sleep(1)
