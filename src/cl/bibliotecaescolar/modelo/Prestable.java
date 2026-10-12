package cl.bibliotecaescolar.modelo;

public interface Prestable {

    boolean estaDisponible();
    void prestar();
    void devolver();
}