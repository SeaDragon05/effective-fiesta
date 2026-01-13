package com.engine.graphics;

public class ModelTexture {
	private int textureID;
	private int normalMap;
	private int roughMap;
	private int reflectiveMap;
	
	private float shineDamper = 1;
	private float reflectivity = 0;
	private float roughness = 0;
	private float normalAmount = 1;
	
	private boolean hasTransparency = false;
	private boolean useFakeLighting = false;
	private boolean hasRoughMap = false;
	private boolean hasNormalMap = false;
	private boolean hasReflectiveMap = false;
	
	private int numberOfRows = 1;
	
	public ModelTexture(int id) {
		textureID = id;
	}
	
	public int getID() {
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
	public boolean isHasTransparency() {
		return hasTransparency;
	}
	public void setHasTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
	}
	public boolean isUseFakeLighting() {
		return useFakeLighting;
	}
	public void setUseFakeLighting(boolean useFakeLighting) {
		this.useFakeLighting = useFakeLighting;
	}


	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public boolean hasNormalMap() {
		return hasNormalMap;
	}
	
	public int getNormalMap() {
		return normalMap;
	}

	public void setNormalMap(int normalMap) {
		this.hasNormalMap = true;
		this.normalMap = normalMap;
	}
	
	public int getRoughMap() {
		return this.roughMap;
	}
	
	public float getNormalAmount() {
		return this.normalAmount;
	}
	
	public void setNormalAmount(float amount) {
		//amount = (amount > 1 ? 1 : amount);
		//amount = (amount < 0 ? 0 : amount);
		//do not question the above code
		//you can now question it
		this.normalAmount = amount;
	}
	
	public void setRoughMap(int num) {
		hasRoughMap = true;
		this.roughMap = num;
	}
	
	public boolean hasRoughMap() {
		return hasRoughMap;
	}

	public float getRoughness() {
		return roughness;
	}

	public void setRoughness(float roughness) {
		this.roughness = roughness;
	}

	public boolean hasReflectiveMap() {
		return this.hasReflectiveMap;
	}
	
	public int getReflectiveMap() {
		return this.reflectiveMap;
	}
	
	public void setReflectiveMap(int reflectiveMap) {
		this.hasReflectiveMap = true;
		this.reflectiveMap = reflectiveMap;
	}

	public void cleanUp() {
		
	}
}
