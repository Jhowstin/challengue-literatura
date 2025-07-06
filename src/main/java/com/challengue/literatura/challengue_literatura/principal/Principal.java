package com.challengue.literatura.challengue_literatura.principal;

import com.challengue.literatura.challengue_literatura.model.*;
import com.challengue.literatura.challengue_literatura.repository.AutorRepository;
import com.challengue.literatura.challengue_literatura.repository.LibroRepository;
import com.challengue.literatura.challengue_literatura.service.ConsumoAPI;
import com.challengue.literatura.challengue_literatura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/";

    private final ConsumoAPI consumoApi;
    private final ConvierteDatos conversor;
    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;
    private final Scanner teclado;

    @Autowired
    public Principal(ConsumoAPI consumoApi,
                     ConvierteDatos conversor,
                     AutorRepository autorRepository,
                     LibroRepository libroRepository) {
        this.consumoApi = consumoApi;
        this.conversor = conversor;
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
        this.teclado = new Scanner(System.in);
    }

    public void muestraMenu() {
        int opcion = -1;
        while (opcion != 0) {
            String menu = """
                    Menu de Opciones
                    1 - Buscar libro por titulo
                    2 - Buscar autor por nombre
                     0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> buscarAutorPorNombre();
            }
        }
    }

    public void buscarLibroPorTitulo() {
        System.out.println("Ingresa el nombre del libro que deseas buscar:");
        String nombreLibro = teclado.nextLine();

        String json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));

        if (json == null || json.isEmpty()) {
            System.out.println("No se pudo obtener datos de la API para ese libro.");
            return;
        }

        Datos datos = conversor.obtenerDatos(json, Datos.class);

        if (datos.resultados().isEmpty()) {
            System.out.println("No se encontraron libros con ese título.");
            return;
        }

        DatosLibros datosLibro = datos.resultados().get(0);

        DatosAutor datosAutor = datosLibro.autor().get(0);

        // Buscar autor existente
        Optional<Autor> autorExistente = autorRepository.findByNombre(datosAutor.nombre());

        Autor autor;

        if (autorExistente.isPresent()) {
            autor = autorExistente.get();
            // No llamar a save() para evitar intentar insertar un autor que ya existe
        } else {
            autor = new Autor(
                    datosAutor.nombre(),
                    datosAutor.fechaDeNacimiento(),
                    datosAutor.fechaDeMuerte()
            );
            autor = autorRepository.save(autor); // sólo guardar si es nuevo
        }

        Libro libro = new Libro(
                datosLibro.titulo(),
                datosLibro.numeroDeDescargas(),
                datosLibro.idiomas(),
                autor
        );

        libroRepository.save(libro);
        System.out.println("Libro guardado correctamente: " + libro.getTitulo());
    }


    private void buscarAutorPorNombre() {
        System.out.println("Ingresa el nombre del autor que deseas buscar:");
        String nombreAutor = teclado.nextLine();

        // Verificar si ya existe en la base de datos
        Optional<Autor> autorExistente = autorRepository.findByNombreContainingIgnoreCase(nombreAutor);

        if (autorExistente.isPresent()) {
            System.out.println("🧾 Autor encontrado en la base de datos:");
            Autor autor = autorExistente.get();
            System.out.println("Nombre: " + autor.getNombre());
            System.out.println("Nacimiento: " + autor.getFechaDeNacimiento());
            System.out.println("Fallecimiento: " + autor.getFechaDeMuerte());
            return;
        }

        // Si no existe en la base de datos, buscar en la API
        String json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreAutor.replace(" ", "+"));
        Datos datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        // Buscar el primer autor coincidente
        Optional<DatosAutor> autorAPI = datosBusqueda.resultados().stream()
                .flatMap(libro -> libro.autor().stream())
                .filter(a -> a.nombre().toUpperCase().contains(nombreAutor.toUpperCase()))
                .findFirst();

        if (autorAPI.isPresent()) {
            DatosAutor datosAutor = autorAPI.get();

            Autor autor = new Autor(
                    datosAutor.nombre(),
                    datosAutor.fechaDeNacimiento(),
                    datosAutor.fechaDeMuerte()
            );

            autorRepository.save(autor);

            System.out.println("✅ Autor encontrado en la API y guardado en la base de datos:");
            System.out.println("Nombre: " + autor.getNombre());
            System.out.println("Nacimiento: " + autor.getFechaDeNacimiento());
            System.out.println("Fallecimiento: " + autor.getFechaDeMuerte());
        } else {
            System.out.println("❌ No se encontró el autor en la API.");
        }
    }
}

