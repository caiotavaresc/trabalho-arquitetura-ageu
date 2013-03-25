package elementsArq;

public class Register {
	private boolean[] value;
	
	public Register(int tamWord){
		boolean[] valor = new boolean[tamWord];
		for(int i=0; i<valor.length; i++){
			valor[i] = false;
		}
		
		this.initiateClass(tamWord, valor);
	}
	
	public Register(int tamWord, boolean[] value){
		this.initiateClass(tamWord, value);
	}
	
	private void initiateClass(int tamWord, boolean[] value){
		if(tamWord != value.length){
			return;
		}
		
		this.value = value;
	}
	
	public boolean setValue(boolean [] value){
		if(this.value.length != value.length){
			return false;
		}
		
		this.value = value;
		
		return true;
	}
	
	public boolean[] getValue(){
		return this.value;
	}
}
