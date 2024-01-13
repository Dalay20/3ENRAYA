/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.juego1;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author naomi
 */
public class TablaController implements Initializable,Runnable{
    ModeloController modelo;
    Jugador jugador1,jugador2;
    ComputadoraIA computadora;
    boolean jugando, terminado;
    public final int HOMBREvsHOMBRE = 1;
    public final int HOMBREvsCOMPUTADORA = 2;
    public final int COMPUTADORAvsCOMPUTADORA = 3;
    public final int JUGADOR1 = 1;
    public final int JUGADOR2 = 2;
    public boolean PENSANDO = false;
    @FXML
    private AnchorPane lblPerdidos;
    @FXML
    private Label lblEstado;
    @FXML
    private ImageView f1;
    @FXML
    private ImageView f2;
    @FXML
    private ImageView f3;
    @FXML
    private ImageView f4;
    @FXML
    private ImageView f5;
    @FXML
    private ImageView f6;
    @FXML
    private ImageView f7;
    @FXML
    private ImageView f8;
    @FXML
    private ImageView f9;
    @FXML
    private Label Tablero;
    @FXML
    private Label lblGanados;
    @FXML
    private Label lblEmpatados;
    @FXML
    private Label lblIcono;
    @FXML
    private Label lblGanados2;
    @FXML
    private Label lblPerdidos2;
    @FXML
    private Label lblEmpatados2;
    @FXML
    private Label lblIcono2;
    @FXML
    private Label lblPlayer;
    @FXML
    private Label lblPlayer2;
    @FXML
    private MenuItem mnuIniciar;
    @FXML
    private MenuItem mnuSuspender;
    @FXML
    private MenuItem mnuEstadisticas;
    @FXML
    private Label Fondo;
    @FXML
    private Label blPerdidos;
    
    private ImageView imagen1;
    private ImageView imagen2;
    /**
     * Initializes the controller class.
     */
     /*Turno de jugador.*/
    ConfigurarController config = new ConfigurarController();
    
    ImageView[] fichas = new ImageView[9];
    int turno = 0;
    int turnoGeneral = 0;
    
    /*Matriz que representa el juego.*/
    int[] tablero = new int[9];

    /*Tablero en componentes.*/
   // Label fichas[]; //PREGUNTAR ESTO
    @FXML
    private MenuButton mnuJuego;
    @FXML
    private Button btnconfigurar;
    
    
    
   public TablaController() {
    
}
   
   public ModeloController getModeloController() {
        return modelo;
    }
    
    public void setModeloController(ModeloController modeloController) {
        this.modelo = modeloController;
        System.out.println("ModeloController recibido en TablaController");
    }
    
//    public ConfigurarController getConfigurarController() {
//        return config;
//    }
//    
//    public void setModeloController(ConfigurarController confiController) {
//        this.config = confiController;
//        System.out.println("ModeloController recibido en TablaController");
//    }
    public void someMethod() {
        if (modelo != null) {
            int tipo_juego = modelo.tipo_juego;
            String nombre1 = modelo.nombre1;         
            String nombre2 = modelo.nombre2;
            
//            ImageView imagen=config.fichas[0];
//            ImageView imagen2=config.fichas[1];}
           

            // Realiza acciones con los datos
            System.out.println("Datos de ModeloController en TablaController: " + tipo_juego + ", " + nombre1 + ", " + nombre2);
        } else {
            System.out.println("ModeloController no ha sido establecido en TablaController");
        }
    }
    public void run(){
        
    }
     public void movimiento( ImageView ficha ){
        /*Colocamos la ficha.*/
        if ( jugando ){
            
           
            if( !PENSANDO )
                ponerFicha( ficha );//TRABAJAR
            
            
            if ( this.modelo.tipo_juego == HOMBREvsCOMPUTADORA && this.turno == JUGADOR2 ){
                PENSANDO = true;
                ponerFichaCPU(computadora.movimiento( this.tablero )); //TRABAJAR
                PENSANDO = false;
                
            }

        }
        /*Si se va a comenzar un juego nuevo*/
        if( terminado ){
            reiniciarJuego(); //TRABAJAR
            return;
        }
        
        /*Preguntamos si el juego termino o alguien ganó.*/
        if ( terminado() != 0){  
            
            /*Asignamos resultados.*/
            if ( terminado() == 1 ){
                jugador1.gano();    
                jugador2.perdio();   
                mensaje(jugador1.nombre + " ganó!");  
            }else{
                jugador2.gano(); //TRABAJAR
                jugador1.perdio();  //TRABAJAR
                mensaje(jugador2.nombre + " ganó!");  
            }
            
            /*Mostramos la información.*/
            mostrarInformacion();  
            
            /*Detenemos el juego actual.*/
            jugando = false;
            terminado = true;
            
            
        } else if ( lleno() ){   //TRABAJAR
            /*Asignamos resultados.*/
            jugador1.empato();
            jugador2.empato();
            mensaje("Empate!");  //TRABAJAR
            
            /*Mostramos la información.*/
            mostrarInformacion();   //TRABAJAR
            
            /*Detenemos el juego actual.*/
            jugando = false;
            terminado = true;
        }
        
        /*Movemos el foco al otro jugador.*/
        cambiarFoco();  //TRABAJAR
    }
     
