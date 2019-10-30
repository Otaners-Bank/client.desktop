package control;

import model.DataAccess;

public class Control {

	private DataAccess model = new DataAccess();

	// Serve para criar uma nova conta
	public void adicionarConta(Cliente c) {
		model.criarConta(c);
	}

	// N�O SER� FEITO -
	public boolean removerConta(int numeroConta) {
		return true;
	}

	// N�O SER� FEITO -
	public String buscarContasEspeciais() {
		return "";
	}

	// N�O SER� FEITO -
	public String buscarClientesUsandoLimite() {
		return "";
	}

	// Serve para busca todas as informacoes do Cliente que acessar
	public Conta buscarConta(String numeroConta) {
		return model.pesquisarCliente(numeroConta);
	}

	// Serve para efetuar transferencias de um Cliente para outro
	public boolean transferir(int numeroContaFonte, int numeroContaDestino, double valor) {
		return true;
	}

	// Serve para para um Cliente sacar dinheiro de sua conta
	public boolean sacar(String valorSaque, String numeroConta ) {
		return model.sacar(valorSaque, numeroConta);

	}

	// Serve para para um Cliente depositar dinheiro em sua conta
	public boolean depositar(String valorDeposito, String numeroConta) {
		return model.depositar(valorDeposito, numeroConta);
	}

	// N�O SER� FEITO -
	public String listarContas() {
		return "";
	}

	// METODO ADICIONADO +
	// Serve para um Cliente consultar seu saldo na conta
	public String consultar() {
		return "";
	}

	// METODO ADICIONADO +
	// Serve para um Cliente acessar sua conta
	public boolean acessarConta(String numeroConta, String[] senhaConta) {
		return model.acessarConta(numeroConta, senhaConta);
	}

	// METODO ADICIONADO +
	// Serve para gerar um historico de saque, tranferencias, deposito e etc
	public String visualizarHistoricoConta(int numeroConta, int senhaConta) {
		return "";
	}

	// METODO ADICIONADO +
	// Serve para para determinar o numero de uma nova conta
	public int numeroContaDisponivel() {
		return model.gerarNumeroParaContaNova();
	}

	// METODO ADICIONADO +
	// Serve para verificar se o CPF de um cliente j� est� cadastrado
	public boolean BuscarCPF(String CPF) {
		return model.validarCPFNoBanco(CPF);
	}
}
