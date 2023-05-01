package models;

import java.util.List;

public class CrowdingDistance {

    public List<Individuo> cdAvaliar(List<Individuo> front){
        int frontLength = front.size();

        for (Individuo ind : front) {
            ind.Cd = 0;
        }

        Individuo ind = front.get(0);
        int M = ind.genes.length;

        for (int m = 0; m < M ; m++) {
            sort(front,M);

            front.get(0).Cd = Double.POSITIVE_INFINITY;
            front.get(frontLength-1).Cd = Double.POSITIVE_INFINITY;

            for (int i = 1; i < front.size()-1; i++) {
                Individuo antInd = front.get(i-1);
                Individuo postInd = front.get(i+1);

                Individuo menorValor = front.get(0);
                Individuo maiorValor = front.get(frontLength-1);

                double aux = (postInd.genes[m] - antInd.genes[m])/ (maiorValor.genes[m] - menorValor.genes[m]);

                postInd.Cd += aux;
            }
        }
        return front;
    }
    public static void sort(List<Individuo> list, int m) {
        int n = list.size();
        Individuo temp;

        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (list.get(i).genes[m]>list.get(j).genes[m]) {
                    // swap list[j] and list[j+1]
                    temp = list.get(j);
                    list.set(j, list.get(j+1));
                    list.set(j+1, temp);
                }
            }
        }
    }
}
