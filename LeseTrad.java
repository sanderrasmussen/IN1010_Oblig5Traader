public class LeseTrad implements Runnable{
    String filnavn;
    Monitor2 monitor;
    
    public LeseTrad(String filnavn, Monitor2 monitor){
        this.filnavn = filnavn;
        this.monitor = monitor;
    }   
    //les inn fil og legg til i monitor
    public void lesFilLeggTilMonitor(){
        monitor.settInnSubsekvensMap(monitor.LesFilLagMap(filnavn));
    }
    public void run(){
        monitor.settInnSubsekvensMap(monitor.LesFilLagMap(filnavn));
    }
}
