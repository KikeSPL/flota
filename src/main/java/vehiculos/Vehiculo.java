/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vehiculos;

import java.time.LocalDate;
import dni.DNI;

/**
 * CLASE PARA CREAR UN VEHICULO INCLUYENDO LOS METODOS VIAJAR, REPOSTAR, ETC...
 *
 * @author Enrique Sainz-Pardo López
 */
public class Vehiculo {

    // Atributo estatico: El precio del combustible es común a todos los vehiculos). 
    private static double precioCombustible;

    // Atributos de instancia
    private String marca;
    private String modelo;
    private String matricula;
    private LocalDate fechaMatriculacion;
    private String nombrePropietario;
    private DNI dniPropietario;
    private double capacidadDeposito;
    private double cantidadCombustible;
    private double consumoMedio;
    private double kilometrosTotales;
    private double dineroGastado;

    /**
     * Constructor.
     *
     * @param marca Marca del coche
     * @param modelo Modelo del coche
     * @param matricula Matricula del coche
     * @param fechaMatriculacion Fecha de matriculacion
     * @param nombrePropietario Nombre del propietario
     * @param dniPropietario DNI del propietario
     * @param capacidadDeposito Capacidad del deposito del coche
     * @param cantidadCombustible Cantidad de combustible gastada
     * @param consumoMedio Consumo medio de combustible cada 100km
     * @param kilometrosTotales Kilometros totales recorridos
     * @param dineroGastado Dinero gastado en combustible para viajar
     */
    public Vehiculo(String marca, String modelo, String matricula, LocalDate fechaMatriculacion, 
            String nombrePropietario, DNI dniPropietario, double capacidadDeposito, double cantidadCombustible, double consumoMedio) {
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.fechaMatriculacion = fechaMatriculacion;
        this.nombrePropietario = nombrePropietario;
        this.dniPropietario = dniPropietario;
        this.capacidadDeposito = capacidadDeposito;
        this.cantidadCombustible = cantidadCombustible;
        this.consumoMedio = consumoMedio;
        this.kilometrosTotales = 0;
        this.dineroGastado = 0;
    }

    /**
     * Metodo para mostar los datos identificativos del vehiculo.
     *
     * @return Devuelve los datos identificativos del vehiculo.
     */
    public String mostrarDatosIdentificativos() {
        return String.format("Marca: %s, Modelo: %s, Matrícula: %s, Fecha Matriculacion: %s",
                marca, modelo, matricula, fechaMatriculacion.toString());
    }

    /**
     * Metodo para mostrar el estado actual del vehículo: kilómetros y
     * combustible.
     *
     * @return Devuelve el estado del vehiculo
     */
    public String mostrarEstadoVehiculo() {
        return String.format("Kilómetros: %.2f, Combustible: %.2f L",
                kilometrosTotales, cantidadCombustible);
    }

    /**
     * Metodo para viajar. Si no hay combustible suficiente, viaja los km que se
     * puedan.
     *
     * @return Devuelve los km que ha viajado el vehiculo con el combustible
     * repostado.
     */
    public String viajar(double km) {
        if (km <= 0) {
            return "Los kilómetros a viajar deben ser positivos.";
        }
        double litrosNecesarios = (consumoMedio * km) / 100.0;
        double kmRealizados;
        if (litrosNecesarios <= cantidadCombustible) {
            kmRealizados = km;
            cantidadCombustible -= litrosNecesarios;
        } else {
            kmRealizados = (cantidadCombustible * 100.0) / consumoMedio;
            cantidadCombustible = 0;
        }
        kilometrosTotales += kmRealizados;
                return String.format(
                "El vehículo %s ha viajado %.2f km. "
                + "Estado de combustible: %.2f litros. "
                + "Kilómetros totales: %.2f km",
                matricula, kmRealizados, cantidadCombustible, kilometrosTotales);
    }

    /**
     * Metodo para repostar la cantidad indicada de litros, sin sobrepasar la
     * capacidad del depósito.
     *
     * @return Informa de la cantidad repostada e importe.
     */
    public String repostar(double litros) {
        if (litros <= 0) {
            return "No se puede repostar una cantidad negativa o cero.";
        }
        double espacioDisponible = capacidadDeposito - cantidadCombustible;
        double litrosEfectivos = Math.min(litros, espacioDisponible);
        cantidadCombustible += litrosEfectivos;
        double importe = litrosEfectivos * precioCombustible;
        dineroGastado += importe;
        return String.format("El vehículo %s ha repostado %.2f litros por %.2f €.",
                matricula, litrosEfectivos, importe, cantidadCombustible);
    }

    /**
     * Llena el depósito hasta su capacidad.
     *
     * @return Informa que el deposito se ha llenado.
     */
    public String llenar() {
        double espacioDisponible = capacidadDeposito - cantidadCombustible;
        if (espacioDisponible <= 0) {
            return "El depósito ya está lleno.";
        }
        return repostar(espacioDisponible);
    }

    /**
     * Actualiza el precio del combustible (método estático).
     *
     * @return Informa del nuevo precio del combustible.
     */
    public static String actualizarPrecio(double nuevoPrecio) {
        if (nuevoPrecio <= 0) {
            return "El precio debe ser positivo.";
        }
        precioCombustible = nuevoPrecio;
        return String.format("Precio actualizado a %.2f €/litro.", nuevoPrecio);
    }

    /**
     * Devuelve el propietario del vehículo (nombre y NIF).
     *
     * @return Devuelve los datos del propietario.
     */
    public String mostrarPropietario() {
        return String.format("Propietario: %s, DNI: %s",
                nombrePropietario, dniPropietario.obtenerNIF());
    }

    /**
     * @return Devuelve la matrícula del vehículo.
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * @return Devuelve los kilómetros totales recorridos.
     */
    public double getKilometrosTotales() {
        return kilometrosTotales;
    }
}
