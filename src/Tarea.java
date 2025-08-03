public class Tarea {
    private String descripcion;
    private boolean completada;

    public Tarea(String descripcion){
        this.descripcion = descripcion;
        this.completada = false;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isCompletada(){
        return completada;
    }

    public void setCompletada(boolean completada){
        this.completada = completada;
    }

    public String toString(){
        String estado;
        if(completada){
            estado = "[X] ";
        } else {
            estado = "[ ] ";
        }
        return estado + descripcion;
    }

    // Método para guardar la tarea en una línea de archivo
    public String formatoArchivo(){
        String estado;
        if (completada == true) {
            estado = "[X]";
        } else {
            estado = "[ ]";
        }
        return estado + " | " + descripcion;
    }


}
