public class Player {
	public char[][] mapa=new char[100][100];
	int total_fil=38, total_col=57;
	public int pos_x;
	public int pos_y;
	public char letra;

	public Player(int x, int y, char l){
		pos_x=x;
		pos_y=y;
		letra=l;
	}

	public void Derecha(){
		if(pos_y!=100 && mapa[pos_x][pos_y+1]=='0')	pos_y++;
	}

	public void Izquierda(){
		if(pos_y!=0 && mapa[pos_x][pos_y-1]=='0')	pos_y--;
	}
	
	public void Abajo(){
		if(pos_x!=100 && mapa[pos_x+1][pos_y]=='0')	pos_x++;
	}

	public void Arriba(){
		if(pos_x!=0 && mapa[pos_x-1][pos_y]=='0')	pos_x--;
	}
	
	public double distancia(Player otro){
		double resta_x=otro.pos_x-pos_x;
	    	double resta_y=otro.pos_y-pos_y;
		return Math.sqrt(resta_x*resta_x+resta_y*resta_y);
	}
	
	void InicializaMapa(String mapa_string){
		int k=0;
		for(int i=0; i<total_fil; i++){
			for(int j=0; j<total_col; j++){
				mapa[i][j]=mapa_string.charAt(k);
				k++;
			}
		}
	}
}	
