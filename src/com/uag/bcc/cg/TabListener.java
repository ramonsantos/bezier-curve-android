package com.uag.bcc.cg;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;

/**
 * Classe que implementa os eventos de uma Tab.
 * 
 * @author ramonsantos
 * 
 */
public class TabListener implements ActionBar.TabListener {

	public static final int ADD = 0;
	public static final int MOVE = 1;
	public static final int REMOVE = 2;
	
	private int opcao;
	private CurvaView curva;

	public TabListener(int op, CurvaView c) {

		this.opcao = op;
		curva = c;

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		this.acao();
		curva.plotar();

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

		//
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {

		//
	}

	private void acao() {

		switch (opcao) {
		case ADD:

			Tela.isAdd = true;
			Tela.isMove = false;
			Tela.isRemove = false;

			break;

		case MOVE:

			Tela.isAdd = false;
			Tela.isMove = true;
			Tela.isRemove = false;

			break;

		case REMOVE:

			Tela.isAdd = false;
			Tela.isMove = false;
			Tela.isRemove = true;

			break;

		default:
			break;
		}

	}

}
