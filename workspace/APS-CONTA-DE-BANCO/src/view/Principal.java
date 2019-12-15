package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import com.mongodb.MongoException;

import control.Conta;
import control.ContaCorrente;
import control.ContaEspecial;
import control.ContaPoupanca;
import control.Control;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;

@SuppressWarnings("serial")
public class Principal extends JFrame {

	// DETERMINA O QUANTO A CONTA POUPANCA RENDE
	final double RENDIMENTO_ANUAL_POUPANCA = 3.5;
	final double RENDIMENTO_MENSAL_POUPANCA = 0.35;
	final double RENDIMENTO_DIARIO_POUPANCA = 0.035;

	// Armazena quanto a conta rendeu desde a ultima vez que o ele entrou
	static double qteRenda = 0;

	// Armazena quanto a conta ja rendeu ao todo
	static double qteRendaTotal = 0;

	// Instancias
	static Control control = new Control();
	static Conta CONTA = null;

	// Timer
	Timer timer;

	// Para mostrar o cursor do mouse diferente para clicar nas coisas
	Cursor cursor = new Cursor(Cursor.HAND_CURSOR);

	// Diz se está acontecendo uma animacao
	public boolean animacaoEmCurso = false;
	public String operacaoEscolhida = "";

	// Para acresentar valor ao visor pelos botoes
	private int valor = 0;

	// Numero da conta do Cliente atual
	private static String NUMERO_CONTA = "";
	private static String TIPO_CONTA = "";

	// Para armazenar a resolucao da maquina
	public static Toolkit toolkit = Toolkit.getDefaultToolkit();
	public static Dimension dimension = toolkit.getScreenSize();
	public static int x;
	public static int y;

	// Determina se o numPad está aparecendo ou não
	private boolean numPadVisivel = false;
	private int numPadPosicao = 0;

	// Componentes
	private JPanel contentPane;
	private JPanel numPad;
	private static JButton btnDeposito;
	private static JButton btnTranferencia;
	private static JButton btnSaque;
	private static JLabel lblBemVindo;
	private static JLabel lblRenda;
	private static JFormattedTextField txtVisor;
	private static JButton btn10;
	private static JButton btn100;
	private static JButton btn50;
	private static JButton btn500;
	private static JButton btnConfirmar;

