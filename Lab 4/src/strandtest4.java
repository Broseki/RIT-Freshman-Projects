import cords.Cord;
import cords.CordMaker;

/**
 * Do a lot of crazy text manipulations based on text from an old
 * Sun Microsystems manual page.
 * @author James Heliotis
 */
class strandtest4 {
    private static String line[] = {
        "DESCRIPTION",
        "CC( capital CC ) translates C++ source code to C source code.",
        "The command uses cpp(1) for preprocessing, cfront for syntax",
        "and type checking, and cc(1) for code generation.",
        "",
        "CC takes arguments ending in .c, .C or .i to be C++ source",
        "programs.  .i files are presumed to be the output of cpp(1).",
        "Both .s and .o files are also accepted by the CC command and",
        "passed to cc(1).",
        "",
        "For each C++ source file, CC creates a temporary file in",
        "/usr/tmp, file.c, containing the generated C file for",
        "compilation with cc. The -F -.suffix option saves a copy of",
        "this file in the current directory with the name",
        "file.suffix.  The +i option saves an edited copy of the",
        "generated C code in the current directory with the name",
        "file..c",
        "",
        "In addition to the options described below, CC accepts other",
        "options and passes them on to the C compilation system",
        "tools.  See cpp(1) for preprocessor options, cc(1) for c",
        "compiler options, ld(1) for link editor options, and as(1)",
        "for assembler options."
    };

    /**
     * Make sure the generated CordMaker string is what we expect.
     * If it is, print "YES". Otherwise, print "NO".
     * @param a the constructed CordMaker string
     * @param i the characters to which it should be equal
     */
    private static void compare( Cord a, String i  ) {
        System.out.println( "Does \"" + a.toString() +
            "\" equal\n     \"" + i + "\"?" );
        System.out.println( i.equals( a.toString() ) ?
            "        YES." : "        NO.");
    }

    /**
     * Do tests on both the strand system and on the String adapter.
     */
    public static void main(  String argv[]  ) {

        useFactory( CordMaker.strandFactory() );
        useFactory( CordMaker.oldStringFactory() );
    }

    /**
     * Just do a lot of manipulations using concatenation and substringing.
     */
    private static void useFactory( CordMaker factory ) {
        Cord I = factory.from( line[18]);
        Cord can = factory.from( line[20]);
        Cord can2 = factory.from( line[3]);
        Cord save = factory.from( line[12]);
        Cord the = factory.from( line[13]);
        Cord rectoryWith = factory.from( line[15]);
        Cord arguments = factory.from( line[5]);
        Cord and = factory.from( line[21]);
        Cord programs = factory.from( line[6]);

        I = I.slice( 0, 1 );
        Cord can3 = can.slice( 54, can.getSize() ).
                concatenate( can2.slice( 0, 2 ) );
        Cord saveThe = save.concatenate( the );
        saveThe = saveThe.slice( 43, 76 );
        saveThe = saveThe.remove( 5, 28 );
        rectoryWith = rectoryWith.slice( 34, 47 );
        Cord arguments2 = arguments.slice( 9, 19 );
        and = and.slice( 49, 53 );
        programs = programs.slice( 0, 9 );

        Cord s1 = I.concatenate( can3 );
        s1 = s1.concatenate( saveThe );
        Cord s2 = rectoryWith.concatenate( arguments2 );
        Cord s3 = and.concatenate( programs );
        Cord s4 = s2.concatenate( s3 );
        Cord s = s1.concatenate( s4 );

        s.toOutput( System.out ); // Tests built-in method
        System.out.println();
        System.out.println( s.toString() ); // Tests Cord.toString

        compare( I, "I" );
        compare( can, line[20] );
        compare( can2, line[3] );
        compare( save, line[12] );
        compare( the, line[13] );
        compare( arguments, line[5] );
        compare( saveThe, " save the " );
        compare( rectoryWith, line[15] );
        compare( and, line[21] );
        compare( programs, line[6] );
    }
}
