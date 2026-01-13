package games.trainSim.base;

import java.util.ArrayList;
import java.util.List;

import org.lwjglx.util.vector.Vector3f;

import com.engine.graphics.ModelTexture;
import com.engine.objects.Entity;
import com.engine.objects.TexturedModel;
import com.engine.utils.MasterLoader;

public class TrainLoader {
	private TrainData train;

	public TrainLoader(float startingDistance) {
		this.train = new TrainData();
		this.train.setDistance(startingDistance);
	}

	public TrainData getData() {
		return this.train;
	}

	public TrainData loadTrain(String url, int texSize, int LOD) {

		return null;//class_method_load_in_train(null);
	}

	public TrainData createTestTrain(int texSize, int LOD) {
		String textureSize = "" + texSize;
		String modelLOD = "/models/lod_" + LOD + "/";
		// get all the information about the train model
		// create the data list
		List<TrainObjectData> modelData = new ArrayList<TrainObjectData>();

		TrainObjectData testModel = new TrainObjectData(TrainObjectData.TYPE_STATICMODEL, // 0
				"/trains/TrainMaster/model", "/trains/TrainMaster/texture4096");
		testModel.setNormalUrl("/trains/TrainMaster/normal4096");
		testModel.setReflectiveUrl("/trains/TrainMaster/shiny4096");
		testModel.setRoughnessUrl("/trains/TrainMaster/shinyM4096");
		// add the front model
		// modelData.add(testModel);

		// front model stuff
		TrainObjectData frontModel = new TrainObjectData(TrainObjectData.TYPE_STATICMODEL, // 0
				"/trains/alco_pa-1/" + modelLOD + "front", "/trains/alco_pa-1/textures/frontTexture" + textureSize);
		frontModel.setNormalUrl("/trains/alco_pa-1/textures/frontnormal" + textureSize);
		frontModel.setReflectiveUrl("/trains/alco_pa-1/textures/frontshiny" + textureSize);
		frontModel.setRoughnessUrl("/trains/alco_pa-1/textures/frontshiny" + textureSize);
		// add the front model
		modelData.add(frontModel);

		// body model
		TrainObjectData mainBodyModel = new TrainObjectData(TrainObjectData.TYPE_STATICMODEL, // 1
				"/trains/alco_pa-1/" + modelLOD + "body", "/trains/alco_pa-1/textures/body" + textureSize);
		mainBodyModel.setNormalUrl("/trains/alco_pa-1/textures/bodynormal" + textureSize);
		mainBodyModel.setReflectiveUrl("/trains/alco_pa-1/textures/bodyshiny" + textureSize);
		mainBodyModel.setRoughnessUrl("/trains/alco_pa-1/textures/bodyshiny" + textureSize);
		// add the body model
		modelData.add(mainBodyModel);

		// frame model
		TrainObjectData frameModel = new TrainObjectData(TrainObjectData.TYPE_STATICMODEL, // 1
				"/trains/alco_pa-1/" + modelLOD + "frame", "/trains/alco_pa-1/textures/body" + textureSize);
		frameModel.setNormalUrl("/trains/alco_pa-1/textures/bodynormal" + textureSize);
		frameModel.setReflectiveUrl("/trains/alco_pa-1/textures/bodyshiny" + textureSize);
		frameModel.setRoughnessUrl("/trains/alco_pa-1/textures/bodyshiny" + textureSize);
		// add the body model
		modelData.add(frameModel);

		// detail model
		TrainObjectData detailModel = new TrainObjectData(TrainObjectData.TYPE_STATICMODEL, // 2
				"/trains/alco_pa-1/" + modelLOD + "details", "/trains/alco_pa-1/textures/detail" + textureSize);
		detailModel.setReflectivity(0.8f);
		detailModel.setRoughness(0.6f);
		// add the body model
		modelData.add(detailModel);

		TrainObjectData cabinModel = new TrainObjectData(TrainObjectData.TYPE_STATICMODEL, // 3
				"/trains/alco_pa-1/" + modelLOD + "cabin", "/trains/alco_pa-1/textures/cabinTexture" + textureSize);
		// add the body model
		modelData.add(cabinModel);

		// axle model that will be used many times
		TrainObjectData truckTestModel = new TrainObjectData(TrainObjectData.TYPE_TRUCK, // 5
				"/trains/EC_evo/truck", "/gameModels/"// old code moment
		);
		truckTestModel.setReflectiveUrl("/gameModels/ALCO_M-K_PA-4/truckSpecular");
		truckTestModel.setRoughnessUrl("/gameModels/ALCO_M-K_PA-4/truckSpecular");
		truckTestModel.setUseCount(2);
		truckTestModel.setOffset(new Vector3f(0, 3f, 30.297f));
		truckTestModel.setOffset2(new Vector3f(0, 3f, -23.37f));
		// modelData.add(truckTestModel);

		// axle model that will be used many times
		TrainObjectData truckModel = new TrainObjectData(TrainObjectData.TYPE_TRUCK, // 5
				"/gameModels/ALCO_M-K_PA-4/truck", "/gameModels/ALCO_M-K_PA-4/truckTexture"// old code moment
		);
		truckModel.setReflectiveUrl("/gameModels/ALCO_M-K_PA-4/truckSpecular");
		truckModel.setRoughnessUrl("/gameModels/ALCO_M-K_PA-4/truckSpecular");
		truckModel.setUseCount(2);
		truckModel.setOffset(new Vector3f(0, 4f, 23.37f));
		truckModel.setOffset2(new Vector3f(0, 30f, -23.37f));
		modelData.add(truckModel);

		// axle model that will be used many times
		TrainObjectData axleModel = new TrainObjectData(TrainObjectData.TYPE_WHEEL, // 4
				"/gameModels/ALCO_M-K_PA-4/axle", "tank1texture"// old code moment
		);
		axleModel.setUseCount(3);
		axleModel.setOffset(new Vector3f(0, 0.24f, -10));
		axleModel.setOffset2(new Vector3f(0, 0.24f, 0));
		axleModel.setOffset3(new Vector3f(0, 0.24f, 10));
		modelData.add(axleModel);

		// time to fill in data by filling in model data with the loader
		mainLoader l = new mainLoader();
		TrainObject trainCar = l.inner_method_load_in_train(modelData);
		l = null;//garbage collector
		this.train.addTrainObject(trainCar);
		return this.train;
	}
	
