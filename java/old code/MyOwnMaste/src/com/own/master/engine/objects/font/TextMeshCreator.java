package com.own.master.engine.objects.font;

import java.io.File;
import java.util.ArrayList;

import org.lwjglx.util.vector.Vector2f;

import com.own.master.engine.graphics.Material;
import com.own.master.engine.graphics.Mesh;
import com.own.master.engine.graphics.Vertex;
import com.own.master.engine.math.Vector3f;

public class TextMeshCreator {
	protected static final float LINE_HEIGHT = 0.03f;
	protected static final int SPACE_ASCII = 32;

	private MetaFile metaData;

	public TextMeshCreator(File metaFile) {
		metaData = new MetaFile(metaFile);
	}

	public MetaFile getMetaFile() {
		return metaData;
	}

	public Mesh createTextMesh(GUIText text, Material mat) {
		ArrayList<Line> lines = createStructor(text);
		return createQuadVertices(text, lines, mat);
	}

	public ArrayList<Line> createStructor(GUIText text) {
		char[] chars = text.getTextString().toCharArray();
		ArrayList<Line> lines = new ArrayList<>();
		Line currentLine = new Line(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
		Word currentWord = new Word(text.getFontSize());
		for (char c : chars) {
			int ascii = c;
			if (ascii == SPACE_ASCII) {
				boolean added = currentLine.attemptToAddWord(currentWord);
				if (!added) {
					lines.add(currentLine);
					currentLine = new Line(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
					currentLine.attemptToAddWord(currentWord);
				}
				currentWord = new Word(text.getFontSize());
				continue;
			}
			Character character = metaData.getCharacter(ascii);
			currentWord.addChar(character);
		}
		completeStructor(lines, currentLine, currentWord, text);
		return lines;
	}

	public void completeStructor(ArrayList<Line> lines, Line currentLine, Word currentWord, GUIText text) {
		boolean added = currentLine.attemptToAddWord(currentWord);
		if (!added) {
			lines.add(currentLine);
			currentLine = new Line(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
			currentLine.attemptToAddWord(currentWord);
		}
		lines.add(currentLine);
	}

	public Mesh createQuadVertices(GUIText text, ArrayList<Line> lines, Material mat) {
		text.setNumberOfLines(lines.size());
		float curserX = 0f;
		float curserY = 0f;
		ArrayList<Vertex> vertices = new ArrayList<>();
		ArrayList<Integer> faces = new ArrayList<>();
		for (Line line : lines) {
			if (text.isCentered()) {
				curserX = (line.getMaxLength() - line.getLineLength()) / 2;
			}
			for (Word word : line.getWords()) {
				for (Character letter : word.getCharacters()) {
					addVerticesForCharacter(curserX, curserY, letter, text.getFontSize(), vertices, faces);
					curserX += letter.getxAdvance() * text.getFontSize();
				}
				curserX += metaData.getSpaceWidth() * text.getFontSize();
			}
			curserX = 0;
			curserY += LINE_HEIGHT * text.getFontSize();
		}
		return new Mesh(listToArray(vertices), listToArrayF(faces), mat);
	}

	public void addVerticesForCharacter(float curserX, float curserY, Character character, float fontSize,
			ArrayList<Vertex> vertices, ArrayList<Integer> faces) {
		float x = curserX + (character.getxOffset() * fontSize);
		float y = curserY + (character.getyOffset() * fontSize);
		float maxX = x + (character.getSizeX() * fontSize);
		float maxY = y + (character.getSizeY() * fontSize);
		float properX = (2 * x) - 1;
		float properY = (-2 * y) + 1;
		float properMaxX = (2 * maxX) - 1;
		float properMaxY = (-2 * maxY) + 1;
		addVertices(vertices, faces, character, properX, properY, properMaxX, properMaxY);
	}

	public static void addVertices(ArrayList<Vertex> vertices, ArrayList<Integer> faces, Character character, float x, float y,
			float maxX, float maxY) {
		int v1, v2, v3, v4;
		v1 = vertices.size();
		Vertex newVertex = new Vertex(new Vector3f(x,  y, 0), new Vector2f(character.getxTextureCoord(), character.getyTextureCoord()));
		vertices.add(newVertex);
		v2 = vertices.size();
		newVertex = new Vertex(new Vector3f(maxX, y, 0), new Vector2f(character.getXMaxTextureCoord(), character.getyTextureCoord()));
		vertices.add(newVertex);
		v3 = vertices.size();
		newVertex = new Vertex(new Vector3f(x, maxY, 0), new Vector2f(character.getxTextureCoord(), character.getYMaxTextureCoord()));
		vertices.add(newVertex);
		v4 = vertices.size();
		newVertex = new Vertex(new Vector3f(maxX, maxY, 0), new Vector2f(character.getXMaxTextureCoord(), character.getYMaxTextureCoord()));
		vertices.add(newVertex);
		faces.add(v1);
		faces.add(v2);
		faces.add(v4);
		faces.add(v1);
		faces.add(v3);
		faces.add(v4);
	}

	private static Vertex[] listToArray(ArrayList<Vertex> listOfVertices) {
		Vertex[] array = new Vertex[listOfVertices.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = listOfVertices.get(i);
		}
		return array;
	}

	private static int[] listToArrayF(ArrayList<Integer> listOfFloats) {
		int[] array = new int[listOfFloats.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = listOfFloats.get(i);
		}
		return array;
	}
}
