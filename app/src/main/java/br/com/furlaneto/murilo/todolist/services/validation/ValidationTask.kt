package br.com.furlaneto.murilo.todolist.services.validation

import br.com.furlaneto.murilo.todolist.model.Task

class ValidationTask {

    companion object {
        const val MIN_TITLE_LENGTH = 3
        const val MAX_TITLE_LENGTH = 100
        const val MAX_DESCRIPTION_LENGTH = 500

        const val MIN_DESCRIPTION_LENGTH = 3
    }

    fun validate(task: Task): ValidationResult {
        val errors = mutableListOf<ValidationError>()

        if (task.title.isBlank()) {
            errors.add(ValidationError(Field.TITLE, "O título não pode estar vazio ou conter apenas espaços."))
        } else {
            if (task.title.length < MIN_TITLE_LENGTH) {
                errors.add(ValidationError(Field.TITLE, "O título deve ter pelo menos $MIN_TITLE_LENGTH caracteres significativos."))
            }
            if (task.title.length > MAX_TITLE_LENGTH) {
                errors.add(ValidationError(Field.TITLE, "O título não pode exceder $MAX_TITLE_LENGTH caracteres."))
            }
        }

        if (task.description.length > MAX_DESCRIPTION_LENGTH) {
            errors.add(ValidationError(Field.DESCRIPTION, "A descrição não pode exceder $MAX_DESCRIPTION_LENGTH caracteres."))
        }
         if (task.description.isNotBlank() && task.description.length < MIN_DESCRIPTION_LENGTH) {
            errors.add(ValidationError(Field.DESCRIPTION, "A descrição fornecida é muito curta."))
         }

        return if (errors.isEmpty()) {
            ValidationResult.Success
        } else {
            ValidationResult.Failure(errors)
        }
    }


}
