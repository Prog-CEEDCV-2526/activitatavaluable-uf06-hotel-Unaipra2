package com.hotel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Gestió de reserves d'un hotel.
 */
public class App {

    // --------- CONSTANTS I VARIABLES GLOBALS ---------

    // Tipus d'habitació
    public static final String TIPUS_ESTANDARD = "Estàndard";
    public static final String TIPUS_SUITE = "Suite";
    public static final String TIPUS_DELUXE = "Deluxe";

    // Serveis addicionals
    public static final String SERVEI_ESMORZAR = "Esmorzar";
    public static final String SERVEI_GIMNAS = "Gimnàs";
    public static final String SERVEI_SPA = "Spa";
    public static final String SERVEI_PISCINA = "Piscina";

    // Capacitat inicial
    public static final int CAPACITAT_ESTANDARD = 30;
    public static final int CAPACITAT_SUITE = 20;
    public static final int CAPACITAT_DELUXE = 10;

    // IVA
    public static final float IVA = 0.21f;

    // Scanner únic
    public static Scanner sc = new Scanner(System.in);

    // HashMaps de consulta
    public static HashMap<String, Float> preusHabitacions = new HashMap<String, Float>();
    public static HashMap<String, Integer> capacitatInicial = new HashMap<String, Integer>();
    public static HashMap<String, Float> preusServeis = new HashMap<String, Float>();

    // HashMaps dinàmics
    public static HashMap<String, Integer> disponibilitatHabitacions = new HashMap<String, Integer>();
    public static HashMap<Integer, ArrayList<String>> reserves = new HashMap<Integer, ArrayList<String>>();

    // Generador de nombres aleatoris per als codis de reserva
    public static Random random = new Random();

    // --------- MÈTODE MAIN ---------

    /**
     * Mètode principal. Mostra el menú en un bucle i gestiona l'opció triada
     * fins que l'usuari decideix eixir.
     */
    public static void main(String[] args) {
        inicialitzarPreus();

        int opcio = 0;
        do {
            mostrarMenu();
            opcio = llegirEnter("Seleccione una opció: ");
            gestionarOpcio(opcio);
        } while (opcio != 6);

        System.out.println("Eixint del sistema... Gràcies per utilitzar el gestor de reserves!");
    }

    // --------- MÈTODES DEMANATS ---------

    /**
     * Configura els preus de les habitacions, serveis addicionals i
     * les capacitats inicials en els HashMaps corresponents.
     */
    public static void inicialitzarPreus() {
        // Preus habitacions
        preusHabitacions.put(TIPUS_ESTANDARD, 50f);
        preusHabitacions.put(TIPUS_SUITE, 100f);
        preusHabitacions.put(TIPUS_DELUXE, 150f);

        // Capacitats inicials
        capacitatInicial.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        capacitatInicial.put(TIPUS_SUITE, CAPACITAT_SUITE);
        capacitatInicial.put(TIPUS_DELUXE, CAPACITAT_DELUXE);

        // Disponibilitat inicial (comença igual que la capacitat)
        disponibilitatHabitacions.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        disponibilitatHabitacions.put(TIPUS_SUITE, CAPACITAT_SUITE);
        disponibilitatHabitacions.put(TIPUS_DELUXE, CAPACITAT_DELUXE);

        // Preus serveis
        preusServeis.put(SERVEI_ESMORZAR, 10f);
        preusServeis.put(SERVEI_GIMNAS, 15f);
        preusServeis.put(SERVEI_SPA, 20f);
        preusServeis.put(SERVEI_PISCINA, 25f);
    }

    /**
     * Mostra el menú principal amb les opcions disponibles per a l'usuari.
     */
    public static void mostrarMenu() {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Reservar una habitació");
        System.out.println("2. Alliberar una habitació");
        System.out.println("3. Consultar disponibilitat");
        System.out.println("4. Llistar reserves per tipus");
        System.out.println("5. Obtindre una reserva");
        System.out.println("6. Ixir");
    }

