// He hecho todos los puntos del documento

import java.util.Scanner;

class AjedrezJuego {

      // variables
      public String[][] tablero = {
      { "0", "1", "2", "3", "4", "5", "6", "7", "8" },
      { "a", "BT", "BP", "0", "0", "0", "0", "NP", "NT" }, // X = 1
      { "b", "BC", "BP", "0", "0", "0", "0", "NP", "NC" }, // X = 2
      { "c", "BA", "BP", "0", "0", "0", "0", "NP", "NA" }, // X = 3
      { "d", "BQ", "BP", "0", "0", "0", "0", "NP", "NK" }, // X = 4
      { "e", "BK", "BP", "0", "0", "0", "0", "NP", "NQ" }, // X = 5
      { "f", "BA", "BP", "0", "0", "0", "0", "NP", "NA" }, // X = 6
      { "g", "BC", "BP", "0", "0", "0", "0", "NP", "NC" }, // X = 7
      { "h", "BT", "BP", "0", "0", "0", "0", "NP", "NT" } // X = 8
      };

      public String[][] tablero_virtual = new String[9][9];

      public final int X = 0; // posicion[0]: X
      public final int Y = 1; // posicion[1]: Y

      public int n_turno = 1; // cada turno, n_turno++
      public boolean turno_jugador = true; // true: blanco false: negro
      public char color_jugador = 'B'; // 'B': blanco 'N': negro
      public char color_enemigo = 'N'; // 'B': blanco 'N': negro
      public String nombre_blanco = "BLANCO"; // nombre de jugadores
      public String nombre_negro = "NEGRO";

      public int[] posicion_rey_jugador = { 5, 1 };  // se actualiza cada turno en gamePlay()
      public int[] posicion_rey_blanco_actual = { 5, 1 }; // posicion de rey blanco
      public int[] posicion_rey_negro_actual = { 4, 8 }; // posicion de rey negro
      public int[] posicion_rey_blanco_inicio = { 5, 1 };
      public int[] posicion_rey_negro_inicio = { 4, 8 };
      public int[] posicion_movimiento_anterior = new int[2]; // posicion anterior (x, y)
      public int[] posicion_movimiento_posterior = new int[2]; // posicion posterior (x, y)

      public Scanner input = new Scanner(System.in);

      public int puntos_blanco = 0; // puntos que ha tenido el blanco por eliminacion
      public int puntos_negro = 0; // puntos que ha tenido el negro por eliminacion
      public String[] piezas_eliminadas_blanco = { "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0" };
      public String[] piezas_eliminadas_negro = { "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0" };
      public int n_eliminado_blanco = 0;
      public int n_eliminado_negro = 0;

      public boolean movido_rey_blanco = false; // en caso de enroque, guardan si se han movido los torres y los reyes
      public boolean movido_torre_blanco_izq = false;
      public boolean movido_torre_blanco_der = false;
      public boolean movido_rey_negro = false;
      public boolean movido_torre_negro_izq = false;
      public boolean movido_torre_negro_der = false;

      public boolean en_jaque_blanco = false; // para saber si hace falta mostrar el mensaje "te has salvado"
      public boolean en_jaque_negro = false;

      public String mensaje_accion; // "mover" o "matar"
      public String mensaje_pieza_movido; // ej."BP"
      public String mensaje_pieza_matado;
      public int[] mensaje_casilla_antes = { -48, 0 }; // (char)(-48+96) == '0'
      public int[] mensaje_casilla_despues = { -48, 0 };
      public int mensaje_puntos; // en caso de matar, guarda cuantos puntos ha conseguido el jugador
      public String mensaje_aviso_blanco = ""; // "estas en jaque", "te has salvado" ...
      public String mensaje_aviso_negro = "";

      public boolean juego_acabado = false;
      int resultado_partida; //1: blanco gana  0: empate  -1: negro gana


      // mostrar por pantalla
      public void mostrarTablero() {
            System.out.println();

            // mostrar el nombre del jugador actual (siempre esta en el medio)
            System.out.print("                  ");
            if (turno_jugador == true) {
                  for (int i = 1; i <= 4-nombre_blanco.length() / 2; i++) {
                        System.out.print(" ");
                  }
                  System.out.print(nombre_blanco);
            } else {
                  for (int i = 1; i <= 4-nombre_negro.length() / 2; i++) {
                        System.out.print(" ");
                  }
                  System.out.print(nombre_negro);
            }
            System.out.println();

            // mostrar las letras de arriba
            if (turno_jugador == true) {
                  System.out.println("Y    a    b    c    d    e    f    g    h");
            }
            else {
                  System.out.println("Y    h    g    f    e    d    c    b    a");
            }

            // mostrar el tablero
            for (int y = 8; y >= 1; y--) {
                  System.out.println("  +----+----+----+----+----+----+----+----+");

                  // mostrar los numeros de la izquierda
                  if (turno_jugador == true) {
                        System.out.print(y);
                  }
                  else {
                        System.out.print(9 - y);
                  }

                  System.out.print(" | ");

                  // array 'tablero'
                  for (int x = 1; x <= 8; x++) {
                        // si es blanco
                        if (turno_jugador == true) {
                              if (tablero[x][y] == "0") {
                                    System.out.print("  ");
                              }
                              else {
                                    System.out.print(tablero[x][y]);
                              }
                        }
                        // si es negro
                        else {
                              if (tablero[9 - x][9 - y] == "0") {
                                    System.out.print("  ");
                              }
                              else {
                                    System.out.print(tablero[9 - x][9 - y]);
                              }
                        }
                        System.out.print(" | ");
                  }

                  // mostrar los numeros de la derecha
                  if (turno_jugador == true) {
                        System.out.print(y);
                  }
                  else {
                        System.out.print(9 - y);
                  }

                  // mostrar mensajes (turno, puntos, felicidad...)
                  System.out.print("      ");
                  if (y == 8) {
                        System.out.print("TURNO: " + n_turno);
                  }
                  if (y == 7) {
                        if (turno_jugador == true) {
                              System.out.print("Tienes " + puntos_blanco + " puntos y has eliminado: ");
                              for (int i = 0; i < 16; i++) {
                                    if (piezas_eliminadas_negro[i] != "0") {
                                          System.out.print(piezas_eliminadas_negro[i] + " ");
                                    } else {
                                          break;
                                    }
                              }
                        } else {
                              System.out.print("Tienes " + puntos_negro + " puntos y has eliminado: ");
                              for (int i = 0; i < 16; i++) {
                                    if (piezas_eliminadas_blanco[i] != "0") {
                                          System.out.print(piezas_eliminadas_blanco[i] + " ");
                                    } else {
                                          break;
                                    }
                              }
                        }
                  }
                  if (y == 6) {
                        System.out.print("Introducir \"abandono\" para rendirse en cualquier momento");
                  }
                  if (y == 5) {
                        System.out.print("Ultimo paso:");
                  }
                  if (y == 4) {
                        System.out.print("    ");
                        System.out.print(mensaje_accion + ": ");
                        System.out.print(mensaje_pieza_movido + "(" + (char)(mensaje_casilla_antes[X] + 96) + "," + mensaje_casilla_antes[Y] + ")");
                        System.out.print(" --> ");
                        System.out.print(mensaje_pieza_matado + "(" + (char)(mensaje_casilla_despues[X] + 96) + "," + mensaje_casilla_despues[Y] + ")");
                  }
                  if (mensaje_puntos != 0 && y == 3) {
                        System.out.print("    ");
                        System.out.print("obtener " + mensaje_puntos + " punto");
                        if (mensaje_puntos > 1) {
                              System.out.print("s");
                        }
                  }
                  if (y == 2) {
                        System.out.print(mensaje_aviso_blanco);
                  }
                  if (y == 1) {
                        System.out.print(mensaje_aviso_negro);
                  }
                  System.out.println();
            }
            
            System.out.println("  +----+----+----+----+----+----+----+----+");

            // mostrar las letras de abajo
            if (turno_jugador == true) {
                  System.out.println("     a    b    c    d    e    f    g    h    X");
            }
            else {
                  System.out.println("     h    g    f    e    d    c    b    a    X");
            }
      }

