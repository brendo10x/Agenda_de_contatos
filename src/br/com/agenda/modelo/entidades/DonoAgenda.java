package br.com.agenda.modelo.entidades;

public class DonoAgenda {

	//Dono o bixo é o cão mermo
	
	private int id_dono_agenda;
	private String nome_dono_agenda;
	private byte[] foto_dono_agenda;
	private int sexo_dono_agenda;
	

	public int getSexo_dono_agenda() {
		return sexo_dono_agenda;
	}

	public void setSexo_dono_agenda(int sexo_dono_agenda) {
		this.sexo_dono_agenda = sexo_dono_agenda;
	}

	public byte[] getFoto_dono_agenda() {
		return foto_dono_agenda;
	}

	public void setFoto_dono_agenda(byte[] foto_dono_agenda) {
		this.foto_dono_agenda = foto_dono_agenda;
	}

	public int getId_dono_agenda() {
		return id_dono_agenda;
	}

	public void setId_dono_agenda(int id_dono_agenda) {
		this.id_dono_agenda = id_dono_agenda;
	}

	public String getNome_dono_agenda() {
		return nome_dono_agenda;
	}

	public void setNome_dono_agenda(String nome_dono_agenda) {
		this.nome_dono_agenda = nome_dono_agenda;
	}

}
