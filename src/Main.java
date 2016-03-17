import javax.annotation.processing.SupportedSourceVersion;
import java.io.*;
import java.util.*;
import java.io.PrintWriter;
import java.io.FileWriter;

public class Main {

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("What is the name of your file? (For example T10I4D100K.dat) Please have this saved in the root directory of the Apriori folder");
        String fileName = sc.next();
        System.out.println("What is your minimum support count? For low memory machines, we do not suggest numbers lower than 500 (this is 0.5% for T10I4D100K.dat)");
        int supCount = sc.nextInt();
        System.out.println("What is the name of your output file? (For example output.txt) This will be saved to the root directory of the Apriori folder");
        String outName = sc.next();

        long temp= System.currentTimeMillis();
        FrequencyList f = new FrequencyList(supCount, fileName);
        long temp2= System.currentTimeMillis()-temp;
        //System.out.println("Time elapsed: "+ temp2);

        //System.out.println("Frequencies");
        //System.out.println(f.FreqMap);
        //temp2= System.currentTimeMillis()-temp;
        //System.out.println("Time elapsed: "+ temp2);

        //System.out.println("Pruned Frequencies");
        //System.out.println(f.pruneMap);
        //temp2= System.currentTimeMillis()-temp;
        //System.out.println("Time elapsed: "+ temp2);

        //System.out.println("New Database");
        //System.out.println(f.database.Transaction);
        //temp2= System.currentTimeMillis()-temp;
        //System.out.println("Time elapsed: "+ temp2);
        //System.out.println("Done!");

        //System.out.println("Checking the combinations for k=2 sets");
        //System.out.println(f.pruneMaps);
        //temp2= System.currentTimeMillis()-temp;
        //System.out.println("Time elapsed: "+ temp2);


        //System.out.println("Seeing whether FreqMaps is loaded");
        //System.out.println(f.FreqMaps);
        //temp2= System.currentTimeMillis()-temp;
        System.out.println("Done! Please navigate to the directory and find your file. Time elapsed: " + temp2);

        try
        {
            PrintWriter FileOut = new PrintWriter(outName);
            for(Map.Entry<Integer, Integer> entry : f.pruneMap.entrySet())
            {
                FileOut.println(entry.getKey()+ " "+ "("+entry.getValue()+")");
            }
            for (int i=0; i<f.pruneMaps.size(); i++)
            {
                for (Map.Entry<String, Integer> entry : f.pruneMaps.get(i).entrySet())
                {
                    FileOut.println(entry.getKey() + " " + "(" + entry.getValue() + ")");
                }
            }
            //FileOut.println(f.pruneMap);
            //FileOut.println(f.pruneMaps);
            FileOut.close();
        }
        catch(Exception e)
        {
            System.out.println("file not found");
        }

    }
}
//39 132 623 704 795 825 853
//82 406 421 438 629
// 32 239 419 448 510 540 581 674 752 887 922