package p2pnet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;


public class Hash
{
    public static Hashtable<String,Integer> hashtable = new Hashtable<String,Integer>();
    //function to read neighbor details from hash

    public static String readHash()
    {
        System.out.println("Inside readHash");
        Set<String> keys = hashtable.keySet();
        Iterator<String> itr = keys.iterator();
        String availableUsers="";
        Integer first = 0;
        while (itr.hasNext())
        {
          // Getting Key
          String key = itr.next();
          if (first==0)
          {
            availableUsers=availableUsers + key +","+ hashtable.get(key);
          }
          else
          availableUsers=availableUsers + ":" + key +","+ hashtable.get(key);
        }
        return availableUsers;
    }
//*******************************************************************************************************************************************************************
    public static void writeHash(String name,Integer port)
    {
      System.out.println("Inside writeHash");
      hashtable.put(name,port);
    }
//*******************************************************************************************************************************************************************
    public Boolean isOnlyNode()
    {
      System.out.println("Inside isOnlyNode");
      Set<String> keys = hashtable.keySet();
      Iterator<String> itr = keys.iterator();
      return (!itr.hasNext());
    }
    
//*******************************************************************************************************************************************************************
} //End of Hash table
