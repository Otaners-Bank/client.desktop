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
	public String TIPOCONTA = "";

	// Getters e Setters

	private String getCONTA() {
		return CONTA;
	}

	private void setCONTA(String cONTA) {
		CONTA = cONTA;
	}

	private String getSENHA() {
		return SENHA;
	}

	private void setSENHA(String sENHA) {
		SENHA = sENHA;
	}

	private String getNOME() {
		return NOME;
	}

	private void setNOME(String nOME) {
		NOME = nOME;
	}

	private String getEMAIL() {
		return EMAIL;
	}

	private void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	private String getCPF() {
		return CPF;
	}

	private void setCPF(String cPF) {
		CPF = cPF;
	}

	private String getSALDO() {
		return SALDO;
	}

	private void setSALDO(String sALDO) {
		SALDO = sALDO;
	}

	private String getTIPOCONTA() {
		return TIPOCONTA;
	}

	private void setTIPOCONTA(String tIPOCONTA) {
		TIPOCONTA = tIPOCONTA;
	}

}
