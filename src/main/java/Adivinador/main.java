package Adivinador;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class main {
    /*
    /*
    Cantidad maxima de intentos en dificualtad falcil
     */
    public static int MAX_INTENTOS_FACIL = 15;
    /*
    cantidad macima de intentos en dificultad dificil
     */
    public static int MAX_INTENTOS_DIFICIL = 10;
    /**
     * numero maximo posible de eleccion. Se generara un valor al azar entre 1
     * y el NUMERO_MAXIMO
     */
    public static int NUMERO_MAXIMO = 100;

    /**
     * Las dificultades disponibles en el juego
     */
    public static enum Dificultad{FACIL, DIFICIL}

    /**
     * Las dificuldes disponibles en el juego
     * <ul>
     *     <li><b> JUGANDO: </b> El juego ha iniciado. No se ha ganado aun, ni se ha perdido.</li>
     *     <li><b> GANO:</b> El jugad qor adivino el numero secreto antes de agotar los intentos.</li>
     *     <li><b> PERDIO: </b> EL jugador agoto los intentos antes de adivinar el numero secreto.</li>
     * </ul>
     */

    public static enum Estado {JUGANDO, GANO, PERDIO}

    /**
     * Se utilizara para comparar el numero ingresado por el usuario frente al
     * numero secreto
     */
    public static enum Resultado {ES_MAYOR, ES_IGUAL, ES_MENOR}

    /**
     * Modelo de juego: <br>
     * <ul><li><b>Dificultad:</b> Arreglo de una celda conteniendo la dificultad del juego</li>
     * <li><b>maxIntentos:</b> Cantidad maxima de intentos con que se inicio el juego segun su dificultad.</li>
     * <li><b>estado:</b> Arreglo de la celda conteniendo el estado actual del juego.</li>
     * <li><b>numSecreto:</b> El numero secreto generado aleatoriamente entre el 1 y el NUMERO_MAXIMO.</li>
     * <li><b>intentoActual:</b> El intento actual del jugador</li>
     * </ul>
     */

    public static record Juego (
            Dificultad[] dificultad,
            AtomicInteger maxIntentos,
            Estado[] estado,
            AtomicInteger numeroSecreto,
            AtomicInteger intentoActal
            ){}

    /**
     * Inicializa el juego con los valores correctos acorde a la dificultad asigada.
     * El estado inicial sera JUGANDO y el intento actual sera 0. Tambien se generara
     * un numero aleatorio entre 1 y NUMERO_MAXIMO
     * @param d La dificutad con que se iniciara el juego
     * @return Retorna un juego listo para comenzar
     * @see Dificultad
     * @see Estado
     * @see Juego
     */

    public static Juego iniciarJuego(Dificultad d){
        Juego j = new Juego(new Dificultad[1], new AtomicInteger(), new Estado[1], new AtomicInteger(), new AtomicInteger());
        j.dificultad[0]=d;
        if (d==Dificultad.FACIL){
            j.maxIntentos.set(MAX_INTENTOS_FACIL);
        }else {
            j.maxIntentos.set(MAX_INTENTOS_DIFICIL);
        }

        j.estado[0]=Estado.JUGANDO;
        j.numeroSecreto.set(new Random().nextInt(NUMERO_MAXIMO)+1);

        return j;
    }

    /**
     * si n es igual al numero secreto, retorna ES_IGUAL; si el numero secreto
     * es menor que n retorna ES_MENOR y sino ES_MAYOR. Ademas se aumenta el numero
     * de intentos y se cambia el estado del juego por GANO si adivino el numero, PERDIO
     * si aoto los intentos. El parametro mensaje obtendra un mensaje adecuado en funcion
     * de lo que ocurra.
     * @param n El numero ingresado por el usuario
     * @param j El juego
     * @param mensaje El mesaje se obtendra aqui
     * @return ES_IGUAL, ES_MENOR, ES_MAYOR, en funcion del numero secreto comparado con n.
     */
    public static Resultado verificarNumero(int n, Juego j, StringBuilder mensaje){
        j.intentoActal.set(j.intentoActal.get()+1);
        mensaje.delete(0,mensaje.length());
        Resultado resultado;
        if (n==j.numeroSecreto.get()){
            j.estado[0]= Estado.GANO;
            mensaje.append("GANASTE!!");
            resultado= Resultado.ES_IGUAL;
        } else if (j.numeroSecreto.get() < n) {
            mensaje.append("El numero es menor, sigue intentando...");
            resultado = Resultado.ES_MENOR;
        } else{
            mensaje.append("El numero es mayor");
            resultado = Resultado.ES_MAYOR;
        }

        if (j.intentoActal.get() == j.maxIntentos.get()){
            mensaje.delete(0,mensaje.length());
            mensaje.append("Perdiste, el numero secreto es: ").append(j.numeroSecreto).append(".");
            j.estado[0]=Estado.PERDIO;
        }
        return resultado;
    }



    /**
     * Logica del juego
     * @param args
     */

    public static void main(String[] args) {
        String diff;
        var consola = new Scanner(System.in);
        Juego juego;
        int numeroUsuario;
        Resultado resultado;
        StringBuilder mensaje = new StringBuilder();

        System.out.print("Eligue la dificultad [F] FACIL/[D] DIFICIL >> ");
        diff = consola.nextLine();


    }
}
