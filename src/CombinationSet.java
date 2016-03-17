import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import sun.awt.image.ImageWatched;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by Akrylic on 2/23/2016.
 */
public class CombinationSet
{
    private ConcurrentSkipListMap transaction;
    private int size; // desired size of subset
    private int[] input;
    public ConcurrentSkipListMap<String, Integer> subSets = new ConcurrentSkipListMap<>(); //A list of CSLMs. The SkipLists  Arrays
    private LinkedList<Integer> copyTemp = new LinkedList<>();
   // public List<int[]> allCombinations = new ArrayList<>();
    public CombinationSet(ConcurrentSkipListMap t, int s)
    {
        transaction = t;
        size = s;
        input = new int[transaction.size()];
        Set<Integer> key =transaction.keySet();
        int i = 0;
        for(Integer k : key)
        {
            input[i] = k;
            i++;
            //System.out.println(input);
        }
        //System.out.println("combination "+choose(10));
        //combinations(input);
        int[] used = new int[size];
        Arrays.fill(used, -1);
        createSubsets(input, used, 0, 0);

    }

    public void createSubsets(int[] input, int[] used, int index, int usedCount){
        if(usedCount == used.length)
        {
            StringBuilder s = new StringBuilder();
            for(int i = 0; i < usedCount; i++)
            {
                    s.append(input[used[i]]+" ");
            }
            //System.out.println(s);
            subSets.put(s.toString(), 1);
            //subSets.put(temp,1);
        }
        else
        {
            for(int i = index; i < input.length; i++)
            {
                used[usedCount] = i;
                createSubsets(input, used, i + 1, usedCount + 1);
                used[usedCount] = -1;
            }
        }
    }




 /**   public void combinations(LinkedList input)
    {
        int matrixSize = input.size();
        copyTemp = (LinkedList)input.clone();
        for(int i = 0; i < matrixSize; i++)
        {
            copyTemp = (LinkedList)copyTemp.clone();
            subSets.add(i, (LinkedList) copyTemp.clone());
            System.out.println("New " + subSets);
            copyTemp.removeFirst();
        }
        for(int i = 0; i < size; i ++)
        {
            List SS = new LinkedList();
         //  for (int j : subSets.get(i+1))
           // {
              //  SS.add(subSets.get(i).get())
            //}
            if(SS.size()<size)
                 break;
          //  subSets.add();
           // if(SS.)
        }
    }**/
    private int factorial(int n)
    {
        if (n == 1 || n == 0)
            return n;
        int fact = n-1;
        while (fact > 0)
        {
            n = n*fact;
            fact--;
        }
        return n;
    }
    private int choose(int n)
    {
        int j = (factorial(n)/(factorial(size)*factorial(n-size)));
        return j;
    }

 /**   public void combinations()
    {
        int numLists = (int)Math.pow(transaction.size(),size);
        for (int i = 0; i< numLists; i++) {
            subSets.add(new int[size]);
        }

        for (int x = 0; x < size; x++)
        {
            int change = (int) Math.pow(input.length, size - x - 1);
            for(int y = 0; y < numLists; y++)
            {
                int[] current = subSets.get(y);
                int index = x/change%input.length;
                current[x] = input[index];
            }
        }
    }**/



}
