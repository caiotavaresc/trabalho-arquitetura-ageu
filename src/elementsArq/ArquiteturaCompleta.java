package elementsArq;


public class ArquiteturaCompleta implements Runnable{
	private int tamWord;
	
	private int contador; //Contador usado pra esperar normalizar tudo
	
	private boolean[] lastDateMemoryReceived;
	
	private boolean[][] memoriaDeControle;
	
	private Register R0;
	private Register R1;
	private Register A;
	private Register B;
	private Register C;
	private Register D;
	private Register PC;
	
	private Register R2;
	private Register R3;
	private Register R4;
	private Register E;
	private Register F;
	private Register G;
	private Register H;
	private Register I;
	
	private Register IR;
	private Register RDADO;
	private Register REND;
	
	private Multiplexador3 mux3Direita;
	private Multiplexador3 mux3Esquerda;
	
	private Multiplexador1 mux1RDado;
	
	private ALU objALU;
	
	private PrimaryMemory memory;
	
	private boolean halt;
	
	private boolean a;
	private boolean b;
	private boolean c;
	private boolean d;
	private boolean e;
	private boolean f;
	private boolean g;
	private boolean h;
	private boolean i;
	private boolean j;
	private boolean k;
	private boolean l;
	private boolean m;
	private boolean n;
	private boolean o;
	private boolean p;
	private boolean q;
	private boolean r;
	private boolean s;
	private boolean t;
	private boolean u;
	private boolean v;
	private boolean w;
	private boolean x;
	private boolean y;
	private boolean z;
	private boolean a2;
	private boolean b2;
	private boolean c2;
	private boolean d2;
	private boolean e2;
	
	public ArquiteturaCompleta(){
		this.tamWord = 32;
		
		this.contador = 0;
		
		this.lastDateMemoryReceived = new boolean[this.tamWord];
		
		for(int i=0; i<this.lastDateMemoryReceived.length; i++){
			this.lastDateMemoryReceived[i] = false;
		}
		
		this.criarMemoriaDeControle();
		
		this.mudaControles(FuncoesAuxiliares.getNumber(0, 31));

		this.RDADO = new Register(this.tamWord);
		this.R0 = new Register(this.tamWord);
		this.R1 = new Register(this.tamWord);
		this.A = new Register(this.tamWord);;
		this.B = new Register(this.tamWord);
		this.C = new Register(this.tamWord);
		this.D = new Register(this.tamWord);
		this.PC = new Register(this.tamWord);
		
		this.R2 = new Register(this.tamWord);
		this.R3 = new Register(this.tamWord);
		this.R4 = new Register(this.tamWord);
		this.E = new Register(this.tamWord);
		this.F = new Register(this.tamWord);
		this.G = new Register(this.tamWord);
		this.H = new Register(this.tamWord);
		this.I = new Register(this.tamWord);
		
		this.IR = new Register(this.tamWord);
		this.REND = new Register(this.tamWord);
		
		this.mux3Direita = new Multiplexador3(this.tamWord);
		this.mux3Esquerda = new Multiplexador3(this.tamWord);
		
		this.mux1RDado = new Multiplexador1(this.tamWord);
		
		this.objALU = new ALU(this.tamWord);
		
		this.memory = new PrimaryMemory(1024, this.tamWord);
	}
	
	public void insereMemoria(int position, boolean[] valor){
		this.memory.setWord(position, valor);
	}
	
	public boolean[] getMemoria(int position){
		return this.memory.getWord(position);
	}
	
	public void executaPrograma(boolean[] posicaoInicial){
		if(posicaoInicial.length != 32)
			return;
		
		this.PC.setValue(posicaoInicial);
		this.buscaInstrucao();
	}
	
	private void buscaInstrucao(){
		//REND<-PC
		//T1
		this.mudaControles(this.memoriaDeControle[6]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[6]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[7]);
		this.esperaNormalizar();
			
		//RDADO <- MEMÓRIA
		//T1
		this.mudaControles(this.memoriaDeControle[2]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[2]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[3]);
		this.esperaNormalizar();
			
		//IR <- RDADO
		//T1
		this.mudaControles(this.memoriaDeControle[4]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[4]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[8]);
		this.esperaNormalizar();
		
		this.executaInstrucao();
	}
	
