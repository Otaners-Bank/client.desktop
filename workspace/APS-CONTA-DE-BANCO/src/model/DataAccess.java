package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;

import control.Conta;
import control.ContaCorrente;
import control.ContaEspecial;
import control.ContaPoupanca;
import control.Control;

public class DataAccess {

	final private static String MENSAGEM_DE_ERRO = "Ocorreu um erro:\n"; // Para padronizar a mensagem de erro
	final private static String TITULO_DE_ERRO = "Erro durante execução"; // Para padronizar a mensagem de erro
	private static MongoClientURI uri; // irá conter a URL do banco de dados, e das tabelas
	private static MongoClient mongo; // servirá para podermos acessar o banco de dados, e as tabelas
	private static DB database; // irá guardar o banco de dados
	private static DBCollection collection; // irá guardar a tabela

	// Determina qual deverá ser o numero para a proxima conta criada
	public int gerarNumeroParaContaNova() {
		try {
			conectar();
			int nConta = (int) collection.count();
			desconectar();

			return nConta;
		} catch (MongoException e) {
			desconectar();
			JOptionPane.showMessageDialog(null, MENSAGEM_DE_ERRO + e, TITULO_DE_ERRO, JOptionPane.ERROR_MESSAGE);
			return 9999;
		}
	}

	// Verifica se o cliente excedeu o limite da sua conta
	public boolean validarLimite(String numeroConta, String valorTransicao) {
		try {
			Conta c = _pesquisarDadosCliente(numeroConta);

			double Saldo = Double.parseDouble((c.getSaldo()).replace("R$ ", ""));
			double Valor = Double.parseDouble(valorTransicao);

			if (Saldo >= Valor) {
				// Acao permitida, pois ainda há mais limite
				return false;
			} else {
				// Acao bloqueada, pois ja esta usando o limite
				return true;
			}
		} catch (MongoException e) {
			JOptionPane.showMessageDialog(null, MENSAGEM_DE_ERRO + e, TITULO_DE_ERRO, JOptionPane.ERROR_MESSAGE);
			return true;
		}
	}

