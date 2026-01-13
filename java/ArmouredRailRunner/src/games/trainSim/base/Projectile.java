package games.trainSim.base;

import org.lwjglx.util.vector.Vector3f;

import com.engine.objects.Entity;
import com.engine.objects.RawModel;

public class Projectile {
	public float gravityEffect = -0.01f;
	public float pennetrationDeficitPerUnit = -0.01f;
	private RawModel model;
	private Vector3f position;
	private Vector3f rotation;
	private Vector3f energy;
	private float roundSize = 0;
	private float impactExplosionValue = 0;
	private float startingPennetrationValue = 0;
	private float mass = 0;
	protected Projectile(RawModel model, float mm, float IEV, float SPV, float mass) {
		this.position = new Vector3f(0,0,0);
		this.rotation = new Vector3f(0,0,0);
		this.model = model;
		this.impactExplosionValue = IEV;
		this.mass = mass;
		this.roundSize = mm;
		this.startingPennetrationValue = SPV;
	}
}
