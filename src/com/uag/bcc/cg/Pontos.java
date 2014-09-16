package com.uag.bcc.cg;

import android.graphics.RectF;

/**
 * Classe responsável por modelar os pontos de controle.
 * 
 * @author ramonsantos
 * 
 */
public class Pontos {

	/**
	 * Coordenada 'X' do ponto.
	 */
	private float x;

	/**
	 * Coordenada 'Y' do ponto.
	 */
	private float y;

	/**
	 * Área ao redor do ponto.
	 */
	private final RectF area;

	/* Construtores */
	public Pontos(float x, float y) {

		this.x = x;
		this.y = y;
		this.area = new RectF(x - 10, y - 10, x + 10, y + 10);

	}

	/* Métodos setters e getters */
	public float getX() {

		return x;

	}

	public void setX(float x) {

		this.x = x;

	}

	public float getY() {

		return y;

	}

	public void setY(float y) {

		this.y = y;

	}

	public RectF getArea() {

		return area;

	}

	/**
	 * Método responsável por movimentar o ponto na tela.
	 * 
	 * @param x
	 *            - Coordenada 'X'
	 * @param y
	 *            - Coordenada 'Y'
	 */
	public void moverPonto(float x, float y) {

		this.setX(x);
		this.setY(y);
		this.area.set(x - 10, y - 10, x + 10, y + 10);

	}

}
