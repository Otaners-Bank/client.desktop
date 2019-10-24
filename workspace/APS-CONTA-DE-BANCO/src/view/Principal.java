package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import control.Conta;
import control.ContaCorrente;
import control.ContaEspecial;
import control.ContaPoupanca;
import control.Control;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;

@SuppressWarnings("serial")
public class Principal extends JFrame {

	// Instancias
	Control control = new Control();
	Conta CONTA = null;

	// Numero da conta do Cliente atual
	private static String NUMERO_CONTA = "";
	private static String TIPO_CONTA = "";

	// Componentes
	private JPanel contentPane;

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
		setExtendedState(6); // Deixa a janela em tela cheia
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblBemVindo = new JLabel();
		lblBemVindo.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblBemVindo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBemVindo.setBounds(10, 11, 1346, 24);
		contentPane.add(lblBemVindo);
		setLocationRelativeTo(null);
		setVisible(true);

		getConta();

		lblBemVindo.setText("Bem Vindo, " + CONTA.getNome() + " ! Seu saldo é " + CONTA.getSaldo() + " hahaha. (" + CONTA.getClass());

	}

	private void getConta() {

		try {
			TIPO_CONTA = String.valueOf(NUMERO_CONTA.charAt(NUMERO_CONTA.length() - 1));

			if (CONTA == null) {
				CONTA = control.buscarConta(NUMERO_CONTA);
			} else {
				CONTA = null;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage(), "ERRO",
					JOptionPane.ERROR_MESSAGE);
		}

	}

}
