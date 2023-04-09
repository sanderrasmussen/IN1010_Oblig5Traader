import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.print.attribute.standard.RequestingUserName;

public class Monitor1 {
    
    //skal ha laaser paa:
    //1. sett inn hashmap
    //2. ta ut hasmap
    // LesFilLagMap trenger ikke laas fordi den returnerer hasmap fra lest fra fil
    //slaaSammenMaps har retur verdi så den trenger ikke laas tror jeg

    static SubsekvensRegister register = new SubsekvensRegister();

    //maa lage laaser
    Lock laas = new ReentrantLock();
    Condition tomForMaps = laas.newCondition();

    //taUtHashMap
    public HashMap<String, Subsekvens> taUtHashMap(int index) throws InterruptedException{
        laas.lock();
        HashMap<String, Subsekvens> returMap;
        try{
            while (register.antallHashMap()<1){
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
        }
        finally{
            if (register.antallHashMap()>=1){
                tomForMaps.signal();
            }
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
        // TODO Auto-generated method stub
        return register.toString();
    }
}
