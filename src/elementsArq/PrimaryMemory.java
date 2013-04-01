package elementsArq;

public class PrimaryMemory {
	private boolean[][] memory;
	private int tamMemory;
	private int tamWord;
	
	public PrimaryMemory(){
		this.initiateClass(1024, 32);
		
	}
	
	public PrimaryMemory(int tamMemory){
		this.initiateClass(tamMemory, 32);
	}
	
	public PrimaryMemory(int tamMemory, int tamWord){
		this.initiateClass(tamMemory, tamWord);
	}
	
	private void initiateClass(int tamMemory, int tamWord){
		this.memory = new boolean[tamMemory][tamWord];
		this.tamMemory = tamMemory;
		this.tamWord = tamWord;
		
		for(int i=0; i<this.tamMemory; i++){
			for(int j=0; j<this.tamWord; j++){
				this.memory[i][j] = false;
			}
		}
		
	}
	
	public boolean setWord(int position, boolean[] word){
		if(position >= tamMemory){
			return false;
		}
		if(word.length != tamWord){
			return false;
		}
		
		this.memory[position] = word;
		
		return true;
	}
	
	public boolean[] getWord(int position){
		if(position >= this.tamMemory ||
				position < 0){
			return FuncoesAuxiliares.getNumber(0, this.tamWord);
		}
		
		return this.memory[position];
	}
}
