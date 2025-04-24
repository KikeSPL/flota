/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vehiculos;

import dni.DNI;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Clase para representar una flota de vehículos usando TreeSet.
 *
 * @author Enrique Sainz-Pardo Lopez
 */
public class Flota {

    private TreeSet<Vehiculo> flota;
    private Vehiculo vehiculoActual;
    private static final Comparator<Vehiculo> COMPARADOR_MATRICULA = new Comparator<Vehiculo>() {
        @Override
        public int compare(Vehiculo v1, Vehiculo v2) {
            // Primero comparamos las letras (últimos 3 caracteres)
            String letras1 = v1.getMatricula().substring(4);
            String letras2 = v2.getMatricula().substring(4);
            int comparacionLetras = letras1.compareTo(letras2);

            if (comparacionLetras != 0) {
                return comparacionLetras;
            }

            // Si las letras son iguales, comparamos los números (primeros 4 caracteres)
            int num1 = Integer.parseInt(v1.getMatricula().substring(0, 4));
            int num2 = Integer.parseInt(v2.getMatricula().substring(0, 4));
            return Integer.compare(num1, num2);
        }
    };

    public Flota() {
        flota = new TreeSet<>(COMPARADOR_MATRICULA);
        vehiculoActual = null;
    }

    /**
     * Añade un nuevo vehículo a la flota.
     *
     * @return 0 si éxito, -2 si matrícula duplicada
     */
    public int insertarVehiculo(String marca, String modelo, String matricula,
            LocalDate fechaMatriculacion, String nombrePropietario, DNI dniPropietario,
            double capacidadDeposito, double consumoMedio, double kmInicial) {

        String matriculaUpper = matricula.toUpperCase();
        Vehiculo nuevo = new Vehiculo(marca, modelo, matriculaUpper, fechaMatriculacion,
                nombrePropietario, dniPropietario, capacidadDeposito, 0, consumoMedio);

        if (flota.add(nuevo)) {
            vehiculoActual = nuevo;  // Selecciona automáticamente el nuevo vehículo
            return 0;
        }
        return -2;  // Matrícula duplicada
    }

    /**
     * Elimina un vehículo por matrícula
     *
     * @return 0 si éxito, -1 si no existe
     */
    public int eliminarVehiculo(String matricula) {
        String matUpper = matricula.toUpperCase();
        Vehiculo dummy = new Vehiculo("", "", matUpper, LocalDate.now(), "", new DNI(), 0, 0, 0);

        if (flota.remove(dummy)) {
            // Si eliminamos el vehículo actual, lo deseleccionamos
            if (vehiculoActual != null && vehiculoActual.getMatricula().equals(matUpper)) {
                vehiculoActual = null;
            }
            return 0;
        }
        return -1;
    }

    /**
     * Lista todos los vehículos ordenados por matrícula
     */
    public String listaVehiculos() {
        if (flota.isEmpty()) {
            return "\nNo hay vehículos en la flota.";
        }

        StringBuilder sb = new StringBuilder();
        Iterator<Vehiculo> it = flota.iterator();
        int contador = 1;

        while (it.hasNext()) {
            Vehiculo v = it.next();
            sb.append(String.format("Vehículo %d:\n", contador++))
                    .append(v.mostrarDatosIdentificativos())
                    .append("\nKilómetros totales: ")
                    .append(String.format("%.2f", v.getKilometrosTotales()))
                    .append("\n---------------------------\n");
        }
        return sb.toString();
    }

    /**
     * Selecciona un vehículo por matrícula
     *
     * @return 0 si éxito, -1 si no existe
     */
    public int seleccionarVehiculo(String matricula) {
        String matUpper = matricula.toUpperCase();
        Iterator<Vehiculo> it = flota.iterator();

        while (it.hasNext()) {
            Vehiculo v = it.next();
            if (v.getMatricula().equals(matUpper)) {
                vehiculoActual = v;
                return 0;
            }
        }
        return -1;
    }

    // Métodos de operación con el vehículo actual
    private boolean hayVehiculoSeleccionado() {
        return vehiculoActual != null;
    }

    public String verDatosIdentificativos() {
        return hayVehiculoSeleccionado()
                ? vehiculoActual.mostrarDatosIdentificativos()
                : "No hay vehículo seleccionado";
    }

    public String verEstadoVehiculo() {
        return hayVehiculoSeleccionado()
                ? vehiculoActual.mostrarEstadoVehiculo()
                : "No hay vehículo seleccionado";
    }

    public String viajar(double km) {
        return hayVehiculoSeleccionado()
                ? vehiculoActual.viajar(km)
                : "No hay vehículo seleccionado";
    }

    public String repostar(double litros) {
        return hayVehiculoSeleccionado()
                ? vehiculoActual.repostar(litros)
                : "No hay vehículo seleccionado";
    }

    public String llenar() {
        return hayVehiculoSeleccionado()
                ? vehiculoActual.llenar()
                : "No hay vehículo seleccionado";
    }

    public String mostrarPropietario() {
        return hayVehiculoSeleccionado()
                ? vehiculoActual.mostrarPropietario()
                : "No hay vehículo seleccionado";
    }

    public String actualizarPrecioCombustible(double nuevoPrecio) {
        return Vehiculo.actualizarPrecio(nuevoPrecio);
    }

    public int getNumVehiculos() {
        return flota.size();
    }
}
