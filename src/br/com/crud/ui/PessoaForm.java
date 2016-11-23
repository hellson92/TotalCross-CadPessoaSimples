package br.com.crud.ui;

import java.sql.SQLException;

import br.com.crud.business.EditMode;
import br.com.crud.dao.PessoaDAO;
import br.com.crud.model.Pessoa;
import totalcross.sys.Convert;
import totalcross.sys.InvalidNumberException;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Control;
import totalcross.ui.Grid;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;

public class PessoaForm extends Container{
	private static final int INSERE_BTN_ID		= 100;
	private static final int ATUALIZA_BTN_ID 	= 101;
	private static final int DELETE_BTN_ID 		= 102;
	private static PessoaDAO	 dao 		    = null; 
	private Grid			 pessoaGrid;
	
	public PessoaForm() throws SQLException{
		dao = new PessoaDAO();
	}
	public void initUI(){
		Button insereButton   = new Button("Novo");
		Button atualizaButton = new Button("Atualiza");
		Button deleteButton   = new Button("Remove");
		
		insereButton.appId	 = INSERE_BTN_ID;
		atualizaButton.appId = ATUALIZA_BTN_ID;
		deleteButton.appId	 = DELETE_BTN_ID;
		
		add(deleteButton, RIGHT - 2, TOP + 2, PREFERRED + 5, PREFERRED + 5 );
		add(atualizaButton, BEFORE - 2, SAME, SAME, SAME);
		add(insereButton, BEFORE - 2, SAME, SAME, SAME);
		
		pessoaGrid = new Grid(new String[]{"Id", "Nome"}, new int[]{-10, -30}, new int[]{RIGHT, LEFT}, false);
		
		add(pessoaGrid, LEFT, AFTER + 2, FILL - 2, FILL - 2);
		try {
			pessoaGrid.setItems(dao.todos());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void onEvent(Event event){
		switch (event.type) {
		case ControlEvent.PRESSED:
			switch (((Control) event.target).appId) {
				case INSERE_BTN_ID:
					new PessoaEditForm(dao, new Pessoa(), EditMode.INSERT).popupNonBlocking();				
					break;
				case ATUALIZA_BTN_ID:{
					if(selecionouUmaPessoa()){
						new PessoaEditForm(dao, pegaPessoaSelecionada(), EditMode.UPDATE).popupNonBlocking();
					}
					break;
				}
				case DELETE_BTN_ID:{
					if(selecionouUmaPessoa()){
						try {
							dao.delete(pegaPessoaSelecionada());
						} catch (SQLException e) {
							e.printStackTrace();
						}
						try {
							pessoaGrid.setItems(dao.todos());
						} catch (SQLException e) {
							e.printStackTrace();
						}
						pessoaGrid.setSelectedIndex(-1);
					}
					break;
				}
			}			
			break;
		case ControlEvent.WINDOW_CLOSED:{
			if(event.target instanceof PessoaEditForm){
				try {
					pessoaGrid.setItems(dao.todos());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				pessoaGrid.setSelectedIndex(-1);
			}
		}
		break;
		}		
	}
	
	private boolean selecionouUmaPessoa(){
		boolean selecionouUmaPessoa = pessoaGrid.getSelectedIndex() >= 0;
		
		if(!selecionouUmaPessoa){
			new MessageBox("Atenção", "Nenhuma pessoa foi selecionada").popup();;
		}		
		return selecionouUmaPessoa;
	}
	
	private Pessoa pegaPessoaSelecionada(){
		Pessoa p = new Pessoa();
		String[] colunas = pessoaGrid.getSelectedItem();
		
		try{
			p.setId(Convert.toInt(colunas[0]));
		}catch(InvalidNumberException e){
			e.printStackTrace();
		}
		p.setName(colunas[1]);
		return p;
	}
	
}
