package elementsArq;

public class TestePrimaryMemory {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PrimaryMemory memory;
		
		memory = new PrimaryMemory();
		
		int tamWord = 32;
		boolean[] word = new boolean[tamWord];
		
		for(int i=0; i<word.length; i++){
			double number = Math.random();
			
			if(number >= 0.5){
				word[i] = true;
			}
			else{
				word[i] = false;
			}
		}
		
		String result = FuncoesAuxiliares.getStringWord(word);
		
		System.out.println(result);
		
		memory.setWord(7, word);
		
		word = memory.getWord(7);
		
		result = FuncoesAuxiliares.getStringWord(word);
		System.out.println(result);
	}
	 
}



