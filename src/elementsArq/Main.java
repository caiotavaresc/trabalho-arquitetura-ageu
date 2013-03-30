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
		
		
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(81, 32)); //SUB #CONST, R1
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(34, 32)); //#CONST = 34
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(29, 32)); //ADD #CONST, R2
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(3, 32)); //#CONST = 3
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(43, 32)); //ADD #CONST, R3
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(1, 32)); //#CONST = 1
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(84, 32)); //SUB R2,R1
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(85, 32)); //SUB R3,R1
		
		
		
		
		
		Thread ligaComputador = new Thread(arquitetura);
		
		ligaComputador.start();
		
		arquitetura.executaPrograma(FuncoesAuxiliares.getNumber(100, 32)); //Posição Inicial pra iniciar o programa igual a 100
	}

}