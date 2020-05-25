package src;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;

public class Jogador extends UnicastRemoteObject implements JogadorInterface {
  private static final long serialVersionUID = -4613845962359855373L;

  public Jogador() throws RemoteException {
  }

  public static void main(String[] args) {
    int result;

    if (args.length != 2) {
      System.out.println("Usage: java Jogador <ip local> <servidor>");
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
      Naming.rebind("Jogador", new Jogador());
      System.out.println("Jogador Server is ready.");
    } catch (Exception e) {
      System.out.println("Jogador Serverfailed: " + e);
    }

    String remoteHostName = args[1];
    String connectLocation = "//" + remoteHostName + "/Jogo";

    JogoInterface jogo = null;
    try {
      System.out.println("Connecting to server at : " + connectLocation);
      jogo = (JogoInterface) Naming.lookup(connectLocation);
    } catch (Exception e) {
      System.out.println ("Jogador failed: ");
      e.printStackTrace();
    }

    // registra()
    try {
      jogo.registra();
    } catch (RemoteException e) {
      e.printStackTrace();
    }

    for (int i = 0; i < 50; i++) {
      try {
        // joga
        jogo.joga(111);
      } catch (RemoteException e) {
        e.printStackTrace();
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {}
    }

    // encerra
  }

  public void encerrado() {
    System.out.println("encerrado");
  }

  public void cutucado() {
    System.out.println("cutucado");
  }
}
