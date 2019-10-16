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
import view.Acesso;

public class DataAccess {

	private static MongoClientURI uri; // irá conter a URL do banco de dados, e das tabelas
	private static MongoClient mongo; // servirá para podermos acessar o banco de dados, e as tabelas
	private static DB database; // irá guardar o banco de dados
	private static DBCollection collection; // irá guardar a tabela

	// Este metodo retorna o proximo numero de conta disponivel
	public int NumeroParaContaNova() {
		try {
			connect();
			int nConta = (int) collection.count();
			disconnect();

			return nConta;
		} catch (Exception e) {
			disconnect();
			JOptionPane.showMessageDialog(null, e, "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
			return 1;
		}
	}

	// Metodo utilizado para verificar se um CPF ja esta cadastrado no sistema
	public boolean ValidarCPF(String CPF) {
		try {

			Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
			mongoLogger.setLevel(Level.SEVERE);

			connect();
			BasicDBObject document = new BasicDBObject("CPF", CPF);
			String content = "";
			Cursor cursor = collection.find(document);
			while (cursor.hasNext()) {
				content += cursor.next();
			}

			disconnect();

			if (!content.equals("")) {
				return true;
			}

			return false;

		} catch (Exception e) {
			disconnect();
			JOptionPane.showMessageDialog(null, e, "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	// Metodo para adicionar/criar conta
	public void CriarConta(Conta c) {
		try {
			connect();
			BasicDBObject document = new BasicDBObject("CONTA", c.getCONTA()).append("CPF", c.getCPF())
					.append("NOME", c.getNOME()).append("E-MAIL", c.getEMAIL()).append("SENHA", c.getSENHA())
					.append("SALDO", c.getSALDO());
			collection.insert(document);
			disconnect();

		} catch (Exception e) {
			disconnect();
			JOptionPane.showMessageDialog(null, e, "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	// Metodo de Login - para o usuario acessar sua conta
	public boolean Acessar(String numeroConta, String[] senhaConta) {
		try {

			Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
			mongoLogger.setLevel(Level.SEVERE);

			connect();
			BasicDBObject document = new BasicDBObject("CONTA", numeroConta);
			String content = "";
			Cursor cursor = collection.find(document);
			while (cursor.hasNext()) {
				content += cursor.next();
			}

			disconnect();

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
			disconnect();
			JOptionPane.showMessageDialog(null, e, "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	// Metodo utilizado para buscar os dados da conta do cliente logado
	public Cliente Buscar(String numeroConta) {
		try {

			Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
			mongoLogger.setLevel(Level.SEVERE);

			connect();
			BasicDBObject document = new BasicDBObject("CONTA", numeroConta);
			String content = "";
			Cursor cursor = collection.find(document);
			while (cursor.hasNext()) {
				content += cursor.next();
			}

			disconnect();

			// ---- RETIRANDO A SENHA E ID DO RETORNO ----

			// Jason to Object
			Cliente c = new Cliente();
			Gson gson = new Gson();
			c = gson.fromJson(content, Cliente.class);
			
			c.setSENHA(null);

			// Object to Jason
			String jason = gson.toJson(c);

			// --------------------------------------------

			return c;

		} catch (Exception e) {
			disconnect();
			JOptionPane.showMessageDialog(null, e, "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	// Metodos para conectar e desconectar do Banco de Dados
	// ----------------------------
	@SuppressWarnings("deprecation")
	private void connect() {
		try {

			// Aqui se atribui os valores para as variaveis que se conectam ao banco

			uri = new MongoClientURI(
					"mongodb+srv://thales:iambatman@teste-tngy3.mongodb.net/test?retryWrites=true&w=majority");

			mongo = new MongoClient(uri);
			database = mongo.getDB("APS");
			collection = database.getCollection("Otaner Bank");

		} catch (Exception e) {
			disconnect();
			JOptionPane.showMessageDialog(null, e, "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void disconnect() {
		try {

			// Desconecta do banco
			mongo.close();
			mongo = null;
			database = null;
			collection = null;

		} catch (Exception e) {

		}
	}
	// ----------------------------------------------------------------------------------

	// Insere um documento
	public void Insert_Document(String strDocument) {
		try {
			connect();
			BasicDBObject document = new BasicDBObject("nome", strDocument);
			collection.insert(document);
			disconnect();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Atualiza um documento
	public void Update_Document(String strNewDocument_Update, String strOldDocument_Update) {
		try {
			connect();
			BasicDBObject newDocument = new BasicDBObject("nome", strNewDocument_Update);

			BasicDBObject oldDocument = new BasicDBObject("nome", strOldDocument_Update);

			collection.update(oldDocument, newDocument);
			disconnect();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Deleta um documento
	public void Delete_Document(String strDocument) {
		try {
			connect();
			BasicDBObject document = new BasicDBObject("nome", strDocument);

			collection.remove(document);

			// collection.drop(); isso apaga a collection
			disconnect();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Le todos os documentos
	public String Read_Documents() {
		try {
			connect();
			Cursor cursor = collection.find();
			String content = "";
			while (cursor.hasNext()) {
				content += cursor.next();

			}
			disconnect();
			return content;
		} catch (Exception e) {
			disconnect();
			return "erro: " + e;
		}
	}

	// Le um documento
	public void Read_Document(String strDocument) {
		try {
			connect();
			BasicDBObject document = new BasicDBObject("nome", strDocument);

			Cursor cursor = collection.find(document);
			while (cursor.hasNext()) {
				System.out.println(cursor.next());

			}
			disconnect();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
