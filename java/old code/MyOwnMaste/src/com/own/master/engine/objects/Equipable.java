package com.own.master.engine.objects;

import com.own.master.engine.graphics.Material;

public interface Equipable {
	int ammo_noAct = 0;
	int ammo_melee = 1;
	int ammo_small = 2;
	int ammo_large = 3;
	abstract GameObject getGO();
	abstract Material getMat();
	abstract void setGO(GameObject object);
	abstract void setVisible();
	abstract void setAmmoType(int type);
	abstract void setAmmo(int amount);
	abstract void use();
	abstract void destroy();
}
