package ejercicio3Sincronizacion;

public class Cliente extends Thread {
	private int id;
    public Cliente(int id) { this.id = id; }
    public void run() {
        System.out.println("Cliente " + id + " intenta comprar entrada.");
    }
}
