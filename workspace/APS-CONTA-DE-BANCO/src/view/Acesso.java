package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import control.Control;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class Acesso extends JFrame {

	// Instancias
	Control control = new Control();

	// Para armazenar a resolucao da maquina
	public static Toolkit toolkit = Toolkit.getDefaultToolkit();
	public static Dimension dimension = toolkit.getScreenSize();

	// Para gerar os numeros da senha
	private static String strSenha1 = "", strSenha2 = "", strSenha3 = "", strSenha4 = "", strSenha5 = "";
	private static List<Integer> senhas_1, senhas_2, senhas_3, senhas_4, senhas_5;

	// para guardar a senha digitada pelo usuario e suas combinacoes
	protected static List<String> ListaDeSenhasPossiveis = new ArrayList<String>();
	protected static String senhasPossiveis[] = new String[16];

	// Componentes
	private JPanel LoginContentPane;

	private JTextField txtConta;
	private JLabel lblConta;
	private JLabel lblSenha;
	private JPasswordField txtSenha;
	private JButton btnSenha1;
	private JButton btnSenha3;
	private JButton btnSenha2;
	private JButton btnSenha4;
	private JButton btnSenha5;
	private JButton btnApagar;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Acesso frame = new Acesso(); // Utilizado para configurar a janela
					frame.setSize((dimension.width / 2), (dimension.height - 50)); // Muda o tamanho da janela
					frame.setLocationRelativeTo(null); // Deixa a janela centralizada
					// frame.setExtendedState(6); // Deixa a janela em tela cheia
					frame.setVisible(true); // Faz a janela aparecer
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings("deprecation")
	public Acesso() {
		setResizable(false);
		setAlwaysOnTop(false);
		setTitle("Otaner Bank");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, (dimension.width / 2), (dimension.height - 50));
		// JOptionPane.showMessageDialog(null, "Tamanho: height="+(dimension.width /
		// 2)+" width="+(dimension.height - 50));
		LoginContentPane = new JPanel();
		LoginContentPane.setForeground(Color.YELLOW);
		LoginContentPane.setBackground(Color.PINK);
		LoginContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(LoginContentPane);
		LoginContentPane.setLayout(null);

		JLabel lblBemVindo = new JLabel("Bem Vindo ao Otaner Bank !");
		lblBemVindo.setFont(new Font("Felix Titling", Font.PLAIN, 20));
		lblBemVindo.setHorizontalAlignment(SwingConstants.CENTER);
		lblBemVindo.setBounds(10, 28, (dimension.width / 2), 20);
		LoginContentPane.add(lblBemVindo);

		txtConta = new JFormattedTextField(Mascara("####-#"));
		txtConta.setHorizontalAlignment(SwingConstants.CENTER);
		txtConta.setBounds(276, 120, 120, 25);
		LoginContentPane.add(txtConta);
		txtConta.setColumns(10);

		lblConta = new JLabel("Conta:");
		lblConta.setFont(new Font("Felix Titling", Font.PLAIN, 11));
		lblConta.setHorizontalAlignment(SwingConstants.CENTER);
		lblConta.setBounds(276, 94, 120, 25);
		LoginContentPane.add(lblConta);

		lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Felix Titling", Font.PLAIN, 11));
		lblSenha.setHorizontalAlignment(SwingConstants.CENTER);
		lblSenha.setBounds(276, 183, 120, 25);
		LoginContentPane.add(lblSenha);

		txtSenha = new JPasswordField(5);
		txtSenha.setHorizontalAlignment(SwingConstants.CENTER);
		txtSenha.setFont(new Font("Tahoma", Font.PLAIN, 99));
		txtSenha.setBackground(Color.PINK);
		txtSenha.setBounds(155, 209, 359, 74);
		txtSenha.setEditable(false);

		LoginContentPane.add(txtSenha);

		DefinirTextoDosBotoesDeSenha(); // Adiciona os textos dos botoes de senha aleatoriamente para seguranca

		btnSenha1 = new JButton(strSenha1);
		btnSenha1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Registra os dois possiveis numero do botao clicado para a senha
				if (txtSenha.getText().length() < 4) {
					txtSenha.setText(txtSenha.getText() + "X");
					ListaDeSenhasPossiveis.add(btnSenha1.getText().replace(" ou ", "|"));
				}
			}
		});
		btnSenha1.setFont(new Font("Felix Titling", Font.PLAIN, 11));
		btnSenha1.setBounds(155, 328, 94, 74);
		LoginContentPane.add(btnSenha1);

		btnSenha2 = new JButton(strSenha2);
		btnSenha2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Registra os dois possiveis numero do botao clicado para a senha
				if (txtSenha.getText().length() < 4) {
					txtSenha.setText(txtSenha.getText() + "X");
					ListaDeSenhasPossiveis.add(btnSenha2.getText().replace(" ou ", "|"));
				}
			}
		});
		btnSenha2.setFont(new Font("Felix Titling", Font.PLAIN, 11));
		btnSenha2.setBounds(285, 328, 94, 74);
		LoginContentPane.add(btnSenha2);

		btnSenha3 = new JButton(strSenha3);
		btnSenha3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Registra os dois possiveis numero do botao clicado para a senha
				if (txtSenha.getText().length() < 4) {
					txtSenha.setText(txtSenha.getText() + "X");
					ListaDeSenhasPossiveis.add(btnSenha3.getText().replace(" ou ", "|"));
				}
			}
		});
		btnSenha3.setFont(new Font("Felix Titling", Font.PLAIN, 11));
		btnSenha3.setBounds(420, 328, 94, 74);
		LoginContentPane.add(btnSenha3);

		btnSenha4 = new JButton(strSenha4);
		btnSenha4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Registra os dois possiveis numero do botao clicado para a senha
				if (txtSenha.getText().length() < 4) {
					txtSenha.setText(txtSenha.getText() + "X");
					ListaDeSenhasPossiveis.add(btnSenha4.getText().replace(" ou ", "|"));
				}
			}
		});
		btnSenha4.setFont(new Font("Felix Titling", Font.PLAIN, 11));
		btnSenha4.setBounds(155, 425, 94, 74);
		LoginContentPane.add(btnSenha4);

		btnSenha5 = new JButton(strSenha5);
		btnSenha5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Registra os dois possiveis numero do botao clicado para a senha
				if (txtSenha.getText().length() < 4) {
					txtSenha.setText(txtSenha.getText() + "X");
					ListaDeSenhasPossiveis.add(btnSenha5.getText().replace(" ou ", "|"));
				}
			}
		});
		btnSenha5.setFont(new Font("Felix Titling", Font.PLAIN, 11));
		btnSenha5.setBounds(285, 425, 94, 74);
		LoginContentPane.add(btnSenha5);

		btnApagar = new JButton("APAGAR");
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Utilizado para apagar o ultimo digito da senha digitada
				String strSenhaDigitada = txtSenha.getText();
				txtSenha.setText(DeletarUltimoDigitoDaSenha(strSenhaDigitada));
			}
		});
		btnApagar.setFont(new Font("Felix Titling", Font.PLAIN, 11));
		btnApagar.setBounds(420, 425, 94, 74);
		LoginContentPane.add(btnApagar);

		JButton btnAcessar = new JButton("ACESSAR");
		btnAcessar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				// Esse botao verifica a senha e a conta para efetuar login

				if (txtSenha.getText().length() < 4 || txtConta.getText().length() < 6
						|| txtConta.getText().contains(" ")) {
					JOptionPane.showMessageDialog(null, "Preencha todos os campos para continuar, valeu !", "AVISO",
							JOptionPane.WARNING_MESSAGE);
				} else {
					DefinirPossiveisPadroesDeSenha();

					if (control.acessarConta(txtConta.getText(), senhasPossiveis)) {
						Principal.main(null,txtConta.getText());
						LimparDados();
						senhasPossiveis = null;
						dispose();
					} else {
						LimparDados();
						btnSenha1.setText(strSenha1);
						btnSenha2.setText(strSenha2);
						btnSenha3.setText(strSenha3);
						btnSenha4.setText(strSenha4);
						btnSenha5.setText(strSenha5);
						JOptionPane.showMessageDialog(null, "Acesso negado", "AVISO", JOptionPane.WARNING_MESSAGE);
					}

				}
			}

		});
		btnAcessar.setFont(new Font("Felix Titling", Font.PLAIN, 11));
		btnAcessar.setBounds(247, 539, 173, 33);
		LoginContentPane.add(btnAcessar);

		JLabel lblCriarConta = new JLabel("> n\u00E3o possui conta ainda?");
		lblCriarConta.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				LimparDados();
				CriarConta.main(null);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
				lblCriarConta.setCursor(cursor);
			}
		});
		lblCriarConta.setHorizontalAlignment(SwingConstants.CENTER);
		lblCriarConta.setForeground(Color.BLUE);
		lblCriarConta.setBounds(247, 583, 173, 14);
		LoginContentPane.add(lblCriarConta);

		JLabel lblRecuperarSenha = new JLabel("> esqueceu sua senha de acesso?");
		lblRecuperarSenha.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				LimparDados();
				RecuperarSenha.main(null);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
				lblRecuperarSenha.setCursor(cursor);
			}
		});
		lblRecuperarSenha.setHorizontalAlignment(SwingConstants.CENTER);
		lblRecuperarSenha.setForeground(Color.BLUE);
		lblRecuperarSenha.setBounds(238, 608, 195, 14);
		LoginContentPane.add(lblRecuperarSenha);

	}

	// Cria aleatoriamente os pares para que o usuario digite a senha
	private static void DefinirTextoDosBotoesDeSenha() {
		try {
			Random random = new Random();
			int num1 = 0, num2 = 0;

			senhas_1 = new ArrayList<Integer>();
			senhas_2 = new ArrayList<Integer>();
			senhas_3 = new ArrayList<Integer>();
			senhas_4 = new ArrayList<Integer>();
			senhas_5 = new ArrayList<Integer>();

			for (int i = 0; i <= 5;) {

				num1 = random.nextInt(10);
				num2 = random.nextInt(10);

				if (i == 0) {
					if (num1 != num2) {
						senhas_1.add(num1);
						senhas_1.add(num2);
						i++;
					}
				}

				else if (i == 1) {
					if (num1 != num2 && !senhas_1.contains(num1) && !senhas_1.contains(num2)) {
						senhas_2.add(num1);
						senhas_2.add(num2);
						i++;
					}
				}

				else if (i == 2) {
					if (num1 != num2 && !senhas_2.contains(num1) && !senhas_2.contains(num2) && !senhas_1.contains(num1)
							&& !senhas_1.contains(num2)) {
						senhas_3.add(num1);
						senhas_3.add(num2);
						i++;
					}
				}

				else if (i == 3) {
					if (num1 != num2 && !senhas_3.contains(num1) && !senhas_3.contains(num2) && !senhas_2.contains(num1)
							&& !senhas_2.contains(num2) && !senhas_1.contains(num1) && !senhas_1.contains(num2)) {
						senhas_4.add(num1);
						senhas_4.add(num2);
						i++;
					}
				}

				else if (i == 4) {
					if (num1 != num2 && !senhas_4.contains(num1) && !senhas_4.contains(num2) && !senhas_3.contains(num1)
							&& !senhas_3.contains(num2) && !senhas_2.contains(num1) && !senhas_2.contains(num2)
							&& !senhas_1.contains(num1) && !senhas_1.contains(num2)) {
						senhas_5.add(num1);
						senhas_5.add(num2);
						i++;
					}
				}

				else if (i == 5) {
					strSenha1 = senhas_1.get(0) + " ou " + senhas_1.get(1);
					strSenha2 = senhas_2.get(0) + " ou " + senhas_2.get(1);
					strSenha3 = senhas_3.get(0) + " ou " + senhas_3.get(1);
					strSenha4 = senhas_4.get(0) + " ou " + senhas_4.get(1);
					strSenha5 = senhas_5.get(0) + " ou " + senhas_5.get(1);

					i++;
				}

			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "ERRO",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// Define os padroes possiveis de senha com base no que o usuario digitou
	private static void DefinirPossiveisPadroesDeSenha() {
		try {
			for (int i = 0, j = 0; i < 16;) {

				if (i < 8) {

					senhasPossiveis[i] = (ListaDeSenhasPossiveis.get(0).substring(0,
							ListaDeSenhasPossiveis.get(0).indexOf("|")));

					if (i < 4) {
						senhasPossiveis[i] += (ListaDeSenhasPossiveis.get(1).substring(0,
								ListaDeSenhasPossiveis.get(1).indexOf("|")));
					} else {
						senhasPossiveis[i] += (ListaDeSenhasPossiveis.get(1)
								.substring(ListaDeSenhasPossiveis.get(1).indexOf("|")).replace("|", ""));
					}

					if (j < 2) {
						senhasPossiveis[i] += (ListaDeSenhasPossiveis.get(2).substring(0,
								ListaDeSenhasPossiveis.get(1).indexOf("|")));
						j++;
					} else {
						senhasPossiveis[i] += (ListaDeSenhasPossiveis.get(2)
								.substring(ListaDeSenhasPossiveis.get(1).indexOf("|")).replace("|", ""));
						if (i % 2 != 0)
							j = 0;
					}

					if (i % 2 == 0) {
						senhasPossiveis[i] += (ListaDeSenhasPossiveis.get(3).substring(0,
								ListaDeSenhasPossiveis.get(1).indexOf("|")));
					} else {
						senhasPossiveis[i] += (ListaDeSenhasPossiveis.get(3)
								.substring(ListaDeSenhasPossiveis.get(1).indexOf("|")).replace("|", ""));
					}

					i++;

				} else {

					senhasPossiveis[i] = ((ListaDeSenhasPossiveis.get(0)
							.substring(ListaDeSenhasPossiveis.get(0).indexOf("|"))).replace("|", ""));

					if (i < 12) {
						senhasPossiveis[i] += (ListaDeSenhasPossiveis.get(1).substring(0,
								ListaDeSenhasPossiveis.get(1).indexOf("|")));
					} else {
						senhasPossiveis[i] += (ListaDeSenhasPossiveis.get(1)
								.substring(ListaDeSenhasPossiveis.get(1).indexOf("|")).replace("|", ""));
					}

					if (j < 2) {
						senhasPossiveis[i] += (ListaDeSenhasPossiveis.get(2).substring(0,
								ListaDeSenhasPossiveis.get(1).indexOf("|")));
						j++;
					} else {
						senhasPossiveis[i] += (ListaDeSenhasPossiveis.get(2)
								.substring(ListaDeSenhasPossiveis.get(1).indexOf("|")).replace("|", ""));
						if (i % 2 != 0)
							j = 0;
					}

					if (i % 2 == 0) {
						senhasPossiveis[i] += (ListaDeSenhasPossiveis.get(3).substring(0,
								ListaDeSenhasPossiveis.get(1).indexOf("|")));
					} else {
						senhasPossiveis[i] += (ListaDeSenhasPossiveis.get(3)
								.substring(ListaDeSenhasPossiveis.get(1).indexOf("|")).replace("|", ""));
					}

					i++;

				}

			}

			/*
			 * System.out.println("Possibilidades:\n" + ListaDeSenhasPossiveis + "\n\n");
			 * for (int j = 0; j < senhasPossiveis.length; j++) { if (j == 8)
			 * System.out.println(); System.out.println(senhasPossiveis[j]); }
			 */

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "ERRO",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// Isso é um botao de deletar, que vai deletando numero por numero, um de cada
	// vez no campo senha
	private static String DeletarUltimoDigitoDaSenha(String strSenhaDigitada) {
		try {

			String strRetornoParaOCampoSenha = "";

			if (strSenhaDigitada.length() == 0) {

			} else if (strSenhaDigitada.length() == 1) {
				strRetornoParaOCampoSenha = "";
			} else if (strSenhaDigitada.length() == 2) {
				strRetornoParaOCampoSenha = ("" + strSenhaDigitada.charAt(0));
			} else if (strSenhaDigitada.length() == 3) {
				strRetornoParaOCampoSenha = ("" + strSenhaDigitada.charAt(0) + strSenhaDigitada.charAt(1));
			} else if (strSenhaDigitada.length() == 4) {
				strRetornoParaOCampoSenha = ("" + strSenhaDigitada.charAt(0) + strSenhaDigitada.charAt(1)
						+ strSenhaDigitada.charAt(2));
			}

			if (ListaDeSenhasPossiveis.size() == 0) {

			} else if (ListaDeSenhasPossiveis.size() == 1) {
				ListaDeSenhasPossiveis.remove(0);
			} else if (ListaDeSenhasPossiveis.size() == 2) {
				ListaDeSenhasPossiveis.remove(1);
			} else if (ListaDeSenhasPossiveis.size() == 3) {
				ListaDeSenhasPossiveis.remove(2);
			} else if (ListaDeSenhasPossiveis.size() == 4) {
				ListaDeSenhasPossiveis.remove(3);
			}

			return strRetornoParaOCampoSenha;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "ERRO",
					JOptionPane.ERROR_MESSAGE);
			return "";
		}
	}

	// Metodo para criar mascaras nos campos
	public MaskFormatter Mascara(String Mascara) {

		MaskFormatter F_Mascara = new MaskFormatter();
		try {
			F_Mascara.setMask(Mascara); // Atribui a mascara
			F_Mascara.setPlaceholderCharacter(' '); // Caracter para preencimento
		} catch (Exception excecao) {
			excecao.printStackTrace();
		}
		return F_Mascara;
	}

	// Limpa os dados do cliente para segurança
	private void LimparDados() {

		ListaDeSenhasPossiveis.clear();
		txtConta.setText("");
		txtSenha.setText("");
		strSenha1 = "";
		strSenha2 = "";
		strSenha3 = "";
		strSenha4 = "";
		strSenha5 = "";
		DefinirTextoDosBotoesDeSenha(); // Adiciona os textos dos botoes de senha aleatoriamente para seguranca
	}
}
