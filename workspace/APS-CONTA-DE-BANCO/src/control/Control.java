package control;

import model.DataAccess;

public class Control {

	// Instancia para a classe que controla as conexoes com o Banco de Dados
	private DataAccess model = new DataAccess();

	// Cria uma nova Conta
	public boolean adicionarConta(Conta c) {
		return model.criarConta(c);
	}

	// NÃO SERÁ FEITO -
	public boolean removerConta(int numeroConta) {
		return true;
	}

	// NÃO SERÁ FEITO -
	public String buscarContasEspeciais() {
		return "";
	}

	// NÃO SERÁ FEITO -
	public String buscarClientesUsandoLimite() {
		return "";
	}

	// Serve para busca todas as informacoes do Cliente menos a senha
	public Conta buscarConta(String numeroConta) {
		return model.pesquisarCliente(numeroConta);
	}

	// NÃO SERÁ FEITO -
	public String listarContas() {
		return "";
	}

	// METODO ADICIONADO +
	// Valida o acesso do cliente no sistema
	public boolean acessarConta(String numeroConta, String[] senhaConta) {
		return model.acessarConta(numeroConta, senhaConta);
	}

	// METODO ADICIONADO +
	// Retorna um numero para a criacao de uma nova conta
	public int numeroContaDisponivel() {
		return model.gerarNumeroParaContaNova();
	}

	// METODO ADICIONADO +
	// Verifica se um CPF já está cadastrado no sistema
	public boolean BuscarCPF(String CPF) {
		return model.validarCPFNoBanco(CPF);
	}

	// METODO ADICIONADO +
	// Envia uma mensagem no e-mail do cliente para notifica-lo
	public void EnviarNotificacao(String emailCliente, String titulo, String mensagem) {
		Email email = new Email();
		email.EnviarMensagem(emailCliente, titulo, mensagem);
	}

	// METODO ADICIONADO +
	// Atualiza o campo "ultimoAcesso" em Contas Poupanças
	public void AtualizarContaPoupanca(String conta) {
		model.AtualizarContaPoupanca(conta);
	}

	// METODO ADICIONADO +
	// Verifica se uma conta existe ou não
	public boolean validarConta(String numeroConta) {
		return model.validarConta(numeroConta);
	}

}
