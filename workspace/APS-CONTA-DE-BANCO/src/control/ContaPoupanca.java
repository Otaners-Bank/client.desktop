package control;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ContaPoupanca extends Conta {

	// Construtores
	public ContaPoupanca(String numeroConta, String nome, String cpf) {
		super(numeroConta, nome, cpf);

	}

	public ContaPoupanca() {

	}

	// Atributos
	String rendaGerada;
	String ultimoAcesso;

	// Getter e Setters
	public String getRendaGerada() {
		return rendaGerada;
	}

	public void setRendaGerada(String rendaGerada) {
		this.rendaGerada = rendaGerada;
	}

	public String getUltimoAcesso() {
		return ultimoAcesso;
	}

	public void setUltimoAcesso(String ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}

	// Metodos
	// Aplica o valor de redimento recebido na conta
	public double calculaRendimento(String conta, double porcentagemRendimentoDiario,
			double porcentagemRendimentoMensal, double porcentagemRendimentoAnual, boolean retornarRendaTotalGerada) {

		// Pesquisa todos os dados da conta do cliente
		ContaPoupanca c = model.pesquisarConta_Rendimentos(conta);

		double saldo = Double.parseDouble(c.getSaldo().replace("R$ ", ""));
		double rendaTotalGerada = Double.parseDouble(c.getRendaGerada().replace("R$ ", "").replace(",", "."));
		double rendaGerada = 0;

		// Armazena de todas as maneiras, a data do ultimo acesso do cliente
		String ultimoAcesso = c.getUltimoAcesso();

		if (!ultimoAcesso.equals("null")) {
			int dia_ultimoAcesso = Integer.parseInt(ultimoAcesso.substring(0, 2));
			int mes_ultimoAcesso = Integer.parseInt(ultimoAcesso.substring(3, 5));
			int ano_ultimoAcesso = Integer.parseInt(ultimoAcesso.substring(6, 10));

			// Armazena de todas as maneiras, a data atual
			int dia_atual = Integer.parseInt(new SimpleDateFormat("dd").format(Calendar.getInstance().getTime()));
			int mes_atual = Integer.parseInt(new SimpleDateFormat("MM").format(Calendar.getInstance().getTime()));
			int ano_atual = Integer.parseInt(new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime()));
			// String acessoAtual = String.valueOf(dia_atual + "/" + mes_atual + "/" +
			// ano_atual);

			// System.out.println("Saldo Antigo: " + saldo);
			// System.out.println("Seu ultimo acesso: " + ultimoAcesso);
			// System.out.println("Total de renda ja gerada: " + rendaTotalGerada);

			// Verificacao do tempo que passou ---------------------------------------------
			// Verificacao do ano
			if (ano_atual > ano_ultimoAcesso) {
				// Aplicar a renda anual
				rendaGerada = saldo;
				saldo = efetuarRendimento(porcentagemRendimentoAnual, ano_atual - ano_ultimoAcesso, rendaGerada);
				rendaGerada = saldo - rendaGerada;
			} else if (ano_atual == ano_ultimoAcesso) {
				// Não aplicar renda nenhuma pois ainda está no mesmo ano
			} else {
				// Erro, pois o ano atual não pode ser menor que o ano do ultimo acesso
			}

			// Verificacao do mes
			if (mes_atual > mes_ultimoAcesso) {
				// Aplicar a renda mensal
				rendaGerada = saldo;
				saldo = efetuarRendimento(porcentagemRendimentoMensal, mes_atual - mes_ultimoAcesso, rendaGerada);
				rendaGerada = saldo - rendaGerada;
			} else if (mes_atual == mes_ultimoAcesso) {
				// Não aplicar renda nenhuma pois ainda está no mesmo mes
			} else {
				// Erro, pois o mes atual não pode ser menor que o mes do ultimo acesso
			}

			// Verificacao do dia
			if (dia_atual > dia_ultimoAcesso) {
				// Aplicar a renda diaria
				rendaGerada = saldo;
				saldo = efetuarRendimento(porcentagemRendimentoDiario, dia_atual - dia_ultimoAcesso, rendaGerada);
				rendaGerada = saldo - rendaGerada;
			} else if (dia_atual == dia_ultimoAcesso) {
				// Não aplicar renda nenhuma pois ainda está no mesmo dia
			} else {
				// Erro, pois o dia atual não pode ser menor que o dia do ultimo acesso
			}

			rendaTotalGerada += rendaGerada;

			// System.out.println("");
			// System.out.println("Saldo Novo: " + saldo);
			// System.out.println("Dia de hoje: " + acessoAtual);
			// System.out.println("Renda gerada durante o tempo inativo: " + rendaGerada);
			// System.out.println("Gerou Total: " + rendaTotalGerada);

			model.atualizarConta_Rendimentos(c, "R$ " + String.valueOf(saldo).replace(",", "."),
					"R$ " + String.valueOf(rendaTotalGerada).replace(",", "."));
		}

		if (retornarRendaTotalGerada) {
			return rendaTotalGerada;
		} else {
			return rendaGerada;
		}

	}

	public boolean usandoLimite(String numeroConta, String valorTransicao) {
		return model.validarLimite(numeroConta, valorTransicao);
	}

	private double efetuarRendimento(double porcentagem, int qte, double saldoAtual) {
		if (saldoAtual == 0) {
			return saldoAtual;
		} else {
			double retorno = saldoAtual;

			for (int i = 0; i < qte; i++) {
				retorno += retorno * porcentagem;
				// System.out.println(retorno + "");
			}

			return retorno;
		}
	}

	// Metodos reimplementados
	@Override
	public boolean sacar(String valorSaque, String numeroConta) {
		if (!usandoLimite(numeroConta, valorSaque)) {
			return super.sacar(valorSaque, numeroConta);
		} else {
			return false;
		}
	}

	@Override
	public void setSaldo(String saldo) {
		super.setSaldo(saldo);
	}

}
