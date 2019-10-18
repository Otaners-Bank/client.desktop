package control;

public class ContaEspecial extends ContaCorrente {

	public ContaEspecial(String numeroConta, String nome, String cpf, String gerenteResponsavel) {
		super(numeroConta, nome, cpf);
	}

	public ContaEspecial(String numeroConta, String nome, String cpf, String limite,String gerenteResponsavel) {
		super(numeroConta, nome, cpf);
	}

	String nomeGerenteResponsavel;

	private String getNomeGerenteResponsavel() {
		return nomeGerenteResponsavel;
	}

	private void setNomeGerenteResponsavel(String nomeGerenteResponsavel) {
		this.nomeGerenteResponsavel = nomeGerenteResponsavel;
	}
	
	@Override
	public String consultarSaldo() {
		// TODO Auto-generated method stub
		return super.consultarSaldo();
	}
	

	
}
