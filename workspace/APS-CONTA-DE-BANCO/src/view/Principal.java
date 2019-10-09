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

	
	public static void main(String[] args, String numeroConta) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal(numeroConta);
					frame.setLocationRelativeTo(null);
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
	public Principal(String numeroConta) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Conta conta = control.buscarConta(numeroConta);
		
		JLabel lblBemVindo = new JLabel("Bem Vindo, "+ numeroConta + " !" );
		lblBemVindo.setHorizontalAlignment(SwingConstants.CENTER);
		lblBemVindo.setBounds(10, 102, 414, 24);
		contentPane.add(lblBemVindo);
		setLocationRelativeTo(null);
		
		// JOptionPane.showMessageDialog(null, control.buscarConta(numeroConta));
		
	}

}