      public void mostrarOpcionConoracion() {
            System.out.println("Puedes conorar tu Peon");
            System.out.println("Q:Queen    T:Torre    A:Alfil    C:Caballo");
      }

      public void mostrarMensajeTermino() {
            int longitud_nombre = 0; // longitud de nombre del jugador ganado
            if (resultado_partida == 1) {
                  longitud_nombre = nombre_blanco.length();
            } else if (resultado_partida == -1) {
                  longitud_nombre = nombre_negro.length();
            }

            System.out.println();

            // linia 1
            System.out.print("+------------------------");
            for (int i = 1; i <= longitud_nombre; i++) {
                  System.out.print("-");
            }
            System.out.print("+\n");

            // linia 2
            if (resultado_partida != 0) {
                  System.out.print("|      ");
                  for (int i = 1; i <= longitud_nombre / 2; i++) {
                        System.out.print(" ");
                  }
                  System.out.print("FELICIDADES!");
                  for (int i = 1; i <= longitud_nombre - longitud_nombre / 2; i++) {
                        System.out.print(" ");
                  }
                  System.out.println("      |");      
            }

            // linia 3
            if (resultado_partida == 1) {
                  System.out.println("| El jugador " + nombre_blanco + " ha ganado! |");
            } else if (resultado_partida == -1) {
                  System.out.println("| El jugador " + nombre_negro + " ha ganado! |");
            } else {
                  System.out.println("|         EMPATE         |");
            }

            // linia 4
            System.out.print("+------------------------");
            for (int i = 1; i <= longitud_nombre; i++) {
                  System.out.print("-");
            }
            System.out.print("+\n\n");
      }
      

      // main
      public static void main(String[] args) {
            AjedrezJuego programa = new AjedrezJuego();
            programa.gamePlay();
      }


      // game play (introducirNombre - bucleTurno - endGame)
      public void gamePlay() {
            // los jugadores se ponen nombres
            nombre_blanco = introducirNombre("BLANCO");
            nombre_negro = introducirNombre("NEGRO");

            // bucle de turno (blanco -> negro -> blanco -> ...)
            while (juego_acabado == false) {
                  if (turno_jugador == true) {
                        turnoBlanco();
                        turno_jugador = false;
                        color_jugador = 'N';
                        color_enemigo = 'B';
                        posicion_rey_jugador = posicion_rey_negro_actual;
                  } else {
                        turnoNegro();
                        turno_jugador = true;
                        color_jugador = 'B';
                        color_enemigo = 'N';
                        posicion_rey_jugador = posicion_rey_blanco_actual;
                  }

                  // endGame
                  if (endGame()) {
                        juego_acabado = true;
                  }
            }

            // Cuando el juego ha acabado, mostrar los mensajes
            mostrarMensajeTermino();
      }


      // turnos
      public void turnoBlanco() {
            // si el jugador blanco esta en jaque:
            if (jaque(color_enemigo, color_jugador, posicion_rey_blanco_actual, tablero)) {
                  en_jaque_blanco = true;
                  mensaje_aviso_blanco = (nombre_blanco + ", estas en jaque!");
            }

            mostrarTablero();
            mensaje_aviso_blanco = "";
            mensaje_aviso_negro = "";
            n_turno++;
            movimiento();

            // si el jugador blanco estaba en jaque: 
            if (juego_acabado == false && en_jaque_blanco == true) {
                  mensaje_aviso_blanco = (nombre_blanco + ", estas salvado!");
                  en_jaque_blanco = false;
            }
      }

      public void turnoNegro() {
            // si el jugador negro esta en jaque:
            if (jaque(color_enemigo, color_jugador, posicion_rey_negro_actual, tablero)) {
                  en_jaque_negro = true;
                  mensaje_aviso_negro = (nombre_negro + ", estas en jaque!");
            }

            mostrarTablero();
            mensaje_aviso_blanco = "";
            mensaje_aviso_negro = "";
            n_turno++;
            movimiento();

            // si el jugador negro estaba en jaque:
            if (juego_acabado == false && en_jaque_negro == true) {
                  mensaje_aviso_negro = (nombre_negro + ", estas salvado!");
                  en_jaque_negro = false;
            }
      }


