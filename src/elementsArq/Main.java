package elementsArq;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArquiteturaCompleta arquitetura = new ArquiteturaCompleta();
		
		int number = 100;
		
		arquitetura.insereMemoria(number, FuncoesAuxiliares.getNumber(601, 32)); //ADD #CONST, R0
		arquitetura.insereMemoria(++number, FuncoesAuxiliares.getNumber(15, 32));// #CONST = 15
		
		Thread ligaComputador = new Thread(arquitetura);
		
		ligaComputador.start();
		
		arquitetura.executaPrograma(FuncoesAuxiliares.getNumber(100, 32)); //Posição Inicial pra iniciar o programa igual a 100
	}

}