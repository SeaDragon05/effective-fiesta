package com.own.master.horrorgame.ai;


import com.own.master.engine.graphics.Mesh;
import com.own.master.engine.math.Vector3f;
import com.own.master.engine.objects.GameObject;
import com.own.master.engine.objects.Playerable;
import com.own.master.engine.utils.FileUtils;
import com.own.master.horrorgame.objects.Spoops;

import Main.Main;

public class AI {
	private static Mesh spoopModel = FileUtils.loadOBJ("./models/spoop.obj", "/textures/ART.JPG", new Vector3f(0, 0, 0),
			new Vector3f(0.1f, 0.1f, 0.1f), 10, 10);
	private static GameObject model;

	public AI() {
		model = new GameObject(null, null, null, spoopModel);
		model.getMesh().create();//every spoop will use the same model in memory to save memory
	}

	public Playerable getRandomTarget() {// single player for the time, so just get the player object
		return Main.player;
	}

	public void move(Spoops spoop) {
		float ms = 0.1f;
		float pd = 0.1f + ms;
		float nd = -0.1f - ms;
		if (spoop.isRenderable()) {
			if (spoop.shouldNotSpoop()) {
				spoop.isRenderable(false);
			} else {
				// teleport towards the target:
				Vector3f pos = spoop.getPos();
				Vector3f tar = spoop.getTarget().getPos();
				float dx = (pos.getX() - tar.getX());
				float dy = (pos.getY() - tar.getY());
				float dz = (pos.getZ() - tar.getZ());

				float distance = (float) Math.sqrt((dx * dx) + (dy * dy) + (dz * dz));
				if (distance > 0.5) {
					float mx, my, mz;
					if (spoop.getPos().getX() > spoop.getTarget().getPos().getX() + pd) {
						mx = pos.getX() - ms;
					} else if (spoop.getPos().getX() < spoop.getTarget().getPos().getX() + nd) {
						mx = pos.getX() + ms;
					} else {
						mx = pos.getX();
					}

					if (spoop.getPos().getY() > spoop.getTarget().getPos().getY() + pd) {
						my = pos.getY() - ms;
					} else if (spoop.getPos().getY() < spoop.getTarget().getPos().getY() + nd) {
						my = pos.getY() + ms;
					} else {
						my = pos.getY();
					}

					if (spoop.getPos().getZ() > spoop.getTarget().getPos().getZ() + pd) {
						mz = pos.getZ() - ms;
					} else if (spoop.getPos().getZ() < spoop.getTarget().getPos().getZ() + nd) {
						mz = pos.getZ() + ms;
					} else {
						mz = pos.getZ();
					}

					spoop.setPos(new Vector3f(mx, my, mz));

				}

				// now to make it look at its target for more spoopy:
				spoop.lookAt(tar);

			}
		}
	}
	public void destroy() {
		spoopModel.destroy();
	}
}
