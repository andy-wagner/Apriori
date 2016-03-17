import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by Akrylic on 2/21/2016.
 */
public class FrequencyList
{
    HashMap<Integer, Integer> FreqMap = new HashMap(); //Key=transaction id, Value=frequency
    HashMap<Integer, Integer> pruneMap = new HashMap(); //Pruned Frequency list
    HashMap sortedMap;
    Database database;
    List<HashMap<String, Integer>> FreqMaps = new ArrayList(); //FreqMaps for k length freq
    List<HashMap<String, Integer>> pruneMaps = new ArrayList(); //pruneMaps for k length freqs
    CombinationSet combSet;
    HashSet<Integer> pruneHolder = new HashSet<>();
    boolean outOfSubsets = false;

    public FrequencyList(int minCount, String fileName)
    {
        database = new Database(fileName);
        getFrequencies();
        PruneFrequencies(minCount);
        PruneDatabase(minCount);
        //System.out.println("Building combinations");
        getCombinationFrequencies(minCount);
      //  PruneCombinationFrequencies(minCount);


    }
    private void getFrequencies()
    {
        for(int i = 0; i<database.Transaction.size(); i++ )
        {
            //System.out.println(database.Transaction.get(i));
            MapPut(database.Transaction.get(i));
        }
       /** r = new Reader();
        while(!r.endOfFile)
        {
            MapPut(r.nextLine());
        }**/
    }
    private void getCombinationFrequencies(int minCount)
    {
        int i =2;
        while(!outOfSubsets) //iterate from 2 to k size subsets
        {
            for(int j = 0; j<database.Transaction.size(); j++)  //find and count the frequencies of the subsets
            {
                //System.out.println("Transaction line number "+j);
                combSet = new CombinationSet(database.Transaction.get(j),i); //find subsets of a specific transaction
                combinationMapPut(combSet.subSets, i-2);
            }
            PruneCombinationFrequencies(minCount,i-2); //Also calls prune database inside
            //System.out.println("finding combinations of 1+"+i);
            i++;
            pruneHolder.clear();
        }
        for(int j = 0; j<database.Transaction.size(); j++)  //find and count the frequencies of the subsets
        {
            //System.out.println("Transaction line number "+j);
            combSet = new CombinationSet(database.Transaction.get(j),i); //find subsets of a specific transaction
            combinationMapPut(combSet.subSets, i-2);
        }
        PruneCombinationFrequencies(minCount,i-2); //Also calls prune database inside
        //System.out.println("finding combinations of 1+"+i);
        i++;
        pruneHolder.clear();


        //System.out.println("Done with combination frequencies");
    }

    //Puts items in a transaction line into the HashMap FreqMap
    private void MapPut (ConcurrentSkipListMap<Integer, Integer> transactionLine)
    {
        Set set = transactionLine.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext())
        {
            Map.Entry me = (Map.Entry)it.next();
            //System.out.println(me);
            if(FreqMap.get(me.getKey())!=null)
            {
                FreqMap.put((int) me.getKey(), FreqMap.get(me.getKey()) + 1); //Put in the frequency map
            }
            else
            {
                FreqMap.put((int) me.getKey(), 1);
            }
        }
    }
    private void combinationMapPut (ConcurrentSkipListMap<String, Integer> combinationSet, int index) //index is the index in FreqMaps
    {
        FreqMaps.add(new HashMap<String, Integer>());
        Set set = combinationSet.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext())
        {
            Map.Entry me = (Map.Entry)it.next(); //find a map entry to a particular entry
            if(FreqMaps.get(index).get(me.getKey())!=null)
            {
                //System.out.println("Put in FreqMaps "+Objects.toString(me.getKey()));
                FreqMaps.get(index).put(Objects.toString(me.getKey()), FreqMaps.get(index).get(me.getKey()) + 1);
            }
            else
            {
                FreqMaps.get(index).put(Objects.toString(me.getKey()), 1);
            }
        }
    }
    private void PruneCombinationFrequencies(int minCount, int index)
    {
        pruneMaps.add(new HashMap<String, Integer>());
        //for (int i = 0; i<FreqMaps.get(index).size(); i++)
       // {
            for (Map.Entry<String, Integer> entry : FreqMaps.get(index).entrySet())
            {
                if (entry.getValue() >= minCount)
                {
                    //System.out.println("Tracking the entry into pruneMaps "+entry+" min is: "+minCount);
                    pruneMaps.get(index).put(Objects.toString(entry.getKey()), entry.getValue());
                    String[] temp = (Objects.toString(entry.getKey()).split("\\s"));
                    //System.out.println("The array that gets passed in is "+Arrays.toString(temp));
                    for(String t: temp)
                    {
                        pruneHolder.add(Integer.parseInt(t));
                        //System.out.println(pruneHolder);
                    }
                    //System.out.println("PruneHolder contains: " +pruneHolder);
                }
            }
    //    }
        RePruneDatabase();
    }

    private void PruneFrequencies(int minSupportCount)
    {
         for(Map.Entry<Integer, Integer> entry : FreqMap.entrySet())
         {
            if (entry.getValue()>=minSupportCount)
            {
                pruneMap.put(entry.getKey(),entry.getValue());
            }
         }
    }

    private void RePruneDatabase ()
    {
        //System.out.println("RePruning database");
        //Iterate for each transaction i
        for(int i=0; i<database.Transaction.size(); i++)
        {
            //System.out.println("Transaction Line "+i);

            Set set = database.Transaction.get(i).entrySet();
            Iterator it = set.iterator();

            while (it.hasNext())
            {
                Map.Entry me = (Map.Entry) it.next();
                if (!pruneHolder.contains(me.getKey())) //if transaction has items not in the list frequent k item sets, remove it (needless clutter)
                {
                    //System.out.println("Removed from the database "+me.getKey());
                    database.Transaction.get(i).remove(me.getKey());
                }
            //    System.out.println("Removed a bunch of stuff from transactions: ");
            //    System.out.print(database.Transaction);
            }
        }
        for(ConcurrentSkipListMap c : database.Transaction) {
            if (c.isEmpty())
                outOfSubsets = true;
            else
                outOfSubsets= false;
        }

        //System.out.println(database.Transaction );
    }

    //Removes non frequent items from the database
    private void PruneDatabase(int minSupportCount)
    {
        //System.out.println("Pruning database");
        //Iterate for each transaction i
        for(int i=0; i<database.Transaction.size(); i++)
        {
            //System.out.println("Transaction Line "+i);

            Set set = database.Transaction.get(i).entrySet();
            Iterator it = set.iterator();

            while (it.hasNext())
            {
                Map.Entry me = (Map.Entry) it.next();
                if(FreqMap.get(me.getKey())<minSupportCount)
                {
                    //System.out.println("Removing something");
                    database.Transaction.get(i).remove(me.getKey());
                }
            }

        }
    }






}
