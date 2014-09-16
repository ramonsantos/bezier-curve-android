package com.uag.bcc.cg;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Tela da aplicação.
 * 
 * @author ramonsantos
 *
 */
public class Tela extends Activity {

	public static boolean isAdd = true;
	public static boolean isMove = false;
	public static boolean isRemove = false;
	
	private static CurvaView curvaView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		curvaView = new CurvaView(this);
		super.onCreate(savedInstanceState);
		
		setContentView(curvaView);
		
		//Tabs		
		ActionBar actBar = getActionBar();
		actBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		Tab tabAdd = actBar.newTab();
		tabAdd.setText("Add");
		tabAdd.setTabListener(new TabListener(TabListener.ADD, curvaView));
		actBar.addTab(tabAdd);
		
		Tab tabMove = actBar.newTab();
		tabMove.setText("Move");
		tabMove.setTabListener(new TabListener(TabListener.MOVE, curvaView));
		actBar.addTab(tabMove);
	
		Tab tabRemove = actBar.newTab();
		tabRemove.setText("Remove");
		tabRemove.setTabListener(new TabListener(TabListener.REMOVE, curvaView));	
		actBar.addTab(tabRemove);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.tela, menu);
		return true;
	}
	
	
	@SuppressWarnings("static-access")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		int id = item.getItemId();
		
		if (id == R.id.action_limpar) {

			curvaView.limparPontos();
			return true;
			
		}else if(id == R.id.action_amarelo){
			
			curvaView.mudarCorLinha(curvaView.AMARELO);
			
		}else if(id == R.id.action_azul){
			
			curvaView.mudarCorLinha(curvaView.AZUL);
			
		}else if(id == R.id.action_verde){
			
			curvaView.mudarCorLinha(curvaView.VERDE);
			
		}else if(id == R.id.action_vermelho){
			
			curvaView.mudarCorLinha(curvaView.VERMELHO);
			
		}else{
			
			//...
			
		}
		
		return super.onOptionsItemSelected(item);
		
	}
	
}
