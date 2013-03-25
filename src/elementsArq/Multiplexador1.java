package elementsArq;

public class Multiplexador1 {
	private boolean[] entrada1;
	private boolean[] entrada2;
	
	public Multiplexador1(int tamWord){
		this.entrada1 = new boolean[tamWord];
		this.entrada2 = new boolean[tamWord];
	}
	
	public void setValues(boolean[] number1, boolean[] number2){
		
		if(!this.validNumber(number1) &&
				!this.validNumber(number2)){
			return;
		}
		
		this.entrada1 = number1;
		this.entrada2 = number2;
	}
	
	public boolean[] getValue(boolean desiredSaida){		
		
		if(desiredSaida)
			return this.entrada1;
		else
			return this.entrada2;
	
	}
	
	private boolean validNumber(boolean[] number){
		if(number.length != this.entrada1.length){
			return false;
		}
		return true;
	}
}
