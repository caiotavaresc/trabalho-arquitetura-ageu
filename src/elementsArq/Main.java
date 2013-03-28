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
		
		arquitetura.insereMemoria(100, FuncoesAuxiliares.getNumber(1, 32)); //ADD #CONST, R0
		arquitetura.insereMemoria(101, FuncoesAuxiliares.getNumber(20, 32)); //#CONST = 20
		arquitetura.insereMemoria(102, FuncoesAuxiliares.getNumber(1, 32)); //ADD #CONST, R0
		arquitetura.insereMemoria(103, FuncoesAuxiliares.getNumber(40, 32)); //#CONST = 40
		arquitetura.insereMemoria(104, FuncoesAuxiliares.getNumber(2, 32)); //ADD #CONST, (R0)
		arquitetura.insereMemoria(105, FuncoesAuxiliares.getNumber(150, 32)); //#CONST = 150
		arquitetura.insereMemoria(106, FuncoesAuxiliares.getNumber(2, 32)); //ADD #CONST, (R0)
		arquitetura.insereMemoria(107, FuncoesAuxiliares.getNumber(70, 32)); //#CONST = 70
		arquitetura.insereMemoria(108, FuncoesAuxiliares.getNumber(3, 32)); //ADD R1,R0
		arquitetura.insereMemoria(109, FuncoesAuxiliares.getNumber(4, 32)); //ADD R2,R0
		arquitetura.insereMemoria(110, FuncoesAuxiliares.getNumber(5, 32)); //ADD R2,R0
		arquitetura.insereMemoria(111, FuncoesAuxiliares.getNumber(6, 32)); //ADD R3,R0
		arquitetura.insereMemoria(112, FuncoesAuxiliares.getNumber(7, 32)); //ADD (R1),(R0)
		
		Thread ligaComputador = new Thread(arquitetura);
		
		ligaComputador.start();
		
		arquitetura.executaPrograma(FuncoesAuxiliares.getNumber(100, 32)); //Posição Inicial pra iniciar o programa igual a 100
	}

}
