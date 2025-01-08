package main;

import java.util.Scanner;

public class Main {
    private Scanner s = new Scanner(System.in);

    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libros por Título
                    2 - Lista de Libros Registrados (BBDD)
                    3 - Lista de Autores Registrados (BBDD)
                    4 - Lista de Autores vivos en un año (BBDD)
                    5 - Lista de Libros por idioma (BBDD)
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = s.nextInt();
            s.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("Seleccionaste opción "+opcion);
                    break;
                case 2:
                    System.out.println("Seleccionaste opción "+opcion);
                    break;
                case 3:
                    System.out.println("Seleccionaste opción "+opcion);
                    break;
                case 4:
                    System.out.println("Seleccionaste opción "+opcion);
                    break;
                case 5:
                    System.out.println("Seleccionaste opción "+opcion);
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }
}
