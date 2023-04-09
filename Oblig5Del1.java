import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Oblig5Del1 {
	public static void main (String[] args) {
		//args er argumentet for mappe og fil lokasjon: mappenavn/filnavn.filextension, Skriv dette inn som argument etter java Oblig5Del1 i terminalen
		
		//for testing: java Oblig5Del1 TestDataLike/fil1.csv TestDataLike/fil2.csv TestDataLike/fil3.csv TestDataLike/fil4.csv TestDataLike/fil5.csv TestDataLike/fil6.csv TestDataLike/fil7.csv TestDataLike/fil8.csv TestDataLike/fil9.csv 
		//java Oblig5Del1 TestDataLitenLike/fil1.csv TestDataLitenLike/fil2.csv TestDataLitenLike/fil3.csv 
		//kopierte linjene over inn i terminalen for aa teste at jeg fikk rette verdier

		SubsekvensRegister subsekvensregister= new SubsekvensRegister();
		
		for (String arg : args){
			System.out.println("fra fil:");
			subsekvensregister.settInnSubsekvensMap(subsekvensregister.LesFilLagMap(arg));
	
			//lage en loop og for alle maps så skal jeg slå dem sammen
			while (subsekvensregister.antallHashMap()!=1){
					HashMap<String, Subsekvens> mapEn = subsekvensregister.taUtHashMap(0);
					HashMap<String, Subsekvens> mapTo = subsekvensregister.taUtHashMap(0);
					HashMap<String, Subsekvens> nyHashMap = subsekvensregister.slaaSammenMaps(mapEn, mapTo);
					subsekvensregister.settInnSubsekvensMap(nyHashMap);
			}
			
		}
		//finner den med flest forekomster og printer ut
		Entry<String, Subsekvens> stoerste = Map.entry("Error", new Subsekvens("Error", 0)); //denne fjernes når den sammenlignes med foerste subsekvens i hashmap

		for (Entry<String, Subsekvens> sekv : subsekvensregister.taUtHashMap(0).entrySet()) {
			if (sekv.getValue().hentForekomster()>stoerste.getValue().hentForekomster()){
				stoerste=sekv;
			}
		}
		System.out.println("stoerste: " + stoerste);

		//printer ut subsekvenser i hashmap
		//for (int i = 0 ; i< subsekvensregister.antallHashMap();i++){
		//	System.out.println("printer fra neste fil:");
		//	for (Entry<String, Subsekvens> map : subsekvensregister.taUtHashMap(i).entrySet()) {
		//		System.out.println(map);
		//	}
			
		//}
		
	}


}
