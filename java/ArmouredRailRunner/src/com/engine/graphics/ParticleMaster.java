package com.engine.graphics;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjglx.util.vector.Matrix4f;

import com.engine.objects.Camera;
import com.engine.objects.Particle;
import com.engine.objects.Light;
import com.engine.renderer.ParticleRenderer;
import com.engine.utils.Loader;

public class ParticleMaster {

	private static Map<ParticleTexture, List<Particle>> particles = 
			new HashMap<ParticleTexture, List<Particle>>();
	private static ParticleRenderer renderer;
	public static boolean isSetup = false;
	
	public static void init(Matrix4f projectionMatrix) {
		renderer = new ParticleRenderer(projectionMatrix);
		isSetup = true;
	}
	
	public static void update() {
		Iterator<Entry<ParticleTexture, List<Particle>>> mapIterator = particles.entrySet().iterator();
		while (mapIterator.hasNext()) {
			List<Particle> list = mapIterator.next().getValue();
			Iterator<Particle> iterator = list.iterator();
			while (iterator.hasNext()) {
				Particle p = iterator.next();
				boolean stillAlive = p.update();
				if(!stillAlive) {
					iterator.remove();
					if(list.isEmpty()) { 
						mapIterator.remove();
					}
				}
			}
		}
	}
	
	public static void renderParticles(Camera camera, Matrix4f SPM, List<Light> lights) {
		renderer.render(particles, SPM, lights, camera);
	}
	
	public static void cleanUp() {
		renderer.cleanUp();
	}
	
	public static void addParticle(Particle particle) {
		List<Particle> list = particles.get(particle.getTexture());
		if(list==null) {
			list = new ArrayList<Particle>();
			particles.put(particle.getTexture(), list);
		}
		list.add(particle);
	}
}
