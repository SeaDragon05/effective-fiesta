package engine.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Material {
	private String path;
	private Texture texture;
	private float width, height;
	private int textureID;
	private float shineDamper = 10f;
	private float reflectivity = 0f;
	private String name;
	
	public Material(String path, float reflectivity) {
		this.path = path;
		this.name = path;
		this.reflectivity = reflectivity;
	}
	
	public void create() {
		try {
			texture = TextureLoader.getTexture(path.split("[.]")[1], Material.class.getResourceAsStream(path), GL11.GL_NEAREST);//use LINEAR or NEAREST
			this.width = texture.getWidth();
			this.height = texture.getHeight();
			this.textureID = texture.getTextureID();
		} catch (Exception e) {
			System.err.println("Cannot find texture! " + e + " at " + path);
		}
	}
	
	public void destroy() {
		GL13.glDeleteTextures(textureID);
	}
	
	public String getName() {
		return name;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}

	public Texture getTexture() {
		return texture;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public int getTextureID() {
		return textureID;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
}