	private void executaInstrucao(){		
		int number = FuncoesAuxiliares.getIntNumber(this.IR.getValue());
		
		if(number == 0)
		{
			this.halt();
			return;
		}
		

		OperationBetween operation;
		Registrador type1;
		Registrador type2;
		
		int espacoParaOperacoes = 200;
		int espacoParaRegistrador = 20;
		
		int i = number/espacoParaOperacoes;
		
		if(number >= 6*espacoParaOperacoes){
			return;
		}
		
		switch(i){
			case 0:
				operation = OperationBetween.ADD;
				break;
			
			case 1:
				operation = OperationBetween.SUB;
				break;
				
			case 2:
				operation = OperationBetween.MOV;
				break;
				
			case 3:
				operation = OperationBetween.CMP;
				break;
				
			case 4:
				operation = OperationBetween.AND;
				break;
				
			case 5:
				operation = OperationBetween.OR;
				break;
			
			default:
				operation = OperationBetween.ADD;
				break;				
		}
		
		int j = (number%espacoParaOperacoes)%espacoParaRegistrador;
		
		switch(j){
			case 1:
				type1 = Registrador.Const;
				break;
			
			case 2:
				type1 = Registrador.R0;
				break;
			
			case 3:
				type1 = Registrador.R1;
				break;
			
			case 4:
				type1 = Registrador.R2;
				break;
			
			case 5:
				type1 = Registrador.R3;
				break;
			
			case 6:
				type1 = Registrador.R4;
				break;
			
			case 7:
				type1 = Registrador.EndR0;
				break;
			
			case 8:
				type1 = Registrador.EndR1;
				break;
			
			case 9:
				type1 = Registrador.EndR2;
				break;
			
			case 10:
				type1 = Registrador.EndR3;
				break;
				
			case 11:
				type1 = Registrador.EndR4;
				break;
			
			default:
				type1 = Registrador.Const;
				break;
		}
		
		int k = (number%espacoParaOperacoes)/espacoParaRegistrador;
		
		switch(k){
			case 0:
				type2 = Registrador.R0;
				break;
				
			case 1:
				type2 = Registrador.R1;
				break;
				
			case 2:
				type2 = Registrador.R2;
				break;
				
			case 3:
				type2 = Registrador.R3;
				break;
				
			case 4:
				type2 = Registrador.R4;
				break;
				
			case 5:
				type2 = Registrador.EndR0;
				break;
				
			case 6:
				type2 = Registrador.EndR1;
				break;
				
			case 7:
				type2 = Registrador.EndR2;
				break;
				
			case 8:
				type2 = Registrador.EndR3;
				break;
				
			case 9:
				type2 = Registrador.EndR4;
				break;
				
			default:
				type2 = Registrador.R0;
				break;
		}
		
		this.operationRxRy(operation, type1, type2);
	}
	