      // movimiento de jugadores (introducir - comprobar - mover)
      public void movimiento() {
            // el usuario introduce la posicion anterior
            // ----si es su pieza
            // --------el usuario introduce la posicion posterior
            // ------------si la pieza puede mover ahi
            // ----------------OK
            // ------------sino vuelve a introducir la posicion posterior
            // ----sino vuelve a introducir la posicion anterior
            while (true) {
                  // el usuario introduce la pieza que quiere mover
                  posicion_movimiento_anterior = introducirPosicion("Quiero mover(x y): ");

                  // si el usuario ha introducido "abandono", salir del movimiento
                  if (juego_acabado == true) {
                        break;
                  }

                  // si la pieza no es suya, continue
                  if (esSuPieza(posicion_movimiento_anterior, color_jugador) == false) {
                        System.out.println("No es tu pieza");
                        continue;
                  }

                  // el usuario introduce la casilla_destino
                  posicion_movimiento_posterior = introducirPosicion("A la casilla(x y): ");

                  // si el usuario ha introducido "abandono", salir del movimiento
                  if (juego_acabado == true) {
                        break;
                  }

                  // si se puede mover
                  if (esSuPieza(posicion_movimiento_posterior, color_jugador) == false && puedeMover(posicion_movimiento_anterior,
                              posicion_movimiento_posterior, true, tablero) == true) {
                        
                        // guardar las informaciones en los variables de mensaje
                        guardarInfoMensaje();
                                    
                        // mover la pieza
                        moverPieza();

                        // coronacion del peon
                        if (puedeCoronarPeon() == true) {
                              if (turno_jugador == true) {
                                    mensaje_aviso_blanco = (nombre_blanco + ", felicidades por coronar tu peon!");
                              }
                              else {
                                    mensaje_aviso_negro = (nombre_negro + ", felicidades por coronar tu peon!");
                              }
                              mostrarOpcionConoracion();
                              coronarPeon(introducirPiezaCoronacion());
                              actualizarPuntos(tablero[posicion_movimiento_posterior[X]][posicion_movimiento_posterior[Y]].charAt(1),true);
                        }

                        // guardar la posicion de rey en posicion_rey_actual
                        if (tablero[posicion_movimiento_posterior[X]][posicion_movimiento_posterior[Y]] == "BK") {
                              posicion_rey_blanco_actual = posicion_movimiento_posterior;
                        } else if (tablero[posicion_movimiento_posterior[X]][posicion_movimiento_posterior[Y]] == "NK") {
                              posicion_rey_negro_actual = posicion_movimiento_posterior;
                        }

                        // si el rey se ha movido, movido_rey = true
                        actualizarEstadoRey();

                        // si algun torre se ha movido, movido_torre = true
                        actualizarEstadoTorre();

                        //
                        break;
                  }

                  // si no puede mover ahi, continue
                  else {
                        System.out.println("No la puedes mover ahi");
                        continue;
                  }
            }
      }


      // comprobacion de movimiento
      public boolean puedeMover(int[] posicion_antes, int[] posicion_despues, boolean comprobacion_jaque, String[][] tablero) {
            //Es para comprobar si la pieza de posicion_antes puede mover a la posicion_despues
            //Debajo de esta funcion hay varios 'hijos', como:: puedeMoverPeon, puedeMoverCaballo...
            // int[] posicion_antes: la posicion de la pieza que quiere mover
            // int[] posicion_despues: la posicion de la casilla donde quiere moverse
            // String [][] tablero: en cual tablero quieres hacer la comprobacion
            boolean resultado = true;
            switch (tablero[posicion_antes[X]][posicion_antes[Y]].charAt(1)) {
                  case 'P':
                        resultado = puedeMoverPeon(posicion_antes, posicion_despues, tablero);
                        break;
                  case 'T':
                        resultado = puedeMoverTorre(posicion_antes, posicion_despues, tablero);
                        break;
                  case 'C':
                        resultado = puedeMoverCaballo(posicion_antes, posicion_despues);
                        break;
                  case 'A':
                        resultado = puedeMoverAlfil(posicion_antes, posicion_despues, tablero);
                        break;
                  case 'Q':
                        resultado = puedeMoverQueen(posicion_antes, posicion_despues, tablero);
                        break;
                  case 'K':
                        resultado = puedeMoverKing(posicion_antes, posicion_despues, tablero);
                        break;
            }

            // cuando la funcion puedeMover() esta ejecutado dentro de la funcion jaque(), no hace falta ejecutar jaque otra vez
            // sino se producira un bucle infinito
            // necesita ayuda de tablero_virtual: para comprobar si hay jaque despues de mover, tenemos que mover la pieza primero
            // pero no podemos mover la pieza en el tablero real, porque aun estamos en la funcion puedeMover,
            // solo puede decir si puede o no, no puede cambiar los variables de fuera, sino se va a liar
            if (resultado == true && comprobacion_jaque == true) {
                  // copia el contenido de tablero a tablero_virtual
                  for (int i = 0; i <= 8; i++) {
                        for (int j = 0; j <= 8; j++) {
                              tablero_virtual[i][j] = tablero[i][j];
                        }
                  }
                  // mover la pieza en tablero_virtual
                  tablero_virtual[posicion_despues[X]][posicion_despues[Y]] = tablero_virtual[posicion_antes[X]][posicion_antes[Y]];
                  tablero_virtual[posicion_antes[X]][posicion_antes[Y]] = "0";
                  int[] posicion_rey_b_jugador_virtual = posicionPieza(color_jugador, 'K', tablero_virtual);
                  // tablero_virtual es 
                  resultado = !jaque(color_enemigo, color_jugador, posicion_rey_b_jugador_virtual, tablero_virtual);
            }

            return resultado;
      }

