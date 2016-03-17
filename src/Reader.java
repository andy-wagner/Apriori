import java.io.*;
import java.util.*;

public class Reader {

    public Scanner scanner;
    public List<Integer> data = new ArrayList();
    public int max;
    private String file;
    public Reader(String fileName) //constructor
    {
        file = fileName;
        fileRead();
        max= -1;
    }

    private void fileRead()
    {
        try {
            scanner = new Scanner(new File(file));
        }
        catch(Exception e){
            System.out.println("file not found");
        }
    }
    public boolean endOfFile;


    public List<Integer> nextLine() //returns a new transaction line
    {
        data = new ArrayList();
        try
        {
        String s = scanner.nextLine();
        Scanner stringScan = new Scanner(s);

            while (stringScan.hasNext()) {
                data.add(Integer.parseInt(stringScan.next()));
            }
            if (!scanner.hasNextLine())
            {
                endOfFile=true;
            }

        }
        catch(NoSuchElementException e)
        {
            endOfFile=true;
            data.add(null);
            return data;
        }
        if (data.get(data.size()-1)>(max))
        {
            max=data.get(data.size()-1);
        }

        return data;
    }
}
