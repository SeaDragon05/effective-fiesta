package game.ArmouredRailRunner.main;

import com.engine.font.TextMaster;
import com.engine.interfaces.Game;
import com.engine.renderer.GuiRenderer;
import com.engine.utils.Loader;

import main.Engine;

public class GameThread implements Runnable {
	private static Thread gameThread;// = new Thread("game logic and scripts");
	static boolean canRun = true;
	
	public static void gameThreadRun() {
		GameThread th = new GameThread();
		gameThread = new Thread(th, "game logic and scripts");
		gameThread.start();
	}
	
	@Override
	public void run() {
		while (canRun) {
			loadingScreen();
		}
		System.err.println("INFO: Game thread has stopped!");
	}


	public void loadingScreen() {// test
		Engine.guiRenderer.render(Engine.guis);
		TextMaster.render();
		Game.update();
		try {
			Thread.sleep(17);
		} catch (Exception e) {}
	}
	public static void kill() {
		System.out.println("INFO: Killing thread!");
		canRun = false;
	}
}
