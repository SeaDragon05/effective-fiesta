package main;

import java.util.ArrayList;
import java.util.List;

import org.lwjglx.util.vector.Vector3f;

import com.engine.objects.Entity;
import com.engine.objects.Player;
import com.engine.objects.RawModel;
import com.engine.objects.TexturedModel;
import com.engine.textures.ModelTexture;
import com.engine.utils.MasterLoader;
import com.engine.utils.OBJLoader;

public class LevelMaster {
	private static final Vector3f startingPoint = new Vector3f(0,0,0);

	private static List<Vector3f> positions0_50 = new ArrayList<Vector3f>();
	private static List<Vector3f> positions51_100 = new ArrayList<Vector3f>();
	
	public static void createLevel(int difficultyLevel) {
		int blocks = 50 * difficultyLevel;
		RawModel model = MasterLoader.loadRawModel("models/model");
		ModelTexture texture1 = new ModelTexture(MasterLoader.loadTexturePointer("textures/cube1/texture"));
		ModelTexture texture2 = new ModelTexture(MasterLoader.loadTexturePointer("textures/cube2/texture"));
		ModelTexture texture3 = new ModelTexture(MasterLoader.loadTexturePointer("textures/cube3/texture"));
		texture1.setShineDamper(10);texture1.setReflectivity(1);texture2.setShineDamper(10);
		texture2.setReflectivity(1);texture3.setShineDamper(10);texture3.setReflectivity(1);
		TexturedModel t1 = new TexturedModel(model, texture1);
		TexturedModel t2 = new TexturedModel(model, texture2);
		TexturedModel t3 = new TexturedModel(model, texture2);
		float height = 0;
		float x = 0, z = 0;
		for (int i = 0; i < blocks; i++) {
			height += 7;
			Entity entity = new Entity(t1, new Vector3f(0,0,0), new Vector3f(0,0,0), new Vector3f(2,2,2));
			entity.getPosition().y = height;
			entity.getPosition().x += x;
			entity.getPosition().z += z;
			x += -2* (Math.random() * 2 * (1+x));
			z += -2*(Math.random() * 2 * (1+z));
			Engine.entities.add(entity);
			addPosition(entity.getPosition());
		}
	}
	
	private static void addPosition(Vector3f position) {
		if (position.y < 50) {
			positions0_50.add(position);
		} else if (position.y < 100) {
			positions51_100.add(position);
		}
		
	}

	public static void update(Player player) {
		if (player.getPosition().y < 50) {
			float height = -99;
			for (Vector3f cube : positions0_50) {
				height = Math.max(height, player.collision3d(cube, 2));
			}
			player.setCollisionHeight(height);
		} else if (player.getPosition().y < 100) {
			float height = -99;
			for (Vector3f cube : positions51_100) {
				height = Math.max(height, player.collision3d(cube, 2));
			}
			player.setCollisionHeight(height);
		}
	}
}
