package app;

import models.CrowdingDistance;
import models.FNDS;
import models.GenerateFile;
import models.Individuo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class main {

  public static void main(String[] args) {

    final int MAX_GEN = 10000;
    final int NUM_INDIVIDUOS = 20;
    final double X_BOUND = 10 ;// 20 e -20
    final double F = 0.5;
    final double Cr = 0.8;
    final int FUNCTION = 1;

    switch (FUNCTION){
      case 1:
        int QTD_GENES = 1;
        int QTD_AVALIACAO

    }
    FNDS fnds = new FNDS();
    ArrayList<Individuo> individuos = new ArrayList<>(NUM_INDIVIDUOS);
    individuos.addAll(init(NUM_INDIVIDUOS,X_BOUND));
    // funcao = x1^2 + x2^2


    Individuo exp = new Individuo();
    Random random = new Random();

    avaliaIndividuos(individuos);

    int INICIAL_GEN = 1;
    ArrayList<Individuo> new_pop = null;
    ArrayList<Individuo> intermed_Pop = null;

    final List<Integer> genToPrint = Arrays.asList(1,20, 40, 60, 80,100, MAX_GEN);


    while(INICIAL_GEN <= MAX_GEN){

      new_pop = new ArrayList<>(NUM_INDIVIDUOS);
      intermed_Pop = new ArrayList<>(NUM_INDIVIDUOS);


      for(int i = 0; i < NUM_INDIVIDUOS; i++){

        int r1 = random.nextInt(0,NUM_INDIVIDUOS-1);
        int r2 = random.nextInt(0,NUM_INDIVIDUOS-1);
        int r3 = random.nextInt(0,NUM_INDIVIDUOS-1);

        Individuo u = new Individuo();
        u.gerarIndividuo(individuos,r1,r2,r3,F);


        exp = recombinar(individuos.get(i),u,Cr);
        exp.avaliar();

        intermed_Pop.add(exp);
      }
      List<List<Individuo>> fronts = fnds.execute(intermed_Pop);

      for (List<Individuo> front : fronts) {
        if (new_pop.size() >= NUM_INDIVIDUOS) break;

        if (front.size() + new_pop.size() > NUM_INDIVIDUOS) {
          List<Individuo> individuoCD = new CrowdingDistance().cdAvaliar(front);
          for (Individuo individuo : individuoCD) {
            if (new_pop.size() < NUM_INDIVIDUOS) {
              new_pop.add(individuo);
            } else break;
          }

        } else {
          new_pop.addAll(front);
        }
      }

      individuos = new_pop;

      if (genToPrint.contains(INICIAL_GEN)) {
        generatePonts(individuos, INICIAL_GEN);
      }

      INICIAL_GEN++;
    }

    for (Individuo individuo :individuos) {
      System.out.println();
    }

    new_pop = new_pop;
//    for (int i = 0; i < individuos.size(); i++) {
//      String dadosDoIndividuo = "models.Individuo " + (i+1);
//      for (int j = 0; j < individuos.get(i).genes.length ; j++) {
//        dadosDoIndividuo += "\t Genes " + (j+1)+": " +  individuos.get(i).genes[j];
//      }
//      for (int j = 0; j <individuos.get(i).avaliacao.length ; j++) {
//        dadosDoIndividuo += "\t Avaliacao " + (j+1)+": " +  individuos.get(i).avaliacao[j];
//      }
//      System.out.println(dadosDoIndividuo);
//    }
  }



  public static Individuo recombinar(Individuo individuo, Individuo u, double Cr){
    Individuo filho = new Individuo();
    for(int i = 0; i < individuo.genes.length ; i++){
      double r = new Random().nextDouble(0,1);
      if(r < Cr){
        filho.genes[i] = individuo.genes[i];
      }else{
        filho.genes[i] = u.genes[i];
      }
    }
    return filho;
  }

  public  static void avaliaIndividuos(ArrayList<Individuo> individuos){
    for (Individuo individuo :
            individuos) {
      individuo.avaliar();
    }
    individuos = individuos;
  }

  public static ArrayList<Individuo> init(int NUM_INDIVIDUOS, double X_BOUND){
    ArrayList<Individuo> popInicial = new ArrayList<>(NUM_INDIVIDUOS);
    for (int i = 0; i < NUM_INDIVIDUOS; i++) {
      Individuo individuo = new Individuo();
      individuo.gerarGenes(-X_BOUND,X_BOUND);
      popInicial.add(individuo);
    }
    return popInicial;
  }

  private static void generatePonts(List<Individuo> individuals, int numGen) {
    try {
      generateGensFile(individuals, numGen);
      generateFunctionResults(individuals, numGen);
    } catch (IOException erro){
      System.out.printf("Erro: %s", erro.getMessage());
    }
  }

  private static void generateGensFile(List<Individuo> individuals, int numGen) throws IOException {
    String path = "./src/files/gen_"+numGen+"_genes.txt";
    GenerateFile.createFile(path, individuals, false);
  }

  private static void generateFunctionResults(List<Individuo> individuals, int numGen) throws IOException {
    String path = "./src/files/gen_"+numGen+"_values.txt";
    GenerateFile.createFile(path, individuals, true);
  }
}