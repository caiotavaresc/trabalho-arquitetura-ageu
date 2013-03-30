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
		
		try{
			//REND<-PC
			//T1
			this.mudaControles(this.memoriaDeControle[8]);
			this.esperaNormalizar();
			//T2
			this.mudaControles(this.memoriaDeControle[8]);
			this.esperaNormalizar();
			//T3
			this.mudaControles(this.memoriaDeControle[9]);
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
			this.mudaControles(this.memoriaDeControle[10]);
			this.esperaNormalizar();
			
			this.executaInstrucao();
			
		}catch(Exception e){
			this.halt = true;
			System.out.println("Deu Interrupted Exception");
		}
	}
	
	private void executaInstrucao(){
		int number = FuncoesAuxiliares.getIntNumber(this.IR.getValue());
		
		switch(number){
			case 0:
				this.halt();
				break;
			
			case 1:
				this.addConstR0();
				break;
			
			case 2:
				this.addConstEndR0();
				break;
			
			case 3:
				this.addR1R0();
				break;
			
			case 4:
				this.addR2R0();
				break;
			
			case 5:
				this.addR3R0();
				break;
			
			case 6:
				this.addR4R0();
				break;
			
			case 7:
				this.addEndR1EndR0();
				break;
			
			case 8:
				this.addEndR2EndR0();
				break;
			
			case 9:
				this.addEndR3EndR0();
				break;
			
			case 10:
				this.addEndR4EndR0();
				break;
			
			case 11:
				this.addEndR1R0();
				break;
			
			case 12:
				this.addEndR2R0();
				break;
			
			case 13:
				this.addEndR3R0();
				break;
			
			case 14:
				this.addEndR4R0();
				break;
			
			case 15:
				this.addConstR1();
				break;
			
			case 16:
				this.addConstEndR1();
				break;
			
			case 17:
				this.addR0R1();
				break;
			
			case 18:
				this.addR2R1();
				break;
				
			case 19:
				this.addR3R1();
				break;
			
			case 20:
				this.addR4R1();
				break;
			
			case 21:
				this.addEndR0EndR1();
				break;
			
			case 22:
				this.addEndR2EndR1();
				break;
				
			case 23:
				this.addEndR3EndR1();
				break;
			
			case 24:
				this.addEndR4EndR1();
				break;
			
			case 25:
				this.addEndR0R1();
				break;
			
			case 26:
				this.addEndR2R1();
				break;
			
			case 27:
				this.addEndR3R1();
				break;
			
			case 28:
				this.addEndR4R1();
				break;
			
			case 29:
				this.addConstR2();
				break;
			
			case 30:
				this.addConstEndR2();
				break;
			
			case 31:
				this.addR0R2();
				break;
			
			case 32:
				this.addR1R2();
				break;
			
			case 33:
				this.addR3R2();
				break;
			
			case 34:
				this.addR4R2();
				break;
			
			case 35:
				this.addEndR0EndR2();
				break;
			
			case 36:
				this.addEndR1EndR2();
				break;
			
			case 37:
				this.addEndR3EndR2();
				break;
			
			case 38:
				this.addEndR4EndR2();
				break;
			
			case 39:
				this.addEndR0R2();
				break;
			
			case 40:
				this.addEndR1R2();
				break;
			
			case 41:
				this.addEndR3R2();
				break;
			
			case 42:
				this.addEndR4R2();
				break;
			
			case 43:
				this.addConstR3();
				break;
				
			case 44:
				this.addConstEndR3();
				break;
			
			case 45:
				this.addR0R3();
				break;
			
			case 46:
				this.addR1R3();
				break;
			
			case 47:
				this.addR2R3();
				break;
			
			case 48:
				this.addR4R3();
				break;
			
			case 49:
				this.addEndR0EndR3();
				break;
			
			case 50:
				this.addEndR1EndR3();
				break;
			
			case 51:
				this.addEndR2EndR3();
				break;
				
			case 52:
				this.addEndR4EndR3();
				break;
			
			case 53:
				this.addEndR0R3();
				break;
			
			case 54:
				this.addEndR1R3();
				break;
			
			case 55:
				this.addEndR2R3();
				break;
			
			case 56:
				this.addEndR4R3();
				break;
				
			case 57:
				this.addConstR4();
				break;
			
			case 58:
				this.addConstEndR4();
				break;
			
			case 59:
				this.addEndR0EndR4();
				break;
			
			case 60:
				this.addEndR1EndR4();
				break;
			
			case 61:
				this.addEndR2EndR4();
				break;
			
			case 62:
				this.addEndR3EndR4();
				break;
			
			case 63:
				this.addEndR0R4();
				break;
			
			case 64:
				this.addEndR1R4();
				break;
				
			case 65:
				this.addEndR2R4();
				break;
			
			case 66:
				this.addEndR3R4();
				break;
			
			case 67:
				this.subConstR0();
				break;
			
			case 68:
				this.subConstEndR0();
				break;
			
			case 69:
				this.subR1R0();
				break;
			
			case 70:
				this.subR2R0();
				break;
			
			case 71:
				this.subR3R0();
				break;
			
			case 72:
				this.subR4R0();
				break;
			
			case 73:
				this.subEndR1EndR0();
				break;
			
			case 74:
				this.subEndR2EndR0();
				break;
			
			case 75:
				this.subEndR3EndR0();
				break;
			
			case 76:
				this.subEndR4EndR0();
				break;
			
			case 77:
				this.subEndR1R0();
				break;
			
			case 78:
				this.subEndR2R0();
				break;
			
			case 79:
				this.subEndR3R0();
				break;
			
			case 80:
				this.subEndR4R0();
				break;
			
			case 81:
				this.subConstR1();
				break;
			
			case 82:
				this.subConstEndR1();
				break;
			
			case 83:
				this.subR0R1();
				break;
			
			case 84:
				this.subR2R1();
				break;
			
			default:
				this.halt();
				break;
		}
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
		
		//A <- R0 + E
		this.memoriaDeControle[6] = FuncoesAuxiliares.getNumber(Integer.parseInt("0110010001100", 2), 31);
		this.memoriaDeControle[7] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000110010001100", 2), 31);
		
		//REND <- PC
		this.memoriaDeControle[8] = FuncoesAuxiliares.getNumber(Integer.parseInt("1110000000", 2), 31);
		this.memoriaDeControle[9] = FuncoesAuxiliares.getNumber(Integer.parseInt("10001110000000", 2), 31);
		
		//IR <- RDADO
		//this.memoriaDeControle[4]
		this.memoriaDeControle[10] = FuncoesAuxiliares.getNumber(Integer.parseInt("1000000000000000", 2), 31);
		
		//PC <- A
		this.memoriaDeControle[11] = FuncoesAuxiliares.getNumber(Integer.parseInt("0110000000", 2), 31);
		this.memoriaDeControle[12] = FuncoesAuxiliares.getNumber(Integer.parseInt("1000000000000000110000000", 2), 31);
		
		//R0 <- A
		//this.memoriaDeControle[11]
		this.memoriaDeControle[13] = FuncoesAuxiliares.getNumber(Integer.parseInt("1000000000000000000000110000000", 2), 31);
		
		//REND <- R0
		this.memoriaDeControle[14] = FuncoesAuxiliares.getNumber(Integer.parseInt("0010000000", 2), 31);
		this.memoriaDeControle[15] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000010000000", 2), 31);
		
		//A <- RDADO + E
		this.memoriaDeControle[16] = FuncoesAuxiliares.getNumber(Integer.parseInt("0110000001100", 2), 31);
		this.memoriaDeControle[17] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000110000001100", 2), 31);
		
		//RDADO <- A
		this.memoriaDeControle[18] = FuncoesAuxiliares.getNumber(Integer.parseInt("0111000000", 2), 31);
		this.memoriaDeControle[19] = FuncoesAuxiliares.getNumber(Integer.parseInt("100000111000000", 2), 31);
		
		//MEMÓRIA <- RDADO
		this.memoriaDeControle[20] = FuncoesAuxiliares.getNumber(Integer.parseInt("1", 2), 31);
		
		//E <- R1
		this.memoriaDeControle[21] = FuncoesAuxiliares.getNumber(Integer.parseInt("0100000000", 2), 31);
		this.memoriaDeControle[22] = FuncoesAuxiliares.getNumber(Integer.parseInt("100000000000100000000", 2), 31);
		
		//A <- R0 + E
		this.memoriaDeControle[23] = FuncoesAuxiliares.getNumber(Integer.parseInt("0110010001100", 2), 31);
		this.memoriaDeControle[24] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000110010001100", 2), 31);
		
		//A <- R0 + R2
		this.memoriaDeControle[25] = FuncoesAuxiliares.getNumber(Integer.parseInt("0000010001100", 2), 31);
		this.memoriaDeControle[26] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000000010001100", 2), 31);
		
		//A <- R0 + R3
		this.memoriaDeControle[27] = FuncoesAuxiliares.getNumber(Integer.parseInt("0010010001100", 2), 31);
		this.memoriaDeControle[28] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000010010001100", 2), 31);
		
		//A <- R0 + R4
		this.memoriaDeControle[29] = FuncoesAuxiliares.getNumber(Integer.parseInt("0100010001100", 2), 31);
		this.memoriaDeControle[30] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000100010001100", 2), 31);
		
		//REND <- R1
		this.memoriaDeControle[31] = FuncoesAuxiliares.getNumber(Integer.parseInt("0100000000", 2), 31);
		this.memoriaDeControle[32] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000100000000", 2), 31);
		
		//REND <- R2
		this.memoriaDeControle[33] = FuncoesAuxiliares.getNumber(Integer.parseInt("0000000000110", 2), 31);
		this.memoriaDeControle[34] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000110", 2), 31);
		
		//REND <- R3
		this.memoriaDeControle[35] = FuncoesAuxiliares.getNumber(Integer.parseInt("0010000000110", 2), 31);
		this.memoriaDeControle[36] = FuncoesAuxiliares.getNumber(Integer.parseInt("10010000000110", 2), 31);
		
		//REND <- R4
		this.memoriaDeControle[37] = FuncoesAuxiliares.getNumber(Integer.parseInt("0100000000110", 2), 31);
		this.memoriaDeControle[38] = FuncoesAuxiliares.getNumber(Integer.parseInt("10100000000110", 2), 31);
		
		//A <- R1 + E
		this.memoriaDeControle[39] = FuncoesAuxiliares.getNumber(Integer.parseInt("0110100001100", 2), 31);
		this.memoriaDeControle[40] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000110100001100", 2), 31);
		
		//R1 <- A
		//this.memoriaDeControle[11]
		this.memoriaDeControle[41] = FuncoesAuxiliares.getNumber(Integer.parseInt("100000000000000000000110000000", 2), 31);
		
		//E <- R0
		//this.memoriaDeControle[14] = FuncoesAuxiliares.getNumber(Integer.parseInt("0010000000", 2), 31);
		this.memoriaDeControle[42] = FuncoesAuxiliares.getNumber(Integer.parseInt("100000000000010000000", 2), 31);
		
		//A <- R1 + R2
		this.memoriaDeControle[43] = FuncoesAuxiliares.getNumber(Integer.parseInt("0000100001100", 2), 31);
		this.memoriaDeControle[44] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000000100001100", 2), 31);
		
		//A <- R1 + R3
		this.memoriaDeControle[45] = FuncoesAuxiliares.getNumber(Integer.parseInt("0010100001100", 2), 31);
		this.memoriaDeControle[46] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000010100001100", 2), 31);
		
		//A <- R1 + R4
		this.memoriaDeControle[47] = FuncoesAuxiliares.getNumber(Integer.parseInt("0100100001100", 2), 31);
		this.memoriaDeControle[48] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000100100001100", 2), 31);
		
		//E <- RDADO + R2
		this.memoriaDeControle[49] = FuncoesAuxiliares.getNumber(Integer.parseInt("0000000001100", 2), 31);
		this.memoriaDeControle[50] = FuncoesAuxiliares.getNumber(Integer.parseInt("100000000000000001100", 2), 31);
		
		//R2 <- E
		this.memoriaDeControle[51] = FuncoesAuxiliares.getNumber(Integer.parseInt("0110000000110", 2), 31);
		this.memoriaDeControle[52] = FuncoesAuxiliares.getNumber(Integer.parseInt("100000000000110000000110", 2), 31);
		
		//R2 <- A
		//this.memoriaDeControle[11]
		this.memoriaDeControle[53] = FuncoesAuxiliares.getNumber(Integer.parseInt("100000000000000110000000", 2), 31);
		
		//A <- R3
		//this.memoriaDeControle[35]
		this.memoriaDeControle[54] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000010000000110", 2), 31);
		
		//E <- A+R2
		this.memoriaDeControle[55] = FuncoesAuxiliares.getNumber(Integer.parseInt("0000110001100", 2), 31);
		this.memoriaDeControle[56] = FuncoesAuxiliares.getNumber(Integer.parseInt("100000000000110001100", 2), 31);
		
		//A <- R4
		//this.memoriaDeControle[37]
		this.memoriaDeControle[57] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000100000000110", 2), 31);
		
		//E <- RDADO + R3
		this.memoriaDeControle[58] = FuncoesAuxiliares.getNumber(Integer.parseInt("0010000001100", 2), 31);
		this.memoriaDeControle[59] = FuncoesAuxiliares.getNumber(Integer.parseInt("100000000010000001100", 2), 31);
		
		//R3 <- E
		//this.memoriaDeControle[51] = FuncoesAuxiliares.getNumber(Integer.parseInt("0110000000110", 2), 31);
		this.memoriaDeControle[60] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000110000000110", 2), 31);
		
		//R3 <- A
		//this.memoriaDeControle[11]
		this.memoriaDeControle[61] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000110000000", 2), 31);
		
		//E <- A+R4
		this.memoriaDeControle[62] = FuncoesAuxiliares.getNumber(Integer.parseInt("0100110001100", 2), 31);
		this.memoriaDeControle[63] = FuncoesAuxiliares.getNumber(Integer.parseInt("100000000100110001100", 2), 31);
		
		//E <- RDADO + R4
		this.memoriaDeControle[64] = FuncoesAuxiliares.getNumber(Integer.parseInt("0100000001100", 2), 31);
		this.memoriaDeControle[65] = FuncoesAuxiliares.getNumber(Integer.parseInt("100000000100000001100", 2), 31);
		
		//R3 <- E
		//this.memoriaDeControle[51] = FuncoesAuxiliares.getNumber(Integer.parseInt("0110000000110", 2), 31);
		this.memoriaDeControle[66] = FuncoesAuxiliares.getNumber(Integer.parseInt("1000000000110000000110", 2), 31);
		
		//A <- R0 - E
		this.memoriaDeControle[67] = FuncoesAuxiliares.getNumber(Integer.parseInt("0110010001110", 2), 31);
		this.memoriaDeControle[68] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000110010001110", 2), 31);
		
		//A <- RDADO - E
		this.memoriaDeControle[69] = FuncoesAuxiliares.getNumber(Integer.parseInt("0110010001110", 2), 31);
		this.memoriaDeControle[70] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000110010001110", 2), 31);
		
		//A <- R0 - R2
		this.memoriaDeControle[71] = FuncoesAuxiliares.getNumber(Integer.parseInt("0000010001110", 2), 31);
		this.memoriaDeControle[72] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000000010001110", 2), 31);
		
		//A <- R0 - R3
		this.memoriaDeControle[73] = FuncoesAuxiliares.getNumber(Integer.parseInt("0010010001110", 2), 31);
		this.memoriaDeControle[74] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000010010001110", 2), 31);
		
		//A <- R0 - R4
		this.memoriaDeControle[75] = FuncoesAuxiliares.getNumber(Integer.parseInt("0100010001110", 2), 31);
		this.memoriaDeControle[76] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000100010001110", 2), 31);
		
		//A <- R1 - E
		this.memoriaDeControle[77] = FuncoesAuxiliares.getNumber(Integer.parseInt("0110100001110", 2), 31);
		this.memoriaDeControle[78] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000110100001110", 2), 31);
		
		//A <- R1 - R2
		this.memoriaDeControle[79] = FuncoesAuxiliares.getNumber(Integer.parseInt("0000100001110", 2), 31);
		this.memoriaDeControle[80] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000000000100001110", 2), 31);
	}
	
	
	private void addConstR0(){
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
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[12]);
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
			
		//A <- R0 + E
		//T1
		this.mudaControles(this.memoriaDeControle[6]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[6]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[7]);
		this.esperaNormalizar();
		
		//R0 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[13]);
		this.esperaNormalizar();
			
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
	
	private void addConstEndR0(){
		//REND <- PC+1 A<- PC+1
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
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[12]);
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
		
		//REND <- R0
		//T1
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[15]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	
	private void addR1R0(){
		//E <- R1
		//T1
		this.mudaControles(this.memoriaDeControle[21]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[21]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[22]);
		this.esperaNormalizar();
		
		//A <- R0 + E
		//T1
		this.mudaControles(this.memoriaDeControle[23]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[23]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[24]);
		this.esperaNormalizar();
		
		//R0 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[13]);
		this.esperaNormalizar();
		
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
	
	private void addR2R0(){
		//A <- R0+R2
		//T1
		this.mudaControles(this.memoriaDeControle[25]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[25]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[26]);
		this.esperaNormalizar();
		
		//R0 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[13]);
		this.esperaNormalizar();
		
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
	
	private void addR3R0(){
		//A <- R0 + R3
		//T1
		this.mudaControles(this.memoriaDeControle[27]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[27]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[28]);
		this.esperaNormalizar();
		
		//R0 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[13]);
		this.esperaNormalizar();
				
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

	private void addR4R0(){
		//A <- R0 + R4
		//T1
		this.mudaControles(this.memoriaDeControle[29]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[29]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[30]);
		this.esperaNormalizar();
		
		//R0 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[13]);
		this.esperaNormalizar();
						
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
	
	private void addEndR1EndR0(){
		//REND <- R1
		//T1
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[32]);
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

		//REND <- R0
		//T1
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[15]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	
	private void addEndR2EndR0(){
		//REND <- R2
		//T1
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[34]);
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

		//REND <- R0
		//T1
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[15]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	
	private void addEndR3EndR0(){
		//REND <- R3
		//T1
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[36]);
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

		//REND <- R0
		//T1
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[15]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	
	private void addEndR4EndR0(){
		//REND <- R4
		//T1
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[38]);
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

		//REND <- R0
		//T1
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[15]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	
	private void addEndR1R0(){
		//REND <- R1
		//T1
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[32]);
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
		
		//A <- R0 + E
		//T1
		this.mudaControles(this.memoriaDeControle[23]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[23]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[24]);
		this.esperaNormalizar();
		
		//R0 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[13]);
		this.esperaNormalizar();
		
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
	
	private void addEndR2R0(){
		//REND <- R2
		//T1
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[34]);
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
		
		//A <- R0 + E
		//T1
		this.mudaControles(this.memoriaDeControle[23]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[23]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[24]);
		this.esperaNormalizar();
		
		//R0 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[13]);
		this.esperaNormalizar();
		
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
	
	private void addEndR3R0(){
		//REND <- R3
		//T1
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[36]);
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
		
		//A <- R0 + E
		//T1
		this.mudaControles(this.memoriaDeControle[23]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[23]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[24]);
		this.esperaNormalizar();
		
		//R0 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[13]);
		this.esperaNormalizar();
		
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
	
	private void addEndR4R0(){
		//REND <- R4
		//T1
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[38]);
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
		
		//A <- R0 + E
		//T1
		this.mudaControles(this.memoriaDeControle[23]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[23]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[24]);
		this.esperaNormalizar();
		
		//R0 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[13]);
		this.esperaNormalizar();
		
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
	
	
	private void addConstR1(){
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
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[12]);
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
		
		//A <- R1+E
		//T1
		this.mudaControles(this.memoriaDeControle[39]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[39]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[40]);
		this.esperaNormalizar();
		
		//R1 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[41]);
		this.esperaNormalizar();

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
	
	private void addConstEndR1(){
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
					
		//PC <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[12]);
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
		
		//REND <- R1
		//T1
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[32]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	
	private void addR0R1(){
		//E <- R0
		//T1
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[42]);
		this.esperaNormalizar();
		
		//A <- R1+E
		//T1
		this.mudaControles(this.memoriaDeControle[39]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[39]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[40]);
		this.esperaNormalizar();
		
		//R1 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[41]);
		this.esperaNormalizar();
		
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
	
	private void addR2R1(){
		//A <- R1+R2
		//T1
		this.mudaControles(this.memoriaDeControle[43]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[43]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[44]);
		this.esperaNormalizar();
		
		//R1 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[41]);
		this.esperaNormalizar();
		
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

	private void addR3R1(){
		//A <- R1 + R3
		//T1
		this.mudaControles(this.memoriaDeControle[45]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[45]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[46]);
		this.esperaNormalizar();
		
		//R1 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[41]);
		this.esperaNormalizar();
		
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
	
	private void addR4R1(){
		//A <- R1 + R4
		//T1
		this.mudaControles(this.memoriaDeControle[47]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[47]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[48]);
		this.esperaNormalizar();
		
		//R1 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[41]);
		this.esperaNormalizar();
		
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
	
	private void addEndR0EndR1(){
		//REND <- R0
		//T1
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[15]);
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

		//REND <- R1
		//T1
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[32]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	
	private void addEndR2EndR1(){
		//REND <- R2
		//T1
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[34]);
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

		//REND <- R1
		//T1
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[32]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	
	private void addEndR3EndR1(){
		//REND <- R3
		//T1
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[36]);
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

		//REND <- R1
		//T1
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[32]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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

	private void addEndR4EndR1(){
		//REND <- R4
		//T1
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[38]);
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

		//REND <- R1
		//T1
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[32]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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

	private void addEndR0R1(){
		//REND <- R0
		//T1
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[15]);
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
		
		//A <- R1+E
		//T1
		this.mudaControles(this.memoriaDeControle[39]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[39]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[40]);
		this.esperaNormalizar();
		
		//R1 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[41]);
		this.esperaNormalizar();

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
	
	private void addEndR2R1(){
		//REND <- R2
		//T1
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[34]);
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
		
		//A <- R1+E
		//T1
		this.mudaControles(this.memoriaDeControle[39]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[39]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[40]);
		this.esperaNormalizar();
		
		//R1 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[41]);
		this.esperaNormalizar();

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
	private void addEndR3R1(){
		//REND <- R3
		//T1
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[36]);
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
		
		//A <- R1+E
		//T1
		this.mudaControles(this.memoriaDeControle[39]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[39]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[40]);
		this.esperaNormalizar();
		
		//R1 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[41]);
		this.esperaNormalizar();

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
	private void addEndR4R1(){
		//REND <- R4
		//T1
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[38]);
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
		
		//A <- R1+E
		//T1
		this.mudaControles(this.memoriaDeControle[39]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[39]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[40]);
		this.esperaNormalizar();
		
		//R1 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[41]);
		this.esperaNormalizar();

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
	
	
	private void addConstR2(){
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
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[12]);
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
		
		//E <- RDADO + R2
		//T1
		this.mudaControles(this.memoriaDeControle[49]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[49]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[50]);
		this.esperaNormalizar();
		
		//R2 <- E
		//T1
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[52]);
		this.esperaNormalizar();

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
	private void addConstEndR2(){
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
					
		//PC <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[12]);
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
		
		//REND <- R2
		//T1
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[34]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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

	private void addR0R2(){
		//A <- R0+R2
		//T1
		this.mudaControles(this.memoriaDeControle[25]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[25]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[26]);
		this.esperaNormalizar();
		
		//R2 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[53]);
		this.esperaNormalizar();
		
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
	
	private void addR1R2(){
		//A <- R1+R2
		//T1
		this.mudaControles(this.memoriaDeControle[43]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[43]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[44]);
		this.esperaNormalizar();		

		//R2 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[53]);
		this.esperaNormalizar();
		
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
	
	private void addR3R2(){		
		//A <- R3
		//T1
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[54]);
		this.esperaNormalizar();
		
		//E <- A+R2
		//T1
		this.mudaControles(this.memoriaDeControle[55]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[55]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[56]);
		this.esperaNormalizar();
		
		//R2 <- E
		//T1
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[52]);
		this.esperaNormalizar();

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
	
	private void addR4R2(){		
		//A <- R4
		//T1
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[57]);
		this.esperaNormalizar();
		
		//E <- A+R2
		//T1
		this.mudaControles(this.memoriaDeControle[55]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[55]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[56]);
		this.esperaNormalizar();
		
		//R2 <- E
		//T1
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[52]);
		this.esperaNormalizar();

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
	
	private void addEndR0EndR2(){
		//REND <- R0
		//T1
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[15]);
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

		//REND <- R2
		//T1
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[34]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	private void addEndR1EndR2(){
		//REND <- R1
		//T1
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[32]);
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

		//REND <- R2
		//T1
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[34]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	private void addEndR3EndR2(){
		//REND <- R3
		//T1
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[36]);
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

		//REND <- R2
		//T1
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[34]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	private void addEndR4EndR2(){
		//REND <- R4
		//T1
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[38]);
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

		//REND <- R2
		//T1
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[34]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	private void addEndR0R2(){
		//REND <- R0
		//T1
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[15]);
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

		//E <- RDADO + R2
		//T1
		this.mudaControles(this.memoriaDeControle[49]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[49]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[50]);
		this.esperaNormalizar();
		
		//R2 <- E
		//T1
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[52]);
		this.esperaNormalizar();

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
	private void addEndR1R2(){
		//REND <- R1
		//T1
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[32]);
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

		//E <- RDADO + R2
		//T1
		this.mudaControles(this.memoriaDeControle[49]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[49]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[50]);
		this.esperaNormalizar();
		
		//R2 <- E
		//T1
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[52]);
		this.esperaNormalizar();

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
	private void addEndR3R2(){
		//REND <- R3
		//T1
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[36]);
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

		//E <- RDADO + R2
		//T1
		this.mudaControles(this.memoriaDeControle[49]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[49]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[50]);
		this.esperaNormalizar();
		
		//R2 <- E
		//T1
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[52]);
		this.esperaNormalizar();

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
	private void addEndR4R2(){
		//REND <- R4
		//T1
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[38]);
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

		//E <- RDADO + R2
		//T1
		this.mudaControles(this.memoriaDeControle[49]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[49]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[50]);
		this.esperaNormalizar();
		
		//R2 <- E
		//T1
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[52]);
		this.esperaNormalizar();

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
	
	
	private void addConstR3(){
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
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[12]);
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
		
		//E <- RDADO + R3
		//T1
		this.mudaControles(this.memoriaDeControle[58]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[58]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[59]);
		this.esperaNormalizar();
		
		//R3 <- E
		//T1
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[60]);
		this.esperaNormalizar();
		
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
	private void addConstEndR3(){
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
					
		//PC <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[12]);
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
		
		//REND <- R3
		//T1
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[36]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	private void addR0R3(){
		//A <- R0 + R3
		//T1
		this.mudaControles(this.memoriaDeControle[27]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[27]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[28]);
		this.esperaNormalizar();
		
		//R3 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[61]);
		this.esperaNormalizar();
		
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
	private void addR1R3(){
		//A <- R1 + R3
		//T1
		this.mudaControles(this.memoriaDeControle[45]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[45]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[46]);
		this.esperaNormalizar();
		
		//R3 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[61]);
		this.esperaNormalizar();
		
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
	private void addR2R3(){
		//A <- R3
		//T1
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[54]);
		this.esperaNormalizar();
		
		//E <- A+R2
		//T1
		this.mudaControles(this.memoriaDeControle[55]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[55]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[56]);
		this.esperaNormalizar();
		
		//R3 <- E
		//T1
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[60]);
		this.esperaNormalizar();
		
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
	private void addR4R3(){
		//A <- R3
		//T1
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[54]);
		this.esperaNormalizar();
		
		//E <- A+R4
		//T1
		this.mudaControles(this.memoriaDeControle[62]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[62]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[63]);
		this.esperaNormalizar();
		
		//R3 <- E
		//T1
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[60]);
		this.esperaNormalizar();
		
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
	
	private void addEndR0EndR3(){
		//REND <- R0
		//T1
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[15]);
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

		//REND <- R3
		//T1
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[36]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	
	private void addEndR1EndR3(){
		//REND <- R1
		//T1
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[32]);
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

		//REND <- R3
		//T1
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[36]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	
	private void addEndR2EndR3(){
		//REND <- R2
		//T1
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[34]);
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

		//REND <- R3
		//T1
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[36]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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

	private void addEndR4EndR3(){
		//REND <- R4
		//T1
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[38]);
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

		//REND <- R3
		//T1
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[36]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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

	private void addEndR0R3(){
		//REND <- R0
		//T1
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[15]);
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

		//E <- RDADO + R3
		//T1
		this.mudaControles(this.memoriaDeControle[58]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[58]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[59]);
		this.esperaNormalizar();
		
		//R3 <- E
		//T1
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[60]);
		this.esperaNormalizar();

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
	
	private void addEndR1R3(){
		//REND <- R1
		//T1
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[32]);
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

		//E <- RDADO + R3
		//T1
		this.mudaControles(this.memoriaDeControle[58]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[58]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[59]);
		this.esperaNormalizar();
		
		//R3 <- E
		//T1
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[60]);
		this.esperaNormalizar();

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
	
	private void addEndR2R3(){
		//REND <- R2
		//T1
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[34]);
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

		//E <- RDADO + R3
		//T1
		this.mudaControles(this.memoriaDeControle[58]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[58]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[59]);
		this.esperaNormalizar();
		
		//R3 <- E
		//T1
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[60]);
		this.esperaNormalizar();

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
	
	private void addEndR4R3(){
		//REND <- R4
		//T1
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[38]);
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

		//E <- RDADO + R3
		//T1
		this.mudaControles(this.memoriaDeControle[58]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[58]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[59]);
		this.esperaNormalizar();
		
		//R3 <- E
		//T1
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[60]);
		this.esperaNormalizar();

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
	
	private void addConstR4(){
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
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[12]);
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
		
		//E <- RDADO + R4
		//T1
		this.mudaControles(this.memoriaDeControle[64]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[64]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[65]);
		this.esperaNormalizar();
		
		//R4 <- E
		//T1
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[66]);
		this.esperaNormalizar();
		
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
	
	private void addConstEndR4(){
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
					
		//PC <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[12]);
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
		
		//REND <- R4
		//T1
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[38]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	
	private void addEndR0EndR4(){
		//REND <- R0
		//T1
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[15]);
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

		//REND <- R4
		//T1
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[38]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	
	private void addEndR1EndR4(){
		//REND <- R1
		//T1
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[32]);
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

		//REND <- R4
		//T1
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[38]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	
	private void addEndR2EndR4(){
		//REND <- R2
		//T1
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[34]);
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

		//REND <- R4
		//T1
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[38]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	
	private void addEndR3EndR4(){
		//REND <- R3
		//T1
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[36]);
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

		//REND <- R4
		//T1
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[38]);
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
		
		//A <- RDADO + E
		//T1
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[16]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[17]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	
	private void addEndR0R4(){
		//REND <- R0
		//T1
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[15]);
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

		//E <- RDADO + R4
		//T1
		this.mudaControles(this.memoriaDeControle[64]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[64]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[65]);
		this.esperaNormalizar();
		
		//R4 <- E
		//T1
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[66]);
		this.esperaNormalizar();

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
	
	private void addEndR1R4(){
		//REND <- R1
		//T1
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[32]);
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

		//E <- RDADO + R4
		//T1
		this.mudaControles(this.memoriaDeControle[64]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[64]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[65]);
		this.esperaNormalizar();
		
		//R4 <- E
		//T1
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[66]);
		this.esperaNormalizar();

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
	
	private void addEndR2R4(){
		//REND <- R2
		//T1
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[34]);
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

		//E <- RDADO + R4
		//T1
		this.mudaControles(this.memoriaDeControle[64]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[64]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[65]);
		this.esperaNormalizar();
		
		//R4 <- E
		//T1
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[66]);
		this.esperaNormalizar();

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
	
	private void addEndR3R4(){
		//REND <- R3
		//T1
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[36]);
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

		//E <- RDADO + R4
		//T1
		this.mudaControles(this.memoriaDeControle[64]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[64]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[65]);
		this.esperaNormalizar();
		
		//R4 <- E
		//T1
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[51]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[66]);
		this.esperaNormalizar();

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
	
	private void subConstR0(){
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
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[12]);
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
			
		//A <- R0 - E
		//T1
		this.mudaControles(this.memoriaDeControle[67]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[67]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[68]);
		this.esperaNormalizar();
		
		//R0 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[13]);
		this.esperaNormalizar();
			
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
	
	private void subConstEndR0(){
		//REND <- PC+1 A<- PC+1
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
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[12]);
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
		
		//REND <- R0
		//T1
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[15]);
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
		
		//A <- RDADO - E
		//T1
		this.mudaControles(this.memoriaDeControle[69]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[69]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[70]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	
	private void subR1R0(){
		//E <- R1
		//T1
		this.mudaControles(this.memoriaDeControle[21]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[21]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[22]);
		this.esperaNormalizar();
		
		//A <- R0 - E
		//T1
		this.mudaControles(this.memoriaDeControle[67]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[67]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[68]);
		this.esperaNormalizar();
		
		//R0 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[13]);
		this.esperaNormalizar();
		
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
	
	private void subR2R0(){
		//A <- R0-R2
		//T1
		this.mudaControles(this.memoriaDeControle[71]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[71]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[72]);
		this.esperaNormalizar();
		
		//R0 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[13]);
		this.esperaNormalizar();
		
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
	
	private void subR3R0(){
		//A <- R0-R3
		//T1
		this.mudaControles(this.memoriaDeControle[73]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[73]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[74]);
		this.esperaNormalizar();
		
		//R0 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[13]);
		this.esperaNormalizar();
		
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
	
	private void subR4R0(){
		//A <- R0-R4
		//T1
		this.mudaControles(this.memoriaDeControle[75]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[75]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[76]);
		this.esperaNormalizar();
		
		//R0 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[13]);
		this.esperaNormalizar();
		
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
	
	private void subEndR1EndR0(){
		//REND <- R1
		//T1
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[32]);
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

		//REND <- R0
		//T1
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[15]);
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
		
		//A <- RDADO - E
		//T1
		this.mudaControles(this.memoriaDeControle[69]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[69]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[70]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	
	private void subEndR2EndR0(){
		//REND <- R2
		//T1
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[34]);
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

		//REND <- R0
		//T1
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[15]);
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
		
		//A <- RDADO - E
		//T1
		this.mudaControles(this.memoriaDeControle[69]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[69]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[70]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	
	private void subEndR3EndR0(){
		//REND <- R3
		//T1
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[36]);
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

		//REND <- R0
		//T1
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[15]);
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
		
		//A <- RDADO - E
		//T1
		this.mudaControles(this.memoriaDeControle[69]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[69]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[70]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	
	private void subEndR4EndR0(){
		//REND <- R4
		//T1
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[38]);
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

		//REND <- R0
		//T1
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[15]);
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
		
		//A <- RDADO - E
		//T1
		this.mudaControles(this.memoriaDeControle[69]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[69]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[70]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	
	private void subEndR1R0(){
		//REND <- R1
		//T1
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[32]);
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
		
		//A <- R0 - E
		//T1
		this.mudaControles(this.memoriaDeControle[67]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[67]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[68]);
		this.esperaNormalizar();
		
		//R0 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[13]);
		this.esperaNormalizar();
		
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
	
	private void subEndR2R0(){
		//REND <- R2
		//T1
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[33]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[34]);
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
		
		//A <- R0 - E
		//T1
		this.mudaControles(this.memoriaDeControle[67]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[67]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[68]);
		this.esperaNormalizar();
		
		//R0 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[13]);
		this.esperaNormalizar();
		
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
	
	private void subEndR3R0(){
		//REND <- R3
		//T1
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[35]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[36]);
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
		
		//A <- R0 - E
		//T1
		this.mudaControles(this.memoriaDeControle[67]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[67]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[68]);
		this.esperaNormalizar();
		
		//R0 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[13]);
		this.esperaNormalizar();
		
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
	
	private void subEndR4R0(){
		//REND <- R4
		//T1
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[37]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[38]);
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
		
		//A <- R0 - E
		//T1
		this.mudaControles(this.memoriaDeControle[67]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[67]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[68]);
		this.esperaNormalizar();
		
		//R0 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[13]);
		this.esperaNormalizar();
		
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
	
	private void subConstR1(){
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
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[12]);
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
		
		//A <- R1 - E
		//T1
		this.mudaControles(this.memoriaDeControle[77]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[77]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[78]);
		this.esperaNormalizar();
		
		//R1 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[41]);
		this.esperaNormalizar();

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
	
	private void subConstEndR1(){
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
					
		//PC <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[12]);
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
		
		//REND <- R1
		//T1
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[31]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[32]);
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
		
		//A <- RDADO - E
		//T1
		this.mudaControles(this.memoriaDeControle[69]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[69]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[70]);
		this.esperaNormalizar();
		
		//RDADO <- A
		//T1
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[18]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[19]);
		this.esperaNormalizar();
		
		//MEMORIA <- RDADO
		//T1, T2 e T3
		this.mudaControles(this.memoriaDeControle[20]);
		this.esperaNormalizar();
		
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
	
	private void subR0R1(){
		//E <- R0
		//T1
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[14]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[42]);
		this.esperaNormalizar();
		
		//A <- R1 - E
		//T1
		this.mudaControles(this.memoriaDeControle[77]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[77]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[78]);
		this.esperaNormalizar();
		
		//R1 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[41]);
		this.esperaNormalizar();
		
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
	
	private void subR2R1(){
		//A <- R1 - R2
		//T1
		this.mudaControles(this.memoriaDeControle[79]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[79]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[80]);
		this.esperaNormalizar();
		
		//R1 <- A
		//T1
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T2
		this.mudaControles(this.memoriaDeControle[11]);
		this.esperaNormalizar();
		//T3
		this.mudaControles(this.memoriaDeControle[41]);
		this.esperaNormalizar();
		
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
		System.out.println("R0 = " + FuncoesAuxiliares.getIntNumber(R0.getValue()));
		System.out.println("R1 = " + FuncoesAuxiliares.getIntNumber(R1.getValue()));
		System.out.println("R2 = " + FuncoesAuxiliares.getIntNumber(R2.getValue()));
		System.out.println("R3 = " + FuncoesAuxiliares.getIntNumber(R3.getValue()));
		System.out.println("R4 = " + FuncoesAuxiliares.getIntNumber(R4.getValue()));
		System.out.println("PC = " + FuncoesAuxiliares.getIntNumber(PC.getValue()));
		System.out.println("A = " + FuncoesAuxiliares.getIntNumber(A.getValue()));
		System.out.println("B = " + FuncoesAuxiliares.getIntNumber(B.getValue()));
		System.out.println("C = " + FuncoesAuxiliares.getIntNumber(C.getValue()));
		System.out.println("D = " + FuncoesAuxiliares.getIntNumber(D.getValue()));
		System.out.println("E = " + FuncoesAuxiliares.getIntNumber(E.getValue()));
		System.out.println("F = " + FuncoesAuxiliares.getIntNumber(F.getValue()));
		System.out.println("G = " + FuncoesAuxiliares.getIntNumber(G.getValue()));
		System.out.println("H = " + FuncoesAuxiliares.getIntNumber(H.getValue()));
		System.out.println("I = " + FuncoesAuxiliares.getIntNumber(I.getValue()));
		System.out.println("RDADO = " + FuncoesAuxiliares.getIntNumber(RDADO.getValue()));
		System.out.println("REND = " + FuncoesAuxiliares.getIntNumber(REND.getValue()));
		System.out.println("IR = " + FuncoesAuxiliares.getIntNumber(IR.getValue()));
		System.out.println("Memória["+FuncoesAuxiliares.getIntNumber(this.R0.getValue())+ "] = " + FuncoesAuxiliares.getIntNumber(this.memory.getWord(FuncoesAuxiliares.getIntNumber(this.R0.getValue()))));
		System.out.println("Memória["+FuncoesAuxiliares.getIntNumber(this.R1.getValue())+ "] = " + FuncoesAuxiliares.getIntNumber(this.memory.getWord(FuncoesAuxiliares.getIntNumber(this.R1.getValue()))));
		System.out.println("Memória["+FuncoesAuxiliares.getIntNumber(this.R2.getValue())+ "] = " + FuncoesAuxiliares.getIntNumber(this.memory.getWord(FuncoesAuxiliares.getIntNumber(this.R2.getValue()))));
		System.out.println("Memória["+FuncoesAuxiliares.getIntNumber(this.R3.getValue())+ "] = " + FuncoesAuxiliares.getIntNumber(this.memory.getWord(FuncoesAuxiliares.getIntNumber(this.R3.getValue()))));
		System.out.println("Memória["+FuncoesAuxiliares.getIntNumber(this.R4.getValue())+ "] = " + FuncoesAuxiliares.getIntNumber(this.memory.getWord(FuncoesAuxiliares.getIntNumber(this.R4.getValue()))));
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