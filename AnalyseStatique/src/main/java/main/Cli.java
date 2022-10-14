package main;

import java.io.IOException;
import java.util.Scanner;

public class Cli {
	private static Scanner sc;

	private static void displayFunctions() {
		StringBuilder listFeatures = new StringBuilder();
		listFeatures.append("0.        Analyser un projet\n");
		listFeatures.append("1.        Nombre de classes de l’application.\n");
		listFeatures.append("2.        Nombre de lignes de code de l’application.\n");
		listFeatures.append("3.        Nombre total de méthodes de l’application.\n");
		listFeatures.append("4.        Nombre total de packages de l’application.\n");
		listFeatures.append("5.        Nombre moyen de méthodes par classe.\n");
		listFeatures.append("6.        Nombre moyen de lignes de code par méthode.\n");
		listFeatures.append("7.        Nombre moyen d’attributs par classe.\n");
		listFeatures.append("8.        Les 10% des classes qui possèdent le plus grand nombre de méthodes.\n");
		listFeatures.append("9.        Les 10% des classes qui possèdent le plus grand nombre d’attributs.\n");
		listFeatures.append("10.       Les classes qui font partie en même temps des deux catégories précédentes.\n");
		listFeatures.append("11.       Les classes qui possèdent plus de X méthodes (la valeur de X est à saisir).\n");
		listFeatures.append(
				"12.       Les 10% des méthodes qui possèdent le plus grand nombre de lignes de code (par classe).\n");
		listFeatures.append(
				"13.       Le nombre maximal de paramètres par rapport à toutes les méthodes de l’application.\n");
		listFeatures.append("14.       Afficher les informations du graphe d'appel .\n");
		listFeatures.append("15.       Afficher le graphe d'appel .\n");
		listFeatures.append("menu.     Afficher le menu de nouveau.\n");
		listFeatures.append("quitter.  Quitter l’application.\n");
		System.out.print(listFeatures);
	}

	private static void chooseAFeatures(Analyse analyse) throws IOException {
		displayFunctions();
		String choice = "";
		while (!choice.equals("quitter")) {
			System.out.print("\nCHOISIR UNE OPTION : ");
			choice = sc.nextLine();
			switch (choice.trim()) {
			case "0":
				System.out.print("Saisissez le repertoire vers le nouveau projet à analyser : ");
				String projectPath = sc.nextLine();
				String projectSourcePath = projectPath + "\\src";
				Analyse newAnalyse = new Analyse();
				newAnalyse.staticAnalysis(projectSourcePath);
				chooseAFeatures(newAnalyse);
				break;
			case "1":
				System.out.println("Le nombre de classes de l'application : " + analyse.getJavaClassCounter());
				break;
			case "2":
				System.out.println("Le nombre de lignes de code de l’application : " + analyse.getSommeLineCounter());
				break;

			case "3":
				System.out.println("Le nombre total de méthodes de l’application : " + analyse.getJavaMethodCounter());
				break;
			case "4":
				System.out.println("Le nombre total de packages de l’application : " + analyse.getNbPackages());
				break;
			case "5":
				System.out.println("Le nombre moyen de méthodes par classe : " + analyse.AvgMethodsByClass());
				break;
			case "6":
				System.out
						.println("Le nombre moyen de lignes de code par méthode : " + analyse.printAvgLinesByMethod());
				break;
			case "7":
				System.out.println("Le nombre moyen d’attributs par classe : " + analyse.AvgFields());
				break;
			case "8":
				System.out.println("Les 10% des classes qui possèdent le plus grand nombre de méthodes : ");
				Analyse.afficherMap(analyse.get10PercentsClassesByMethods());
				break;
			case "9":
				System.out.println("Les 10% des classes qui possèdent le plus grand nombre d’attributs : ");
				Analyse.afficherMap(analyse.get10PercentsClassesByFields());
				break;
			case "10":
				System.out.println(
						"Les classes qui possèdent à la fois le plus grand nombre de méthodes et d'attributs : ");
				Analyse.afficherMap(analyse.get10PercentsClassesByFieldsAndMethods(
						analyse.get10PercentsClassesByFields(), analyse.get10PercentsClassesByMethods()));
				break;
			case "11":
				System.out.print("Les classes qui possèdent plus de X méthodes : ");
				System.out.println("Saisissez le nombre de methodes souhaiter !");
				int x = sc.nextInt();
				Analyse.afficherMap(analyse.getClassesBybMethodsGreaterThanX(x));
				break;
            case "12":
                System.out.println("Les 10% des méthodes qui possèdent le plus grand nombre de lignes de code (par classe) : ");
                Analyse.afficherMethodMap(analyse.get10PrecentsOfMethodsByLine());
                break;
            case "13":
                System.out.println("le nombre maximal de paramètres par rapport à toutes les méthodes de l’application :");
                Analyse.afficherMethodMap(analyse.getMaxParameters());
                break;
            case "14":
            	System.out.println("CallGraph : ");
            	Analyse.displayTheCAllGraph(analyse.getMapInvocationMethodsByclass());
            case "15":
            	new Analyse().dotToPng("graph.dot");
			case "menu":
				displayFunctions();
				break;
			case "quitter":
				sc.close();
				break;
			default:
				System.err.println("Choix incorrect ... Veuillez recommencer !");
				break;
			}
		}
	}

	private static void openCLI() throws IOException {
		System.out.println("Saisissez le repertoire vers le projet à analyser : ");
		String projectPath = sc.nextLine();
		String projectSourcePath = projectPath + "\\src";

		Analyse analyse = new Analyse();
		analyse.staticAnalysis(projectSourcePath);

		chooseAFeatures(analyse);
	}

	public static void main(String[] args) throws IOException {
		System.out.println(" WELCOME, cette CLI va vous aidez a executer mon code :), Let's get started !");
		sc = new Scanner(System.in);
		openCLI();
		
	}

}
