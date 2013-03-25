package elementsArq;

public class ALU {
	private boolean[] ladoA;
	private boolean[] ladoB;
	private boolean[] especificaFuncao;
	
	private boolean flagCarry;
	private boolean flagNegative;
	private boolean flagEqual;
	private boolean flagZero;
	private boolean flagLess;
	
	public ALU (int tamWord){
		this.ladoA = new boolean[tamWord];
		this.ladoB = new boolean[tamWord];
		
		this.especificaFuncao = new boolean[5];
	}
	
	public boolean setLados(boolean[] valLadoA, boolean[] valLadoB){
		if(valLadoA.length != valLadoB.length || valLadoA.length != this.ladoA.length){
			return false;
		}
		
		this.ladoA = valLadoA;
		this.ladoB = valLadoB;
		
		return true;
	}
	
	public void setFuncao(boolean[] especificaFuncao){
		if(especificaFuncao.length != this.especificaFuncao.length){
			return;
		}
		
		this.especificaFuncao = especificaFuncao;
	}
	
	public boolean[] getFlags(){
		boolean[] result = new boolean[]{this.flagCarry, this.flagEqual, this.flagLess, this.flagNegative, this.flagZero};
		
		return result;
	}
	
	public boolean[] getResult(){
		int value = FuncoesAuxiliares.getIntNumber(this.especificaFuncao);
		
		switch (value){
			case 0:
				return this.ladoA;
			case 1:
				return this.soma1(this.ladoA);
			case 2:
				return this.subtrai1(this.ladoA);
			case 3:
				return this.ladoB;
			case 4:
				return this.soma1(this.ladoB);
			case 5:
				return this.subtrai1(this.ladoB);
			case 6:
				return this.soma(this.ladoA, this.ladoB);
			case 7:
				return this.subtrai(this.ladoA, this.ladoB);
			case 8:
				return this.subtrai(this.ladoB, this.ladoA);
			case 9:
				return this.and(this.ladoA, this.ladoB);
			case 10:
				return this.or(this.ladoA, this.ladoB);
			case 11:
				return this.clear();
			case 12:
				return this.shl(this.ladoA);
			case 13:
				return this.shl(this.ladoB);
			case 14:
				return this.shr(this.ladoA);
			case 15:
				return this.shr(this.ladoB);
			case 16:
				return this.not(this.ladoA);
			case 17:
				return this.not(this.ladoB);
			default:
				return this.ladoA;
		}
	}
	
	private boolean[] subtrai1(boolean[] number){
		if(number.length != this.ladoA.length){
			return null;
		}
		
		boolean[] numberValueNeg1 = FuncoesAuxiliares.getNumber(-1, this.ladoA.length);
		boolean[] result = this.soma(number, numberValueNeg1);
		
		return result;
	}
	
	private boolean[] soma1(boolean[] number){
		if(number.length != this.ladoA.length){
			return null;
		}
		
		boolean[] numberValue1 = FuncoesAuxiliares.getNumber(1, this.ladoA.length);
		
		boolean[] result = this.soma(number, numberValue1);
		
		return result;
	}
	
	private boolean[] subtrai(boolean[] number1, boolean[] number2){
		if(number1.length != number2.length || number1.length != this.ladoA.length){
			return null;
		}
		
		boolean flagCarryAntes = this.flagCarry;
		
		number2 = this.getNeg(number2);
		
		number2 = this.soma(number1, number2);
		
		this.flagCarry = flagCarryAntes;
		this.flagEqual = this.flagZero;
		this.flagLess = this.flagNegative;
		
		return number2;
	}
	
	// Retorna o negativo do número inserido (complemento a 2)
	private boolean[] getNeg(boolean[] number){

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
	
	private boolean[] and(boolean[] number1, boolean[] number2){
		if(number1.length != number2.length || number1.length != this.ladoA.length){
			return null;
		}
		
		boolean[] result = new boolean[number1.length];
		
		this.flagZero = true;
		
		for(int i=0; i<number1.length; i++){
			result[i] = number1[i] && number2[i];
			this.flagZero = this.flagZero && result[i];
		}
		
		this.flagNegative = result[0];
		
		return result;
	}
	
	private boolean[] or(boolean[] number1, boolean[] number2){
		if(number1.length != number2.length || number1.length != this.ladoA.length){
			return null;
		}
		
		boolean[] result = new boolean[number1.length];
		
		this.flagZero = true;
		
		for(int i=0; i<number1.length; i++){
			result[i] = number1[i] || number2[i];
			this.flagZero = this.flagZero && !(result[i]);
		}
		
		this.flagNegative = result[0];
		
		return result;
	}
	
	private boolean[] shl(boolean[] number){
		if(number.length != this.ladoA.length){
			return null;
		}
		
		boolean[] result = new boolean[this.ladoA.length];
		
		this.flagZero = true;
		
		for(int i= (number.length -2); i>=0; i--){
			result[i] = number[i+1];
			this.flagZero = this.flagZero && !(result[i]);
		}
		
		result[number.length-1] = false;
		this.flagNegative = result[0];
		
		return result;
	}
	
	private boolean[] shr(boolean[] number){
		if(number.length != this.ladoA.length){
			return null;
		}
		
		boolean[] result = new boolean[this.ladoA.length];
		
		this.flagZero = true;
		
		for(int i= (number.length -2); i>=1; i--){
			result[i+1] = number[i];
			this.flagZero = this.flagZero && !(number[i]); 
		}
		
		result[0] = false;
		this.flagNegative = result[0];
		
		return result;
	}
	
	private boolean[] not(boolean[] number){
		if(number.length != this.ladoA.length){
			return null;
		}
		
		boolean[] result = new boolean[this.ladoA.length];
		
		this.flagZero = true;
		
		for(int i=0; i<number.length; i++){
			result[i] = !number[i];
			this.flagZero = this.flagZero && !(result[i]);
		}
		
		this.flagNegative = result[0];
		
		return result;
	}
	
	private boolean[] clear(){
		boolean[] result = new boolean[this.ladoA.length];
		
		for(int i=0; i<this.ladoA.length; i++){
			result[i] = false;
		}
		
		this.flagZero = true;
		this.flagNegative = false;
		
		return result;
	}
	
	private boolean[] soma(boolean[] number1, boolean[] number2){
		if(number1.length != number2.length || number1.length != this.ladoA.length){
			return null;
		}
		
		boolean[] result = new boolean[number1.length];
		
		this.flagCarry = false;
		this.flagZero = true;
		
		boolean xorAB;
		
		for(int i = number1.length -1; i>=0; i--){
			
			xorAB = FuncoesAuxiliares.xor(number1[i], number2[i]);
			// Será usado para várias operações
			
			result[i] = FuncoesAuxiliares.xor(xorAB, this.flagCarry); 
			//O resultado da soma de um somador completo
			
			this.flagZero = this.flagZero&&!(result[i]); 
			// Se aparecer um 1, vai dar false.
			// Levando em consideração, caso seja complemento a 2
			// 11....111 também é 0, logo, nesse caso dará true para
			// flagZero
			// usaremos isso no final para saber se o resultado deu 0
			
			this.flagCarry = (number1[i] && number2[i]) || (xorAB && this.flagCarry); 
			//O resultado do Carry de um somador
			//completo																			
		}
		
		this.flagNegative = result[0];
		// Se for 1 no bit mais alto, então é negativo (Complemento a 2)
		
		return result;
	}
}
