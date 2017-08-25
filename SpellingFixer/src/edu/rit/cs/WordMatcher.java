package edu.rit.cs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

/**
 * This class is a utility that does a lot of the dirty work of the
 * spell-<em>checking</em> part of this spelling correction lab.
 * When an instance is made, it loads up a "dictionary" of legal
 * words from a file. It then takes queries against that word
 * collection.
 * @author James Heliotis
 */
public class WordMatcher {

    /**
     * An array of sets, indexed by the length of a word prefix.
     * It is used to find out what words, if any, start with some
     * sequence of letters.
     */
    private HashSet< String >[] legitimates;

    /**
     * A list of complete legal words, indexed by their lengths
     */
    private HashSet< String >[] fullWords;

    /**
     * Load up all the legal words of some language
     * @param wordFileName the name of a file containing all the words,
     *                     one per line
     * @throws IOException if there is any problem with the file
     */
    public WordMatcher( String wordFileName ) throws IOException {

        // Scan for longest word but don't save anything until it is known.
        int maxLen = 0;
        try (
                BufferedReader br =
                    new BufferedReader( new FileReader( wordFileName ) )
            ) {
            maxLen = 0;
            String line = br.readLine();
            while ( line != null ) {
                int len = line.length();
                if ( maxLen < len ) maxLen = len;
                line = br.readLine();
            }
        }

        // Create empty table of prefixes.
        this.legitimates = new HashSet[ maxLen ];
        for ( int len = 0; len < this.legitimates.length; ++len ) {
            this.legitimates[ len ] = new HashSet<>();
        }

        // Create empty table of words.
        this.fullWords = new HashSet[ maxLen ];
        for ( int len = 0; len < this.fullWords.length; ++len ) {
            this.fullWords[ len ] = new HashSet<>();
        }

        // Fill table of prefixes. Note that index is word length - 1
        // because we don't have a 0-length word.
        try (
                BufferedReader br =
                        new BufferedReader( new FileReader( wordFileName ) )
        ) {
            String word = br.readLine();
            while ( word != null ) {

                String lword = word.toLowerCase();
                for ( int i = 1; i <= lword.length(); ++i ) {
                    String prefix = lword.substring( 0, i );
                    this.legitimates[ i - 1 ].add( prefix );
                }
                this.fullWords[ lword.length() - 1 ].add( lword );

                word = br.readLine();
            }
        }
    }

    /**
     * Do any legal words begin with a given string?
     * @param prefix the string of letters being searched at the start of words
     * @return true iff there is at least one word that starts with the prefix
     */
    public boolean prefixExists( String prefix ) {
        return this.legitimates[ prefix.length() - 1 ].contains( prefix );
    }

    /**
     * Is the given word legal?
     * @param word the word to test for membership in the language of
     *             the input file
     * @return true iff the word was an entire word in the language input file
     */
    public boolean wordExists( String word ) {
        return this.fullWords[ word.length() - 1 ].contains( word );
    }
}
