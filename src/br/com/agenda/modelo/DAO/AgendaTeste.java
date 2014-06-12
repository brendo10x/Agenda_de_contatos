package br.com.agenda.modelo.DAO;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import br.com.agenda.modelo.entidades.Contato;
import br.com.agenda.modelo.entidades.DonoAgenda;
import br.com.agenda.modelo.entidades.Endereco;

public class AgendaTeste {

	
	public static void main(String[] args) {
		
		
		/*DonoAgenda donoBrendo = new DonoAgenda();
		donoBrendo.setNome_dono_agenda("Ronaldo Nazário");
		DonoAgendaDAO.inserirDonoAgenda(donoBrendo);
		
		int ultimoDono = DonoAgendaDAO.getIdUltimoDono();
		
		Contato contatoBrendo = new Contato();
		contatoBrendo.setCelular_contato("85423101");
		contatoBrendo.setEmail_contato("ronaldo12@gmail.com.br");
		contatoBrendo.setId_fk_contato_dono_agenda(ultimoDono);
		
		ContatoDAO.inserirContato(contatoBrendo);
		
		Endereco enderecoBrendo = new Endereco();
		enderecoBrendo.setBairro_endereco("tauba");
		enderecoBrendo.setCidade_endereco("Quixadá");
		enderecoBrendo.setEstado_endereco("SE");
		enderecoBrendo.setId_fk_endereco_dono_agenda(ultimoDono );
		
		EnderecoDAO.inserirEndereco(enderecoBrendo);
		
		//donoBrendo.setNome_dono_agenda("Ronaldo Nazário");
		//DonoAgendaDAO.alterarDonoAgenda(donoBrendo, ultimoDono);
*/		
		 Connection conn = ConexaoDAO.getConexao();
		try {
			Statement stmt = conn.createStatement();
			stmt.executeQuery("truncate `tbl_dono_agenda`");
			stmt.executeQuery("truncate `tbl_contato`");
			stmt.executeQuery("truncate `tbl_endereco`");
		
			JOptionPane.showMessageDialog(null, "Deu certo");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro");
			e.printStackTrace();
		}
		
		
	}

}
