package dDev.tech.server.ServerNet;


import dDev.tech.server.ServerUtils.Callback;
import dDev.tech.server.ServerUtils.Console;
import dDev.tech.server.Game.Game;

/** Launches the server application. */
public class ServerLauncher {
	public static Game game;
	public static boolean USING_GRAPHICS=  false;
	public static Callback callback;
	public static void main(String[] args) {
		start();

	}
	public static void start(){
		Console.logInfo("Server starting");
		Server server = new Server();
		game= server.game;
		server.start();

		Console.logInfo("Server started");

		Console console = new Console();
		Console.addCommand("s",(String[]args)->{
			server.startGame();
		});
		console.start();
	}
}