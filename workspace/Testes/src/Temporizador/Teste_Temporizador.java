package Temporizador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Teste_Temporizador {
	static Timer timer;
	static int count;

	public static void main(String[] args) {

		timer = new Timer((1000), action);
		timer.start();

	}

	static ActionListener action = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			System.out.println("" + count);
			count++;

			if (count > 5)
				timer.stop();

		}
	};
}