      public boolean puedeMoverPeon(int[] posicion_antes, int[] posicion_despues, String[][] tablero) {
            boolean resultado = false;
            // blanco
            if (tablero[posicion_antes[X]][posicion_antes[Y]].charAt(0) == 'B') {
                  // mover
                  if (tablero[posicion_despues[X]][posicion_despues[Y]] == "0") {
                        // solo puede ir recto
                        if (posicion_despues[X] == posicion_antes[X]) {
                              // si puede mover 2 casillas
                              if (posicion_antes[Y] == 2) {
                                    if (posicion_despues[Y] - posicion_antes[Y] == 2
                                                || posicion_despues[Y]
                                                            - posicion_antes[Y] == 1) {
                                          resultado = true;
                                    }
                              }
                              // si solo puede mover 1 casilla
                              else {
                                    if (posicion_despues[Y] - posicion_antes[Y] == 1) {
                                          resultado = true;
                                    }
                              }
                        }
                  }
                  // matar
                  else {
                        if (posicion_despues[Y] - posicion_antes[Y] == 1) {
                              if (posicion_despues[X] - posicion_antes[X] == 1
                                          || posicion_despues[X] - posicion_antes[X] == -1) {
                                    resultado = true;
                              }
                        }
                  }
            }
            // negro
            else {
                  // mover
                  if (tablero[posicion_despues[X]][posicion_despues[Y]] == "0") {
                        // solo puede ir recto
                        if (posicion_despues[X] == posicion_antes[X]) {
                              // si puede mover 2 casillas
                              if (posicion_antes[Y] == 7) {
                                    if (posicion_despues[Y] - posicion_antes[Y] == -2
                                                || posicion_despues[Y]
                                                            - posicion_antes[Y] == -1) {
                                          resultado = true;
                                    }
                              }
                              // si solo puede mover 1 casilla
                              else {
                                    if (posicion_despues[Y] - posicion_antes[Y] == -1) {
                                          resultado = true;
                                    }
                              }
                        }
                  }
                  // matar
                  else {
                        if (posicion_despues[Y] - posicion_antes[Y] == -1) {
                              if (posicion_despues[X] - posicion_antes[X] == 1
                                          || posicion_despues[X] - posicion_antes[X] == -1) {
                                    resultado = true;
                              }
                        }
                  }
            }
            return resultado;
      }

      public boolean puedeMoverTorre(int[] posicion_antes, int[] posicion_despues, String[][] tablero) {
            boolean resultado = true;
            // vertical
            if (posicion_despues[X] == posicion_antes[X]) {
                  if (hayAlgoMedioTorre(false, posicion_antes, posicion_despues, tablero)) {
                        resultado = false;
                  }
            }
            // horizontal
            else if (posicion_despues[Y] == posicion_antes[Y]) {
                  if (hayAlgoMedioTorre(true, posicion_antes, posicion_despues, tablero)) {
                        resultado = false;
                  }
            } else {
                  resultado = false;
            }
            return resultado;
      }

      public boolean puedeMoverCaballo(int[] posicion_antes, int[] posicion_despues) {
            boolean resultado = false;
            if ((posicion_despues[X] - posicion_antes[X]) * (posicion_despues[Y] - posicion_antes[Y]) == 2) {
                  resultado = true;
            }
            else if ((posicion_despues[X] - posicion_antes[X]) * (posicion_despues[Y] - posicion_antes[Y]) == -2){
                  resultado = true;
            }
            return resultado;
      }

      public boolean puedeMoverAlfil(int[] posicion_antes, int[] posicion_despues, String[][] tablero) {
            boolean resultado = true;
            // sube
            if ((posicion_despues[X] - posicion_antes[X]) == (posicion_despues[Y] - posicion_antes[Y])) {
                  if (hayAlgoMedioAlfil(true, posicion_antes, posicion_despues, tablero)) {
                        resultado = false;
                  }
            }
            // baja
            else if ((posicion_despues[X] - posicion_antes[X]) == (posicion_antes[Y] - posicion_despues[Y])) {
                  if (hayAlgoMedioAlfil(false, posicion_antes, posicion_despues, tablero)) {
                        resultado = false;
                  }
            }
            // otras situaciones
            else {
                  resultado = false;
            }
            return resultado;
      }

      public boolean puedeMoverQueen(int[] posicion_antes, int[] posicion_despues, String[][] tablero) {
            boolean resultado = true;
            // vertical
            if (posicion_despues[X] == posicion_antes[X]) {
                  if (hayAlgoMedioTorre(false, posicion_antes, posicion_despues, tablero)) {
                        resultado = false;
                  }
            }
            // horizontal
            else if (posicion_despues[Y] == posicion_antes[Y]) {
                  if (hayAlgoMedioTorre(true, posicion_antes, posicion_despues, tablero)) {
                        resultado = false;
                  }
            }
            // sube
            else if ((posicion_despues[X]
                        - posicion_antes[X]) == (posicion_despues[Y]
                                    - posicion_antes[Y])) {
                  if (hayAlgoMedioAlfil(true, posicion_antes, posicion_despues, tablero) == true) {
                        resultado = false;
                  }
            }
            // baja
            else if ((posicion_despues[X]
                        - posicion_antes[X]) == (posicion_antes[Y]
                                    - posicion_despues[Y])) {
                  if (hayAlgoMedioAlfil(false, posicion_antes, posicion_despues, tablero) == true) {
                        resultado = false;
                  }
            }
            // otras situaciones
            else {
                  resultado = false;
            }
            return resultado;
      }

