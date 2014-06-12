package br.com.agenda.modelo.DAO;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.*;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import br.com.agenda.modelo.entidades.*;

public class DonoAgendaDAO {

	private static Connection conn;

	private static PreparedStatement pstmt;
	private static Statement stmt;
	private static ResultSet rsResultado;

	// Funções de Inserir, Alterar, Excluir, Selecionar

	// aqui vai inserir o dono da agenda + endereço + contato
	public static void inserirDonoAgenda(DonoAgenda donoAgenda) {
		conn = ConexaoDAO.getConexao();

		try {
			pstmt = conn
					.prepareStatement("INSERT INTO tbl_dono_agenda(nome_dono_agenda,foto_dono_agenda,sexo_dono_agenda) VALUES (?,?,?)");
			pstmt.setString(1, donoAgenda.getNome_dono_agenda());
			pstmt.setBytes(2, donoAgenda.getFoto_dono_agenda());
			pstmt.setInt(3, donoAgenda.getSexo_dono_agenda());
			pstmt.executeUpdate();

			JOptionPane.showMessageDialog(null,
					"Ok!\n Dados inseridos com sucesso");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro! AgendaDono \n Não foi possível inserir no banco de dados.");

		} finally {
			ConexaoDAO.encerraConexao(conn);
			ConexaoDAO.encerraStm(pstmt);
		}

	}

