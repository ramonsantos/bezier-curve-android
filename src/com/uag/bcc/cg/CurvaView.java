package com.uag.bcc.cg;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * View que implementa as funcionalidades da curva de Bezier.
 * 
 * @author ramonsantos
 * 
 */
public class CurvaView extends View {

	/* Cores da linha */
	private Paint corPreto;
	private Paint corAmarelo;
	private Paint corVermelho;
	private Paint corVerde;
	private Paint corAzul;
	public static Paint corLinha;

	/* Opções de cores */
	public static final int AZUL = 0;
	public static final int AMARELO = 1;
	public static final int VERDE = 2;
	public static final int VERMELHO = 3;

	int indice = -1;
	private static List<Pontos> listaPontos;

	/* Construtores */
	public CurvaView(Context context, AttributeSet attrs) {

		super(context, attrs);
		this.init();

	}

	public CurvaView(Context context) {

		super(context);
		this.init();

	}

	/**
	 * Inicia os objetos da classe.
	 */
	private void init() {

		listaPontos = new ArrayList<Pontos>();

		this.corPreto = new Paint();
		this.corPreto.setARGB(255, 0, 0, 0);

		this.corAmarelo = new Paint();
		this.corAmarelo.setARGB(255, 255, 255, 0);

		this.corVermelho = new Paint();
		this.corVermelho.setARGB(255, 255, 0, 0);

		this.corVerde = new Paint();
		this.corVerde.setARGB(255, 0, 255, 0);

		this.corAzul = new Paint();
		this.corAzul.setARGB(255, 0, 0, 255);

		// Cor padrão Azul.
		corLinha = corAzul;

	}

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);

		// Desenha os pontos na tela.
		for (int i = 0; i < listaPontos.size(); i++) {

			Pontos p = listaPontos.get(i);
			//canvas.drawRect(p.getArea(), corPreto);
			canvas.drawCircle(p.getX(), p.getY(), 5, corPreto);
			//canvas.drawPoint(p.getX(), p.getY(), corPreto);

		}

		// Desenha a curva, caso não esteja adicionando pontos.
		if (!Tela.isAdd) {

			bezier(canvas);
		}

	}

	/**
	 * Apaga todos os pontos da tela.
	 */
	public void limparPontos() {

		listaPontos.clear();
		invalidate();

	}

	/**
	 * Plota a curva na tela.
	 */
	public void plotar() {

		invalidate();
	}

	/**
	 * Verifica se exite um ponto em determinado (x,y).
	 * 
	 * @param x
	 * @param y
	 * @return retorna o número do índice se encontrado ou -1 caso contrario.
	 */
	private int procurarPonto(float x, float y) {

		for (int i = 0; i < listaPontos.size(); i++) {

			Pontos p = listaPontos.get(i);
			RectF r = p.getArea();

			if (r.contains(x, y)) {

				return i;

			}
		}

		return -1;

	}

	/**
	 * Muda a cor da linha.
	 * 
	 * @param op
	 *            - Opção de Cor.
	 */
	public void mudarCorLinha(int op) {

		corLinha = corAzul;

		switch (op) {

		case AZUL:
			corLinha = corAzul;
			break;

		case AMARELO:
			corLinha = corAmarelo;
			break;

		case VERDE:
			corLinha = corVerde;
			break;

		case VERMELHO:
			corLinha = corVermelho;
			break;

		default:
			corLinha = corAzul;

		}

		invalidate();

	}

	/**
	 * Método para calcular a curva de Bezier.
	 * 
	 * @param canvas
	 */
	private void bezier(Canvas canvas) {

		int quantPontos = listaPontos.size();

		if (quantPontos >= 3) {

			int i = 0;

			// Regra da divisão para Curva de Bezier Cúbica.
			while (i + 3 < quantPontos) {

				float lX;

				if ((listaPontos.get(i + 3).getX() - listaPontos.get(i).getX()) < 0) {

					lX = (-(listaPontos.get(i + 3).getX() - listaPontos.get(i)
							.getX()));

				} else {

					lX = (listaPontos.get(i + 3).getX() - listaPontos.get(i)
							.getX());

				}

				float lY;

				if ((listaPontos.get(i + 3).getY() - listaPontos.get(i).getY()) < 0) {

					lY = (-(listaPontos.get(i + 3).getY() - listaPontos.get(i)
							.getY()));

				} else {

					lY = (listaPontos.get(i + 3).getY() - listaPontos.get(i)
							.getY());

				}

				float inter;

				if (lX > lY) {

					inter = (1 / lX);

				} else {

					inter = (1 / lY);

				}

				float pX = 0, pY = 0, xAnt = 0, yAnt = 0;

				for (float t = 0; t <= 1; t += inter) {

					pX = (float) (((-1 * Math.pow(t, 3) + 3 * Math.pow(t, 2)
							- 3 * t + 1)
							* listaPontos.get(i).getX()// (1 - t)^3[B0]

							+ (3 * Math.pow(t, 3) - 6 * Math.pow(t, 2) + 3 * t)
							* listaPontos.get(i + 1).getX()// 3t(1 - t)^2[B1]

							+ (-3 * Math.pow(t, 3) + 3 * Math.pow(t, 2))
							* listaPontos.get(i + 2).getX()// 3t^2(1 - t)[B2]

					+ (1 * Math.pow(t, 3)) * listaPontos.get(i + 3).getX()));// t^3[B3]

					pY = (float) (((-1 * Math.pow(t, 3) + 3 * Math.pow(t, 2)
							- 3 * t + 1)
							* listaPontos.get(i).getY()
							+ (3 * Math.pow(t, 3) - 6 * Math.pow(t, 2) + 3 * t)
							* listaPontos.get(i + 1).getY()
							+ (-3 * Math.pow(t, 3) + 3 * Math.pow(t, 2))
							* listaPontos.get(i + 2).getY() + (1 * Math.pow(t,
							3)) * listaPontos.get(i + 3).getY()));

					if (t == 0) {

						xAnt = pX;
						yAnt = pY;

					} else {

						canvas.drawLine((float) xAnt, (float) yAnt, (float) pX,
								(float) pY, corLinha);

						xAnt = pX;
						yAnt = pY;

					}

				}

				i = i + 3;

			}

			// Regra da divisão para Curva de Bezier Quadrática.
			if (i + 2 < quantPontos) {

				float lX;

				if ((listaPontos.get(i + 2).getX() - listaPontos.get(i).getX()) < 0) {

					lX = (-(listaPontos.get(i + 2).getX() - listaPontos.get(i)
							.getX()));

				} else {

					lX = (listaPontos.get(i + 2).getX() - listaPontos.get(i)
							.getX());

				}

				float lY;

				if ((listaPontos.get(i + 2).getY() - listaPontos.get(i).getY()) < 0) {

					lY = (-(listaPontos.get(i + 2).getY() - listaPontos.get(i)
							.getY()));

				} else {

					lY = (listaPontos.get(i + 2).getY() - listaPontos.get(i)
							.getY());

				}

				float inter;

				if (lX > lY) {

					inter = (1 / lX);

				} else {

					inter = (1 / lY);

				}

				float pX = 0, pY = 0, xAnt = 0, yAnt = 0;

				for (float t = 0; t <= 1; t += inter) {

					pX = (float) ((

					+(1 * Math.pow(t, 2) - 2 * t + 1)
							* listaPontos.get(i).getX()

							+ (-2 * Math.pow(t, 2) + 2 * Math.pow(t, 1))
							* listaPontos.get(i + 1).getX()

					+ (1 * Math.pow(t, 2)) * listaPontos.get(i + 2).getX()));

					pY = (float) ((

					+(1 * Math.pow(t, 2) - 2 * t + 1)
							* listaPontos.get(i).getY()

							+ (-2 * Math.pow(t, 2) + 2 * Math.pow(t, 1))
							* listaPontos.get(i + 1).getY()

					+ (1 * Math.pow(t, 2)) * listaPontos.get(i + 2).getY()));

					if (t == 0) {

						xAnt = pX;
						yAnt = pY;

					} else {

						canvas.drawLine((float) xAnt, (float) yAnt, (float) pX,
								(float) pY, corLinha);

						xAnt = pX;
						yAnt = pY;

					}

				}

				i = i + 2;

			}

			// Regra da divisão para Curva de Bezier Linear.
			else if (i + 1 < quantPontos) {

				float lX;

				if ((listaPontos.get(i + 1).getX() - listaPontos.get(i).getX()) < 0) {

					lX = (-(listaPontos.get(i + 1).getX() - listaPontos.get(i)
							.getX()));

				} else {

					lX = (listaPontos.get(i + 1).getX() - listaPontos.get(i)
							.getX());

				}

				float lY;

				if ((listaPontos.get(i + 1).getY() - listaPontos.get(i).getY()) < 0) {

					lY = (-(listaPontos.get(i + 1).getY() - listaPontos.get(i)
							.getY()));

				} else {

					lY = (listaPontos.get(i + 1).getY() - listaPontos.get(i)
							.getY());

				}

				float inter;

				if (lX > lY) {

					inter = (1 / lX);

				} else {

					inter = (1 / lY);

				}

				float pX = 0, pY = 0, xAnt = 0, yAnt = 0;

				for (float t = 0; t <= 1; t += inter) {

					pX = (float) ((

					(-t + 1) * listaPontos.get(i).getX()

					+ (t) * listaPontos.get(i + 1).getX()));

					pY = (float) ((

					(-t + 1) * listaPontos.get(i).getY()

					+ (t) * listaPontos.get(i + 1).getY()));

					if (t == 0) {

						xAnt = pX;
						yAnt = pY;

					} else {

						canvas.drawLine((float) xAnt, (float) yAnt, (float) pX,
								(float) pY, corLinha);

						xAnt = pX;
						yAnt = pY;
					}
				}

				i++;

			}else{
				
				//...
				
			}

		}

	}

	/**
	 * Eventos de toque.
	 */
	public boolean onTouchEvent(MotionEvent event) {

		float x = event.getX();
		float y = event.getY();

		Pontos pontoSelect = null;

		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:

			if (Tela.isAdd) {

				listaPontos.add(new Pontos(x, y));

			} else if (Tela.isRemove) {

				int i = procurarPonto(x, y);

				if (i != -1) {

					listaPontos.remove(i);

				}

			} else {

				for (int i = 0; i < listaPontos.size(); i++) {

					Pontos p = listaPontos.get(i);
					RectF r = p.getArea();

					if (r.contains(x, y)) {

						pontoSelect = p;
						indice = i;

					}

				}

			}

			break;

		case MotionEvent.ACTION_MOVE:

			if (indice != -1) {

				pontoSelect = listaPontos.get(indice);
				pontoSelect.moverPonto(x, y);

			}

			break;

		case MotionEvent.ACTION_UP:

			pontoSelect = null;
			indice = -1;

		}

		invalidate();

		return true;

	}

}
