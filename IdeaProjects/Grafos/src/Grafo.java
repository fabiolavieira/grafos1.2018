public class Grafo {

    private Lista adj[];
    private int numVertice;

    public Grafo(int numVertice) {
        this.adj = new Lista[numVertice];
        this.numVertice = numVertice;
        for (int i = 0; i < this.numVertice; i++) {
            this.adj[i] = new Lista();
        }
    }

    public void insereAresta(int v1, int v2, int peso) {
        Celula item = new Celula(v2, peso);
        this.adj[v1].insere(item);
    }

    public boolean existeAresta(int v1, int v2) {
        Celula item = new Celula(v1, 0);
        return (this.adj[v1].pesquisa(item) != null);
    }

    public boolean listaAdjVazia(int v) {
        return this.adj[v].vazia();
    }

    public Aresta primeiroListaAdj(int v) {
        //Retorna a primeira aresta que o int participa ou null se a lista de adjacencia for vazia
        Celula item = (Celula) this.adj[v].primeiro();
        return item != null ? new Aresta(v, item.vertice, item.peso) : null;
    }

    public Aresta proxAdj(int v) {
        //Retorna a proxima aresta que o int v participa ou null se a lista de adjacencia estiver no fim
        Celula item = (Celula) this.adj[v].proximo();
        return item != null ? new Aresta(v, item.vertice, item.peso) : null;
    }

    public Aresta retiraAresta(int v1, int v2) throws Exception {
        Celula chave = new Celula(v2, 0);
        Celula item = (Celula) this.adj[v1].retira(chave);
        return item != null ? new Aresta(v1, v2, item.peso) : null;
    }

    public void imprime() {
        for (int i = 0; i < this.numVertice; i++) {
            System.out.println("int " + i + ":");
            Celula item = (Celula) this.adj[i].primeiro();
            while (item != null) {
                System.out.println(" " + item.vertice + " (" + item.peso + ")");
                item = (Celula) this.adj[i].proximo();
            }
        }
    }

    public int numVertices() {
        return this.numVertice;
    }

    public static class Vertice {

        int pesquisa;

        public Vertice(int pesquisa) {
            this.pesquisa = pesquisa;
        }
    }

    public static class Aresta {

        int v1, v2;
        int peso;

        public Aresta(int v1, int v2, int peso) {
            this.v1 = v1;
            this.v2 = v2;
            this.peso = peso;
        }

        public int v1() {
            return this.v1;
        }

        public int v2() {
            return this.v2;
        }

        public int peso() {
            return this.peso;
        }

    }

    public static class Celula {
        int vertice;
        int peso;

        public Celula(int v, int p) {
            this.vertice = v;
            this.peso = p;
        }

        public boolean equals(Object obj) {
            Celula item = (Celula) obj;
            return (this.vertice == item.vertice);
        }
    }

}