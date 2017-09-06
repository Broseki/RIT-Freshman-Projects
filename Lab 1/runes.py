"""
file: runes.py
language: python3
description: translate numbers to base 4 runes, displayed by turtle
"""

__author__ = 'Mike Canning'

# In-Lab: Expand lines marked with asterisks ("****")
#         You may have to ignore much of the documentation.

# When implementing the complete lab,
# refer to the posted pydoc file for details.

import math
import turtle

# The following is handy for debugging.
DEBUG = False   # If true, print extra information during execution.

# The user prompt
PROMPT = "0 to exit> "

# Where the current line is located on the grid
current_line = 0


# Some constants that keep you from recomputing things while drawing.
# The first few can be changed to modify the runes' shapes.

RUNE_HEIGHT = 20
RUNE_WIDTH = 10
RUNE_SPACING = 5
HALF_RUNE_HEIGHT = RUNE_HEIGHT / 2
QUARTER_RUNE_HEIGHT = RUNE_HEIGHT / 4

QUARTER_TURN = 90
EIGHTH_TURN = QUARTER_TURN // 2
TWELFTH_TURN = QUARTER_TURN // 3
SIN_EIGHTH_TURN = math.sin( math.radians( EIGHTH_TURN ) )
SIN_TWELFTH_TURN = math.sin( math.radians( TWELFTH_TURN ) )
COS_TWELFTH_TURN = math.cos( math.radians( TWELFTH_TURN ) )


def to_base( num, radix ):
    """
    Convert an integer to a different base.
    :param num: the int to be represented in some base
    :param radix: the integer base
    :return: a sequence of integers or integer characters representing the
             coefficients, or digit values, LEAST significant digit first, of
             the integer in the specified base. There will be no leading zeros
             at the end of the sequence.
    :pre: num > 0
    """
    converted = ""
    largestExponent = 0
    while(radix**largestExponent < num):
        largestExponent += 1
    while largestExponent >= 0:
        converted = converted + str(num // radix**largestExponent)
        num %= radix**largestExponent
        largestExponent -= 1
    return str(int(converted))

def turtle_ready_position():
    # Moves the turtle cursor to the next start position
    """
    :return: NoneType
    """
    turtle.up()
    turtle.sety(current_line)
    turtle.setx(turtle.position()[0] + RUNE_SPACING)
    turtle.setheading(0)
    turtle.down()


def turtle_newline():
    """
    :return: NoneType
    """
    # Moves the turtle cursor to X = 0, and then down by RUNE_SPACING
    turtle.up()
    turtle.setx(0)
    global current_line
    current_line = -(RUNE_HEIGHT + RUNE_SPACING) + current_line
    turtle.sety(current_line)
    turtle.down()


def test_to_base():
    """
    Make sure the to_base function is working.
    """
    for base in 10, 4, 12:
        print( "Base", base )
        for n in tuple( range( 1, 14 ) ) + ( 57, 136 ):
            print( n, "-->", to_base( n, base ) )
        print()

def draw( n ):
    """
    Draw a single rune digit.
    :pre: Turtle is facing right.
    :pre: Turtle's pen is up.
    :pre: Turtle is at the bottom left of the rune to be drawn.
    :pre: n is greater than 0 and less than RADIX.
    :post: Turtle is facing right.
    :post: Turtle's pen is up.
    :post: Turtle is at the bottom left of the spot where the next rune
           would be drawn.
    :param n: a positive number of type int
    :return: NoneType
    """

    if n == 0:
        turtle.forward(RUNE_WIDTH)
        turtle_ready_position()

    elif n == 1:
        turtle.left(90)
        turtle.forward(RUNE_HEIGHT)
        turtle.right(180)
        turtle.forward(RUNE_HEIGHT / 4)
        turtle.left(120)
        turtle.forward(math.sqrt(((RUNE_HEIGHT/4)**2) + (RUNE_WIDTH/2)**2))
        turtle.right(180)
        turtle.forward(math.sqrt(((RUNE_HEIGHT / 4) ** 2) + (RUNE_WIDTH / 2) ** 2))
        turtle.setheading(-90)
        turtle.forward(RUNE_HEIGHT / 4)
        turtle.left(120)
        turtle.forward(math.sqrt(((RUNE_HEIGHT/2)**2) + RUNE_WIDTH ** 2))
        turtle_ready_position()

    elif n == 2:
        turtle.left(90)
        turtle.forward(RUNE_HEIGHT)
        turtle.right(150)
        turtle.forward(math.sqrt((RUNE_HEIGHT**2) + (RUNE_WIDTH**2)))
        turtle_ready_position()

    elif n == 3:
        turtle.left(90)
        turtle.forward(RUNE_HEIGHT)
        turtle.right(180)
        turtle.forward(RUNE_HEIGHT/4)
        turtle.left(60)
        turtle.forward(math.sqrt(((RUNE_HEIGHT/4)**2) + RUNE_WIDTH ** 2))
        turtle.right(120)
        turtle.forward(math.sqrt(((RUNE_HEIGHT/4)**2) + RUNE_WIDTH ** 2))
        turtle.up()
        turtle.setheading(0)
        turtle.forward(RUNE_WIDTH)
        turtle_ready_position()

    else:
        assert False, str( n ) + " is out of range?"

def test( ):
    """
    :return: NoneType
    """
    turtle.setheading( 0 )
    test_to_base( )
    draw( 0 )
    draw(1)
    draw(2)
    draw(3)
    turtle.done()

def main( ):
    """
    :return: NoneType
    """
    while(True):
        user_input = input(PROMPT)
        if user_input == '0':
            exit()
        else:
            try:
                int(user_input)
                if int(user_input) < 0:
                    print(user_input + " is not a positive integer. Try Again.")
                else:
                    user_input = to_base(int(user_input), 4)
                    for x in str(user_input):
                        draw(int(x))
                    turtle_newline()
            except TypeError:
                print(user_input + " is not a legal positive integer. Try Again.")
            except ValueError:
                print(user_input + " is not a legal positive integer. Try Again.")


if __name__ == '__main__':
    main()
