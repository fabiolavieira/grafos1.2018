import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;

public class Aplicacao {

    public static void main(String[] args) throws Exception {

        Reader alunoFile = new FileReader("C://Users//fabio//Downloads/alunos.txt");
        Reader pesquisaFile = new FileReader("C://Users//fabio//Downloads/pesquisa.txt");

        BufferedReader bufferedReader = new BufferedReader(alunoFile);
        String line = bufferedReader.readLine();
        int numAlunos = 0;
        while (line != null) {
            numAlunos++;
            line = bufferedReader.readLine();
        }

        Set<Integer> pesos = new TreeSet<Integer>();
        bufferedReader = new BufferedReader(pesquisaFile);
        line = bufferedReader.readLine();
        int numPesquisas = 0;
        while (line != null) {
            String[] aux = line.split(" ");
            for (int i = 0; i < aux.length; i++) {
                pesos.add(Integer.parseInt(aux[i]));
            }
            numPesquisas++;
            line = bufferedReader.readLine();
        }

        Integer[] listaPesos = new Integer[numPesquisas];
        int p = 0;
        for (Integer peso : pesos) {
            listaPesos[p] = peso;
            p++;
        }

        bufferedReader.close();

        // cria matriz de dissimilaridade
        bufferedReader = new BufferedReader(new FileReader("C://Users//fabio//Downloads/pesquisa.txt"));
        line = bufferedReader.readLine();
        int m[][] = new int[numPesquisas][numPesquisas];
        int linha = 0;
        while (line != null) {
            linha++;
            String[] aux = line.split(" ");
            line = bufferedReader.readLine();

            for (int i = 0; i < numPesquisas; i++) {
                m[linha - 1][i] = Integer.parseInt(aux[i]);
                m[i][linha - 1] = Integer.parseInt(aux[i]);
            }
        }
        bufferedReader.close();

        // cria vetor de vertices
        bufferedReader = new BufferedReader(new FileReader("C://Users//fabio//Downloads/alunos.txt"));
        line = bufferedReader.readLine();
        int vertices[] = new int[numAlunos];
        int n = 0;
        while (line != null) {
            String[] aux = line.split(" ");
            int v = Integer.parseInt(aux[1]);
            vertices[n] = v;
            line = bufferedReader.readLine();
            n++;
        }
        bufferedReader.close();

        //cria arestas
        Grafo grafo = new Grafo(numAlunos);
        int v1 = 0, v2 = 0;
        for (int i = 0; i < vertices.length; i++) {
            for (int j = 0; j < vertices.length; j++) {
                if (i != j) {
                    v1 = vertices[i];
                    v2 = vertices[j];
                    grafo.insereAresta(i, j, m[v1 - 1][v2 - 1]);
                }
            }
        }

        Scanner ler = new Scanner(System.in);
        System.out.println("Informe o numero de professores: ");
        int numProfs = ler.nextInt();
        ler.close();

        int prof = 0;

        AgmPrim agm = new AgmPrim(grafo);
        agm.obterAgm(1);

        List<AgmAresta> arestas = agm.retornaArestas();
        List<Grupo> grupos = new ArrayList<Grupo>();

        List<Integer> agrupados = new ArrayList<Integer>();

        if (numProfs == numAlunos) {
            int profe = 0;
            for (AgmAresta aresta : arestas) {
                List<Integer> integrantes1 = new ArrayList<Integer>();
                if (!agrupados.contains(aresta.v1)) {
                    profe++;
                    integrantes1.add(aresta.v1);
                    agrupados.add(aresta.v1);
                }
                if (integrantes1.size() != 0) {
                    grupos.add(new Grupo(profe, aresta.peso, integrantes1));
                }
                List<Integer> integrantes2 = new ArrayList<Integer>();
                if (!agrupados.contains(aresta.v2)) {
                    profe++;
                    integrantes2.add(aresta.v2);
                    agrupados.add(aresta.v2);
                }
                if (integrantes2.size() != 0) {
                    grupos.add(new Grupo(profe, aresta.peso, integrantes2));
                }
            }
        } else {
            int gruposProf = 0, contG = 0;
            prof = 1;

            if ((numPesquisas / numProfs) % 2 == 0) {
                gruposProf = numPesquisas / numProfs;
            } else {
                gruposProf = (numPesquisas / numProfs) + 1;
            }

            for (int x = 0; x < listaPesos.length; x++) {

                for (AgmAresta aresta : arestas) {
                    List<Integer> integrantes = new ArrayList<Integer>();
                    if (aresta.peso == listaPesos[x]) {
                        if (contG >= gruposProf) {
                            prof++;
                            contG = 0;
                        }
                        if (!agrupados.contains(aresta.v1) && aresta.v1 != -1) {
                            integrantes.add(aresta.v1);
                            agrupados.add(aresta.v1);
                            aresta.v1 = -1;
                        }
                        if (!agrupados.contains(aresta.v2) && aresta.v2 != -1) {
                            integrantes.add(aresta.v2);
                            agrupados.add(aresta.v2);
                            aresta.v2 = -1;
                        }
                        if (integrantes.size() != 0) {
                            grupos.add(new Grupo(prof, aresta.peso, integrantes));
                            contG++;
                            aresta.peso = -1;
                        }
                    }
                }
            }
        }

        for (Grupo grupo : grupos) {
            System.out.print("\nProfessor: " + grupo.prof + "\nPeso: " + grupo.peso + "\nIntegrantes: ");
            grupo.integrantes.forEach(element -> System.out.print((Integer) (element + 1) + " "));
            System.out.println("\n");
        }

    }
}