      public boolean puedeMoverKing(int[] posicion_antes, int[] posicion_despues, String[][] tablero) {
            boolean resultado = false;
            int distancia_x = posicion_despues[X] - posicion_antes[X];
            int distancia_y = posicion_despues[Y] - posicion_antes[Y];

            // en caso de enroque
            if ((distancia_x == 2 || distancia_x == -2) && distancia_y == 0) {
                  int[] posicion_rey = new int[2];
                  int[] posicion_torre = new int[2];
                  int[] posicion_torre_despues = new int[2];
                  boolean a_derecha;

                  // blanco
                  if (tablero[posicion_antes[X]][posicion_despues[Y]].charAt(0) == 'B') {
                        posicion_rey[X] = 4;
                        posicion_rey[Y] = 1;
                        // a la derecha
                        if (distancia_x == 2) {
                              posicion_torre[X] = 8;
                              posicion_torre[Y] = 1;
                              posicion_torre_despues[X] = 5;
                              posicion_torre_despues[Y] = 1;
                              a_derecha = true;
                        }
                        // a la izquierda
                        else {
                              posicion_torre[X] = 1;
                              posicion_torre[Y] = 1;
                              posicion_torre_despues[X] = 3;
                              posicion_torre_despues[Y] = 1;
                              a_derecha = false;
                        }
                  }
                  // negro
                  else {
                        posicion_rey[X] = 5;
                        posicion_rey[Y] = 8;
                        // a la derehca
                        if (distancia_x == 2) {
                              posicion_torre[X] = 8;
                              posicion_torre[Y] = 8;
                              posicion_torre_despues[X] = 6;
                              posicion_torre_despues[Y] = 8;
                              a_derecha = true;
                        }
                        // a la izquierda
                        else {
                              posicion_torre[X] = 1;
                              posicion_torre[Y] = 8;
                              posicion_torre_despues[X] = 4;
                              posicion_torre_despues[Y] = 8;
                              a_derecha = false;
                        }
                  }
                  if (puedeEnroque(a_derecha, posicion_rey, posicion_torre, tablero)) {
                        resultado = true;
                        enroqueMoverTorre(posicion_torre, posicion_torre_despues);
                  }
            }

            // valor absoluto de distancia
            if (distancia_x < 0) {
                  distancia_x = 0 - distancia_x;
            }
            if (distancia_y < 0) {
                  distancia_y = 0 - distancia_y;
            }

            // en caso normal
            if (distancia_x <= 1 && distancia_y <= 1) {
                  resultado = true;
            }

            return resultado;
      }

      public boolean hayAlgoMedioTorre(boolean esHorizontal, int[] posicion_antes, int[] posicion_despues, String[][] tablero) {
            // en caso de torre: comprobar si hay otra pieza esta en el medio
            boolean resultado = false;
            if (esHorizontal == true) {
                  // a la derecha
                  if (posicion_despues[X] > posicion_antes[X]) {
                        for (int i = posicion_antes[X] + 1; i < posicion_despues[X]; i++) {
                              if (tablero[i][posicion_antes[Y]] != "0") {
                                    resultado = true;
                                    break;
                              }
                        }
                  }
                  // a la izquierda
                  else {
                        for (int i = posicion_antes[X] - 1; i > posicion_despues[X]; i--) {
                              if (tablero[i][posicion_antes[Y]] != "0") {
                                    resultado = true;
                                    break;
                              }
                        }
                  }
            } else {
                  // arriba
                  if (posicion_despues[Y] > posicion_antes[Y]) {
                        for (int i = posicion_antes[Y] + 1; i < posicion_despues[Y]; i++) {
                              if (tablero[posicion_antes[X]][i] != "0") {
                                    resultado = true;
                                    break;
                              }
                        }
                  }
                  // abajo
                  else {
                        for (int i = posicion_antes[Y] - 1; i > posicion_despues[Y]; i--) {
                              if (tablero[posicion_antes[X]][i] != "0") {
                                    resultado = true;
                                    break;
                              }
                        }
                  }
            }
            return resultado;
      }

      public boolean hayAlgoMedioAlfil(boolean sube, int[] posicion_antes, int[] posicion_despues, String[][] tablero) {
            // en caso de alfil: comprobar si hay otra pieza esta en el medio
            boolean resultado = false;
            int distancia = posicion_despues[X] - posicion_antes[X];
            if (distancia < 0) {
                  distancia = 0 - distancia;
            }
            // sube
            if (sube == true) {
                  // caso: cuadrante 3
                  if (posicion_despues[X] < posicion_antes[X]) {
                        for (int i = 1; i < distancia; i++) {
                              if (tablero[posicion_despues[X] + i][posicion_despues[Y]
                                          + i] != "0") {
                                    resultado = true;
                                    break;
                              }
                        }
                  }
                  // caso: cuadrante 1
                  else {
                        for (int i = 1; i < distancia; i++) {
                              if (tablero[posicion_despues[X] - i][posicion_despues[Y]
                                          - i] != "0") {
                                    resultado = true;
                                    break;
                              }
                        }
                  }
            }
            // baja
            else {
                  // caso: cuadrante 2
                  if (posicion_despues[X] < posicion_antes[X]) {
                        for (int i = 1; i < distancia; i++) {
                              if (tablero[posicion_despues[X] + i][posicion_despues[Y] - i] != "0") {
                                    resultado = true;
                                    break;
                              }
                        }
                  }
                  // caso: cuadrante 4
                  else {
                        for (int i = 1; i < distancia; i++) {
                              if (tablero[posicion_despues[X] - i][posicion_despues[Y]
                                          + i] != "0") {
                                    resultado = true;
                                    break;
                              }
                        }
                  }
            }
            return resultado;
      }


      // puedeMoverKing - enroque
      public boolean puedeEnroque(boolean a_derecha, int[] posicion_rey, int[] posicion_torre, String[][] tablero) {
            // ninguna pieza se movio
            // no hay piezas entre rey y torre
            // el rey des de principio hasta destino no puede estar en jaque
            // boolean a_derecha: si rey va a la derecha o no
            // int[] posicion_rey: la posicion del rey relacionado
            // int[] posicion_torre: la posicion del torre relacionado
            // Stringp[][] tablero: el tablero que quieres usar
            boolean resultado = false;
            if (enroqueNoMovido(a_derecha) == true && hayAlgoMedioTorre(true, posicion_rey, posicion_torre, tablero) == false
                        && enroqueReyNoJaque(a_derecha) == true) {
                  resultado = true;
            }
            return resultado;
      }

