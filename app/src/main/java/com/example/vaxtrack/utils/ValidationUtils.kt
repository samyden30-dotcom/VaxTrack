package com.example.vaxtrack.utils

object ValidationUtils {

    fun validateChefMenage(nom: String): Boolean {
        return nom.isNotBlank() && nom.length >= 3
    }

    fun validateContact(contact: String): Boolean {
        // Vérifie que le contact est un numéro ou un email simple
        return contact.isNotBlank() &&
                (contact.matches(Regex("^\\+?[0-9]{7,15}$")) ||
                        contact.contains("@"))
    }

    fun validateNbPersonnes(nb: Int): Boolean {
        return nb > 0
    }

    fun validateNbDoses(nb: Int, nbPersonnes: Int): Boolean {
        return nb in 0..nbPersonnes
    }
}
