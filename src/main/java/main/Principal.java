/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package main;

import dni.DNI;
import java.time.LocalDate;
import java.util.Scanner;
import vehiculos.Flota;

/**
 * Clase principal que contiene el metodo main. Se encarga de mostar el menu por
 * pantalla, realizar validaciones de entrada y utiliza la clase Flota para
 * gestionar los vehiculos.
 *
 * @author Enrique Sainz-Pardo Lopez
 */
public class Principal {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Flota flota = new Flota();
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- Menú principal ---");
            System.out.println("1. Seleccionar Vehículo");
            System.out.println("2. Nuevo Vehículo");
            System.out.println("3. Ver datos identificativos");
            System.out.println("4. Ver estado del vehículo");
            System.out.println("5. Viajar");
            System.out.println("6. Repostar");
            System.out.println("7. Llenar depósito");
            System.out.println("8. Actualizar precio combustible");
            System.out.println("9. Mostrar propietario");
            System.out.println("10. Listar Vehículos");
            System.out.println("11. Eliminar Vehiculo");
            System.out.println("12. Salir");
            System.out.print("\nSeleccione una opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    //Seleccionar vehiculo
                    if (flota.getNumVehiculos() > 0) {
                        System.out.println(flota.listaVehiculos());
                        System.out.println("Introduzca la matricula del vehiculo: ");
                        String seleccionMatricula = sc.nextLine();
                        int seleccionado = flota.seleccionarVehiculo(seleccionMatricula);
                        if (seleccionado == 0) {
                            System.out.println("Vehiculo seleccionado correctamente: " + seleccionMatricula);
                        } else {
                            System.out.println("ERROR: Vehiculo no encontrado en la flota.");
                        }
                    } else {
                        System.out.println("ERROR: La flota está vacía.");
                    }
                    break;

                case "2":
                    //Nuevo vehiculo. Solicita datos y los valida. 
                    String marca,
                     modelo,
                     matricula;
                    LocalDate fechaMatricula;
                    String nombrePropietario;
                    DNI dniPropietario = new DNI();
                    double capacidad,
                     consumo,
                     kmIniciales;

                    //Marca
                    do {
                        System.out.println("Marca: ");
                        marca = sc.nextLine();
                    } while (marca.trim().isEmpty());

                    //Modelo
                    do {
                        System.out.println("Modelo: ");
                        modelo = sc.nextLine();
                    } while (modelo.trim().isEmpty());

                    //Matricula. Validar formato (NNNNLLL)
                    boolean matriculaValida = false;
                    do {
                        System.out.println("Matricula (formato NNNNLLL): ");
                        matricula = sc.nextLine().toUpperCase();
                        if (matricula.matches("^[0-9]{4}[A-Z]{3}$")) {
                            matriculaValida = true;
                        } else {
                            System.out.println("ERROR: La matricula debe tener el formato NNNNLLL");
                        }
                    } while (!matriculaValida);

                    //Fecha de matriculacion (formato YYYY-MM-DD)
                    while (true) {
                        System.out.println("Fecha de matriculacion (YYYY-MM-DD: ");
                        String fechaStr = sc.nextLine();
                        try {
                            fechaMatricula = LocalDate.parse(fechaStr);
                            break;
                        } catch (Exception e) {
                            System.out.println("ERROR: La fecha debe tener el formato YYYY-MM-DD");
                        }
                    }

                    //Nombre del propietario: AL menos 3 palabras y max 40 caracteres
                    boolean nombreValido = false;
                    do {
                        System.out.println("Nombre del propietario (1 nombre y 2 apellidos)");
                        nombrePropietario = sc.nextLine().trim();
                        String[] partes = nombrePropietario.split("\\s+");
                        if (partes.length >= 3 && nombrePropietario.length() <= 40) {
                            nombreValido = true;
                        } else {
                            System.out.println("ERROR: Debe introducirse un nombre y dos apellidos con un máximo de 40 caracteres.");
                        }
                    } while (!nombreValido);

                    //DNI del propietario: Utiliza la clase DNI y se valida mediante regex.
                    boolean dniValido = false;
                    do {
                        System.out.println("NIF del propietario (12345678Z): ");
                        String nif = sc.nextLine().toUpperCase();
                        try {
                            dniPropietario.establecer(nif);
                            dniValido = true;
                        } catch (Exception e) {
                            System.out.println("ERROR: El formato del NIF debe ser 12345678Z");
                        }
                    } while (!dniValido);

                    // Capacidad del deposito entre 0 y 100
                    while (true) {
                        System.out.println("Capacidad del deposito (entre 0 y 100 litros: ");
                        try {
                            capacidad = Double.parseDouble(sc.nextLine());
                            if (capacidad > 0 && capacidad < 100) {
                                break;
                            } else {
                                System.out.println("ERROR: Capacidad fuera de rango, debe estar entre 0 y 100 litros.");
                            }
                        } catch (Exception e) {
                            System.out.println("ERROR: Solo se pueden introducir valores numericos");
                        }
                    }

                    // Consumo medio (valor positivo)
                    while (true) {
                        System.out.println("Ingrese el consumo medio (litros/100 km): ");
                        try {
                            consumo = Double.parseDouble(sc.nextLine());
                            if (consumo > 0) {
                                break;
                            } else {
                                System.out.println("ERROR: El consumo debe ser positivo.");
                            }
                        } catch (Exception e) {
                            System.out.println("ERROR: Debe introducir un valor numerico.");
                        }
                    }

                    //Kilometros actuales mayor o igual que 0.
                    while (true) {
                        System.out.println("Introduce los kilometros actuales: ");
                        try {
                            kmIniciales = Double.parseDouble(sc.nextLine());
                            if (kmIniciales >= 0) {
                                break;
                            } else {
                                System.out.println("ERROR: Los kilometros deben ser mayor o igual que 0.");
                            }
                        } catch (Exception e) {
                            System.out.println("ERROR: Debe introducir un valor numerico.");
                        }
                    }

                    int resultado = flota.insertarVehiculo(marca, modelo,
                            matricula, fechaMatricula,
                            nombrePropietario, dniPropietario,
                            capacidad, consumo,
                            kmIniciales);

                    if (resultado == 0) {
                        System.out.println("Vehiculo añadido correctamente.");
                    } else if (resultado == -2) {
                        System.out.println("ERROR: El vehiculo ya existe en la flota");
                    }
                    break;

                case "3":
                    //Ver datos identificativos del vehiculo actual
                    System.out.println(flota.verDatosIdentificativos());
                    break;

                case "4":
                    //Ver estado del vehiculo actual
                    System.out.println(flota.verEstadoVehiculo());
                    break;

                case "5":
                    //Viajar
                    System.out.println("Introduce los kilometros a viajar: ");
                    try {
                        double km = Double.parseDouble(sc.nextLine());
                        System.out.println(flota.viajar(km));
                    } catch (Exception e) {
                        System.out.println("ERROR: Debe introducir un valor numerico.");
                    }
                    break;

                case "6":
                    // Repostar
                    System.out.println("Introduce los litros a repostar: ");
                    try {
                        double litros = Double.parseDouble(sc.nextLine());
                        System.out.println(flota.repostar(litros));
                    } catch (Exception e) {
                        System.out.println("ERROR: Debe introducir un valor numerico.");
                    }
                    break;

                case "7":
                    // Llenar deposito 
                    System.out.println(flota.llenar());
                    break;

                case "8":
                    // Actualizar precio combustible 
                    System.out.println("Introduce el precio del combustible: ");
                    try {
                        double nuevoPrecio = Double.parseDouble(sc.nextLine());
                        System.out.println(flota.actualizarPrecioCombustible(nuevoPrecio));
                    } catch (Exception e) {
                        System.out.println("ERROR: Debe introducir un valor numerico.");
                    }
                    break;

                case "9":
                    // Mostrar propietario 
                    System.out.println(flota.mostrarPropietario());
                    break;

                case "10":
                    // Listar vehiculos
                    System.out.println(flota.listaVehiculos());
                    break;

                case "11":
                    if (flota.getNumVehiculos() > 0) {
                        System.out.println("Introduzca la matrícula a eliminar: ");
                        String matriculaEliminar = sc.nextLine();
                        int resultadoEliminar = flota.eliminarVehiculo(matriculaEliminar);
                        if (resultadoEliminar == 0) {
                            System.out.println("Vehiculo eliminado.");
                        } else {
                            System.out.println("ERROR: Matricula no encontrada.");
                        }
                    } else {
                        System.out.println("ERROR: Flota vacia.");
                    }
                    break;

                case "12":
                    salir = true;
                    System.out.println("Saliendo de la aplicación...");
                    break;

                default:
                    System.out.println("Opción no válida");

            }
        }
    }
}
