package control;

public abstract class Conta {

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
	// --------------------------------------

	// Metodos
	// Saca da conta do cliente
	public boolean sacar(double valorSacado) {
		return true;
	}

	// Deposita na conta do cliente
	public boolean depositar(double valorDepositado) {
		return true;
	}

	// Transfere de uma conta para outra
	public boolean transferir(int numeroContaFonte, int numeroContaDestino, double valor) {
		return true;
	}

	// Mostra o saldo que o cliente possui na conta
	public String consultarSaldo() {
		return "";
	}
	// --------------------------------------

	// Getters e Setters
	public String getConta() {
		return conta;
	}

	private void setConta(String conta) {
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

	private void setCPF(String CPF) {
		this.CPF = CPF;
	}

	public String getSaldo() {
		return saldo;
	}

	private void setSaldo(String saldo) {
		this.saldo = saldo;
	}

}
