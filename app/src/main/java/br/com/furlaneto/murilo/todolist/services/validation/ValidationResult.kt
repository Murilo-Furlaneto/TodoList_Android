package br.com.furlaneto.murilo.todolist.services.validation

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Failure(val errors: List<ValidationError>) : ValidationResult()
}

data class ValidationError(
    val field: Field,
    val message: String
)

enum class Field {
    TITLE,
    DESCRIPTION,
}