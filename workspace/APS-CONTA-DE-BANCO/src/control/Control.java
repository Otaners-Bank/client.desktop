package control;

import model.DataAccess;

public class Control {

	private DataAccess model = new DataAccess();

	// Serve para criar uma nova conta
	public boolean adicionarConta(Cliente c) {
		return model.criarConta(c);
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
	
	// METODO ADICIONADO +
	public void EnviarNotificacao(String emailCliente, String titulo, String mensagem) {
		Email email = new Email();
		email.EnviarMensagem(emailCliente, titulo, mensagem);
	}
	
	public void AtualizarContaPoupanca(String conta) {
		model.AtualizarContaPoupanca(conta);
	}
	
}
