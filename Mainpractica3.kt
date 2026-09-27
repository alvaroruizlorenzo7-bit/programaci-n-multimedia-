package `practica Funciones de extensión y lambda`

import java.lang.IllegalArgumentException

// ==========================================
// BASE (Sesión 2): data class Ticket
// ==========================================
data class Ticket(
    val id: Int,
    val descripcion: String,
    var tecnicoAsignado: String? = null,
    var resuelta: Boolean = false,
    val esUrgente: Boolean = false // Propiedad extra para simular urgencia (Ejercicio 7)
)

// ==========================================
// EJERCICIO 1: List<Ticket>.sinAsignar()
// ==========================================
// Función de extensión sobre List<Ticket> que usa filter
fun List<Ticket>.sinAsignar(): List<Ticket> = filter { it.tecnicoAsignado == null }

// ==========================================
// EJERCICIO 2: Ticket.resumen()
// ==========================================
// Función de extensión de expresión única sobre Ticket usando el operador Elvis
fun Ticket.resumen(): String =
    "#$id - $descripcion (${tecnicoAsignado?.let { "asignado a $it" } ?: "sin asignar"})"

// ==========================================
// EJERCICIO 5: List<Ticket>.resueltos()
// ==========================================
// Función de extensión sobre List<Ticket> para filtrar tickets resueltos
fun List<Ticket>.resueltos(): List<Ticket> = filter { it.resuelta }

// ==========================================
// EJERCICIO 6: Extensión sobre receptor nullable (Ticket?)
// ==========================================
// Función de extensión sobre Ticket? que se puede llamar sin la navegación segura ?.
fun Ticket?.descripcionSegura(): String {
    return if (this == null) "(sin ticket)" else resumen()
}

// ==========================================
// EJERCICIO 7: Función de orden superior propia
// ==========================================
// Función de extensión sobre List<Ticket> que recibe una lambda como parámetro
fun List<Ticket>.aplicarATodos(accion: (Ticket) -> Unit) {
    forEach { accion(it) } // Usa forEach internamente sin ningún bucle for
}

// ==========================================
// FUNCIÓN MAIN
// ==========================================
fun main() {
    // Lista mutable de prueba (Panel de tickets)
    val panel: MutableList<Ticket> = mutableListOf(
        Ticket(1, "Impresora atascada", "Marta", resuelta = false),
        Ticket(2, "Ordenador bloqueado", null, resuelta = false, esUrgente = true),
        Ticket(3, "Fallo en la red Wi-Fi", "Carlos", resuelta = true),
        Ticket(4, "Teclado roto en recepción", null, resuelta = false, esUrgente = false),
        Ticket(5, "Monitor no enciende", null, resuelta = true)
    )

    // ------------------------------------------
    // EJERCICIO 3: map + forEach
    // ------------------------------------------
    println("--- EJERCICIO 3: Lista de todos los resúmenes (map + forEach) ---")
    val todosLosResumenes: List<String> = panel.map { it.resumen() }
    todosLosResumenes.forEach { println(it) }
    println()

    // ------------------------------------------
    // EJERCICIO 4: Encadenado
    // ------------------------------------------
    println("--- EJERCICIO 4: Resumen de pendientes de asignar (Encadenado) ---")
    panel.sinAsignar().map { it.resumen() }.forEach { println(it) }
    println()

    // ------------------------------------------
    // EJERCICIO 5: resueltos()
    // ------------------------------------------
    println("--- EJERCICIO 5: Tickets resueltos ---")
    val listaResueltos = panel.resueltos()
    println("Número de tickets resueltos: ${listaResueltos.size}")
    listaResueltos.forEach { println(it.resumen()) }
    println()

    // ------------------------------------------
    // EJERCICIO 6: Receptor nullable (Ticket?)
    // ------------------------------------------
    println("--- EJERCICIO 6: Prueba con receptor nullable ---")
    val ticketExistente: Ticket? = panel.firstOrNull()
    val ticketNulo: Ticket? = null

    // Se llaman directamente sin el operador ?.
    println("Con ticket válido: ${ticketExistente.descripcionSegura()}")
    println("Con ticket nulo:   ${ticketNulo.descripcionSegura()}")
    println()

    // ------------------------------------------
    // EJERCICIO 7: Trailing lambda con aplicarATodos
    // ------------------------------------------
    println("--- EJERCICIO 7: Función de orden superior propia ---")
    // Invocación usando sintaxis de trailing lambda (lambda fuera de los paréntesis)
    panel.aplicarATodos { ticket ->
        println(ticket.resumen())
        if (ticket.tecnicoAsignado == null && ticket.esUrgente) {
            println(" -> URGENTE")
        }
    }
}