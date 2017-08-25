import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * This is the model for storing tree information for the DQU compression format. The root DQUNode and its children
 * are stored here. File reading and writing occurs here. Compression and decompression also happens here using recursion. 
 * 
 * @author Mike Canning @ RIT CS
 */
public class QTree {
	
	public static final int QUAD_SPLIT = -1;
	private DQUNode root;
	private int dim;
	private int[][] rawImage;
	private int rawSize;
	private int compressedSize;
	
	/**
     * This reads in a raw image file from a given file location. It returns a new QTree instance.
     *
     * @param string The file path to the raw image file.
     * @return A QTree representing the given file.
     */
	public static QTree rawFromFile(String string) {
		int length = 0;
		ArrayList<Integer> list = new ArrayList<>();
		QTree tree = new QTree();
		try (BufferedReader br = new BufferedReader(new FileReader(string))) {
			String line;
			while((line = br.readLine()) != null) {
				length ++;
				list.add(Integer.parseInt(line));
			}
			tree.rawSize = length;
			tree.dim = (int)Math.sqrt(length);
			int currentLocation = 0;
			tree.rawImage = new int[tree.dim][tree.dim];
			for(int y = 0; y < tree.dim; y ++) {
				for(int x = 0; x < tree.dim; x ++) {
					tree.rawImage[y][x] = list.get(currentLocation);
					currentLocation ++;
				}
			}
			return tree;
		} catch (FileNotFoundException e) {
			System.err.println("Raw file not found!");
		} catch (NumberFormatException e) {
			System.err.println("Number format problem in raw file!");
		} catch (IOException e) {
			System.err.println("IOException in raw file reader!");
		}
		return tree;
	}

