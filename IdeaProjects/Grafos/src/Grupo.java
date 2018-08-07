import java.util.List;

public class Grupo {

    public int prof;
    public int peso;
    public List<Grupo> g;
    public List<Integer> integrantes;

    public Grupo() {
        this.peso = -1;
        this.prof = -1;
        this.integrantes = null;
    }

    public Grupo(int prof, int peso, List<Integer> integrantes) {
        this.peso = peso;
        this.prof = prof;
        this.integrantes = integrantes;
    }

}
