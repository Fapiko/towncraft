package com.fapiko.towncraft.rendering;

import com.sun.j3d.utils.geometry.ColorCube;

import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

public class Block extends ColorCube {

	private TransformGroup transformGroup;
	private Vector3f position = new Vector3f(0, 0, 0);

	private int x = 0;
	private int y = 0;

	private int z = 0;

	public static final int NEW_TRANSFORM = 1;

	public Block(double v, int flags) {
		super(v);

		if ((flags & NEW_TRANSFORM) != 0) {
			transformGroup = new TransformGroup();
			transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
			transformGroup.addChild(this);
		}
	}

	public Block(double v, TransformGroup transformGroup) {
		super(v);

		this.transformGroup = transformGroup;
		transformGroup.addChild(this);
	}

	public Vector3f getPosition3f() {
		return position;
	}

	public TransformGroup getTransformGroup() {
		return transformGroup;
	}

	public void setPosition3f(Vector3f position) {
		this.position = position;
	}

	public void setPosition(float x, float y, float z) {
		setPosition3f(new Vector3f(x, y, z));
	}
}
