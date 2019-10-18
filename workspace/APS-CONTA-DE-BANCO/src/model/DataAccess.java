package model;

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
import control.Cliente;
import control.Conta;
import control.ContaCorrente;

public class DataAccess {

	private static MongoClientURI uri; // irá conter a URL do banco de dados, e das tabelas
	private static MongoClient mongo; // servirá para podermos acessar o banco de dados, e as tabelas
	private static DB database; // irá guardar o banco de dados
	private static DBCollection collection; // irá guardar a tabela

	// Retorna o numero da proxima conta a ser criada
	public int gerarNumeroParaContaNova() {
		try {
			conectar();
			int nConta = (int) collection.count();
			desconectar();

			return nConta;
		} catch (Exception e) {
			desconectar();
			JOptionPane.showMessageDialog(null, e, "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
			return 1;
		}
	}

	// Retorna true caso este CPF já esteja cadastrado, e false caso não esteja
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

		} catch (Exception e) {
			desconectar();
			JOptionPane.showMessageDialog(null, e, "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	// Retorna todos os dados do cliente no modelo da classe "Cliente"
	public Cliente pesquisarCliente(String numeroConta) {
		try {

			Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
			mongoLogger.setLevel(Level.SEVERE);

			conectar();
			BasicDBObject document = new BasicDBObject("CONTA", numeroConta);
			String content = "";
			Cursor cursor = collection.find(document);
			while (cursor.hasNext()) {
				content += cursor.next();
			}

			desconectar();

			// ---- RETIRANDO A SENHA E ID DO RETORNO ----

			// Jason to Object
			Cliente c = new Cliente();
			Gson gson = new Gson();
			c = gson.fromJson(content, Cliente.class);

			c.setSENHA(null);

			// Object to Jason
			// String jason = gson.toJson(c);

			// --------------------------------------------

			return c;

		} catch (Exception e) {
			desconectar();
			JOptionPane.showMessageDialog(null, e, "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	// Metodo para adicionar/criar conta
	public void criarConta(Conta c) {
		try {
			conectar();
			BasicDBObject document = new BasicDBObject("CONTA", c.getCONTA()).append("CPF", c.getCPF())
					.append("NOME", c.getNOME()).append("E-MAIL", c.getEMAIL()).append("SENHA", c.getSENHA())
					.append("SALDO", c.getSALDO());
			collection.insert(document);
			desconectar();

		} catch (Exception e) {
			desconectar();
			JOptionPane.showMessageDialog(null, e, "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	// Metodo de login/acesso
	public boolean acessarConta(String numeroConta, String[] senhaConta) {
		try {

			Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
			mongoLogger.setLevel(Level.SEVERE);

			conectar();
			BasicDBObject document = new BasicDBObject("CONTA", numeroConta);
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

		} catch (Exception e) {
			desconectar();
			JOptionPane.showMessageDialog(null, e, "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
			return false;
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

		} catch (Exception e) {
			desconectar();
			JOptionPane.showMessageDialog(null, e, "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void desconectar() {
		try {

			// Desconecta do banco
			mongo.close();
			mongo = null;
			database = null;
			collection = null;

		} catch (Exception e) {

		}
	}

}
