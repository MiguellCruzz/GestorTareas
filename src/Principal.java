import java.io.*;
import java.util.*;

public class Principal {

    private static ArrayList<Tarea> tareas = new ArrayList<>();

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        boolean seguir = true;

        cargarTareasDesdeArchivo();

        do {
            int opcion;
            menu();
            opcion = entrada.nextInt();
            entrada.nextLine(); // Para limpiar el buffer y poder leer texto después.
        
            switch(opcion) {

                case 1: 
                mostrarTareas();
                break;

                case 2:
                añadirTarea(entrada);
                break;

                case 3:
                completarTarea(entrada);
                break;

                case 4: 
                desmarcarTareaCompletada(entrada);
                break;

                case 5: 
                eliminarTarea(entrada);
                break;

                case 6:
                System.out.println("Finalizando el programa...");
                seguir = false;
                break;

                default:
                System.out.println("Opción no válida.");
                break;
            }
        
        } while (seguir);

        entrada.close();

    }

    public static void menu(){
        System.out.println("\n--- GESTOR DE TAREAS ---");
        System.out.println("1. Ver tareas.");
        System.out.println("2. Añadir tareas.");
        System.out.println("3. Marcar tarea como completada.");
        System.out.println("4. Desmarcar tarea completada.");
        System.out.println("5. Eliminar tarea.");
        System.out.println("6. Salir");
        System.out.print("Elige una opción: ");

    }

    public static void mostrarTareas(){
        if (tareas.isEmpty()){
            System.out.println("\nNo hay tareas.");
        } else {
            System.out.println("\nTareas:");
            for (int i = 0; i < tareas.size(); i++){
                System.out.println((i + 1)+ ". " +tareas.get(i));
            }
        }
    }

    public static void añadirTarea(Scanner entrada){
        System.out.print("\nEscribe la descripción de la nueva tarea: ");
        String descripcion = entrada.nextLine();
        Tarea nueva = new Tarea(descripcion);
        tareas.add(nueva);

        guardarTareasEnArchivo();
        
        System.out.println("Tarea añadida.");
    }

    public static void completarTarea(Scanner entrada){
        mostrarTareas();
        if (tareas.isEmpty()) return;

        System.out.print("\n¿Qué número de tarea quieres marcar como completada? ");
        int num = entrada.nextInt();
        entrada.nextLine(); // Para limpiar el buffer después de nextInt();
    
        if (num >= 1 && num <= tareas.size()){
            Tarea tarea = tareas.get(num - 1);

            if (tarea.isCompletada()) {
                System.out.println("Esa tarea ya está marcada como completada.");
            } else {
                tarea.setCompletada(true);
                System.out.println("Tarea marcada como completada.");

                guardarTareasEnArchivo();
            }
        } else {
            System.out.println("Número de tarea no válido.");
        }
    }

    public static void desmarcarTareaCompletada(Scanner entrada){
        mostrarTareas();
        if (tareas.isEmpty()) return;

        System.out.print("\n¿Qué número de tarea quieres desmarcar como completada? ");
        int num = entrada.nextInt();
        entrada.nextLine(); // Para limpiar el buffer después de nextInt();
    
        if (num >= 1 && num <= tareas.size()){
            Tarea tarea = tareas.get(num - 1);

            if (!tarea.isCompletada()) {
                System.out.println("Esa tarea no está marcada como completada.");
            } else {
                tarea.setCompletada(false);
                System.out.println("Tarea desmarcada como completada.");

                guardarTareasEnArchivo();
            }
        } else {
            System.out.println("Número de tarea no válido.");
        }
    }

    public static void eliminarTarea(Scanner entrada){
        mostrarTareas();
        if (tareas.isEmpty()) return;

        System.out.println("\n¿Qué número de tarea quieres eliminar? ");
        int num = entrada.nextInt();
        entrada.nextLine();

        if (num >= 1 && num <= tareas.size()){
            tareas.remove(num - 1);
            System.out.println("Tarea eliminada.");

            guardarTareasEnArchivo();

        } else {
            System.out.println("Número inválido.");
        }
    }

    public static void guardarTareasEnArchivo(){
        try {
            PrintWriter escritor = new PrintWriter(new FileWriter("tareas.txt"));

            for (Tarea t : tareas){
                escritor.println(t.formatoArchivo());
            }

            escritor.close();

        } catch (Exception e) {
            System.out.println("Error al guardar tareas: " + e.getMessage());
        }
    }

    public static void cargarTareasDesdeArchivo(){
        try {
            File archivo = new File("tareas.txt");

            if (archivo.exists()){
                Scanner lector = new Scanner(archivo);

                while(lector.hasNextLine()){
                    String linea = lector.nextLine();
                    String[] partes = linea.split("\\|", 2); // Dividimos en estado y descripción
                    
                    if (partes.length == 2) {
                        String estado = partes[0].trim(); // [X] ó [ ]
                        String descripcion = partes[1].trim();

                        Tarea tarea = new Tarea(descripcion);
                        if (estado.equals("[X]")) {
                            tarea.setCompletada(true);
                        }

                        tareas.add(tarea);

                        }
                    }
                
                    lector.close();
                
                }
            
        } catch (Exception e) {
            System.out.println("Error al cargar tareas: " +e.getMessage());
        }
    }

}