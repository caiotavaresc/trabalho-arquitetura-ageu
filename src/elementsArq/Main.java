package elementsArq;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArquiteturaCompleta arquitetura = new ArquiteturaCompleta();
		
		int number = 15;
		
		System.out.println(FuncoesAuxiliares.getStringWord(FuncoesAuxiliares.getNumber(number, 32)));
		System.out.println(FuncoesAuxiliares.getIntNumber(FuncoesAuxiliares.getNumber(number, 32)));
		
		int x = 100;
		
		arquitetura.insereMemoria(x, FuncoesAuxiliares.getNumber(15, 32)); //ADD #CONST, R1
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(40, 32)); //#CONST = 40
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(57, 32)); //ADD #CONST, R4
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(300, 32)); //#CONST = 300
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(123, 32)); //SUB #CONST, R4
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(64, 32)); //#CONST = 64
		
		
		
		Thread ligaComputador = new Thread(arquitetura);
		
		ligaComputador.start();
		
		arquitetura.executaPrograma(FuncoesAuxiliares.getNumber(100, 32)); //Posição Inicial pra iniciar o programa igual a 100
	}

}