     /*Método que pone una ficha por la computadora.*/
     public void ponerFichaCPU( int indice ){
        
        if( indice == -1 ) return;
        
        switch ( indice ){//editado el seticon por el setgrafic
            case 0: this.f1.setImage(jugador2.obtenFicha().getImage()); break;
            case 1: this.f2.setImage(jugador2.obtenFicha().getImage()); break;
            case 2: this.f3.setImage(jugador2.obtenFicha().getImage()); break;
            case 3: this.f4.setImage(jugador2.obtenFicha().getImage()); break;
            case 4: this.f5.setImage(jugador2.obtenFicha().getImage()); break;
            case 5: this.f6.setImage(jugador2.obtenFicha().getImage()); break;
            case 6: this.f7.setImage(jugador2.obtenFicha().getImage()); break;
            case 7: this.f8.setImage(jugador2.obtenFicha().getImage()); break;
            case 8: this.f9.setImage(jugador2.obtenFicha().getImage()); break;       
        }
        
        this.tablero[indice] = 2;
        
        /*Cambiamos el turno.*/
        turno = ( turno == JUGADOR1 ) ? JUGADOR2 : JUGADOR1;            
    } 
     
    /*Método que "pone una ficha" en el tablero.*/
    public void ponerFicha( ImageView ficha ){

        /*Obtenemos la casilla.*/
        int casilla = Integer.parseInt(""+ficha.getId().charAt(1)) - 1;// VERIFICAR ESE CAMBIO
        
        /*Comprobamos si la casilla no estaba ocupada.*/
        if ( estaOcupada(casilla ) )
            return;
        
        /*Elegimos la ficha según el turno*/
        if ( turno == JUGADOR1 )
            ficha.setImage(jugador1.obtenFicha().getImage());//editado el seticon
        else
            ficha.setImage(jugador2.obtenFicha().getImage());//editado el seticon
        
        /*Guardamos la representación en el tablero*/
        tablero[casilla] = turno;
        
        /*Cambiamos el turno.*/
        turno = ( turno == JUGADOR1 ) ? JUGADOR2 : JUGADOR1;
        
    }
    
