package com.engine.postProcess;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.engine.objects.RawModel;
import com.engine.utils.Loader;
import com.engine.utils.MasterLoader;

import main.Engine;

public class PostProcessing {

	private static final float[] POSITIONS = { -1, 1, -1, -1, 1, 1, 1, -1 };	
	private static RawModel quad;
	private static ContrastChanger contrastChanger;
	private static BrightFilter brightFilter;
	private static HorizontalBlur hBlur;
	private static VerticalBlur vBlur;
	private static CombineFilter combineFilter;
	private static SSAOFilter SSAO;
	public static boolean isSetup = false;
	

	public static void init(){ 
		quad = MasterLoader.getLoader().loadToVAO(POSITIONS, 2, "POSTPROCESSING Autogen model");
		contrastChanger = new ContrastChanger();
		brightFilter = new BrightFilter(Engine.window.getWidth() / 5, Engine.window.getHeight() / 5);
		hBlur = new HorizontalBlur(Engine.window.getWidth()/5, Engine.window.getHeight()/5);
		vBlur = new VerticalBlur(Engine.window.getWidth()/5, Engine.window.getHeight()/5);
		combineFilter = new CombineFilter();
		SSAO = new SSAOFilter(Engine.window.getWidth(), Engine.window.getHeight());
		isSetup = true;
	}
	
	public static void doPostProcessing(Fbo fbo){
		start();
		SSAO.render(fbo);
		brightFilter.render(SSAO.getColorTexture());
		hBlur.render(brightFilter.getOutputTexture());
		vBlur.render(hBlur.getOutputTexture());
		combineFilter.render(SSAO.getColorTexture(), vBlur.getOutputTexture());
		end();
	}
	
	public static void cleanUp(){
		contrastChanger.cleanUp();
		brightFilter.cleanUp();
		hBlur.cleanUp();
		vBlur.cleanUp();
		SSAO.cleanUp();
		combineFilter.cleanUp();
	}
	
	private static void start(){
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}
	
	private static void end(){
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
}
