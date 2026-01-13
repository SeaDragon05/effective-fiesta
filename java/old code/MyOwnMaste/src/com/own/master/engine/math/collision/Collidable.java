package com.own.master.engine.math.collision;

public interface Collidable {
	abstract CollisionBox getCollision();
	abstract void setCollisionBox(CollisionBox box);
	abstract Object self();
}