	public static TrainObject test_createTrainObject(TrainData train, float distance, int texSize, int LOD) {
		String textureSize = "" + texSize;
		String modelLOD = "/models/lod_" + LOD + "/";
		// get all the information about the train model
		// create the data list
		List<TrainObjectData> modelData = new ArrayList<TrainObjectData>();

		// front model stuff
		TrainObjectData bodyModel = new TrainObjectData(TrainObjectData.TYPE_STATICMODEL, // 0
				"/trains/Train_ALCO_PA1_ENGINE/body", "/trains/alco_pa-1/textures/body" + textureSize);
		bodyModel.setNormalUrl("/trains/alco_pa-1/textures/frontnormal" + textureSize);
		bodyModel.setReflectiveUrl("/trains/alco_pa-1/textures/frontshiny" + textureSize);
		bodyModel.setRoughnessUrl("/trains/alco_pa-1/textures/frontshiny" + textureSize);
		// add the front model
		modelData.add(bodyModel);

		// frame model
		TrainObjectData frameModel = new TrainObjectData(TrainObjectData.TYPE_STATICMODEL, // 1
				"/trains/Train_ALCO_PA1_ENGINE/frame", "/trains/alco_pa-1/textures/body" + textureSize);
		frameModel.setNormalUrl("/trains/alco_pa-1/textures/bodynormal" + textureSize);
		frameModel.setReflectiveUrl("/trains/alco_pa-1/textures/bodyshiny" + textureSize);
		frameModel.setRoughnessUrl("/trains/alco_pa-1/textures/bodyshiny" + textureSize);
		// add the body model
		modelData.add(frameModel);

		// detail model NOT DONE YET
		//TrainObjectData detailModel = new TrainObjectData(TrainObjectData.TYPE_STATICMODEL, // 2
		//		"/trains/alco_pa-1/" + modelLOD + "details", "/trains/alco_pa-1/textures/detail" + textureSize);
		//detailModel.setReflectivity(0.8f);
		//detailModel.setRoughness(0.6f);
		// add the body model
		//modelData.add(detailModel);

		// axle model that will be used many times
		TrainObjectData truckTestModel = new TrainObjectData(TrainObjectData.TYPE_TRUCK, // 5
				"/trains/EC_evo/truck", "/gameModels/"// old code moment
		);
		truckTestModel.setReflectiveUrl("/gameModels/ALCO_M-K_PA-4/truckSpecular");
		truckTestModel.setRoughnessUrl("/gameModels/ALCO_M-K_PA-4/truckSpecular");
		truckTestModel.setUseCount(2);
		truckTestModel.setOffset(new Vector3f(0, 3f, 30.297f));
		truckTestModel.setOffset2(new Vector3f(0, 3f, -23.37f));
		// modelData.add(truckTestModel);

		// axle model that will be used many times
		TrainObjectData truckModel = new TrainObjectData(TrainObjectData.TYPE_TRUCK, // 5
				"/gameModels/ALCO_M-K_PA-4/truck", "/gameModels/ALCO_M-K_PA-4/truckTexture"// old code moment
		);
		truckModel.setReflectiveUrl("/gameModels/ALCO_M-K_PA-4/truckSpecular");
		truckModel.setRoughnessUrl("/gameModels/ALCO_M-K_PA-4/truckSpecular");
		truckModel.setUseCount(2);
		truckModel.setOffset(new Vector3f(0, 4f, 23.37f));
		truckModel.setOffset2(new Vector3f(0, 30f, -23.37f));
		modelData.add(truckModel);

		// axle model that will be used many times
		TrainObjectData axleModel = new TrainObjectData(TrainObjectData.TYPE_WHEEL, // 4
				"/gameModels/ALCO_M-K_PA-4/axle", "tank1texture"// old code moment
		);
		axleModel.setUseCount(3);
		axleModel.setOffset(new Vector3f(0, 0.24f, -10));
		axleModel.setOffset2(new Vector3f(0, 0.24f, 0));
		axleModel.setOffset3(new Vector3f(0, 0.24f, 10));
		modelData.add(axleModel);

		// time to fill in data by filling in model data with the loader
		mainLoader l = new mainLoader();
		l.setDistance(distance);
		TrainObject trainCar = l.inner_method_load_in_train(modelData);
		l = null;//garbage collector
		return trainCar;
	}
	
