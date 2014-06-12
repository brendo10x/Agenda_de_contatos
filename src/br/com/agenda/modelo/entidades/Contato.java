package br.com.agenda.modelo.entidades;

public class Contato {

	private int id_contato;
	private int id_fk_contato_dono_agenda;
	private String email_contato;
	private String celular_contato;

	public int getId_contato() {
		return id_contato;
	}

	public void setId_contato(int id_contato) {
		this.id_contato = id_contato;
	}

	public int getId_fk_contato_dono_agenda() {
		return id_fk_contato_dono_agenda;
	}

	public void setId_fk_contato_dono_agenda(int id_fk_contato_dono_agenda) {
		this.id_fk_contato_dono_agenda = id_fk_contato_dono_agenda;
	}

	public String getEmail_contato() {
		return email_contato;
	}

	public void setEmail_contato(String email_contato) {
		this.email_contato = email_contato;
	}

	public String getCelular_contato() {
		return celular_contato;
	}

	public void setCelular_contato(String celular_contato) {
		this.celular_contato = celular_contato;
	}

}
