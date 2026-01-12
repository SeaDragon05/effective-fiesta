package main;

import org.lwjglx.util.vector.Vector3f;

import com.engine.graphics.RayMarchingShader;
import com.engine.graphics.Window;
import com.engine.objects.Light;
import com.engine.renderer.RayMarchingRenderer;
import com.engine.interfaces.RenderingSystem;

public class BL {
	static RenderingSystem renderer;
	public static void main(String[] args) {
		System.out.println("Starting the game");
		Engine.window = new Window("Title");
		Engine.window.create();

		renderer = new RayMarchingRenderer();
		
		RayMarchingShader shader = new RayMarchingShader();
		
		
		LevelMaster.createLevel(1);
		while (!Engine.window.shouldClose()) {
			update();
		}
		close();
	}
	private static void update() {
		Engine.window.swapBuffers();
		Engine.window.update();
	}
	public static void close() {
		renderer.destroy();
		Engine.window.destroy();
	}
}
