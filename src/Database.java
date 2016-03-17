import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by Akrylic on 2/23/2016.
 */
public class Database
{
    Reader r;
    String file;
    List<ConcurrentSkipListMap> Transaction = new ArrayList(); //Transaction is a list of transactions stored as CSLMs
    public Database(String fileName)
    {
        r = new Reader(fileName);
        newLoad();
    }
    public void Prune()
    {
        return;
    }
    private void newLoad()
    {
        while(!r.endOfFile)
        {
            List<Integer> l = r.nextLine();
            ConcurrentSkipListMap map = new ConcurrentSkipListMap();
            for(int i = 0; i<l.size(); i++)
            {
                map.put(l.get(i), l.get(i));
            }
            Transaction.add(map);
        }
    }

}
