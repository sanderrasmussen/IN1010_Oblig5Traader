import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;

public class SubsekvensRegister {
	
	ArrayList<HashMap<String, Subsekvens>> hashmaps= new ArrayList<>();
	
	public void settInnSubsekvensMap(HashMap<String, Subsekvens> hashmap) {
		hashmaps.add(hashmap);
	}
	public HashMap<String, Subsekvens> taUtHashMap(int index){//SKRIV DENNE, OPGAVETEKSTEN SPESIFISERT IKKE HVORDAN HASHMAPSKAL HENTES UT
		HashMap<String, Subsekvens> returMap = hashmaps.get(index);
		hashmaps.remove(index);
		return returMap;
	}
	public HashMap<String, Subsekvens> HentUtHashMap(int index){
		return hashmaps.get(index);
	}
	
	public int antallHashMap() {
		return hashmaps.size();
	}
	
	public HashMap<String, Subsekvens> LesFilLagMap(String filnavn) {
		HashMap<String, Subsekvens> hashmap = new HashMap<>();
		try {
			File fil = new File(filnavn);
			Scanner scanner= new Scanner(fil);
			while(scanner.hasNextLine()) {

				String sekvens = scanner.nextLine();
				if (sekvens.length()>=3) {
					for (int i =0; i<sekvens.length()-2;i++) {
				
						String substring = sekvens.substring(i,i+3);
						if (!hashmap.containsKey(substring)){ //hvis subsekvens ikke i map så legg den til
							Subsekvens subsekvens = new Subsekvens(substring,1); //lager en substring som består av 3 bokstaver fra i til i +2
							System.out.println(subsekvens);
							hashmap.put(substring, subsekvens);
						}
					}
				}
				else {
					scanner.close();
					break;
				}
			
			}
			return hashmap;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; //dersom hashmap er tom
	}
	
	public HashMap<String, Subsekvens> slaaSammenMaps(HashMap<String, Subsekvens> mapEn, HashMap<String, Subsekvens> mapTo){
		HashMap<String, Subsekvens> returMap = new HashMap<>();
		
		// for alle nøkler i map 1 hvis samme ikke finnes i map2 så legg subsekvens med antall 2 i Ny map
		//ellers legg til i nyMap
		for (String nokkel : mapEn.keySet()) {
			if (mapTo.containsKey(nokkel)) {
				returMap.put(nokkel, new Subsekvens(nokkel, mapEn.get(nokkel).hentForekomster() + mapTo.get(nokkel).hentForekomster())); //slaar sammen forekomster 
			}
			else {
				returMap.put(nokkel, mapEn.get(nokkel));
			}
		}
		
		return returMap;
		//NB1
		//I dette tilfellet skal de gamle HashMap-ene og Subsekvens-objektene ikke brukes videre, så du
		//kan gjerne oppdatere disse til bruk i den sammenslåtte HashMap-en. 
	}
	@Override
	public String toString(){
		String string = "";
		for (int i = 0 ; i< antallHashMap();i++){
			System.out.println("printer fra neste fil:");
			for (Entry<String, Subsekvens> map : taUtHashMap(i).entrySet()) {
				string+="\n"+map;
			}
			
		}
		return string;
	}
}

