package control;

public class ContaCorrente extends Conta {

	public ContaCorrente() {
		
	}
	
	public ContaCorrente(String numeroConta, String nome, String cpf) {
		super(numeroConta, nome, cpf);

	}

	public ContaCorrente(String numeroConta, String nome, String cpf, String limite) {
		super(numeroConta, nome, cpf);
		setLimite(super.saldo);
		limite = getLimite();
	}

	String limite;

	private String getLimite() {
		return limite;
	}

	private void setLimite(String limite) {
		this.limite = limite;
	}

	public boolean usandoLimite() {
		if (Integer.parseInt(getLimite().replace("R$ ", "")) <= 0) {
			return true;
		} else {
			return false;
		}
	}


	@Override
	public String consultarSaldo() {
		// TODO Auto-generated method stub
		return "Saldo >>> " + super.consultarSaldo() + " & Limite >>> " + getLimite();
	}

}
