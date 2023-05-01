package models;

import java.util.ArrayList;
import java.util.Random;

public class Individuo {

    public double[] genes;

    public double[] avaliacao;

    public double Cd;
    public Random random;

    public Individuo(int QTD_GENES,int QTD_AVALIACAO){
        genes = new double[QTD_GENES];
        avaliacao = new double[QTD_AVALIACAO];
        random = new Random();
    }

    public void avaliar(int FUNCTION){
        switch (FUNCTION){
            case 1:
                // problema 1
                this.avaliacao[0] = (Math.pow(this.genes[0],2));
                this.avaliacao[1] = Math.pow(this.genes[0]-1,2);
                break;
            case 2:
                // problema 2
                this.avaliacao[0] = (Math.pow(this.genes[0],2))+(Math.pow(this.genes[1],2));
                this.avaliacao[1] = (Math.pow(this.genes[0],2))+(Math.pow(this.genes[1]-2,2));
                break;
            case 3:
                // problema 3

                this.avaliacao[0] = (Math.pow(this.genes[0]-1,2))+(Math.pow(this.genes[1],2))+(Math.pow(this.genes[2],2));
                this.avaliacao[1] = (Math.pow(this.genes[0],2))+(Math.pow(this.genes[1]-1,2))+(Math.pow(this.genes[2],2));
                this.avaliacao[2] = (Math.pow(this.genes[0],2))+(Math.pow(this.genes[1],2))+(Math.pow(this.genes[2]-1,2));
                break;
        }



    }

    public void gerarIndividuo(ArrayList<Individuo> individuos, int r1, int r2, int r3, double F){
        for (int j = 0; j < individuos.get(r1).genes.length; j++) {
            this.genes[j] = individuos.get(r3).genes[j] + F * (individuos.get(r1).genes[j]-individuos.get(r2).genes[j]); // fazer isso para cada posicao
        }
    }

    public void gerarGenes(double a, double b){
        for (int i = 0; i < genes.length; i++) {
            this.genes[i] = random.nextDouble(a, b);
        }
    }
}


