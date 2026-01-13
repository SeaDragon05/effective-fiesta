package games.trainSim.base;

import java.util.ArrayList;
import java.util.List;

import org.lwjglx.util.vector.Vector3f;

import com.engine.graphics.ModelTexture;
import com.engine.objects.Entity;
import com.engine.objects.TexturedModel;
import com.engine.utils.Loader;
import com.engine.utils.OBJLoader;
import com.engine.utils.NormalMappedObjLoader;
/*
 * simple class tht just sets up the trains
 * preset loadouts for various train objects
 * 
 * 
 * DO NOT UPDATE THIS CLASS AS IT IS OLD AND IS NO LONGER SUPPORTED
 */
public class Train {
	/*
	 * commented out everything so that the compiler doesn't scream
	private List<TrainCar> trainCars = new ArrayList<TrainCar>();
	private float distance;

	public Train() {
	}
	
	public boolean canMove() {
		return this.trainCars.get(0).isEngineOn();
	}
	public void turnEngineOn() {
		this.trainCars.get(0).turnEndgineOn();
	}
	public Train(int type) {
		if (type == 0) {
			Vector3f position = new Vector3f(0, 18, 0);
			Vector3f rotation = new Vector3f(0, 0, 0);
			ModelTexture texture = null;
			ModelTexture old = null;
			TexturedModel trainModel = null;
			if (loader != null) {

				old = new ModelTexture(loader.loadTexture("tank1texture"));
				texture = new ModelTexture(loader.loadTexture("/gameModels/Dragonmk2/texture"));

				texture.setHasTransparency(false);
				texture.setShineDamper(2f);
				texture.setReflectivity(0.1f);
				texture.setRoughness(0.03f);
				trainModel = new TexturedModel(OBJLoader.loadObjModel("/gameModels/Dragonmk2/model", loader), texture);
			}
			Vector3f bedOffset = new Vector3f(0, 2.8f, 0);
			TexturedModel WheelModel = null;
			if (loader != null) {
				WheelModel = new TexturedModel(OBJLoader.loadObjModel("/gameModels/car2slot/wheelsTest2", loader),
						old);
			}
			List<Vector3f> offSets = new ArrayList<Vector3f>();
			offSets.add(new Vector3f(0, -0.7f, -3.97f));
			offSets.add(new Vector3f(0, -0.7f, 0));
			offSets.add(new Vector3f(0, -0.7f, 3.959f));
			TexturedModel wheel1Model = null;
			TexturedModel wheel2Model = null;
			if (loader != null) {
				wheel1Model = new TexturedModel(OBJLoader.loadObjModel("/gameModels/car2slot/frontN", loader), old);
				wheel2Model = new TexturedModel(OBJLoader.loadObjModel("/gameModels/car2slot/backN", loader), old);
			}
			Vector3f wheel1Offset = new Vector3f(0, 2f, 23.37f);
			Vector3f wheel2Offset = new Vector3f(0, 2f, -23.37f);
			Entity body = new Entity(trainModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1);
			List<Entity> wheelList = new ArrayList<Entity>();
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			Entity wheel1 = new Entity(wheel1Model, 0, Vector3f.add(position, wheel1Offset, null), 0, 0, 0, 1);
			Entity wheel2 = new Entity(wheel2Model, 0, Vector3f.add(position, wheel2Offset, null), 0, 0, 0, 1);
			// TrainCar wagon = new TrainCar(body, bedOffset, wheel, offSets, wheel2,
			// wheel1Offset, offSets, wheel2, wheel2Offset, null, null, null, null,
			// TrainCar.TYPE_WAGON, 0, 0, 2, false,
			// 0, 0, 100, 0, position, rotation);

			TrainCar engine = new TrainCar(0, body, bedOffset, wheelList, offSets, wheel1, wheel1Offset, offSets,
					wheel2, wheel2Offset, null, null, null, null, TrainCar.TYPE_WAGON, 0, 0, 2, false, 0, 0, 100, 0,
					position, rotation,0,0);
			engine.pushToMainList();
			trainCars.add(engine);
		} else if (type == 1) {
			Vector3f position = new Vector3f(0, 18, 0);
			Vector3f rotation = new Vector3f(0, 0, 0);
			ModelTexture texture = null;
			TexturedModel bedModel = null;
			if (loader != null) {
				texture = new ModelTexture(loader.loadTexture("tank1texture"));

				texture.setHasTransparency(false);
				texture.setShineDamper(2f);
				texture.setReflectivity(0.1f);
				texture.setRoughness(0.03f);
				bedModel = new TexturedModel(OBJLoader.loadObjModel("/gameModels/detailedflat", loader), texture);
			}
			Vector3f bedOffset = new Vector3f(0, 4, 0);
			TexturedModel WheelModel = null;
			if (loader != null) {
				WheelModel = new TexturedModel(OBJLoader.loadObjModel("/gameModels/car2slot/wheelsTest2", loader),
						texture);
			}
			List<Vector3f> offSets = new ArrayList<Vector3f>();
			offSets.add(new Vector3f(0, -0.6f, -3.97f));
			offSets.add(new Vector3f(0, -0.6f, 0));
			offSets.add(new Vector3f(0, -0.6f, 3.959f));
			TexturedModel wheel1Model = null;
			TexturedModel wheel2Model = null;
			if (loader != null) {
				wheel1Model = new TexturedModel(OBJLoader.loadObjModel("/gameModels/car2slot/frontN", loader), texture);
				wheel2Model = new TexturedModel(OBJLoader.loadObjModel("/gameModels/car2slot/backN", loader), texture);
			}
			Vector3f wheel1Offset = new Vector3f(0, 2f, 25f);
			Vector3f wheel2Offset = new Vector3f(0, 2f, -25f);
			Entity body = new Entity(bedModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1);
			List<Entity> wheelList = new ArrayList<Entity>();
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			Entity wheel1 = new Entity(wheel1Model, 0, Vector3f.add(position, wheel1Offset, null), 0, 0, 0, 1);
			Entity wheel2 = new Entity(wheel2Model, 0, Vector3f.add(position, wheel2Offset, null), 0, 0, 0, 1);
			// TrainCar wagon = new TrainCar(body, bedOffset, wheel, offSets, wheel2,
			// wheel1Offset, offSets, wheel2, wheel2Offset, null, null, null, null,
			// TrainCar.TYPE_WAGON, 0, 0, 2, false,
			// 0, 0, 100, 0, position, rotation);

			TrainCar wagon = new TrainCar(0, body, bedOffset, wheelList, offSets, wheel1, wheel1Offset, offSets, wheel2,
					wheel2Offset, null, null, null, null, TrainCar.TYPE_WAGON, 0, 0, 2, false, 0, 0, 100, 0, position,
					rotation,0,0);
			wagon.pushToMainList();
			trainCars.add(wagon);
		} else if (type == 2) {
			Vector3f position = new Vector3f(0, 18, 0);
			Vector3f rotation = new Vector3f(0, 0, 0);
			ModelTexture texture = new ModelTexture(loader.loadTexture("tank1texture"));
			texture.setHasTransparency(false);
			texture.setShineDamper(2f);
			texture.setReflectivity(0.1f);
			texture.setRoughness(0.03f);
			TexturedModel bedModel = new TexturedModel(OBJLoader.loadObjModel("/gameModels/car2slot/longbed", loader),
					texture);
			Vector3f bedOffset = new Vector3f(0, 4, 0);
			TexturedModel WheelModel = new TexturedModel(
					OBJLoader.loadObjModel("/gameModels/car2slot/wheelsTest2", loader), texture);
			List<Vector3f> offSets = new ArrayList<Vector3f>();
			offSets.add(new Vector3f(0, -0.6f, -3.97f));
			offSets.add(new Vector3f(0, -0.6f, 0));
			offSets.add(new Vector3f(0, -0.6f, 3.959f));
			TexturedModel wheel1Model = new TexturedModel(OBJLoader.loadObjModel("/gameModels/car2slot/frontN", loader),
					texture);
			Vector3f wheel1Offset = new Vector3f(0, 2f, 20.1f);
			TexturedModel wheel2Model = new TexturedModel(OBJLoader.loadObjModel("/gameModels/car2slot/backN", loader),
					texture);
			Vector3f wheel2Offset = new Vector3f(0, 2f, -20.1f);
			Entity body = new Entity(bedModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1);
			List<Entity> wheelList = new ArrayList<Entity>();
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			Entity wheel1 = new Entity(wheel1Model, 0, Vector3f.add(position, wheel1Offset, null), 0, 0, 0, 1);
			Entity wheel2 = new Entity(wheel2Model, 0, Vector3f.add(position, wheel2Offset, null), 0, 0, 0, 1);
			// TrainCar wagon = new TrainCar(body, bedOffset, wheel, offSets, wheel2,
			// wheel1Offset, offSets, wheel2, wheel2Offset, null, null, null, null,
			// TrainCar.TYPE_WAGON, 0, 0, 2, false,
			// 0, 0, 100, 0, position, rotation);

			TrainCar wagon = new TrainCar(0, body, bedOffset, wheelList, offSets, wheel1, wheel1Offset, offSets, wheel2,
					wheel2Offset, null, null, null, null, TrainCar.TYPE_WAGON, 0, 0, 2, false, 0, 0, 100, 0, position,
					rotation,0,0);
			wagon.pushToMainList();
			trainCars.add(wagon);
		} else if (type == 3){
			Vector3f position = new Vector3f(0, 18, 0);
			Vector3f rotation = new Vector3f(0, 0, 0);
			ModelTexture texture = new ModelTexture(loader.loadTexture("tank1texture"));
			ModelTexture wagonTexture = new ModelTexture(loader.loadTexture("/gameModels/detailedCar/textureSnowy"));
			//wagonTexture.setRoughMap(loader.loadTexture("/gameModels/roughness"));
			texture.setHasTransparency(false);
			texture.setShineDamper(2f);
			texture.setReflectivity(0.1f);
			texture.setRoughness(0.03f);
			TexturedModel bedModel = new TexturedModel(OBJLoader.loadObjModel("/gameModels/detailedCar/model", loader),
					wagonTexture);
			Vector3f bedOffset = new Vector3f(0, 3, 0);
			TexturedModel WheelModel = new TexturedModel(
					OBJLoader.loadObjModel("/gameModels/car2slot/wheelsTest2", loader), texture);
			List<Vector3f> offSets = new ArrayList<Vector3f>();
			offSets.add(new Vector3f(0, -0.6f, -3.97f));
			offSets.add(new Vector3f(0, -0.6f, 0));
			offSets.add(new Vector3f(0, -0.6f, 3.959f));
			TexturedModel wheel1Model = new TexturedModel(OBJLoader.loadObjModel("/gameModels/car2slot/frontN", loader),
					texture);
			Vector3f wheel1Offset = new Vector3f(0, 2f, 17f);
			TexturedModel wheel2Model = new TexturedModel(OBJLoader.loadObjModel("/gameModels/car2slot/backN", loader),
					texture);
			Vector3f wheel2Offset = new Vector3f(0, 2f, -17f);
			Entity body = new Entity(bedModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1);
			List<Entity> wheelList = new ArrayList<Entity>();
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			Entity wheel1 = new Entity(wheel1Model, 0, Vector3f.add(position, wheel1Offset, null), 0, 0, 0, 1);
			Entity wheel2 = new Entity(wheel2Model, 0, Vector3f.add(position, wheel2Offset, null), 0, 0, 0, 1);
			TrainCar wagon = new TrainCar(0, body, bedOffset, wheelList, offSets, wheel1, wheel1Offset, offSets,
					wheel2, wheel2Offset, null, null, null, null, TrainCar.TYPE_WAGON, 0, 0, 2, false, 0, 0, 100, 0,
					position, rotation,0,0);
			wagon.pushToMainList();
			trainCars.add(wagon);
		} else if (type ==4) {
			Vector3f position = new Vector3f(0, 18, 0);
			Vector3f rotation = new Vector3f(0, 0, 0);
			ModelTexture texture = null;
			ModelTexture old = null;
			TexturedModel trainModel = null;
			TexturedModel detail = null;
			String textureSize = "4096";
			String modelLOD = "/models/lod_0/";
			//" + modelLOD + "/" + "
			if (loader != null) {

				old = new ModelTexture(loader.loadTexture("/gameModels/ALCO_M-K_PA-4/truckTexture"));
				old.setReflectiveMap(loader.loadTexture("/gameModels/ALCO_M-K_PA-4/truckSpecular"));
				old.setRoughMap(loader.loadTexture("/gameModels/ALCO_M-K_PA-4/truckSpecular"));

				old.setHasTransparency(false);
				old.setShineDamper(4f);
				old.setReflectivity(0.8f);
				old.setRoughness(0.5f);
				
				texture = new ModelTexture(loader.loadTexture("/trains/alco_pa-1/textures/frontTexture" + textureSize));
				texture.setReflectiveMap(loader.loadTexture("/trains/alco_pa-1/textures/frontshiny" + textureSize));
				texture.setRoughMap(loader.loadTexture("/trains/alco_pa-1/textures/frontshiny" + textureSize));

				//texture.setNormalAmount(0.1f);
				//texture.setHasTransparency(false);
				//texture.setShineDamper(1f);
				//texture.setReflectivity(0.8f);
				//texture.setRoughness(1);
				
				//trainModel = new TexturedModel(NormalMappedObjLoader.loadObjModel("/gameModels/ALCO_M-K_PA-4/newBody", loader), texture);
				trainModel = new TexturedModel(NormalMappedObjLoader.loadObjModel("/trains/alco_pa-1/" + modelLOD + "front", loader), texture);
				trainModel.getTexture().setNormalMap(loader.loadTexture("/trains/alco_pa-1/textures/frontnormal" + textureSize));
				trainModel.getTexture().setReflectivity(0.8f);
				trainModel.getTexture().setRoughness(0.6f);
			}
			Vector3f bedOffset = new Vector3f(0, 0f, 0);
			TexturedModel WheelModel = null;
			ModelTexture wheelTexture = null; 
			if (loader != null) {
				wheelTexture = new ModelTexture(loader.loadTexture("tank1texture"));
				WheelModel = new TexturedModel(OBJLoader.loadObjModel("/gameModels/ALCO_M-K_PA-4/axle", loader),
						wheelTexture);
			}
			List<Vector3f> offSets = new ArrayList<Vector3f>();
			offSets.add(new Vector3f(0, 0.24f, -10));
			offSets.add(new Vector3f(0, 0.24f, 0));
			offSets.add(new Vector3f(0, 0.24f, 10));
			TexturedModel wheel1Model = null;
			TexturedModel wheel2Model = null;
			if (loader != null) {
				wheel1Model = new TexturedModel(OBJLoader.loadObjModel("/gameModels/ALCO_M-K_PA-4/truck", loader), old);
				wheel2Model = new TexturedModel(OBJLoader.loadObjModel("/gameModels/ALCO_M-K_PA-4/truck", loader), old);
			}
			Vector3f wheel1Offset = new Vector3f(0, 4f, 23.37f);
			Vector3f wheel2Offset = new Vector3f(0, 30f, -23.37f);
			TexturedModel gunModel = null;
			TexturedModel cabin = null;
			TexturedModel cabinDetail = null;
			if (loader != null) {
				ModelTexture bodytex = new ModelTexture(loader.loadTexture("/trains/alco_pa-1/textures/body" + textureSize));
				bodytex.setReflectiveMap(loader.loadTexture("/trains/alco_pa-1/textures/bodyshiny" + textureSize));
				bodytex.setRoughMap(loader.loadTexture("/trains/alco_pa-1/textures/bodyshiny" + textureSize));
				bodytex.setReflectivity(0.8f);
				bodytex.setRoughness(0.6f);
				bodytex.setNormalMap(loader.loadTexture("/trains/alco_pa-1/textures/bodynormal" + textureSize));
				gunModel = new TexturedModel(NormalMappedObjLoader.loadObjModel("/trains/alco_pa-1/" + modelLOD + "body", loader), bodytex);
				
				ModelTexture bodydetail = new ModelTexture(loader.loadTexture("/trains/alco_pa-1/textures/detail" + textureSize));
				bodydetail.setReflectivity(0.8f);
				bodydetail.setRoughness(0.6f);
				detail = new TexturedModel(NormalMappedObjLoader.loadObjModel("/trains/alco_pa-1/" + modelLOD + "details", loader), bodydetail);
				cabin = new TexturedModel(OBJLoader.loadObjModel("/trains/alco_pa-1/" + modelLOD + "cabin", loader), new ModelTexture(loader.loadTexture("/trains/alco_pa-1/textures/cabinTexture" + textureSize)));
				
				ModelTexture cabinTex = new ModelTexture(loader.loadTexture("/trains/alco_pa-1/textures/cabindetail" + textureSize));
				cabinTex.setRoughMap(loader.loadTexture("/trains/alco_pa-1/textures/cabindetailroughness" + textureSize));
				cabinTex.setReflectiveMap(loader.loadTexture("/trains/alco_pa-1/textures/cabindetailroughness" + textureSize));
				cabinDetail = new TexturedModel(OBJLoader.loadObjModel("/trains/alco_pa-1/" + modelLOD + "cabindetail", loader), cabinTex);
			}
			//Vector3f gunModelOffset = new Vector3f(-1.7803f, 17.46f, 29.297f);
			Vector3f gunModelOffset = new Vector3f(0,0,0);
			Entity body = new Entity(trainModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1);
			List<Entity> wheelList = new ArrayList<Entity>();
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			Entity wheel1 = new Entity(wheel1Model, 0, Vector3f.add(position, wheel1Offset, null), 0, 0, 0, 1);
			Entity wheel2 = new Entity(wheel2Model, 0, Vector3f.add(position, wheel2Offset, null), 0, 0, 0, 1);
			TrainCar engine = new TrainCar(0, body, bedOffset, wheelList, offSets, wheel1, wheel1Offset, offSets,
					wheel2, wheel2Offset, null, null, null, null, TrainCar.TYPE_WAGON, 2, 0, 2, false, 0, 0, 100, 0,
					position, rotation,40,40);
			//addiditional models and things
			Entity bodything = new Entity(gunModel, 0, Vector3f.add(position, gunModelOffset, null), 0, 180, 0, 1);
			engine.setModel0(bodything);
			engine.setModel0Offset(new Vector3f(0,0,0));
			Entity detailthing = new Entity(detail, 0, Vector3f.add(position, gunModelOffset, null), 0, 180, 0, 1);
			engine.setModel1(detailthing);
			engine.setModel1Offset(new Vector3f(0,0,0));
			Entity cabinthing = new Entity(cabin, 0, Vector3f.add(position, gunModelOffset, null), 0, 180, 0, 1);
			engine.setModel2(cabinthing);
			engine.setModel2Offset(new Vector3f(0,0,0));
			Entity cabinthing2 = new Entity(cabinDetail, 0, Vector3f.add(position, gunModelOffset, null), 0, 180, 0, 1);
			engine.setModel3(cabinthing2);
			engine.setModel3Offset(new Vector3f(0,0,0));
			engine.pushToMainList();
			trainCars.add(engine);
		} else {
			System.err.println("oof");
		}
	}

	public void addWagon(Loader loader, int type) {
		if (type == 0) {
			Vector3f position = new Vector3f(0, 18, 0);
			Vector3f rotation = new Vector3f(0, 0, 0);
			ModelTexture texture = new ModelTexture(loader.loadTexture("tank1texture"));
			texture.setHasTransparency(false);
			texture.setShineDamper(2f);
			texture.setReflectivity(0.1f);
			texture.setRoughness(0.03f);
			TexturedModel bedModel = new TexturedModel(OBJLoader.loadObjModel("/gameModels/detailedflat", loader),
					texture);
			Vector3f bedOffset = new Vector3f(0, 4, 0);
			TexturedModel WheelModel = new TexturedModel(
					OBJLoader.loadObjModel("/gameModels/car2slot/wheelsTest2", loader), texture);
			List<Vector3f> offSets = new ArrayList<Vector3f>();
			offSets.add(new Vector3f(0, -0.6f, -3.97f));
			offSets.add(new Vector3f(0, -0.6f, 0));
			offSets.add(new Vector3f(0, -0.6f, 3.959f));
			TexturedModel wheel1Model = new TexturedModel(OBJLoader.loadObjModel("/gameModels/car2slot/frontN", loader),
					texture);
			Vector3f wheel1Offset = new Vector3f(0, 2f, 25f);
			TexturedModel wheel2Model = new TexturedModel(OBJLoader.loadObjModel("/gameModels/car2slot/backN", loader),
					texture);
			Vector3f wheel2Offset = new Vector3f(0, 2f, -25f);
			Entity body = new Entity(bedModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1);
			List<Entity> wheelList = new ArrayList<Entity>();
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			Entity wheel1 = new Entity(wheel1Model, 0, Vector3f.add(position, wheel1Offset, null), 0, 0, 0, 1);
			Entity wheel2 = new Entity(wheel2Model, 0, Vector3f.add(position, wheel2Offset, null), 0, 0, 0, 1);
			// TrainCar wagon = new TrainCar(body, bedOffset, wheel, offSets, wheel2,
			// wheel1Offset, offSets, wheel2, wheel2Offset, null, null, null, null,
			// TrainCar.TYPE_WAGON, 0, 0, 2, false,
			// 0, 0, 100, 0, position, rotation);
			float distance = -(Math.abs(trainCars.get(trainCars.size() - 1).getWheel1Offset().z)
					+ Math.abs(wheel2Offset.z)
					+ Math.abs(trainCars.get(trainCars.size() - 1).getDistance())
					+ 14.223f);
			TrainCar wagon = new TrainCar(distance, body, bedOffset, wheelList, offSets, wheel1, wheel1Offset, offSets,
					wheel2, wheel2Offset, null, null, null, null, TrainCar.TYPE_WAGON, 0, 0, 2, false, 0, 0, 100, 0,
					position, rotation,25,25);
			wagon.pushToMainList();
			trainCars.add(wagon);
		} else if (type == 1) {

			Vector3f position = new Vector3f(0, 18, 0);
			Vector3f rotation = new Vector3f(0, 0, 0);
			ModelTexture texture = new ModelTexture(loader.loadTexture("tank1texture"));
			texture.setHasTransparency(false);
			texture.setShineDamper(2f);
			texture.setReflectivity(0.1f);
			texture.setRoughness(0.03f);
			TexturedModel bedModel = new TexturedModel(OBJLoader.loadObjModel("/gameModels/car2slot/longbed", loader),
					texture);
			Vector3f bedOffset = new Vector3f(0, 4, 0);
			TexturedModel WheelModel = new TexturedModel(
					OBJLoader.loadObjModel("/gameModels/car2slot/wheelsTest2", loader), texture);
			List<Vector3f> offSets = new ArrayList<Vector3f>();
			offSets.add(new Vector3f(0, -0.5f, -3.97f));
			offSets.add(new Vector3f(0, -0.5f, 0));
			offSets.add(new Vector3f(0, -0.5f, 3.959f));
			TexturedModel wheel1Model = new TexturedModel(OBJLoader.loadObjModel("/gameModels/car2slot/frontN", loader),
					texture);
			Vector3f wheel1Offset = new Vector3f(0, 2f, 20.1f);
			TexturedModel wheel2Model = new TexturedModel(OBJLoader.loadObjModel("/gameModels/car2slot/backN", loader),
					texture);
			Vector3f wheel2Offset = new Vector3f(0, 2f, -20.1f);
			Entity body = new Entity(bedModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1);
			List<Entity> wheelList = new ArrayList<Entity>();
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			Entity wheel1 = new Entity(wheel1Model, 0, Vector3f.add(position, wheel1Offset, null), 0, 0, 0, 1);
			Entity wheel2 = new Entity(wheel2Model, 0, Vector3f.add(position, wheel2Offset, null), 0, 0, 0, 1);
			// TrainCar wagon = new TrainCar(body, bedOffset, wheel, offSets, wheel2,
			// wheel1Offset, offSets, wheel2, wheel2Offset, null, null, null, null,
			// TrainCar.TYPE_WAGON, 0, 0, 2, false,
			// 0, 0, 100, 0, position, rotation);

			float distance = -(Math.abs(trainCars.get(trainCars.size() - 1).getWheel1Offset().z)
					+ Math.abs(wheel2Offset.z) + Math.abs(trainCars.get(trainCars.size() - 1).getDistance())
					+ 14.223f);
			TrainCar wagon = new TrainCar(distance, body, bedOffset, wheelList, offSets, wheel1, wheel1Offset, offSets, wheel2,
					wheel2Offset, null, null, null, null, TrainCar.TYPE_WAGON, 0, 0, 2, false, 0, 0, 100, 0, position,
					rotation,025,025);
			wagon.pushToMainList();
			trainCars.add(wagon);
		} else  if (type == 2) {
			Vector3f position = new Vector3f(0, 18, 0);
			Vector3f rotation = new Vector3f(0, 0, 0);
			ModelTexture texture = new ModelTexture(loader.loadTexture("tank1texture"));
			ModelTexture wagonTexture = new ModelTexture(loader.loadTexture("/gameModels/detailedCar/textureSnowy"));
			texture.setHasTransparency(false);
			texture.setShineDamper(2f);
			texture.setReflectivity(0.1f);
			texture.setRoughness(0.03f);
			TexturedModel bedModel = new TexturedModel(OBJLoader.loadObjModel("/gameModels/detailedCar/model", loader),
					wagonTexture);
			Vector3f bedOffset = new Vector3f(0, 4, 0);
			TexturedModel WheelModel = new TexturedModel(
					OBJLoader.loadObjModel("/gameModels/car2slot/wheelsTest2", loader), texture);
			List<Vector3f> offSets = new ArrayList<Vector3f>();
			offSets.add(new Vector3f(0, -0.6f, -3.97f));
			offSets.add(new Vector3f(0, -0.6f, 0));
			offSets.add(new Vector3f(0, -0.6f, 3.959f));
			TexturedModel wheel1Model = new TexturedModel(OBJLoader.loadObjModel("/gameModels/car2slot/frontN", loader),
					texture);
			Vector3f wheel1Offset = new Vector3f(0, 2f, 17f);
			TexturedModel wheel2Model = new TexturedModel(OBJLoader.loadObjModel("/gameModels/car2slot/backN", loader),
					texture);
			Vector3f wheel2Offset = new Vector3f(0, 2f, -17f);
			Entity body = new Entity(bedModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1);
			List<Entity> wheelList = new ArrayList<Entity>();
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			Entity wheel1 = new Entity(wheel1Model, 0, Vector3f.add(position, wheel1Offset, null), 0, 0, 0, 1);
			Entity wheel2 = new Entity(wheel2Model, 0, Vector3f.add(position, wheel2Offset, null), 0, 0, 0, 1);
			// TrainCar wagon = new TrainCar(body, bedOffset, wheel, offSets, wheel2,
			// wheel1Offset, offSets, wheel2, wheel2Offset, null, null, null, null,
			// TrainCar.TYPE_WAGON, 0, 0, 2, false,
			// 0, 0, 100, 0, position, rotation);
			float distance = -(Math.abs(trainCars.get(trainCars.size() - 1).getDistanceToBack()) 
								+ Math.abs(trainCars.get(trainCars.size() - 1).getDistance())
								+ 25);
			TrainCar wagon = new TrainCar(distance, body, bedOffset, wheelList, offSets, wheel1, wheel1Offset, offSets,
					wheel2, wheel2Offset, null, null, null, null, TrainCar.TYPE_WAGON, 0, 0, 2, false, 0, 0, 100, 0,
					position, rotation, 25, 25);
			wagon.pushToMainList();
			trainCars.add(wagon);
		} else if (type == 3) {
			Vector3f position = new Vector3f(0, 18, 0);
			Vector3f rotation = new Vector3f(0, 0, 0);
			ModelTexture wagonTexture = new ModelTexture(loader.loadTexture("trains/2_slot_flat_car/texture"));
			wagonTexture.setNormalMap(loader.loadTexture("trains/2_slot_flat_car/normal"));
			wagonTexture.setReflectiveMap(loader.loadTexture("trains/2_slot_flat_car/roughness"));
			wagonTexture.setRoughMap(loader.loadTexture("trains/2_slot_flat_car/roughness"));
			wagonTexture.setHasTransparency(true);
			TexturedModel model = new TexturedModel(NormalMappedObjLoader.loadObjModel("trains/2_slot_flat_car/body", loader),
					wagonTexture);
			Vector3f bedOffset = new Vector3f(0, 4.6f, 0);
			ModelTexture wheelTexture = new ModelTexture(loader.loadTexture("tank1texture"));
			TexturedModel WheelModel = new TexturedModel(OBJLoader.loadObjModel("/gameModels/ALCO_M-K_PA-4/axle", loader),
					wheelTexture);
			List<Vector3f> offSets = new ArrayList<Vector3f>();
			offSets.add(new Vector3f(0, -0.2f, -6.396f));
			offSets.add(new Vector3f(0, -0.2f, -0.42931f));
			offSets.add(new Vector3f(0, -0.2f, 5.6023f));
			
			TexturedModel wheel1Model = new TexturedModel(NormalMappedObjLoader.loadObjModel("trains/2_slot_flat_car/truck", loader),
					wagonTexture);
			Vector3f wheel1Offset = new Vector3f(0, 2f, 26.84f);
			TexturedModel wheel2Model = wheel1Model;
			
			Vector3f wheel2Offset = new Vector3f(0, 2f, -26.84f);
			Entity body = new Entity(model, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1);
			List<Entity> wheelList = new ArrayList<Entity>();
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			Entity wheel1 = new Entity(wheel1Model, 0, Vector3f.add(position, wheel1Offset, null), 0, 0, 0, 1);
			Entity wheel2 = new Entity(wheel2Model, 0, Vector3f.add(position, wheel2Offset, null), 0, 0, 0, 1);
			// TrainCar wagon = new TrainCar(body, bedOffset, wheel, offSets, wheel2,
			// wheel1Offset, offSets, wheel2, wheel2Offset, null, null, null, null,
			// TrainCar.TYPE_WAGON, 0, 0, 2, false,
			// 0, 0, 100, 0, position, rotation);
			float distance = -(Math.abs(trainCars.get(trainCars.size() - 1).getDistanceToBack()) + 27
					+ Math.abs(trainCars.get(trainCars.size() - 1).getDistance())
					+ 27);
			TrainCar wagon = new TrainCar(distance, body, bedOffset, wheelList, offSets, wheel1, wheel1Offset, offSets,
					wheel2, wheel2Offset, null, null, null, null, TrainCar.TYPE_WAGON, 0, 0, 2, false, 0, 0, 100, 0,
					position, rotation, 25, 25);
			wagon.pushToMainList();
			trainCars.add(wagon);
		} else if (type == 50) {

			Vector3f position = new Vector3f(0, 18, 0);
			Vector3f rotation = new Vector3f(0, 0, 0);
			ModelTexture texture = null;
			ModelTexture old = null;
			TexturedModel trainModel = null;
			if (loader != null) {

				old = new ModelTexture(loader.loadTexture("/gameModels/ALCO_M-K_PA-4/truckTexture"));
				old.setReflectiveMap(loader.loadTexture("/gameModels/ALCO_M-K_PA-4/truckSpecular"));
				old.setRoughMap(loader.loadTexture("/gameModels/ALCO_M-K_PA-4/truckSpecular"));

				old.setHasTransparency(false);
				old.setShineDamper(4f);
				old.setReflectivity(1f);
				old.setRoughness(1f);
				
				texture = new ModelTexture(loader.loadTexture("/gameModels/ALCO_M-K_PA-4/4ktexture1"));
				texture.setReflectiveMap(loader.loadTexture("/gameModels/ALCO_M-K_PA-4/shiny"));
				texture.setRoughMap(loader.loadTexture("/gameModels/ALCO_M-K_PA-4/newRoughMap"));
				texture.setNormalMap(loader.loadTexture("/gameModels/ALCO_M-K_PA-4/normals"));

				//texture.setNormalAmount(1);
				texture.setHasTransparency(false);
				texture.setReflectivity(1f);
				texture.setRoughness(0.4f);
				trainModel = new TexturedModel(NormalMappedObjLoader.loadObjModel("/gameModels/ALCO_M-K_PA-4/newBody", loader), texture);
			}
			Vector3f bedOffset = new Vector3f(0, 0f, 0);
			TexturedModel WheelModel = null;
			ModelTexture wheelTexture = null; 
			if (loader != null) {
				wheelTexture = new ModelTexture(loader.loadTexture("tank1texture"));
				WheelModel = new TexturedModel(OBJLoader.loadObjModel("/gameModels/ALCO_M-K_PA-4/axle", loader),
						wheelTexture);
			}
			List<Vector3f> offSets = new ArrayList<Vector3f>();
			offSets.add(new Vector3f(0, 0.4f, -10));
			offSets.add(new Vector3f(0, 0.4f, 0));
			offSets.add(new Vector3f(0, 0.4f, 10));
			TexturedModel wheel1Model = null;
			TexturedModel wheel2Model = null;
			if (loader != null) {
				wheel1Model = new TexturedModel(OBJLoader.loadObjModel("/gameModels/ALCO_M-K_PA-4/truck", loader), old);
				wheel2Model = new TexturedModel(OBJLoader.loadObjModel("/gameModels/ALCO_M-K_PA-4/truck", loader), old);
			}
			Vector3f wheel1Offset = new Vector3f(0, 4f, 23.37f);
			Vector3f wheel2Offset = new Vector3f(0, 30f, -23.37f);
			Entity body = new Entity(trainModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1);
			List<Entity> wheelList = new ArrayList<Entity>();
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			wheelList.add(new Entity(WheelModel, 0, Vector3f.add(position, bedOffset, null), 0, 0, 0, 1));
			Entity wheel1 = new Entity(wheel1Model, 0, Vector3f.add(position, wheel1Offset, null), 0, 0, 0, 1);
			Entity wheel2 = new Entity(wheel2Model, 0, Vector3f.add(position, wheel2Offset, null), 0, 0, 0, 1);
			float distance = -(Math.abs(trainCars.get(trainCars.size() - 1).getDistanceToBack()) + 20
					+ Math.abs(trainCars.get(trainCars.size() - 1).getDistance())
					+ 25);
			TrainCar engine = new TrainCar(distance, body, bedOffset, wheelList, offSets, wheel1, wheel1Offset, offSets,
					wheel2, wheel2Offset, null, null, null, null, TrainCar.TYPE_WAGON, 0, 0, 2, false, 0, 0, 100, 0,
					position, rotation,40,40);
			engine.pushToMainList();
			trainCars.add(engine);
		}
	}

	public void setAssignedLine(Track track) {
		for (TrainCar cars : trainCars) {
			cars.setAssignedLine(track);
		}
	}

	public TrainCar getCar(int index) {
		return this.trainCars.get(index);
	}

	public void update(float amount, float speed) {//no slack whatsoever
		for (TrainCar cars : trainCars) {
			cars.setOnTrack(this.distance + amount, speed);
			//cars.updateGuns(new Vector3f(30,30,30));
			//cars.updateGunsDisabled();
		}
	}

	public void setDistance(float f) {
		this.distance = f;
	}
	*/
}
