package ejercicio3Sincronizacion;

public class Cliente extends Thread {
    private int id;
    private Cine cine;

    public Cliente(int id, Cine cine) {
        this.id = id;
        this.cine = cine;
    }

    @Override
    public void run() {
        
        cine.ponerseEnCola(this);
    }

   
    public int getEspectadorId() {
        return id;
    }
   
}