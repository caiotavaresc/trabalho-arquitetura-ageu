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
		
		switch(number){
			case 0:
				this.halt();
				break;
			
			case 1:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 2:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 3:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 4:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 5:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;				
				
			case 6:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 7:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 8:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 9:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 10:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 11:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 12:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 13:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 14:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 15:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 16:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
			
			case 17:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
			
			case 18:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 19:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 20:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 21:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 22:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 23:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 24:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 25:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 26:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 27:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 28:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 29:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 30:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 31:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 32:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 33:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 34:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 35:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 36:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 37:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 38:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 39:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 40:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 41:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 42:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 43:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 44:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 45:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 46:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 47:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 48:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 49:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 50:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 51:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 52:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 53:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 54:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 55:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 56:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 57:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 58:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 59:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 60:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 61:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 62:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 63:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
			
			case 64:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 65:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 66:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 67:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 68:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 69:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 70:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 71:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 72:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 73:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 74:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 75:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 76:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 77:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 78:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 79:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 80:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 81:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 82:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 83:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 84:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 85:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 86:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 87:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 88:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 89:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 90:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 91:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 92:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 93:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 94:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 95:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 96:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 97:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 98:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 99:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 100:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 101:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 102:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 103:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 104:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 105:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 106:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 107:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 108:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 109:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 110:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 111:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 112:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 113:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 114:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 115:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 116:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 117:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 118:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 119:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 120:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 121:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 122:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 123:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 124:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 125:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 126:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 127:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 128:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 129:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 130:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 131:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 132:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 133:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 134:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 135:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 136:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 137:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 138:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 139:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 140:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 141:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 142:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 143:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 144:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 145:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 146:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 147:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 148:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 149:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 150:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 151:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 152:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 153:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 154:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 155:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 156:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 157:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 158:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 159:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 160:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 161:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 162:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 163:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 164:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 165:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 166:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 167:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 168:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 169:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 170:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 171:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 172:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 173:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 174:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 175:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 176:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 177:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 178:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 179:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 180:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 181:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 182:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 183:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 184:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 185:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 186:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 187:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 188:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 189:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 190:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 191:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 192:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 193:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 194:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 195:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 196:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 197:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 198:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 199:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 200:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 201:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 202:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 203:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 204:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 205:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 206:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 207:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 208:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 209:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 210:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 211:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 212:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 213:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 214:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 215:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 216:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 217:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 218:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 219:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 220:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 221:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 222:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 223:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 224:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 225:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 226:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 227:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 228:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 229:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 230:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 231:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 232:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 233:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 234:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 235:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 236:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 237:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 238:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 239:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 240:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 241:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 242:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 243:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 244:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 245:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 246:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 247:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 248:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 249:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 250:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 251:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 252:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 253:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 254:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 255:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 256:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 257:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 258:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 259:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 260:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 261:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 262:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 263:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 264:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 265:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 266:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 267:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 268:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 269:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 270:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 271:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 272:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 273:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 274:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 275:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 276:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 277:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 278:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 279:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 280:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 281:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 282:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 283:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 284:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 285:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 286:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 287:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 288:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 289:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 290:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 291:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 292:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 293:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 294:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 295:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 296:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 297:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 298:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 299:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 300:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 301:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 302:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 303:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 304:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 305:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 306:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 307:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 308:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 309:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 310:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 311:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 312:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 313:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 314:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 315:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 316:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 317:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 318:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 319:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 320:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 321:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 322:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 323:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 324:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 325:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 326:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 327:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 328:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 329:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 330:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 331:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 332:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 333:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 334:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 335:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 336:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 337:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 338:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 339:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 340:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 341:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 342:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 343:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 344:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 345:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 346:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 347:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 348:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 349:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 350:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 351:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 352:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 353:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 354:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 355:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 356:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 357:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 358:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 359:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 360:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 361:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 362:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 363:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 364:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 365:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 367:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 368:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 369:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 370:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 371:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 372:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 373:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 374:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 375:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 376:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 377:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 378:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 379:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 380:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 381:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 382:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 383:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 384:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 385:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 386:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 387:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 388:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 389:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 390:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 391:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 392:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 393:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 394:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 395:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 396:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 397:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 399:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
				break;
				
			case 400:
				this.operationRxRy(OperationBetween.ADD, Registrador.Const, Registrador.R0);
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