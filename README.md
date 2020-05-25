# RMI

Remote Method Invocation (RMI)

1. Elabore uma aplicação Java RMI para registrar usuários (jogadores) em um
servidor remoto de jogos. Ao registrar um jogador no servidor, recebe-se um
identificador único. Este identificador único é usado como parâmetro em dois
outros métodos remotos disponibilizados pelo servidor: joga (simula uma
atividade/jogada realizada pelo usuário) e encerra (finaliza o registro do
usuário).


public interface JogoInterface extends Remote {
 public int registra() throws RemoteException;
 public int joga(int id) throws RemoteException;
 public int encerra(int id) throws RemoteException;
}

public interface JogadorInterface extends Remote {
 public void encerrado() throws RemoteException;
 public void cutucado() throws RemoteException;
}


Cada jogador deve realizar 50 jogadas (invocação do método joga()) e após
finalizar (invocação do método encerra()). A cada jogada, existe uma
probabilidade de 1% do servidor encerrar o jogador (invocação do método
encerrado(), disponibilizado na interface do jogador) ou 20% de cutucar o
mesmo (método cutucado()).

Para a elaboração da atividade, sugere-se utilizar como referência o exemplo
sobre callbacks apresentado na aula anterior (aula19-add_rmi_callback.tar.gz).
