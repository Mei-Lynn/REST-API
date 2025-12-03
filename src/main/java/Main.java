import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

	public static Map<String, Integer> calcularAscension(Personaje pj, int asc) {

		String[] niveles = { "level_20", "level_40", "level_50", "level_60", "level_70", "level_80" };

		Map<String, Integer> total = new HashMap<>();

		if (pj == null)
			return total;
		Map<String, List<AscensionItem>> materias = pj.getAscension_materials();

		if (materias == null || materias.isEmpty()) {
			return total;
		}

		if (asc < 0)
			asc = 0;
		if (asc > niveles.length)
			asc = niveles.length;

		for (int i = 0; i < asc; i++) {
			String key = niveles[i];
			List<AscensionItem> lista = materias.get(key);

			if (lista == null)
				continue;

			// Sumar materiales al mapa total
			for (AscensionItem it : lista) {
				if (it == null || it.getName() == null)
					continue;
				total.merge(it.getName(), it.getValue(), Integer::sum);
			}
		}

		return total;
	}

	public static Sinergia obtenerSinergia(String e1, String e2) {

		String a = e1.toLowerCase();
		String b = e2.toLowerCase();

		if ((a.equals("pyro") && b.equals("hydro")) || (b.equals("pyro") && a.equals("hydro"))) {
			return new Sinergia("Vaporizado", "Multiplica el daño, una de las mejores reacciones ofensivas.",
					"EXCELENTE",
					"Añade un personaje Anemo para agrupar (Kazuha, Sucrose) o un healer/shielder para supervivencia.");
		}

		if ((a.equals("pyro") && b.equals("cryo")) || (b.equals("pyro") && a.equals("cryo"))) {
			return new Sinergia("Derretido", "Daño muy elevado al combinar Pyro y Cryo.", "EXCELENTE",
					"Añade un Anemo para bajar resistencias o un Hydro para control y freeze ocasional.");
		}

		if ((a.equals("hydro") && b.equals("electro")) || (b.equals("hydro") && a.equals("electro"))) {
			return new Sinergia("Electrocargado", "Daño continuado a enemigos mojados.", "BUENA",
					"Añade un personaje Dendro para activar Sobreflorecimiento/Aceleración.");
		}

		if ((a.equals("pyro") && b.equals("electro")) || (b.equals("pyro") && a.equals("electro"))) {
			return new Sinergia("Sobrecarga", "Explosión y daño en área, útil contra enemigos duros.", "BUENA",
					"Añade un escudo (Zhongli, Layla) o un Anemo que agrupe para controlar el retroceso.");
		}

		if ((a.equals("dendro") && b.equals("electro")) || (b.equals("dendro") && a.equals("electro"))) {
			return new Sinergia("Aceleración/Propagación", "Meta actual: daño altísimo basado en Dendro.", "EXCELENTE",
					"Añade un Hydro para generar Sobreflorecimiento o un healer de apoyo.");
		}

		if ((a.equals("geo") && b.equals("anemo")) || (a.equals("anemo") && b.equals("geo"))) {
			return new Sinergia("Sin reacción", "Geo + Anemo no generan reacción elemental.", "NEUTRA",
					"Añade un personaje reactivo (Pyro, Hydro, Electro o Dendro) para mejorar daño.");
		}

		return new Sinergia("Ninguna", "Estos elementos no generan reacción elemental entre sí.", "NEUTRA",
				"Añade un elemento reactivo (Pyro, Hydro, Electro o Dendro) para crear combinaciones fuertes.");
	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		GenshinAPI api = new GenshinAPI();

		while (true) {

			System.out.println("\nMenú:");
			System.out.println("1. Buscar personaje");
			System.out.println("2. Recomendar armas para personaje");
			System.out.println("3. Calcular materiales de ascensión de un personaje");
			System.out.println("4. Analizar visiones (elementos)");
			System.out.println("5. Guardar equipo de ejemplo");
			System.out.println("6. Listar equipos guardados");
			System.out.println("7. Salir");
			System.out.print("Elige opción: ");
			String opt = sc.nextLine().trim();

			switch (opt) {

			case "1":
				System.out.print("Introduce el nombre del personaje: ");
				String nombre = sc.nextLine().trim().toLowerCase().replace(" ", "-");

				try {
					Personaje pj = api.getPersonaje(nombre);

					System.out.println("\n=== PERSONAJE ENCONTRADO ===");
					System.out.println("Nombre: " + pj.getName());
					System.out.println("Visión: " + pj.getVision());
					System.out.println("Rareza: " + pj.getRarity() + "★");

					System.out.print("\n¿Quieres ver toda su información? (s/n): ");
					String extra = sc.nextLine().trim().toLowerCase();

					if (extra.equals("s") || extra.equals("si")) {
						System.out.println("\n--- INFORMACIÓN COMPLETA ---");
						System.out.println("Región: " + pj.getNation());
						System.out.println("Descripción: " + pj.getDescription());

						System.out.println("\nTalentos:");
						for (Talento t : pj.getSkillTalents()) {
							System.out.println("- " + t.getName());
							System.out.println("  " + t.getDescription());
						}
					}

				} catch (Exception e) {
					System.out.println("ERROR: " + e.getMessage());
				}
				break;

			case "2":
				try {
					System.out.print("Introduce el nombre del personaje: ");
					String nombrePj = sc.nextLine().trim().toLowerCase().replace(" ", "-");

					Personaje pj = api.getPersonaje(nombrePj);

					System.out.println("\nBuscando armas adecuadas para: " + pj.getName());
					System.out.println("Tipo de arma: " + pj.getWeapon());

					System.out.print("\n¿Quieres ver armas de 4, 5 o ambas estrellas? (4/5/a): ");
					String r = sc.nextLine().trim().toLowerCase();

					int filtro = 0;
					if (r.equals("4"))
						filtro = 4;
					else if (r.equals("5"))
						filtro = 5;

					List<String> listaIds = api.getListaArmas();
					List<Arma> compatibles = new ArrayList<>();

					for (String id : listaIds) {
						Arma arma = api.getArma(id);

						if (arma.getType().equalsIgnoreCase(pj.getWeapon())) {

							if (filtro == 4 && arma.getRarity() != 4)
								continue;
							if (filtro == 5 && arma.getRarity() != 5)
								continue;

							compatibles.add(arma);
						}
					}

					if (compatibles.isEmpty()) {
						System.out.println("No hay armas compatibles para ese filtro.");
						break;
					}

					System.out.println("\n=== ARMAS DISPONIBLES ===");
					for (int i = 0; i < compatibles.size(); i++) {
						System.out.println((i + 1) + ". " + compatibles.get(i).getName());
					}

					System.out.print("\n¿Quieres ver la información completa de alguna? (s/n): ");
					String extra = sc.nextLine().trim().toLowerCase();

					if (!extra.equals("s"))
						break;

					System.out.print("Escribe los números de las armas separadas por comas: ");
					String seleccion = sc.nextLine().trim();

					String[] partes = seleccion.split(",");

					System.out.println("\n=== INFORMACIÓN DE ARMAS SELECCIONADAS ===");

					for (String p : partes) {
						try {
							int index = Integer.parseInt(p.trim()) - 1;

							if (index < 0 || index >= compatibles.size()) {
								System.out.println("Número inválido: " + p);
								continue;
							}

							Arma a = compatibles.get(index);

							System.out.println("\n--- " + a.getName() + " (" + a.getRarity() + "★) ---");
							System.out.println("Tipo: " + a.getType());
							System.out.println("Substat: " + a.getSubStat());
							System.out.println("Pasiva: " + a.getPassiveName());
							System.out.println("Descripción: " + a.getPassiveDesc());

						} catch (NumberFormatException nfe) {
							System.out.println("Entrada no válida: " + p);
						}
					}

				} catch (Exception e) {
					System.out.println("ERROR: " + e.getMessage());
				}
				break;

			case "3":
				try {
					// Pedir personaje al usuario
					System.out.print("Introduce el personaje: ");
					String nombrePj = sc.nextLine().trim().toLowerCase().replace(" ", "-");

					// Descargar datos del PJ
					Personaje pj = api.getPersonaje(nombrePj);

					System.out.println("\nAscensión del personaje: " + pj.getName());
					System.out.println("Niveles disponibles (0 = sin ascender): 0 1 2 3 4 5 6");
					System.out.print("¿Hasta qué ascensión quieres calcular? (0-6): ");
					int asc = Integer.parseInt(sc.nextLine().trim());

					if (asc < 0 || asc > 6) {
						System.out.println("Ascensión inválida. Debe ser entre 0 y 6.");
						break;
					}

					// Calcular materiales
					Map<String, Integer> res = calcularAscension(pj, asc);

					if (res.isEmpty()) {
						System.out.println(
								"No se han encontrado materiales (el personaje puede no tener 'ascension_materials' en la API).");
						break;
					}

					System.out.println("\n=== MATERIALES NECESARIOS (0 → " + asc + ") ===");

					// Ordenar el mapa automáticamente por nombre del material
					Map<String, Integer> ordenado = new TreeMap<>(res);

					for (String k : ordenado.keySet()) {
						System.out.println(k + ": " + ordenado.get(k));
					}

				} catch (NumberFormatException nfe) {
					System.out.println("Entrada inválida. Introduce un número entre 0 y 6.");
				} catch (Exception e) {
					System.out.println("ERROR: " + e.getMessage());
				}
				break;
			case "4":
				try {
					System.out.print("Introduce el primer personaje: ");
					String p1id = sc.nextLine().trim().toLowerCase().replace(" ", "-");

					System.out.print("Introduce el segundo personaje: ");
					String p2id = sc.nextLine().trim().toLowerCase().replace(" ", "-");

					Personaje pj1 = api.getPersonaje(p1id);
					Personaje pj2 = api.getPersonaje(p2id);

					String e1 = pj1.getVision();
					String e2 = pj2.getVision();

					System.out.println("\n" + pj1.getName() + " (" + e1 + ") + " + pj2.getName() + " (" + e2 + ")");

					Sinergia sin = obtenerSinergia(e1, e2);

					System.out.println("\nReacción elemental: " + sin.getNombre());
					System.out.println("Descripción: " + sin.getDescripcion());
					System.out.println("Valoración: " + sin.getCalidad());
					System.out.println("Recomendación: " + sin.getRecomendacion());

				} catch (Exception e) {
					System.out.println("ERROR: " + e.getMessage());
				}
				break;

			case "5":
			case "6":
				System.out.println("Funcionalidad en desarrollo.");
				break;

			case "7":
				System.out.println("¡Hasta luego!");
				return;

			default:
				System.out.println("Opción no válida.");
			}
		}
	}
}