      public boolean enroqueNoMovido(boolean a_derecha) {
            boolean resultado = false;
            if (turno_jugador == true) {
                  if (a_derecha == true) {
                        if (movido_rey_blanco == false && movido_torre_blanco_der == false) {
                              resultado = true;
                        }
                  } else {
                        if (movido_rey_blanco == false && movido_torre_blanco_izq == false) {
                              resultado = true;
                        }
                  }
            } else {
                  if (a_derecha == true) {
                        if (movido_rey_negro == false && movido_torre_blanco_der == false) {
                              resultado = true;
                        }
                  } else {
                        if (movido_rey_negro == false && movido_torre_blanco_izq == false) {
                              resultado = true;
                        }
                  }
            }
            return resultado;
      }

      public boolean enroqueReyNoJaque(boolean a_derecha) {
            boolean resultado = true;
            int for_direccion, for_final;
            char color_jugador_a;

            if (color_jugador == 'B') {
                  color_jugador_a = 'N';
            } else {
                  color_jugador_a = 'B';
            }

            if (a_derecha == true) {
                  for_direccion = 1;
                  for_final = 2;
            } else {
                  for_direccion = -1;
                  for_final = -2;
            }

            for (int i = 0; i != for_final; i += for_direccion) {
                  int[] posicion_virtual_rey = { posicion_rey_blanco_inicio[X] + i, posicion_rey_blanco_inicio[Y] };
                  if (jaque(color_jugador_a, color_jugador, posicion_virtual_rey, tablero)) {
                        resultado = false;
                        break;
                  }
            }

            return resultado;
      }

      public void enroqueMoverTorre(int[] posicion_antes, int[] posicion_despues) {
            tablero[posicion_despues[X]][posicion_despues[Y]] = tablero[posicion_antes[X]][posicion_antes[Y]];
            tablero[posicion_antes[X]][posicion_antes[Y]] = "0";
      }


      // movimiento - coronacion
      public boolean puedeCoronarPeon() {
            // si el peon esta en Y == 1 o y == 8, entonces puede coronar
            boolean resultado = false;
            if (tablero[posicion_movimiento_posterior[X]][posicion_movimiento_posterior[Y]].charAt(1) == 'P') {
                  if (posicion_movimiento_posterior[Y] == 1 || posicion_movimiento_posterior[Y] == 8) {
                        resultado = true;
                  }
            }
            return resultado;
      }

      public void coronarPeon(char char_pieza) {// en caso de coronacion, cambiar el peon
            tablero[posicion_movimiento_posterior[X]][posicion_movimiento_posterior[Y]] = ("" + color_jugador
                        + char_pieza);
      }


      // movimeinto - mover piezas
      public void moverPieza() {
            // si mata, actualiza la eliminacion y puntos
            if (tablero[posicion_movimiento_posterior[X]][posicion_movimiento_posterior[Y]] != "0") {
                  actualizarEliminacion();
                  actualizarPuntos(
                              tablero[posicion_movimiento_posterior[X]][posicion_movimiento_posterior[Y]].charAt(1),
                              false);
            }
            // mover la pieza a la casilla posterior
            tablero[posicion_movimiento_posterior[X]][posicion_movimiento_posterior[Y]] = tablero[posicion_movimiento_anterior[X]][posicion_movimiento_anterior[Y]];
            tablero[posicion_movimiento_anterior[X]][posicion_movimiento_anterior[Y]] = "0";
      }


      // gamePlay - end game
      public boolean endGame() {
            // Existen 4 situaciones de endGame
            // 1. abandono  2. jaqueMate  3. empateAhogado  4. empateMuerto
            // return true: juego acabado
            boolean resultado = false;
            int[] posicion_rey_jugador = posicion_rey_blanco_actual;
            if (turno_jugador == false) {
                  posicion_rey_jugador = posicion_rey_negro_actual;
            }      

            // caso: abandono
            if (juego_acabado == true) {
                  if (turno_jugador == true) {
                        resultado_partida = 1;
                  }
                  else {
                        resultado_partida = -1;
                  }
                  System.out.println("acabado por abandono");
                  resultado = true;
            }
      
            // caso: jaqueMate
            else if (jaque(color_enemigo, color_jugador, posicion_rey_jugador, tablero) && noPuedeMoverNada(color_jugador)) {
                  if (turno_jugador == true) {
                        resultado_partida = -1;
                  }
                  else {
                        resultado_partida = 1;
                  }
                  System.out.println("acabado por jaqueMate");
                  resultado = true;
            }

            // caso: empate
            else if (empateAhogado() || empateMuerto()) {
                  // empateAhogado: un jugador no puede mover nada y no esta en jaque
                  // empateMuerto: por falta de piezas, ningun jugador puede ganar
                  resultado_partida = 0;
                  System.out.println("acabado por empate");
                  resultado = true;
            }
            return resultado;
      }

      public boolean empateAhogado() {
            // esta funcion solo tiene una linia, no es tan necesario
            // pero para hacer mas clara la idea del programa, aqui creamos una funcion nueva
            return noPuedeMoverNada(color_jugador);
      }

      public boolean empateMuerto() {
            boolean resultado = false;
                  // situacion 1 - rey contra rey
                  if (puntos_blanco == 39 && puntos_negro == 39) {
                        resultado = true;
                  }
                  // situacion 2 - rey contra rey y alfil o rey contra rey y caballo
                  // Explicacion: el negro solo tiene 2 piezas y el blanco tiene 36 puntos (39-3) o el blanco tiene 2 piezas y el negro tiene 36 puntos
                  else if ((puntos_blanco == 36 && puntos_negro == 39 && n_eliminado_negro == 14) || (puntos_blanco == 39 && puntos_negro == 36 && n_eliminado_blanco == 14)) {
                        resultado = true;
                  }
                  // situacion 3 - a cada jugador le quedan un rey y un alfil, y los alfiles estan en casilla de mismo color
                  else if (puntos_blanco == 36 && puntos_negro == 36) {
                        // comprobar si solo les quedan 2 piezas
                        if (n_eliminado_blanco == 14 && n_eliminado_negro == 14) {
                              // conseguir las posiciones del alfil (si existe), si no existe pues devuelve posicion {-1,-1}
                              int[] posicion_alfil_blanco = posicionPieza('B', 'A', tablero);
                              int[] posicion_alfil_negro = posicionPieza('N', 'A', tablero);
                              // comprobar si las posiciones son validas (que no sean {-1,-1})
                              if (posicionValida(posicion_alfil_blanco) == true && posicionValida(posicion_alfil_negro) == true) {
                                    // si son de casilla de mismo color:
                                    if (colorCasilla(posicion_alfil_blanco) == colorCasilla(posicion_alfil_negro)) {
                                          resultado = true;
                                    }
                              }
                        }
                  }
            return resultado;
      }

