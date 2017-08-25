package cords.oldstring;

import java.io.PrintStream;

import cords.Cord;

public class OldString implements Cord{
	private String string;
	
	public OldString(){
		string = "";
	}
	
	public OldString(String string){
		this.string = string;
	}
	
	@Override
	/**
     * Cut the cord's string into parts starting at from and ending at to
     * @param from The point to start the new string segment
     * @param to Where to end the new string segment
     * @return The new version on OldStringFactory with the slice completed
     */
	public Cord slice(int from, int to) {
		return new OldString(string.substring(from, to));
	}

	@Override
	/**
     * Combines this cord with another and returns a new object with the combined string
     * @param s The Cord to combine this OldStringFactory object with
     * @return The new version on OldStringFactory with the concatenation completed
     */
	public Cord concatenate(Cord s) {
		return new OldString(string + s.toString());
	}

	@Override
	/**
     * Replaces part of the cord's content from two points with the value of s
     * @param from Where to start the replacement
     * @param to Where to end the replacement
     * @param s The cord to replace the selected section with
     * @return The new version on OldStringFactory with the replaced section
     */
	public Cord replace(int from, int to, Cord s) {
		return new OldString(string.substring(0, from) + s.toString() + string.substring(to, string.length() - 1));
	}

	@Override
	/**
     * Removes a section of the current cord's text
     * @param from The place where the removal should start
     * @param to The point to end the removal
     * @return The new version on OldStringFactory with the removal completed
     */
	public Cord remove(int from, int to) {
		return new OldString(string.substring(0, from) + string.substring(to, string.length()));
	}

	@Override
	/**
     * Returns the length of the cord's content
     * @return The length of the cord's content
     */
	public int getSize() {
		return string.length();
	}

	@Override
	/**
     * Returns the character at the designated point in the cord
     * @param index The location of the character to return
     * @return The character at the index location
     */
	public char get(int index) {
		return string.charAt(index);
	}

	@Override
	/**
     * Prints the string that the factory is wrapped around to the given PrintStream
     * @param pw The stream to print the string to
     */
	public void toOutput(PrintStream pw) {
		pw.print(string);
		
	}

	@Override
	/**
     * Prints the requested section of the string that the factory is wrapped around to the given PrintStream
     * @param pw The stream to print the section of string to
     * @param from Where to start the section of string being printed
     * @param to Where to end the section of string being printed
     */
	public void toOutput(PrintStream pw, int from, int to) {
		pw.print(string.substring(from, to));
		
	}
	
	/**
     * Returns the raw unwrapped string
     * @return The raw unwrapped string
     */
	public String toString() {
		return string;
	}
	
}
