package br.com.agenda.modelo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import br.com.agenda.modelo.entidades.*;

public class ContatoDAO {

	private static Connection conn;
	private static PreparedStatement pstmt;
	private static Statement stmt;
	private static ResultSet rsResultado;

	public static void inserirContato(Contato contato) {
		conn = ConexaoDAO.getConexao();

		try {

			pstmt = conn
					.prepareStatement("INSERT INTO tbl_contato(id_fk_contato_dono_agenda,"
							+ " email_contato, celular_contato) VALUES (?,?,?)");

			pstmt.setInt(1, contato.getId_fk_contato_dono_agenda());
			pstmt.setString(2, contato.getEmail_contato());
			pstmt.setString(3, contato.getCelular_contato());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			JOptionPane
					.showMessageDialog(null,
							"Erro! Contato \n Não foi possível inserir no banco de dados.");
		} finally {
			ConexaoDAO.encerraConexao(conn);
			ConexaoDAO.encerraStm(pstmt);
		}

	}

	public static void alterarContato(Contato contato, int idContato) {
		conn = ConexaoDAO.getConexao();

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE `tbl_contato` SET  `celular_contato` = '"
					+ contato.getCelular_contato()
					+ "',`email_contato` = '"
					+ contato.getEmail_contato()
					+ "'  WHERE  `id_fk_contato_dono_agenda` = "
					+ idContato);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConexaoDAO.encerraConexao(conn);
			ConexaoDAO.encerraStm(stmt);
		}
	}

	public static Contato[] selecContato() {
		conn = ConexaoDAO.getConexao();
		Contato[] contatoDono = null;
		// Contato[] contatoDono = new Contato[10];
		int cont = 0;
		try {
			stmt = conn.createStatement();
			rsResultado = stmt
					.executeQuery("SELECT count(*) AS total FROM `tbl_contato` ");
			if (rsResultado.next()) {
				contatoDono = new Contato[rsResultado.getInt("total")];
			}

			rsResultado = stmt.executeQuery("SELECT * FROM `tbl_contato` ");

			while (rsResultado.next()) {
				contatoDono[cont] = new Contato();
				contatoDono[cont].setId_contato(rsResultado
						.getInt("id_contato"));
				contatoDono[cont].setId_fk_contato_dono_agenda(rsResultado
						.getInt("id_fk_contato_dono_agenda"));
				contatoDono[cont].setCelular_contato(rsResultado
						.getString("celular_contato"));
				contatoDono[cont].setEmail_contato(rsResultado
						.getString("email_contato"));
				cont++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConexaoDAO.encerraConexao(conn);
			ConexaoDAO.encerraStm(stmt);
		}

		return contatoDono;

	}
}