	// Verifica se um CPF já está cadastrado no Banco de Dados ou não
	public boolean validarCPFNoBanco(String CPF) {
		try {

			Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
			mongoLogger.setLevel(Level.SEVERE);

			conectar();
			BasicDBObject document = new BasicDBObject("CPF", CPF);
			String content = "";
			Cursor cursor = collection.find(document);
			while (cursor.hasNext()) {
				content += cursor.next();
			}

			desconectar();

			if (!content.equals("")) {
				return true;
			}

			return false;

		} catch (MongoException e) {
			desconectar();
			JOptionPane.showMessageDialog(null, MENSAGEM_DE_ERRO + e, TITULO_DE_ERRO, JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	// Verifica se uma conta existe no Banco de Dados ou não
	public boolean validarConta(String numeroConta) {
		try {

			Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
			mongoLogger.setLevel(Level.SEVERE);

			conectar();
			BasicDBObject document = new BasicDBObject("conta", numeroConta);
			String content = "";
			Cursor cursor = collection.find(document);
			while (cursor.hasNext()) {
				content += cursor.next();
			}

			desconectar();

			if (!content.equals("") && content.contains("conta") && content.contains("senha")) {
				return true;
			} else {
				return false;
			}

		} catch (MongoException e) {
			desconectar();
			JOptionPane.showMessageDialog(null, MENSAGEM_DE_ERRO + e, TITULO_DE_ERRO, JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	// Retorna todos os dados do cliente menos a senha
	public Conta pesquisarCliente(String numeroConta) {
		try {

			Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
			mongoLogger.setLevel(Level.SEVERE);

			conectar();
			BasicDBObject document = new BasicDBObject("conta", numeroConta);
			String content = "";
			Cursor cursor = collection.find(document);
			while (cursor.hasNext()) {
				content += cursor.next();
			}

			desconectar();

			// Jason to Object

			Gson gson = new Gson();

			if (numeroConta.endsWith("1")) {
				ContaCorrente c = new ContaCorrente();
				c = gson.fromJson(content, ContaCorrente.class);
				c.setSenha(null);
				return c;
			} else if (numeroConta.endsWith("2")) {
				ContaPoupanca c = new ContaPoupanca();
				c = gson.fromJson(content, ContaPoupanca.class);
				c.setSenha(null);
				return c;
			} else if (numeroConta.endsWith("3")) {
				ContaEspecial c = new ContaEspecial();
				c = gson.fromJson(content, ContaEspecial.class);
				c.setSenha(null);
				return c;
			}

			return null;
		} catch (MongoException e) {
			desconectar();
			JOptionPane.showMessageDialog(null, MENSAGEM_DE_ERRO + e, TITULO_DE_ERRO, JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	// Criar uma nova conta no Banco de Dados
	public boolean criarConta(Conta c) {
		try {
			conectar();
			BasicDBObject document = new BasicDBObject("conta", c.getConta()).append("CPF", c.getCPF())
					.append("nome", c.getNome()).append("email", c.getEmail()).append("senha", c.getSenha())
					.append("saldo", c.getSaldo());

			switch (c.getConta().charAt((c.getConta().length() - 1))) {
			case '1':
				// Não faz nada
				break;

			case '2':
				// Adiciona os campos "ultimoAcesso" e "rendaGerada"
				document.append("ultimoAcesso", "null").append("rendaGerada", "R$ 0,00");
				break;

			case '3':
				// Adiciona os campos "nomeGerenteResponsavel", "emailGerenteResponsavel"
				ContaEspecial contaEspecial = (ContaEspecial) c;
				document.append("nomeGerenteResponsavel", contaEspecial.getNomeGerenteResponsavel())
						.append("emailGerenteResponsavel", contaEspecial.getEmailGerenteResponsavel());
				break;

			default:
				break;
			}

			collection.insert(document);
			desconectar();
			return true;

		} catch (MongoException e) {
			desconectar();
			JOptionPane.showMessageDialog(null, MENSAGEM_DE_ERRO + e, TITULO_DE_ERRO, JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	// Valida as credencias da conta do cliente para liberar ou não o acesso
	public boolean acessarConta(String numeroConta, String[] senhaConta) {
		try {

			Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
			mongoLogger.setLevel(Level.SEVERE);

			conectar();
			BasicDBObject document = new BasicDBObject("conta", numeroConta);
			String content = "";
			Cursor cursor = collection.find(document);
			while (cursor.hasNext()) {
				content += cursor.next();
			}

			desconectar();

			for (int i = 0; i < senhaConta.length; i++) {

				if (!content.equals("")) {
					if (content.contains(numeroConta) && content.contains(senhaConta[i])) {
						content = null;
						return true;
					}
				}

			}

			return false;

		} catch (MongoException e) {
			desconectar();
			JOptionPane.showMessageDialog(null, MENSAGEM_DE_ERRO + e, TITULO_DE_ERRO, JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	// Efetua a operação de saque na conta do cliente
	public boolean sacar(String valorSaque, String numeroConta, boolean transferindo) {
		try {

			BasicDBObject document = new BasicDBObject("conta", numeroConta);
			String content = "";

			conectar();
			Cursor cursor = collection.find(document);
			while (cursor.hasNext()) {
				content += cursor.next();
			}

			// Jason to Object
			Gson gson = new Gson();

			if (numeroConta.endsWith("1")) {
				Conta c = null;
				c = gson.fromJson(content, ContaCorrente.class);

				double valorAntigo = Double.parseDouble(c.getSaldo().replace("R$ ", "").replace(",", "."));
				double valorNovo = valorAntigo - (Double.parseDouble(valorSaque.replace("R$ ", "").replace(",", ".")));

				// new value
				BasicDBObject newDocument = new BasicDBObject("conta", c.getConta()).append("CPF", c.getCPF())
						.append("nome", c.getNome()).append("email", c.getEmail()).append("senha", c.getSenha())
						.append("saldo", "R$ " + valorNovo);

				collection.update(document, newDocument);
				desconectar();

				if (!transferindo) {
					Control control = new Control();
					control.EnviarNotificacao(c.getEmail(), "Novo saque na conta " + c.getConta(), "Olá " + c.getNome()
							+ ",\n" + "O saque de R$ " + valorSaque + " foi concluido com sucesso em sua conta !!! ");
				}

			} else if (numeroConta.endsWith("2")) {
				ContaPoupanca c = null;
				c = gson.fromJson(content, ContaPoupanca.class);

				double valorAntigo = Double.parseDouble(c.getSaldo().replace("R$ ", "").replace(",", "."));
				double valorNovo = valorAntigo - (Double.parseDouble(valorSaque.replace("R$ ", "").replace(",", ".")));

				// new value
				BasicDBObject newDocument = new BasicDBObject("conta", c.getConta()).append("CPF", c.getCPF())
						.append("nome", c.getNome()).append("email", c.getEmail()).append("senha", c.getSenha())
						.append("saldo", "R$ " + valorNovo).append("ultimoAcesso", c.getUltimoAcesso())
						.append("rendaGerada", c.getRendaGerada());

				collection.update(document, newDocument);
				desconectar();

				if (!transferindo) {
					Control control = new Control();
					control.EnviarNotificacao(c.getEmail(), "Novo saque na conta " + c.getConta(), "Olá " + c.getNome()
							+ ",\n" + "O saque de R$ " + valorSaque + " foi concluido com sucesso em sua conta !!! ");
				}

			} else if (numeroConta.endsWith("3")) {

				ContaEspecial c = null;
				c = gson.fromJson(content, ContaEspecial.class);

				double valorAntigo = Double.parseDouble(c.getSaldo().replace("R$ ", "").replace(",", "."));
				double valorNovo = valorAntigo - (Double.parseDouble(valorSaque.replace("R$ ", "").replace(",", ".")));

				// new value
				BasicDBObject newDocument = new BasicDBObject("conta", c.getConta()).append("CPF", c.getCPF())
						.append("nome", c.getNome()).append("email", c.getEmail()).append("senha", c.getSenha())
						.append("saldo", "R$ " + valorNovo)
						.append("nomeGerenteResponsavel", c.getNomeGerenteResponsavel())
						.append("emailGerenteResponsavel", c.getEmailGerenteResponsavel());

				collection.update(document, newDocument);
				desconectar();

				if (!transferindo) {
					Control control = new Control();
					control.EnviarNotificacao(c.getEmail(), "Novo saque na conta " + c.getConta(), "Olá " + c.getNome()
							+ ",\n" + "O saque de R$ " + valorSaque + " foi concluido com sucesso em sua conta !!! ");
				}
			}

			return true;

		} catch (MongoException e) {
			JOptionPane.showMessageDialog(null, MENSAGEM_DE_ERRO + e, TITULO_DE_ERRO, JOptionPane.ERROR_MESSAGE);
			desconectar();
			return false;
		}
	}

	// Efetua a operação de depósito na conta do cliente
	public boolean depositar(String valorDeposito, String numeroConta, boolean transferindo) {

		try {

			BasicDBObject document = new BasicDBObject("conta", numeroConta);
			String content = "";

			conectar();
			Cursor cursor = collection.find(document);
			while (cursor.hasNext()) {
				content += cursor.next();
			}

			if (numeroConta.endsWith("1")) {
				Conta c = null;
				// Jason to Object
				Gson gson = new Gson();
				c = gson.fromJson(content, ContaCorrente.class);

				double valorAntigo = Double.parseDouble(c.getSaldo().replace("R$ ", "").replace(",", "."));
				double valorNovo = (Double.parseDouble(valorDeposito.replace("R$ ", "").replace(",", ".")))
						+ valorAntigo;

				// new value
				BasicDBObject newDocument = new BasicDBObject("conta", c.getConta()).append("CPF", c.getCPF())
						.append("nome", c.getNome()).append("email", c.getEmail()).append("senha", c.getSenha())
						.append("saldo", "R$ " + valorNovo);

				collection.update(document, newDocument);
				desconectar();

				if (!transferindo) {
					Control control = new Control();
					control.EnviarNotificacao(c.getEmail(), "Novo depósito na conta " + c.getConta(),
							"Olá " + c.getNome() + ",\n" + "O depósito de R$ " + valorDeposito
									+ " foi concluido com sucesso em sua conta !!! ");
				}

			} else if (numeroConta.endsWith("2")) {

				ContaPoupanca c = null;
				// Jason to Object
				Gson gson = new Gson();
				c = gson.fromJson(content, ContaPoupanca.class);

				double valorAntigo = Double.parseDouble(c.getSaldo().replace("R$ ", "").replace(",", "."));
				double valorNovo = (Double.parseDouble(valorDeposito.replace("R$ ", "").replace(",", ".")))
						+ valorAntigo;

				// new value
				BasicDBObject newDocument = new BasicDBObject("conta", c.getConta()).append("CPF", c.getCPF())
						.append("nome", c.getNome()).append("email", c.getEmail()).append("senha", c.getSenha())
						.append("saldo", "R$ " + valorNovo).append("ultimoAcesso", c.getUltimoAcesso())
						.append("rendaGerada", c.getRendaGerada());

				collection.update(document, newDocument);
				desconectar();

				if (!transferindo) {
					Control control = new Control();
					control.EnviarNotificacao(c.getEmail(), "Novo depósito na conta " + c.getConta(),
							"Olá " + c.getNome() + ",\n" + "O depósito de R$ " + valorDeposito
									+ " foi concluido com sucesso em sua conta !!! ");
				}

			} else if (numeroConta.endsWith("3")) {
				ContaEspecial c = null;
				// Jason to Object
				Gson gson = new Gson();
				c = gson.fromJson(content, ContaEspecial.class);

				double valorAntigo = Double.parseDouble(c.getSaldo().replace("R$ ", "").replace(",", "."));
				double valorNovo = (Double.parseDouble(valorDeposito.replace("R$ ", "").replace(",", ".")))
						+ valorAntigo;

				// new value
				BasicDBObject newDocument = new BasicDBObject("conta", c.getConta()).append("CPF", c.getCPF())
						.append("nome", c.getNome()).append("email", c.getEmail()).append("senha", c.getSenha())
						.append("saldo", "R$ " + valorNovo)
						.append("nomeGerenteResponsavel", c.getNomeGerenteResponsavel())
						.append("emailGerenteResponsavel", c.getEmailGerenteResponsavel());

				collection.update(document, newDocument);
				desconectar();

				if (!transferindo) {
					Control control = new Control();
					control.EnviarNotificacao(c.getEmail(), "Novo depósito na conta " + c.getConta(),
							"Olá " + c.getNome() + ",\n" + "O depósito de R$ " + valorDeposito
									+ " foi concluido com sucesso em sua conta !!! ");
				}
			}

			return true;

		} catch (MongoException e) {
			JOptionPane.showMessageDialog(null, MENSAGEM_DE_ERRO + e, TITULO_DE_ERRO, JOptionPane.ERROR_MESSAGE);
			desconectar();
			return false;
		}
	}

	// Efetua a operação de tranferencia para outra conta
	public boolean transferir(String valorTransferencia, String numeroContaQueEnvia, String numeroContaQueRecebe) {

		try {

			// Valor que será retirado da conta1
			String valorSaque = (valorTransferencia);
			// Valor que será colocado da conta2
			String valorDeposito = (valorTransferencia);

			// Busca a primeira conta (A conta que está enviando dinheiro)
			Conta c1 = _pesquisarDadosCliente(numeroContaQueEnvia);

			// Busca a segunda conta (A conta que está recebendo dinheiro)
			Conta c2 = _pesquisarDadosCliente(numeroContaQueRecebe);

			if (c1 == null || c2 == null) {
				return false;
			} else {
				if (sacar(valorSaque, numeroContaQueEnvia, true)) {
					// Retira o dinheiro da conta1
					if (depositar(valorDeposito, numeroContaQueRecebe, true)) {
						// Tenta depositar o dinheiro na conta2

						// Se conseguir, ele notifica ambos os clientes
						Control control = new Control();
						control.EnviarNotificacao(c2.getEmail(), "Nova transferencia para sua conta " + c2.getConta(),
								"Olá " + c2.getNome() + ",\n" + c1.getNome() + "( conta " + c1.getConta()
										+ " ) acabou de te transferir R$ " + valorTransferencia + " !!!");

						control.EnviarNotificacao(c1.getEmail(), "Nova transferencia de sua conta " + c1.getConta(),
								"Olá " + c1.getNome() + ",\n" + c2.getNome() + "( conta " + c2.getConta()
										+ " ) acabou de receber R$ " + valorTransferencia + " de você !!!");

						// E retorna true
						return true;
					} else {
						// Se ele não conseguir depositar na conta2:
						if (depositar(valorDeposito, numeroContaQueRecebe, true)) {
							// Caso ele não consiga depoistar na conta2, o dinheiro retorna a conta1
						}
						return false; // e retorna false
					}

				} else {
					// Caso não consiga sacar o dinheiro da conta1 ele para e retorna false
					return false;
				}
			}

		} catch (MongoException e) {
			JOptionPane.showMessageDialog(null, MENSAGEM_DE_ERRO + e, TITULO_DE_ERRO, JOptionPane.ERROR_MESSAGE);
			desconectar();
			return false;
		}
	}

	// Atualiza o campo "ultimoAcesso" em Contas Poupanças
	public void AtualizarContaPoupanca(String conta) {

		try {
			ContaPoupanca c = (ContaPoupanca) _pesquisarDadosCliente(conta);

			// dados atuais
			BasicDBObject conteudoAtual = new BasicDBObject("conta", c.getConta()).append("CPF", c.getCPF())
					.append("nome", c.getNome()).append("email", c.getEmail()).append("senha", c.getSenha())
					.append("saldo", c.getSaldo()).append("ultimoAcesso", c.getUltimoAcesso())
					.append("rendaGerada", c.getRendaGerada());

			// dados novos
			BasicDBObject conteudoNovo = new BasicDBObject("conta", c.getConta()).append("CPF", c.getCPF())
					.append("nome", c.getNome()).append("email", c.getEmail()).append("senha", c.getSenha())
					.append("saldo", c.getSaldo()).append("ultimoAcesso", c.getUltimoAcesso())
					.append("rendaGerada", RetornarData());

			conectar();
			collection.update(conteudoAtual, conteudoNovo);
			desconectar();

		} catch (MongoException e) {
			JOptionPane.showMessageDialog(null, MENSAGEM_DE_ERRO + e, TITULO_DE_ERRO, JOptionPane.ERROR_MESSAGE);
			desconectar();
		}
	}

	// Metodo auxiliar do sistema
	// Retorna todos os dados do cliente para poder edita-los em outras funcoes
	public Conta _pesquisarDadosCliente(String numeroConta) {
		try {

			Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
			mongoLogger.setLevel(Level.SEVERE);

			conectar();
			BasicDBObject document = new BasicDBObject("conta", numeroConta);
			String content = "";
			Cursor cursor = collection.find(document);
			while (cursor.hasNext()) {
				content += cursor.next();
			}

			desconectar();

			// Jason to Object

			Gson gson = new Gson();

			if (numeroConta.endsWith("1")) {
				ContaCorrente c = new ContaCorrente();
				c = gson.fromJson(content, ContaCorrente.class);
				return c;
			} else if (numeroConta.endsWith("2")) {
				ContaPoupanca c = new ContaPoupanca();
				c = gson.fromJson(content, ContaPoupanca.class);
				return c;
			} else if (numeroConta.endsWith("3")) {
				ContaEspecial c = new ContaEspecial();
				c = gson.fromJson(content, ContaEspecial.class);
				return c;
			}

			return null;
		} catch (MongoException e) {
			desconectar();
			JOptionPane.showMessageDialog(null, MENSAGEM_DE_ERRO + e, TITULO_DE_ERRO, JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	// Metodo auxiliar do sistema
	// Este metodo retorna a hora atual
	public String RetornarData() {

		try {
			// Retona a data atual para os Logs
			String data = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(Calendar.getInstance().getTime());
			return data;

		} catch (MongoException e) {
			JOptionPane.showMessageDialog(null, MENSAGEM_DE_ERRO + e, TITULO_DE_ERRO, JOptionPane.ERROR_MESSAGE);
			return "null";
		}

	}

	// Metodos para conectar e desconectar do Banco de Dados
	// -----------------------------------------------------
	@SuppressWarnings("deprecation")
	private void conectar() {
		try {

			// Aqui se atribui os valores para as variaveis que se conectam ao banco

			uri = new MongoClientURI(
					"mongodb+srv://thales:iambatman@teste-tngy3.mongodb.net/test?retryWrites=true&w=majority");

			mongo = new MongoClient(uri);
			database = mongo.getDB("APS");
			collection = database.getCollection("Otaner Bank");

		} catch (MongoException e) {
			desconectar();
			JOptionPane.showMessageDialog(null, MENSAGEM_DE_ERRO + e, TITULO_DE_ERRO, JOptionPane.ERROR_MESSAGE);
		}
	}

	private void desconectar() {
		try {

			// Desconecta do banco
			mongo.close();
			mongo = null;
			database = null;
			collection = null;

		} catch (MongoException e) {
			JOptionPane.showMessageDialog(null, MENSAGEM_DE_ERRO + e, TITULO_DE_ERRO, JOptionPane.ERROR_MESSAGE);
		}
	}
	// -----------------------------------------------------
}
