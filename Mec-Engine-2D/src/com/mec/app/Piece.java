package com.mec.app;

import com.mec.engine.GameContainer;
import com.mec.engine.GameObject;
import com.mec.engine.Renderer;
import com.mec.engine.audio.SoundClip;

public class Piece extends GameObject
{
	private int width;
	private int height;
	private int color;

	private Direction direction = Direction.NONE;

	private SoundClip pickupSound;

	public Piece(int width, int height, int color)
	{
		this.tag = "snake-piece";
		this.width = width;
		this.height = height;
		this.color = color;

		pickupSound = new SoundClip("/res/Audio/pickup.wav");
	}

	@Override
	public void update(GameContainer gc, Application app, float deltaTime)
	{
	}

	@Override
	public void render(GameContainer gc, Renderer renderer)
	{
	}


	public int getWidth() {	return width; }
	public int getHeight() { return height; }

	public int getColor() {	return color; }

	public Direction getDirection() { return direction; }
	public void setDirection(Direction direction)
	{
		for (int i = 0; i <= 100; i++)
		{
			this.direction = direction;
		}
	}
}
