// Time Complexity : exponential, where H is the number of rows and W is the number of columns
// Space Complexity : O(H*W), where H is the number of rows and W is the number of columns (space for grid, queue and recursive stack)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : I want to know the exact space and time complexity

//Three liner explanation of your code in plain english
//1. create a grid of size H*W. call dfs on the first row and column. place the first building and recurssively start placing the
        //remaining buildings
//2. Once all the buildings are placed for a combination, call BFS to calculate the distance of the farthest lot. Update the
        //global minimum distance.
//3. Once all the building placement combinations are done, return the global minimum distance

// Your code here along with comments explaining your approach

// "static void main" must be defined in a public class.
public class Main {
    public static void main(String[] args) {
        BuildingPlacement buildingPlacement = new BuildingPlacement();
        System.out.println(buildingPlacement.findMinDistance(4, 4, 2));
    }
}

class BuildingPlacement {
    int minDistance = Integer.MAX_VALUE;
    int[][] dirs = {{0,1}, {-1,0}, {0,-1}, {1,0}};
    public int findMinDistance(int H, int W, int n){
        //create grid of size H&W
        int[][] grid = new int[H][W];
        //bactrack function to try out all the building placements combination
        backtrack(grid, 0, 0, n, H, W);
        return minDistance;
    }
    
    private void backtrack(int[][] grid, int r, int c, int n, int H, int W){
        //base
        //all buildings are placed, call bfs to check distance of buildings from the lots
        if(n == 0){
            bfs(grid, H, W);
            return;
        }
        //column goes outOfBounds
        if(c == W){
            r++;
            c=0;
        }
        //logic
        //place one building and move forward to place the remaining buildigs
        for(int i=r; i<H; i++){
            for(int j=c; j<W; j++){
                //action
                grid[i][j] = 1;
                //recurse
                backtrack(grid, i, j+1, n-1, H, W);
                //backtrack
                grid[i][j] = 0;
            }
            //column goes outOfBounds, so start the column from 0 for the next row
            c=0;
        }
    }
    
    private void bfs(int[][] grid, int H, int W){
        //create queue to perform bfs and visited array
        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[H][W];
        
        //put all the placed buildings in the queue and mark them visited
        for(int i=0; i<H; i++){
            for(int j=0; j<W; j++){
                if(grid[i][j]==1){
                    q.add(new int[]{i, j});
                    visited[i][j] = true; 
                }
            }
        }
        //counter to find the distance of the farthest lot
        int dist=0;
        //start bfs
        while(!q.isEmpty()){
            //maintain size variable
            int size = q.size();
            for(int i=0; i<size; i++){
                int[] curr = q.poll();
                //iterate over it's neighbors and add to the queue
                for(int[] dir: dirs){
                    int rIdx = curr[0] + dir[0];
                    int cIdx = curr[1] + dir[1];
                    //boundries check
                    if(rIdx>=0 && cIdx>=0 && rIdx<H && cIdx<W && visited[rIdx][cIdx] ==false){
                        q.add(new int[]{rIdx, cIdx});
                        visited[rIdx][cIdx] = true;
                    }
                }
            }
            //increase distance by 1, after every level
            dist++;
        }
        //update the global minimum distance for each building placement
        minDistance = Math.min(minDistance, dist-1);
    }
}