	public TrainLoader() {}

	public static class mainLoader {//magic
		private float distance = 50;
		private	Vector3f bodyOffset = new Vector3f(0,-2f, 0);//offsets all static objects, lights and the player camera. Make sure that lights are fixed
		private Vector3f camOffset = new Vector3f(3.2687f, 10.1f, 29);
		private Vector3f cabinLightOffset = new Vector3f(0, 13, 43.239f);
		public void setDistance(float d) {this.distance = d;}
		public TrainObject inner_method_load_in_train(List<TrainObjectData> modelData) {
			boolean axle = false;
			boolean truck = false;
			TrainObject trainObject = new TrainObject();

			// changed the loader to masterloader class to help decrease memory usage

			for (int i = 0; i < modelData.size(); i++) {// could be rewritten to be better
				// debug line:
				System.out.println("DEBUG: " + modelData.get(i).getType() + "\n" + modelData.get(i).toString());
				// static model loader:
				if (modelData.get(i).getType() == TrainObjectData.TYPE_STATICMODEL) {
					// define texture:
					ModelTexture texture = new ModelTexture(
							MasterLoader.loadTexturePointer(modelData.get(i).getTextureUrl()));
					if (modelData.get(i).getNormalUrl() != null) {
						texture.setNormalMap(MasterLoader.loadTexturePointer(modelData.get(i).getNormalUrl()));
					}
					// reflective map:
					if (modelData.get(i).getReflectiveUrl() != null) {
						texture.setReflectiveMap(MasterLoader.loadTexturePointer(modelData.get(i).getReflectiveUrl()));
						texture.setReflectivity(1);
					} else {
						texture.setReflectivity(modelData.get(i).getReflectivity());
					}
					// rough map:
					if (modelData.get(i).getRoughnessUrl() != null) {
						texture.setRoughMap(MasterLoader.loadTexturePointer(modelData.get(i).getRoughnessUrl()));
						texture.setRoughness(1);
					} else {
						texture.setRoughness(modelData.get(i).getRoughness());
					}
					// define the model with the texture
					TexturedModel model = new TexturedModel((modelData.get(i).hasNormalMap())
							? MasterLoader.loadNormalMappedObject(modelData.get(i).getModelUrl())
							: MasterLoader.loadRawModel(modelData.get(i).getModelUrl()), texture);
					Entity newEntity = new Entity(model, new Vector3f(), 0, 0, 0, modelData.get(i).getModelScale());
					trainObject.getStaticModelList().add(newEntity);
					trainObject.getStaticModelOffset().add(modelData.get(i).getOffset());
				} else if (modelData.get(i).getType() == TrainObjectData.TYPE_TRUCK) {
					if (truck) {
						System.err.println("Error: truck has already been defined! Object ID: " + i
								+ "\nObject Information:" + "\n" + modelData.get(i).toString());
						continue;
					}
					if (axle) {
						System.err.println(
								"Error: Axle has not been defined before truck was called! Train will not have any wheels!");
					}
					truck = true;
					// define texture:
					ModelTexture texture = new ModelTexture(
							MasterLoader.loadTexturePointer(modelData.get(i).getTextureUrl()));
					if (modelData.get(i).getNormalUrl() != null) {
						texture.setNormalMap(MasterLoader.loadTexturePointer(modelData.get(i).getNormalUrl()));
					}
					if (modelData.get(i).getReflectiveUrl() != null) {
						texture.setReflectiveMap(MasterLoader.loadTexturePointer(modelData.get(i).getReflectiveUrl()));
					} else {
						texture.setReflectivity(modelData.get(i).getReflectivity());
					}
					if (modelData.get(i).getRoughnessUrl() != null) {
						texture.setRoughMap(MasterLoader.loadTexturePointer(modelData.get(i).getRoughnessUrl()));
					} else {
						texture.setRoughness(modelData.get(i).getRoughness());
					}
					// define the model with the texture
					TexturedModel model = new TexturedModel((modelData.get(i).hasNormalMap())
							? MasterLoader.loadNormalMappedObject(modelData.get(i).getModelUrl())
							: MasterLoader.loadRawModel(modelData.get(i).getModelUrl()), texture);
					Entity newEntity = new Entity(model, new Vector3f(), 0, 0, 0, modelData.get(i).getModelScale());
					trainObject.getTruckOffsets()[0] = modelData.get(i).getOffset();
					Entity newEntity1 = new Entity(model, new Vector3f(), 0, 0, 0, modelData.get(i).getModelScale());
					trainObject.getTruckOffsets()[1] = modelData.get(i).getOffset2();
					trainObject.getTruckModels().add(newEntity);

					trainObject.getTruckModels().add(newEntity1);

				} else if (modelData.get(i).getType() == TrainObjectData.TYPE_WHEEL) {
					if (axle) {
						System.err.println("Error: axle has already been defined! Object ID: " + i
								+ "\nObject Information:" + "\n" + modelData.get(i).toString());
						continue;
					}
					if (!truck) {
						System.err.println("You're an idiot");
						continue;
					}
					axle = true;
					// define texture:
					ModelTexture texture = new ModelTexture(
							MasterLoader.loadTexturePointer(modelData.get(i).getTextureUrl()));
					if (modelData.get(i).getNormalUrl() != null) {
						texture.setNormalMap(MasterLoader.loadTexturePointer(modelData.get(i).getNormalUrl()));
					}
					if (modelData.get(i).getReflectiveUrl() != null) {
						texture.setReflectiveMap(MasterLoader.loadTexturePointer(modelData.get(i).getReflectiveUrl()));
					} else {
						texture.setReflectivity(modelData.get(i).getReflectivity());
					}
					if (modelData.get(i).getRoughnessUrl() != null) {
						texture.setRoughMap(MasterLoader.loadTexturePointer(modelData.get(i).getRoughnessUrl()));
					} else {
						texture.setRoughness(modelData.get(i).getRoughness());
					}
					// define the model with the texture
					TexturedModel model = new TexturedModel((modelData.get(i).hasNormalMap())
							? MasterLoader.loadNormalMappedObject(modelData.get(i).getModelUrl())
							: MasterLoader.loadRawModel(modelData.get(i).getModelUrl()), texture);
					Entity newEntity = new Entity(model, new Vector3f(), 0, 0, 0, modelData.get(i).getModelScale());
					trainObject.getAxleModels().add(newEntity);
					trainObject.getAxleOffsets().add(modelData.get(i).getOffset());
					if (modelData.get(i).getUseCount() >= 2) {
						trainObject.getAxleModels().add(newEntity);
						trainObject.getAxleOffsets().add(modelData.get(i).getOffset2());
					}
					if (modelData.get(i).getUseCount() >= 3) {
						trainObject.getAxleModels().add(newEntity);
						trainObject.getAxleOffsets().add(modelData.get(i).getOffset3());
					}
					// repeat it for the next truck
					trainObject.getAxleModels().add(newEntity);
					trainObject.getAxleOffsets().add(modelData.get(i).getOffset());
					if (modelData.get(i).getUseCount() >= 2) {
						trainObject.getAxleModels().add(newEntity);
						trainObject.getAxleOffsets().add(modelData.get(i).getOffset2());
					}
					if (modelData.get(i).getUseCount() >= 3) {
						trainObject.getAxleModels().add(newEntity);
						trainObject.getAxleOffsets().add(modelData.get(i).getOffset3());
					}
				}
			}
			if (!truck) {
				System.err.println("Did not find a truck!");
			}
			if (!axle) {
				System.err.println("Did not find an axle!");
			}
			// push more information into the train object:
			trainObject.setCrewCount(4);
			trainObject.setCabinLightOffset(this.cabinLightOffset);
			trainObject.setFirstPersonCameraOffset(this.camOffset);
			trainObject.setBodyOffset(this.bodyOffset);
			trainObject.pushToMasinList();
			trainObject.setDistanceToFront(this.distance);
			return trainObject;
		}
	}
}