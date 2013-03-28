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
			
			//RDADO <- MEM�RIA
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
		}
	}
	
	private void criarMemoriaDeControle(){
		
		this.memoriaDeControle = new boolean[1024][31];
		
		// REND <- PC+1 e A <- PC+1
		this.memoriaDeControle[0] = FuncoesAuxiliares.getNumber(Integer.parseInt("1110000010", 2), 31);
		this.memoriaDeControle[1] = FuncoesAuxiliares.getNumber(Integer.parseInt("10000000000000010001110000010", 2), 31);
		
		// RDADO <- MEM�RIA
		this.memoriaDeControle[2] = FuncoesAuxiliares.getNumber(Integer.parseInt("1", 2), 31);
		this.memoriaDeControle[3] = FuncoesAuxiliares.getNumber(Integer.parseInt("100000000000001", 2), 31);
		
		// E <- RDADO
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
		
		//MEM�RIA <- RDADO
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
		
		//RDADO <- MEM�RIA
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
		System.out.println("Mem�ria["+FuncoesAuxiliares.getIntNumber(this.R0.getValue())+ "] = " + FuncoesAuxiliares.getIntNumber(this.memory.getWord(FuncoesAuxiliares.getIntNumber(this.R0.getValue()))));
	}
	
	private void mudaControles(boolean[] controladores){
		if(controladores.length != 31){
			System.out.println("N�o est� mudando os controladores");
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