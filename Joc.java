import java.util.*;

public class Joc {

    private ArrayList<Jugador> jugadors;
    private Map<String, List<String>> baralla;
    private int cartesPerJugador;
    private Palos palos[];

    public Joc (){
        this.jugadors = new ArrayList<Jugador>();
        this.baralla = new HashMap<String, List<String>>();
        this.palos = Palos.values();
        this.cartesPerJugador = 2;
    }

    // Getters i Setters
    public ArrayList<Jugador> getJugadors() {
        return jugadors;
    }

    public void setJugadors(ArrayList<Jugador> jugadors) {
        this.jugadors = jugadors;
    }

    public Map<String, List<String>> getBaralla() {
        return baralla;
    }

    public void setBaralla(Map<String, List<String>> baralla) {
        this.baralla = baralla;
    }

    public Palos[] getPalos() {
        return palos;
    }

    public void setPalos(Palos[] palos) {
        this.palos = palos;
    }

    public int getCartesPerJugador() {
        return cartesPerJugador;
    }

    public void setCartesPerJugador(int cartesPerJugador) {
        this.cartesPerJugador = cartesPerJugador;
    }

    public void initBaralla(){
        Map<String, List<String>> mapaBaralla = new HashMap<String, List<String>>();
        String palo = "";
        List<String> listaNumeros = new ArrayList<String>();
        for(int i=0; i<4; i++){ // Bucle que recorre els palos de la baralla
            palo = this.palos[i].toString();
            for(int j=0; j<12; j++){ // Bucle que recorre els numeros de la baralla
                listaNumeros.add(String.valueOf(j+1));
            }
            mapaBaralla.put(palo, listaNumeros);
            listaNumeros = new ArrayList<String>();
        }
        setBaralla(mapaBaralla);
    }

    public void repartir(){
        Map<String, List<String>> baralla = this.baralla;
        List<String> numerosPalo;
        ArrayList<Jugador> jugadors = this.jugadors;
        Jugador jugador;
        ArrayList<Carta> cartesJugador = new ArrayList<Carta>();
        Random paloRandom = new Random();
        int minPalo = 0, maxPalo = 4;

        Random numeroRandom = new Random();
        int minNumero = 1, maxNumero = 12;

        int generoPaloRandom = -1;
        String paloGenerado = "-1";
        int numeroGenerado = -1;
        boolean tengoNumero = false;
        int indiceQuitarNumero = -1;

        for(int i=0; i < jugadors.size(); i++){
            jugador = jugadors.get(i);
            cartesJugador = new ArrayList<Carta>();
            for(int j=0; j<this.cartesPerJugador; j++){
                Carta carta = new Carta();
                indiceQuitarNumero = -1;
                if(baralla.size()>0){
                    while(!baralla.containsKey(paloGenerado)){
                        generoPaloRandom = paloRandom.nextInt(minPalo, maxPalo);
                        paloGenerado = this.palos[generoPaloRandom].toString();
                    }
                    numerosPalo = baralla.get(paloGenerado);
                    while(!tengoNumero){
                        numeroGenerado = numeroRandom.nextInt(minNumero, maxNumero);
                        for(int k=0; k<numerosPalo.size(); k++){
                            if(numerosPalo.get(k).equals(Integer.toString(numeroGenerado))){
                                tengoNumero = true;
                                indiceQuitarNumero = k;
                                break;
                            }
                        }
                    }
                    if(indiceQuitarNumero!=-1 && baralla.get(paloGenerado).size()>indiceQuitarNumero){
                        baralla.get(paloGenerado).remove(indiceQuitarNumero);
                    }
                    if(baralla.get(paloGenerado).size()==0){
                        baralla.remove(paloGenerado);
                    }
                    tengoNumero = false;
                    carta.setPalo(paloGenerado);
                    carta.setNumero(String.valueOf(numeroGenerado));
                    cartesJugador.add(carta);
                }
                paloGenerado = "-1";
            }
            jugador.setCartes(cartesJugador);
        }
    }

    public Carta destapaCarta(){

        Carta carta = new Carta();
        Random random = new Random();
        int claveRandom = random.nextInt(baralla.size());
        ArrayList<String> valorsBaralla = (ArrayList<String>) baralla.get(claveRandom);
        int valorRandom = random.nextInt(valorsBaralla.size());
        String paloSacado = palos[claveRandom].toString();
        String numeroSacado = valorsBaralla.get(valorRandom);
        carta.setPalo(paloSacado);
        carta.setNumero(numeroSacado);

        valorsBaralla.remove(valorRandom);
        if(valorsBaralla.size()==0){
            baralla.remove(paloSacado);
        }
        return carta;
    }
}