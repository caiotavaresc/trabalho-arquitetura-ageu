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
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(30, 32)); //#CONST = 30
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(29, 32)); //ADD #CONST, R2
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(37, 32)); //#CONST = 37
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(43, 32)); //ADD #CONST, R3
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(64, 32)); //#CONST = 64
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(57, 32)); //ADD #CONST, R4
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(400, 32)); //#CONST = 400
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(59, 32)); //ADD R0, R4
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(60, 32)); //ADD R1, R4
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(61, 32)); //ADD R2, R4
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(62, 32)); //ADD R3, R4
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(129, 32)); //SUB R0, R4
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(130, 32)); //SUB R1, R4
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(131, 32)); //SUB R2, R4		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(132, 32)); //SUB R3, R4
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(143, 32)); //MOV R1,R0
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(144, 32)); //MOV R2,R0
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(145, 32)); //MOV R3,R0
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(146, 32)); //MOV R3,R0
		
		
		Thread ligaComputador = new Thread(arquitetura);
		
		ligaComputador.start();
		
		arquitetura.executaPrograma(FuncoesAuxiliares.getNumber(100, 32)); //Posição Inicial pra iniciar o programa igual a 100
	}

}