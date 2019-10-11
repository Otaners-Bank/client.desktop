package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import control.Conta;
import control.Control;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Principal extends JFrame {

	Control control = new Control();
	
	private JPanel contentPane;
	
	private static String NUMERO_CONTA = "";

	
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
		lblBemVindo.setHorizontalAlignment(SwingConstants.CENTER);
		lblBemVindo.setBounds(10, 102, 414, 24);
		contentPane.add(lblBemVindo);
		setLocationRelativeTo(null);
		setVisible(true);
		
		Conta conta = control.buscarConta(NUMERO_CONTA);
		lblBemVindo.setText("Bem Vindo, "+ conta.NOME + " !" );
		
	}

}
