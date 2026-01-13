package Main;

import java.util.ArrayList;

import com.own.master.engine.graphics.Renderer;
import com.own.master.engine.math.Vector3f;
import com.own.master.engine.objects.Camera;
import com.own.master.engine.objects.Equipable;
import com.own.master.engine.objects.GameObject;
import com.own.master.engine.objects.Playerable;

public class Player implements Playerable {
	private Vector3f pos;
	private Vector3f rot;
	private int health = 100;
	private int armour = 0;
	private Camera camera;// = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));
	private GameObject model;
	private ArrayList<Equipable> items = new ArrayList<>();
	public Player(Vector3f pos, Vector3f rot, GameObject model) {
		this.pos = pos;
		this.rot = rot;
		this.camera = new Camera(pos, rot);
		this.model = model;
	}
	@Override
	public Vector3f getPos() {
		return this.pos;
	}

	@Override
	public Vector3f getRot() {
		return this.rot;
	}
	@Override
	public GameObject getModel() {
		return this.model;
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public int getArmour() {
		return armour;
	}

	@Override
	public void setPos(Vector3f pos) {
		this.pos = pos;
		this.camera.setPostition(pos);
	}

	@Override
	public void setRot(Vector3f rot) {
		this.rot = rot;
		this.camera.setRotation(rot);
	}

	@Override
	public void setModel(GameObject object) {
		this.model = object;
	}
	@Override
	public void setHealth(int value) {
		this.health = value;
	}

	@Override
	public void setArmour(int value) {
		this.armour = value;
	}

	@Override
	public void addHealth(int value) {
		this.health += value;
	}

	@Override
	public void addArmour(int value) {
		this.armour += value;
	}

	@Override
	public boolean isAI() {
		return false;
	}

	@Override
	public void spawn() {
		//this.pos = Main.map.whatever().getWorldSpawn(); this is not an actual thing yet
		//this.isRenderable = true;
	}

	@Override
	public void kill() {
		this.health = 0;
	}

	@Override
	public void destroy() {
		this.model.getMesh().destroy();
	}

	@Override
	public ArrayList<Equipable> getInventory() {
		return items;
	}

	@Override
	public void update() {
		this.pos = this.camera.getPosition();
		this.rot = this.camera.getRotation();

	}
	@Override
	public Camera getCamera() {
		return this.camera;
	}
	@Override
	public Player self() {
		return this;
	}
	@Override
	public void render(Renderer rnd) {
		rnd.renderMesh(model, 1);
	}
	@Override
	public boolean isRenderable() {
		return false;//dont need this for single player
	}
	@Override
	public void lookAt(Vector3f tar) {
		// TODO Auto-generated method stub

	}
	@Override
	public void isRenderable(boolean b) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean shouldNotSpoop() {
		// TODO Auto-generated method stub
		return false;
	}

}
