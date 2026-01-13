package com.engine.objects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

import com.engine.graphics.ModelTexture;
import com.engine.graphics.TerrainTexture;
import com.engine.graphics.TerrainTexturePack;
import com.engine.math.Maths;
import com.engine.utils.Loader;
import com.engine.utils.MasterLoader;

public class Terrain {
	private static final float SIZE = 1600;
	private static final float MAX_HEIGHT = 40;
	private static final float MAX_PIXEL_COLOR = 256 * 256 * 256;
	
	private float x;
	private float z;
	private RawModel model;
	private TerrainTexturePack texturePack;
	private TerrainTexture blendMap;
	private BufferedImage image;
	
	private float[][] heights;
	 
	public Terrain(int gridX, int gridZ, TerrainTexturePack terrainpack, TerrainTexture blendMap, String heightMap) {
		this.texturePack = terrainpack; 
		this.blendMap = blendMap;
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
		this.model = generateTerrain(MasterLoader.getLoader(), heightMap);
	}
	
	public float getHeightTerrain(float worldX, float worldZ) {
		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		float gridSQS = SIZE / ((float) heights.length - 1);
		int gridx = (int) Math.floor(terrainX / gridSQS);
		int gridz = (int) Math.floor(terrainZ / gridSQS);
		if (gridx >= heights.length - 1 || gridz >= heights.length - 1 || gridx < 0 || gridz < 0) {
			return -99;
		}
		float xCoord = (terrainX % gridSQS)/gridSQS;//i have no idea how this works but it does and thats all that matters
		float zCoord = (terrainZ % gridSQS)/gridSQS;
		float answer;
		if (xCoord <= (1-xCoord)) {
			answer = Maths.barryCentric(
					new Vector3f(0, heights[gridx][gridz], 0), 
					new Vector3f(1, heights[gridx + 1][gridz], 0), 
					new Vector3f(0, heights[gridx][gridz + 1], 1), 
					new Vector2f(xCoord, zCoord));
		} else {
			answer = Maths.barryCentric(
					new Vector3f(1, heights[gridx + 1][gridz], 0), 
					new Vector3f(1, heights[gridx + 1][gridz + 1], 1), 
					new Vector3f(0, heights[gridx][gridz + 1], 1), 
					new Vector2f(xCoord, zCoord));
		}
		return answer;
	}
	
	public Entity convertTerrainToEntity(Loader loader) {
		TexturedModel model = new TexturedModel(this.model, new ModelTexture(loader.loadTexture("white")));
		Entity entity = new Entity(model, new Vector3f(x, 0, z), 0, 0, 0, 1);
		return entity;
	}
	
	
	private RawModel generateTerrain(Loader loader, String heightMap){
		
		image = null;
		try {
			image = ImageIO.read(new File("res/" + heightMap + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int VERTEX_COUNT = image.getHeight();
		heights = new float[VERTEX_COUNT][VERTEX_COUNT];
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		int vertexPointer = 0;
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
				float height = getHeight(j,i,image);
				heights[j][i] = height;
				vertices[vertexPointer*3+1] = height;
				vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
				Vector3f normal = calcNorm(j, i, image);
				normals[vertexPointer*3] = normal.x;
				normals[vertexPointer*3+1] = normal.y;
				normals[vertexPointer*3+2] = normal.z;
				textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz=0;gz<VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<VERTEX_COUNT-1;gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return loader.loadToVAO(vertices, textureCoords, normals, indices, "TERRAIN Autogen model");
	}
	
	public Vector3f calcNorm(int x, int z, BufferedImage image) {
		float heightL = getHeight(x-1, z, image);
		float heightR = getHeight(x+1, z, image);
		float heightD = getHeight(x, z-1, image);
		float heightU = getHeight(x, z+1, image);
		Vector3f normal = new Vector3f(heightL-heightR, 2f, heightD - heightU);
		normal.normalise();
		return normal;
	}
	
	private float getHeight(int x, int y, BufferedImage image) {
		if (x < 0 || x >= image.getHeight() || y < 0 || y >= image.getHeight()) {
			return 0;
		}
		float height = image.getRGB(x, y);
		height += MAX_PIXEL_COLOR/2f;
		height /= MAX_PIXEL_COLOR/2f;
		height *= MAX_HEIGHT;
		return height;
	}
	
	public static float getSize() {
		return SIZE;
	}
	public float getX() {
		return x;
	}
	public float getZ() {
		return z;
	}
	public RawModel getModel() {
		return model;
	}
	public BufferedImage getHeightMap() {
		return image;
	}

	public TerrainTexturePack getTexturePack() {
		return texturePack;
	}


	public void setTexturePack(TerrainTexturePack texturePack) {
		this.texturePack = texturePack;
	}


	public TerrainTexture getBlendMap() {
		return blendMap;
	}


	public void setBlendMap(TerrainTexture blendMap) {
		this.blendMap = blendMap;
	}
}