	private void criarMemoriaDeControle(){
		
		this.memoriaDeControle = new boolean[1024][31];
		
		//REND <- PC+1 e A <- PC+1
		this.memoriaDeControle[0] = FuncoesAuxiliares.getNumber(Integer.parseInt("1110000010", 2), 31);
		this.memoriaDeControle[1] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000010001110000010", 2), 31);
		
		//RDADO <- MEMÓRIA
		this.memoriaDeControle[2] = FuncoesAuxiliares.getNumber(Integer.parseInt("1", 2), 31);
		this.memoriaDeControle[3] = FuncoesAuxiliares.getNumber(Integer.parseInt("100000000000001", 2), 31);
		
		//E <- RDADO
		this.memoriaDeControle[4] = FuncoesAuxiliares.getNumber(Integer.parseInt("0", 2), 31);
		this.memoriaDeControle[5] = FuncoesAuxiliares.getNumber(Integer.parseInt("100000000000000000000", 2), 31);
		
		//REND <- PC
		this.memoriaDeControle[6] = FuncoesAuxiliares.getNumber(Integer.parseInt("1110000000", 2), 31);
		this.memoriaDeControle[7] = FuncoesAuxiliares.getNumber(Integer.parseInt("10001110000000", 2), 31);
		
		//IR <- RDADO
		//this.memoriaDeControle[4]
		this.memoriaDeControle[8] = FuncoesAuxiliares.getNumber(Integer.parseInt("1000000000000000", 2), 31);
		
		//PC <- A
		this.memoriaDeControle[9] = FuncoesAuxiliares.getNumber(Integer.parseInt("0110000000", 2), 31);
		this.memoriaDeControle[10] = FuncoesAuxiliares.getNumber(Integer.parseInt("1000000000000000110000000", 2), 31);
		
		//REND <- R0
		this.memoriaDeControle[11] = FuncoesAuxiliares.getNumber(Integer.parseInt("0010000000", 2), 31);
		this.memoriaDeControle[12] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000010000000", 2), 31);
		
		//RDADO <- A
		this.memoriaDeControle[13] = FuncoesAuxiliares.getNumber(Integer.parseInt("0111000000", 2), 31);
		this.memoriaDeControle[14] = FuncoesAuxiliares.getNumber(Integer.parseInt("100000111000000", 2), 31);
		
		//MEMÓRIA <- RDADO
		this.memoriaDeControle[15] = FuncoesAuxiliares.getNumber(Integer.parseInt("1", 2), 31);
		
		//E <- R1
		this.memoriaDeControle[16] = FuncoesAuxiliares.getNumber(Integer.parseInt("0100000000", 2), 31);
		this.memoriaDeControle[17] = FuncoesAuxiliares.getNumber(Integer.parseInt("100000000000100000000", 2), 31);
		
		//E <- R0
		this.memoriaDeControle[18] = FuncoesAuxiliares.getNumber(Integer.parseInt("0010000000", 2), 31);
		this.memoriaDeControle[19] = FuncoesAuxiliares.getNumber(Integer.parseInt("100000000000010000000", 2), 31);
		
		//E <- R2
		this.memoriaDeControle[20] = FuncoesAuxiliares.getNumber(Integer.parseInt("0000000000110", 2), 31);
		this.memoriaDeControle[21] = FuncoesAuxiliares.getNumber(Integer.parseInt("100000000000000000110", 2), 31);
		
		//E <- R3
		this.memoriaDeControle[22] = FuncoesAuxiliares.getNumber(Integer.parseInt("0010000000110", 2), 31);
		this.memoriaDeControle[23] = FuncoesAuxiliares.getNumber(Integer.parseInt("100000000010000000110", 2), 31);
		
		//E <- R4
		this.memoriaDeControle[24] = FuncoesAuxiliares.getNumber(Integer.parseInt("0100000000110", 2), 31);
		this.memoriaDeControle[25] = FuncoesAuxiliares.getNumber(Integer.parseInt("100000000100000000110", 2), 31);
		
		//REND <- R0
		//this.memoriaDeControle[18] = FuncoesAuxiliares.getNumber(Integer.parseInt("0010000000", 2), 31);
		this.memoriaDeControle[26] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000010000000", 2), 31);
		
		//REND <- R1
		//this.memoriaDeControle[16] = FuncoesAuxiliares.getNumber(Integer.parseInt("0100000000", 2), 31);
		this.memoriaDeControle[27] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000100000000", 2), 31);
		
		//REND <- R2
		//this.memoriaDeControle[20] = FuncoesAuxiliares.getNumber(Integer.parseInt("0000000000110", 2), 31);
		this.memoriaDeControle[28] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000110", 2), 31);
		
		//REND <- R3
		//this.memoriaDeControle[22] = FuncoesAuxiliares.getNumber(Integer.parseInt("0010000000110", 2), 31);
		this.memoriaDeControle[29] = FuncoesAuxiliares.getNumber(Integer.parseInt("10010000000110", 2), 31);
		
		//REND <- R4
		//this.memoriaDeControle[24] = FuncoesAuxiliares.getNumber(Integer.parseInt("0100000000110", 2), 31);
		this.memoriaDeControle[30] = FuncoesAuxiliares.getNumber(Integer.parseInt("10100000000110", 2), 31);
		
		//A <- R0
		//this.memoriaDeControle[18] = FuncoesAuxiliares.getNumber(Integer.parseInt("0010000000", 2), 31);
		this.memoriaDeControle[31] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000000010000000", 2), 31);
		
		//A <- R1
		//this.memoriaDeControle[16] = FuncoesAuxiliares.getNumber(Integer.parseInt("0100000000", 2), 31);
		this.memoriaDeControle[32] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000000100000000", 2), 31);
		
		//A <- R2
		//this.memoriaDeControle[20] = FuncoesAuxiliares.getNumber(Integer.parseInt("0000000000110", 2), 31);
		this.memoriaDeControle[33] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000000000000110", 2), 31);
		
		//A <- R3
		//this.memoriaDeControle[22] = FuncoesAuxiliares.getNumber(Integer.parseInt("0010000000110", 2), 31);
		this.memoriaDeControle[34] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000010000000110", 2), 31);
		
		//A <- R4
		//this.memoriaDeControle[24] = FuncoesAuxiliares.getNumber(Integer.parseInt("0100000000110", 2), 31);
		this.memoriaDeControle[35] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000100000000110", 2), 31);
		
		//A <- RDADO
		//this.memoriaDeControle[4]
		this.memoriaDeControle[36] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000000000000000", 2), 31);
		
		//B <- A+E
		this.memoriaDeControle[37] = FuncoesAuxiliares.getNumber(Integer.parseInt("0110110001100", 2), 31);
		this.memoriaDeControle[38] = FuncoesAuxiliares.getNumber(Integer.parseInt("1000000000000000110110001100", 2), 31);
		
		//B <- A-E
		this.memoriaDeControle[39] = FuncoesAuxiliares.getNumber(Integer.parseInt("0110110001110", 2), 31);
		this.memoriaDeControle[40] = FuncoesAuxiliares.getNumber(Integer.parseInt("1000000000000000110110001110", 2), 31);
		
		//B <- A AND E
		this.memoriaDeControle[41] = FuncoesAuxiliares.getNumber(Integer.parseInt("0110110010010", 2), 31);
		this.memoriaDeControle[42] = FuncoesAuxiliares.getNumber(Integer.parseInt("1000000000000000110110010010", 2), 31);
		
		//B <- A OR E
		this.memoriaDeControle[43] = FuncoesAuxiliares.getNumber(Integer.parseInt("0110110010100", 2), 31);
		this.memoriaDeControle[44] = FuncoesAuxiliares.getNumber(Integer.parseInt("1000000000000000110110010100", 2), 31);
		
		//B <- E
		this.memoriaDeControle[45] = FuncoesAuxiliares.getNumber(Integer.parseInt("0110110010100", 2), 31);
		this.memoriaDeControle[46] = FuncoesAuxiliares.getNumber(Integer.parseInt("1000000000000000110110000110", 2), 31);
		
		//R0 <- B
		this.memoriaDeControle[47] = FuncoesAuxiliares.getNumber(Integer.parseInt("1000000000", 2), 31);
		this.memoriaDeControle[48] = FuncoesAuxiliares.getNumber(Integer.parseInt("1000000000000000000001000000000", 2), 31);
		
		//R1 <- B
		//this.memoriaDeControle[47] = FuncoesAuxiliares.getNumber(Integer.parseInt("1000000000", 2), 31);
		this.memoriaDeControle[49] = FuncoesAuxiliares.getNumber(Integer.parseInt("100000000000000000001000000000", 2), 31);
		
		//R2 <- B
		//this.memoriaDeControle[47] = FuncoesAuxiliares.getNumber(Integer.parseInt("1000000000", 2), 31);
		this.memoriaDeControle[50] = FuncoesAuxiliares.getNumber(Integer.parseInt("100000000000001000000000", 2), 31);
		
		//R3 <- B
		//this.memoriaDeControle[47] = FuncoesAuxiliares.getNumber(Integer.parseInt("1000000000", 2), 31);
		this.memoriaDeControle[51] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000001000000000", 2), 31);
		
		//R4 <- B
		//this.memoriaDeControle[47] = FuncoesAuxiliares.getNumber(Integer.parseInt("1000000000", 2), 31);
		this.memoriaDeControle[52] = FuncoesAuxiliares.getNumber(Integer.parseInt("1000000000001000000000", 2), 31);
		
		//RDADO <- B
		this.memoriaDeControle[53] = FuncoesAuxiliares.getNumber(Integer.parseInt("1001000000", 2), 31);
		this.memoriaDeControle[54] = FuncoesAuxiliares.getNumber(Integer.parseInt("100001001000000", 2), 31);
	}
	
	
	private void operationRxRy(OperationBetween operation, Registrador type1, Registrador type2){	
		switch(type1){
			case Const:
			{
				// REND <- PC+1 e A<-PC+1
				//T1
				this.mudaControles(this.memoriaDeControle[0]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[0]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[1]);
				this.esperaNormalizar();
					
				//PC <- A
				//T1
				this.mudaControles(this.memoriaDeControle[9]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[9]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[10]);
				this.esperaNormalizar();

				//RDADO <- MEM
				//T1
				this.mudaControles(this.memoriaDeControle[2]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[2]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[3]);
				this.esperaNormalizar();
					
				//E <- RDADO
				//T1
				this.mudaControles(this.memoriaDeControle[4]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[4]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[5]);
				this.esperaNormalizar();
			}
				break;
			case R0:
			{
				//E <- R0
				//T1
				this.mudaControles(this.memoriaDeControle[18]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[18]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[19]);
				this.esperaNormalizar();
			}
				break;
			case R1:
			{
				//E <- R1
				//T1
				this.mudaControles(this.memoriaDeControle[16]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[16]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[17]);
				this.esperaNormalizar();				
			}
				break;
			case R2:
			{
				//E <- R2
				//T1
				this.mudaControles(this.memoriaDeControle[20]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[20]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[21]);
				this.esperaNormalizar();
			}
				break;
			case R3:
			{
				//E <- R3
				//T1
				this.mudaControles(this.memoriaDeControle[22]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[22]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[23]);
				this.esperaNormalizar();
			}
				break;
			case R4:
			{
				//E <- R4
				//T1
				this.mudaControles(this.memoriaDeControle[24]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[24]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[25]);
				this.esperaNormalizar();
			}
				break;
			
			case EndR0:
			{
				// REND <- R0
				//T1
				this.mudaControles(this.memoriaDeControle[18]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[18]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[26]);
				this.esperaNormalizar();

				//RDADO <- MEM
				//T1
				this.mudaControles(this.memoriaDeControle[2]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[2]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[3]);
				this.esperaNormalizar();
					
				//E <- RDADO
				//T1
				this.mudaControles(this.memoriaDeControle[4]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[4]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[5]);
				this.esperaNormalizar();
			}
			break;
			
			case EndR1:
			{
				// REND <- R1
				//T1
				this.mudaControles(this.memoriaDeControle[16]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[27]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[27]);
				this.esperaNormalizar();

				//RDADO <- MEM
				//T1
				this.mudaControles(this.memoriaDeControle[2]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[2]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[3]);
				this.esperaNormalizar();
					
				//E <- RDADO
				//T1
				this.mudaControles(this.memoriaDeControle[4]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[4]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[5]);
				this.esperaNormalizar();				
			}
			break;
			
			case EndR2:
			{
				// REND <- R2
				//T1
				this.mudaControles(this.memoriaDeControle[20]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[20]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[28]);
				this.esperaNormalizar();

				//RDADO <- MEM
				//T1
				this.mudaControles(this.memoriaDeControle[2]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[2]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[3]);
				this.esperaNormalizar();
					
				//E <- RDADO
				//T1
				this.mudaControles(this.memoriaDeControle[4]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[4]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[5]);
				this.esperaNormalizar();
			}
			break;
			
			case EndR3:
			{
				// REND <- R3
				//T1
				this.mudaControles(this.memoriaDeControle[22]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[22]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[29]);
				this.esperaNormalizar();

				//RDADO <- MEM
				//T1
				this.mudaControles(this.memoriaDeControle[2]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[2]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[3]);
				this.esperaNormalizar();
					
				//E <- RDADO
				//T1
				this.mudaControles(this.memoriaDeControle[4]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[4]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[5]);
				this.esperaNormalizar();
			}
			break;
			
			case EndR4:
			{
				// REND <- R4
				//T1
				this.mudaControles(this.memoriaDeControle[24]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[24]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[30]);
				this.esperaNormalizar();

				//RDADO <- MEM
				//T1
				this.mudaControles(this.memoriaDeControle[2]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[2]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[3]);
				this.esperaNormalizar();
					
				//E <- RDADO
				//T1
				this.mudaControles(this.memoriaDeControle[4]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[4]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[5]);
				this.esperaNormalizar();
			}
			break;
			
		}
				
		switch(type2)
		{
			case R0:
			{
				//A <- R0
				//T1
				this.mudaControles(this.memoriaDeControle[18]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[18]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[31]);
				this.esperaNormalizar();
			}
			break;
			
			case R1:
			{
				//A <- R1
				//T1
				this.mudaControles(this.memoriaDeControle[16]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[32]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[32]);
				this.esperaNormalizar();
			}
			break;
			
			case R2:
			{
				//A <- R2
				//T1
				this.mudaControles(this.memoriaDeControle[20]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[20]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[33]);
				this.esperaNormalizar();
			}
			break;
			
			case R3:
			{
				//A <- R3
				//T1
				this.mudaControles(this.memoriaDeControle[22]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[22]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[34]);
				this.esperaNormalizar();
			}
			break;
			
			case R4:
			{
				//A <- R4
				//T1
				this.mudaControles(this.memoriaDeControle[24]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[24]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[35]);
				this.esperaNormalizar();
			}
			break;
			
			case EndR0:
			{
				// REND <- R0
				//T1
				this.mudaControles(this.memoriaDeControle[18]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[18]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[26]);
				this.esperaNormalizar();

				//RDADO <- MEM
				//T1
				this.mudaControles(this.memoriaDeControle[2]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[2]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[3]);
				this.esperaNormalizar();
					
				//A <- RDADO
				//T1
				this.mudaControles(this.memoriaDeControle[4]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[4]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[36]);
				this.esperaNormalizar();
			}
			break;
			
			case EndR1:
			{
				// REND <- R1
				//T1
				this.mudaControles(this.memoriaDeControle[16]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[16]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[27]);
				this.esperaNormalizar();

				//RDADO <- MEM
				//T1
				this.mudaControles(this.memoriaDeControle[2]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[2]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[3]);
				this.esperaNormalizar();
					
				//A <- RDADO
				//T1
				this.mudaControles(this.memoriaDeControle[4]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[4]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[36]);
				this.esperaNormalizar();
			}
			break;
			
			case EndR2:
			{
				// REND <- R2
				//T1
				this.mudaControles(this.memoriaDeControle[20]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[20]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[28]);
				this.esperaNormalizar();

				//RDADO <- MEM
				//T1
				this.mudaControles(this.memoriaDeControle[2]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[2]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[3]);
				this.esperaNormalizar();
					
				//A <- RDADO
				//T1
				this.mudaControles(this.memoriaDeControle[4]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[4]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[36]);
				this.esperaNormalizar();
			}
			break;
			
			case EndR3:
			{
				// REND <- R3
				//T1
				this.mudaControles(this.memoriaDeControle[22]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[22]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[29]);
				this.esperaNormalizar();

				//RDADO <- MEM
				//T1
				this.mudaControles(this.memoriaDeControle[2]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[2]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[3]);
				this.esperaNormalizar();
					
				//A <- RDADO
				//T1
				this.mudaControles(this.memoriaDeControle[4]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[4]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[36]);
				this.esperaNormalizar();
			}
			break;
			
			case EndR4:
			{
				// REND <- R4
				//T1
				this.mudaControles(this.memoriaDeControle[24]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[24]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[30]);
				this.esperaNormalizar();

				//RDADO <- MEM
				//T1
				this.mudaControles(this.memoriaDeControle[2]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[2]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[3]);
				this.esperaNormalizar();
					
				//A <- RDADO
				//T1
				this.mudaControles(this.memoriaDeControle[4]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[4]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[36]);
				this.esperaNormalizar();
			}
			break;
		}
		
		switch(operation){
			case ADD:
			{
				//B <- A+E
				//T1
				this.mudaControles(this.memoriaDeControle[37]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[37]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[38]);
				this.esperaNormalizar();
			}
			break;
			
			case CMP:
			case SUB:
			{
				//B <- A-E
				//T1
				this.mudaControles(this.memoriaDeControle[39]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[39]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[40]);
				this.esperaNormalizar();
			}
			break;
			
			case MOV:
			{
				//B <- E
				//T1
				this.mudaControles(this.memoriaDeControle[45]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[45]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[46]);
				this.esperaNormalizar();
			}
			break;
			
			case AND:
			{
				//B <- A AND E
				//T1
				this.mudaControles(this.memoriaDeControle[41]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[41]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[42]);
				this.esperaNormalizar();
			}
			break;
			
			case OR:
			{
				//B <- A OR E
				//T1
				this.mudaControles(this.memoriaDeControle[43]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[43]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[44]);
				this.esperaNormalizar();
			}
			break;
		
		}
	
		if(operation != OperationBetween.CMP)
		{	
			switch(type2){
				case R0:
				{
					//R0 <- B
					//T1
					this.mudaControles(this.memoriaDeControle[47]);
					this.esperaNormalizar();
					//T2
					this.mudaControles(this.memoriaDeControle[47]);
					this.esperaNormalizar();
					//T3
					this.mudaControles(this.memoriaDeControle[48]);
					this.esperaNormalizar();				
				}
				break;
			
			case R1:
			{
				//R1 <- B
				//T1
				this.mudaControles(this.memoriaDeControle[47]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[47]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[49]);
				this.esperaNormalizar();
			}
			break;
			
			case R2:
			{
				//R2 <- B
				//T1
				this.mudaControles(this.memoriaDeControle[47]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[47]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[50]);
				this.esperaNormalizar();
			}
			break;
			
			case R3:
			{
				//R3 <- B
				//T1
				this.mudaControles(this.memoriaDeControle[47]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[47]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[51]);
				this.esperaNormalizar();
			}
			break;
			
			case R4:
			{
				//R4 <- B
				//T1
				this.mudaControles(this.memoriaDeControle[47]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[47]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[52]);
				this.esperaNormalizar();
			}
			break;
			
			case EndR0:
			{
				//REND <- R0
				//T1
				this.mudaControles(this.memoriaDeControle[18]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[18]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[26]);
				this.esperaNormalizar();

				//RDADO <- B
				//T1
				this.mudaControles(this.memoriaDeControle[53]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[53]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[54]);
				this.esperaNormalizar();
				
				//MEMORIA <- RDADO
				//T1, T2 e T3
				this.mudaControles(this.memoriaDeControle[15]);
				this.esperaNormalizar();
			}
			break;
			
			case EndR1:
			{
				// REND <- R1
				//T1
				this.mudaControles(this.memoriaDeControle[16]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[27]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[27]);
				this.esperaNormalizar();

				//RDADO <- B
				//T1
				this.mudaControles(this.memoriaDeControle[53]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[53]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[54]);
				this.esperaNormalizar();
				
				//MEMORIA <- RDADO
				//T1, T2 e T3
				this.mudaControles(this.memoriaDeControle[15]);
				this.esperaNormalizar();
			}
			break;
			
			case EndR2:
			{
				// REND <- R2
				//T1
				this.mudaControles(this.memoriaDeControle[20]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[20]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[28]);
				this.esperaNormalizar();

				//RDADO <- B
				//T1
				this.mudaControles(this.memoriaDeControle[53]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[53]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[54]);
				this.esperaNormalizar();
				
				//MEMORIA <- RDADO
				//T1, T2 e T3
				this.mudaControles(this.memoriaDeControle[15]);
				this.esperaNormalizar();
			}
			break;
			
			case EndR3:
			{
				// REND <- R3
				//T1
				this.mudaControles(this.memoriaDeControle[22]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[22]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[29]);
				this.esperaNormalizar();

				//RDADO <- B
				//T1
				this.mudaControles(this.memoriaDeControle[53]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[53]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[54]);
				this.esperaNormalizar();
				
				//MEMORIA <- RDADO
				//T1, T2 e T3
				this.mudaControles(this.memoriaDeControle[15]);
				this.esperaNormalizar();
			}
			break;
			
			case EndR4:
			{
				// REND <- R4
				//T1
				this.mudaControles(this.memoriaDeControle[24]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[24]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[30]);
				this.esperaNormalizar();

				//RDADO <- B
				//T1
				this.mudaControles(this.memoriaDeControle[53]);
				this.esperaNormalizar();
				//T2
				this.mudaControles(this.memoriaDeControle[53]);
				this.esperaNormalizar();
				//T3
				this.mudaControles(this.memoriaDeControle[54]);
				this.esperaNormalizar();
				
				//MEMORIA <- RDADO
				//T1, T2 e T3
				this.mudaControles(this.memoriaDeControle[15]);
				this.esperaNormalizar();
			}
			break;
		}
	}
			
		//REND <- PC+1 e A<-PC+1
		//T1
		this.mudaControles(this.memoriaDeControle[0]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[0]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[1]);
		this.esperaNormalizar();
			
		// PC <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[12]);
		this.esperaNormalizar();
		
		this.buscaInstrucao();		
	}
	