    /**
     * Processa l'opció seleccionada per l'usuari i crida el mètode corresponent.
     */
    public static void gestionarOpcio(int opcio) {
        switch (opcio) {
            case 1:
                reservarHabitacio();
                break;
            case 2: 
                alliberarHabitacio();
                break;
            case 3:
                consultarDisponibilitat();
                break;
            case 4:
                llistarReservesPerTipus(null, TIPUS_DELUXE);
                break;
            case 5:
                obtindreReserva();
                break;
            case 6:
                break;
            default:
                System.out.println("Has elegido una opción invalida");
                main(null);
                break;
        }

       //TODO:
    }

    /**
     * Gestiona tot el procés de reserva: selecció del tipus d'habitació,
     * serveis addicionals, càlcul del preu total i generació del codi de reserva.
     */
    public static void reservarHabitacio() {
        System.out.println("\n===== RESERVAR HABITACIÓ =====");
        //Variables usadas
        String tipo = "";
        ArrayList<String> servicios = new ArrayList<>();
        float precioTotal = 0f;
        int codiReser = 0;
        ArrayList<String> reservaArray = new ArrayList<>();

        //llamado a las funcioenes
        tipo = seleccionarTipusHabitacio();
        servicios = seleccionarServeis();
        precioTotal = calcularPreuTotal(tipo, servicios);
        codiReser = generarCodiReserva();

        //uso de los datos de las funciones
        reservaArray.add(tipo);
        reservaArray.add(Float.toString(precioTotal));
        reservaArray.add(servicios.toString());

        //guardado de la reserva
        reserves.put(codiReser, reservaArray);
        System.out.println("El coste total de tu habitación es de "+ precioTotal + "\ntu codigo de reserva es "+ codiReser);

        //TODO:
        
    }

    /**
     * Pregunta a l'usuari un tipus d'habitació en format numèric i
     * retorna el nom del tipus.
     */
    public static String seleccionarTipusHabitacio() {
        String tipo = "";
        System.out.println("== Tipos de habitación ==");
        System.out.println("1. Estandar");
        System.out.println("2. Suite.");
        System.out.println("3. Deluxe.");
        System.out.print("seleccionar tipo de habitación: ");
        switch (sc.nextInt()) {
            case 1:
                tipo = TIPUS_ESTANDARD;
                System.out.println("has elegido el tipo estandar");
                break;
            case 2: 
                tipo = TIPUS_SUITE;
                System.out.println("has elegido el tipo Suite");
                break;
            case 3: 
                tipo = TIPUS_DELUXE;
                System.out.println("has elegido el tipo Deluxe");
                break;
            default:
                System.out.println("Has elegido un tipo invalido!!");
                tipo = "";
                seleccionarTipusHabitacio();
                break;
        }        

        return tipo;

    }

    /**
     * Mostra la disponibilitat i el preu de cada tipus d'habitació,
     * demana a l'usuari un tipus i només el retorna si encara hi ha
     * habitacions disponibles. En cas contrari, retorna null.
     */
    public static String seleccionarTipusHabitacioDisponible() {
        System.out.println("\nTipus d'habitació disponibles:");
        //TODO:
        return null;
    }

    /**
     * Permet triar serveis addicionals (entre 0 i 4, sense repetir) i
     * els retorna en un ArrayList de String.
     */
    public static ArrayList<String> seleccionarServeis() {
        ArrayList<String> servicios = new ArrayList<>();
        servicios.add(SERVEI_ESMORZAR);
        servicios.add(SERVEI_GIMNAS);
        servicios.add(SERVEI_SPA);
        servicios.add(SERVEI_PISCINA);
        ArrayList<String> serviciosAñadidos = new ArrayList<>();
            int cont = 0;
            switch (llegirEnter("¿Quieres servicios? \n1. Si\n2. No\n")) {
                case 1:
                    while (cont < 4) {
                        switch (llegirEnter("¿Quieres el servicio de "+ servicios.get(cont)+"?"+ "\n1. Si\n2. No\n")) {
                            case 1:
                                if (serviciosAñadidos.contains(servicios.get(cont))){
                                    System.out.println("Ese servicio ya esta añadido");
                                }else{
                                    System.out.println("Has elegido el servicio "+ servicios.get(cont));
                                    serviciosAñadidos.add(servicios.get(cont));
                                    cont++;
                                }
                            break;
                            case 2:
                                System.out.println("No quieres el servicio "+ servicios.get(cont));
                                cont++;
                            break;
                            default:
                                System.out.println("Eso no es un servicio!!");
                            break;
                        }
                    }
                    System.out.println("Has elegido los servicios: "+ serviciosAñadidos);
                    break;
                case 2:
                    System.out.println("No quieres ningun servicio");
                    break;
                default:
                    System.out.println("Has elegido una opcion invalida");
                    break;
            } 
        return serviciosAñadidos;
    }

