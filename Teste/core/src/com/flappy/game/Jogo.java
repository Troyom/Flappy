package com.flappy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.sun.xml.internal.messaging.saaj.soap.GifDataContentHandler;

import java.util.Random;

public class Jogo extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture[] passaro;
	private Texture fundo;
	private Texture canoTopo;
	private Texture canoBaixo;
	private float larguraDispositivo;
	private float alturaDispositivo;


	private int gravidade = 0;
	private int pontos=0;

	private float variacao = 0;
	private float posicaoInicialVerticalPassaro = 0;
	private float posicaoCanoHorizontal;
	private float posicaoCanoVertical;
	private float espaçoEntreCano;

	BitmapFont textoPontuacao;
	private  boolean passouCano=false;
	private Random random;

	private ShapeRenderer shapeRenderer;
	private Circle circuloPassaro;
	private Rectangle retanguloCanoCima;
	private Rectangle retanguloCanoBaixo;



	@Override
	public void create() {


		inicializarTexturas();
		inicializarObjetos();

	}

	private void inicializarObjetos() {

        random=new Random();

		batch = new SpriteBatch();

		//Ageita a largura da tela
		larguraDispositivo = Gdx.graphics.getWidth();

		//Ageita a altur da tela
		alturaDispositivo= Gdx.graphics.getHeight();

		//Ageita o posicionamento da tela
		posicaoInicialVerticalPassaro = alturaDispositivo / 2;

		posicaoCanoHorizontal =larguraDispositivo;
		espaçoEntreCano=350;

		textoPontuacao=new BitmapFont();
		textoPontuacao.setColor(Color.WHITE);
		textoPontuacao.getData().setScale(10);

		shapeRenderer=new ShapeRenderer();
		circuloPassaro=new Circle();
		retanguloCanoCima=new Rectangle();
		retanguloCanoBaixo=new Rectangle();

	}

	private void inicializarTexturas() {
		//Cria fundo
		fundo = new Texture("fundo.png");

		//Cria sprites de passaro
		passaro = new Texture[3];

		//sprit 1
		passaro[0] = new Texture("passaro1.png");

		//sprit 2
		passaro[1] = new Texture("passaro2.png");

		//sprit 3
		passaro[2] = new Texture("passaro3.png");

		canoBaixo = new Texture("cano baixo maior.png");

		canoTopo = new Texture("cano topo maior.png");

	}

	@Override
	public void render() {

		verificarEstadoJogo();

		desenharTexturas();

		detectarColisao();

		validaPontos();


	}

    private void detectarColisao() {
	    circuloPassaro.set(50+passaro[0].getWidth()/2,
                posicaoInicialVerticalPassaro   + passaro[0].getHeight()/2, passaro[0].getWidth()/2);

	    retanguloCanoBaixo.set(posicaoCanoHorizontal,
                alturaDispositivo/2-canoBaixo.getHeight()-espaçoEntreCano/2+posicaoCanoVertical, canoBaixo.getWidth(),
                canoBaixo.getHeight());

	    retanguloCanoCima.set(posicaoCanoHorizontal,
                alturaDispositivo/2-canoTopo.getHeight()-espaçoEntreCano/2+posicaoCanoVertical,
                canoTopo.getWidth(), canoBaixo.getHeight());

	    boolean bateuCanoCima= Intersector.overlaps(circuloPassaro,retanguloCanoCima);
        boolean bateuCanoBaixo= Intersector.overlaps(circuloPassaro,retanguloCanoBaixo);

        if (bateuCanoBaixo||bateuCanoCima){
            Gdx.app.log("log","bateu");
        }
    }

    private void validaPontos() {
	    if (posicaoCanoHorizontal<50-passaro[0].getWidth()){
            if (!passouCano){
                pontos++;
                passouCano=true;
            }
        }

    }

    private void desenharTexturas() {

		batch.begin();

		batch.draw(fundo, 0, 0, larguraDispositivo,alturaDispositivo);
		batch.draw(passaro[(int)variacao], 30,posicaoInicialVerticalPassaro);
		batch.draw(canoBaixo, posicaoCanoHorizontal,
                alturaDispositivo/2-canoBaixo.getHeight()-espaçoEntreCano/2+posicaoCanoVertical);
		batch.draw(canoTopo, posicaoCanoHorizontal, alturaDispositivo/2+espaçoEntreCano/2+posicaoCanoVertical);
		textoPontuacao.draw(batch, String.valueOf(pontos), larguraDispositivo/2, alturaDispositivo-100 );

		batch.end();

	}

	private void verificarEstadoJogo() {

        posicaoCanoHorizontal -=Gdx.graphics.getDeltaTime()*200;
        if (posicaoCanoHorizontal <-canoBaixo.getHeight()){
            posicaoCanoHorizontal =larguraDispositivo;
            posicaoCanoVertical=random.nextInt(400)-200;
            passouCano=false;
        }

        boolean toqueTela = Gdx.input.justTouched();
        if (Gdx.input.justTouched()) {
            gravidade = -25;
        }

        //Faz ele pular
        if(posicaoInicialVerticalPassaro>0||toqueTela)
            posicaoInicialVerticalPassaro=posicaoInicialVerticalPassaro-gravidade;


        variacao+=Gdx.graphics.getDeltaTime()*10;
        if(variacao>3)
            variacao=0;

        gravidade++;




    }

	@Override
	public void dispose () {

	}
}
