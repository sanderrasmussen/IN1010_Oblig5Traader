import java.io.File;
import java.io.FileNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

public class Oblig5Hele {
   //for testing: java Oblig5Hele.java TestDataLiten/metadata.csv TestDataLiten/fil1.csv TestDataLiten/fil2.csv TestDataLiten/fil3.csv
   //for testing: java Oblig5Hele.java TestData/metadata.csv TestData/fil1.csv TestData/fil2.csv TestData/fil3.csv TestData/fil4.csv TestData/fil5.csv TestData/fil6.csv TestData/fil7.csv TestData/fil8.csv TestData/fil9.csv
    public static void main (String[] args) throws InterruptedException{
        int antallFiler = args.length-1;
        
   
         
        Thread[] leseTrader = new Thread[antallFiler] ; //en fil per trad , metadata fil er ikke tatt med i betrakningen
        final int antFletteTrader = 8;
        Thread[] fletteTraderSykdom = new Thread[antFletteTrader];
        Thread[] fletteTraderIkkeSykdom = new Thread[antFletteTrader];
            
        //foerst maa jeg lese inn alle data i metafilen og vite hvilken monitor traden skal lese og legge inn i
        //les metadatafil og opprett en hasmap med filnavn som nokkelverdi og boolean for syk som value

        HashMap<String, Boolean> sykeOgIkkeSykeMap= lesMetadata(args[0]);//forste argument i terminalen maa vere metadataene 
      
        int antallFilerSykMonitor= hentAntallFilerSykMonitor(sykeOgIkkeSykeMap);
        int antalFilerIkkeSykMonitor = hentAntalFilerIkkeSykMonitor(sykeOgIkkeSykeMap);

        
        Monitor2 ikkeHattSykdomkMonitor = new Monitor2(antalFilerIkkeSykMonitor);//deklarer naar jeg hvet hvor mange filer skal i ver monitor
        Monitor2 hattSykdomMonitor= new Monitor2(antallFilerSykMonitor);
        //lager en loop og traadobject faar monitor basert på boolean verdi til filen
        for (int i=1; i< args.length;i++){//starter på arg 1 pga arg 0 er metadata fil
            //splitter argumentet til en array slik at vi kan ta det siste navne i path som er filnavn
            //soker derreter opp filnavnet i map og finner ut om filen er fra en som har vert syk eller ikke, dermed hvet vi hvilken monitor traden skal ha
            //splitter args[i]
            String path = args[i];
            String[] filensPath = args[i].split("/");//deler opp stegen i path til array : mappe/navn = [mappe, navn]
            String filensNavn = filensPath[filensPath.length-1];//siste string bakerst i mappe/filnavn
            if (sykeOgIkkeSykeMap.containsKey(filensNavn)){
                if (sykeOgIkkeSykeMap.get(filensNavn)==true){
                    Thread leseSykeTrad = new Thread(new LeseTrad(path, hattSykdomMonitor));
                    leseTrader[i-1]= leseSykeTrad;
                    leseTrader[i-1].start();
                    System.out.println("syk trad");
                }
                else{
                    Thread leseIkkeSykeTrad = new Thread(new LeseTrad(path, ikkeHattSykdomkMonitor));
                    leseTrader[i-1]= leseIkkeSykeTrad;
                    leseTrader[i-1].start();
                    System.out.println("ikke syk trad");
                }
            }
            else{
                System.out.println("fant ikke filen : " + filensNavn);
            }
        }
        //etter loopen har lest inn ale filene skal jeg join() alle tradene
        for (int i = 0; i< leseTrader.length;i++){
            try {
            
                    leseTrader[i].join();
             
            
            } catch (InterruptedException e) {
                System.out.println("trad kunne ikke join()");
                e.printStackTrace();
            } 
        }
        System.out.println("alle lesetrader ferdig");

        // naa skal jeg opprette fletteTrader 
        for (int i = 0; i<antFletteTrader;i++){
            Thread fletteTrad= new Thread(new FletteTrad(hattSykdomMonitor));
            fletteTraderSykdom[i]= fletteTrad;
            fletteTraderSykdom[i].start();
        }
        for (int i = 0; i<antFletteTrader;i++){
            Thread fletteTrad= new Thread(new FletteTrad(ikkeHattSykdomkMonitor));
            fletteTraderIkkeSykdom[i]= fletteTrad;
            fletteTraderIkkeSykdom[i].start();
        }

        //deretter skal alle flette trader join() slik at alle blir ferdige for vi gaar videre i programmet
        
        for (Thread trad : fletteTraderIkkeSykdom){
            trad.join();
        }
        for (Thread trad : fletteTraderSykdom){
            trad.join();
        }
        //finner dominte subsekvenser
        //Jeg tolket oppgave teksten som at jeg skal finne subsekvensen som forekommer oftest i smittede personer og ikke oftest i personer som ikke er smittet
        //Den subsekvensen som forekommer
        //System.out.println(finnDominanteSubsekvenser(hattSykdomMonitor, ikkeHattSykdomkMonitor, args.length-1));
        System.out.println("ferdig med programmet");

        System.out.println(ikkeHattSykdomkMonitor.taUtHashMap(0).toString());
   
        System.out.println(hattSykdomMonitor.taUtHashMap(0).toString());
        System.out.println(hattSykdomMonitor.taUtHashMap(0).toString());
    }