	private void halt(){
		this.halt = true;
		
		this.showRegistradores();
	}
	
	private void showRegistradores(){
		System.out.println("R0 = " + FuncoesAuxiliares.getIntNumber(R0.getValue()) + " = " + FuncoesAuxiliares.getStringWord(R0.getValue()));
		System.out.println("R1 = " + FuncoesAuxiliares.getIntNumber(R1.getValue()) + " = " + FuncoesAuxiliares.getStringWord(R1.getValue()));
		System.out.println("R2 = " + FuncoesAuxiliares.getIntNumber(R2.getValue()) + " = " + FuncoesAuxiliares.getStringWord(R2.getValue()));
		System.out.println("R3 = " + FuncoesAuxiliares.getIntNumber(R3.getValue()) + " = " + FuncoesAuxiliares.getStringWord(R3.getValue()));
		System.out.println("R4 = " + FuncoesAuxiliares.getIntNumber(R4.getValue()) + " = " + FuncoesAuxiliares.getStringWord(R4.getValue()));
		System.out.println("PC = " + FuncoesAuxiliares.getIntNumber(PC.getValue()) + " = " + FuncoesAuxiliares.getStringWord(PC.getValue()));
		System.out.println("A = " + FuncoesAuxiliares.getIntNumber(A.getValue()) + " = " + FuncoesAuxiliares.getStringWord(A.getValue()));
		System.out.println("B = " + FuncoesAuxiliares.getIntNumber(B.getValue()) + " = " + FuncoesAuxiliares.getStringWord(B.getValue()));
		System.out.println("C = " + FuncoesAuxiliares.getIntNumber(C.getValue()) + " = " + FuncoesAuxiliares.getStringWord(C.getValue()));
		System.out.println("D = " + FuncoesAuxiliares.getIntNumber(D.getValue()) + " = " + FuncoesAuxiliares.getStringWord(D.getValue()));
		System.out.println("E = " + FuncoesAuxiliares.getIntNumber(E.getValue()) + " = " + FuncoesAuxiliares.getStringWord(E.getValue()));
		System.out.println("F = " + FuncoesAuxiliares.getIntNumber(F.getValue()) + " = " + FuncoesAuxiliares.getStringWord(F.getValue()));
		System.out.println("G = " + FuncoesAuxiliares.getIntNumber(G.getValue()) + " = " + FuncoesAuxiliares.getStringWord(G.getValue()));
		System.out.println("H = " + FuncoesAuxiliares.getIntNumber(H.getValue()) + " = " + FuncoesAuxiliares.getStringWord(H.getValue()));
		System.out.println("I = " + FuncoesAuxiliares.getIntNumber(I.getValue()) + " = " + FuncoesAuxiliares.getStringWord(I.getValue()));
		System.out.println("RDADO = " + FuncoesAuxiliares.getIntNumber(RDADO.getValue()));
		System.out.println("REND = " + FuncoesAuxiliares.getIntNumber(REND.getValue()));
		System.out.println("IR = " + FuncoesAuxiliares.getIntNumber(IR.getValue()));
	}
	
