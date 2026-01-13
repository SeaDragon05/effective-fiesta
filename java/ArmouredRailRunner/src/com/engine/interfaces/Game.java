package com.engine.interfaces;

import org.lwjglx.util.vector.Vector3f;

import com.engine.audio.AudioMaster;
import com.engine.font.TextMaster;
import com.engine.graphics.ParticleMaster;
import com.engine.graphics.Window;
import com.engine.postProcess.PostProcessing;
import com.engine.utils.ConfigLoader;
import com.engine.utils.MasterLoader;

import main.Engine;

public interface Game extends Runnable {
	public static void update() {
		Engine.window.swapBuffers();
		Engine.window.update();
	}
	abstract void start();
	public static void init(String configFile) {
		ConfigLoader.loadConfiguraion(configFile);
		System.out.println("---Creating window---");
		Engine.window = new Window("Title");
		Engine.window.setBackgroundColor(new Vector3f(0, 0, 0));
		Engine.window.create();
	}
	public static void close() {
		Engine.println("Game is closing", Engine.color_red);
		Engine.print("Destroying audio...", Engine.color_yellow);
		if (AudioMaster.isSet) {
			AudioMaster.cleanUp();
			Engine.println(" Success", Engine.color_green);
		} else {
			Engine.println(" Not set, not executing", Engine.color_cyan);
		}
		Engine.print("Destroying canvas...", Engine.color_yellow);
		Engine.window.destroy();
		Engine.println(" Success", Engine.color_green);
		Engine.print("Destroying all loaded objects...", Engine.color_yellow);
		MasterLoader.cleanUp();
		Engine.println(" Success", Engine.color_green);
		Engine.print("Destroying GUIs...", Engine.color_yellow);
		if (Engine.guiRenderer != null) {
			Engine.guiRenderer.cleanUp();
			Engine.println(" Success", Engine.color_green);
		} else {
			Engine.println(" Not set, not executing", Engine.color_cyan);
		}
		Engine.print("Destroying renderer...", Engine.color_yellow);
		if (Engine.renderer != null) {
			Engine.renderer.cleanUp();
			Engine.println(" Success", Engine.color_green);
		} else {
			Engine.println(" Not set, not executing", Engine.color_cyan);
		}
		Engine.print("Destroying post process...", Engine.color_yellow);
		if (PostProcessing.isSetup) {
			PostProcessing.cleanUp();
			Engine.println(" Success", Engine.color_green);
		} else {
			Engine.println(" Not set, not executing", Engine.color_cyan);
		}
		Engine.print("Destroying particle system...", Engine.color_yellow);
		if (ParticleMaster.isSetup) {
			ParticleMaster.cleanUp();
			Engine.println(" Success", Engine.color_green);
		} else {
			Engine.println(" Not set, not executing", Engine.color_cyan);
		}
		Engine.print("Destroying everything else...", Engine.color_yellow);
		TextMaster.cleanUp();
		Engine.println(" Success", Engine.color_green);
		Engine.println("This is farewell", Engine.color_white);
		System.exit(0);
	}
}
