package br.com.crud.ui;



import java.sql.SQLException;

import br.com.crud.business.EditMode;
import br.com.crud.dao.PessoaDAO;
import br.com.crud.model.Pessoa;
import totalcross.sys.Convert;
import totalcross.ui.Button;
import totalcross.ui.Control;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.Window;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;

public class PessoaEditForm extends Window{
	
	private static final int SALVAR_BTN_ID 		= 100;
	private static final int CANCELAR_BTN_ID 	= 101;
	private final PessoaDAO dao;
	
	private Edit nameEdit;
	
	public PessoaEditForm(PessoaDAO dao, Pessoa pessoa, int editMode){
		this.dao = dao;
		this.appObj = pessoa;
		this.appId = editMode;
	}
	
	protected void postPopup(){
		super.postPopup();
		initUI();
	}
	
	public void initUI(){
		super.initUI();
		
		Button salvarButton   = new Button("Salvar");
		Button cancelarButton = new Button("Cancelar");
		Edit   idEdit 		  = new Edit("9999");
		nameEdit 		      = new Edit();
		
		
		salvarButton.appId = SALVAR_BTN_ID;
		cancelarButton.appId = CANCELAR_BTN_ID;
		idEdit.setEditable(false);
		idEdit.setText(Convert.toString(((Pessoa) appObj).getId()));
		nameEdit.setText(((Pessoa) appObj).getName());
		
		add(cancelarButton, RIGHT - 2, TOP + 2, PREFERRED + 2, PREFERRED);
		add(salvarButton, BEFORE - 2, SAME, SAME, SAME);
		add(new Label("Nome"), LEFT, AFTER + 5);
		add(nameEdit, SAME, AFTER + 2);		
		
	}
	
	public void onEvent(Event event){
		switch (event.type) {
			case ControlEvent.PRESSED:{
				switch (((Control) event.target).appId) {
					case SALVAR_BTN_ID:{
						if(edicaoFoiPreenchidaCorretamente()){
							switch (appId) {
								case EditMode.INSERT:{
									try {
										dao.insert((Pessoa) appObj);
									} catch (SQLException e) {
										e.printStackTrace();
									}
									break;
								}
								case EditMode.UPDATE:{
									try {
										dao.update((Pessoa) appObj);
									} catch (SQLException e) {
										e.printStackTrace();
									}
									break;
								}
							}
							unpop();
						}
						break;
					}
					case CANCELAR_BTN_ID:{
						unpop();
						break;
					}
				}
			}						
		}
	}
	
	public boolean edicaoFoiPreenchidaCorretamente(){
		if(!(this.nameEdit.getLength() > 0)){
			new MessageBox("Erro", "Nome inválido").popup();
			return false;
		}
		
		((Pessoa) appObj).setName(this.nameEdit.getText());
		
		return true;
	}

}
