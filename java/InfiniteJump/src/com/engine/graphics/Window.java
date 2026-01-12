package com.engine.graphics;

import org.lwjgl.glfw.GLFW;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;

import com.engine.io.Input;

import main.Engine;

public class Window {
	private String title;
	public static boolean vsync = false;
	private static long window;
	private int frames;
	private int FPS;
	private static long time;
	private Vector3f background = new Vector3f(1, 1, 1);
	private GLFWWindowSizeCallback sizeCallback;
	private boolean isResized;
	private boolean isFullscreen;
	private boolean shouldMoveMouse;
	private int[] windowPosX = new int[1], windowPosY = new int[1];
	private Matrix4f projection;

	private Input input;

	private static long lastFrameTime;
	private static float delta;

	public Window(String title) {
		this.title = title;
	}

	public void create() {
		if (!GLFW.glfwInit()) {
			System.err.println("ERROR: GLFW wasn't initializied");
			System.err.println("Please ensure that your system is OpenGL compatable");
			System.exit(-45);
			return;
		} else {
			System.out.println("Check for errors");
		}
		
		

		GLFW.glfwWindowHint(GLFW.GLFW_STENCIL_BITS, 4);
		GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 4);
		PointerBuffer monitors = GLFW.glfwGetMonitors();
		window = GLFW.glfwCreateWindow(Engine.WIDTH, Engine.HEIGHT, title,
				isFullscreen ? GLFW.glfwGetPrimaryMonitor() : 0, 0);
		if (window == 0 || window == NULL) {
			System.err.println("ERROR: Window wasn't created");
			return;
		}
		input = new Input();
		System.out.println("Window created successfully!" + "\nDimensions: " + Engine.WIDTH + " x " + Engine.HEIGHT);

		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		windowPosX[0] = (videoMode.width() - Engine.WIDTH) / 2;
		windowPosY[0] = (videoMode.height() - Engine.HEIGHT) / 2;
		GLFW.glfwSetWindowPos(window, windowPosX[0], windowPosY[0]);
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		GL11.glEnable(GL11.GL_DEPTH_TEST);// this makes the draws know. They know when this is enabled. Try turning it
											// off I dare you.
		GL11.glEnable(GL13.GL_MULTISAMPLE);

		// new PixelFormat().withSamples(8).withDepthBits(24);

		createCallbacks();


		//GL30.glPolygonMode(GL30.GL_FRONT_AND_BACK, GL30.GL_LINE); 

		GLFW.glfwShowWindow(window);

		if (vsync) {
			GLFW.glfwSwapInterval(1);
			System.out.println("V-sync is ON");
		} else {
			GLFW.glfwSwapInterval(0);
			System.out.println("V-sync is OFF");
		}

		time = System.currentTimeMillis();
	}

	private void createCallbacks() {
		sizeCallback = new GLFWWindowSizeCallback() {
			public void invoke(long window, int w, int h) {
				Engine.WIDTH = w;
				Engine.HEIGHT = h;
				isResized = true;
			}
		};
		GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
		GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
		GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());
		GLFW.glfwSetScrollCallback(window, input.getMouseScrollCallback());
		GLFW.glfwSetWindowSizeCallback(window, sizeCallback);
	}

	public float getFrameTimeSeconds() {
		return delta / 1000;
	}

	public void update() {
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GLFW.glfwPollEvents();
		frames++;
		if (System.currentTimeMillis() > time + 1000) {
			GLFW.glfwSetWindowTitle(window, title + " | FPS: " + frames + " | " + Engine.WIDTH + " x " + Engine.HEIGHT);
			time = System.currentTimeMillis();
			FPS = frames;
			frames = 0;
		}
		lastFrameTime = currentFrameTime;
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

	private static long getCurrentTime() {
		return (long) (GLFW.glfwGetTime() * 1000);
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
			GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, Engine.WIDTH, Engine.HEIGHT, 0);
		} else {
			GLFW.glfwSetWindowMonitor(window, 0, windowPosX[0], windowPosY[0], Engine.WIDTH, Engine.HEIGHT, 0);
		}
		// projection = Matrix4f.projection(70.0f, (float) width / (float) height, 0.1f,
		// 1000.0f);
	}

	public void mouseState(boolean lock) {
		GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, lock ? GLFW.GLFW_CURSOR_DISABLED : GLFW.GLFW_CURSOR_NORMAL);
	}

	public int getFPS() {
		return FPS;
	}

	public int getWidth() {
		return Engine.WIDTH;
	}

	public int getHeight() {
		return Engine.HEIGHT;
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
		Engine.WIDTH = width;
	}

	public void setHeight(int height) {
		Engine.HEIGHT = height;
	}

	public void setFrames(int frames) {
		this.frames = frames;
	}

	public static void setTime(long time) {
		Window.time = time;
	}

	//public void setInput(Input input) {
	//	this.input = input;
//	}

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

	public int getFrames() {
		return frames;
	}

	public static long getTime() {
		return time;
	}

	//public Input getInput() {
	//	return input;
	//}

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

	public void setBackgroundColor(Vector3f gr_fog_color) {
		background.set(gr_fog_color);
	}
}