      public boolean noPuedeMoverNada(char player) {
            // el player no puede mover ninguna pieza suya a ninguna casilla, ya no puede hacer ningun movimiento
            // se utiliza en caso de jaque-mate y empate-ahogado
            // La idea es: mover cada pieza de player a todas casillas, probando uno por uno
            // si puedeMover == true una vez, el resultado == false.
            // sino resultado == true, que significa que no puede mover ninguna pieza
            boolean resultado = true;
            for (int i = 1; i <= 8; i++) {
                  for (int j = 1; j <= 8; j++) {
                        int[] posicion_antes = { i, j };
                        if (esSuPieza(posicion_antes, player)) {
                              // si esta pieza es de player
                              for (int ii = 1; ii <= 8; ii++) {
                                    for (int jj = 1; jj <= 8; jj++) {
                                          int[] posicion_despues = { ii, jj };
                                          if (!esSuPieza(posicion_despues, player)) {
                                                // si la casilla no es de player
                                                // perfecto, comprobamos a ver si lo puede mover ahi
                                                if (puedeMover(posicion_antes, posicion_despues, true, tablero)) {
                                                      resultado = false;
                                                      break;
                                                }
                                          }
                                    }
                                    if (!resultado) {
                                          break;
                                    }
                              }
                        }
                        if (!resultado) {
                              break;
                        }
                  }
                  if (!resultado) {
                        break;
                  }
            }
            return resultado;
      }


      // movimiento - introducir posicion - entreUnoOcho/esSuPieza
      public int[] introducirPosicion(String mensaje) { // introducir una posicion de tablero
            // String mensaje: el mensaje que quieres mostrar antes de introducir la posicion
            int[] posicion = new int[2];
            while (true) {
                  System.out.print(mensaje);
                  if (input.hasNext()) {
                        String entrada = input.next();

                        // abandono
                        if (entrada.equals("abandono")) {
                              juego_acabado = true;
                              break;
                        }

                        // en caso de que el usuario ha introducido mas de un caracter, error
                        if (entrada.length() > 1) {
                              System.out.println("Error: entrada de X demasiado largo");
                              input.nextLine();
                              continue;
                        }

                        posicion[X] = (int)entrada.charAt(0) - 96; // X
                        if (input.hasNextInt()) {
                              posicion[Y] = input.nextInt(); // Y
                              if (entreUnoOcho(posicion[X]) == true && entreUnoOcho(posicion[Y]) == true) {
                                    input.nextLine();
                                    break;
                              } else {
                                    System.out.println("Error: entrada fuera de rango (a-h y 1-8)");
                                    input.nextLine();
                              }
                        } else {
                              System.out.println("Error: entrada de Y no numerica");
                              input.nextLine();
                        }
                  }
            }
            return posicion;
      }

      public boolean entreUnoOcho(int n) { // comprobar si el numero es 1-8
            // int n: el numero que quieres comprobar
            boolean resultado = true;
            if (n < 1 || n > 8)
                  resultado = false;
            return resultado;
      }

      public boolean esSuPieza(int[] posicion, char color) { // comprobar que en la casilla hay su pieza
            // int[] posicion: la posicion que quieres comprobar
            boolean resultado = true;
            if (tablero[posicion[0]][posicion[1]].charAt(0) != color) {
                  resultado = false;
            }
            return resultado;
      }


      // movimiento - introducirPiezaCororacion
      public char introducirPiezaCoronacion() { // en caso de coronacion, introducir char_pieza
            char char_pieza;

            while (true) {
                  System.out.print("Quieres que tu peon cambie a ([T]torre, [C]caballo, [Q]Reina, [A]Alfil): ");
                  char_pieza = input.next().charAt(0);
                  if (char_pieza == 'T' || char_pieza == 'C' || char_pieza == 'Q' || char_pieza == 'A') {
                        break;
                  } else {
                        System.out.println("Entrada invalida");
                  }
            }
            return char_pieza;
      }


      // gamePlay - introducirNombre
      public String introducirNombre(String nombre) {
            String entrada;
            while (true) {
                  System.out.print("El jugador " + nombre + " se llama (por defecto " + nombre + "): ");
                  entrada = input.nextLine();
                  // si no ha introducido nada
                  if (entrada.length() == 0) {
                        entrada = nombre;
                        break;
                  }

                  // si hay espacios
                  else if (entrada.indexOf(' ') != -1) {
                        System.out.println("Error: el nombre no puede contener espacios\n");
                        continue;
                  }

                  // si es muy largo
                  else if (entrada.length() > 8) {
                        System.out.println("Error: demasiado largo (max 8 caracteres)\n");
                        continue;
                  }

                  // si es muy corto
                  else if (entrada.length() < 2) {
                        System.out.println("Error: demasiado corto (min 2 caracteres)\n");
                        continue;
                  }

                  // caso general
                  else {
                        break;
                  }
            }
            return entrada.toUpperCase();
      }


      // actualizar/guardar estados/infos
      public void actualizarEliminacion() { // en caso de eliminacion, actualiza los arrays piezas_eliminadas y n_eliminados
            if (turno_jugador == true) {
                  piezas_eliminadas_negro[n_eliminado_negro] = tablero[posicion_movimiento_posterior[X]][posicion_movimiento_posterior[Y]];
                  n_eliminado_negro++;
            } else {
                  piezas_eliminadas_blanco[n_eliminado_blanco] = tablero[posicion_movimiento_posterior[X]][posicion_movimiento_posterior[Y]];
                  n_eliminado_blanco++;
            }
      }