	/**
     * This reads in a compressed image file and returns a new QTree instance.
     *
     * @param file_location The location of the compressed image file
     * @return A new instance of QTree containing the compressed image information
     */
	public static QTree compressedFromFile(String file_location) {
		QTree result = new QTree();
		int imageSize = 0;
		int dim = 0;
		int lineCount = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(file_location))) {
		    String line = br.readLine();
		    if(line != null) {
		    	imageSize = Integer.parseInt(line);
		    	dim = (int) Math.sqrt(imageSize);
		    	DQUNode node = parse(br);
		    	result.root = node;
		    	result.dim = dim;
		    	result.rawSize = imageSize;  
		    }
		} catch (IOException e) {
			System.err.println("IOException in compressedFromFile");
		}
		try (BufferedReader br = new BufferedReader(new FileReader(file_location))) {
		    while(br.readLine() != null) {
		    	lineCount ++;
		    }
		} catch (IOException e) {
			System.err.println("IOException in compressedFromFile");
		}
		result.compressedSize = lineCount;
		return result;
	}
	
	/**
     * This calls the recursive compress method with base settings and sets the root DQUNode.
     */
	public void compress() {
		compressedSize = 0;
		this.root = compress(new Coordinate(0, 0), dim);
	}
	
	/**
     * This is the recursive method that compressed the image to a root DQUNode.
     *
     * @param start Where to start the compression on the rawImage 2d list.
     * @param size The current dimension of the given section of the 2d list being read.
     * @return The DQUNode representing the requested section of the graph
     */
	private DQUNode compress(Coordinate start, int size) {
		compressedSize ++;
		if(canCompressBlock(start, size)) {
			return new DQUNode(rawImage[start.getRow()][start.getCol()]);
		}else {
			DQUNode upperLeft = compress(new Coordinate(start.getRow(), start.getCol()), size / 2);
			DQUNode upperRight = compress(new Coordinate(start.getRow(), start.getCol() + size / 2), size / 2);
			DQUNode lowerLeft = compress(new Coordinate(start.getRow() + size / 2, start.getCol()), size / 2);
			DQUNode lowerRight = compress(new Coordinate(start.getRow() + size / 2, start.getCol() + size / 2), size / 2);
			return new DQUNode(upperLeft, upperRight, lowerLeft, lowerRight);
		}
	}
	
	/**
     * Checks to see if a given section of the image can be compressed and returns true or false respectively.
     * 
     * @param start The coordinate to start the check on
     * @param size The dimension of the section of graph to check
     * @return True if the section can be compressed, false if the given section cannot be compressed.
     */
	private boolean canCompressBlock(Coordinate start, int size) {
		int standard = -1;
		for(int y = 0; y < size; y ++) {
			for(int x = 0; x < size; x ++) {
				if(standard == -1) {
					standard = rawImage[y + start.getRow()][x + start.getCol()];
				}else {
					if(standard != rawImage[y + start.getRow()][x + start.getCol()]) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
     * This writes the compressed image to a file at a given location
     *
     * @param string The location to save the compressed image to
	 * @throws DQUException Thrown if the compressed image does not exist yet.
     */
	public void writeCompressed(String string) throws DQUException {
		if(root == null) {
			throw new DQUException("The compressed version of the image does not exist yet");
		}else {
			try{
				String[] stringSplit = this.preorder(root).split(" ");
			    PrintWriter writer = new PrintWriter(string);
			    writer.println(this.rawSize);
			    for(int i = 0; i < stringSplit.length; i ++) {
			    	writer.println(stringSplit[i]);
			    }
			    writer.close();
			} catch (IOException e) {
				System.err.println("IOException in writeCompressed");
			}
		}
	}
	
	/**
     * This returns the raw size of the image.
     * 
     * @throws DQUException This is thrown if the image has not been put into raw format yet.
     * @return The raw size of the image
     */
	public int getRawSize() throws DQUException {
		if(rawImage.length == 0) {
			throw new DQUException("Raw image does not exist");
		}else {
			return rawSize;
		}
	}
	
	/**
     * This returns the compressed size of the image.
     * 
     * @throws DQUException This is thrown if the image has not been compressed or read as compressed yet
     * @return The compressed size of the image
     */
	public int getCompressedSize() throws DQUException {
		if(root == null) {
			throw new DQUException("Compressed image does not exist");
		}else {
			return compressedSize;
		}
	}
	
	/**
     * This calls the recursive uncompress function and initializes the rawImage 2d list.
	 * @throws DQUException 
     */
	public void uncompress() throws DQUException {
		if(root == null) {
			throw new DQUException("Compressed image does not exist");
		}else {
			rawImage = new int[dim][dim];
			uncompress(new Coordinate(0, 0), dim, root);
		}
	}
	
	/**
     * This is the recursive function used to decompress an image and fill the rawImage 2d list with it
     */
	private void uncompress(Coordinate coord, int dim2, DQUNode node) {
		if (node.getValue() != QTree.QUAD_SPLIT) {
			for(int x = 0; x < dim2; x ++) {
				for(int y = 0; y < dim2; y ++) {
					rawImage[x + coord.getRow()][y + coord.getCol()] = node.getValue();
				}
			}
		}else {
			uncompress(coord, dim2 / 2, node.getChild(Quadrant.UL));
			uncompress(new Coordinate(coord.getRow(), coord.getCol() + dim2 / 2), dim2 / 2, node.getChild(Quadrant.UR));
			uncompress(new Coordinate(coord.getRow()  + dim2 / 2, coord.getCol()), dim2 / 2, node.getChild(Quadrant.LL));
			uncompress(new Coordinate(coord.getRow()  + dim2 / 2, coord.getCol()  + dim2 / 2), dim2 / 2, node.getChild(Quadrant.LR));
		}
	}
	
	/**
     * This returns the rawImage 2d list
     * @return the raw
	 * @throws DQUException This is thrown if no raw image exists yet
     */
	public int[][] getRawImage() throws DQUException {
		if(rawImage.length == 0) {
			throw new DQUException("Raw image does not exist");
		}
		return rawImage;
	}
	
	/**
     * This returns the raw size of the image.
     * @throws DQUException This is thrown if the image has not been put into raw format yet.
     * @return The raw size of the image
     */
	public int getSideDim() {
		return dim;
	}
	
	/**
     * This recursively parses a compressed file from a given file path
     * 
     * @throws IOException This is returned if a file is missing
     */
	private static DQUNode parse(BufferedReader file) throws IOException {
		String current_line;
		current_line = file.readLine();
		int current_line_int = Integer.parseInt(current_line);
		if(current_line_int != -1){
			return new DQUNode(current_line_int);
		}else {
			DQUNode top_left = parse(file);
			DQUNode top_right = parse(file);
			DQUNode bottom_left = parse(file);
			DQUNode bottom_right = parse(file);
			return new DQUNode(top_left, top_right, bottom_left, bottom_right);
		}
	}
	
	/**
     * This traverses the tree in a preorder path
     * 
     * @param node The node to traverse
     * @return A space separated string representing the tree which was traversed
	 * @throws DQUException This is thrown if compressed images do not exist
     */
	private String preorder(DQUNode node) throws DQUException {
		if(root == null) {
			throw new DQUException("Compressed image does not exist");
		}
		if(node.getValue() != -1) {
			return Integer.toString(node.getValue());
		}else {
			return("-1 " + preorder(node.getChild(Quadrant.UL)) + " " + preorder(node.getChild(Quadrant.UR)) + " " + preorder(node.getChild(Quadrant.LL)) + " " + preorder(node.getChild(Quadrant.LR)));
		}
	}
	
	/**
     * This returns a nice to look at version of the image tree
     * 
     * @return The string version of the image tree
     */
	public String toString() {
		return stringToPrint(root);
	}
	
	/**
     * This recursively read the image tree and puts together a string to represent it
     * 
     * @param node The node to start reading from
     * @return The string representing the read in node
     */
	private String stringToPrint(DQUNode node) {
		if(root == null) {
			return "NO TREE";
		}
 		if(node.getValue() != -1) {
			return Integer.toString(node.getValue());
		}else {
			return("( " + stringToPrint(node.getChild(Quadrant.UL)) + " " + stringToPrint(node.getChild(Quadrant.UR)) + " " + stringToPrint(node.getChild(Quadrant.LL)) + " " + stringToPrint(node.getChild(Quadrant.LR)) + ")");
		}
	}
}
