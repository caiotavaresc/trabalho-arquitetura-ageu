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
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(20, 32)); //#CONST = 20
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(1, 32)); //ADD #CONST, R0
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(40, 32)); //#CONST = 40
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(2, 32)); //ADD #CONST, (R0)
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(150, 32)); //#CONST = 150
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(2, 32)); //ADD #CONST, (R0)
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(70, 32)); //#CONST = 70
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(3, 32)); //ADD R1,R0
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(4, 32)); //ADD R2,R0
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(5, 32)); //ADD R2,R0
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(6, 32)); //ADD R3,R0
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(7, 32)); //ADD (R1),(R0)
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(8, 32)); //ADD (R2),(R0)
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(9, 32)); //ADD (R3),(R0)
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(10, 32)); //ADD (R4),(R0)
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(11, 32)); //ADD (R1),R0
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(12, 32)); //ADD (R2),R0
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(15, 32)); //ADD #CONST,R1
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(32, 32)); //#CONST = 32
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(17, 32)); //ADD R0,R1
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(18, 32)); //ADD R2,R1
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(19, 32)); //ADD R3,R1
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(20, 32)); //ADD R4,R1
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(16, 32)); //ADD #CONST,(R1)
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(32, 32)); //#CONST = 32
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(16, 32)); //ADD #CONST,(R1)
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(32, 32)); //#CONST = 32
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(21, 32)); //ADD (R0),(R1)
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(22, 32)); //ADD (R2),(R1)
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(29, 32)); //ADD #CONST,R2
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(51, 32)); //#CONST
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(31, 32)); //ADD R0,R2
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(32, 32)); //ADD R1,R2
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(33, 32)); //ADD R3,R2
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(34, 32)); //ADD R4,R2
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(35, 32)); //ADD (R0),(R2)
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(39, 32)); //ADD (R0),R2
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(30, 32)); //ADD #CONST,(R2)
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(63	, 32)); //#CONST
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(43, 32)); //ADD #CONST,R3
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(65, 32)); //#CONST
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(45, 32)); //ADD R0,R3
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(46, 32)); //ADD R1,R3
		//arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(47, 32)); //ADD R2,R3
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(48, 32)); //ADD R1,R3
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(49, 32)); //ADD (R0),(R3)
		
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(44, 32)); //ADD #CONST,(R3)
		arquitetura.insereMemoria(++x, FuncoesAuxiliares.getNumber(32, 32)); //#CONST
		
		
		Thread ligaComputador = new Thread(arquitetura);
		
		ligaComputador.start();
		
		arquitetura.executaPrograma(FuncoesAuxiliares.getNumber(100, 32)); //Posição Inicial pra iniciar o programa igual a 100
	}

}