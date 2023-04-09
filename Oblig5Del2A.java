import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Oblig5Del2A {
    static Monitor1 monitor= new Monitor1();
    static Thread[] leseTrader ;
    static Thread[] slaaSammenTrader;
    //for testing: java Oblig5Del2A TestDataLike/fil1.csv TestDataLike/fil2.csv TestDataLike/fil3.csv TestDataLike/fil4.csv TestDataLike/fil5.csv TestDataLike/fil6.csv TestDataLike/fil7.csv TestDataLike/fil8.csv TestDataLike/fil9.csv 
	//java Oblig5Del2A TestDataLitenLike/fil1.csv TestDataLitenLike/fil2.csv TestDataLitenLike/fil3.csv 
    

    public static void main (String[] args) throws InterruptedException{
        leseTrader = new Thread[args.length];
        //for antall argumenter start traad som leser fra fil og legger til sekvensregister i monitoren
        for (int i = 0; i< args.length; i++){
            //trader som leser inn fra fil:
            Thread trad = new Thread(new LeseTrad(args[i], monitor));
            leseTrader[i]= trad;
            leseTrader[i].start();

        }
        for (int i =0; i<leseTrader.length;i++){
            try{leseTrader[i].join();}
            catch(InterruptedException e){
                System.out.println("join() avbrutt");
            }
        }
        //ifolge oppgaveteksten skal ikke trader utfore oppgaven om å slaa sammen hashmaps. Dette skal Main metoden/traden gjore
        //slaar sammen maps
        while (monitor.antallHashMap()!=1){
            HashMap<String, Subsekvens> mapEn = monitor.taUtHashMap(0);
            HashMap<String, Subsekvens> mapTo = monitor.taUtHashMap(0);
            HashMap<String, Subsekvens> nyHashMap = monitor.slaaSammenMaps(mapEn, mapTo);
            monitor.settInnSubsekvensMap(nyHashMap);
        }


        //finner den med flest forekomster og printer ut
        Entry<String, Subsekvens> stoerste = Map.entry("Error", new Subsekvens("Error", 0)); //denne fjernes når den sammenlignes med foerste subsekvens i hashmap

        for (Entry<String, Subsekvens> sekv : monitor.taUtHashMap(0).entrySet()) {
            if (sekv.getValue().hentForekomster()>stoerste.getValue().hentForekomster()){
                stoerste=sekv;
            }
        }
        System.out.println("stoerste: " + stoerste);
	
    }
}
