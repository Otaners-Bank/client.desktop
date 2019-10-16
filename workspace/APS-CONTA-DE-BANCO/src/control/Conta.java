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
