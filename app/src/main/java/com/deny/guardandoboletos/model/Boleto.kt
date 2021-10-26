package com.deny.guardandoboletos.model

class Boleto(
    var id: String = "",
    var titulo: String = "",
    var prioridadeBoleto: String = "",
    var valorBoleto: String = "",
    var dataBoleto: String = "",
    var avatar: Int = 0
) {
    fun toMap() : HashMap<String, Any> {

        var result : HashMap<String, Any> = HashMap<String, Any>()
        result.put("id", this.id)
        result.put("titulo", this.titulo)
        result.put("prioridadeBoleto", this.prioridadeBoleto)
        result.put("valorBoleto", this.valorBoleto)
        result.put("dataBoleto", this.dataBoleto)
        result.put("avatar", this.avatar)

        return result
    }
}