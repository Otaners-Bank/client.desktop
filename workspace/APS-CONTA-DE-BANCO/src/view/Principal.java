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

	// Numero da conta do Cliente atual
	private static String NUMERO_CONTA = "";
	private static String TIPO_CONTA = "";

	// Para armazenar a resolucao da maquina
	public static Toolkit toolkit = Toolkit.getDefaultToolkit();
	public static Dimension dimension = toolkit.getScreenSize();
	private static int meio_horizontal = 0;
	private static int meio_vertical = 0;

	// Componentes
	private JPanel contentPane;
	private static JButton btnDeposito;
	private static JButton btnSaque;
	private static JTextField txtDeposito;

	public static void main(String[] args, String numeroConta) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					meio_vertical = ( dimension.height / 2 );
					meio_horizontal = ( dimension.width / 2 );
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

		// getConta();

		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, (dimension.width), (dimension.height)); // Altera o tamanho e a posicao da janela
		setLocationRelativeTo(null);
		setVisible(true);
		setExtendedState(6); // Deixa a janela em tela cheia
		
		contentPane = new JPanel();
		contentPane.setBounds(100, 100, (dimension.width), (dimension.height));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblBemVindo = new JLabel();
		lblBemVindo.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblBemVindo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBemVindo.setBounds( (meio_horizontal/2) , meio_vertical, meio_horizontal, 300);
		contentPane.add(lblBemVindo);

		btnDeposito = new JButton();
		btnDeposito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (control.depositar("R$ 100", NUMERO_CONTA)) {
					getConta();
					// lblBemVindo.setText("Bem Vindo, " + CONTA.getNome() + " ! Seu saldo é " + CONTA.getSaldo() + " hahaha.");
				} else {
					JOptionPane.showMessageDialog(null, "Putz, não deu não");
				}
			}
		});
		btnDeposito.setFont(new Font("Felix Titling", Font.PLAIN, 11));
		btnDeposito.setBounds( (meio_horizontal/2) , dimension.height - 400, meio_horizontal, (meio_vertical/2));
		btnDeposito.setText("DEPOSITAR");
		contentPane.add(btnDeposito);

		btnSaque = new JButton();
		btnSaque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (control.sacar("R$ 1", NUMERO_CONTA)) {
					getConta();
					// lblBemVindo.setText("Bem Vindo, " + CONTA.getNome() + " ! Seu saldo é " + CONTA.getSaldo() + " hahaha.");
				} else {
					JOptionPane.showMessageDialog(null, "Putz, não deu não");
				}
			}
		});
		btnSaque.setFont(new Font("Felix Titling", Font.PLAIN, 11));
		btnSaque.setBounds( (meio_horizontal/2) , dimension.height - 200, meio_horizontal, (meio_vertical/2));
		btnSaque.setText("SACAR");
		contentPane.add(btnSaque);
		
		JButton btnTranferencia = new JButton("TRANFERENCIA");
		btnTranferencia.setBounds( (meio_horizontal/2) , meio_vertical, meio_horizontal, (meio_vertical/2));
		contentPane.add(btnTranferencia);

		// lblBemVindo.setText("Bem Vindo, " + CONTA.getNome() + " ! Seu saldo é " + CONTA.getSaldo() + " hahaha.");

	}

	private void getConta() {

		try {
			TIPO_CONTA = String.valueOf(NUMERO_CONTA.charAt(NUMERO_CONTA.length() - 1));
			CONTA = control.buscarConta(NUMERO_CONTA);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "ERRO",
					JOptionPane.ERROR_MESSAGE);
		}

	}
}
