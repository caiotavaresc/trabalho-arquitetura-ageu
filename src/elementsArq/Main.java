package elementsArq;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArquiteturaCompleta arquitetura = new ArquiteturaCompleta();
		
		arquitetura.insereMemoria(100, FuncoesAuxiliares.getNumber(1, 32)); //ADD #CONST, R0
		arquitetura.insereMemoria(101, FuncoesAuxiliares.getNumber(20, 32)); //#CONST = 20
		
		Thread ligaComputador = new Thread(arquitetura);
		
		ligaComputador.start();
		
		arquitetura.executaPrograma(FuncoesAuxiliares.getNumber(100, 32)); //Posição Inicial pra iniciar o programa igual a 100
	}

}
