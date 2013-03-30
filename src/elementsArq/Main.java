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
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(1, 32)); //ADD #CONST, R0
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(40, 32)); //#CONST = 40
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(43, 32)); //ADD #CONST, R3
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(300, 32)); //#CONST = 300
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(109, 32)); //SUB #CONST,R3
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(55, 32)); //SUB #CONST,55
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(111, 32)); //SUB R0,R3
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(112, 32)); //SUB R1,R3
		
		
		
		
		
		
		
		
		
		
		Thread ligaComputador = new Thread(arquitetura);
		
		ligaComputador.start();
		
		arquitetura.executaPrograma(FuncoesAuxiliares.getNumber(100, 32)); //Posição Inicial pra iniciar o programa igual a 100
	}

}