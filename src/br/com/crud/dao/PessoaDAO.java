package br.com.crud.dao;

import java.sql.SQLException;

import br.com.crud.model.Pessoa;
import totalcross.sql.Connection;
import totalcross.sql.DriverManager;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.sys.Convert;
import totalcross.sys.Settings;

public class PessoaDAO {

	private Connection con 			= null;
	PreparedStatement  pSmt	     	= null;
	PreparedStatement  pSmtInsert   = null;
	PreparedStatement  pSmtUpdate   = null;
	PreparedStatement  pSmtDelete   = null;
	PreparedStatement  pSmtSelect   = null;
	PreparedStatement  pSmtId   = null;
	
	
	public PessoaDAO() throws SQLException {
		con = DriverManager.getConnection("jdbc:sqlite:" + Convert.appendPath(Settings.appPath, "crud.db"));

		pSmt = con.prepareStatement("create table if not exists person(id int primary key, name varchar, born datetime, number varchar)");
		pSmt.executeUpdate();
	}

	public void insert(Pessoa p) throws SQLException {	
		pSmtInsert = con.prepareStatement("insert into person values(?, ?, ?, ?)");
		pSmtInsert.clearParameters();
		p.setId(proximoId());
		pSmtInsert.setInt(1, p.getId());
		pSmtInsert.setString(2, p.getName());
		pSmtInsert.setDate(3, p.getBorn());
		pSmtInsert.setString(4, p.getNumber());	
		
		pSmtInsert.executeUpdate();
	}

	public void update(Pessoa p) throws SQLException {
		pSmtUpdate = con.prepareStatement("UPDATE person SET name = ? WHERE id = ?");
		pSmtUpdate.clearParameters();
		pSmtUpdate.setString(1, p.getName());
		pSmtUpdate.setInt(2, p.getId());
		
		pSmtUpdate.executeUpdate();
	}

	public void delete(Pessoa p) throws SQLException {
		pSmtDelete = con.prepareStatement("DELETE FROM person WHERE id = ?");
		pSmtDelete.clearParameters();
		pSmtDelete.setInt(1, p.getId());
		
		pSmtDelete.executeUpdate();		
	}

	public String[][] todos() throws SQLException {
		
		pSmtSelect   	 	= con.prepareStatement("SELECT * FROM person");
		ResultSet rs	    =  pSmtSelect.executeQuery();
		
	    int amount = 0;
		while(rs.next()){
			amount += 1;
		}
		
		String[][] retorno  = new String[amount][4];		
		
		rs	=  pSmtSelect.executeQuery();	
		for (int i = 0; i < amount; i++) {
			rs.next();
			for (int j = 0; j < 4; j++) {
				retorno[i][j] = rs.getString(j+1);		
			}
			
		}		
		rs.close();				
		return retorno;
	}
	
	public int proximoId() throws SQLException{		
		int retorno = 1;
		
		pSmtId = con.prepareStatement("SELECT max(id) as  vId from person");
		ResultSet rs = pSmtId.executeQuery();
		while(rs.next()){
			retorno = rs.getInt("vId") + 1;
		}
		rs.close();
		return retorno;
	}

}
