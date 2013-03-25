package elementsArq;

public class Multiplexador3 {
	private boolean[] entrada1;
	private boolean[] entrada2;
	private boolean[] entrada3;
	private boolean[] entrada4;
	private boolean[] entrada5;
	private boolean[] entrada6;
	private boolean[] entrada7;
	private boolean[] entrada8;
	
	public Multiplexador3(int tamWord){
		this.entrada1 = new boolean[tamWord];
		this.entrada2 = new boolean[tamWord];
		this.entrada3 = new boolean[tamWord];
		this.entrada4 = new boolean[tamWord];
		this.entrada5 = new boolean[tamWord];
		this.entrada6 = new boolean[tamWord];
		this.entrada7 = new boolean[tamWord];
		this.entrada8 = new boolean[tamWord];
	}
	
	public void setValues(boolean[] number1, boolean[] number2, boolean[] number3,
							boolean[] number4, boolean[] number5, boolean[] number6,
							boolean[] number7, boolean[] number8){
		
		if(!this.validNumber(number1) &&
				!this.validNumber(number2) &&
				!this.validNumber(number3) &&
				!this.validNumber(number4) &&
				!this.validNumber(number5) &&
				!this.validNumber(number6) &&
				!this.validNumber(number7) &&
				!this.validNumber(number8)){
			return;
		}
		
		this.entrada1 = number1;
		this.entrada2 = number2;
		this.entrada3 = number3;
		this.entrada4 = number4;
		this.entrada5 = number5;
		this.entrada6 = number6;
		this.entrada7 = number7;
		this.entrada8 = number8;
		
	}
	
	public boolean[] getValue(boolean[] desiredSaida){
		if(desiredSaida.length != 3){
			return null;
		}
		
		int number = FuncoesAuxiliares.getIntNumber(desiredSaida);
		
		switch(number){
			case 0:
				return this.entrada1;
			case 1:
				return this.entrada2;
			case 2:
				return this.entrada3;
			case 3:
				return this.entrada4;
			case 4:
				return this.entrada5;
			case 5:
				return this.entrada6;
			case 6:
				return this.entrada7;
			case 7:
				return this.entrada8;
		}
		
		return null;
	}
	
	private boolean validNumber(boolean[] number){
		if(number.length != this.entrada1.length){
			return false;
		}
		return true;
	}
}
