package engine.maths;


import org.lwjgl.glfw.GLFW;

import engine.chapters.Chapter1;
import engine.objects.GameObject;
import engine.io.Input;
import engine.io.SimpleAudioPlayer;
import main.Main;

public class Physics {
	private  float gravity;// = 2f;
	private Vector3f position;// = new Vector3f(0,1,0);
	private Vector3f velocity = new Vector3f(0,0,0);
	private float friction;// = 0.4f;
	private boolean falling = true;
	private float level = 0f;// = -10;
	private GameObject object;
	public Physics(float gravity, Vector3f position, float friction, float level, GameObject object) {
		this.gravity = gravity;
		this.position = position;
		this.friction = friction;
		this.level = level;
		this.object = object;
	}
	
	public void update3d() {
		if (Input.isKeyDown(GLFW.GLFW_KEY_D)) velocity = Vector3f.add(velocity, new Vector3f(0.01f * (float) -Math.cos(Math.toRadians(Main.camera.getRotation().getY())), 0, 0.01f * (float) Math.sin(Math.toRadians(Main.camera.getRotation().getY()))));
		if (Input.isKeyDown(GLFW.GLFW_KEY_A)) velocity = Vector3f.add(velocity, new Vector3f(0.01f * (float) Math.cos(Math.toRadians(Main.camera.getRotation().getY())), 0, 0.01f * (float) -Math.sin(Math.toRadians(Main.camera.getRotation().getY()))));
		if (Input.isKeyDown(GLFW.GLFW_KEY_S)) velocity = Vector3f.add(velocity, new Vector3f(0.01f * (float) -Math.sin(Math.toRadians(Main.camera.getRotation().getY())), 0, 0.01f * (float) -Math.cos(Math.toRadians(Main.camera.getRotation().getY()))));
		if (Input.isKeyDown(GLFW.GLFW_KEY_W)) velocity = Vector3f.add(velocity, new Vector3f(0.01f * (float) Math.sin(Math.toRadians(Main.camera.getRotation().getY())), 0, 0.01f * (float) Math.cos(Math.toRadians(Main.camera.getRotation().getY()))));
		if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) jump();
		
