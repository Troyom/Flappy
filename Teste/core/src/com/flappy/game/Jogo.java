package com.flappy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Jogo extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture[] passaro;
	private Texture fundo;
	private Texture canoTopo;
	private Texture canoBaixo;

	private float larguraDispositivo;
	private float alturaDispositivo;


	private int movimentax = 0;

	private float variacao = 0;
	private float gravidade = 0;
	private float posicaoInicialVerticalPassaro = 0;
	private float posiçaoCanoHorizontal;
	private float espaçoEntreCano;


	@Override
	public void create() {


		inicializarTexturas();
		inicializarObjetos();

	}

	private void inicializarObjetos() {

		batch = new SpriteBatch();

		//Ageita a largura da tela
		larguraDispositivo = Gdx.graphics.getWidth();

		//Ageita a altur da tela
		alturaDispositivo= Gdx.graphics.getHeight();

		//Ageita o posicionamento da tela
		posicaoInicialVerticalPassaro = alturaDispositivo / 2;

		posiçaoCanoHorizontal=larguraDispositivo;
		espaçoEntreCano=150;

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


	}

	private void desenharTexturas() {

		//Ativa ao tocar na tela
		boolean toqueTela = Gdx.input.justTouched();
		if (Gdx.input.justTouched()) {
			gravidade = -25;
		}

		//Faz ele pular
		if(posicaoInicialVerticalPassaro>0||toqueTela)
			posicaoInicialVerticalPassaro=posicaoInicialVerticalPassaro-gravidade;

		//
		variacao+=Gdx.graphics.getDeltaTime()*10;


		//anima o passaro
		if(variacao>3)variacao=0;

		gravidade++;
	}

	private void verificarEstadoJogo() {

		batch.begin();

		//Coloca o fundo
		batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);

		//anima o passaro
		batch.draw(passaro[(int) variacao], 30, posicaoInicialVerticalPassaro);

		batch.draw(canoBaixo, posiçaoCanoHorizontal- 100, alturaDispositivo/2-canoBaixo.getHeight()-espaçoEntreCano/2);
		batch.draw(canoTopo, posiçaoCanoHorizontal-100, alturaDispositivo/2+espaçoEntreCano);

		batch.end();


	}

	@Override
	public void dispose () {

	}
}
