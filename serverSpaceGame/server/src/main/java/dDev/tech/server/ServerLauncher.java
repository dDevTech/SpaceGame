package dDev.tech.server;


/** Launches the server application. */
public class ServerLauncher {
	private static Game game;

	public static void main(String[] args) {
		start();

	}
	public static void start(){
		Console.logInfo("Server starting");
		Server server = new Server();
		server.start();
		Console.logInfo("Server started");

		Console console = new Console();
		Console.addCommand("s",(String[]arg)->{

			server.startGame();
		});
		console.start();
	}
}