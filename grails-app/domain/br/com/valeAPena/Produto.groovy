package br.com.valeAPena

class Produto {

    String nome
    
    static belongsTo = [categoria:Categoria]
    
    static constraints = {
        nome nullable:false, blank:false, maxSize:128, unique:true
    }
}
