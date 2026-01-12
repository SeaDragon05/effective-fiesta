package main;

import org.lwjgl.glfw.GLFW;
import org.lwjglx.util.vector.Vector3f;

import com.engine.graphics.RayMarchingShader;
import com.engine.graphics.Window;
import com.engine.io.Input;
import com.engine.renderer.RayMarchingRenderer;

public class RayMarchingMain {
	public static void main(String[] args) {
		Engine.window.vsync = true;
		Engine.window = new Window("Title");
		Engine.window.create();
		
		RayMarchingRenderer renderer = new RayMarchingRenderer();
		
		RayMarchingShader shader = new RayMarchingShader();
		
		float time = 0;
		while (!Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE) && !Engine.window.shouldClose()) {
			time ++;//= 0.5f;
			renderer.prepare();
			shader.start();
			shader.loadResolution(new Vector3f(Engine.WIDTH, Engine.HEIGHT, 0));
			shader.load_iTime(time/100);
			//shader.load_Camera(cam);
			shader.load_iFOV(1.5f);
			//shader.load_iMouse();
			renderer.render();
			shader.stop();
			update();
		}
		shader.cleanUp();
		Engine.window.destroy();
	}
	
	private static void update() {
		Engine.window.swapBuffers();
		Engine.window.update();
	}
}
