import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;

public class Jogo extends UnicastRemoteObject implements JogoInterface {
	private static volatile int result;
	private static volatile boolean changed;
	private static String clientHost = "localhost";

	public Jogo() throws RemoteException {
	}
	
	public static void main(String[] args) throws RemoteException {
		if (args.length != 1) {
			System.out.println("Usage: java Jogo <servidor>");
			System.exit(1);
		}

		try {
			System.setProperty("java.rmi.server.hostname", args[0]);
			LocateRegistry.createRegistry(1099);
			System.out.println("java RMI registry created.");
		} catch (RemoteException e) {
			System.out.println("java RMI registry already exists.");
		}

		try {
			Naming.rebind("Jogo", new Jogo());
			System.out.println("Server is ready.");
		} catch (Exception e) {
			System.out.println("Server failed: " + e);
		}
		
		while (true) {
			if (changed == true) {
				changed = false;

				String connectLocation = "//" + clientHost + "/Jogador";

				JogadorInterface jogador = null;
				try {
					System.out.println("Connecting to client at : " + connectLocation);
					jogador = (JogadorInterface) Naming.lookup(connectLocation);
				} catch (Exception e) {
					System.out.println ("Client failed: ");
					e.printStackTrace();
				}

				try {
					jogador.cutucado();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {}
		}
	}
	
	public int registra() {
		System.out.println("Registro!");
		return 1;
	}
	
	public int joga(int id) {
		while (changed == true);
		changed = true;
		
		try {
			clientHost = getClientHost();
		} catch (ServerNotActiveException e) {
			e.printStackTrace();
		}
		
		return 2;
	}
	
	public int encerra(int id) {
		return 3;
	}
}
