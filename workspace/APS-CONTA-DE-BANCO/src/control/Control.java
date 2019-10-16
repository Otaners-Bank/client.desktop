package control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.DataAccess;

public class Control {

	private DataAccess model = new DataAccess();

	public void adicionarConta(Cliente c) {

		model.CriarConta(c);

	}

	public boolean removerConta(int numeroConta) {
		return true;
	}

	public Cliente buscarConta(String numeroConta) {

		return model.Buscar(numeroConta);
	}

	public boolean transferirValor(int numeroContaFonte, int numeroContaDestino, double valor) {
		return true;
	}

	public boolean sacarValor(int numeroConta, double valorSacado) {
		return true;
	}

	public boolean depositarValor(int numeroConta, double valorDepositado) {
		return true;
	}

	// Metodos que não serão implementados:
	public String listarContas() {
		return "";
	}

	public String buscarContasEspeciais() {
		return "";
	}

	public String buscarClientesUsandoLimite() {
		return "";
	}

	// Metodos adicionados ao scopo
	public boolean acessarConta(String numeroConta, String[] senhaConta) {
		return model.Acessar(numeroConta, senhaConta);
	}

	public String visualizarHistoricoConta(int numeroConta, int senhaConta) {
		return "";
	}

	public int numeroContaDisponivel() {
		return model.NumeroParaContaNova();
	}

	public boolean BuscarCPF(String CPF) {
		return model.ValidarCPF(CPF);
	}
}
