package control;

public class ContaEspecial extends ContaCorrente {

	// Construtores
	public ContaEspecial() {

	}

	public ContaEspecial(String numeroConta, String nome, String cpf, String nomeGerenteResponsavel,
			String emailGerenteResponsavel) {
		super(numeroConta, nome, cpf);
	}

	public ContaEspecial(String numeroConta, String nome, String cpf, String nomeGerenteResponsavel,
			String emailGerenteResponsavel, String limite) {
		super(numeroConta, nome, cpf);
		setNomeGerenteResponsavel(nomeGerenteResponsavel);
		setEmailGerenteResponsavel(emailGerenteResponsavel);
	}

	// Atributos
	String nomeGerenteResponsavel;
	String emailGerenteResponsavel;

	// Getters e Setters
	public String getNomeGerenteResponsavel() {
		return nomeGerenteResponsavel;
	}

	public void setNomeGerenteResponsavel(String nomeGerenteResponsavel) {
		this.nomeGerenteResponsavel = nomeGerenteResponsavel;
	}

	public String getEmailGerenteResponsavel() {
		return emailGerenteResponsavel;
	}

	public void setEmailGerenteResponsavel(String emailGerenteResponsavel) {
		this.emailGerenteResponsavel = emailGerenteResponsavel;
	}

	// Metodos reimplementados
	@Override
	public boolean sacar(String valorSaque, String numeroConta) {
		return super.sacar(valorSaque, numeroConta);
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
