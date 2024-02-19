package knapsack;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// fc56374 - Vasco Maria
public class Main {
	public static void main(String[] args) throws IOException {
		StringBuilder sb = new StringBuilder();

		FileWriter fw = new FileWriter("ParalTimes12NP.csv");
		try (BufferedWriter out = new BufferedWriter(fw)) {
			sb.append("GA").append(",").append("seconds").append("\n");
			out.write(sb.toString());
			sb.setLength(0);
			long startTime = System.nanoTime();
			KnapsackGA ga = new KnapsackGA();
			// Sem phaser em baixo comentando 
			//KnapsackGANoPhaser ga = new KnapsackGANoPhaser();
			for (int i = 0; i < 30; i++) {
				System.out.println(i);
				sb.append(i).append(",");
				long startTimeGA = System.nanoTime();
				ga.run();
				long estimatedTimeGA = System.nanoTime() - startTimeGA;
				double elapsedTimeInSecondGA = (double) estimatedTimeGA / 1_000_000_000;
				sb.append(elapsedTimeInSecondGA).append("\n");
				out.write(sb.toString());
				sb.setLength(0);

			}
			// TEMPO PARA EU VER NA CONSOLA A DEMORA TOTAL DO PROGRAMA
			long estimatedTimeComplete = System.nanoTime() - startTime;
			double elapsedTimeInSecond = (double) estimatedTimeComplete / 1_000_000_000;
			System.out.println(estimatedTimeComplete);
			System.out.println(elapsedTimeInSecond + " seconds");
		}
		
	}
}
