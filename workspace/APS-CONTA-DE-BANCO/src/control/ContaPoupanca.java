package control;

public class ContaPoupanca extends Conta {

	public ContaPoupanca(String numeroConta, String nome, String cpf) {
		super(numeroConta, nome, cpf);
		
	}
	
	public ContaPoupanca() {
		
	}
	
	String gerenteResponsavel;
	String rendaGerada;
	String ultimoAcesso;
	
	// Getter e Setters
	public String getGerenteResponsavel() {
		return gerenteResponsavel;
	}

	public void setGerenteResponsavel(String gerenteResponsavel) {
		this.gerenteResponsavel = gerenteResponsavel;
	}

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
	
}
