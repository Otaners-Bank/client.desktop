package control;

public class ContaCorrente extends Conta {

	public ContaCorrente(String numeroConta, String nome, String cpf) {
		super(numeroConta, nome, cpf);
		
	}

	public ContaCorrente(String numeroConta, String nome, String cpf, String limite) {
		super(numeroConta, nome, cpf);
		
	}

	String limite;

	private String getLimite() {
		return limite;
	}

	private void setLimite(String lIMITE) {
		limite = lIMITE;
	}
	
	public boolean usandoLimite() {
		return true;
	}
	
	@Override
	public boolean sacar(double valorSacado) {
		// TODO Auto-generated method stub
		return super.sacar(valorSacado);
	}
	
	@Override
	public String consultarSaldo() {
		// TODO Auto-generated method stub
		return super.consultarSaldo();
	}
	
}
