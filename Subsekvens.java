
public class Subsekvens {
	final String subsekvens ;
	private int forekomster;
	
	public Subsekvens(String subsekvens, int forekomster) {
		this.subsekvens=subsekvens;
		this.forekomster= forekomster;

		
	}
	
	@Override
	public String toString() {
		return subsekvens +", "+ Integer.toString(forekomster);
	}
	public int hentForekomster(){ //maa ha denne for å slå sammen forekomster naar mappene skal slaas sammen
		return forekomster;
	}
}
