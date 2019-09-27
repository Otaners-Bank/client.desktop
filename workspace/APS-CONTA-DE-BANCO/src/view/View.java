package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class View extends JFrame {

	// Para armazenar a resolucao da maquina
	public static Toolkit toolkit = Toolkit.getDefaultToolkit();
	public static Dimension dimension = toolkit.getScreenSize();

	// Para gerar os numeros da senha
	private static String strSenha1 = "", strSenha2 = "", strSenha3 = "", strSenha4 = "", strSenha5 = "";
	private static List<Integer> senhas_1, senhas_2, senhas_3, senhas_4, senhas_5;

	// Componentes
	private JPanel contentPane;

	private JTextField txtConta;
	private JLabel lblNmeroDeSua;
	private JLabel lblSuaSenha;
	private JPasswordField passwordField;
	private JButton btnSenha1;
	private JButton btnSenha3;
	private JButton btnSenha2;
	private JButton btnSenha4;
	private JButton btnSenha5;
	private JButton btnBackspace;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					View frame = new View();

					frame.setSize((dimension.width / 2), (dimension.height - 50));
					frame.setLocationRelativeTo(null);
					// frame.setExtendedState(6); // Deixa a janela em tela cheia

					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public View() {
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle("Otaner Bank");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, (dimension.width / 2), (dimension.height - 50));
		contentPane = new JPanel();
		contentPane.setForeground(Color.YELLOW);
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblBemVindo = new JLabel("Bem Vindo ao Otaner Bank !");
		lblBemVindo.setFont(new Font("Felix Titling", Font.PLAIN, 20));
		lblBemVindo.setHorizontalAlignment(SwingConstants.CENTER);
		lblBemVindo.setBounds(10, 28, (dimension.width / 2), 20);
		contentPane.add(lblBemVindo);

		txtConta = new JTextField();
		txtConta.setBounds(276, 120, 120, 25);
		contentPane.add(txtConta);
		txtConta.setColumns(10);

		lblNmeroDeSua = new JLabel("Conta:");
		lblNmeroDeSua.setHorizontalAlignment(SwingConstants.CENTER);
		lblNmeroDeSua.setBounds(276, 94, 120, 25);
		contentPane.add(lblNmeroDeSua);

		lblSuaSenha = new JLabel("Senha:");
		lblSuaSenha.setHorizontalAlignment(SwingConstants.CENTER);
		lblSuaSenha.setBounds(276, 183, 120, 25);
		contentPane.add(lblSuaSenha);

		passwordField = new JPasswordField(5);
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 99));
		passwordField.setBackground(Color.PINK);
		passwordField.setBounds(155, 209, 359, 74);
		passwordField.setEditable(false);
		passwordField.setText("a");
		
		contentPane.add(passwordField);

		DefinirTextoDosBotoesDeSenha();

		btnSenha1 = new JButton(strSenha1);
		btnSenha1.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(passwordField.getText().length() < 4) {
					passwordField.setText(passwordField.getText() + "X");
				}
			}
		});
		btnSenha1.setBounds(155, 328, 94, 74);
		contentPane.add(btnSenha1);

		btnSenha2 = new JButton(strSenha2);
		btnSenha2.setBounds(285, 328, 94, 74);
		btnSenha2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}
		});
		contentPane.add(btnSenha2);

		btnSenha3 = new JButton(strSenha3);
		btnSenha3.setBounds(420, 328, 94, 74);
		btnSenha3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}
		});
		contentPane.add(btnSenha3);

		btnSenha4 = new JButton(strSenha4);
		btnSenha4.setBounds(155, 425, 94, 74);
		btnSenha4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}
		});
		contentPane.add(btnSenha4);

		btnSenha5 = new JButton(strSenha5);
		btnSenha5.setBounds(285, 425, 94, 74);
		btnSenha5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}
		});
		contentPane.add(btnSenha5);

		btnBackspace = new JButton("New button");
		btnBackspace.setBounds(420, 425, 94, 74);
		btnBackspace.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String senha = passwordField.getText();
				if(senha.length() == 0) {
					
				}else if(senha.length() == 1) {
					passwordField.setText( "" );
				}else if(senha.length() == 2) {
					passwordField.setText( "" + senha.charAt(0) );
				}else if(senha.length() == 3) {
					passwordField.setText( "" + senha.charAt(0) + senha.charAt(1) );
				}else if(senha.length() == 4) {
					passwordField.setText( "" + senha.charAt(0) + senha.charAt(1) + senha.charAt(2) );
				}
			}
		});
		contentPane.add(btnBackspace);

	}

	private static void DefinirTextoDosBotoesDeSenha() {

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

	}

}