	public static int getIdUltimoDono() {
		conn = ConexaoDAO.getConexao();
		int ultimoIdDonoAgenda = 0;
		try {
			stmt = conn.createStatement();
			rsResultado = stmt
					.executeQuery("SELECT MAX(`id_dono_agenda`) AS UltimoDono FROM `tbl_dono_agenda`");
			if (rsResultado.next()) {
				ultimoIdDonoAgenda = rsResultado.getInt("UltimoDono");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConexaoDAO.encerraConexao(conn);
			ConexaoDAO.encerraStm(pstmt);
		}

		return ultimoIdDonoAgenda;
	}

	// aqui vou atualizar os atributos do objeto e no banco de dados nas
	// tabelas, faz 2 coisas
	public static void alterarDonoAgenda(DonoAgenda donoAgenda, int idDonoAgenda) {
		conn = ConexaoDAO.getConexao();

		try {

			pstmt = conn
					.prepareStatement("UPDATE `tbl_dono_agenda` SET  `nome_dono_agenda` = ? ,`foto_dono_agenda` = ?, `sexo_dono_agenda` = ? WHERE id_dono_agenda = ? ");

			pstmt.setString(1, donoAgenda.getNome_dono_agenda());
			pstmt.setBytes(2, donoAgenda.getFoto_dono_agenda());
			pstmt.setInt(3, donoAgenda.getSexo_dono_agenda());
			pstmt.setInt(4, idDonoAgenda);
			pstmt.executeUpdate();

			JOptionPane.showMessageDialog(null, "Editado com sucesso");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConexaoDAO.encerraConexao(conn);
			ConexaoDAO.encerraStm(pstmt);
		}
	}

	// aqui vai exluir o dono da própria tabela e das outras relacionadas
	public static void excluirDonoAgenda(int idDonoAgenda) {
		conn = ConexaoDAO.getConexao();

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM `tbl_dono_agenda` WHERE `id_dono_agenda` = "
					+ idDonoAgenda);
			stmt.executeUpdate("DELETE FROM `tbl_contato` WHERE `id_fk_contato_dono_agenda` = "
					+ idDonoAgenda);
			stmt.executeUpdate("DELETE FROM `tbl_endereco` WHERE `id_fk_endereco_dono_agenda` = "
					+ idDonoAgenda);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConexaoDAO.encerraConexao(conn);
			ConexaoDAO.encerraStm(stmt);
		}
	}

	// método customizado para retorna todos da culuna nome e id da tabela Dono
	// agenda, que será usada da tabela

	public static String[] selecionarNomeDonoAgenda() {
		conn = ConexaoDAO.getConexao();
		String[] stgNomeDono = null;
		int cont = 0;
		try {
			stmt = conn.createStatement();
			rsResultado = stmt
					.executeQuery("SELECT count(*) AS total FROM `tbl_dono_agenda` ");
			if (rsResultado.next()) {
				stgNomeDono = new String[rsResultado.getInt("total")];
			}

			rsResultado = stmt
					.executeQuery("SELECT `nome_dono_agenda` FROM `tbl_dono_agenda` ");

			while (rsResultado.next()) {
				stgNomeDono[cont] = rsResultado.getString("nome_dono_agenda");
				cont++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConexaoDAO.encerraConexao(conn);
			ConexaoDAO.encerraStm(stmt);
		}

		return stgNomeDono;

	}

	public static String[] selecionarIdDonoAgenda() {
		conn = ConexaoDAO.getConexao();
		String[] stgIdDono = null;
		int cont = 0;
		try {
			stmt = conn.createStatement();
			rsResultado = stmt
					.executeQuery("SELECT count(*) AS total FROM `tbl_dono_agenda` ");
			if (rsResultado.next()) {
				stgIdDono = new String[rsResultado.getInt("total")];
			}

			rsResultado = stmt
					.executeQuery("SELECT `id_dono_agenda` FROM `tbl_dono_agenda` ");

			while (rsResultado.next()) {
				stgIdDono[cont] = rsResultado.getString("id_dono_agenda");
				cont++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConexaoDAO.encerraConexao(conn);
			ConexaoDAO.encerraStm(stmt);
		}

		return stgIdDono;
	}

	public static DonoAgenda[] selecDonoAgenda() {
		conn = ConexaoDAO.getConexao();
		DonoAgenda[] donoAgenda = null;
		// Contato[] contatoDono = new Contato[10];
		int cont = 0;
		try {
			stmt = conn.createStatement();
			rsResultado = stmt
					.executeQuery("SELECT count(*) AS total FROM `tbl_dono_agenda` ");
			if (rsResultado.next()) {
				donoAgenda = new DonoAgenda[rsResultado.getInt("total")];
			}

			rsResultado = stmt.executeQuery("SELECT * FROM `tbl_dono_agenda` ");

			while (rsResultado.next()) {
				donoAgenda[cont] = new DonoAgenda();
				donoAgenda[cont].setId_dono_agenda(rsResultado
						.getInt("id_dono_agenda"));
				donoAgenda[cont].setNome_dono_agenda(rsResultado
						.getString("nome_dono_agenda"));
				donoAgenda[cont].setFoto_dono_agenda(rsResultado
						.getBytes("foto_dono_agenda"));
				donoAgenda[cont].setSexo_dono_agenda(rsResultado
						.getInt("sexo_dono_agenda"));

				cont++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConexaoDAO.encerraConexao(conn);
			ConexaoDAO.encerraStm(stmt);
		}

		return donoAgenda;
	}

	public static DonoAgenda[] pesquisaSelecDonoAgenda(String pesquisa) {
		conn = ConexaoDAO.getConexao();
		DonoAgenda[] donoAgenda = null;

		int cont = 0;
		try {
			stmt = conn.createStatement();
			rsResultado = stmt
					.executeQuery("SELECT count(*) AS total FROM `tbl_dono_agenda` WHERE `nome_dono_agenda` LIKE '%"
							+ pesquisa + "%' ");
			if (rsResultado.next()) {
				donoAgenda = new DonoAgenda[rsResultado.getInt("total")];
			}

			rsResultado = stmt
					.executeQuery("SELECT * FROM `tbl_dono_agenda` WHERE `nome_dono_agenda` LIKE '%"
							+ pesquisa + "%' ");

			while (rsResultado.next()) {
				donoAgenda[cont] = new DonoAgenda();
				donoAgenda[cont].setId_dono_agenda(rsResultado
						.getInt("id_dono_agenda"));
				donoAgenda[cont].setNome_dono_agenda(rsResultado
						.getString("nome_dono_agenda"));
				donoAgenda[cont].setFoto_dono_agenda(rsResultado
						.getBytes("foto_dono_agenda"));
				donoAgenda[cont].setSexo_dono_agenda(rsResultado
						.getInt("sexo_dono_agenda"));
				cont++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConexaoDAO.encerraConexao(conn);
			ConexaoDAO.encerraStm(stmt);
		}

		return donoAgenda;
	}

	// Métodos da foto

	// pega os bytes e retorna a imagem
	public static BufferedImage TransformaEmImagem(byte[] foto)
			throws IOException {
		ByteArrayInputStream bytesImg = new ByteArrayInputStream(foto);
		BufferedImage img = ImageIO.read(bytesImg);
		BufferedImage aux = new BufferedImage(150, 120, img.getType());
		Graphics2D g = aux.createGraphics();
		AffineTransform at = AffineTransform.getScaleInstance((double) 150
				/ img.getWidth(), (double) 120 / img.getHeight());
		g.drawRenderedImage(img, at);
		return aux;
	}

	// pega a imagem e retorna os bytes
	public static byte[] TransformandoEmBinario(String caminho)
			throws IOException {
		ByteArrayOutputStream bytesImg = new ByteArrayOutputStream();
		BufferedImage imagemBuffer = ImageIO.read(new File(caminho));
		ImageIO.write(imagemBuffer, "jpg", bytesImg);
		bytesImg.flush();
		byte[] bytes = bytesImg.toByteArray();
		bytesImg.close();
		return bytes;
	}

}
