import turtle

PEN_WIDTH = 1
MARGIN = 20
color = 'blue'


def init( size: int, speed: int ):
    """
    The following initialization steps create a square
    window that uses most of the screen, regardless
    of the resolution of the display it runs on.  This
    avoids any trouble with sizing the window.

    :param size: a resolution parameter so that
                 the coordinate system ranges from -20 to size+20 in both dimensions.
    :param speed: a number between 1 and 10 indicating turtle movement speed
                  0 => don't show animation; just draw
    :pre: The program starts with no preconditions
    :post: turtle is at 0,0, facing east (0 degrees), pen up
    """

    turtle.reset()
    smaller_dim = min( turtle.window_height(), turtle.window_width() )
    turtle.setup( smaller_dim, smaller_dim )
    turtle.setworldcoordinates( -MARGIN, -MARGIN, size + MARGIN, size + MARGIN )
    turtle.down()
    turtle.hideturtle()
    turtle.speed( speed )
    turtle.pensize( PEN_WIDTH )
    turtle.penup()
    turtle.setposition( 0, 0 )

def finish():
    """
    Prepare to end the turtle graphics session.
    Wait for the user to close the window.

    :pre: The turtle is at its final point
    :post: The turtle has not moved, there is a message in the terminal saying close the window
    """
    print( "Please close the turtle canvas window." )
    turtle.done() # i'm finally free from this turtle hell, lolz
    # i'll take my 4.0 now

# Length of highest depth square's side
BOX_SIZE = 1000

# By how much to reduce the square's sides at each successive depth
SHRINKAGE = 35.3 / 100


def draw_single_square_left(side_size):
    """
    Draws a single square starting at the current angle of the turtle.

    :param side_size: the length of a side of a square at the current depth

    :pre: The turtle is facing the proper direction for the desired square orientation
    :post: A square is drawn, and the turtle returns to the starting point
    """
    for _ in range(0, 4):
        turtle.forward(side_size)
        turtle.left(90)


def draw_pretty_shape(side_size):
    """
    Draws the double square shape using single squares.

    :param side_size: the length of a side of a square at the current depth

    :pre: The turtle is at the correct starting point the draw the double square shape. This will immidiatly start
    drawing a side
    :post: The turtle is back at the starting point, and the double square shape has been drawn.
    """
    draw_single_square_left(side_size)
    turtle.forward(side_size)
    turtle.left(90)
    turtle.forward(side_size)
    turtle.right(90)
    draw_single_square_left(side_size)
    turtle.right(90)
    turtle.forward(side_size)
    turtle.right(90)
    turtle.forward(side_size)
    turtle.right(180)
    probsolv_1(side_size)


def return_to_base(side_size):
    """
    Returns the turtle to the starting point where the first set of shapes was drawn. This is used
    to prepare to move back up the recursion chain.

    :param side_size: the length of a side of a square at the current depth
    :pre: The turtle is at the end of drawing one of the internal depth squares, and has returned to the starting
    point for that square
    :post: The turtle returns to the starting point for the squares one depth above it, and is facing the
    starting direction.
    """
    turtle.up()
    turtle.right(45)
    turtle.forward(side_size / 2)
    turtle.left(45)
    turtle.forward(side_size / SHRINKAGE * 6/4)
    turtle.right(90)
    turtle.forward(side_size / SHRINKAGE)
    turtle.right(90)
    turtle.forward(side_size / SHRINKAGE / 2)
    turtle.right(135)
    turtle.down()


def probsolv_1( side_size: int ):
    """
    Move the turtle to the proper position to draw the square shape. This is used
    every time that the main shape is to be drawn.

    :param side_size: the length of a side of a square at the current depth

    :pre: The turtle is at the starting point for the first square.
    :post: The turtle is at the starting point for the first double square.
    """
    turtle.up()
    turtle.left(90)
    turtle.forward(side_size / 2)
    turtle.right(135)
    turtle.down()


def probsolv_2( side_size: int ):
    """
    Moves the turtle to the other square to prepare to draw the second main shape.
    This is used in place of probsolv_1 for the first movement of the 2nd set of squares.

    :param side_size: the length of a side of a square at the current depth

    :pre: The turtle is at the starting point for the first set of square shapes
    :post: The turtle is at the starting point for the second set of square shapes [left to right]
    """
    turtle.up()
    turtle.right(45)
    turtle.forward(side_size / 2)
    turtle.left(45)
    turtle.forward((side_size / SHRINKAGE) / 2)
    turtle.left(90)
    turtle.forward(side_size / SHRINKAGE)
    turtle.left(90)
    turtle.forward((side_size / SHRINKAGE) * 6/4)
    turtle.right(135)
    turtle.down()

move_to_subsquare_1 = probsolv_1
move_to_subsquare_2 = probsolv_2


def toggle_color():
    """
    Returns the opposite value of the current global color, and resets the global color variable.

    :return: The opposite of the current turtle color (yellow or blue)

    :pre: The color is blue or yellow
    :post: The color is now the opposite of the color in the precondition
    """
    global color
    if color == 'blue':
        turtle.color('yellow')
        color = 'yellow'
    else:
        turtle.color('blue')
        color = 'blue'

def draw_squares(side_size, depth, top_level):
    """
    Combine all of the functions used to draw the images, and implement recursion.

    :param side_size: the length of a side of a square at the current depth
    :param depth: How many times the program should recurse
    :param top_level: A copy of depth that does not change with the count. Used to stop repeating.

    :pre: The turtle is at the starting point for the first full double square shape
    :post: The entire drawing has been completed and the turtle has stopped.
    """
    if depth < 1:
        pass
    else:
        toggle_color()
        draw_pretty_shape(side_size)
        draw_squares(side_size * SHRINKAGE, depth - 1, top_level)
        if depth != top_level:
            probsolv_2(side_size)
            draw_pretty_shape(side_size)
            draw_squares(side_size * SHRINKAGE, depth - 1, top_level)
            toggle_color()
            return_to_base(side_size)


depth = input("Depth?: ")
try:
    depth = int(depth)
    if depth > 0:
        init(300, 1)
        turtle.down()
        turtle.color('blue')
        draw_single_square_left(300)
        move_to_subsquare_1(300)
        draw_squares(300 * SHRINKAGE, depth, depth)
        finish()
    elif depth < 0:
        print('Please enter a positive number!')
    else:
        init(300, 1)
        turtle.down()
        draw_squares(300 * SHRINKAGE, depth, depth)
        finish()
except ValueError:
    print(depth + " is not a valid number. Please enter a valid number.")
except TypeError:
    print(depth + " is not a valid number. Please enter a valid number.")