		if (position.getY() <= 0) {
			falling = false;
			velocity.setY(velocity.getY() * -friction);
			velocity.setX(velocity.getX() * friction);
			velocity.setZ(velocity.getZ() * friction);
			position.setX(position.getX() - (velocity.getX() / gravity));
			position.setZ(position.getZ() - (velocity.getZ() / gravity));
			position.setY(position.getY() - (velocity.getY() / gravity));
		} else {
			if (falling) {
				velocity.setY(velocity.getY() + 0.0098f);//SUPEH SMAWW NUMBWAH
			}
			falling = true;
			position.setZ(position.getZ() - (velocity.getZ() / gravity));
			position.setY(position.getY() - (velocity.getY() / gravity));
			position.setX(position.getX() - (velocity.getX() / gravity));
		}
		if (position.getX() >= 7f) {
			velocity.setX(velocity.getX() * -friction);
			position.setX(6.99f);
		}
		if (position.getX() <= -7f) {
			velocity.setX(velocity.getX() * -friction);
			position.setX(-6.99f);
		}
		if (position.getZ() >= 7f) {
			velocity.setZ(velocity.getZ() * -friction);
			position.setZ(6.99f);
		}
		if (position.getZ() <= -7f) {
			velocity.setZ(velocity.getZ() * -friction);
			position.setZ(-6.99f);
		}
		object.setPosition(position);
	}
	public void collide3d(float[] surface) {
		if (intersect(position, surface)) {
			if (position.getY() <= surface[4]) {
				velocity.setY(velocity.getY() * -friction);
				velocity.setX(velocity.getX() * friction);
				velocity.setZ(velocity.getZ() * friction);
				falling = false;
				position.setY(position.getY() - (velocity.getY() / gravity));
				position.setX(position.getX() - (velocity.getX() / gravity));
				position.setZ(position.getZ() - (velocity.getZ() / gravity));
			}
		} 
	}
	private boolean intersect(Vector3f position2, float[] surface) {
		return (position2.getX() - 0.5f <= surface[1] && position2.getX() + 0.5f >= surface[0]) &&
				(position2.getZ() - 0.5f <= surface[3] && position2.getZ() + 0.5f >= surface[2]);
	}

	public float getGravity() {
		return gravity;
	}
	public void setGravity(float gravity) {
		this.gravity = gravity;
	}
	public Vector3f getPosition() {
		return position;
	}
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	public Vector3f getVelocity() {
		return velocity;
	}
	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}
	public float getFriction() {
		return friction;
	}
	public void setFriction(float friction) {
		this.friction = friction;
	}
	public boolean isFalling() {
		return falling;
	}
	public void setFalling(boolean falling) {
		this.falling = falling;
	}
	public float getLevel() {
		return level;
	}
	public void setLevel(float level) {
		this.level = level;
	}

	public void update() {
		if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) Chapter1.getMap().getPlayerPhysics().jump(); 
		if (Input.isKeyDown(GLFW.GLFW_KEY_W)) Chapter1.getMap().getPlayerPhysics().jump();  
		if (Input.isKeyDown(GLFW.GLFW_KEY_A)) Chapter1.getMap().getPlayerPhysics().left(); 
		if (Input.isKeyDown(GLFW.GLFW_KEY_D)) Chapter1.getMap().getPlayerPhysics().right();
		if (Main.level == 0) {
			Main.camera.setPositionY(position.getY());
		}
		if (position.getY() <= level) {
			falling = false;
			velocity.setY(velocity.getY() * -friction);
			velocity.setX(velocity.getX() * friction);
			velocity.setZ(velocity.getZ() * friction);
			position.setX(position.getX() - (velocity.getX() / gravity));
			position.setY(position.getY() - (velocity.getY() / gravity));
		} else {
			if (falling) {
				velocity.setY(velocity.getY() + 0.0098f);//SUPEH SMAWW NUMBWAH
			}
			falling = true;
			position.setZ(position.getZ() - (velocity.getZ() / gravity));
			position.setY(position.getY() - (velocity.getY() / gravity));
			position.setX(position.getX() - (velocity.getX() / gravity));
		}
		if (position.getX() >= 7f) {
			velocity.setX(velocity.getX() * -friction);
			position.setX(6.99f);
		}
		if (position.getX() <= -7f) {
			velocity.setX(velocity.getX() * -friction);
			position.setX(-6.99f);
		}
		object.setPosition(position);
	}
	public void jump() {
		float energy = 0.5f;
		if (position.getY() <= 0) {
			velocity.setY(energy);
			SimpleAudioPlayer.playaudio("./sounds/350902__cabled-mess__jump-c-01.wav");
		} else if (!falling) {
			SimpleAudioPlayer.playaudio("./sounds/350902__cabled-mess__jump-c-01.wav");
			velocity.setY(velocity.getY() - energy);
		}
	}
	public void left() {
		if (Main.level == 0) velocity.setX(velocity.getX() + .01f);
		if (Main.level >= 1) {
			if (!falling) {
				velocity.setX(velocity.getX() + .1f);
			} else {
				velocity.setX(velocity.getX() + .01f);
			}
		}
	}
	public void right() {
		if (Main.level == 0) velocity.setX(velocity.getX() - .01f);
		if (Main.level >= 1) {
			if (!falling) {
				velocity.setX(velocity.getX() - .1f);
			} else {
				velocity.setX(velocity.getX() - .01f);
			}
		}
	}
	public void collide(float height, GameObject object) {
		if (Main.level == 0) {
			if (position.getY() <= height) {
				velocity.setY(velocity.getY() * -friction);
				falling = false;
				velocity.setX(velocity.getX() * friction);
				velocity.setZ(velocity.getZ() * friction);
				position.setX(position.getX() - (velocity.getX() / gravity));
				position.setY(position.getY() - (velocity.getY() / gravity));
			}
		} else {
			if (position.getY() <= height) {
				velocity.setY(velocity.getY() * -friction);
				falling = false;
				velocity.setZ(velocity.getZ() * friction);
				position.setY(position.getY() - (velocity.getY() / gravity));
			} else if (position.getY() <= height - 0.5f) {
				falling = true;
				velocity.setY(velocity.getY() * -friction);
				velocity.setZ(velocity.getZ() * friction);
				position.setY(position.getY() - (velocity.getY() / gravity));
			}
			if (position.getX() - 0.5f <= object.getPosition().getX() + 0.5f) {
				velocity.setX(velocity.getX() * -friction);
				position.setX(position.getX() - (velocity.getX() / gravity));
			} else if (position.getX() + 0.5f >= object.getPosition().getX() - 0.5f) {
				velocity.setX(velocity.getX() * -friction);
				position.setX(position.getX() - (velocity.getX() / gravity));
			}
		}
	}
	public void collision(GameObject object) {
		Vector3f po = object.getPosition();
		if (position.getX() - 0.5f <= po.getX() + 0.5f && position.getX() - 0.5f >= po.getX() - 1.5f) {//check right side of physics object with entire cube
			if (position.getX() + 0.5f >= po.getX() - 0.5f && position.getX() + 0.5f <= po.getX() + 1.5f) {//check left side of the cube
				if (position.getY() + 0.4f >= po.getY() - 0.4f && position.getY() + 0.4f <= po.getY() + 1.4f) {
					collide(po.getY() + 1f, object);
				}
				if (position.getY() - 0.4f <= po.getY() + 0.4f && position.getY() - 0.4f >= po.getY() + 0.4f) {
					collide(po.getY() + 1.5f, object);
				}
			}
		}
	}
	public void collision3d(GameObject object) {//will work for ONLY 1x1x1 cubes!
		Vector3f po = object.getPosition();
		if (position.getX() - 0.5f <= po.getX() + 0.5f && position.getX() - 0.5f >= po.getX() - 1.5f) {//check right side of physics object with entire cube
			if (position.getX() + 0.5f >= po.getX() - 0.5f && position.getX() + 0.5f <= po.getX() + 1.5f) {//check left side of the cube
				if (position.getZ() - 0.5f <= po.getZ() + 0.5f && position.getZ() - 0.5f >= po.getZ() - 1.5f) {
					if (position.getZ() + 0.5f >= po.getZ() - 0.5f && position.getZ() + 0.5f <= po.getZ() + 1.5f) {
						if (position.getY() + 0.4f >= po.getY() - 0.4f && position.getY() + 0.4f <= po.getY() + 1.4f) {
							collide(po.getY() + 1f, object);
						}
						if (position.getY() - 0.4f <= po.getY() + 0.4f && position.getY() - 0.4f >= po.getY() + 0.4f) {
							collide(po.getY() + 1.5f, object);
						}
					}
				}
			}
		}
	}
}