     /*Método que dice si el juego está terminado.*/
    /*Regresa 0 si nadie gana, 1 si gana jugador 1 y 2 si gana jugador 2*/
    public int terminado(){
        /*Comprobamos si el juego terminó.*/
        /*Filas*/
        if ( tablero[0] == tablero[1] && tablero[0] == tablero[2] && tablero[0] != 0 )
            return tablero[0];
        else if ( tablero[3] == tablero[4] && tablero[3] == tablero[5]  && tablero[3] != 0  )
            return tablero[3];
        else if ( tablero[6] == tablero[7] && tablero[6]== tablero[8]  && tablero[6] != 0 )
            return tablero[6];
        /*Columnas*/
        else if( tablero[0] == tablero[3] && tablero[0] == tablero[6]  && tablero[0] != 0 )
            return tablero[0];
        else if ( tablero[1] == tablero[4] && tablero[1] == tablero[7]  && tablero[1] != 0  )
            return tablero[1];
        else if ( tablero[2] == tablero[5] && tablero[2] == tablero[8]  && tablero[2] != 0 )
            return tablero[2];
        /*Diagonales*/
        else if ( tablero[0] == tablero[4] && tablero[0] == tablero[8] && tablero[0] !=0 )
            return tablero[0];
        else if ( tablero[2] == tablero[4] && tablero[2] == tablero[6] && tablero[2] != 0 )
            return tablero[2];
        
        return 0;
        
    }
    
     /*Método que nos dice si el tablero se llenó.*/
    public boolean lleno(){
        boolean res = true;
        for ( int i = 0; i < tablero.length; i ++ )
            if ( tablero[i] == 0 )
                res = false;
        
        return res;
    }
    
    /*Método que nos dice si una casilla está ocupada.*/
    public boolean estaOcupada( int casilla ){
        return ( tablero[casilla] != 0 );
    }
    
    
    /*Método que inicia los componentes del Gato.*/
    private void iniciarComponentes(){  
        /*Referenciamos todas las etiquetas.*/
        fichas = new ImageView[9];
        fichas[0] = f1; fichas[1] = f2; fichas[2] = f3;
        fichas[3] = f4; fichas[4] = f5; fichas[5] = f6;
        fichas[6] = f7; fichas[7] = f8; fichas[8] = f9;
        
        
        /*Cursor para los componentes.*/
        for ( int i = 0; i < 9; i ++ )
       
        fichas[i].setCursor(Cursor.HAND);
    }
    
    
    
    
    public void mostrarEstadisticas(){ //TRABAJARLOCAL!!
        
    }
    
    public void mensaje(String mensaje){
        this.lblEstado.setText(mensaje);
    }
    
    public void cambiarFoco(){       
        /*Si estamos jugando.*/
        if ( !jugando )
            return;
    
        /*Si es turno del primer jugador..*/
        if ( turno == JUGADOR1 ){
            mensaje("Turno de " + jugador1.nombre );           
        } else {
           mensaje("Turno de " + jugador2.nombre );
        }       
    }
  
   
    
    /*Método que inicia el juego una vez obtenido el modelo.*/
    public void iniciarJuego(){  // ------------ FICHA PREDETERMINADA-------------
        /*Creamos los jugadores según el tipo de juego.*/
        System.out.println("estoo sale "+modelo.tipo_juego);
        System.out.println(HOMBREvsHOMBRE);
        if ( modelo.tipo_juego == HOMBREvsHOMBRE ){
            System.out.println(" pasa el primer filtro");
          // this.jugador1 = new Jugador( modelo.nombre1,new ImageView( new Image(getClass().getResourceAsStream("/images/circulo3.png"))) );  
            
    this.jugador1 = new Jugador( modelo.nombre1,modelo.imagen11 ); // PARA CAMBIAR LAS FICHAS
    this.jugador2 = new Jugador( modelo.nombre1,modelo.imagen22 );  //PARA CAMBIAR LAS FICGAS
           
            //this.jugador2 = new Jugador( modelo.nombre2, new ImageView( new Image(getClass().getResourceAsStream("/images/cruz.png"))) ); 
            
            /*Mostramos su información, asignamos los nombres de jugador al panel.*/
           
            mostrarInformacion();
        } else {
            /*Jugadores*/
            this.jugador1 = new Jugador( modelo.nombre1, modelo.imagen11);
            this.jugador2 = new Jugador ( "Computadora", modelo.imagen22);
            this.lblPlayer2.setVisible(true);//verificar
            
            /*Creamos la instancia para la computadora.*/
            computadora = new ComputadoraIA();
            
            /*Mostramos su información, asignamos los nombres de jugador al panel.*/
            mostrarInformacion();
        }
        
        /*Iniciamos el turno en jugador 1*/
        this.turno = 1;
        this.turnoGeneral = JUGADOR1;
        
        /*Variables de juego.*/
        jugando = true;
        terminado = false;
        
        /*Deshabilitamos el menú nuevo juego.*/
        this.mnuIniciar.setDisable(false);
        this.mnuSuspender.setDisable(false);
       // this.panelEstadisticas.setVisible(false);
        
        /*Movemos el foco.*/
        cambiarFoco();
        
    }
    
