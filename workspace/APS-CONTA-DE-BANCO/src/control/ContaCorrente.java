package control;

public class ContaCorrente extends Conta {

	// Construtores
	public ContaCorrente() {

	}

	public ContaCorrente(String numeroConta, String nome, String cpf) {
		super(numeroConta, nome, cpf);

	}

	public ContaCorrente(String numeroConta, String nome, String cpf, String limite) {
		super(numeroConta, nome, cpf);
		setLimite(super.getSaldo());
		limite = getLimite();
	}

	// Atributos
	String limite; // Não será utilizado

	// Getters e Setters
	private String getLimite() {
		return limite;
	}

	private void setLimite(String limite) {
		this.limite = limite;
	}

	// Metodos
	public boolean usandoLimite(String numeroConta, String valorTransicao) {
		return model.validarLimite(numeroConta, valorTransicao);
	}

	// Metodos reimplementados
	@Override
	public boolean sacar(String valorSaque, String numeroConta) {
		if(!usandoLimite(numeroConta, valorSaque)) {
			return super.sacar(valorSaque, numeroConta);
		}else {
			return false;
		}
	}

	@Override
	public void setSaldo(String saldo) {
		super.setSaldo(saldo);
	}

	// NÃO SERÁ FEITO -
	@Override
	public String imprimir() {
		return super.imprimir();
	}
}
