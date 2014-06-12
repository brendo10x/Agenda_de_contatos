package br.com.agenda.modelo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import br.com.agenda.modelo.entidades.DonoAgenda;
import br.com.agenda.modelo.entidades.Estado;

public class EstadoDAO {
	private static Connection conn;

	private static PreparedStatement pstmt;
	private static Statement stmt;
	private static ResultSet rsResultado;

	public static Estado[] selecEstadoEndereco() {
		conn = ConexaoDAO.getConexao();
		Estado[] estado = null;
		// Contato[] contatoDono = new Contato[10];
		int cont = 0;
		try {
			stmt = conn.createStatement();
			rsResultado = stmt
					.executeQuery("SELECT count(*) AS total FROM `tbl_estados_brasileiros` ");
			if (rsResultado.next()) {
				estado = new Estado[rsResultado.getInt("total")];
			}

			rsResultado = stmt
					.executeQuery("SELECT * FROM `tbl_estados_brasileiros` ");

			while (rsResultado.next()) {
				estado[cont] = new Estado();
				estado[cont].setId_estado(rsResultado.getInt("id_estado"));
				estado[cont].setNome_estado(rsResultado
						.getString("nome_estado"));
				estado[cont].setSigla_estado(rsResultado
						.getString("sigla_estado"));
				cont++;
			}
			//JOptionPane.showMessageDialog(null, estado[1].getNome_estado());
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConexaoDAO.encerraConexao(conn);
			ConexaoDAO.encerraStm(stmt);
		}

		return estado;
	}

}
