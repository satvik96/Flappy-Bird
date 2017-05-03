package com.satviknarang.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] birds;
	Texture topTube;
	Texture bottomTube;
	Texture gameOver;

	BitmapFont font;


	int scoreingTube = 0;
	int score = 0;
	int flapState = 0;
	float birdY = 0;
	float velocity = 0;
	int gameState = 0;
	float gravity = 2;
	float gap = 400;
	Random random;
	int noOfTubes = 4;
	float maxTubeOffset;
	float[] tubeOffset = new float[noOfTubes];
	float tubeVelocity = 4;
	float[] tubeX = new float[noOfTubes];
	float distanceBetweenTubes;
	//	ShapeRenderer shapeRenderer;
	Circle birdCircle;


	Rectangle[] topTubeRectangles;
	Rectangle[] bottomTubeRectangles;

	@Override
	public void create() {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");

		maxTubeOffset = Gdx.graphics.getHeight() / 2 - gap / 2 - 100;
		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		gameOver = new Texture("gameover.png");
		random = new Random();
		distanceBetweenTubes = Gdx.graphics.getWidth() * 3 / 4;
		//shapeRenderer=new ShapeRenderer();
		birdCircle = new Circle();
		topTubeRectangles = new Rectangle[noOfTubes];
		bottomTubeRectangles = new Rectangle[noOfTubes];

		font = new BitmapFont();
		font.setColor(Color.LIME);
		font.getData().setScale(10);
		startGame();

	}


	public void startGame() {


	birdY=Gdx.graphics.getHeight()/2-birds[0].

	getHeight()/2;

	for(
	int i = 0;
	i<noOfTubes;i++)

	{
		tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + Gdx.graphics.getWidth() + i * distanceBetweenTubes;
		tubeOffset[i] = (random.nextFloat() - (0.5f)) * (Gdx.graphics.getHeight() - gap - 200);

		topTubeRectangles[i] = new Rectangle();
		bottomTubeRectangles[i] = new Rectangle();


	}

}


	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState  == 1 ) {

			if(tubeX[scoreingTube]<Gdx.graphics.getWidth()/2)
			{
				score++;

				Gdx.app.log("Score:",String.valueOf(score));

				if(scoreingTube<noOfTubes-1)
				{
					scoreingTube++;
				}
				else
					scoreingTube=0;


			}




			if (Gdx.input.justTouched()) {

				velocity=-30;

			}
			for(int i=0;i<noOfTubes;i++) {

				if(tubeX[i]< -topTube.getWidth())
				{
					tubeX[i]=tubeX[i] + noOfTubes * distanceBetweenTubes;
					tubeOffset[i]=(random.nextFloat()-(0.5f))*(Gdx.graphics.getHeight()-gap-200);
				}
				else {
					tubeX[i] = tubeX[i] - tubeVelocity;



				}



					batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
					batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);

				topTubeRectangles[i]=new Rectangle(tubeX[i],Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(),topTube.getHeight());
				bottomTubeRectangles[i]=new Rectangle(tubeX[i],Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i],bottomTube.getWidth(),bottomTube.getHeight());
			}
			if(birdY>0 )
			{

			velocity=velocity+gravity;
			birdY -= velocity;

			}
			else
				gameState=2;

		}
		else if(gameState==0)
		{
			if (Gdx.input.justTouched()) {

				gameState=1;

			}
		}


		else if(gameState==2)
		{
			batch.draw(gameOver,Gdx.graphics.getWidth()/2-gameOver.getWidth()/2 , Gdx.graphics.getHeight()/2-gameOver.getHeight()/2);

			if (Gdx.input.justTouched()) {

				gameState=1;
				startGame();;
				scoreingTube=0;
				score=0;
				velocity=0;

			}



		}

		if (flapState == 0)
			flapState = 1;
		else
			flapState = 0;




		batch.draw(birds[flapState], Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2, birdY);

		font.draw(batch,String.valueOf(score),100,200);

		batch.end();

//		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//		shapeRenderer.setColor(Color.BLUE);

		birdCircle.set(Gdx.graphics.getWidth()/2,birdY+birds[flapState].getHeight()/2,birds[flapState].getWidth()/2);

		//shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);

		for(int i=0;i<noOfTubes;i++) {
			//shapeRenderer.rect(tubeX[i],Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(),topTube.getHeight());
			//shapeRenderer.rect(tubeX[i],Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i],bottomTube.getWidth(),bottomTube.getHeight());

			if(Intersector.overlaps(birdCircle,topTubeRectangles[i]) || (Intersector.overlaps(birdCircle,bottomTubeRectangles[i])))
			{
					gameState=2;



			}


		}


		//shapeRenderer.end();
	}
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}
}