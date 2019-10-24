package Jason;

import com.google.gson.Gson;

public class Serialize_Deserialize {

	static ClasseModelo modelo = new ClasseModelo();

	public static void main(String[] args) {

		// System.out.println( Read_Document_And_Return("aaaaaaaaaa") );

		// Obj to JSON
		modelo.nome = "Thales";
		modelo.idade = 18;
		modelo.genero = 'M';
		modelo.ativo = true;

		Gson gson = new Gson();
		String jsonStr = gson.toJson(modelo);

		System.out.println("Teste1:\n" + jsonStr);
		// -----------------------------------------------

		// JSON to Obj
		jsonStr = "{\"nome\":\"velha\",\"idade\":\"99\",\"genero\":\"F\",\"ativo\":\"false\"}";
		modelo = gson.fromJson(jsonStr, ClasseModelo.class); // deserializes json into target2

		System.out.println("\nTeste2:\n" + modelo.nome + "\n" + modelo.idade + "\n" + modelo.genero + "\n" + modelo.ativo);
		// -----------------------------------------------

	}

}
