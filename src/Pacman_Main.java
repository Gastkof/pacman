import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


class Node
{
    // (x, y) represents coordinates of a cell in the matrix
    int x, y;
    int level;
    // maintain a parent node for printing the final path
    Node parent;
 
    Node(int x, int y, Node parent, int level)
    {
        this.x = x;
        this.y = y;
        this.level = level;
        this.parent = parent;
    }
 
    @Override
    public String toString() {
        return "(" + x + ", " + y + ')';
    }
}


public class Pacman_Main {

		static double [][] list;
		static int count=0;
		ArrayList<ArrayList<String>> ans = new ArrayList<ArrayList<String>>();
	    
		 private static int[] row = { -1, 0, 0, 1 };
		    private static int[] col = { 0, -1, 1, 0 };
		    
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		// TODO Auto-generated method stub
	    Object obj = new JSONParser().parse(new FileReader("./to/board1.json"));
	    
	    
	    JSONArray pacmanList = (JSONArray) obj;
	    System.out.println(pacmanList);
	    	list= new double[pacmanList.size()][]; 
	   // int[][] list = (int[][]) pacmanList.toArray(); 
	    
	
	    
	   pacmanList.forEach( pac -> pacmanGame( (JSONArray) pac ) );
	  //  JSONObject jo = (JSONObject) obj;

	   int pacmanrow = findRow(list);
	   int pacmancol =findCol(list);
	   System.out.println(pacmancol);
	   System.out.println(pacmanrow);
	   
	 //  int dist = findPaths(list, pacmanrow, pacmancol);
	   
	   boolean n= solve(list,pacmanrow,pacmancol);
	   System.out.println(n);
//	   if (path != null && path.size() > 0) {
//           System.out.print("The shortest path is " + path);
//       } else {
//           System.out.println("Destination is not found");
//       }
//	   
//       if (dist != -1) {
//           System.out.println("The shortest path length is " + dist);
//       }
//       else {
//           System.out.println("Destination is not found");
//       }
//	   
	   
	   
	}

	private static int findCol(double[][] list2) {
		for(int i =0 ; i<list2.length-1;i++) {
			for(int j=0; j<list2[i].length-1;j++) {
				if (list2[i][j]==3.0) {
					return j;
				}
			}
		}
		
		return -1;
		
	}

	private static void pacmanGame(JSONArray pac) {
		// TODO Auto-generated method stub
		
		System.out.println(pac);
		list[count]= new double [pac.size()] ;
		
//		for(int i=0; i<list[count].length-1;i++) {
//			   System.out.println(pac.get(i).toString());
//			list[count][i]=  Double.parseDouble(pac.get(i).toString());
//		}
		
	
        int j = 0;
        for (Object e : pac) {
        	double d = (double)e;
 
          //   for (Map.Entry<Double, JSONObject> entry : e.getAsJsonObject().entrySet()) {
                 list[count][j] =d;
                 j++;
            }
            
		count++;
		

	}
	
	public static int findRow(double[][] pacman)
	{
		for(int i =0 ; i<pacman.length-1;i++) {
			for(int j=0; j<pacman[i].length-1;j++) {
				if (pacman[i][j]==3.0) {
					return i;
				}
			}
		}
		
		return -1;
		
	}
	
	
	
	
	static boolean solve (double[][] grid, int row, int col)
	{
	boolean done = false;
	if (valid(grid, row, col))
	{
	//grid[row][col] = 3; // mark visted
	if ((row == grid.length-1) && (col == grid[0].length-1) && grid[row][col] == 2.0 && grid[row][col] != 3.0 )
		
	done = true; // maze is solved
	else {
	done=( solve(grid, row+1, col) || // try down
	solve(grid, row, col+1) || // try right
	solve(grid, row-1, col) || // try up
	solve(grid, row, col-1) // try left
	);
	}
	if (done)
	grid[row][col] = 7; // mark as part of the path
	}
	return done;
	}
	
	
	public static int findPaths(double[][] matrix, int x, int y)
    {
        // list to store shortest path
       // List<String> path = new ArrayList<>();
 
        // base case
        if (matrix == null || matrix.length == 0) {
            return -1;
        }
 
        // `N × N` matrix
        int N = matrix.length;
 
        // create a queue and enqueue the first node
        Queue<Node> q = new ArrayDeque<>();
        Node src = new Node(x, y, null, 0);
        q.add(src);
 
        // set to check if the matrix cell is visited before or not
        Set<String> visited = new HashSet<>();
 
        String key = src.x + "," + src.y;
        visited.add(key);
 
        // loop till queue is empty
        while (!q.isEmpty())
        {

            Node curr = q.poll();
            int i = curr.x, j = curr.y;
            int level = curr.level;
            int n = (int)matrix[i][j];
   
            if (i == N - 1 && j == N - 1  ) {
             //   findPath(curr, path);
                return level;
            }
 
            // value of the current cell
       
         
            // check all four possible movements from the current cell
            // and recur for each valid movement
            for (int k = 0; k < row.length; k++)
            {
                // get next position coordinates using the value of the current cell
                x =  (i + (row[k])*n);
                y =   (j +(col[k])*n );
 
                // check if it is possible to go to the next position
                // from the current position
                if (valid(x, y, N))
                {
                    // construct the next cell node
                    Node next = new Node(x, y, curr, k+1);
 
                    key = next.x + "," + next.y;
 
                    // if it isn't visited yet
                    if (!visited.contains(key))
                    {
                        // enqueue it and mark it as visited
                        q.add(next);
                        visited.add(key);
                    }
                }
            }
        }
 
        // we reach here if the path is not possible
        return -1;
    }
	

	
	
	
	
	  private static boolean valid(double[][] arr, int row, int col) {
		  return (row < arr.length) && (col < arr[0].length)
				  && (arr[row][col] == 0.0); 
	    }
	
	 private static void findPath(Node node, List<String> path)
	    {
	        if (node != null) {
	            findPath(node.parent, path);
	            path.add(node.toString());
	        }
	    }


    



}
