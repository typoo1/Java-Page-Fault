/**
 * 
 * @author TyeG
 * contains all variables and methods for determining the number of page faults
 * that would occur for a randomly generated page-reference
 */
public class Page {

	static String page = "d";
	static String pFrame[];
	static int FIFOFault;
	static int LRUFault;
	static int x = 2;
	
	/**
	 * Generates a page-reference
	 */
	public void GeneratePage(){
		//determine the number of digits for the page string
		int p = (int)(Math.random()*4 + 8);
		for(int i = 0; i < p; i++){
			//assign a random number to each digit of the page string
			page = page + (char)(Math.random()*9 + 48);
		}
		page = page.substring(1);
	}
	
	/**
	 * Test constructor for testing a predetermined page-reference
	 * @param manual The page-reference to be used for testing
	 */
	public Page(String manual){
		page = manual;
	}
	
	/**
	 * Constructor intended for regular use, will generate a page-reference for the user
	 */
	public Page(){
		GeneratePage();
	}
	
	/**
	 * Test method for finding page faults using the First in First out method
	 * @return returns the number of faults for a given page-reference
	 */
	public static int FIFO(){
		FIFOFault = x;
		pFrame = new String[x];
		for(int i = 0; i < x ; i++){
			pFrame[i] = page.substring(i, i+1);
		}
		int pointer = 0;
		int marker = x;
		for(int i = x; i < page.length(); i ++){
			boolean matched = false;
			for(int z = 0; z < x; z++){
				if( pFrame[z].equals(page.substring(marker, marker + 1)))
					matched = true;
				
			}
			if(!matched){
				pFrame[pointer] = page.substring(marker, marker+1);
				pointer++;
				if(pointer >= x)
					pointer = 0;
				FIFOFault++;
			}
			marker++;
		}
		return FIFOFault;
	}
	
	/**
	 * Test method for finding page faults using the Least Recently Used
	 * @return returns the number of faults for a given page-reference
	 */
	public static int LRU(){
		LRUFault = x;
		int marker = x;
		pFrame = new String[x];
		for(int i = 0; i < x ; i++){
			pFrame[i] = page.substring(i, i+1);
		}
		for(int i = x; i < page.length(); i ++){
			boolean match = false;
			for(int z = 0; z <x; z++){
				//test for necessary page fault
				if(pFrame[z].equals(page.substring(marker, marker +1))){
					push(z);
					match = true;
				}
						
			}
			if(!match){
				pushNew(page.substring(marker, marker +1));
				LRUFault++;
			}
		}
				
		return LRUFault;
	}
	
	/**
	 * moves an entry within an array to the beginning of the array.
	 * This is done to signify that the given entry is the most recently used entry
	 * @param startPos The position of the entry to be moved
	 */
	public static void push(int startPos){
		String x1[] = new String[page.length()];
		for(int i = 0; i < page.length(); i++){
			x1[i] = page.substring(i, i+1);
		}
		String comp = x1[startPos];
		String temp = x1[startPos];
		String temp2;
		boolean done = false;
		for(int i = 0; i < x1.length && !done; i++){
			temp2 = x1[i];
			x1[i] = temp;
			temp = temp2;
			done = comp.equals(temp);
		}
	}
	
	/**
	 * pushes a new entry into the array and pushes the lest recently used entry out
	 * @param newRef the new entry to be pushed into the array
	 */
	public static void pushNew(String newRef){
		String x1[] = new String[page.length()];
		for(int i = 0; i < page.length(); i++){
			x1[i] = page.substring(i, i+1);
		}
		String temp = newRef;
		String temp2;
		for(int i = 0; i < x1.length; i++){
			temp2 = x1[i];
			x1[i] = temp;
			temp = temp2;
		}
	}
	
	/**
	 * runs and prints the page-reference, and the required results for 
	 */
	public static void run(){
		System.out.println(page);
		for(int i = 0; i < 6; i ++){
			System.out.println(x + " " + FIFO() + " " + LRU());
			x++;
		}
	}
}