    public void mostrarInformacion(){ 
        
        /*Establecemos el título.*/
        lblPlayer.setText(jugador1.nombre );
        lblPlayer2.setText( jugador2.nombre );
        
        /*Establecemos las estadísticas del jugador.*/
        this.lblGanados.setText("Ganados: " + jugador1.GANADOS );
        this.blPerdidos.setText("Perdidos: " + jugador1.PERDIDOS );
        this.lblEmpatados.setText("Empatados: " + jugador1.EMPATADOS );
        this.lblIcono.setGraphic(jugador1.obtenFicha() );
        
        this.lblGanados2.setText("Ganados: " + jugador2.GANADOS );
        this.lblPerdidos2.setText("Perdidos: " + jugador2.PERDIDOS );
        this.lblEmpatados2.setText("Empatados: " + jugador2.EMPATADOS );
        this.lblIcono2.setGraphic(jugador2.obtenFicha() );
        
        this.lblPlayer.setVisible(true); //es la ventada del jugaddor pero por ahora solo vamos a ocultara o mostrarel nombre
        this.lblPlayer.setVisible(true);
       // if( this.modelo.tipo_juego != HOMBREvsCOMPUTADORA)
           // this.lblPlayer2.setVisible(true);// ververver
    }
    /*Método que inicia un nuevo juego.*/
    public void reiniciarJuego(){  //NO SE VE MUY IMPORTANTE PERO HAY QUE TRABAJARLO SI ES QUE SIRVE
        
        //Llenamos el tablero con 0s*/
        Arrays.fill(tablero,0);
        
        /*Borramos los iconos.*/
        for ( int i = 0; i < 9; i ++ )
            fichas[i].setImage(null);
        
       
        /*Cambiamos el turno General.*/
        if ( this.modelo.tipo_juego == HOMBREvsCOMPUTADORA )
            turnoGeneral = JUGADOR1;
        else
            turnoGeneral = ( turnoGeneral == JUGADOR1 ) ? JUGADOR2 : JUGADOR1;
        
        turno = turnoGeneral;
        
        /*Jugando.*/
        if ( turno == JUGADOR1 )
            mensaje( "Turno de " +jugador1.nombre);
        else
            mensaje( "Turno de " +jugador2.nombre);
        
        /*Mostramos su información, asignamos los nombres de jugador al panel.*/
        mostrarInformacion();
        
        jugando = true;
        terminado = false;
    }
    
    /*Método que suspende un juego.*/
    public void suspenderJuego(){ 
                
        //Llenamos el tablero con 0s*/
        Arrays.fill(tablero,0);
        
        /*Borramos los iconos.*/
        for ( int i = 0; i < 9; i ++ )
            fichas[i].setImage(null);
        
        /*Reinciamos el turno.*/
        turno = 1;
        jugando = false;
        terminado = false;
        
        /*Borramos jugadores.*/
        jugador1 = null;
        jugador2 = null;
        
        /*Habilitamos los menús.*/
        this.mnuIniciar.setDisable(false); 
        this.mnuSuspender.setDisable(false);
        this.lblEstado.setText("Juega al gato!");
        
        /*Quitamos los paneles.*/
        this.lblPlayer.setVisible(false);
        this.lblPlayer2.setVisible(false);
        
        
        
    }
    
