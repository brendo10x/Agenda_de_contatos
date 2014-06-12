package br.com.agenda.modelo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import br.com.agenda.modelo.entidades.*;

public class EnderecoDAO {

	private static Connection conn;
	private static PreparedStatement pstmt;
	private static Statement stmt;
	private static ResultSet rsResultado;
	
	public static void inserirEndereco(Endereco endereco) {
		conn = ConexaoDAO.getConexao();

		try {
			pstmt = conn
					.prepareStatement("INSERT INTO `tbl_endereco`(id_fk_endereco_dono_agenda,cidade_endereco,estado_endereco,bairro_endereco) VALUES (?,?,?,?)");
			pstmt.setInt(1, endereco.getId_fk_endereco_dono_agenda());
			pstmt.setString(2, endereco.getCidade_endereco());
			pstmt.setString(3, endereco.getEstado_endereco());
			pstmt.setString(4, endereco.getBairro_endereco());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			JOptionPane
					.showMessageDialog(null,
							"Erro! Endereço \n Não foi possível inserir no banco de dados.");
		} finally {
			ConexaoDAO.encerraConexao(conn);
			ConexaoDAO.encerraStm(pstmt);
		}
	}

	public static void alterarEndereco(Endereco endereco, int idEndereco) {
		conn = ConexaoDAO.getConexao();

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE `tbl_endereco` SET  `cidade_endereco` = '"
					+ endereco.getCidade_endereco()
					+ "',`estado_endereco` = '"
					+ endereco.getEstado_endereco()
					+ "',`bairro_endereco` = '"
					+ endereco.getBairro_endereco()
					+ "'  WHERE  `id_fk_endereco_dono_agenda` = " + idEndereco);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConexaoDAO.encerraConexao(conn);
			ConexaoDAO.encerraStm(stmt);
		}
	}
	
	public static Endereco[] selecEnderecoDono (){
		conn = ConexaoDAO.getConexao();
		Endereco[]  enderecoDono = null;
		//Contato[] contatoDono = new Contato[10];
		int cont = 0;
		try {
			stmt = conn.createStatement();
			 rsResultado = stmt.executeQuery("SELECT count(*) AS total FROM `tbl_endereco` ");
			if(rsResultado.next()){
				enderecoDono = new Endereco[rsResultado.getInt("total")];
			}
		
			rsResultado  = stmt.executeQuery("SELECT * FROM `tbl_endereco` ");
			
			while(rsResultado .next()){
				enderecoDono[cont] = new Endereco();
				enderecoDono[cont].setId_endereco(rsResultado.getInt("id_endereco"));
				enderecoDono[cont].setId_fk_endereco_dono_agenda(rsResultado.getInt("id_fk_endereco_dono_agenda"));
				enderecoDono[cont].setBairro_endereco(rsResultado.getString("bairro_endereco"));
				enderecoDono[cont].setCidade_endereco(rsResultado.getString("cidade_endereco"));
				enderecoDono[cont].setEstado_endereco(rsResultado.getString("estado_endereco"));
				cont++;		
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConexaoDAO.encerraConexao(conn);
			ConexaoDAO.encerraStm(stmt);
		}
		
		return enderecoDono;
		
	}
	
	
	
}
