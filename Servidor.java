import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.util.concurrent.Semaphore;
import java.lang.Math;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor{

	static final String mapa_string="444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444440000222222244444444444444444444444444444444444444444444440000100000144444444444444444444444444444444444444444444443003100000144444444444444444444444444444444444444444444441001100000144444444444444444444444444444444444444444444441001100000144444444444444444444444444444444444444444444441000000000144444444444222222222244444444444444444444444441001222222244444444444100000000144444444444444444444444441001222222244444444444100000000122222222244444444444444441001100000133333333333100000000110000000144444444444444441001100000100000000001000000000110000000144444444444444441001100000122222222022100000000110000000144444444444444441001100000110000011001100000000110000000144444444444444441001100000110000011000122222202110000000144444444444444441000000000110000011000012222100122202222244444444444444441001222222210000011000011001100122202222244444444444444441001442222210000011000011001100110000000144444444444444441001441000110000011000011000000110000000144444444444444441001442220222222022220222222100110000000144444444444444441001222220222222022220222222100110000000144444444444444441000000000000000000000000000000000000000144444444444444441000000000000000000000000000000000000000144444444444444442222222222222222222222222222222222222222244444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444";


	public String player1 = "0;0";
	public String player2 = "19;19";
	final Semaphore sem1 = new Semaphore(1);
	final Semaphore sem2 = new Semaphore(1);
	static final String[] USERNAMES={"Ana", "Simon", "Alberto", "Pedro", "German"};
	  
	public Servidor(){
		PosAleatorio();
	}
  
	void dibuja(){ //IMPRIME LOS JUGADORES EN EL MAPA
		String[] pos1 = player1.split(";");
		String[] pos2 = player2.split(";");

		int x1 = Integer.parseInt(pos1[0]);
		int y1 = Integer.parseInt(pos1[1]);

		int x2 = Integer.parseInt(pos2[0]);
		int y2 = Integer.parseInt(pos2[1]);
		
		System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); //Un buen clear
        	for(int i=-1; i<20+1; i++){
        		System.out.print("\n");
			for(int j=-1; j<20+1; j++){                        
				if(x1==i && y1==j){ System.out.print("X "); }
				else if(x2==i && y2==j){ System.out.print("O "); }
				else {
					if(i==-1 || i==20){ System.out.print("| "); }
					else if(j==-1 || j==20){ System.out.print("- "); }
 					else{ System.out.print("  "); }
				}
			}
		}
	
	
	}

	public void PosAleatorio(){ //POSICION ALEATORIA PARA EL COMIENZO DEL JUEGO
		Random rand=new Random();

		int player1_x = rand.nextInt(20);
		int player1_y = rand.nextInt(20);

		int player2_x = rand.nextInt(20);
		int player2_y = rand.nextInt(20);

		player1 = player1_x + ";" + player1_y;
		player2 = player2_x + ";" + player2_y;
	}



	public double Distancia(){ //CALCULA LA DISTANCIA. UTILIZADO PARA COMPROBAR EL FIN DEL JUEGO
		String[] pos1 = player1.split(";");
		String[] pos2 = player2.split(";");

		int x1 = Integer.parseInt(pos1[0]);
		int y1 = Integer.parseInt(pos1[1]);

		int x2 = Integer.parseInt(pos2[0]);
		int y2 = Integer.parseInt(pos2[1]);

		double resta_x=x1-x2;
		double resta_y=y1-y2;

		return Math.sqrt(resta_x*resta_x+resta_y*resta_y);
	}


	public boolean EntradaCorrecta(String username){ //COMPRUEBA QUE EL USUARIO INTRODUCIDO ESTA ACEPTADO
		for(int i=0; i<USERNAMES.length; i++){
			if(USERNAMES[i].equals(username))
				return true;
		}
		return false;
	}

	public class Servidor1 extends Thread{
		@Override
		public void run(){
	      		Socket socketServicio=null;
      			ServerSocket serverSocket=null;
			int puerto = 2052; //RECIBE DEL CLIENTE 1

			String respuesta;
			String datosRecibidos;

			try{

          			serverSocket = new ServerSocket(puerto);
				socketServicio = serverSocket.accept();

				InputStream inputStream=socketServicio.getInputStream();
				OutputStream outputStream=socketServicio.getOutputStream();
				PrintWriter outPrinter = new PrintWriter (outputStream , true);
				BufferedReader inReader = new BufferedReader (new InputStreamReader(inputStream));

				boolean correcto=false;

          			do{	
            				datosRecibidos = inReader.readLine();
            				
            				if(EntradaCorrecta(datosRecibidos)){
              					outPrinter.println("X");
              					correcto=true;
            				}else{
              					outPrinter.println("FAILED");
              					System.out.println("Usuario " + datosRecibidos + " no aceptado en el servidor.");
              					socketServicio = serverSocket.accept();              					
            				}
          			}while(!correcto);

          			System.out.println("Conectado cliente 1");

          			outPrinter.println(player1);
          			
          			outPrinter.println(mapa_string);

				// No vale pillar en diagonal; en tal caso seria while(Distancia() > sqrt(2))
          			while(Distancia() > 1){
          				//dibuja();
					datosRecibidos = inReader.readLine();
					
					sem1.acquire();
					player1 = datosRecibidos;
					sem1.release();

					sem2.acquire();
					datosRecibidos = player2;
					sem2.release();

					outPrinter.println(datosRecibidos);
					outPrinter.flush();
          			}
          			
          			outPrinter.println("FIN");
          			
          			System.out.println("Cliente 1 desconectado.");
          			
          			socketServicio.close();
          			
			}catch(IOException e){
				System.out.println("SERVIDOR 1");
				System.out.println("Error: no se pudo atender en el puerto " + puerto + ".");
			}catch (InterruptedException ex) {
				Logger.getLogger(Servidor1.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public class Servidor2 extends Thread{
		@Override
		public void run(){
	      		Socket socketServicio=null;
      			ServerSocket serverSocket=null;
			int puerto = 2053; //RECIBE DEL CLIENTE 2

			String respuesta;
			String datosRecibidos;

			try{

          			serverSocket = new ServerSocket(puerto);
				socketServicio = serverSocket.accept();
				
				InputStream inputStream=socketServicio.getInputStream();
				OutputStream outputStream=socketServicio.getOutputStream();
				PrintWriter outPrinter = new PrintWriter (outputStream , true);
				BufferedReader inReader = new BufferedReader (new InputStreamReader(inputStream));

				boolean correcto=false;

          			do{
            				datosRecibidos = inReader.readLine();
            				
            				if(EntradaCorrecta(datosRecibidos)){
              					outPrinter.println("O");
              					correcto=true;
            				}else{
              					outPrinter.println("FAILED");
              					System.out.println("Usuario " + datosRecibidos + " no aceptado en el servidor.");
            				}
          			}while(!correcto);

          			System.out.println("Conectado cliente 2");

          			outPrinter.println(player2);
          			
          			outPrinter.println(mapa_string);

				// No vale pillar en diagonal; en tal caso seria while(Distancia() > sqrt(2))
          			while(Distancia() > 1){
          				//dibuja();
					datosRecibidos = inReader.readLine();

					sem2.acquire();
					player2 = datosRecibidos;
					sem2.release();

					sem1.acquire();
					datosRecibidos = player1;
					sem1.release();

					outPrinter.println(datosRecibidos);
					outPrinter.flush();
          			}
          			
          			outPrinter.println("FIN");
          			
          			System.out.println("Cliente 2 desconectado.");
          			
          			socketServicio.close();
          			
			}catch(IOException e){
				System.out.println("SERVIDOR 2");
				System.out.println("Error: no se pudo atender en el puerto " + puerto + ".");
			}catch (InterruptedException ex) {
				Logger.getLogger(Servidor2.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}


 
	public void run(){ //INICIALIZA LOS SERVIORES Y LANZA EL PROGRAMA
		Servidor1 servidor1;
		Servidor2 servidor2;
		servidor1 = new Servidor1();
		servidor2 = new Servidor2();
		servidor1.start();
		servidor2.start();
	}

	public static void main(String[] args) throws IOException {
		Servidor obj = new Servidor();
		obj.run();
	}
	
}
