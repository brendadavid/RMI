package src;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.rmi.registry.LocateRegistry;

public class Jogo extends UnicastRemoteObject implements JogoInterface {
  private static final long serialVersionUID = 2520268900833408151L;
  private static String clientHost = "localhost";

  private final Random random = new Random();

  public Jogo() throws RemoteException {}

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
  }

  public int registra() {
    int clientId = random.nextInt(100);
    System.out.println(String.format("Registro do cliente %d!", clientId));
    return clientId;
  }

  public int joga(int id) {
      try {
        clientHost = getClientHost();
        String connectLocation = "//" + clientHost + "/Jogador";
        JogadorInterface jogador = (JogadorInterface) Naming.lookup(connectLocation);
        int chance = random.nextInt(100);
        System.out.println(String.format("Valor obtido: %d", chance));
        if (chance <= 0) {
          encerra(id);
        } else if (chance > 0 && chance <= 20) {
          jogador.cutucado();
          System.out.println(String.format("Jogador %d foi cutucado!", id));
        } else {
          System.out.println(String.format("Jogador %d jogou!", id));
        }
      } catch (MalformedURLException | RemoteException | NotBoundException | ServerNotActiveException e) {
        e.printStackTrace();
      }
    return id;
  }

  public int encerra(int id) {
    String connectLocation = "//" + clientHost + "/Jogador";
    try {
      JogadorInterface jogador = (JogadorInterface) Naming.lookup(connectLocation);
      jogador.encerrado();
      System.out.println(String.format("Jogador %d foi encerrado!", id));
    } catch (MalformedURLException | RemoteException | NotBoundException e) {
      e.printStackTrace();
    }
    return id;
  }
}
