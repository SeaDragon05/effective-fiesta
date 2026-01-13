package engine.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.Vertex;
import engine.maths.Vector2f;
import engine.maths.Vector3f;

public class FileUtils {
	public static String loadAsString(String path) {//redone this util to utilize the Scanner. buffered reader had a bad day with my system lol
		String result = "";
		try {
			Scanner scnr = new Scanner(new File(path));
			String line = "";
			do {
				line = scnr.nextLine();
				result += (line + "\n");
			} while (scnr.hasNext());
			scnr.close();
		} catch (IOException e) {
			System.err.println("Couldn't find the file at " + path);
		}
		return result;
	}
	
	
	@SuppressWarnings("all")//scanner leak
	public static Mesh loadMesh(String fileURL, String textureURL,  Vector3f rotation , Vector3f scale, float textureRefl) {
		Vertex[] result;
		int[] indices;
		File file;
		int i = 0;
		int step = 8;
		String[] SVertex = new String[0];
		try {
			file = new File(fileURL);
			Scanner scnr = new Scanner(file);
			String line = scnr.nextLine();
			SVertex = line.split(",");
			result = new Vertex[SVertex.length / step];
			int count = 0;
			for (i = 0; i < SVertex.length; i += step) {
				if (count == 3) {
					count = 0;
				}
				float x = Float.parseFloat(SVertex[i]);
				float y = Float.parseFloat(SVertex[i + 1]);
				float z = Float.parseFloat(SVertex[i + 2]);
				float nx = Float.parseFloat(SVertex[i + 3]);
				float ny = Float.parseFloat(SVertex[i + 4]);
				float nz = Float.parseFloat(SVertex[i + 5]);
				float u = Float.parseFloat(SVertex[i + 6]);
				float v = Float.parseFloat(SVertex[i + 7]);
				Vector3f point = rotate(new Vector3f(x * scale.getX(),y * scale.getY(),z * scale.getZ()), rotation);
				Vector3f Normal = rotate(new Vector3f(nx,ny,nz), rotation);
				Vector2f textureCoord = new Vector2f(u, v);
				result[i/step] = new Vertex(point, Normal, textureCoord);
				//System.out.println("TextureCoord:" + SVertex[i + 6] + " " + SVertex[i + 7]);
				count += 1;
			}
			line = scnr.nextLine();
			String[] SIndex = line.split(",");
			indices = new int[SIndex.length];
			for (i = 0; i < SIndex.length; i += 1) {
				indices[i] = Integer.parseInt(SIndex[i]);
			}
			return new Mesh(result, indices, new Material(textureURL, textureRefl));
		} catch (NumberFormatException e) {
			System.err.println("Error importing model: " + e);
			System.err.println("index: " + i);
			System.err.println("Point index: " + SVertex.length / step);
			System.err.println("SVertex.length: " + SVertex.length);
			System.err.println("Point info: " + SVertex[i] + " " + SVertex[i + 1] + " " + SVertex[i + 2] + " " + SVertex[i + 3] + " " + SVertex[i + 4] + " " + SVertex[i + 5] + " " + SVertex[i + 6] + " " + SVertex[i + 7]);
			e.printStackTrace();
			System.exit(1);
			return new Mesh(new Vertex[0], new int[0], new Material(null, 0));
		} catch (FileNotFoundException e) {
			System.err.println("Error importing model: Cannot find file: " + fileURL);
			e.printStackTrace();
			System.exit(1);
			return new Mesh(new Vertex[0], new int[0], new Material(null, 0));
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Error importing model: " + e);
			System.err.println("index: " + i);
			System.err.println("Point index: " + SVertex.length / step);
			System.err.println("SVertex.length: " + SVertex.length);
			//System.err.println("Point info: " + SVertex[i] + " " + SVertex[i + 1] + " " + SVertex[i + 2] + " " + SVertex[i + 3] + " " + SVertex[i + 4] + " " + SVertex[i + 5] + " " + SVertex[i + 6] + " " + SVertex[i + 7]);
			e.printStackTrace();
			System.exit(1);
			return new Mesh(new Vertex[0], new int[0], new Material(null, 0));
		}
	}
	public static Vector3f rotate(Vector3f point, Vector3f angles) {
	      float x2 = point.getX();
	      float y2 = point.getY();
	      float z2 = point.getZ();
	      float laxs = (float) Math.sin(Math.toRadians(angles.getX()));
	      float laxc = (float) Math.cos(Math.toRadians(angles.getX()));
	      float lays = (float) Math.sin(Math.toRadians(angles.getY()));
	      float layc = (float) Math.cos(Math.toRadians(angles.getY()));
	      float lazs = (float) Math.sin(Math.toRadians(angles.getZ()));
	      float lazc = (float) Math.cos(Math.toRadians(angles.getZ()));
	      Vector3f sts = new Vector3f(0,0,0);
	      sts.setX(((layc * ((lazs * y2) + (lazc * x2))) - (lays * z2)));
	      sts.setY(((laxs * ((layc * z2) + (lays * ((lazs * y2) + (lazc * x2))))) + (laxc * ((lazc * y2) - (lazs * x2)))));
	      sts.setZ(((laxc * ((layc * z2) + (lays * ((lazs * y2) + (lazc * x2))))) - (laxs * ((lazc * y2) - (lazs * x2)))));
	      return sts;
	}
	public static Mesh loadOBJ(String fileURL, String textureURL,  Vector3f rotation , Vector3f scale, float textureRefl) {
		int Line = 0;
		FileReader fr = null;
		try {
			fr = new FileReader(new File(fileURL));
		} catch (Exception e) {
			System.err.println(e);
		}
		BufferedReader reader = new BufferedReader(fr);
		String line;
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();
		float[] normalArray = null;
		float[] textureArray = null; 
		Vertex[] finalVert = new Vertex[0];
		try {
			while (true) {
				line = reader.readLine();
				Line += 1;
				String[] currentLine = line.split(" ");
				if (line.startsWith("v ")) {
					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					vertices.add(vertex);
				} else if (line.startsWith("vt ")) {
					Vector2f texute = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
					textures.add(texute);
				} else if (line.startsWith("vn ")) {
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					normals.add(normal);
				} else if (line.startsWith("f ")) {
					textureArray = new float[vertices.size()*2];
					normalArray = new float[vertices.size()*3];
					break;
				}
			}
			finalVert = new Vertex[vertices.size()];
			while(line!= null) {//123 124
				if(!line.startsWith("f ")) {
					line = reader.readLine();
					Line += 0;
					continue;
				}
				String[] currentLine = line.split(" ");
				String[] face1 = currentLine[1].split("/");
				String[] face2 = currentLine[2].split("/");
				String[] face3 = currentLine[3].split("/");
				
				finalVert[Integer.parseInt(face1[0]) - 1] = processVertex(face1, vertices, indices, textures, normals, textureArray, normalArray);
				finalVert[Integer.parseInt(face2[0]) - 1] = processVertex(face2, vertices, indices, textures, normals, textureArray, normalArray);
				finalVert[Integer.parseInt(face3[0]) - 1] = processVertex(face3, vertices, indices, textures, normals, textureArray, normalArray);
				
				line = reader.readLine();
				Line += 0;
			}
			reader.close();
		} catch (Exception e) {
			System.err.println("Error importing object: " + fileURL + " at line " + Line);
			System.err.println(e);
		}
		/*
		int vertexPointer = 0;
		Vertex[] finalVert = new Vertex[vertices.size()];
		System.out.println(vertices.size() + " " + normals.size() + " " + textures.size());
		for(int i = 0; i < vertices.size() - 1; i ++) {
			finalVert[i] = new Vertex(vertices.get(i), normals.get(i), textures.get(i));
		}*/
		int[] finalInd = new int[indices.size()];
		for (int i = 0; i < indices.size(); i++) {
			finalInd[i] = indices.get(i);
		}
		return new Mesh(finalVert, finalInd, new Material(textureURL, textureRefl));
	}//public Mesh(Vertex[] vertices, int[] faces, Material material)
	
	private static Vertex processVertex(String[] vertexData, List<Vector3f> position, List<Integer> indices, List<Vector2f> textures,
			List<Vector3f> normals, float[] textureArray, float[] normalsArray) {
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indices.add(currentVertexPointer);
		Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
		textureArray[currentVertexPointer*2] = currentTex.getX();
		textureArray[currentVertexPointer*2 + 1] = currentTex.getY();
		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
		normalsArray[currentVertexPointer*3] = currentNorm.getX();
		normalsArray[currentVertexPointer*3 + 1] = currentNorm.getY();
		normalsArray[currentVertexPointer*3 + 2] = currentNorm.getZ();
		return new Vertex(position.get(currentVertexPointer), currentNorm, currentTex);
	}
}