    /**
     * Calcula i retorna el cost total de la reserva, incloent l'habitació,
     * els serveis seleccionats i l'IVA.
     */
    public static float calcularPreuTotal(String tipusHabitacio, ArrayList<String> serveisSeleccionats) {
        float preuHabitacio = preusHabitacions.get(tipusHabitacio); 
        int cont = 0;
        float preuServicios = 0;
        while (cont < serveisSeleccionats.size()) {
            preuServicios += preusServeis.get(serveisSeleccionats.get(cont));
            cont++;
        }
        float preuIVA = (preuHabitacio + preuServicios) * IVA;
        float preuTotal = preuHabitacio + preuServicios + preuIVA; 
       
        //TODO:
        return preuTotal;
    }

    /**
     * Genera i retorna un codi de reserva únic de tres xifres
     * (entre 100 i 999) que no estiga repetit.
     */
    public static int generarCodiReserva() {
        int codig = random.nextInt(100, 999);
        while (reserves.containsKey(codig)) {
            codig = random.nextInt(100, 999);
        }
        //TODO:
        return codig;
    }

    /**
     * Permet alliberar una habitació utilitzant el codi de reserva
     * i actualitza la disponibilitat.
     */
    public static void alliberarHabitacio() {
        System.out.println("\n===== ALLIBERAR HABITACIÓ =====");
         // TODO: Demanar codi, tornar habitació i eliminar reserva
    }

    /**
     * Mostra la disponibilitat actual de les habitacions (lliures i ocupades).
     */
    public static void consultarDisponibilitat() {
        // TODO: Mostrar lliures i ocupades
    }

    /**
     * Funció recursiva. Mostra les dades de totes les reserves
     * associades a un tipus d'habitació.
     */
    public static void llistarReservesPerTipus(int[] codis, String tipus) {
         // TODO: Implementar recursivitat
    }

    /**
     * Permet consultar els detalls d'una reserva introduint el codi.
     */
    public static void obtindreReserva() {
        System.out.println("\n===== CONSULTAR RESERVA =====");
        // TODO: Mostrar dades d'una reserva concreta
 
    }

    /**
     * Mostra totes les reserves existents per a un tipus d'habitació
     * específic.
     */
    public static void obtindreReservaPerTipus() {
        System.out.println("\n===== CONSULTAR RESERVES PER TIPUS =====");
        // TODO: Llistar reserves per tipus
    }

    /**
     * Consulta i mostra en detall la informació d'una reserva.
     */
    public static void mostrarDadesReserva(int codi) {
       // TODO: Imprimir tota la informació d'una reserva
    }

    // --------- MÈTODES AUXILIARS (PER MILLORAR LEGIBILITAT) ---------

    /**
     * Llig un enter per teclat mostrant un missatge i gestiona possibles
     * errors d'entrada.
     */
    static int llegirEnter(String missatge) {
        int valor = 0;
        boolean correcte = false;
        while (!correcte) {
                System.out.print(missatge);
                valor = sc.nextInt();
                correcte = true;
        }
        return valor;
    }

    /**
     * Mostra per pantalla informació d'un tipus d'habitació: preu i
     * habitacions disponibles.
     */
    static void mostrarInfoTipus(String tipus) {
        int disponibles = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        float preu = preusHabitacions.get(tipus);
        System.out.println("- " + tipus + " (" + disponibles + " disponibles de " + capacitat + ") - " + preu + "€");
    }

    /**
     * Mostra la disponibilitat (lliures i ocupades) d'un tipus d'habitació.
     */
    static void mostrarDisponibilitatTipus(String tipus) {
        int lliures = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        int ocupades = capacitat - lliures;

        String etiqueta = tipus;
        if (etiqueta.length() < 8) {
            etiqueta = etiqueta + "\t"; // per a quadrar la taula
        }

        System.out.println(etiqueta + "\t" + lliures + "\t" + ocupades);
    }
}
