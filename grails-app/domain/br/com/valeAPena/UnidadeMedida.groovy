package br.com.valeAPena

class UnidadeMedida {
    String nome;
    
    static belongsTo = [produto:Produto]
    
    static constraints = {
        nome nullable:false, blank:false, maxSize:128, unique:true
    }
}
