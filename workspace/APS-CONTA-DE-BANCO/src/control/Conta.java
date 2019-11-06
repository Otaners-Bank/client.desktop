package control;

import model.DataAccess;

public abstract class Conta {
	
	DataAccess model = new DataAccess();

	public Conta(String numeroConta, String nome, String cpf) {
		setConta(numeroConta);
		setNome(nome);
		setCPF(cpf);
	}

	public Conta() {

	}

	// Atributos
	public String conta = "";
	public String senha = "";
	public String nome = "";
	public String email = "";
	public String CPF = "";
	public String saldo = "";
	// -------------------------------------------------

	// Metodos
	// Serve para efetuar transferencias de um Cliente para outro
	public boolean transferir(String valorTransferencia, String numeroContaQueEnvia, String numeroContaQueRecebe) {
		return model.transferir(valorTransferencia, numeroContaQueEnvia, numeroContaQueRecebe);
	}

	// Serve para para um Cliente sacar dinheiro de sua conta
	public boolean sacar(String valorSaque, String numeroConta) {
		return model.sacar(valorSaque, numeroConta);

	}

	// Serve para para um Cliente depositar dinheiro em sua conta
	public boolean depositar(String valorDeposito, String numeroConta) {
		return model.depositar(valorDeposito, numeroConta);
	}

	// Mostra o saldo que o cliente possui na conta
	public String consultarSaldo() {
		return "";
	}
	// ---------------------------------------------------

	// Getters e Setters
	public String getConta() {
		return conta;
	}

	protected void setConta(String conta) {
		this.conta = conta;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCPF() {
		return CPF;
	}

	protected void setCPF(String CPF) {
		this.CPF = CPF;
	}

	public String getSaldo() {
		return saldo;
	}

	protected void setSaldo(String saldo) {
		this.saldo = saldo;
	}

}
