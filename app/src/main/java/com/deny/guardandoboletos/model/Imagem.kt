package com.deny.guardandoboletos.model

class Imagem(
    var escolherImagem: Int
) {
    fun toMap() : HashMap<String, Any> {

        var resultImagem: HashMap<String, Any> = HashMap<String, Any>()
        resultImagem.put("imagem", this.escolherImagem)

        return resultImagem
    }
}