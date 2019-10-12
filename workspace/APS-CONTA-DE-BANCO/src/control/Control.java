package control;

import com.google.gson.Gson;
import model.DataAccess;

public class Control {
	
	private DataAccess model = new DataAccess();
	
	public void adicionarConta(Conta c) {
		
	}

	public boolean removerConta(int numeroConta) {
		return true;
	}

	public Conta buscarConta(String numeroConta) {
		
		String json = model.Buscar(numeroConta);
		Gson gson = new Gson();
		
		return gson.fromJson(json, Cliente.class);
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
	
	//  Metodos adicionados ao scopo
	public boolean acessarConta(String numeroConta, String[] senhaConta) {
		return model.Acessar(numeroConta, senhaConta);
	}
	
	public String visualizarHistoricoConta(int numeroConta, int senhaConta) {
		return "";
	}
}
