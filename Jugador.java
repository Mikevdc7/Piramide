import java.util.ArrayList;

public class Jugador {

    private String nom;
    private ArrayList<Carta> cartes;

    // Constructor
    public Jugador (String nom){
        this.nom = nom;
    }

    // Getters i Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<Carta> getCartes() {
        return cartes;
    }

    public void setCartes(ArrayList<Carta> cartes) {
        this.cartes = cartes;
    }

    /**
     * @param numero
     * @return 'S' si té la carta, 'N' si no la té, 'D' si la té dos o més voltes
     **/
    public String teCarta(String numero){
        int contadorCartes = 0;
        for(Carta c:this.cartes){
            if(numero.equals(c.getNumero())){
                contadorCartes++;
            }
        }
        if(contadorCartes>1){
            return "D";
        } else if (contadorCartes == 1){
            return "S";
        } else {
            return "N";
        }
    }

    public void mostraCartes(){
        System.out.println(this.nom + " te: ");
        for(Carta c:this.cartes){
            System.out.println("El " + c.getNumero() + " de " + c.getPalo());
        }
        System.out.println("");
    }
}
