package com.own.master.engine.objects;

import java.util.ArrayList;

public interface Gameable {
	abstract void kill();
	abstract void game();
	abstract int getValue(ArrayList<GameObject> hand);
	abstract void checkTable();
	abstract Object self();
}
