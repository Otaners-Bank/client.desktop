package control;

public class ContaPoupanca extends Conta {

	// Construtores
	public ContaPoupanca(String numeroConta, String nome, String cpf) {
		super(numeroConta, nome, cpf);

	}

	public ContaPoupanca() {

	}

	// Atributos
	String rendaGerada;
	String ultimoAcesso;

	// Getter e Setters
	public String getRendaGerada() {
		return rendaGerada;
	}

	public void setRendaGerada(String rendaGerada) {
		this.rendaGerada = rendaGerada;
	}

	public String getUltimoAcesso() {
		return ultimoAcesso;
	}

	public void setUltimoAcesso(String ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}

	// Aplica o valor de redimento recebido na conta
	public void calculaRendimento(double porcentagemRendimento) {

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

}
