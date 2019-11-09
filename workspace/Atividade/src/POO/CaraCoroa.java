package POO;

import java.security.PublicKey;
import java.util.Random;
import java.util.Scanner;

public class CaraCoroa {
	public static void main(String[] args) {
		Scanner renatinho = new Scanner(System.in);
		int QuantidadeQueOProgramaVaiExecutar = 0;
		boolean continuar = true;
		

		while (continuar) {

			QuantidadeQueOProgramaVaiExecutar = renatinho.nextInt();

			if (QuantidadeQueOProgramaVaiExecutar == 0) {
				System.exit(0);
			} else {
				for (int i = 0; i < QuantidadeQueOProgramaVaiExecutar; i++) {
					Random random = new Random();
					int NumeroSorteado = random.nextInt(2);
					System.out.print(NumeroSorteado + " ");
				}
				System.err.println("\n");
			}
			
		}

		

	}

	public static int GerarResultado() {
		return 0;
	}

}
