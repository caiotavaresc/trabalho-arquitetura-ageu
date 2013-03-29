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
		arquitetura.insereMemoria(113, FuncoesAuxiliares.getNumber(8, 32)); //ADD (R2),(R0)
		arquitetura.insereMemoria(114, FuncoesAuxiliares.getNumber(9, 32)); //ADD (R3),(R0)
		arquitetura.insereMemoria(115, FuncoesAuxiliares.getNumber(10, 32)); //ADD (R4),(R0)
		
		arquitetura.insereMemoria(116, FuncoesAuxiliares.getNumber(11, 32)); //ADD (R1),R0
		arquitetura.insereMemoria(117, FuncoesAuxiliares.getNumber(12, 32)); //ADD (R2),R0
		
		arquitetura.insereMemoria(118, FuncoesAuxiliares.getNumber(15, 32)); //ADD #CONST,R1
		arquitetura.insereMemoria(119, FuncoesAuxiliares.getNumber(32, 32)); //#CONST = 32
		
		arquitetura.insereMemoria(120, FuncoesAuxiliares.getNumber(17, 32)); //ADD R0,R1
		arquitetura.insereMemoria(121, FuncoesAuxiliares.getNumber(18, 32)); //ADD R2,R1
		arquitetura.insereMemoria(122, FuncoesAuxiliares.getNumber(19, 32)); //ADD R3,R1
		arquitetura.insereMemoria(122, FuncoesAuxiliares.getNumber(20, 32)); //ADD R4,R1
		
		arquitetura.insereMemoria(123, FuncoesAuxiliares.getNumber(16, 32)); //ADD #CONST,(R1)
		arquitetura.insereMemoria(124, FuncoesAuxiliares.getNumber(32, 32)); //#CONST = 32
		arquitetura.insereMemoria(125, FuncoesAuxiliares.getNumber(16, 32)); //ADD #CONST,(R1)
		arquitetura.insereMemoria(126, FuncoesAuxiliares.getNumber(32, 32)); //#CONST = 32
		
		arquitetura.insereMemoria(127, FuncoesAuxiliares.getNumber(21, 32)); //ADD (R0),(R1)
		arquitetura.insereMemoria(128, FuncoesAuxiliares.getNumber(22, 32)); //ADD (R2),(R1)
		
		arquitetura.insereMemoria(129, FuncoesAuxiliares.getNumber(29, 32)); //ADD #CONST,R2
		arquitetura.insereMemoria(130, FuncoesAuxiliares.getNumber(51, 32)); //#CONST
		arquitetura.insereMemoria(131, FuncoesAuxiliares.getNumber(31, 32)); //ADD R0,R2
		arquitetura.insereMemoria(132, FuncoesAuxiliares.getNumber(30, 32)); //ADD #CONST,(R2)
		arquitetura.insereMemoria(133, FuncoesAuxiliares.getNumber(63	, 32)); //#CONST
		
		
		Thread ligaComputador = new Thread(arquitetura);
		
		ligaComputador.start();
		
		arquitetura.executaPrograma(FuncoesAuxiliares.getNumber(100, 32)); //Posi��o Inicial pra iniciar o programa igual a 100
	}

}