	private void mudaControles(boolean[] controladores){
		if(controladores.length != 31){
			System.out.println("Não está mudando os controladores");
			return;
		}
		
		this.a = controladores[0];
		this.b = controladores[1];
		this.c = controladores[2];
		this.d = controladores[3];
		this.e = controladores[4];
		this.f = controladores[5];
		this.g = controladores[6];
		
		this.h = controladores[7];
		this.i = controladores[8];
		this.j = controladores[9];
		this.k = controladores[10];
		this.l = controladores[11];
		this.m = controladores[12];
		this.n = controladores[13];
		this.o = controladores[14];
		
		this.p = controladores[15];
		this.q = controladores[16];
		this.r = controladores[17];
		
		this.s = controladores[18];
		this.t = controladores[19];
		this.u = controladores[20];
		
		this.v = controladores[21];
		this.w = controladores[22];
		this.x = controladores[23];
		
		this.y = controladores[24];
		
		this.z = controladores[25];
		this.a2 = controladores[26];
		this.b2 = controladores[27];
		this.c2 = controladores[28];
		this.d2 = controladores[29];
		
		this.e2 = controladores[30];
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!halt){
			this.mux3Esquerda.setValues(this.RDADO.getValue(), this.R0.getValue(), this.R1.getValue(), this.A.getValue(), this.B.getValue(), this.C.getValue(), this.D.getValue(), this.PC.getValue());
			this.mux3Direita.setValues(this.R2.getValue(), this.R3.getValue(), this.R4.getValue(), this.E.getValue(), this.F.getValue(), this.G.getValue(), this.H.getValue(), this.I.getValue());
			
			boolean[] arraySaidaEsquerda = new boolean[3];
			arraySaidaEsquerda[0] = this.v;
			arraySaidaEsquerda[1] = this.w;
			arraySaidaEsquerda[2] = this.x;
			
			boolean[] arraySaidaDireita = new boolean[3];
			arraySaidaDireita[0] = this.s;
			arraySaidaDireita[1] = this.t;
			arraySaidaDireita[2] = this.u;
			
			boolean[] valorEsquerdo = this.mux3Esquerda.getValue(arraySaidaEsquerda);
			boolean[] valorDireito = this.mux3Direita.getValue(arraySaidaDireita);
			
			this.objALU.setLados(valorEsquerdo, valorDireito);
			
			boolean[] setFunctionALU = new boolean[5];
			setFunctionALU[0] = this.z;
			setFunctionALU[1] = this.a2;
			setFunctionALU[2] = this.b2;
			setFunctionALU[3] = this.c2;
			setFunctionALU[4] = this.d2;
			
			this.objALU.setFuncao(setFunctionALU);
			
			boolean[] resultFunction = this.objALU.getResult();
			
			{
				if(this.p){
					this.IR.setValue(resultFunction);
				}
				
				if(this.r){
					this.REND.setValue(resultFunction);
				}
				
				if(this.o){
					this.I.setValue(resultFunction);
				}
				
				if(this.n){
					this.H.setValue(resultFunction);
				}
				
				if(this.m){
					this.G.setValue(resultFunction);
				}
				
				if(this.l){
					this.F.setValue(resultFunction);
				}
				
				if(this.k){
					this.E.setValue(resultFunction);
				}
				
				if(this.j){
					this.R4.setValue(resultFunction);
				}
				
				if(this.i){
					this.R3.setValue(resultFunction);
				}
				
				if(this.h){
					this.R2.setValue(resultFunction);
				}
				
				
				if(this.g){
					this.PC.setValue(resultFunction);
				}
				
				if(this.f){
					this.D.setValue(resultFunction);
				}
				
				if(this.e){
					this.C.setValue(resultFunction);
				}
				
				if(this.d){
					this.B.setValue(resultFunction);
				}
				
				if(this.c){
					this.A.setValue(resultFunction);
				}
				
				if(this.b){
					this.R1.setValue(resultFunction);
				}
				
				if(this.a){
					this.R0.setValue(resultFunction);
				}
			}
			
			if(this.e2){
				this.memory.setWord(FuncoesAuxiliares.getIntNumber(this.REND.getValue()), this.RDADO.getValue()); // 1 - Escrita <=> 0 - Leitura
			}
			else
				this.lastDateMemoryReceived = this.memory.getWord(FuncoesAuxiliares.getIntNumber(this.REND.getValue()));
			
			this.mux1RDado.setValues(this.lastDateMemoryReceived, resultFunction);
			
			boolean[] saidaMux1 = this.mux1RDado.getValue(this.y);
			
			if(this.q){
				this.RDADO.setValue(saidaMux1);
			}
			
			this.contador++;
		}
	}
	
	private void esperaNormalizar(){
		this.contador = 0;
		
		while(this.contador <= 2){
		}
	}
}