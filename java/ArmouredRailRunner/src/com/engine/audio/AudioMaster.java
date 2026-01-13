package com.engine.audio;

import java.util.Locale;
import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.system.MemoryUtil;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;
import org.lwjgl.openal.AL;


import com.engine.math.Maths;
import com.engine.objects.Camera;

import main.Engine;
/*
 * AudioMaster class
 * Manages all audio and sources
 * Source objects can only be created through this class
 */
public class AudioMaster implements Closeable {
	public static List<Integer> buffers = new ArrayList<Integer>();
	public static List<Source> sounds = new ArrayList<Source>();
	
	public static boolean isSet = false;
	
	public static long context;
	public static long device;
	
	public static void init() {
		System.out.println("--Creating audio system--");
		String defaultDeviceName = ALC10.alcGetString(0, ALC10.ALC_DEFAULT_DEVICE_SPECIFIER);
		device = ALC10.alcOpenDevice(defaultDeviceName);
		ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
		context = ALC10.alcCreateContext(device, new int[] {0});
		ALC10.alcMakeContextCurrent(context);
		ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);
		if (!alCapabilities.OpenAL10) {
			System.err.println("WARNING: Audio is not supported");
		} else {
			isSet = true;
		}
	}
	public static Source newSoundSource(float Roll, float Clamp, float Max, boolean looping, Vector3f loc) {
		Source source = new Source(Roll, Clamp, Max);
		source.setLooping(looping);
		source.setPosition(loc);
		sounds.add(source);
		Engine.dataBlocks += 1;
		Engine.soundData += 1;
		return source;
	}
	public static void setListenderData(Vector3f position, Vector3f rotation) {
		//Vector3f pos = camera.getPosition();
		//AL10.alListener3f(AL10.AL_POSITION, pos.x, pos.y, pos.z);    
		//AL10.alListener3f(AL10.AL_ORIENTATION, 0,0,-1);
		//Matrix4f m = camera.getWorldTransformationMatrix();
		//Vector3f up = new Vector3f(m.m10, m.m11, m.m12);
		//Vector3f fr = new Vector3f(-m.m20, -m.m21, -m.m22);
		//float[] listenerOrientation = new float[6];
	//	listenerOrientation[0] = fr.x;
	//	listenerOrientation[1] = fr.y;
	//	listenerOrientation[2] = fr.z;
	//	listenerOrientation[3] = up.x;
	//	listenerOrientation[4] = up.y;
	//	listenerOrientation[5] = up.z;
	//	AL10.alListenerfv(AL10.AL_ORIENTATION, listenerOrientation);
		/*
		 * (rant)
		 * For some reason, I cannot find any helpful tutorial on getting the orientation for the listener correct
		 * The above commented code is by far the closest to actual listener orientation that works... to a point
		 * I have scouted the Internet for clues on HOW to properly do this, but only found one website that actually
		 * pointed out what each thing was, which is what you see there.
		 * The below code is my way of setting the orientation of the listener. Simple, but it works better than what
		 * is provided by openAL. What this does is that it gets the actual position of the sound, deducts the camera
		 * position from it, and then rotates it around 0,0,0 according to the cameras rotation.
		 * It loops through ALL THA SOONDs so that we can actually hear like we are moving our head
		 * I do not know why this has to be the most complex thing about sounds. Why not just use a rotation vector?
		 * Why make it so freaking complex?
		 */
		AL10.alListener3f(AL10.AL_POSITION, 0, 0, 0);
		AL10.alListenerfv(AL10.AL_ORIENTATION, new float[] {0,1,0,0,0,1});
		for (int i = 0; i < sounds.size(); i++) {
			Vector3f soundPosition = Vector3f.sub(sounds.get(i).getPosition(), position, null);
			sounds.get(i).setStaticPosition(Maths.RotateVec3AroundPoint(soundPosition, new Vector3f(0,0,0), rotation));
		}
	}
	
	public static int loadSound(String file) throws NullPointerException {
		int buffer = AL10.alGenBuffers(); 
		buffers.add(buffer);
		WaveData waveFile = WaveData.create(file);
		AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate); 
		waveFile.dispose();
		return buffer;
	}

	public static void cleanUp() {
		for(int buffer : buffers) {
			AL10.alDeleteBuffers(buffer);
		}
		for(Source source : sounds) {
			source.delete();
		}
		
		ALC10.alcMakeContextCurrent(MemoryUtil.NULL);
		ALC10.alcDestroyContext(context);
		ALC10.alcCloseDevice(device);
	}
	public static void setGlobalVolume(float vol) {
		Source.GLOBAL_VOLUME = (vol > Source.MAX_VOLUME) ? Source.MAX_VOLUME : vol;
		for(Source source : sounds)
			source.update();
	}
	@Override
	public void close() throws IOException {
		cleanUp();
	}
}
