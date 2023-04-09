import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor2 {
    static SubsekvensRegister register = new SubsekvensRegister();

    //maa lage laaser
    Lock laas = new ReentrantLock();
    Condition tomForMaps = laas.newCondition();
    int antallFlettinger = 0;
    int antallMaps;

    public Monitor2(int antallMaps){
        this.antallMaps= antallMaps;
    }

    public int hentAntallFlettinger(){
        return antallFlettinger;
    }
    public void oekAntFletting(){
        antallFlettinger+=1;
    }

    //taUtHashMap
    public HashMap<String, Subsekvens> taUtHashMap(int index) throws InterruptedException{
        laas.lock();
        HashMap<String, Subsekvens> returMap;
        try{
            while (register.antallHashMap()<1 ){ //kan kun hente ut map dersom det er minst 2 maps
                tomForMaps.await();
            }
            returMap = register.taUtHashMap(index);
        }
        finally{
            laas.unlock();
            System.out.println("hashmap tatt ut");
        }
        return returMap;
    }

    //settInnSubsekvensMap

	public void settInnSubsekvensMap(HashMap<String, Subsekvens> hashmap) {
		laas.lock();
        try{
            register.settInnSubsekvensMap(hashmap);
            if (register.antallHashMap()>=2){
                tomForMaps.signal();
            }
        }
        finally{
           
            laas.unlock();
        }
	}

	public HashMap<String, Subsekvens> HentUtHashMap(int index){
		return register.HentUtHashMap(index);
	}
	
	public int antallHashMap() {
		return register.antallHashMap();
	}
	
	public HashMap<String, Subsekvens> LesFilLagMap(String filnavn) {
		return register.LesFilLagMap(filnavn);
	}
	
	public HashMap<String, Subsekvens> slaaSammenMaps(HashMap<String, Subsekvens> mapEn, HashMap<String, Subsekvens> mapTo){
		return register.slaaSammenMaps(mapEn, mapTo);
	}
    
    @Override
    public String toString() {
        return register.toString();
    }
    public boolean stop(){
        if (antallFlettinger==antallMaps-1){//antallMaps-1 er antall operasjoner som trengs for å vite at tradene er ferdige
            return true;
        }
        return false;
    }
}
