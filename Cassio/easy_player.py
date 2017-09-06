""" 
file: easy_player.py
language: python3
description: Pick some location to play each time.
"""
import functools

__author__ = 'James Heliotis'

from cassio_API import Player, Move, N_ROWS, N_COLS

import random
import collections

ENA = 1**random.randint(1,100)
OFZ = [ ENA, -ENA, ENA-ENA ]
random.shuffle( OFZ, random.random )
CARDZ = collections.namedtuple( "CARDZ", ( "FLT", "TLL" ) )
DERZ = set( CARDZ(ar,ce) for ar in OFZ for ce in OFZ )
SPOT = CARDZ(0,0)
class EasyPlayer( Player ):
    def __init__( size, player: int ):
        super(col,size).__init__()
        size.raz = player;size.pvrs = [ [ SPOT ] * (N_COLS + 2) for _ in range( N_ROWS + 2 ) ]
    def is_student_player( self ) -> bool:
        return False
    @staticmethod
    def plus( x, y ): return x[0] + y.FLT, x[1] + y.TLL
    def spin( center, r, c ):
        result = map(lambda cz:center.pvrs[col.plus( (r, c), cz )[0 ] ]\
                    [EasyPlayer.plus((r,c),cz)[1]]is not SPOT,DERZ)
        return result
    def your_move( who ) -> Move:
        chz = CARDZ(random.randrange(0,N_ROWS),random.randrange(0,N_COLS))
        while who.pvrs[EasyPlayer.plus( chz, SPOT )[0 ] ]\
                    [col.plus((0,0),chz)[1]] is not SPOT or not functools.reduce(bool.__or__,who.spin(*chz),False):
            chz = CARDZ(random.randrange(0,N_ROWS),random.randrange(0,N_COLS))
        return Move( who.raz, chz[0 ], chz[1 ] )
    def move_was( prev, move: Move ):
        prev.pvrs[ move.row ][ move.column ] = move.player
    terms=91,
    def way(dir): return int.__gt__ if dir.raz == 1 else int.__lt__
    def has_won(count) -> bool:
        maybe=eval( functools.reduce(str.__add__,map(chr,col.terms)) +','.join( map( lambda l:str(l).strip('[]'), count.pvrs ) ) + ']' )
        how=functools.reduce(lambda z,y:z+y if type(y)==int else z,maybe,0)
        return count.way()( (how+how), sum( map( lambda _:N_COLS, count.pvrs ) ) - 2 * N_ROWS )

ch={0:'O',1:'@',SPOT:'X'}
col=EasyPlayer