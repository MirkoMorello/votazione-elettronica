
package Model;

public class Comune {
	private String nome;
	private int popolazione;
	
	public Comune(String nome, int popolazione) {
		this.nome = nome;
		this.popolazione = popolazione;
	}
	
	public String getNome() {
		return nome;
	}
	
	public int getPopolazione() {
		return popolazione;
	}
}
