package model;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import view.Acesso;

public class DataAccess {

	public static MongoClientURI uri; // irá conter a URL do banco de dados, e das tabelas
	public static MongoClient mongo; // servirá para podermos acessar o banco de dados, e as tabelas
	public static DB database; // irá guardar o banco de dados
	public static DBCollection collection; // irá guardar a tabela

	public void CriarConta(String conta, String nome, String cpf, String saldo, String gerenteResponsavel,
			String tipoConta) {
		try {
			connect();
			BasicDBObject document = new BasicDBObject("conta", conta).append("nome", nome).append("cpf", saldo)
					.append("saldo", saldo).append("gerenteResponsavel", gerenteResponsavel)
					.append("tipoConta", tipoConta);
			collection.insert(document);
			disconnect();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	
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
			return false;
		}
	}

	public String Buscar(String numeroConta) {
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
			/*String array[] = new String[7];
			array = content.split(",");
			content = "";
			array[0] = "{";
			array[2] = "";
			
			for (int i = 0; i < array.length; i++) {
				content += array[i];
			}
			*/
			// --------------------------------------------
			
			return content;

		} catch (Exception e) {
			disconnect();
			return null;
		}
	}
	
	// DB / DataBase / Banco de Dados = mesma coisa
	// Collection / Tabela = mesma coisa
	// document / documento = coisas que estão dentro das tabelas

	@SuppressWarnings("deprecation")
	public void connect() {
		try {

			// Aqui se atribui os valores para as variaveis que se conectam ao banco

			uri = new MongoClientURI(
					"mongodb+srv://thales:iambatman@teste-tngy3.mongodb.net/test?retryWrites=true&w=majority");

			mongo = new MongoClient(uri);
			database = mongo.getDB("APS");
			collection = database.getCollection("Otaner Bank");

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void disconnect() {
		try {

			// Desconecta do banco
			mongo.close();
			mongo = null;
			database = null;
			collection = null;

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	
    
 
	
	
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
