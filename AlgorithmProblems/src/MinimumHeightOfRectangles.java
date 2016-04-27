import java.util.*;

/**
 * Given a set of rectangles and the length of a well, put the rectangles level-by-level 
 * in the well so that the centers of rectangles in each level have the same height.
 * 
 * Output the minimum height of these rectangles. If you cannot put rectangles into the well,
 * return -1.
 * 
 * @author josh
 *
 */
public class MinimumHeightOfRectangles {
	
	/**
	 * Given a list of rectangles and the length of a well, return the minimum height 
	 * of these rectangles.
	 * @param rectangles Array of rectangles, each rectangle is represented as [width, height]
	 * @param length length of the well
	 * @return	the min height, or -1 if you cannot put rectangles into the well.
	 */
	public int minimumHeight(int[][] rectangles, int length){
		
		/*
		 * My idea:
		 * 
		 * Firstly we put these rectangles into a map, where the key is the length of a edge, the value is a list of
		 * tuples (id, length of another edge). In this way we can categorize these rectangles based on their edges.
		 * After the categorization, each rectangle is put into the map twice except squares. We also record the 
		 * number of copies of each rectangle in the map (for rectangles it should be 2, for squares it should be 1).
		 * Then imagine we put the rectangles in the map level by level, while rectangles in each level have the same height.
		 * We put taller rectangles in lower level, and put shorter rectangles in higher level. If rectangles in several levels 
		 * have the same height, we put as much rectangles as we can in a higher level. By doing so, we have put these rectangles
		 * in the well, but we have a lot of duplicate rectangles. Then we need to choose some levels where all the rectangles 
		 * are duplicated to be removed. To make the result optimized, we need to remove lower levels firstly. Keep removing
		 * levels until all the duplicated rectangles are removed, then the remaining rectangles have the minimum height.
		 * 
		 * This program can be done in O(nlogn) time.
		 */
		TreeMap<Integer, List<int[]>> map = new TreeMap<Integer, List<int[]>>();
		for(int i=0; i<rectangles.length; i++){
			if(rectangles[i][0] > length || rectangles[i][1] > length) return -1;
			put(map, rectangles[i][0], new int[]{i, rectangles[i][1]});
			if(rectangles[i][0] != rectangles[i][1])
				put(map, rectangles[i][1], new int[]{i, rectangles[i][0]});
		}
		Comparator<int[]> comparator = new Comparator<int[]>(){
			@Override
			public int compare(int[] o1, int[] o2) {
				if(o1[1] > o2[1]) return 1;
				else if(o1[1] < o2[1]) return -1;
				else return 0;
			}
		};
		int height = 0;
		boolean[] visited = new boolean[rectangles.length];
		for(Map.Entry<Integer, List<int[]>> entry: map.entrySet()){
			int h = entry.getKey();
			List<int[]> rects = entry.getValue();
			Collections.sort(rects, comparator);
			boolean remove = true;
			for(int i=0, start=0, width=0; i<rects.size(); i++){
				int[] rect = rects.get(i);
				if(!visited[rect[0]]) remove = false;
				visited[rect[0]] = true;
				width += rect[1];
				if(width == length || (i<rects.size()-1 && (width+rects.get(i+1)[1])>length) || i==rects.size()-1){
					if(!remove) height += h;
					width = 0;
					start = i+1;
					remove = true;
				}
			}
		}
		return height;
	}
	
	private void put(TreeMap<Integer, List<int[]>> map, int key, int[] val){
		List<int[]> l = map.get(key);
		if(l == null){
			l = new ArrayList<int[]>();
			map.put(key, l);
		}
		l.add(val);
	}
	
	public static void main(String[] args){
		MinimumHeightOfRectangles solution = new MinimumHeightOfRectangles();
		int[][] rects = new int[][]{
			new int[]{2, 3}
		};
		assert(solution.minimumHeight(rects, 4) == 2);
		assert(solution.minimumHeight(rects, 1) == -1);
		rects = new int[][]{
			new int[]{10, 12},
			new int[]{10, 1},
			new int[]{10, 1},
			new int[]{10, 4}
		};
		assert(solution.minimumHeight(rects, 18) == 10);
		rects = new int[][]{
			new int[]{2, 5},
			new int[]{2, 1},
			new int[]{3, 7},
			new int[]{3, 5},
			new int[]{3, 6},
			new int[]{10, 12},
			new int[]{10, 1},
			new int[]{10, 1},
			new int[]{10, 4}
		};
		assert(solution.minimumHeight(rects, 18) == 15);
	}

}
