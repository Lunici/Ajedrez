import java.util.Scanner;

class AjedrezJuego {

      // variables
      public String[][] tablero = {
      { "0", "1", "2", "3", "4", "5", "6", "7", "8" },
      { "1", "BT", "BP", "0", "0", "0", "0", "NP", "NT" }, // X = 1
      { "2", "BC", "BP", "0", "0", "0", "0", "NP", "NC" }, // X = 2
      { "3", "BA", "BP", "0", "0", "0", "0", "NP", "NA" }, // X = 3
      { "4", "BK", "BP", "0", "0", "0", "0", "NP", "NQ" }, // X = 4
      { "5", "BQ", "BP", "0", "0", "0", "0", "NP", "NK" }, // X = 5
      { "6", "0", "BP", "0", "0", "0", "0", "NP", "NA" }, // X = 6
      { "7", "BC", "0", "BP", "0", "0", "0", "NP", "NC" }, // X = 7
      { "8", "BT", "BP", "BA", "0", "0", "0", "NP", "NT" } // X = 8
      };

      public String[][] tablero_virtual = new String[9][9];

      // public String[][] tablero = {
      //             { "0", "1", "2", "3", "4", "5", "6", "7", "8" },
      //             { "1", "0", "0", "0", "0", "0", "0", "0", "NK" }, // X = 1
      //             { "2", "0", "0", "0", "0", "0", "0", "0", "0" }, // X = 2
      //             { "3", "0", "0", "0", "0", "0", "BQ", "0", "0" }, // X = 3
      //             { "4", "0", "0", "0", "0", "0", "0", "0", "0" }, // X = 4
      //             { "5", "0", "0", "0", "0", "0", "0", "0", "0" }, // X = 5
      //             { "6", "0", "0", "0", "0", "0", "0", "0", "0" }, // X = 6
      //             { "7", "0", "0", "0", "0", "0", "0", "0", "0" }, // X = 7
      //             { "8", "0", "0", "0", "0", "0", "0", "0", "0" } // X = 8
      // };
      public final int X = 0; // posicion[0]: X
      public final int Y = 1; // posicion[1]: Y

      public boolean turno_jugador = true; // true: blanco false: negro
      public char color_jugador = 'B'; // 'B': blanco 'N': negro
      public char color_enemigo = 'N'; // 'B': blanco 'N': negro
      public int[] posicion_rey_jugador = { 4, 1 }; 
      public int[] posicion_rey_blanco_actual = { 4, 1 }; // posicion de rey blanco
      public int[] posicion_rey_negro_actual = { 5, 8 }; // posicion de rey negro
      public int[] posicion_rey_blanco_inicio = { 4, 1 };
      public int[] posicion_rey_negro_inicio = { 5, 8 };
      public int[] posicion_movimiento_anterior = new int[2]; // posicion anterior (x, y)
      public int[] posicion_movimiento_posterior = new int[2]; // posicion posterior (x, y)

      public Scanner input = new Scanner(System.in);

      public int puntos_blanco = 0; // puntos que ha tenido el blanco por eliminacion
      public int puntos_negro = 0; // puntos que ha tenido el negro por eliminacion
      public String[] piezas_eliminadas_blanco = { "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0" };
      public String[] piezas_eliminadas_negro = { "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0" };
      public int n_eliminado_blanco = 0;
      public int n_eliminado_negro = 0;

      public boolean movido_rey_blanco = false;
      public boolean movido_torre_blanco_izq = false;
      public boolean movido_torre_blanco_der = false;
      public boolean movido_rey_negro = false;
      public boolean movido_torre_negro_izq = false;
      public boolean movido_torre_negro_der = false;

      public boolean juego_acabado = false;
      int resultado_partida; //1: blanco gana  0: empate  -1: negro gana

      // print por pantalla
      public void mostrarPorPantalla() {
            System.out.println();
            mostrarEstado();
            mostrarPuntoYPiezasEliminadas();
            mostrarTableroBlanco();
      }