    private void initComponents() {
        f1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { f1MouseClicked(); 
            }
        });
        f2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                f2MouseClicked(); 
            }
        });
        f3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                f3MouseClicked(); 
            }
        });
        f4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                f4MouseClicked(); 
            }
        });
        
        f5.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {f5MouseClicked(); 
            }
        });
        f6.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                f6MouseClicked(); 
            }
        });
        f7.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                f7MouseClicked(); 
            }
        });
        f8.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                f8MouseClicked(); 
            }
        });
        f9.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                f9MouseClicked(); 
            }
        });        
        lblEmpatados2.setText("Empatados: 0");

        lblPerdidos2.setText("Perdidos: 0");

        lblGanados2.setText("Ganados: 0");
        
        lblGanados.setText("Ganados: 0");

        blPerdidos.setText("Perdidos: 0");

        lblEmpatados.setText("Empatados: 0");
        
         mnuIniciar.setOnAction(event -> {
            try {
                mnuIniciarActionPerformed();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        });
         
         mnuSuspender.setOnAction(event -> mnuSuspenderActionPerformed());
         
         mnuEstadisticas.setOnAction(event -> mnuEstadisticasActionPerformed());
         
         btnconfigurar.setOnAction(event -> configurarActionPerformed());
    }
    
    private void configurarActionPerformed() {   /// POSIBLE OPCIONAL 
        System.out.println("Configurar");
    } 
    private void mnuEstadisticasActionPerformed() {       //POSIBLE OPCIONAL                                          
        this.mostrarEstadisticas();
        System.out.println("Estadisticas");
    } 
    
    private void mnuSuspenderActionPerformed() {       //TRABAJAR                                       
        /*Guardamos records.*/
//        estadisticas.guardarJugador(jugador2);
//        estadisticas.refrescar();
//        estadisticas.guardarJugador(jugador1);
        
        /*Suspendemos.*/
        suspenderJuego();
        System.out.println("susoender");
         //poner codigo para cerrar esta ventana
    } 
    
   private void mnuIniciarActionPerformed() throws IOException {                                           
    // Crear el ModeloController y configurar bidireccionalmente la relación
    ModeloController n = new ModeloController();
    n.setTablaController(this);

    // Cargar el FXML y obtener el controlador asociado
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Modelo.fxml"));
    Parent root = loader.load();

    // Configurar bidireccionalmente
    this.setModeloController(modelo);

    // Crear un nuevo Stage para la ventana del ModeloController
    Stage modeloStage = new Stage();
    Scene scene = new Scene(root, 700, 500);

    // Configurar el Stage con la nueva Scene
    modeloStage.setScene(scene);

    // Mostrar la nueva ventana
    modeloStage.show();
    
}
     
       
    
    private void f9MouseClicked() {                                
        movimiento(f9);
    }                               

    private void f8MouseClicked() {                                
        movimiento(f8);
    }                               

    private void f7MouseClicked() {                                
        movimiento(f7);
    }                               

    private void f6MouseClicked() {                                
        movimiento(f6);
    }                               

    private void f5MouseClicked() {                                
        movimiento(f5);
    }                               

    private void f4MouseClicked() {                                
        movimiento(f4);
    }                               

    private void f3MouseClicked() {                                
        movimiento(f3);
    }                               

    private void f2MouseClicked() {                                
        movimiento(f2);
    }                               

    private void f1MouseClicked() {                                
        movimiento(f1);
    }   
    public void initialize(URL url, ResourceBundle rb) {
    /*LLenamos nuestro tablero de 0, vacío.*/
    Arrays.fill(tablero, 0);

    /*Iniciamos los componentes de nuestra ventana*/
    iniciarComponentes();
    initComponents();
}
    
}
