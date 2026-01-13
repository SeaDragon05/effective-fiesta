package com.own.master.engine.io;


import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.own.master.engine.math.Matrix4f;
import com.own.master.engine.math.Vector3f;

public class Window {
	private int width, height;
	private String title;
	private long window;
	private int frames;
	private static long time;
	private Input input;
	private Vector3f background = new Vector3f(1, 1, 1);
	private GLFWWindowSizeCallback sizeCallback;
	private boolean isResized;
	private boolean isFullscreen;
	private boolean shouldMoveMouse;
	private int[] windowPosX = new int[1], windowPosY = new int[1];
	private Matrix4f projection;
	private float gradient;
	private float density;

	public Window(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		projection = Matrix4f.projection(70.0f, (float) width / (float) height, 0.1f, 1000.0f);
	}

	public void create() {
		if (!GLFW.glfwInit()) {
			System.err.println("ERROR: GLFW wasn't initializied");
			return;
		}

		input = new Input();
		window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
		if (window == 0) {
			System.err.println("ERROR: Window wasn't created");
			return;
		}

		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		windowPosX[0] = (videoMode.width() - width) / 2;
		windowPosY[0] = (videoMode.height() - height) / 2;
		GLFW.glfwSetWindowPos(window, windowPosX[0], windowPosY[0]);
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		GL11.glEnable(GL11.GL_DEPTH_TEST);//this makes the draws know. They know when this is enabled. Try turning it off I dare you.

		createCallbacks();

		GLFW.glfwShowWindow(window);

		GLFW.glfwSwapInterval(0);

		time = System.currentTimeMillis();
	}
	private void createCallbacks() {
		sizeCallback = new GLFWWindowSizeCallback() {
			public void invoke(long window, int w, int h) {
				width = w;
				height = h;
				isResized = true;
			}
		};

		GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
		GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
		GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());
		GLFW.glfwSetScrollCallback(window, input.getMouseScrollCallback());
		GLFW.glfwSetWindowSizeCallback(window, sizeCallback);
	}

	public void update() {
		GL11.glClearColor(background.getX(), background.getY(), background.getZ(), 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GLFW.glfwPollEvents();
		frames++;
		if (System.currentTimeMillis() > time + 1000) {
			GLFW.glfwSetWindowTitle(window, title + " | FPS: " + frames);
			time = System.currentTimeMillis();
			frames = 0;
		}
		projection = Matrix4f.projection(Main.Main.FOV, (float) width / (float) height, 0.1f, 1000.0f);
		if (isResized) {
			GL11.glViewport(0, 0, width, height);
			isResized = false;
		}
	}

	public void clear() {
		GL11.glClearColor(background.getX(), background.getY(), background.getZ(), 1.0f);
	}

	public void swapBuffers() {
		GLFW.glfwSwapBuffers(window);
	}

	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(window);
	}

	public void destroy() {
		input.destroy();
		sizeCallback.free();
		GLFW.glfwWindowShouldClose(window);
		GLFW.glfwDestroyWindow(window);
		GLFW.glfwTerminate();
	}

	public void setBackgroundColor(float r, float g, float b) {
		background.set(r, g, b);
	}

	public boolean isFullscreen() {
		return isFullscreen;
	}

	public void setFullscreen(boolean isFullscreen) {
		this.isFullscreen = isFullscreen;
		isResized = true;
		if (isFullscreen) {
			GLFW.glfwGetWindowPos(window, windowPosX, windowPosY);
			GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, width, height, 0);
		} else {
			GLFW.glfwSetWindowMonitor(window, 0, windowPosX[0], windowPosY[0], width, height, 0);
		}
		projection = Matrix4f.projection(70.0f, (float) width / (float) height, 0.1f, 1000.0f);
	}

	public void mouseState(boolean lock) {
		GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, lock ? GLFW.GLFW_CURSOR_DISABLED : GLFW.GLFW_CURSOR_NORMAL);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String newTitle) {
		title = newTitle;
	}

	public long getWindow() {
		return window;
	}

	public Matrix4f getProjection() {
		return projection;
	}

	public void moveMouse(boolean b) {
		shouldMoveMouse = b;
	}
	public boolean moveMouse() {
		return shouldMoveMouse;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWindow(long window) {
		this.window = window;
	}

	public void setFrames(int frames) {
		this.frames = frames;
	}

	public static void setTime(long time) {
		Window.time = time;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	public void setBackgrounnd(Vector3f background) {
		this.background = background;
	}

	public void setSizeCallback(GLFWWindowSizeCallback sizeCallback) {
		this.sizeCallback = sizeCallback;
	}

	public void setResized(boolean isResized) {
		this.isResized = isResized;
	}

	public void setShouldMoveMouse(boolean shouldMoveMouse) {
		this.shouldMoveMouse = shouldMoveMouse;
	}

	public void setWindowPosX(int[] windowPosX) {
		this.windowPosX = windowPosX;
	}

	public void setWindowPosY(int[] windowPosY) {
		this.windowPosY = windowPosY;
	}

	public void setProjection(Matrix4f projection) {
		this.projection = projection;
	}

	public void setGradient(float gradient) {
		this.gradient = gradient;
	}

	public void setDensity(float density) {
		this.density = density;
	}

	public int getFrames() {
		return frames;
	}

	public static long getTime() {
		return time;
	}

	public Input getInput() {
		return input;
	}

	public Vector3f getBackground() {
		return background;
	}

	public GLFWWindowSizeCallback getSizeCallback() {
		return sizeCallback;
	}

	public boolean isResized() {
		return isResized;
	}

	public boolean isShouldMoveMouse() {
		return shouldMoveMouse;
	}

	public int[] getWindowPosX() {
		return windowPosX;
	}

	public int[] getWindowPosY() {
		return windowPosY;
	}

	public float getGradient() {
		return gradient;
	}

	public float getDensity() {
		return density;
	}

	public void setBackgroundColor(Vector3f gr_fog_color) {
		background.set(gr_fog_color);
	}
}