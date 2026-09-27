import java.lang.IllegalArgumentException

// ==========================================
// EJERCICIO 0 y 1: data class Ticket
// ==========================================
data class Ticket(
    val id: Int,
    val descripcion: String,
    var tecnicoAsignado: String? = null,
    var resuelta: Boolean = false
)

// Variable global
var contadorId = 1

// ==========================================
// EJERCICIO 2: Validación con Elvis y salida temprana
// ==========================================
fun crearTicket(descripcion: String?): Ticket {
    // Uso de ?: con throw como salida temprana / validación
    val descValida = descripcion?.takeIf { it.isNotBlank() }
        ?: throw IllegalArgumentException("La descripción no puede ser null ni estar en blanco")

    return Ticket(
        id = contadorId++,
        descripcion = descValida
    )
}

fun descripcionTecnico(ticket: Ticket): String {
    // Uso del operador Elvis (?:) para proporcionar un valor por defecto si es null
    return ticket.tecnicoAsignado ?: "Sin asignar"
}

// ==========================================
// EJERCICIO 3: ?.let {}
// ==========================================
fun notificar(ticket: Ticket) {
    // Se ejecuta el bloque solo si tecnicoAsignado NO es null
    ticket.tecnicoAsignado?.let { nombreTecnico ->
        println("Notificando a $nombreTecnico")
    }
}

// ==========================================
// EJERCICIO 4: Conversión segura con as?
// ==========================================
fun procesarElementos(elementos: List<Any>) {
    println("--- EJERCICIO 4: Procesando lista mixta con as? ---")
    for (elem in elementos) {
        val ticket = elem as? Ticket
        if (ticket != null) {
            println("Ticket detectado: ${ticket.descripcion}")
        } else {
            println("Elemento ignorado (no es un ticket)")
        }
    }
    println()
}

// ==========================================
// EJERCICIO 5: Igualdad estructural y de referencia
// ==========================================
fun probarIgualdad() {
    println("--- EJERCICIO 5: Igualdad estructural y de referencia ---")
    val t1 = Ticket(1, "Error en el proyector del Aula 102", "Ana")
    val t2 = Ticket(1, "Error en el proyector del Aula 102", "Ana")

    // == compara contenido (llama a equals de la data class) -> Devuelve true
    println("t1 == t2 : ${t1 == t2} (Tienen el mismo contenido)")

    // === compara referencias de memoria -> Devuelve false (son instancias distintas)
    println("t1 === t2: ${t1 === t2} (Son objetos distintos en memoria)")

    // Uso del método copy() para duplicar modificando un parámetro
    val t3 = t1.copy(resuelta = true)
    println("t1 == t3 : ${t1 == t3} (Ya no son iguales al cambiar 'resuelta')")
    println()
}

// ==========================================
// EJERCICIO 6: Panel de tickets (Integrador)
// ==========================================
fun panelDeTickets() {
    println("--- EJERCICIO 6: Panel de tickets ---")

    // Lista mutable con al menos 5 tickets (algunos con técnico null)
    val tickets: MutableList<Ticket> = mutableListOf(
        Ticket(1, "Fallo en la red Wi-Fi", "Carlos"),
        Ticket(2, "Monitor no enciende en Lab 3", null),
        Ticket(3, "Actualización de SO en Aula 201", "Laura"),
        Ticket(4, "Teclado roto en recepción", null),
        Ticket(5, "Impresora sin tónner", null)
    )

    // Recorrido con bucle for mostrando descripción y estado del técnico
    for (t in tickets) {
        println("ID: ${t.id} | Desc: ${t.descripcion} | Técnico: ${descripcionTecnico(t)}")
    }

    // Conteo manual con for (sin usar filter/count con lambdas)
    var sinAsignarCount = 0
    for (t in tickets) {
        if (t.tecnicoAsignado == null) {
            sinAsignarCount++
        }
    }

    println("\nTotal de tickets sin técnico asignado: $sinAsignarCount")
}

// ==========================================
// FUNCIÓN MAIN PARA PROBAR TODO
// ==========================================
fun main() {
    // Prueba Ejercicio 2
    println("--- EJERCICIO 2: Crear Ticket y validación ---")
    val ticketCreado = crearTicket("Configurar nuevo router")
    println("Ticket creado exitosamente: $ticketCreado")

    try {
        crearTicket("   ") // Lanza excepción
    } catch (e: IllegalArgumentException) {
        println("Excepción capturada correctamente: ${e.message}")
    }
    println()

    // Prueba Ejercicio 3
    println("--- EJERCICIO 3: Demostración ?.let ---")
    val ticketConTecnico = Ticket(99, "Fallo de audio", "Marta")
    val ticketSinTecnico = Ticket(100, "Fallo de audio", null)

    print("Intento notificar a ticket con técnico: ")
    notificar(ticketConTecnico) // Imprime mensaje

    print("Intento notificar a ticket sin técnico: ")
    notificar(ticketSinTecnico) // No imprime nada
    println()

    // Prueba Ejercicio 4
    val listaMixta: List<Any> = listOf(
        Ticket(10, "Pantalla azul", "Pedro"),
        "Aviso del sistema: Mantenimiento programado",
        404,
        Ticket(11, "Ratón sin pila", null)
    )
    procesarElementos(listaMixta)

    // Prueba Ejercicio 5
    probarIgualdad()

    // Prueba Ejercicio 6
    panelDeTickets()
}