    public static HashMap<String, Subsekvens> finnDominanteSubsekvenser(Monitor2 hattSykdomMonitor, Monitor2 ikkeHattSykdomkMonitor, int antallFiler) throws InterruptedException{
        HashMap<String, Subsekvens> sekvenser = new HashMap<>(); //de dominante sekvensene som skal returneres
      
        if(antallFiler>9){//dersom flere enn 9 filer returnerer vi alle sekvenser som forekommer minst 7 ganger ofter hos de syke enn de friske, denne er for de ekte data som vi skal kjore
            //for alle subsekvens hos syke, sjekk differansen til den samme subsekvesnen hos de friske(om det er en felles) og dersom differansen er 7 eller mer så legger vi subsekvensen til i hashmap som skal returneres

            for (Entry<String, Subsekvens> sekv : hattSykdomMonitor.taUtHashMap(0).entrySet()) {
                if (sekv.getValue().hentForekomster()>=ikkeHattSykdomkMonitor.taUtHashMap(0).get(sekv.getKey()).hentForekomster()+7){//dersom differansen er 7 eller mer
                    sekvenser.put(sekv.getKey(), sekv.getValue());//legger sekvens til i map
                }
            }
        }
        HashMap<String, Subsekvens> stoersteSekvensITestFil = new HashMap<>();
        if(antallFiler<=9){//for testdata filene
            //finner alle som forekommer flere ganger hos de syke enn de ikke syke og finner til slutt den stoerste
            for (Entry<String, Subsekvens> sekv : hattSykdomMonitor.taUtHashMap(0).entrySet()) {
                System.out.println("søk etter dominant..");
                if (sekv.getValue().hentForekomster()>ikkeHattSykdomkMonitor.taUtHashMap(0).get(sekv.getKey()).hentForekomster()){//dersom differansen er 7 eller mer
                    sekvenser.put(sekv.getKey(), sekv.getValue());//legger sekvens til i map
                }
            }
            //har funnet alle som er stoerre og skal naa sammenlikne
            Entry<String, Subsekvens> stoerste = Map.entry("Error", new Subsekvens("Error", 0)); //denne fjernes når den sammenlignes med foerste subsekvens i hashmap

            for (Entry<String, Subsekvens> sekv : sekvenser.entrySet()) {
                if (sekv.getValue().hentForekomster()>stoerste.getValue().hentForekomster()){
                    stoerste=sekv;
                }
            }
            stoersteSekvensITestFil.put(stoerste.getKey(), stoerste.getValue());
            return stoersteSekvensITestFil;
        }

        return sekvenser;
    }



    public static HashMap<String, Boolean> lesMetadata(String filnavn){
        HashMap<String, Boolean> map = new HashMap<>();

        try {
            File fil = new File(filnavn);
            Scanner scanner = new Scanner(fil);
            while (scanner.hasNextLine()){
                String[] data = scanner.nextLine().split(",");
                
                map.put(data[0], Boolean.parseBoolean(data[1]));
            }
            scanner.close();
        }
        catch(FileNotFoundException e){
            System.out.println("ERROR : klarte ikke lese metadata fil");
            e.printStackTrace();    
        }

        return map;
    }
    public static int hentAntallFilerSykMonitor(HashMap<String, Boolean> sykeOgIkkeSykeMap){
        int antallFiler=0;

        for (Entry<String, Boolean> sekv : sykeOgIkkeSykeMap.entrySet()) {
            if (sekv.getValue()==true){
                antallFiler+=1;
            }
        }
        System.out.println("antal syke filer: " + antallFiler);
        return antallFiler;
    }

    public static int hentAntalFilerIkkeSykMonitor(HashMap<String, Boolean> sykeOgIkkeSykeMap){
        int antallFiler=0;

        for (Entry<String, Boolean> sekv : sykeOgIkkeSykeMap.entrySet()) {
            if (sekv.getValue()==false){
                antallFiler+=1;
            }
        }
        System.out.println("antal friske filer: " + antallFiler);
        return antallFiler;
    }
}