      public void actualizarPuntos(char pieza, boolean es_coronacion) {
            int p = 0;
            switch (pieza) {
                  case 'P':
                        p = 1;
                        break;
                  case 'T':
                        p = 5;
                        break;
                  case 'C':
                        p = 3;
                        break;
                  case 'A':
                        p = 3;
                        break;
                  case 'Q':
                        p = 9;
                        break;
            }

            //
            mensaje_puntos = p;

            // en caso de coronacion
            if (es_coronacion == true) {
                  p--;
                  if (turno_jugador == true) {
                        puntos_negro -= p;
                  } else {
                        puntos_blanco -= p;
                  }
                  // en caso normal
            } else {
                  if (turno_jugador == true) {
                        puntos_blanco += p;
                  } else {
                        puntos_negro += p;
                  }
            }
      }

      public void actualizarEstadoRey() { // para enroque, guardar si se han movido los reyes o no
            if (!movido_rey_blanco) {
                  if (posicion_movimiento_anterior[X] == 4 && posicion_movimiento_anterior[Y] == 1) {
                        movido_rey_blanco = true;
                  }
            }
            if (!movido_rey_negro) {
                  if (posicion_movimiento_anterior[X] == 5 && posicion_movimiento_anterior[Y] == 8) {
                        movido_rey_negro = true;
                  }
            }
      }

      public void actualizarEstadoTorre() { // para enroque, guardar si se han movido los torres o no
            if (!movido_torre_blanco_izq) {
                  if (posicion_movimiento_anterior[X] == 1 && posicion_movimiento_anterior[Y] == 1) {
                        movido_torre_blanco_izq = true;
                  }
            }
            if (!movido_torre_blanco_der) {
                  if (posicion_movimiento_anterior[X] == 8 && posicion_movimiento_anterior[Y] == 1) {
                        movido_torre_blanco_der = true;
                  }
            }
            if (!movido_torre_negro_izq) {
                  if (posicion_movimiento_anterior[X] == 8 && posicion_movimiento_anterior[Y] == 8) {
                        movido_torre_negro_izq = true;
                  }
            }
            if (!movido_torre_negro_der) {
                  if (posicion_movimiento_anterior[X] == 1 && posicion_movimiento_anterior[Y] == 8) {
                        movido_torre_negro_der = true;
                  }
            }
      }

      public void guardarInfoMensaje() {
            if (tablero[posicion_movimiento_posterior[X]][posicion_movimiento_posterior[Y]] != "0") {
                  mensaje_accion = "matar";
                  mensaje_pieza_matado = tablero[posicion_movimiento_posterior[X]][posicion_movimiento_posterior[Y]];
            }
            else {
                  mensaje_accion = "mover";
                  mensaje_pieza_matado = "";
                  mensaje_puntos = 0;
            }
            mensaje_pieza_movido = tablero[posicion_movimiento_anterior[X]][posicion_movimiento_anterior[Y]];
            mensaje_casilla_antes[X] = posicion_movimiento_anterior[X];
            mensaje_casilla_antes[Y] = posicion_movimiento_anterior[Y];
            mensaje_casilla_despues[X] = posicion_movimiento_posterior[X];
            mensaje_casilla_despues[Y] = posicion_movimiento_posterior[Y];
      }


      // endGame - empateMuerto
      public boolean colorCasilla(int[] casilla) {
            // hay dos tipos de casillas (ej.blanco y negro)
            // se utiliza en caso de empateMuerto para comprobar si los alfiles son de mismo tipo de casilla
            // true: un tipo de casilla, false: otro tipo
            boolean resultado;
            // tipo true
            if (casilla[X] % 2 == casilla[Y] % 2) {
                  resultado = true;
            }
            // tipo false
            else {
                  resultado = false;
            }
            return resultado;
      }

      public int[] posicionPieza(char color_pieza, char nombre_pieza, String[][] tablero) {
            // esta funcion busca la pieza que quieres encontrar
            // y devuelve la posicion de la primera que encuentra
            // si no encuentra nada, devuelve {-1, -1}
            boolean break_hecho = false;
            int[] p = {-1, -1};
            for (int i = 1; i <= 8; i++) {
                  for (int j = 1; j <= 8; j++) {
                        if (tablero[i][j].charAt(0) == color_pieza && tablero[i][j].charAt(1) == nombre_pieza) {
                              p[X] = i;
                              p[Y] = j;
                              break_hecho = true;
                              break;
                        }
                  }
                  if (break_hecho == true) {
                        break;
                  }
            }
            return p;
      }

      public boolean posicionValida(int[] posicion) {
            boolean resultado = true;
            if (entreUnoOcho(posicion[X]) == false || entreUnoOcho(posicion[Y]) == false) {
                  resultado = false;
            }
            return resultado;
      }


      // puedeMover/enroqueReyNoJaque/jaqueMate/noPuedeMoverNada --> jaque
      public boolean jaque(char color_jugador_a, char color_jugador_b, int[] posicion_rey_b, String[][] tablero) {
            // una pieza de A puede matar el rey de B en el tablero especificado
            // char color_jugador_a: el color del jugador_a, 'B' o 'N'
            // char color_jugador_b: el color del jugador_b, 'B' o 'N'
            // int[] posicion_rey_b: la posicion del rey del jugador_b
            // String[][] tablero: en cual tablero quieres hacer la comprobacion?
            boolean resultado = false;
            for (int i = 1; i <= 8; i++) {
                  for (int j = 1; j <= 8; j++) {
                        if (tablero[i][j].charAt(0) == color_jugador_a) {
                              int[] posicion_pieza_a = { i, j };
                              if (puedeMover(posicion_pieza_a, posicion_rey_b, false, tablero)) {
                                    resultado = true;
                                    break;
                              }
                        }
                  }
                  if (resultado == true) {
                        break;
                  }
            }
            return resultado;
      }
}