	public static void main(String[] args, String numeroConta) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NUMERO_CONTA = numeroConta;
					Principal frame = new Principal();
					frame.setVisible(true);
					frame.addWindowListener(new WindowListener() {
						public void windowClosing(WindowEvent e) {
							if (TIPO_CONTA.equals("2")) {
								frame.dispose();
								control.AtualizarContaPoupanca(CONTA.getConta());
							}
						}

						public void windowClosed(WindowEvent e) {

						}

						public void windowOpened(WindowEvent e) {
						}

						public void windowIconified(WindowEvent e) {
						}

						public void windowDeiconified(WindowEvent e) {
						}

						public void windowActivated(WindowEvent e) {
						}

						public void windowDeactivated(WindowEvent e) {
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Construtor
	public Principal() {
		setBackground(Color.WHITE);
		setTitle("Otaner Bank - Home"); // Altera o titulo da janela

		x = dimension.width;
		y = dimension.height;

		timer = new Timer((1000), carregarConta);
		timer.setRepeats(true);
		timer.start();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 700); // Altera o tamanho e a posicao da janela
		setExtendedState(6); // Deixa a janela em tela cheia
		setVisible(true);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBounds(100, 100, MAXIMIZED_BOTH, MAXIMIZED_BOTH);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		lblBemVindo = new JLabel("Carregando");
		lblBemVindo.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblBemVindo.setHorizontalAlignment(SwingConstants.CENTER);
		lblBemVindo.setBounds(0, 0, dimension.width, 50);

		lblRenda = new JLabel();
		lblRenda.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRenda.setHorizontalAlignment(SwingConstants.CENTER);
		lblRenda.setBounds(0, 40, dimension.width, 30);
		lblRenda.setVisible(false);

		btnDeposito = new JButton();
		btnDeposito.setBackground(Color.WHITE);
		btnDeposito.setCursor(cursor);
		btnDeposito.setBounds(0, (y - y) + 200, 450, 90);
		btnDeposito.setFont(new Font("Felix Titling", Font.PLAIN, 25));
		btnDeposito.setText("DEPÓSITO");

		btnTranferencia = new JButton();
		btnTranferencia.setBackground(Color.WHITE);
		btnTranferencia.setCursor(cursor);
		btnTranferencia.setBounds(0, (y - y) + 350, 450, 90);
		btnTranferencia.setFont(new Font("Felix Titling", Font.PLAIN, 25));
		btnTranferencia.setText("TRANFERÊNCIA");

		btnSaque = new JButton();
		btnSaque.setBackground(Color.WHITE);
		btnSaque.setCursor(cursor);
		btnSaque.setBounds(0, (y - y) + 500, 450, 90);
		btnSaque.setFont(new Font("Felix Titling", Font.PLAIN, 25));
		btnSaque.setText("SAQUE");

		numPad = new JPanel();
		numPad.setBackground(Color.LIGHT_GRAY);
		numPad.setBounds(x, (y - y) + 200, 500, 400);
		numPad.setBorder(new EmptyBorder(5, 5, 5, 5));
		numPad.setLayout(null);

		btnSaque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!animacaoEmCurso && !numPadVisivel) {
					txtVisor.setText("0");
					btnDeposito.setEnabled(false);
					btnTranferencia.setEnabled(false);
					btnSaque.setText("CANCELAR SAQUE");
					operacaoEscolhida = "S";
					btnConfirmar.setText("CONFIRMAR SAQUE");
					AnimarNumPad();
					txtVisor.grabFocus();
				} else {
					if (operacaoEscolhida.equals("S")) {
						txtVisor.setText("0");
						btnDeposito.setEnabled(true);
						btnTranferencia.setEnabled(true);
						btnSaque.setText("SAQUE");
						operacaoEscolhida = "";
						AnimarNumPad();
						animacaoEmCurso = false;
						btnConfirmar.setText("CONFIRMAR");
					}
				}
			}
		});

		btnTranferencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!animacaoEmCurso && !numPadVisivel) {
					txtVisor.setText("0");
					btnDeposito.setEnabled(false);
					btnSaque.setEnabled(false);
					btnTranferencia.setText("CANCELAR TRANSFERENCIA");
					operacaoEscolhida = "T";
					AnimarNumPad();
					txtVisor.grabFocus();
				} else {
					if (operacaoEscolhida.equals("T")) {
						txtVisor.setText("0");
						btnSaque.setEnabled(true);
						btnDeposito.setEnabled(true);
						btnTranferencia.setText("TRANSFERENCIA");
						operacaoEscolhida = "";
						AnimarNumPad();
						animacaoEmCurso = false;
						btnConfirmar.setText("CONFIRMAR");
					}

				}

			}
		});

		btnDeposito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!animacaoEmCurso && !numPadVisivel) {
					txtVisor.setText("0");
					btnSaque.setEnabled(false);
					btnTranferencia.setEnabled(false);
					btnDeposito.setText("CANCELAR DEPÓSITO");
					operacaoEscolhida = "D";
					btnConfirmar.setText("CONFIRMAR DEPÓSITO");
					AnimarNumPad();
					txtVisor.grabFocus();
				} else {
					if (operacaoEscolhida.equals("D")) {
						txtVisor.setText("0");
						btnSaque.setEnabled(true);
						btnTranferencia.setEnabled(true);
						btnDeposito.setText("DEPÓSITO");
						operacaoEscolhida = "";
						AnimarNumPad();
						animacaoEmCurso = false;
						btnConfirmar.setText("CONFIRMAR");
					}
				}
			}
		});

		btnDeposito.setEnabled(false);
		btnSaque.setEnabled(false);
		btnTranferencia.setEnabled(false);

		setContentPane(contentPane);
		contentPane.add(lblBemVindo);
		contentPane.add(lblRenda);
		contentPane.add(btnSaque);
		contentPane.add(btnTranferencia);
		contentPane.add(btnDeposito);
		contentPane.add(numPad);

		txtVisor = new JFormattedTextField();
		txtVisor.setHorizontalAlignment(SwingConstants.CENTER);
		txtVisor.setBounds(0, 0, 500, 50);
		txtVisor.setFont(new Font("Tahoma", Font.PLAIN, 25));

		btn10 = new JButton("+ R$ 10,00");
		btn10.setBackground(Color.WHITE);
		btn10.setCursor(cursor);
		btn10.setBounds(80, 100, 150, 70);

		btn50 = new JButton("+ R$ 50,00");
		btn50.setBackground(Color.WHITE);
		btn50.setCursor(cursor);
		btn50.setBounds(270, 100, 150, 70);

		btn100 = new JButton("+ R$ 100,00");
		btn100.setBackground(Color.WHITE);
		btn100.setCursor(cursor);
		btn100.setBounds(80, 200, 150, 70);

		btn500 = new JButton("+ R$ 500,00");
		btn500.setBackground(Color.WHITE);
		btn500.setCursor(cursor);
		btn500.setBounds(270, 200, 150, 70);

		btnConfirmar = new JButton("CONFIRMAR");
		btnConfirmar.setBackground(Color.WHITE);
		btnConfirmar.setCursor(cursor);
		btnConfirmar.setBounds(150, 300, 200, 65);
		btnSaque.setFont(new Font("Felix Titling", Font.PLAIN, 25));

		btn10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					valor = valorVisor() + 10;
					txtVisor.setText(valor + "");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
				}
			}
		});

		btn100.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					valor = valorVisor() + 100;
					txtVisor.setText(valor + "");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
				}
			}
		});

		btn50.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					valor = valorVisor() + 50;
					txtVisor.setText(valor + "");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
				}
			}
		});

		btn500.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					valor = valorVisor() + 500;
					txtVisor.setText(valor + "");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
				}
			}
		});

		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					String visorString = txtVisor.getText();

					// Primeiro ele verifica se o tamanho do valor pode ser convertido para int
					if (txtVisor.getText().length() > 5) {
						JOptionPane.showMessageDialog(null,
								"heey, não é possível efetuar operações maiores que R$ 99.999,00 de uma só vez :/");
					}
					// Se for possivel converter para int
					else {

						// maximo int 999999999
						int visorInt = Integer.parseInt(visorString);

						if (visorInt > 0) {

							try {
								switch (operacaoEscolhida) {
								case "D":
									Depositar();
									break;
								case "T":
									String contaQueVaiReceberTranferencia = "";

									boolean podeProseeguir = false;

									while (!podeProseeguir) {
										contaQueVaiReceberTranferencia = JOptionPane.showInputDialog(
												"Digite o numero da conta que receberá a transferencia:");

										if (contaQueVaiReceberTranferencia == null) {
											// Significa que o usuario apertou o botao "cancelar"
											podeProseeguir = true;
										} else if (!contaQueVaiReceberTranferencia.equals("")) {
											// Significa que o usuario apertou o botao "Ok" e preencheu
											if (control.validarConta(contaQueVaiReceberTranferencia)) {
												Transferir(contaQueVaiReceberTranferencia);
												podeProseeguir = true;
											} else {
												JOptionPane.showMessageDialog(null, "Conta não encontrada");
											}
										} else {
											JOptionPane.showMessageDialog(null, "Preencha os dados corretamente");
										}

									}

									break;
								case "S":
									Sacar();
									break;

								default:
									break;
								}

								AnimarNumPad();

								btnDeposito.setText("DEPOSITO");
								btnDeposito.setEnabled(true);

								btnSaque.setText("SAQUE");
								btnSaque.setEnabled(true);

								btnTranferencia.setText("TRANFERÊNCIA");
								btnTranferencia.setEnabled(true);

							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
							}

						} else {
							JOptionPane.showMessageDialog(null,
									"Por favor insira um valor maior que zero no visor !!!\n");
						}

					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Por favor insira apenas números no visor !!!\n");
				}
			}
		});

		numPad.add(btn10);
		numPad.add(btn50);
		numPad.add(btn100);
		numPad.add(btn500);
		numPad.add(btnConfirmar);
		numPad.add(txtVisor);

	}

	// Utilizado para carregar os dados do usuario no incio
	ActionListener carregarConta = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			AtualizarPagina();
		}
	};

	// Busca os dados da conta do usuario
	private void getConta() {

		try {
			TIPO_CONTA = String.valueOf(NUMERO_CONTA.charAt(NUMERO_CONTA.length() - 1));
			CONTA = control.buscarConta(NUMERO_CONTA);
			if (TIPO_CONTA.equals("2")) {
				// ContaPoupanca contaPoupanca = (ContaPoupanca) CONTA;

				lblRenda.setVisible(true);
				lblRenda.setText("Heey, você recebeu + " + qteRenda + " desde quando entrou pela ultima vez xD "
						+ " -- Valor recebido até o momento: + " + qteRendaTotal);
			} else if (TIPO_CONTA.equals("3")) {

				ContaEspecial contaEspecial = (ContaEspecial) CONTA;

				lblRenda.setVisible(true);
				lblRenda.setText("Nome do Gerente Responsável: " + contaEspecial.getNomeGerenteResponsavel()
						+ " / E-mail para contato: " + contaEspecial.getEmailGerenteResponsavel());
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "ERRO",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	// Faz a animacao inicial, e mostra o saldo do usuario
	private void AtualizarPagina() {
		try {

			if (lblBemVindo.getText().equals("Carregando")) {
				lblBemVindo.setText("Carregando .");
			} else if (lblBemVindo.getText().equals("Carregando .")) {
				lblBemVindo.setText("Carregando . .");
			} else if (lblBemVindo.getText().equals("Carregando . .")) {
				lblBemVindo.setText("Carregando . . .");
			} else {

				TIPO_CONTA = String.valueOf(NUMERO_CONTA.charAt(NUMERO_CONTA.length() - 1));

				if (TIPO_CONTA.equals("2")) {
					CONTA = control.buscarConta(NUMERO_CONTA);
					ContaPoupanca contaPoupanca = (ContaPoupanca) CONTA;
					qteRenda = contaPoupanca.calculaRendimento(NUMERO_CONTA, RENDIMENTO_DIARIO_POUPANCA,
							RENDIMENTO_MENSAL_POUPANCA, RENDIMENTO_ANUAL_POUPANCA, false);
					qteRendaTotal = contaPoupanca.calculaRendimento(NUMERO_CONTA, RENDIMENTO_DIARIO_POUPANCA,
							RENDIMENTO_MENSAL_POUPANCA, RENDIMENTO_ANUAL_POUPANCA, true);
				}

				getConta();
				lblBemVindo.setText("Bem Vindo, " + CONTA.getNome() + " ! Seu saldo atual é: " + CONTA.getSaldo());

				btnDeposito.setEnabled(true);
				btnSaque.setEnabled(true);
				btnTranferencia.setEnabled(true);

				timer.stop();
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "ERRO",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// Inicia um timer para executar a animacao a cada 5 milisegundos
	public void AnimarNumPad() {
		animacaoEmCurso = true;
		timer = new Timer((5), AnimacaoNumPad);
		timer.setRepeats(true);
		timer.start();
	}

	// Faz a animacao do NumPad
	ActionListener AnimacaoNumPad = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			try {

				// Animação para mostrar o numPad
				if (numPadVisivel) {
					if (numPadPosicao <= 500) {
						numPad.setBounds((x - 500) + (numPadPosicao), (y - y) + 200, 500, 400);
						numPadPosicao += 20;
					} else {
						numPadVisivel = false;
						animacaoEmCurso = false;
						timer.stop();
						numPadPosicao = 0;
					}
				}

				// Animação para esconder o numPad
				else {
					if (numPadPosicao <= 500) {
						numPad.setBounds(x - numPadPosicao, (y - y) + 200, 500, 400);
						numPadPosicao += 20;
					} else {
						numPadVisivel = true;
						animacaoEmCurso = false;
						timer.stop();
						numPadPosicao = 0;
					}
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "ERRO",
						JOptionPane.ERROR_MESSAGE);
			}

		}
	};

	// Faz a ação de depositar na conta do usuario
	private void Depositar() {
		btnDeposito.setEnabled(false);
		if (CONTA.depositar(txtVisor.getText(), NUMERO_CONTA)) {
			btnDeposito.setEnabled(true);
			AtualizarPagina();
			operacaoEscolhida = "";
			txtVisor.setText("0");
			btnSaque.setEnabled(true);
			btnTranferencia.setEnabled(true);
			btnDeposito.setText("DEPÓSITO");
			operacaoEscolhida = "";
			btnConfirmar.setText("CONFIRMAR");
			animacaoEmCurso = false;
			numPadVisivel = true;
		} else {
			JOptionPane.showMessageDialog(null, "Não foi possível efetuar o depósito");
			btnDeposito.setEnabled(true);
			AtualizarPagina();
			operacaoEscolhida = "";
			txtVisor.setText("0");
			btnSaque.setEnabled(true);
			btnTranferencia.setEnabled(true);
			btnDeposito.setText("DEPÓSITO");
			operacaoEscolhida = "";
			btnConfirmar.setText("CONFIRMAR");
			animacaoEmCurso = false;
			numPadVisivel = true;
		}
	}

	// Faz a ação de tranferir da conta do usuario para outra
	private void Transferir(String NUMERO_CONTA2) {
		try {
			btnTranferencia.setEnabled(false);
			boolean tranferencia = false;

			if (TIPO_CONTA.equals("1")) {
				ContaCorrente contaCorrente = (ContaCorrente) CONTA;
				if (!contaCorrente.usandoLimite(NUMERO_CONTA, txtVisor.getText())) {
					if (CONTA.transferir(txtVisor.getText(), NUMERO_CONTA, NUMERO_CONTA2)) {
						tranferencia = true;
					}
				}

			} else if (TIPO_CONTA.equals("2")) {
				ContaPoupanca contaPoupanca = (ContaPoupanca) CONTA;
				if (!contaPoupanca.usandoLimite(NUMERO_CONTA, txtVisor.getText())) {
					if (CONTA.transferir(txtVisor.getText(), NUMERO_CONTA, NUMERO_CONTA2)) {
						tranferencia = true;
					}
				}
			} else {
				if (CONTA.transferir(txtVisor.getText(), NUMERO_CONTA, NUMERO_CONTA2)) {
					tranferencia = true;
				}
			}

			if (tranferencia) {
				btnTranferencia.setEnabled(true);
				AtualizarPagina();
				txtVisor.setText("0");
				btnSaque.setEnabled(true);
				btnDeposito.setEnabled(true);
				btnTranferencia.setText("TRANFERÊNCIA");
				operacaoEscolhida = "";
				btnConfirmar.setText("CONFIRMAR");
				animacaoEmCurso = false;
				numPadVisivel = true;
			} else {
				JOptionPane.showMessageDialog(null, "Não foi possível efetuar a transferencia");
				btnTranferencia.setEnabled(true);
				AtualizarPagina();
				txtVisor.setText("0");
				btnSaque.setEnabled(true);
				btnDeposito.setEnabled(true);
				btnTranferencia.setText("TRANFERÊNCIA");
				operacaoEscolhida = "";
				btnConfirmar.setText("CONFIRMAR");
				animacaoEmCurso = false;
				numPadVisivel = true;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	// Faz a ação de sacar da conta do usuario
	private void Sacar() {
		try {
			btnSaque.setEnabled(false);
			boolean saque = false;

			if (TIPO_CONTA.equals("1")) {
				ContaCorrente contaCorrente = (ContaCorrente) CONTA;
				if (contaCorrente.sacar(txtVisor.getText(), NUMERO_CONTA)) {
					saque = true;
				}
			} else if (TIPO_CONTA.equals("2")) {
				ContaPoupanca contaPoupanca = (ContaPoupanca) CONTA;
				if (contaPoupanca.sacar(txtVisor.getText(), NUMERO_CONTA)) {
					saque = true;
				}
			} else if (TIPO_CONTA.equals("3")) {
				if (CONTA.sacar(txtVisor.getText(), NUMERO_CONTA)) {
					saque = true;
				}
			}

			if (saque) {
				btnSaque.setEnabled(true);
				AtualizarPagina();
				operacaoEscolhida = "";
				txtVisor.setText("0");
				btnDeposito.setEnabled(true);
				btnTranferencia.setEnabled(true);
				btnSaque.setText("SAQUE");
				operacaoEscolhida = "";
				btnConfirmar.setText("CONFIRMAR");
				animacaoEmCurso = false;
				numPadVisivel = true;
			} else {
				JOptionPane.showMessageDialog(null, "Não foi possível efetuar o saque");
				btnSaque.setEnabled(true);
				AtualizarPagina();
				operacaoEscolhida = "";
				txtVisor.setText("0");
				btnDeposito.setEnabled(true);
				btnTranferencia.setEnabled(true);
				btnSaque.setText("SAQUE");
				operacaoEscolhida = "";
				btnConfirmar.setText("CONFIRMAR");
				animacaoEmCurso = false;
				numPadVisivel = true;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
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

	// Serve para copiar o valor do Visor
	private int valorVisor() {
		if (!txtVisor.getText().equals("")) {
			valor = Integer.parseInt(txtVisor.getText());
			return valor;
		} else {
			return 0;
		}
	}

	// Este metodo retorna a hora atual
	public String RetornarData() {

		try {
			// Retona a data atual para os Logs
			String data = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
			return data;

		} catch (MongoException e) {
			JOptionPane.showMessageDialog(null, "Ocorreu em erro" + e, "ERRO", JOptionPane.ERROR_MESSAGE);
			return "null";
		}

	}

}
