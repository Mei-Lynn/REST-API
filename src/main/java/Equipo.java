
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Equipo {

    private ArrayList<Personaje> equipo;
    private int espaciosRestantes;

    public Equipo() {
        equipo = new ArrayList<>(4);
        espaciosRestantes = 4;
    }

    public void guardar(Personaje pj, Scanner sc) {
        if (equipo.contains(pj)) {
            System.out.println("Este personaje ya existe en el equipo");
        } else {
            if (espaciosRestantes > 0) {
                equipo.add(pj);
            } else {
                System.out.println("Tu equipo está lleno, escoge a que personaje reemplazar...");
                eliminar(sc);
                equipo.add(pj);
            }
            espaciosRestantes--;
        }
    }

    public void eliminar(Scanner sc) {
        System.out.println(this);
        System.out.print("--> ");
        int input = -1;

        while (input < 1 || input > espaciosRestantes) {
            try {
                input = sc.nextInt();

                if (input < 1 || input > espaciosRestantes) {
                    System.out.print("Introduce un numero dentro del rango [1-"+espaciosRestantes+"] -> ");
                }
            } catch (InputMismatchException e) {
                input = -1;
                System.out.print("Introduce un numero dentro del rango [1-"+espaciosRestantes+"] -> ");
            }
            sc.nextLine();
        }
        equipo.remove(input - 1);
        espaciosRestantes++;
    }

    public void reset() {
        equipo = new ArrayList<>(4);
        espaciosRestantes = 4;
    }

    @Override
    public String toString() {
        StringBuilder rt = new StringBuilder("");
        int ctr = 1;

        for (Personaje pj : equipo) {
            rt.append(ctr++).append(" - ").append(pj.getName()).append("\n");
        }
        for (int i = 0; i < espaciosRestantes; i++) {
            rt.append(ctr++).append(" - ").append("vacio").append("\n");
        }
        rt.deleteCharAt(rt.length() - 1); //Elimina el ultimo salto de linea
        return rt.toString();
    }

    public int getEspaciosRestantes() {
        return espaciosRestantes;
    }

    public String[] getIds() {
        String[] rt = new String[4];
        int ctr = 0;
        for (Personaje pj : equipo) {
            rt[ctr++] = pj.getId();
        }
        for (int i = 0; i < espaciosRestantes; i++) {
            rt[ctr++] = null;
        }
        return rt;
    }
}
