import java.util.HashMap;

public class FletteTrad implements Runnable {
    Monitor2 monitor;

    public FletteTrad(Monitor2 monitor){
        this.monitor=monitor;
    }
    @Override
    public void run() {
        //slaar sammen maps
        while (monitor.stop()==false){
            try{
                if(monitor.antallHashMap()>=2){
                    HashMap<String, Subsekvens> mapEn = monitor.taUtHashMap(0);
                    HashMap<String, Subsekvens> mapTo = monitor.taUtHashMap(0);
                    HashMap<String, Subsekvens> nyHashMap = monitor.slaaSammenMaps(mapEn, mapTo);
                    monitor.settInnSubsekvensMap(nyHashMap);
                    monitor.oekAntFletting();
                    System.out.println("hashmap satt inn");
                }
                
            }
            catch(InterruptedException e){
                System.out.println("Trad kunne ikke slaa sammen maps");
            }
        }

    }

    
}
