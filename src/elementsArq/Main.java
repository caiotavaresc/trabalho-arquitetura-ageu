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
		
		arquitetura.insereMemoria(x, FuncoesAuxiliares.getNumber(1, 32)); //ADD #CONST, R0
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(200, 32)); //#CONST = 200
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(1, 32)); //ADD #CONST, R0
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(400, 32)); //#CONST = 400
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(15, 32)); //ADD #CONST, R1
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(40, 32)); //#CONST = 40
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(29, 32)); //ADD #CONST, R2
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(5, 32)); //#CONST = 5
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(43, 32)); //ADD #CONST, R3
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(50, 32)); //#CONST = 50
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(57, 32)); //ADD #CONST, R4
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(100, 32)); //#CONST = 100
		
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(69, 32)); //SUB R1, R0
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(70, 32)); //SUB R2, R0
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(71, 32)); //SUB R3, R0
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(72, 32)); //SUB R4, R0
		
		
		
		
		Thread ligaComputador = new Thread(arquitetura);
		
		ligaComputador.start();
		
		arquitetura.executaPrograma(FuncoesAuxiliares.getNumber(100, 32)); //Posição Inicial pra iniciar o programa igual a 100
	}

}