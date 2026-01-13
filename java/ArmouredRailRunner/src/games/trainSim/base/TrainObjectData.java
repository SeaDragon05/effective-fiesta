package games.trainSim.base;

import org.lwjglx.util.vector.Vector3f;

import com.engine.objects.RawModel;

public class TrainObjectData {
	/*
	 * data object to hold all information about a train object when loading into the game
	 * makes it easy to make content when everything is simply laid out rather than programming the specs of everything into one java file
	 */
	public static final int TYPE_STATICMODEL = 1;
	public static final int TYPE_TRUCK = 2;
	public static final int TYPE_WHEEL = 3;
	private int type;
	private String modelUrl = null;
	private String textureUrl = null;
	private String roughnessUrl = null;
	private String reflectiveUrl = null;
	private String normalUrl = null;
	private float modelScale = 1;
	private Vector3f offset = new Vector3f(0,0,0);
	private Vector3f offset2 = new Vector3f(0,0,0);
	private Vector3f offset3 = new Vector3f(0,0,0);
	private int useCount = 1;
	private float roughness = 0;
	private float reflectivity = 0;
	private boolean hasNormals = false;
	private boolean hasReflMap = false;
	private boolean hadRougMap = false;
	public TrainObjectData(int type, String modelUrl, String textureUrl) {
		this.type = type;
		this.modelUrl = modelUrl;
		this.textureUrl = textureUrl;
	}
	public int getType() {
		return this.type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getModelUrl() {
		return modelUrl;
	}
	public void setModelUrl(String modelUrl) {
		this.modelUrl = modelUrl;
	}
	public String getTextureUrl() {
		return textureUrl;
	}
	public void setTextureUrl(String textureUrl) {
		this.textureUrl = textureUrl;
	}
	public String getRoughnessUrl() {
		return roughnessUrl;
	}
	public void setRoughnessUrl(String roughnessUrl) {
		this.hadRougMap = true;
		this.roughnessUrl = roughnessUrl;
	}
	public String getReflectiveUrl() {
		return reflectiveUrl;
	}
	public void setReflectiveUrl(String reflectiveUrl) {
		this.hasReflMap = true;
		this.reflectiveUrl = reflectiveUrl;
	}
	public String getNormalUrl() {
		return normalUrl;
	}
	public void setNormalUrl(String normalUrl) {
		this.hasNormals = true;//very important
		this.normalUrl = normalUrl;
	}
	public float getRoughness() {
		return roughness;
	}
	public void setRoughness(float roughness) {
		this.roughness = roughness;
	}
	public float getReflectivity() {
		return reflectivity;
	}
	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
	public boolean hasNormalMap() {
		return hasNormals;
	}
	public float getModelScale() {
		return modelScale;
	}
	public void setModelScale(float modelScale) {
		this.modelScale = modelScale;
	}
	public Vector3f getOffset() {
		return offset;
	}
	public void setOffset(Vector3f offset) {
		this.offset = offset;
	}
	@Override
	public String toString() {
		String result = "Data:\n";
		result += modelUrl + "\n";
		result += textureUrl;
		return result;
	}
	public Vector3f getOffset2() {
		return offset2;
	}
	public void setOffset2(Vector3f offset2) {
		this.offset2 = offset2;
	}
	public Vector3f getOffset3() {
		return offset3;
	}
	public void setOffset3(Vector3f offset3) {
		this.offset3 = offset3;
	}
	public int getUseCount() {
		return useCount;
	}
	public void setUseCount(int useCount) {
		this.useCount = useCount;
	}
	public boolean isHasReflMap() {
		return hasReflMap;
	}
	public boolean isHadRougMap() {
		return hadRougMap;
	}
}
