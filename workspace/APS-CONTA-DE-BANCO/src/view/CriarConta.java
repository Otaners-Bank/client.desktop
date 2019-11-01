package view;

import control.Cliente;
import control.Control;
import control.Email;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class CriarConta extends JFrame {

	// Instancias
	Control control = new Control();

	// Para armazenar a resolucao da maquina
	public static Toolkit toolkit = Toolkit.getDefaultToolkit();
	public static Dimension dimension = toolkit.getScreenSize();

	// Para armazenar o tipo de conta que será criada
	private static String TIPO_CONTA = "CORRENTE";

	// Timer utilizado para fazer as letras aparecerem devagar
	Timer timer;

	// Diz se existe uma MessageDialog aberta ou não
	boolean canEnter = true;

	// Componentes
	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtCPF;
	private JTextField txtEmail;
	public JRadioButton rdbtnCorrente;
	public JRadioButton rdbtnPoupanca;
	public JRadioButton rdbtnEspecial;
	private JFormattedTextField txtSenha;
	private JLabel lblCPF;
	private JButton btnPronto1;
	private JButton btnPronto2;
	private JButton btnPronto3;
	private JButton btnPronto4;
	private JLabel lblSenha;
	private JLabel lblNome;
	private JLabel lblEmail;
	private JLabel lblTipoConta;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CriarConta frame = new CriarConta();
					frame.setLocationRelativeTo(null); // Deixa a janela centralizada
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Construtor
	public CriarConta() {

		setSize((dimension.width / 2), (dimension.height - 50)); // Muda o tamanho da janela
		setResizable(false); // Bloqueia que o usuario modifique o tamanho da janela
		setAlwaysOnTop(false); // Obriga a janela ficar em primeiro plano sempre
		setTitle("Otaner Bank - Criar conta"); // Altera o titulo da janela
		setBounds(100, 100, (dimension.width / 2), (dimension.height - 50)); // Altera o tamanho e a posicao da janela
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblVoltar = new JLabel("voltar");
		lblVoltar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				Acesso.main(null);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
				lblVoltar.setCursor(cursor);
			}
		});
		lblVoltar.setBounds(10, 11, 36, 14);
		contentPane.add(lblVoltar);

		// Animacao das Letras -------------------------------------------
		timer = new Timer((50), Mostrar_CPF);
		timer.setRepeats(true);
		timer.start();
		// ---------------------------------------------------------------

		lblCPF = new JLabel("");
		lblCPF.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCPF.setHorizontalAlignment(SwingConstants.CENTER);
		lblCPF.setBounds(10, 47, 647, 20);
		contentPane.add(lblCPF);

		txtCPF = new JFormattedTextField(Mascara("###.###.###-##"));
		txtCPF.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == e.VK_ENTER) {
					if (canEnter) {
						Acao1();
						canEnter = false;
					} else {
						canEnter = true;
					}
				}
			}
		});
		txtCPF.setBounds(262, 78, 163, 30);
		txtCPF.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(txtCPF);
		txtCPF.setColumns(10);

		lblNome = new JLabel();
		lblNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNome.setBounds(10, 153, 647, 20);
		lblNome.setVisible(false);
		contentPane.add(lblNome);

		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == e.VK_ENTER) {
					canEnter = true;
					txtEmail.grabFocus();
				}
			}
		});
		txtNome.setHorizontalAlignment(SwingConstants.CENTER);
		txtNome.setBounds(217, 184, 250, 30);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		txtNome.setVisible(false);

		lblEmail = new JLabel();
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setBounds(10, 225, 647, 20);
		lblEmail.setVisible(false);
		contentPane.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == e.VK_ENTER) {

					if (canEnter) {
						Acao2();
						canEnter = false;
					} else {
						canEnter = true;
					}
				}
			}
		});
		txtEmail.setHorizontalAlignment(SwingConstants.CENTER);
		txtEmail.setBounds(217, 256, 250, 30);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		txtEmail.setVisible(false);

		lblTipoConta = new JLabel();
		lblTipoConta.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTipoConta.setHorizontalAlignment(SwingConstants.CENTER);
		lblTipoConta.setBounds(10, 335, 647, 20);
		lblTipoConta.setVisible(false);
		contentPane.add(lblTipoConta);

		rdbtnCorrente = new JRadioButton("Conta Corrente");
		rdbtnCorrente.setSelected(true);
		rdbtnCorrente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnCorrente.isSelected()) {
					TIPO_CONTA = "CORRENTE";
					rdbtnPoupanca.setSelected(false);
					rdbtnEspecial.setSelected(false);
				} else {
					rdbtnCorrente.setSelected(false);
				}
			}
		});
		rdbtnCorrente.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnCorrente.setBackground(Color.WHITE);
		rdbtnCorrente.setForeground(Color.BLACK);
		rdbtnCorrente.setBounds(262, 362, 163, 23);
		rdbtnCorrente.setVisible(false);
		contentPane.add(rdbtnCorrente);

		rdbtnPoupanca = new JRadioButton("Conta Poupan\u00E7a");
		rdbtnPoupanca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnPoupanca.isSelected()) {
					TIPO_CONTA = "POUPANCA";
					rdbtnCorrente.setSelected(false);
					rdbtnEspecial.setSelected(false);
				} else {
					rdbtnPoupanca.setSelected(false);
				}
			}
		});
		rdbtnPoupanca.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnPoupanca.setBackground(Color.WHITE);
		rdbtnPoupanca.setForeground(Color.BLACK);
		rdbtnPoupanca.setBounds(262, 388, 163, 23);
		rdbtnPoupanca.setVisible(false);
		contentPane.add(rdbtnPoupanca);

		rdbtnEspecial = new JRadioButton("Conta Especial");
		rdbtnEspecial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnEspecial.isSelected()) {
					TIPO_CONTA = "ESPECIAL";
					rdbtnCorrente.setSelected(false);
					rdbtnPoupanca.setSelected(false);
				} else {
					rdbtnEspecial.setSelected(false);
				}
			}
		});
		rdbtnEspecial.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnEspecial.setBackground(Color.WHITE);
		rdbtnEspecial.setForeground(Color.BLACK);
		rdbtnEspecial.setBounds(262, 414, 163, 23);
		rdbtnEspecial.setVisible(false);
		contentPane.add(rdbtnEspecial);

		lblSenha = new JLabel();
		lblSenha.setHorizontalAlignment(SwingConstants.CENTER);
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSenha.setBounds(10, 484, 647, 20);
		lblSenha.setVisible(false);
		contentPane.add(lblSenha);

		txtSenha = new JFormattedTextField(Mascara("####"));
		txtSenha.setHorizontalAlignment(SwingConstants.CENTER);
		txtSenha.setFont(new Font("Tahoma", Font.PLAIN, 70));
		txtSenha.setBounds(170, 520, 360, 75);
		txtSenha.setVisible(false);
		contentPane.add(txtSenha);

		btnPronto4 = new JButton("PRONTO");
		btnPronto4.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Acao4();
			}

		});
		btnPronto4.setBounds(304, 627, 89, 23);
		btnPronto4.setVisible(false);
		contentPane.add(btnPronto4);

		btnPronto3 = new JButton("PRONTO");
		btnPronto3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Acao3();
			}

		});
		btnPronto3.setBounds(568, 440, 89, 23);
		btnPronto3.setVisible(false);
		contentPane.add(btnPronto3);

		btnPronto2 = new JButton("PRONTO");
		btnPronto2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (canEnter) {
					Acao2();
					canEnter = false;
				} else {
					Acao2();
					canEnter = true;
				}
			}

		});
		btnPronto2.setBounds(568, 299, 89, 25);
		btnPronto2.setVisible(false);
		contentPane.add(btnPronto2);

		btnPronto1 = new JButton("PRONTO");
		btnPronto1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (canEnter) {
					Acao1();
					canEnter = false;
				} else {
					Acao1();
					canEnter = true;
				}
			}
		});
		btnPronto1.setBounds(568, 117, 89, 25);
		btnPronto1.setVisible(false);
		contentPane.add(btnPronto1);

		EsconderCampos();

	}

	// Esconde todos os campos no inicio desta tela
	private void EsconderCampos() {
		// Modulo 1
		txtCPF.setVisible(false);
		btnPronto1.setVisible(false);
		// Modulo 2
		lblNome.setVisible(false);
		txtNome.setVisible(false);
		lblEmail.setVisible(false);
		txtEmail.setVisible(false);
		btnPronto2.setVisible(false);
		// Modulo 3
		lblTipoConta.setVisible(false);
		rdbtnCorrente.setVisible(false);
		rdbtnPoupanca.setVisible(false);
		rdbtnEspecial.setVisible(false);
		btnPronto3.setVisible(false);
		// Modulo 4
		lblSenha.setVisible(false);
		txtSenha.setVisible(false);
		btnPronto4.setVisible(false);
	}

	// Metodo que define a ação do botao1
	private void Acao1() {
		try {

			if (txtCPF.getText().toString().replace(".", "").replace("-", "").equals("")
					|| txtCPF.getText().equals("000.000.000 00") || txtCPF.getText().equals("111.111.111-11")
					|| txtCPF.getText().equals("222.222.222-22") || txtCPF.getText().equals("333.333.333-33")
					|| txtCPF.getText().equals("444.444.444-44") || txtCPF.getText().equals("555.555.555-55")
					|| txtCPF.getText().equals("666.666.666-66") || txtCPF.getText().equals("777.777.777-77")
					|| txtCPF.getText().equals("888.888.888-88") || txtCPF.getText().equals("999.999.999-99")) {

				txtCPF.setForeground(Color.RED);
				txtCPF.grabFocus();

				JOptionPane.showMessageDialog(null, "O CPF informado está incorreto !", "Ops",
						JOptionPane.ERROR_MESSAGE);

			} else {
				if (ValidacaoCPF(txtCPF.getText().toString().replace(".", "").replace("-", ""))) {

					if (control.BuscarCPF(txtCPF.getText())) {

						txtCPF.setForeground(Color.RED);
						txtCPF.grabFocus();

						JOptionPane.showMessageDialog(null, "O CPF informado já está em uso !", "Ops",
								JOptionPane.ERROR_MESSAGE);

					} else {

						AcaoCampos1();
						// Animacao das Letras -------------------------------------------
						timer = new Timer((50), Mostrar_Nome_Email);
						timer.setRepeats(true);
						timer.start();
						// ---------------------------------------------------------------

					}

				} else {

					txtCPF.setForeground(Color.RED);
					txtCPF.grabFocus();

					JOptionPane.showMessageDialog(null, "O CPF informado é inválido !", "Ops",
							JOptionPane.ERROR_MESSAGE);

				}

			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "ERRO",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// Esconde/Mostra os campos necessario após a primeira Acao
	private void AcaoCampos1() {
		btnPronto1.setEnabled(false);
		txtCPF.setForeground(Color.BLACK);
		txtCPF.setEnabled(false);
	}

	// Metodo que define a ação do botao2
	private void Acao2() {
		try {

			if (txtNome.getText().equals("") || txtEmail.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Prencha todos os campos porfavor xD", "Ops",
						JOptionPane.ERROR_MESSAGE);

			} else if (!(ValidacaoEmail(txtEmail.getText()))) {
				JOptionPane.showMessageDialog(null, "Este email não é válido", "Ops", JOptionPane.ERROR_MESSAGE);

			} else {

				AcaoCampos2();
				// Animacao das Letras -------------------------------------------
				Timer timer = new Timer((50), Mostrar_TipoConta);
				timer.setRepeats(true);
				timer.start();
				// ---------------------------------------------------------------

			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "ERRO",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// Esconde/Mostra os campos necessario após a segunda Acao
	private void AcaoCampos2() {
		txtNome.setEnabled(false);
		txtEmail.setEnabled(false);
		btnPronto2.setEnabled(false);
		lblTipoConta.setVisible(true);
	}

	// Metodo que define a ação do botao3
	private void Acao3() {
		try {
			if (!(rdbtnCorrente.isSelected()) && !(rdbtnPoupanca.isSelected()) && !(rdbtnEspecial.isSelected())) {
				JOptionPane.showMessageDialog(null, "Ative pelo menos uma das opções porfavor xD", "Ops",
						JOptionPane.WARNING_MESSAGE);
			} else {
				AcaoCampos3();
				// Animacao das Letras -------------------------------------------
				Timer timer = new Timer((50), Mostrar_Senha);
				timer.setRepeats(true);
				timer.start();
				// ---------------------------------------------------------------
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "ERRO",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// Esconde/Mostra os campos necessario após a terceira Acao
	private void AcaoCampos3() {
		rdbtnCorrente.setEnabled(false);
		rdbtnPoupanca.setEnabled(false);
		rdbtnEspecial.setEnabled(false);
		btnPronto3.setEnabled(false);
		lblSenha.setVisible(true);
	}

	// Metodo que define a ação do botao4
	private void Acao4() {
		try {
			if (!txtSenha.getText().equals("") && txtSenha.getText().length() == 4) {

				AcaoCampos4();

				Cliente c = new Cliente();

				int numeroConta = control.numeroContaDisponivel() + 1;

				if (numeroConta <= 9)
					c.conta = "000" + numeroConta;
				else if (numeroConta <= 99)
					c.conta = "00" + numeroConta;
				else if (numeroConta <= 999)
					c.conta = "0" + numeroConta;
				else
					c.conta = String.valueOf(numeroConta);

				if (TIPO_CONTA.equals("CORRENTE")) {
					c.conta += "-1";
				} else if (TIPO_CONTA.equals("POUPANCA")) {
					c.conta += "-2";
				} else if (TIPO_CONTA.equals("ESPECIAL")) {
					c.conta += "-3";
				}

				c.CPF = txtCPF.getText();
				c.setNome(txtNome.getText());
				c.setEmail(txtEmail.getText());
				c.setSenha(txtSenha.getText());

				c.saldo = "R$ 00,00";

				control.adicionarConta(c);

				String conta = "";

				if (c.getConta().endsWith("1"))
					conta = "corrente";
				else if (c.getConta().endsWith("2"))
					conta = "poupança";
				else if (c.getConta().endsWith("3"))
					conta = "especial";

				Email email = new Email();
				email.EnviarMensagem(c.getEmail(), "Sua nova conta - Otaner's Bank",
						"OLÁ " + c.getNome() + ", e Bem Vindo ao Otaner's Bank !!! \nPara acessar sua nova conta "
								+ conta + " utilize o numero \"" + c.getConta() + "\" e a senha cadastrada xD");

				c = null;
				conta = null;

				JOptionPane.showMessageDialog(null,
						"Sua conta foi criada e para acessa-la, \nverifique seu e-mail para ver o número de sua conta xD",
						"Conta Criada com SUCESSO !!!", JOptionPane.INFORMATION_MESSAGE);

				Acesso.main(null);
				dispose();

			} else {
				JOptionPane.showMessageDialog(null, "Preencha o campo senha corretamente!", "Ops",
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "ERRO",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// Esconde/Mostra os campos necessario após a quarta Acao
	private void AcaoCampos4() {
		txtSenha.setEnabled(false);
		btnPronto4.setVisible(false);
	}

	// Metodo para criar mascaras nos campos
	public static MaskFormatter Mascara(String Mascara) {

		MaskFormatter F_Mascara = new MaskFormatter();
		try {
			F_Mascara.setMask(Mascara); // Atribui a mascara
			F_Mascara.setPlaceholderCharacter(' '); // Caracter para preencimento
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "ERRO",
					JOptionPane.ERROR_MESSAGE);
		}
		return F_Mascara;
	}

	// Pequena validação de e-mail
	private boolean ValidacaoEmail(String email) {
		boolean emailValido = false;
		if (email != null && email.length() > 0) {
			String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			if (matcher.matches()) {
				emailValido = true;
			}
		}
		return emailValido;
	}

	// Pequena validação de CPF
	private static boolean ValidacaoCPF(String CPF) {

		try {
			CPF += CPF.replace(".", "").replace("-", "");
		} catch (Exception e) {

		}

		char dig10, dig11;
		int sm, i, r, num, peso;

		// "try" - protege o codigo para eventuais erros de conversao de tipo (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48); // converte no respectivo caractere numerico

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			// Verifica se os digitos calculados conferem com os digitos informados.
			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
				return true;
			else
				return false;
		} catch (InputMismatchException erro) {
			return false;
		}
	}

	// Faz a animação das palavras antes de aparecer o CPF
	ActionListener Mostrar_CPF = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			try {

				String conteudoAtual = lblCPF.getText();
				String objetivoFinal = "Eai ser humaninho ! Para começarmos a criação de sua conta, porfavor me informe seu CPF:";
				String letra = "";

				if (!(conteudoAtual.equals(objetivoFinal))) {

					letra = "" + objetivoFinal.charAt(conteudoAtual.length());
					conteudoAtual += letra;

					if ((letra.equals("!")) || (letra.equals(","))) {
						timer.setDelay(500);
					} else {
						Random random = new Random();
						int delay = random.nextInt((70 - 20) + 1) + 20; // Entre
																		// 70
																		// e
																		// 20
						timer.setDelay(delay);
					}

					lblCPF.setText(conteudoAtual);

				} else if (!txtCPF.isVisible()) {

					txtCPF.setVisible(true);
					txtCPF.grabFocus();
					timer.stop();

					// Animacao das Letras -------------------------------------------
					timer = new Timer((500), Mostrar_CPF);
					timer.setRepeats(false);
					timer.start();
					// ---------------------------------------------------------------

				} else {
					btnPronto1.setEnabled(true);
					btnPronto1.setVisible(true);
					timer.stop();
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "ERRO",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	};

	// Faz a animação das palavras antes de aparecer o Nome e Email

	// Faz a animação das palavras antes de aparecer o Nome e o Email
	ActionListener Mostrar_Nome_Email = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			try {

				// Habilitando os proximos campos
				lblNome.setVisible(true);

				String conteudoNome = lblNome.getText();
				String objetivoNome = "Certo, agora seu nome completo";

				String conteudoEmail = lblEmail.getText();
				String objetivoEmail = "Seu e-mail também porfavor ... \r\n"
						+ "ele será utilizado para podermos nos comunicar :p";

				String letra = "";

				if (!(conteudoNome.equals(objetivoNome))) {

					letra = "" + objetivoNome.charAt(conteudoNome.length());
					conteudoNome += letra;

					if ((letra.equals("!")) || (letra.equals(","))) {
						timer.setDelay(500);
					} else {
						Random random = new Random();
						int delay = random.nextInt((70 - 20) + 1) + 20; // Entre 70 e 20
						timer.setDelay(delay);
					}

					lblNome.setText(conteudoNome);

				} else if (!(conteudoEmail.equals(objetivoEmail))) {

					txtNome.setVisible(true);
					lblEmail.setVisible(true);

					if (!(conteudoEmail.equals(objetivoEmail))) {

						letra = "" + objetivoEmail.charAt(conteudoEmail.length());
						conteudoEmail += letra;

						if ((letra.equals("!")) || (letra.equals(","))) {
							timer.setDelay(500);
						} else {
							Random random = new Random();
							int delay = random.nextInt((70 - 20) + 1) + 20; // Entre 70 e 20
							timer.setDelay(delay);
						}
					}

					lblEmail.setText(conteudoEmail);

				} else if (!txtEmail.isVisible()) {

					txtEmail.setVisible(true);
					canEnter = true;
					txtNome.grabFocus();

					// Animacao das Letras -------------------------------------------
					timer = new Timer((500), Mostrar_Nome_Email);
					timer.setRepeats(false);
					timer.start();
					// ---------------------------------------------------------------

				} else {
					btnPronto2.setVisible(true);
					timer.stop();
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "ERRO",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	};

	// Faz a animação das palavras antes de aparecer o Tipo de conta
	ActionListener Mostrar_TipoConta = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			try {

				String conteudoAtual = lblTipoConta.getText();
				String objetivoFinal = "Beleza !!! agora escolha o tipo de conta que deseja criar:";
				String letra = "";

				if (!(conteudoAtual.equals(objetivoFinal))) {

					letra = "" + objetivoFinal.charAt(conteudoAtual.length());
					conteudoAtual += letra;

					Random random = new Random();
					int delay = random.nextInt((100 - 10) + 1) + 20; // Entre 100 e 20
					timer.setDelay(delay);

					lblTipoConta.setText(conteudoAtual);

				} else {
					// Habilitando os proximos campos
					btnPronto3.setVisible(true);
					rdbtnCorrente.setVisible(true);
					rdbtnPoupanca.setVisible(true);
					rdbtnEspecial.setVisible(true);
					timer.stop();
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "ERRO",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	};

	// Faz a animação das palavras antes de aparecer o CPF
	ActionListener Mostrar_Senha = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			try {

				String conteudoAtual = lblSenha.getText();
				String objetivoFinal = "POR FIM, precisamos definir uma senha de acesso \"de quatro\" digitos para sua conta:";
				String letra = "";

				if (!(conteudoAtual.equals(objetivoFinal))) {

					letra = "" + objetivoFinal.charAt(conteudoAtual.length());
					conteudoAtual += letra;

					Random random = new Random();
					int delay = random.nextInt((70 - 20) + 1) + 20; // Entre 70 e 20
					timer.setDelay(delay);

					lblSenha.setText(conteudoAtual);

				} else if (!txtSenha.isVisible()) {

					txtSenha.setVisible(true);
					btnPronto4.setVisible(true);
					txtSenha.grabFocus();
					timer.stop();

					// Animacao das Letras -------------------------------------------
					timer = new Timer((500), Mostrar_Senha);
					timer.setRepeats(false);
					timer.start();
					// ---------------------------------------------------------------

				} else {
					btnPronto4.setEnabled(true);
					btnPronto4.setVisible(true);
					timer.stop();
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "ERRO",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	};
}
