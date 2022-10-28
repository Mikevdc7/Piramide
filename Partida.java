import java.util.*;

public class Partida {

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        init();
    }

    public static void init() {
        System.out.println("Este es el joc de La Piramide...");
        Joc joc = new Joc();
        int n = -1;
        boolean errorInput = false;
        do {
            System.out.println("Quants jugadors sou? ");
            try {
                n = (int) Integer.valueOf(input.next());
                errorInput = false;
            } catch (Exception ex) {
                System.out.println("Has d'escriure un numero. Torna a provar.");
                errorInput = true;
            }
        } while(errorInput);

        Jugador jugador;
        String nom = "";
        ArrayList<Jugador> llistaJugadors = new ArrayList<Jugador>();
        for (int i = 0; i < n; i++) {
            System.out.println("Indica el nom del Jugador " + (i + 1) + ":");
            nom = input.next();
            jugador = new Jugador(nom);
            llistaJugadors.add(jugador);
        }
        System.out.println("Juguen els seguents jugadors: ");
        System.out.println("\t");
        for (int i = 0; i < llistaJugadors.size(); i++) {
            System.out.print(llistaJugadors.get(i).getNom());
            if(i<llistaJugadors.size()-2){
                System.out.print(", ");
            } else if(i==llistaJugadors.size()-2){
                System.out.print(" i ");
            }
        }
        joc.setJugadors(llistaJugadors);
        System.out.println("\n\nS'esta creant la baralla...");
        joc.initBaralla();
        System.out.println("\nRepartint les cartes...\n");
        joc.repartir();
        for (int i = 0; i < llistaJugadors.size(); i++) {
            llistaJugadors.get(i).mostraCartes();
        }

        seguentCarta();

        System.out.println("\nPosant les cartes al tapet...\n");
        // Barallem les cartes i montem un treemap amb les cartes que formaran la piramide
        List<Carta> llistaCartes = barallarCartes(joc);

        // Key: Piso en el que ens trobem; Value: Cartes d'eixe piso
        SortedMap<Integer, List<Carta>> map = new TreeMap<Integer, List<Carta>>(Collections.reverseOrder());
        List<Carta> cartesPiso;
        Carta carta;
        int pisoActual = 0;
        int cartesPisoActual=0;
        for (int i = 0; i < llistaCartes.size(); i+=cartesPisoActual) {
            cartesPisoActual=pisoActual+1;
            cartesPiso = new ArrayList<Carta>();
            carta = new Carta();
            for (int j = 0; j < cartesPisoActual; j++) {
                if((i+j)<llistaCartes.size()){
                    carta = llistaCartes.get(i+j);
                    cartesPiso.add(carta);
                }
            }
            map.put(pisoActual++, cartesPiso);

        }
        if(map.size()>1 && map.get(map.size()-1).size()<=map.get(map.size()-2).size()){
            List<Carta> llistaSolta = map.get(map.size()-1);
            for(int i=0; i<llistaSolta.size(); i++){
                map.get(map.size()-i-2).add(llistaSolta.get(i));
            }
            map.remove((map.size()-1));
        }

        int piso = 1;
        cartesPiso = new ArrayList<Carta>();
        Carta cartaAlzada;
        for(Map.Entry<Integer, List<Carta>> element: map.entrySet()){
            System.out.print("Piso " + piso++ + " (");
            if(piso%2==0){
                System.out.println("beus)");
            } else{
                System.out.println("fas beure)");
            }
            cartesPiso = element.getValue();
            for(int i=0; i<cartesPiso.size(); i++){
                cartaAlzada = cartesPiso.get(i);
                System.out.print("\tCarta " + (i+1) + ": ");
                System.out.println(cartaAlzada.getNumero() + " de " + cartaAlzada.getPalo() + "\n");
                buscaCartaCoincident(cartaAlzada, llistaJugadors, piso);
                if((piso) <= map.size()){
                    System.out.println("Passem a la seguent carta...\n");
                } else{
                    System.out.println("S'han acabat les cartes.");
                }
                seguentCarta();
            }

        }
        System.out.println("\nS'ha acabat la partida, espero que s'hageu acalentat mes que menos :P");
    }

    public static List<Carta> barallarCartes(Joc joc) {
        List<Carta> llistaCartes = new ArrayList<Carta>();
        Carta carta;
        Map<String, List<String>> baralla = joc.getBaralla();
        for(Map.Entry<String, List<String>> element : baralla.entrySet()){
            for(int i=0; i<element.getValue().size(); i++){
                carta = new Carta();
                carta.setPalo(element.getKey());
                carta.setNumero(element.getValue().get(i));
                llistaCartes.add(carta);
            }
        }

        // Knuth Shuffle (Mescla de Knut)
        Random random = new Random();
        Carta swap1, swap2;
        int r;
        for(int i=llistaCartes.size()-1; i>=0; i--){
            r = random.nextInt(0, llistaCartes.size());
            swap1 = llistaCartes.get(i);
            swap2 = llistaCartes.get(r);
            llistaCartes.set(i, swap2);
            llistaCartes.set(r, swap1);
        }
        return llistaCartes;
    }

    public static void buscaCartaCoincident(Carta cartaAlzada, ArrayList<Jugador> llistaJugadors, int piso){
        boolean alguLaTe = false;
        for(int i=0; i<llistaJugadors.size(); i++){
            for(int j=0; j<llistaJugadors.get(i).getCartes().size(); j++){
                if(cartaAlzada.getNumero().equals(llistaJugadors.get(i).getCartes().get(j).getNumero())){
                    System.out.print("\t\tAtencio! " + llistaJugadors.get(i).getNom() + " te un " + cartaAlzada.getNumero() + " i ");
                    if(piso%2==0){
                        System.out.print("ha de beure " + (piso-1) + " trago");

                    } else{
                        System.out.print("fa beure " + (piso-1) + " trago");
                    }
                    if((piso-1)>1){
                        System.out.print("s");
                    }
                    System.out.println("!\n");
                    alguLaTe = true;
                }
            }
        }
        if(!alguLaTe){
            System.out.println("\t\tNingu la te.\n");
        }
    }

    public static void seguentCarta(){
        input.next();
    }
}