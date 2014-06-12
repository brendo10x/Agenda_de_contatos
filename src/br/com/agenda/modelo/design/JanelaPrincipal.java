package br.com.agenda.modelo.design;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import br.com.agenda.modelo.DAO.*;
import br.com.agenda.modelo.entidades.Contato;
import br.com.agenda.modelo.entidades.DonoAgenda;
import br.com.agenda.modelo.entidades.Endereco;
import br.com.agenda.modelo.entidades.Estado;

public class JanelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	private JButton btnSalvar, btnEditar, btnExcluir, btnCancelar,
			btnAtualizar, btnPesquisar, btnNovo, btnFoto, btnCtrPrimeiro,
			btnCtrAnterior, btnCtrProximo, btnCtrUltimo;
	private JTextField txtNome, txtEmail, txtCelular, txtCidade, txtEstado,
			txtBairro, txtPesquisar;
	private JLabel lblNome, lblEmail, lblCelular, lblCidade, lblEstado,
			lblBairro, lblFoto, lblEstadoNome;
	private JPanel panlContato, panlEndereco;
	private JTabbedPane tabAgenda;

	private JTable tabelaRegistros;
	private String[] colunas;
	private JScrollPane scrollPane;
	
	private String caminhoImagem = null;
	
	//JComboBox estado
	private JComboBox jcbEstadoEndereco;
	private Estado estado[];

	private int totalLinhas;
	private int localLinhaFoco;
	// variável q vai dizer se é pra editar ou para inserir
	private boolean isControleEditar = false;

	// selecionar para excluir em massa
	private JTable jtbChekBoxSelecionar;
	private JCheckBox[] jckBoxs;
	private JCheckBox jckBoxTodosSelecionados;
	private String[] colunasChekBoxSelecionar;
	// variáveis da tabela
	private String[] idDono;
	private String[] nomeDono;
	private String[] linha;
	private DefaultTableModel ModeloAgenda;
	private int row;
	private int col;
	private int valorInicialId;

	// objetos do programa
	private DonoAgenda donoAgenda[];
	private Contato contato[];
	private Endereco endereco[];

	// variáveis da escolha de sexo no programa
	private JRadioButton jrdSexoMasculino,jrdSexoFeminino;
	private JPanel pnlEscolhaSexo;
	private ButtonGroup groupJrdSexo; 
	private int jrdEscolhaSexo = 1;
	// Contrutor
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JanelaPrincipal() {
		super("Agenda de Contatos");

		// setBounds(x, y, width, height)
		setLayout(null);
		setBounds(250, 120, 900, 500);
		Container conteudo = getContentPane();

		// *Criando os Objetos

		btnSalvar = new JButton("Salvar");
		btnEditar = new JButton("Editar");
		btnExcluir = new JButton("Excluir");
		btnCancelar = new JButton("Cancelar");
		btnAtualizar = new JButton("Atualizar");
		btnPesquisar = new JButton("Pesquisar");
		btnNovo = new JButton("Novo");
		btnFoto = new JButton("Foto...");
		btnCtrPrimeiro = new JButton("PRI");
		btnCtrAnterior = new JButton("ANT");
		btnCtrProximo = new JButton("PROX");
		btnCtrUltimo = new JButton("ÚLT");

		txtNome = new JTextField();
		txtCelular = new JTextField();
		txtEmail = new JTextField();
		txtBairro = new JTextField();
		txtCidade = new JTextField();
		txtEstado = new JTextField();
		txtPesquisar = new JTextField();

		lblNome = new JLabel("Nome:");
		lblCelular = new JLabel("Celular:");
		lblEmail = new JLabel("Email:");
		lblBairro = new JLabel("Bairro:");
		lblCidade = new JLabel("Cidade:");
		lblEstado = new JLabel("Estado:");
		lblFoto = new JLabel("");
		lblEstadoNome = new JLabel("Nome estado");

		// inicializar os estados para a combobox
		jcbEstadoEndereco = new JComboBox();

		// JCheckBox que vai selecionar todos os outros checkBox
		colunasChekBoxSelecionar = new String[] {"excluir"};
		
		jtbChekBoxSelecionar = new JTable();
		jckBoxTodosSelecionados = new JCheckBox("asd");
		
		
		// inicializar os objetos do escolha sexo
		jrdSexoMasculino = new JRadioButton("Masculino");
		jrdSexoFeminino = new JRadioButton("Feminino");
		pnlEscolhaSexo = new JPanel(new GridLayout(2,1));
		pnlEscolhaSexo.setBorder(BorderFactory.createTitledBorder("Sexo:"));
		
		groupJrdSexo = new ButtonGroup();
		groupJrdSexo.add(jrdSexoMasculino);
		groupJrdSexo.add(jrdSexoFeminino);
		
		
		// começa de 0
		jcbEstadoEndereco.setEnabled(false);

		panlContato = new JPanel(null);
		panlEndereco = new JPanel(null);

		tabAgenda = new JTabbedPane();

		// caracteristicas iniciais
		btnSalvar.setEnabled(false);
		btnCancelar.setEnabled(false);
		txtNome.setEditable(false);
		txtBairro.setEditable(false);
		txtCelular.setEditable(false);
		txtCidade.setEditable(false);
		txtEmail.setEditable(false);
		txtEstado.setEditable(false);

		// aqui está preenchendo a tabela
		idDono = DonoAgendaDAO.selecionarIdDonoAgenda();
		nomeDono = DonoAgendaDAO.selecionarNomeDonoAgenda();
		colunas = new String[] { "ID", "Nome","excluir"};

	
		
		linha = new String[2];
		ModeloAgenda = new DefaultTableModel(colunas, 0);
		
		
		
		// DefaultTableModel modelo = new DefaultTableModel(dados, colunas);
		tabelaRegistros = new JTable();

		tabelaRegistros.setModel(ModeloAgenda);

		scrollPane = new JScrollPane(tabelaRegistros);
		
	
		
		//	jckBoxs = new JCheckBox[2];
		//ModeloAgenda.addColumn("asd",jckBoxs);
		//ModeloAgenda.addColumn(3,"asd");
		// *Localização

		// setBounds(x, y, width, height)

		btnSalvar.setBounds(20, 50, 75, 30);
		btnEditar.setBounds(100, 50, 75, 30);
		btnExcluir.setBounds(180, 50, 75, 30);
		btnCancelar.setBounds(260, 50, 90, 30);
		btnAtualizar.setBounds(400, 10, 100, 30);
		btnPesquisar.setBounds(750, 10, 100, 30);
		btnNovo.setBounds(20, 10, 75, 30);
		btnCtrPrimeiro.setBounds(400, 390, 75, 30);
		btnCtrAnterior.setBounds(485, 390, 75, 30);
		btnCtrProximo.setBounds(570, 390, 75, 30);
		btnCtrUltimo.setBounds(655, 390, 75, 30);

		lblNome.setBounds(20, 120, 100, 20);
		txtNome.setBounds(60, 120, 290, 20);

		// componentes tab "contatos"
		lblCelular.setBounds(10, 20, 100, 20);
		txtCelular.setBounds(60, 20, 120, 20);
		lblEmail.setBounds(10, 50, 100, 20);
		txtEmail.setBounds(60, 50, 250, 20);
		lblFoto.setBounds(10, 80, 150, 135);
		btnFoto.setBounds(180, 80, 80, 20);

		// painel escolha sexo
		pnlEscolhaSexo.setBounds(180, 120, 130, 80);

		
		// componentes tab "endereço"
		lblCidade.setBounds(10, 20, 100, 20);
		txtCidade.setBounds(60, 20, 250, 20);
		lblEstado.setBounds(10, 50, 100, 20);
		jcbEstadoEndereco.setBounds(60, 50, 80, 20);
		lblEstadoNome.setBounds(150, 50, 150, 20);
		lblBairro.setBounds(10, 80, 100, 20);
		txtBairro.setBounds(60, 80, 250, 20);
		txtPesquisar.setBounds(545, 10, 200, 30);

		panlContato.setBounds(10, 10, 100, 100);
		panlEndereco.setBounds(10, 10, 100, 100);

		tabAgenda.setBounds(10, 170, 342, 250);

		scrollPane.setBounds(400, 50, 450, 322);

		// *Adicionando os objetos

		conteudo.add(btnSalvar);
		conteudo.add(btnEditar);
		conteudo.add(btnExcluir);
		conteudo.add(btnCancelar);
		conteudo.add(lblNome);
		conteudo.add(txtNome);
		conteudo.add(btnAtualizar);
		conteudo.add(txtPesquisar);
		conteudo.add(btnPesquisar);
		conteudo.add(btnNovo);
		conteudo.add(btnCtrPrimeiro);
		conteudo.add(btnCtrAnterior);
		conteudo.add(btnCtrProximo);
		conteudo.add(btnCtrUltimo);

		conteudo.add(scrollPane);

		//add os radioButton sexo no painel escolha sexo
		pnlEscolhaSexo.add(jrdSexoFeminino);
		pnlEscolhaSexo.add(jrdSexoMasculino);
		
		// adicionando ao painel contatos
		panlContato.add(lblCelular);
		panlContato.add(txtCelular);
		panlContato.add(lblEmail);
		panlContato.add(txtEmail);
		panlContato.add(lblFoto);
		panlContato.add(btnFoto);
		panlContato.add(pnlEscolhaSexo);
		
		// adicionando ao painel endereço
		panlEndereco.add(lblCidade);
		panlEndereco.add(txtCidade);
		panlEndereco.add(lblEstado);
		panlEndereco.add(jcbEstadoEndereco);
		panlEndereco.add(lblBairro);
		panlEndereco.add(txtBairro);
		panlEndereco.add(lblEstadoNome);

		// adicionando ao painel endereço na tab agenda
		tabAgenda.add("Contato", panlContato);
		tabAgenda.add("Endereço", panlEndereco);

		// adicionando ao conteúdo
		conteudo.add(tabAgenda);

		// adiciona um ouvinte aos botões
		BotaoOuvinte botaoOuvinte = new BotaoOuvinte();
		btnSalvar.addActionListener(botaoOuvinte);
		btnEditar.addActionListener(botaoOuvinte);
		btnExcluir.addActionListener(botaoOuvinte);
		btnCancelar.addActionListener(botaoOuvinte);
		btnAtualizar.addActionListener(botaoOuvinte);
		btnNovo.addActionListener(botaoOuvinte);
		btnPesquisar.addActionListener(botaoOuvinte);
		btnFoto.addActionListener(botaoOuvinte);

		btnCtrPrimeiro.addActionListener(botaoOuvinte);
		btnCtrAnterior.addActionListener(botaoOuvinte);
		btnCtrProximo.addActionListener(botaoOuvinte);
		btnCtrUltimo.addActionListener(botaoOuvinte);

		// adiciona um ouvinte para os radioButtons, escolha de sexo
		RadioButaoOuvinte radioButaoOuvinte = new RadioButaoOuvinte();
		jrdSexoFeminino.addActionListener(radioButaoOuvinte);
		jrdSexoMasculino.addActionListener(radioButaoOuvinte);
		
		// adiciona um ouvinte a comboBox estado
		ComboBoxOuvinte comboBoxOuvinte = new ComboBoxOuvinte();
		jcbEstadoEndereco.addActionListener(comboBoxOuvinte);

		// adiciona uma ouvinte a tabela de registros
		TabelaOuvinte tabelaOuvinte = new TabelaOuvinte();
		tabelaRegistros.getSelectionModel().addListSelectionListener(
				tabelaOuvinte);

		atualizaComboBoxEstado();
	
		// Aqui vou inicializar a tabela com os registros para os campos
		if (idDono.length != 0) {

			// atualuzando tabela
			atualizaTabela();
		
			tabelaRegistros.setRowSelectionInterval(0, 0);

			
			
			// desabilita botões de navegação
			btnCtrPrimeiro.setEnabled(false);
			btnCtrAnterior.setEnabled(false);
			btnFoto.setEnabled(false);
			jcbEstadoEndereco.setEnabled(false);
			
			
			jrdSexoFeminino.setEnabled(false);
			jrdSexoMasculino.setEnabled(false);
			//desabilita radioButtons escolha de sexo
			

		}// fim se tiver algum registro
		else {
			btnEditar.setEnabled(false);
			btnExcluir.setEnabled(false);
			btnPesquisar.setEnabled(false);
			btnAtualizar.setEnabled(false);
			btnFoto.setEnabled(false);

			btnCtrPrimeiro.setEnabled(false);
			btnCtrAnterior.setEnabled(false);
			btnCtrProximo.setEnabled(false);
			btnCtrUltimo.setEnabled(false);
			
			pnlEscolhaSexo.setEnabled(false);
			jrdSexoFeminino.setEnabled(false);
			jrdSexoMasculino.setEnabled(false);
		}

	}

	// esculta o botão para executar a ação do mesmo
	public class BotaoOuvinte implements ActionListener {

		// Trata evento do botão
		public void actionPerformed(ActionEvent evento) {
			if (evento.getSource() == btnEditar) {
				isControleEditar = true;
				// habilitar e desabilitar
				btnSalvar.setEnabled(true);
				btnEditar.setEnabled(false);
				btnCancelar.setEnabled(true);
				btnExcluir.setEnabled(false);
				btnSalvar.setEnabled(true);
				btnCancelar.setEnabled(true);
				txtNome.setEditable(true);
				txtBairro.setEditable(true);
				txtCelular.setEditable(true);
				txtCidade.setEditable(true);
				txtEmail.setEditable(true);
				txtEstado.setEditable(true);
				btnNovo.setEnabled(false);
				btnFoto.setEnabled(true);
				jcbEstadoEndereco.setEnabled(true);
				pnlEscolhaSexo.setEnabled(true);
				jrdSexoFeminino.setEnabled(true);
				jrdSexoMasculino.setEnabled(true);
			}

			if (evento.getSource() == btnSalvar) {
				int numEditado = tabelaRegistros.getSelectedRow();

				// JOptionPane.showMessageDialog(null, caminhoImagem);
				// habilitar e desabilitar
				btnSalvar.setEnabled(false);
				btnEditar.setEnabled(true);
				btnCancelar.setEnabled(false);
				btnExcluir.setEnabled(true);
				btnSalvar.setEnabled(true);
				btnCancelar.setEnabled(false);
				btnNovo.setEnabled(false);
				jcbEstadoEndereco.setEnabled(false);
				btnFoto.setEnabled(false);
				pnlEscolhaSexo.setEnabled(false);
				jrdSexoFeminino.setEnabled(false);
				jrdSexoMasculino.setEnabled(false);
				
				txtNome.setEditable(false);
				txtBairro.setEditable(false);
				txtCelular.setEditable(false);
				txtCidade.setEditable(false);
				txtEmail.setEditable(false);
				txtEstado.setEditable(false);

				// ação do botão salvar
				// crio os objetos
				DonoAgenda Agendadono = new DonoAgenda();
				Contato contatoDono = new Contato();
				Endereco enderecoDono = new Endereco();

				Agendadono.setNome_dono_agenda(txtNome.getText());

				// verificar se editou ou mudou a foto e definir
				// teste se caminho for null ou se caminho tiver preenchido
				// se alterou, lhe dar como parâmetro o caminho
				// senão lhe devolte o arquivo binário
				if (caminhoImagem == null) {
					//Não quero mudar foto
					DonoAgenda DonoAgenda2[] = DonoAgendaDAO.selecDonoAgenda();
					
					if (ModeloAgenda.getRowCount() == 0) {

						valorInicialId = 1;
					} else {
						// recupera o id
						row = tabelaRegistros.getSelectedRow();
						col = 0;
						valorInicialId = Integer
								.parseInt((String) tabelaRegistros.getValueAt(
										row, col));
					}
					int objetoDonoAgenda =0;
					// encontrando o objeto
					for (int i = 0; i < DonoAgenda2.length; i++) {
						if(DonoAgenda2[i].getId_dono_agenda() == valorInicialId){
							 objetoDonoAgenda = i;
						}
					}
					Agendadono.setFoto_dono_agenda(DonoAgenda2[objetoDonoAgenda].getFoto_dono_agenda());
					
				} else {
					
					//quero color novafoto"
					try {
			 			Agendadono.setFoto_dono_agenda(DonoAgendaDAO
								.TransformandoEmBinario(caminhoImagem));

					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				Agendadono.setSexo_dono_agenda(jrdEscolhaSexo);
				
				// pegando estado da combobox
				String valoridEstado = String.valueOf(jcbEstadoEndereco
						.getSelectedIndex());

				contatoDono.setCelular_contato(txtCelular.getText());
				contatoDono.setEmail_contato(txtEmail.getText());
				enderecoDono.setBairro_endereco(txtBairro.getText());
				enderecoDono.setEstado_endereco(valoridEstado);
				enderecoDono.setCidade_endereco(txtCidade.getText());

				// É NECESSÁRIO ATUALIZAR
				// adiciona
				// aqui está preenchendo a tabela

				// aqui está inserindo no banco de dados
				if (isControleEditar == true) {

					row = tabelaRegistros.getSelectedRow();
					col = 0;
					valorInicialId = Integer.parseInt((String) tabelaRegistros
							.getValueAt(row, 0));

					DonoAgendaDAO.alterarDonoAgenda(Agendadono, valorInicialId);
					ContatoDAO.alterarContato(contatoDono, valorInicialId);
					EnderecoDAO.alterarEndereco(enderecoDono, valorInicialId);

					// atualizando tabela
					atualizaTabela();

					tabelaRegistros.setRowSelectionInterval(numEditado,
							numEditado);

				} else {

					// comando salvar e ações
					// fundamental
					if (ModeloAgenda.getRowCount() == 0) {

						valorInicialId = 1;// o incremento no banco de dados
											// começa com 1
					} else {
						// recupera o id
						row = tabelaRegistros.getSelectedRow();
						col = 0;
						valorInicialId = Integer
								.parseInt((String) tabelaRegistros.getValueAt(
										row, col));
					}

					DonoAgendaDAO.inserirDonoAgenda(Agendadono);
					valorInicialId = DonoAgendaDAO.getIdUltimoDono();

					contatoDono.setId_fk_contato_dono_agenda(valorInicialId);
					ContatoDAO.inserirContato(contatoDono);

					enderecoDono.setId_fk_endereco_dono_agenda(valorInicialId);
					EnderecoDAO.inserirEndereco(enderecoDono);

					atualizaTabela();

					tabelaRegistros.setRowSelectionInterval(
							ModeloAgenda.getRowCount() - 1,
							ModeloAgenda.getRowCount() - 1);
				}

				// defino caracteristicas
				btnSalvar.setEnabled(false);
				btnCancelar.setEnabled(false);
				btnNovo.setEnabled(true);
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				

				txtNome.setEditable(false);
				txtBairro.setEditable(false);
				txtCelular.setEditable(false);
				txtCidade.setEditable(false);
				txtEmail.setEditable(false);
				txtEstado.setEditable(false);

			}

			if (evento.getSource() == btnCancelar) {

				// habilitar e desabilitar
				btnSalvar.setEnabled(false);
				btnEditar.setEnabled(true);
				btnCancelar.setEnabled(false);
				btnExcluir.setEnabled(true);
				btnSalvar.setEnabled(false);
				btnCancelar.setEnabled(false);
				btnNovo.setEnabled(true);
				btnFoto.setEnabled(false);
				jcbEstadoEndereco.setEnabled(false);

				txtNome.setEditable(false);
				txtBairro.setEditable(false);
				txtCelular.setEditable(false);
				txtCidade.setEditable(false);
				txtEmail.setEditable(false);
				txtEstado.setEditable(false);
				
				
				jrdSexoFeminino.setEnabled(false);
				jrdSexoMasculino.setEnabled(false);

				if (ModeloAgenda.getRowCount() >= 1) {
					tabelaRegistros.setRowSelectionInterval(0, 0);
				} else {
					txtNome.setText("");
					txtBairro.setText("");
					txtCelular.setText("");
					txtCidade.setText("");
					txtEmail.setText("");
					txtEstado.setText("");
				}

				atualizaTabela();

				// inicializar depois
				// Aqui vou inicializar a tabela com os registros para os campos
				if (tabelaRegistros.getRowCount() > 0) {
					tabelaRegistros.setRowSelectionInterval(0, 0);

					atualizaCampo();
				}

				if (ModeloAgenda.getRowCount() >= 1) {
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
				} else {
					btnEditar.setEnabled(false);
					btnExcluir.setEnabled(false);
				}

			}

			if (evento.getSource() == btnExcluir) {

				// valida o botão
				if (ModeloAgenda.getRowCount() > 0) {

					int confirma = JOptionPane.showConfirmDialog(null,
							"Tem certeza que Excluir?", "Escolha a opção",
							JOptionPane.YES_NO_OPTION);
					if (confirma == 0) {
						// "Sim"

						// 1 recupera o id
						row = tabelaRegistros.getSelectedRow();
						col = 0;

						if (tabelaRegistros.isRowSelected(tabelaRegistros
								.getSelectedRow())) {
							valorInicialId = Integer
									.parseInt((String) tabelaRegistros

									.getValueAt(row, col));
							// vai excluir todos os registro ligados a ele
							DonoAgendaDAO.excluirDonoAgenda(valorInicialId);

						} else {
							JOptionPane.showMessageDialog(null,
									"Selecione um item, para excluir!");
						}

						atualizaTabela();

						if (ModeloAgenda.getRowCount() >= 1) {
							btnEditar.setEnabled(true);
							btnExcluir.setEnabled(true);
						} else {
							btnEditar.setEnabled(false);
							btnExcluir.setEnabled(false);
						}

					}

					if (DonoAgendaDAO.selecDonoAgenda().length >= 1) {
						tabelaRegistros.setRowSelectionInterval(0, 0);
					}

				}// fim se verdadeitoe
				else {
					JOptionPane.showMessageDialog(null,
							"Não há registro! Adicione um item");
				}

			}

			if (evento.getSource() == btnAtualizar) {
				// atualizando os campos e tabela

				if (DonoAgendaDAO.selecDonoAgenda().length >= 1) {

					atualizaTabela();

					tabelaRegistros.setRowSelectionInterval(0, 0);
					if (ModeloAgenda.getRowCount() >= 1) {
						btnEditar.setEnabled(true);
						btnExcluir.setEnabled(true);
						btnAtualizar.requestFocus();
					} else {
						btnEditar.setEnabled(false);
						btnExcluir.setEnabled(false);
					}
				}

			}

			if (evento.getSource() == btnNovo) {
				isControleEditar = false;
				// habilitar e desabilitar
				btnSalvar.setEnabled(true);
				btnEditar.setEnabled(false);
				btnCancelar.setEnabled(true);
				btnExcluir.setEnabled(false);
				btnSalvar.setEnabled(true);
				btnCancelar.setEnabled(true);
				btnNovo.setEnabled(false);
				btnFoto.setEnabled(true);
				jcbEstadoEndereco.setEnabled(true);
				
				pnlEscolhaSexo.setEnabled(true);
				jrdSexoFeminino.setEnabled(true);
				jrdSexoMasculino.setEnabled(true);
				// aqui vai pegar um foto na web, e definir como sem foto
				URL where = null;
				try {
					where = new URL(
							"http://cdn.123i.com.br/img/sem-foto-vertical.jpg");
					// aqui redimensionar a imagem
					ImageIcon imagem = new ImageIcon(where);
					Image img = imagem.getImage();
					Image novaimg = img.getScaledInstance(140, 135,
							java.awt.Image.SCALE_SMOOTH);
					ImageIcon icon = new ImageIcon(novaimg);
					lblFoto.setIcon(icon);
				} catch (MalformedURLException e) {
					//e.printStackTrace();
					lblFoto.setText("Sem Imagem");
				}

				txtNome.setEditable(true);
				txtBairro.setEditable(true);
				txtCelular.setEditable(true);
				txtCidade.setEditable(true);
				txtEmail.setEditable(true);
				txtEstado.setEditable(true);

				txtNome.setText("");
				txtBairro.setText("");
				txtCelular.setText("");
				txtCidade.setText("");
				txtEmail.setText("");
				txtEstado.setText("");

				txtNome.requestFocus();

			}

			if (evento.getSource() == btnPesquisar) {
				// capturar do txtPesquisar
				String pesquisa = txtPesquisar.getText();
				if (pesquisa.equalsIgnoreCase("")) {
					JOptionPane.showMessageDialog(null,
							"Digite algo, para pesquisar");
				} else {
					// está cheio

					// só vai pesquisar se o numero de linhas ou registro for
					// maior que igual a 1
					if (DonoAgendaDAO.selecDonoAgenda().length >= 1) {

						DonoAgenda pesquisaDonoAgenda[] = new DonoAgenda[DonoAgendaDAO
								.pesquisaSelecDonoAgenda(pesquisa).length];
						pesquisaDonoAgenda = DonoAgendaDAO
								.pesquisaSelecDonoAgenda(pesquisa);

						// atualizando os campos e tabela

						// esvazia a tabela
						if (ModeloAgenda.getRowCount() != 0) {
							for (int i = 0; i < ModeloAgenda.getRowCount(); i++) {
								ModeloAgenda.removeRow(i);
							}

						}

						// adiciona
						// aqui está preenchendo a tabel
						ModeloAgenda = new DefaultTableModel(colunas, 0);

						for (int i = 0; i < pesquisaDonoAgenda.length; i++) {

							linha[0] = String.valueOf(pesquisaDonoAgenda[i]
									.getId_dono_agenda());
							linha[1] = String.valueOf(pesquisaDonoAgenda[i]
									.getNome_dono_agenda());
							ModeloAgenda.insertRow(i, linha);

						}

						tabelaRegistros.setModel(ModeloAgenda);

						if (pesquisaDonoAgenda.length == 0) {
							JOptionPane
									.showMessageDialog(null,
											"Nenhum registro encontrado\n Aperte no botão ATUALIZAR ");
							txtPesquisar.setText("");

						} else {
							tabelaRegistros.setRowSelectionInterval(0, 0);
						}

						// habilita atualizar
						btnAtualizar.setEnabled(true);
						btnEditar.setEnabled(true);
						btnExcluir.setEnabled(true);
					}

				}

			}

			if (evento.getSource() == btnFoto) {
				JFileChooser chooser = new JFileChooser(".");
				// showSaveDialog retorna um inteiro , e ele ira determinar que
				// o chooser será para salvar.
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"JPG Images", "jpg");
				chooser.setFileFilter(filter);
				chooser.setFileFilter(filter);
				int retorno = chooser.showOpenDialog(null);

				if (retorno == JFileChooser.APPROVE_OPTION) {
					// o getSelectedFile pega o arquivo e o getAbsolutePath
					// retorna umastring contendo o endereço.
					caminhoImagem = chooser.getSelectedFile().getAbsolutePath();
					// aqui redimensionar a imagem
					ImageIcon imagem = new ImageIcon(caminhoImagem);
					Image img = imagem.getImage();
					Image novaimg = img.getScaledInstance(140, 135,
							java.awt.Image.SCALE_SMOOTH);
					ImageIcon icon = new ImageIcon(novaimg);
					lblFoto.setIcon(icon);
				}

			}

			if (evento.getSource() == btnCtrPrimeiro) {
				// 0
				localLinhaFoco = tabelaRegistros.getSelectedRow();

				if (localLinhaFoco == 0) {
					btnCtrPrimeiro.setEnabled(false);
				} else {
					tabelaRegistros.setRowSelectionInterval(0, 0);
				}
			}

			if (evento.getSource() == btnCtrAnterior) {
				// 1
				totalLinhas = tabelaRegistros.getRowCount();
				// 0
				localLinhaFoco = tabelaRegistros.getSelectedRow();

				if (localLinhaFoco != 0) {

					tabelaRegistros.setRowSelectionInterval(localLinhaFoco - 1,
							localLinhaFoco - 1);

				} else {
					btnCtrAnterior.setEnabled(false);
				}

			}
			if (evento.getSource() == btnCtrProximo) {
				// 1
				totalLinhas = tabelaRegistros.getRowCount();
				// 0
				localLinhaFoco = tabelaRegistros.getSelectedRow();

				if (localLinhaFoco != totalLinhas - 1) {

					tabelaRegistros.setRowSelectionInterval(localLinhaFoco + 1,
							localLinhaFoco + 1);

				} else {
					btnCtrProximo.setEnabled(false);

				}
			}
			if (evento.getSource() == btnCtrUltimo) {
				// 1
				totalLinhas = tabelaRegistros.getRowCount();
				// 0
				localLinhaFoco = tabelaRegistros.getSelectedRow();

				if (localLinhaFoco == totalLinhas) {
					btnCtrUltimo.setEnabled(false);
				} else {
					tabelaRegistros.setRowSelectionInterval(totalLinhas - 1,
							totalLinhas - 1);
				}

			}

		}
	}

	
	// esculta a JRadioButton para executar a ação da mesma
		public class RadioButaoOuvinte implements ActionListener {

			// Trata evento da JRadioButton escolha de sexo, no caso estado
			public void actionPerformed(ActionEvent evento) {

				if(jrdSexoFeminino.isSelected()){
					jrdEscolhaSexo = 0;
				}
				
				if(jrdSexoMasculino.isSelected()){
					jrdEscolhaSexo = 1;
				}
				
			}
		}
	
	
	// esculta a JComboBox para executar a ação da mesma
	public class ComboBoxOuvinte implements ActionListener {

		// Trata evento da combobox, no caso estado
		public void actionPerformed(ActionEvent evento) {

			Estado estado[] = new Estado[EstadoDAO.selecEstadoEndereco().length];
			estado = EstadoDAO.selecEstadoEndereco();

			if (evento.getSource() == jcbEstadoEndereco) {
				int idSelecionadoComboBox = jcbEstadoEndereco
						.getSelectedIndex();
				lblEstadoNome.setText(estado[idSelecionadoComboBox]
						.getNome_estado());
			}

		}
	}
	
	
	// esculta a tabela para executar a ação da mesma
	public class TabelaOuvinte implements ListSelectionListener {

		// Trata evento das linhas e colunas da tabela
		@SuppressWarnings("unchecked")
		public void valueChanged(ListSelectionEvent evento) {

			// definindo largura do coluna "ID"
			tabelaRegistros.getColumnModel().getColumn(0).setMinWidth(10);
			tabelaRegistros.getColumnModel().getColumn(0).setMaxWidth(20);

			row = tabelaRegistros.getSelectedRow();
			col = 0;
			if (row >= 0) {

				atualizaCampo();

				atualizaComboBoxEstado();

				// habilito os botões
				btnPesquisar.setEnabled(true);
				btnAtualizar.setEnabled(true);
			} else {
				btnPesquisar.setEnabled(false);
				btnAtualizar.setEnabled(false);
				btnEditar.setEnabled(false);
				btnExcluir.setEnabled(false);
				btnFoto.setEnabled(false);

				txtBairro.setText("");
				txtCelular.setText("");
				txtCidade.setText("");
				txtEmail.setText("");
				txtEstado.setText("");
				txtNome.setText("");
			}

			// habilita e desabilita botões de controle
			// botão próximo
			if (tabelaRegistros.getRowCount() != tabelaRegistros
					.getSelectedRow()) {
				btnCtrProximo.setEnabled(true);
			}

			// botão anterior
			if (tabelaRegistros.getSelectedRow() != 0) {
				btnCtrAnterior.setEnabled(true);
			}

			// botão primeiro
			if (tabelaRegistros.getSelectedRow() == 0) {
				btnCtrPrimeiro.setEnabled(false);
				btnCtrAnterior.setEnabled(false);
			} else {
				btnCtrPrimeiro.setEnabled(true);
			}

			// botão último
			if (tabelaRegistros.getSelectedRow() == tabelaRegistros
					.getRowCount() - 1) {
				btnCtrUltimo.setEnabled(false);
				btnCtrProximo.setEnabled(false);
			} else {
				btnCtrUltimo.setEnabled(true);
			}

			// todos os botões senão tiver nenhum registro
			if (tabelaRegistros.getRowCount() == 0) {
				btnCtrAnterior.setEnabled(false);
				btnCtrProximo.setEnabled(false);
				btnCtrUltimo.setEnabled(false);
				btnCtrPrimeiro.setEnabled(false);

				// aqui vai pegar um foto na web, e definir como sem foto
				URL where = null;
				try {
					where = new URL(
							"http://cdn.123i.com.br/img/sem-foto-vertical.jpg");
					// aqui redimensionar a imagem
					ImageIcon imagem = new ImageIcon(where);
					Image img = imagem.getImage();
					Image novaimg = img.getScaledInstance(140, 135,
							java.awt.Image.SCALE_SMOOTH);
					ImageIcon icon = new ImageIcon(novaimg);
					lblFoto.setIcon(icon);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void atualizaComboBoxEstado() {
		// nesta ordem
		// inicializando a combobox estado
		Estado estado[] = new Estado[EstadoDAO.selecEstadoEndereco().length];
		estado = EstadoDAO.selecEstadoEndereco();

		Endereco endereco[] = new Endereco[EnderecoDAO.selecEnderecoDono().length];
		endereco = EnderecoDAO.selecEnderecoDono();
		int idEstado = 0;

		// verifica de tem algum registro
		if (tabelaRegistros.getRowCount() > 0) {

			row = tabelaRegistros.getSelectedRow();
			col = 0;
			valorInicialId = Integer.parseInt((String) tabelaRegistros
					.getValueAt(row, 0));

			// primeiro tenho q achar o endereço e recuperar seu estado
			idEstado = 0;
			for (int i = 0; i < endereco.length; i++) {
				if (endereco[i].getId_fk_endereco_dono_agenda() == valorInicialId) {
					idEstado = i;// peguei a posição do objeto endereço
									// correspondente
				}
			}

		}
		// pegar o seu estado e comparar e recuperar o id estado
		int idEstadoNome = 0;
		for (int i = 0; i < estado.length; i++) {
			jcbEstadoEndereco.addItem(estado[i].getSigla_estado());

			if (tabelaRegistros.getRowCount() > 0) {
				if (Integer.parseInt(endereco[idEstado].getEstado_endereco()) == estado[i]
						.getId_estado()) {
					idEstadoNome = i;// peguei a posição do objeto estado
										// correspondente
				}
			}

		}

		// verifica de tem algum registro
		if (tabelaRegistros.getRowCount() > 0) {
			jcbEstadoEndereco.setSelectedIndex(Integer
					.parseInt(endereco[idEstado].getEstado_endereco()));
			lblEstadoNome.setText(estado[idEstadoNome].getNome_estado());
		}
	}

	// atualizando os campos
	public void atualizaCampo() {
		valorInicialId = Integer.parseInt((String) tabelaRegistros.getValueAt(
				row, col));

		// Inicializei Dono Agenda
		donoAgenda = new DonoAgenda[DonoAgendaDAO.selecDonoAgenda().length];
		donoAgenda = DonoAgendaDAO.selecDonoAgenda();
		// por padrão 1 = masculino, 0 = feminino
		for (int i = 0; i < donoAgenda.length; i++) {
			if (donoAgenda[i].getId_dono_agenda() == valorInicialId) {
				txtNome.setText(donoAgenda[i].getNome_dono_agenda());

				// aqui redimensionar a imagem
				ImageIcon imagem;
				try {
					imagem = new ImageIcon(
							DonoAgendaDAO.TransformaEmImagem(donoAgenda[i]
									.getFoto_dono_agenda()));
					Image img = imagem.getImage();
					Image novaimg = img.getScaledInstance(140, 135,
							java.awt.Image.SCALE_SMOOTH);
					ImageIcon icon = new ImageIcon(novaimg);
					lblFoto.setIcon(icon);
				} catch (IOException e) {
					e.printStackTrace();
				}

				if(donoAgenda[i].getSexo_dono_agenda() == 0){
					jrdSexoFeminino.setSelected(true);
					jrdSexoMasculino.setSelected(false);
				}
				
				if(donoAgenda[i].getSexo_dono_agenda() == 1){
					jrdSexoMasculino.setSelected(true);
					jrdSexoFeminino.setSelected(false);
				}
			
				break;
			}
		}

		// Inicializei Contatos
		contato = new Contato[ContatoDAO.selecContato().length];
		contato = ContatoDAO.selecContato();
		for (int i = 0; i < contato.length; i++) {
			if (contato[i].getId_contato() == valorInicialId) {
				txtEmail.setText(contato[i].getEmail_contato());
				txtCelular.setText(contato[i].getCelular_contato());
				
				
				break;
			}
		}

		// Inicializei endereço
		endereco = new Endereco[EnderecoDAO.selecEnderecoDono().length];
		endereco = EnderecoDAO.selecEnderecoDono();

		for (int i = 0; i < endereco.length; i++) {
			if (endereco[i].getId_endereco() == valorInicialId) {
				txtBairro.setText(endereco[i].getBairro_endereco());
				txtCidade.setText(endereco[i].getCidade_endereco());
				txtEstado.setText(endereco[i].getEstado_endereco());
				break;
			}
		}

	}

	// atualizando a tabela
	public void atualizaTabela() {
		// atualiza campos e tabela
		// atualizando os campos e tabela

		// esvazia a tabela
		if (ModeloAgenda.getRowCount() != 0) {
			for (int i = 0; i < ModeloAgenda.getRowCount(); i++) {
				ModeloAgenda.removeRow(i);
			}

		}

		// adiciona
		// aqui está preenchendo a tabela
		idDono = DonoAgendaDAO.selecionarIdDonoAgenda();
		nomeDono = DonoAgendaDAO.selecionarNomeDonoAgenda();
		colunas = new String[] { "ID", "Nome","excluir" };

		linha = new String[2];
		ModeloAgenda = new DefaultTableModel(colunas, 0);

		for (int i = 0; i < nomeDono.length; i++) {

			linha[0] = idDono[i];
			linha[1] = nomeDono[i];
			ModeloAgenda.insertRow(i, linha);

			// captura o número da linha editado, para retorna a
			// ela

			// so fará este teste quando estiver editando o item

		}

		tabelaRegistros.setModel(ModeloAgenda);

	}

	public static void main(String[] args) {
		JanelaPrincipal janelaPrincipal = new JanelaPrincipal();
		janelaPrincipal.setVisible(true);
		janelaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
