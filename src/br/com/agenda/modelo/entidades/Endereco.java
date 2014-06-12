package br.com.agenda.modelo.entidades;

public class Endereco {

	private int id_endereco;
	private int id_fk_endereco_dono_agenda;
	private String cidade_endereco;
	private String estado_endereco;
	private String bairro_endereco;

	public int getId_endereco() {
		return id_endereco;
	}

	public void setId_endereco(int id_endereco) {
		this.id_endereco = id_endereco;
	}

	public int getId_fk_endereco_dono_agenda() {
		return id_fk_endereco_dono_agenda;
	}

	public void setId_fk_endereco_dono_agenda(int id_fk_endereco_dono_agenda) {
		this.id_fk_endereco_dono_agenda = id_fk_endereco_dono_agenda;
	}

	public String getCidade_endereco() {
		return cidade_endereco;
	}

	public void setCidade_endereco(String cidade_endereco) {
		this.cidade_endereco = cidade_endereco;
	}

	public String getEstado_endereco() {
		return estado_endereco;
	}

	public void setEstado_endereco(String estado_endereco) {
		this.estado_endereco = estado_endereco;
	}

	public String getBairro_endereco() {
		return bairro_endereco;
	}

	public void setBairro_endereco(String bairro_endereco) {
		this.bairro_endereco = bairro_endereco;
	}

}
