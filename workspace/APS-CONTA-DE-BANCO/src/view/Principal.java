package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import control.Conta;
import control.ContaCorrente;
import control.ContaEspecial;
import control.ContaPoupanca;
import control.Control;
import model.DataAccess;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

@SuppressWarnings("serial")
public class Principal extends JFrame {

	// Instancias
	Control control = new Control();
	Conta CONTA = null;

	// Timer
	Timer timer;

	// Numero da conta do Cliente atual
	private static String NUMERO_CONTA = "";
	private static String TIPO_CONTA = "";

	// Para armazenar a resolucao da maquina
	public static Toolkit toolkit = Toolkit.getDefaultToolkit();
	public static Dimension dimension = toolkit.getScreenSize();
	public static int x;
	public static int y;

	// Componentes
	private JPanel contentPane;
	private JPanel numPad;
	private static JButton btnDeposito; 
	private static JButton btnTranferencia;
	private static JButton btnSaque;
	private static JLabel lblBemVindo;
	private static JLabel lblRenda;

	public static void main(String[] args, String numeroConta) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NUMERO_CONTA = numeroConta;
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setBackground(Color.WHITE);

		x = dimension.width;
		y = dimension.height;
		
		timer = new Timer((1000), carregarConta);
		timer.setRepeats(true);
		timer.start();

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 700, 700); // Altera o tamanho e a posicao da janela
		setLocationRelativeTo(null);
		setVisible(true);
		setExtendedState(6); // Deixa a janela em tela cheia

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
		btnDeposito.setBounds(0, (y - y) + 200 , 250, 100);
		btnDeposito.setFont(new Font("Felix Titling", Font.PLAIN, 11));
		btnDeposito.setText("DEPOSITAR");

		btnTranferencia = new JButton();
		btnTranferencia.setBounds(0, (y - y) + 350, 250, 100);
		btnTranferencia.setFont(new Font("Felix Titling", Font.PLAIN, 11));
		btnTranferencia.setText("TRANSFERIR");
		
		btnSaque = new JButton();
		btnSaque.setBounds(0, (y - y) + 500, 250, 100);
		btnSaque.setFont(new Font("Felix Titling", Font.PLAIN, 11));
		btnSaque.setText("SACAR");

		numPad = new JPanel();
		numPad.setBounds(x-500, (y - y) + 200, 500, 400);
		numPad.setBorder(new EmptyBorder(5, 5, 5, 5));
		numPad.setLayout(null);
		
		btnSaque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (control.sacar("R$ 1", NUMERO_CONTA)) {
					getConta();
					// lblBemVindo.setText("Bem Vindo, " + CONTA.getNome() + " ! Seu saldo é " +
					// CONTA.getSaldo() + " hahaha.");
				} else {
					JOptionPane.showMessageDialog(null, "Putz, não deu não");
				}
			}
		});

		btnTranferencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (control.depositar("R$ 100", NUMERO_CONTA)) {
					getConta();
					// lblBemVindo.setText("Bem Vindo, " + CONTA.getNome() + " ! Seu saldo é " +
					// CONTA.getSaldo() + " hahaha.");
				} else {
					JOptionPane.showMessageDialog(null, "Putz, não deu não");
				}
			}
		});

		btnDeposito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (control.depositar("R$ 100", NUMERO_CONTA)) {
					getConta();
					// lblBemVindo.setText("Bem Vindo, " + CONTA.getNome() + " ! Seu saldo é " +
					// CONTA.getSaldo() + " hahaha.");
				} else {
					JOptionPane.showMessageDialog(null, "Putz, não deu não");
				}
			}
		});

		setContentPane(contentPane);
		contentPane.add(lblBemVindo);
		contentPane.add(lblRenda);
		contentPane.add(btnSaque);
		contentPane.add(btnTranferencia);
		contentPane.add(btnDeposito);
		contentPane.add(numPad);

	}

	private void getConta() {

		try {
			TIPO_CONTA = String.valueOf(NUMERO_CONTA.charAt(NUMERO_CONTA.length() - 1));
			CONTA = control.buscarConta(NUMERO_CONTA);
			if(TIPO_CONTA.equals("2")) {
				lblRenda.setVisible(true);
				lblRenda.setText("redimentos na poupança: + " + "R$ 0,04" + " ao dia / Valor recebido até o momento: + " + "R$ 1,23");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "ERRO",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	ActionListener carregarConta = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			try {

				if (lblBemVindo.getText().equals("Carregando")) {
					lblBemVindo.setText("Carregando .");
				} else if (lblBemVindo.getText().equals("Carregando .")) {
					lblBemVindo.setText("Carregando . .");
				} else if (lblBemVindo.getText().equals("Carregando . .")) {
					lblBemVindo.setText("Carregando . . .");
				} else if (lblBemVindo.getText().equals("Carregando . . .")) {
					getConta();
					lblBemVindo.setText(
							"Bem Vindo, " + CONTA.getNome() + " ! Seu saldo é " + CONTA.getSaldo() + " hahaha.");
					timer.stop();
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "ERRO",
						JOptionPane.ERROR_MESSAGE);
			}

		}
	};

}
