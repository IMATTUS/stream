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

		// somando todos os elementos da lista -- o zero é o elemento neutro da soma
		// caso fosse uma multiplicação, usariamos o 1 no lugar (pois ele é o neutro da
		// multiplicação)
		int sum = list.stream().reduce(0, (x, y) -> x + y);
		System.out.println("Sum: " + sum);

		/*
		 * Pegou a primeira lista e criou um pipeline colocar na nova lista apenas os
		 * números pares multiplicados por 10
		 */
		List<Integer> newList = list.stream().filter(x -> x % 2 == 0).map(x -> x * 10).collect(Collectors.toList());

		System.out.println(Arrays.toString(newList.toArray()));
	}

	public static void basicStream() {
		/*
		 * Stream são sequencias de eleventos advindas de uma fonte de dados que oferece
		 * suporte a "operações agregadas" A fonte de dados pode ser: coleção, array,
		 * função interação, recurso de entrada e saida
		 * 
		 * A Stream é uma solução para processar sequencias de dados de forma:
		 * Declarativa (iteração interna: escondida do programador) Parallel- friendly
		 * (imutável -> thread safe) Sem efeitos colaterais Sob demanda (laze
		 * evaluation)
		 * 
		 * A Stream é acessada sequancialmente Ela só é usada uma única vez
		 * 
		 * Pipeline: operações em streams retornam novas streams. Então é possível criar
		 * uma cadeia de oparações (fluxo de processamento)
		 * 
		 * 
		 * 
		 * Operações intermediárias e terminais O Pipeline é composto por zero ou mais
		 * operações intermediárias e uma terminal
		 * 
		 * Operações intermediárias: Produz uma nova Stream (encadeamento) Só executa
		 * quando uma operação terminal é invocada (lazy evaluation)
		 * 
		 * Operação terminal: Produz um objeto não-stream (coleção ou outro) Determina o
		 * fim do processamento da Stream
		 * 
		 * 
		 * Lista operações intermediárias: filter, map, flatmap, peek, distinct, sorted,
		 * skip, limit(*) o limit para o processamento quando a condição é atingida
		 * 
		 * Lista de operações terminais: forEach, forEachOrdered, toArray, reduce,
		 * collect, min, max, count, anyMatch(*) -- quando a condição é satisfeita o
		 * processamento para allMatch(*) -- quando a condição é satisfeita o
		 * processamento para noneMatch(*) -- quando a condição é satisfeita o
		 * processamento para findFirst(*) -- quando a condição é satisfeita o
		 * processamento para findAny(*) -- quando a condição é satisfeita o
		 * processamento para
		 * 
		 */

		// Criando stream a partir de uma coleção (lista)
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
