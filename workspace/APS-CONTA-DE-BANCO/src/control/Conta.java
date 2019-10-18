package control;

public abstract class Conta {

	public Conta(String numeroConta, String nome, String cpf) {
		setCONTA(numeroConta);
		setNOME(nome);
		setCPF(cpf);
	}

	public Conta() {

	}

	// Atributos
	public String CONTA = "";
	public String SENHA = "";
	public String NOME = "";
	public String EMAIL = "";
	public String CPF = "";
	public String SALDO = "";
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
	public String getCONTA() {
		return CONTA;
	}

	private void setCONTA(String cONTA) {
		CONTA = cONTA;
	}

	public String getSENHA() {
		return SENHA;
	}

	public void setSENHA(String sENHA) {
		SENHA = sENHA;
	}

	public String getNOME() {
		return NOME;
	}

	public void setNOME(String nOME) {
		NOME = nOME;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getCPF() {
		return CPF;
	}

	private void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getSALDO() {
		return SALDO;
	}

	private void setSALDO(String sALDO) {
		SALDO = sALDO;
	}

}
