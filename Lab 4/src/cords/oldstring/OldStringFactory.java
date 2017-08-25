package cords.oldstring;

import cords.Cord;
import cords.CordMaker;

/**
 * A factory used to create OldString objects
 * @author Michael Canning
 */
public class OldStringFactory implements CordMaker {
	
	/**
     * Returns an OldString object with a blank string
     * @return An OldString object
     */
	public Cord from(){
		return new OldString();
	}
	
	/**
     * Returns an OldString object with a set string
     * @param The string to wrap in OldString
     * @return The OldString wrapper around the given string
     */
	public Cord from(String string){
		return new OldString(string);
	}
}
	