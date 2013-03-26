package elementsArq;

public class FuncoesAuxiliares {

	public static boolean xor(boolean a, boolean b){
		return (!a && b) || (a && !b);
	}
	
	public static boolean[] getNumber(int number, int tamWord){
		boolean[] result = new boolean[tamWord];
		
		for(int i=0; i<result.length; i++){
			result[i] = false;
		}
		
		boolean negative = false;
		
		if(number < 0){
			negative = true;
			number = -number;
		}
		
		for(int i=(result.length-1); i>=0; i--){
			if(number%2 == 1){
				result[i] = true;
			}
			
			number /= 2;
			
			if(number == 0)
				break;
		}
		
		if(negative){
			result = FuncoesAuxiliares.getNeg(result);
		}
		
		return result;
	}
	
	// Retorna o negativo do número inserido (complemento a 2)
	public static boolean[] getNeg(boolean[] number){

		boolean changeFunction = true;
		boolean[] result = new boolean[number.length];
			
		for(int i = number.length -1; i>=0; i--){
				
			result[i] = (changeFunction && (number[i] && changeFunction)) || (!changeFunction &&(FuncoesAuxiliares.xor(number[i], !changeFunction)));
			//Enquanto changeFunction estiver com o valor true, ele irá copiar o valor em number
			//Quando ele for false, ele passará a inverter o valor recebido
				
			changeFunction = !(number[i]) && changeFunction;
			//Quando o número for igual a 1(true), changeFunction ficará permanentemente false
			//Passando assim sempre a inverter o valor recebido acima
		}
			
		return result;
	}
	
	public static int getIntNumber(boolean[] number){
		
		int value = 0;
		
		for(int i=(number.length-1); i>=0; i--){
			if(number[i]){
				value += Math.pow(2, (number.length -1) - i);
			}
		}
		
		return value;
	}
	
	public static String getStringWord(boolean[] word){
		
		String result = "";
		
		for(int i=0; i<word.length; i++){
			if(word[i]){
				result += "1";
			}
			else{
				result += "0";
			}
		}
		
		
		return result;
	}
}