      public void mostrarTableroBlanco() {
            System.out.println();
            if (turno_jugador == true) {
                  System.out.println("Y                  BLANCO");
            } else {
                  System.out.println("Y                  NEGRO");
            }
            for (int y = 8; y > 0; y--) {
                  System.out.println("  +----+----+----+----+----+----+----+----+");
                  for (int x = 0; x <= 8; x++) {
                        if (tablero[x][y] == "0") {
                              System.out.print("  ");
                        } else {
                              System.out.print(tablero[x][y]);
                        }
                        System.out.print(" | ");
                  }
                  System.out.println();
            }
            System.out.println("  +----+----+----+----+----+----+----+----+");
            System.out.println("     1    2    3    4    5    6    7    8    X");
      }

      public void mostrarTableroNegro() {

      }

      public void mostrarPuntoYPiezasEliminadas() {
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

      public void mostrarEstado() {

      }

      public void mostrarOpcionConoracion() {
            System.out.println("Puedes conorar tu Peon");
            System.out.println("Q:Queen    T:Torre    A:Alfil    C:Caballo");
      }

      // main
      public static void main(String[] args) {
            AjedrezJuego programa = new AjedrezJuego();
            programa.gamePlay();
      }

      // game play
      public void gamePlay() {
            while (juego_acabado == false) {
                  mostrarPorPantalla();
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
      }

      // turno de blanco
      public void turnoBlanco() {
            movimiento();
      }

      // turno de negro
      public void turnoNegro() {
            movimiento();
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
                              posicion_movimiento_posterior, true) == true) {
                        // mover la pieza
                        moverPieza();

                        // coronacion del peon
                        if (puedeCoronarPeon() == true) {
                              mostrarOpcionConoracion();
                              coronarPeon(introducirPiezaCoronacion());
                              actualizarPuntos(
                                          tablero[posicion_movimiento_posterior[X]][posicion_movimiento_posterior[Y]]
                                                      .charAt(1),
                                          true);
                        }

                        // guardar la posicion de rey en posicion_rey
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
      public boolean puedeMover(int[] posicion_antes, int[] posicion_despues, boolean comprobacion_jaque) {
            boolean resultado = true;
            switch (tablero[posicion_antes[X]][posicion_antes[Y]].charAt(1)) {
                  case 'P':
                        resultado = puedeMoverPeon(posicion_antes, posicion_despues);
                        break;
                  case 'T':
                        resultado = puedeMoverTorre(posicion_antes, posicion_despues);
                        break;
                  case 'C':
                        resultado = puedeMoverCaballo(posicion_antes, posicion_despues);
                        break;
                  case 'A':
                        resultado = puedeMoverAlfil(posicion_antes, posicion_despues);
                        break;
                  case 'Q':
                        resultado = puedeMoverQueen(posicion_antes, posicion_despues);
                        break;
                  case 'K':
                        resultado = puedeMoverKing(posicion_antes, posicion_despues);
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
                  // tablero_virtual es 
                  resultado = !jaque(color_enemigo, color_jugador, posicion_rey_jugador, tablero_virtual);
            }

            return resultado;
      }

      public boolean puedeMoverPeon(int[] posicion_antes, int[] posicion_despues) {
            boolean resultado = false;
            // blanco
            if (turno_jugador == true) {
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

      public boolean puedeMoverTorre(int[] posicion_antes, int[] posicion_despues) {
            boolean resultado = true;
            // vertical
            if (posicion_despues[X] == posicion_antes[X]) {
                  if (hayAlgoMedioTorre(false, posicion_antes, posicion_despues)) {
                        resultado = false;
                  }
            }
            // horizontal
            else if (posicion_despues[Y] == posicion_antes[Y]) {
                  if (hayAlgoMedioTorre(true, posicion_antes, posicion_despues)) {
                        resultado = false;
                  }
            } else {
                  resultado = false;
            }
            return resultado;
      }

      public boolean puedeMoverCaballo(int[] posicion_antes, int[] posicion_despues) {
            boolean resultado = false;
            if ((posicion_despues[X] - posicion_antes[X]) *
                        (posicion_despues[Y] - posicion_antes[Y]) == 2
                        || (posicion_despues[X] - posicion_antes[X]) *
                                    (posicion_despues[Y] - posicion_antes[Y]) == -2) {
                  resultado = true;
            }
            return resultado;
      }

      public boolean puedeMoverAlfil(int[] posicion_antes, int[] posicion_despues) {
            boolean resultado = true;
            // sube
            if ((posicion_despues[X]
                        - posicion_antes[X]) == (posicion_despues[Y]
                                    - posicion_antes[Y])) {
                  if (hayAlgoMedioAlfil(true, posicion_antes, posicion_despues) == true) {
                        resultado = false;
                  }
                  // baja
            } else if ((posicion_despues[X]
                        - posicion_antes[X]) == (posicion_antes[Y]
                                    - posicion_despues[Y])) {
                  if (hayAlgoMedioAlfil(false, posicion_antes, posicion_despues) == true) {
                        resultado = false;
                  }
            } else {
                  resultado = false;
            }
            return resultado;
      }

      public boolean puedeMoverQueen(int[] posicion_antes, int[] posicion_despues) {
            boolean resultado = true;
            // vertical
            if (posicion_despues[X] == posicion_antes[X]) {
                  if (hayAlgoMedioTorre(false, posicion_antes, posicion_despues)) {
                        resultado = false;
                  }
            }
            // horizontal
            else if (posicion_despues[Y] == posicion_antes[Y]) {
                  if (hayAlgoMedioTorre(true, posicion_antes, posicion_despues)) {
                        resultado = false;
                  }
            }
            // sube
            else if ((posicion_despues[X]
                        - posicion_antes[X]) == (posicion_despues[Y]
                                    - posicion_antes[Y])) {
                  if (hayAlgoMedioAlfil(true, posicion_antes, posicion_despues) == true) {
                        resultado = false;
                  }
            }
            // baja
            else if ((posicion_despues[X]
                        - posicion_antes[X]) == (posicion_antes[Y]
                                    - posicion_despues[Y])) {
                  if (hayAlgoMedioAlfil(false, posicion_antes, posicion_despues) == true) {
                        resultado = false;
                  }
            }
            // otras situaciones
            else {
                  resultado = false;
            }
            return resultado;
      }

      public boolean puedeMoverKing(int[] posicion_antes, int[] posicion_despues) {
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
                  if (turno_jugador == true) {
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
                  if (puedeEnroque(a_derecha, posicion_rey, posicion_torre)) {
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

      public boolean hayAlgoMedioTorre(boolean esHorizontal, int[] posicion_antes, int[] posicion_despues) {
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

      public boolean hayAlgoMedioAlfil(boolean sube, int[] posicion_antes, int[] posicion_despues) {
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

      // coronacion
      public boolean puedeCoronarPeon() {
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

      // cambio de piezas
      public void moverPieza() {
            // si mata, actualiza la eliminacion y puntos
            if (tablero[posicion_movimiento_posterior[X]][posicion_movimiento_posterior[Y]] != "0") {
                  actualizarEliminacion();
                  actualizarPuntos(
                              tablero[posicion_movimiento_posterior[X]][posicion_movimiento_posterior[Y]].charAt(1),
                              false);
            }
            tablero[posicion_movimiento_posterior[X]][posicion_movimiento_posterior[Y]] = tablero[posicion_movimiento_anterior[X]][posicion_movimiento_anterior[Y]];
            tablero[posicion_movimiento_anterior[X]][posicion_movimiento_anterior[Y]] = "0";
      }

      // enroque
      public boolean puedeEnroque(boolean a_derecha, int[] posicion_rey, int[] posicion_torre) {
            // ninguna pieza se movio
            // no hay piezas entre rey y torre
            // el rey des de principio hasta destino no puede estar en jaque
            boolean resultado = false;
            if (enroqueNoMovido(a_derecha) == true && hayAlgoMedioTorre(true, posicion_rey, posicion_torre) == false
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

      // end game
      {
            // 1. abandono
            // 2. empate-ahogado
            // 3. empate-muerto
            // 4. jaqueMate
      }

      public boolean endGame() {
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
                  resultado_partida = 0;
                  System.out.println("acabado por empate");
                  resultado = true;
            }
            return resultado;
      }

      public boolean empateAhogado() {
            System.out.println("ahogado");
            return noPuedeMoverNada(color_jugador);
      }

      public boolean empateMuerto() {
            boolean resultado = false;
                  // situacion 1 - rey contra rey
                  if (puntos_blanco == 39 && puntos_negro == 39) {
                        resultado = true;
                  }
                  // situacion 2 - rey contra rey y alfil o rey contra rey y caballo
                  else if ((puntos_blanco == 36 && puntos_negro == 39) || (puntos_blanco == 39 && puntos_negro == 36)) {
                        resultado = true;
                  }
                  // situacion 3 - BK y 
                  else if (puntos_blanco == 36 && puntos_negro == 36 && colorCasilla(posicionPieza('B', 'A')) == colorCasilla(posicionPieza('N', 'A'))) {
                        resultado = true;
                  }
                  System.out.println("muerto");

            return resultado;
      }

      public boolean noPuedeMoverNada(char player) {
            // el player no puede mover ninguna pieza suya a ninguna casilla, ya no puede hacer ningun movimiento
            boolean resultado = true;
            for (int i = 1; i <= 8; i++) {
                  for (int j = 1; j <= 8; j++) {
                        int[] posicion_antes = { i, j };

                        if (esSuPieza(posicion_antes, player)) {
                              for (int ii = 1; ii <= 8; ii++) {
                                    for (int jj = 1; jj <= 8; jj++) {
                                          int[] posicion_despues = { ii, jj };

                                          if (!esSuPieza(posicion_despues, player)) {
                                                if (puedeMover(posicion_antes, posicion_despues, true)) {
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

      // funciones auxiliares
      public boolean entreUnoOcho(int n) { // comprobar si el numero es 1-8
            boolean resultado = true;
            if (n < 1 || n > 8)
                  resultado = false;
            return resultado;
      }

      public boolean esSuPieza(int[] posicion, char color) { // comprobar que en la casilla hay su pieza
            boolean resultado = true;
            if (tablero[posicion[0]][posicion[1]].charAt(0) != color) {
                  resultado = false;
            }
            return resultado;
      }

      public int[] introducirPosicion(String mensaje) { // introducir una posicion de tablero
            int[] posicion = new int[2];
            while (true) {
                  System.out.print(mensaje);
                  if (input.hasNextInt()) {
                        posicion[X] = input.nextInt();
                        if (input.hasNextInt()) {
                              posicion[Y] = input.nextInt();
                              if (entreUnoOcho(posicion[X]) == true && entreUnoOcho(posicion[Y]) == true) {
                                    input.nextLine();
                                    break;
                              } else {
                                    System.out.println("Entrada Invalida1");
                                    input.nextLine();
                              }
                        } else {
                              System.out.println("Entrada Invalida2");
                              input.nextLine();
                        }
                  } else {
                        if (input.next().equals("abandono")) {
                              juego_acabado = true;
                              break;
                        }
                        else {
                              System.out.println("Entrada Invalida3");
                              input.nextLine();      
                        }
                  }
            }
            return posicion;
      }

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

      public boolean jaque(char color_jugador_a, char color_jugador_b, int[] posicion_rey_b, String[][] tablero) {
            // jugador_b esta en jaque (una pieza de jugador_a puede matar el rey de
            // jugador_b)
            // comprobar si el jugador ------jaque-----> el enemigo
            boolean resultado = false;
            for (int i = 1; i <= 8; i++) {
                  for (int j = 1; j <= 8; j++) {
                        if (tablero[i][j].charAt(0) == color_jugador_a) {
                              int[] posicion_pieza_a = { i, j };
                              if (puedeMover(posicion_pieza_a, posicion_rey_b, false)) {
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

      public void actualizarEliminacion() { // en caso de eliminacion, actualiza los arrays piezas_eliminadas y
                                            // n_eliminados
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

      public int[] posicionPieza(char color_pieza, char nombre_pieza) {
            // esta funcion busca la pieza que quieres encontrar
            // y devuelve la posicion de la primera que encuentra
            boolean break_hecho = false;
            int[] p = new int[2];
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
}