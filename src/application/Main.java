package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import entities.Product;

public class Main {

	public static void main(String[] args) {
		System.out.println("Basic Stream usage and functions: ");
		basicStream();
		System.out.println("= - = - = - = - = - = - = - = - = ");
		System.out.println("Basic Pipeline usage and functions: ");
		basicPipeline();
		System.out.println("= - = - = - = - = - = - = - = - = ");

		System.out.println("Exercicio resolvido: ");
		exercicioResolvido();
		System.out.println("= - = - = - = - = - = - = - = - = ");
	}

	public static void exercicioResolvido() {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

//		System.out.print("Enter full file path: ");
//		String path = sc.nextLine();
		String path = "in.csv";

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			List<Product> list = new ArrayList<>();

			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Product(fields[0], Double.parseDouble(fields[1])));
				line = br.readLine();
			}

			double avg = list.stream().map(p -> p.getPrice()).reduce(0.0, (x, y) -> x + y) / list.size();

			System.out.println("Average price: " + String.format("%.2f", avg));

			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());

			List<String> names = list.stream().filter(p -> p.getPrice() < avg).map(p -> p.getName())
					.sorted(comp.reversed()).collect(Collectors.toList());

			names.forEach(System.out::println);
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		sc.close();
	}

	public static void basicPipeline() {
		List<Integer> list = Arrays.asList(3, 4, 5, 10, 7);
		Stream<Integer> st1 = list.stream().map(x -> x * 10);
		System.out.println(Arrays.toString(st1.toArray()));

		// somando todos os elementos da lista -- o zero � o elemento neutro da soma
		// caso fosse uma multiplica��o, usariamos o 1 no lugar (pois ele � o neutro da
		// multiplica��o)
		int sum = list.stream().reduce(0, (x, y) -> x + y);
		System.out.println("Sum: " + sum);

		/*
		 * Pegou a primeira lista e criou um pipeline colocar na nova lista apenas os
		 * n�meros pares multiplicados por 10
		 */
		List<Integer> newList = list.stream().filter(x -> x % 2 == 0).map(x -> x * 10).collect(Collectors.toList());

		System.out.println(Arrays.toString(newList.toArray()));
	}

	public static void basicStream() {
		/*
		 * Stream s�o sequencias de eleventos advindas de uma fonte de dados que oferece
		 * suporte a "opera��es agregadas" A fonte de dados pode ser: cole��o, array,
		 * fun��o intera��o, recurso de entrada e saida
		 * 
		 * A Stream � uma solu��o para processar sequencias de dados de forma:
		 * Declarativa (itera��o interna: escondida do programador) Parallel- friendly
		 * (imut�vel -> thread safe) Sem efeitos colaterais Sob demanda (laze
		 * evaluation)
		 * 
		 * A Stream � acessada sequancialmente Ela s� � usada uma �nica vez
		 * 
		 * Pipeline: opera��es em streams retornam novas streams. Ent�o � poss�vel criar
		 * uma cadeia de opara��es (fluxo de processamento)
		 * 
		 * 
		 * 
		 * Opera��es intermedi�rias e terminais O Pipeline � composto por zero ou mais
		 * opera��es intermedi�rias e uma terminal
		 * 
		 * Opera��es intermedi�rias: Produz uma nova Stream (encadeamento) S� executa
		 * quando uma opera��o terminal � invocada (lazy evaluation)
		 * 
		 * Opera��o terminal: Produz um objeto n�o-stream (cole��o ou outro) Determina o
		 * fim do processamento da Stream
		 * 
		 * 
		 * Lista opera��es intermedi�rias: filter, map, flatmap, peek, distinct, sorted,
		 * skip, limit(*) o limit para o processamento quando a condi��o � atingida
		 * 
		 * Lista de opera��es terminais: forEach, forEachOrdered, toArray, reduce,
		 * collect, min, max, count, anyMatch(*) -- quando a condi��o � satisfeita o
		 * processamento para allMatch(*) -- quando a condi��o � satisfeita o
		 * processamento para noneMatch(*) -- quando a condi��o � satisfeita o
		 * processamento para findFirst(*) -- quando a condi��o � satisfeita o
		 * processamento para findAny(*) -- quando a condi��o � satisfeita o
		 * processamento para
		 * 
		 */

		// Criando stream a partir de uma cole��o (lista)
		List<Integer> list = Arrays.asList(3, 4, 5, 10, 7);
		Stream<Integer> st1 = list.stream().map(x -> x * 10);
		System.out.println(Arrays.toString(st1.toArray()));

//		Criando o stream diretamente com o Stream.of
		Stream<String> st2 = Stream.of("Maria", "Alex", "Bob");
		System.out.println(Arrays.toString(st2.toArray()));

//		Criando uma stream a partir do iterate e usando o limit
		Stream<Integer> st3 = Stream.iterate(0, x -> x + 2);
		System.out.println(Arrays.toString(st3.limit(10).toArray()));

//		Criando sequencia de fibonacci com stream
		Stream<Long> st4 = Stream.iterate(new Long[] { 0L, 1L }, p -> new Long[] { p[1], p[0] + p[1] }).map(p -> p[0]);
		System.out.println(Arrays.toString(st4.limit(10).toArray()));
	}

}
