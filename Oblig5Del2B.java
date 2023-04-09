import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Oblig5Del2B {
    static Monitor2 monitorTo;
    static Thread[] leseTrader ;
    static Thread[] slaaSammenTrader;
    final static int antFletteTrader = 8;
    static Thread[] fletteTrader = new Thread[antFletteTrader];
    
    //for testing: java Oblig5Del2B TestDataLike/fil1.csv TestDataLike/fil2.csv TestDataLike/fil3.csv TestDataLike/fil4.csv TestDataLike/fil5.csv TestDataLike/fil6.csv TestDataLike/fil7.csv TestDataLike/fil8.csv TestDataLike/fil9.csv 
	//java Oblig5Del2B TestDataLitenLike/fil1.csv TestDataLitenLike/fil2.csv TestDataLitenLike/fil3.csv 
    

    public static void main (String[] args) throws InterruptedException{
        monitorTo= new Monitor2(args.length);
        leseTrader = new Thread[args.length];
        //for antall argumenter start traad som leser fra fil og legger til sekvensregister i monitoren
        for (int i = 0; i< args.length; i++){
            //trader som leser inn fra fil:
            Thread trad = new Thread(new LeseTrad(args[i], monitorTo));
            leseTrader[i]= trad;
            leseTrader[i].start();

        }
        for (int i =0; i<leseTrader.length;i++){
            try{leseTrader[i].join();}
            catch(InterruptedException e){
                System.out.println("join() avbrutt");
            }
        }
   
        //slaar sammen maps
        for (int i = 0; i < antFletteTrader;i++){
            fletteTrader[i]= new Thread(new FletteTrad(monitorTo));
            fletteTrader[i].start();
        }
        for (int i = 0; i < fletteTrader.length;i++){
            fletteTrader[i].join(); // slik at vi ikke skriver ut stoerste verdi for alle maps er slått sammen til en
        }



        //finner den med flest forekomster og printer ut
        Entry<String, Subsekvens> stoerste = Map.entry("Error", new Subsekvens("Error", 0)); //denne fjernes når den sammenlignes med foerste subsekvens i hashmap

        for (Entry<String, Subsekvens> sekv : monitorTo.taUtHashMap(0).entrySet()) {
            if (sekv.getValue().hentForekomster()>stoerste.getValue().hentForekomster()){
                stoerste=sekv;
            }
        }
        System.out.println("stoerste: " + stoerste);
	
    }
}
