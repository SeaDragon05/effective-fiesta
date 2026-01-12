package com.engine.interfaces;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.engine.objects.Entity;

public interface RenderingSystem {
	/**
	 * Renders only raymarching objects onto the screen
	 */
	abstract void render();
	/**
	 * Prepares the render system for rendering
	 */
	abstract void prepare();
	/**
	 * Destroy all LWJGL related stuff
	 */
	abstract void destroy();
}