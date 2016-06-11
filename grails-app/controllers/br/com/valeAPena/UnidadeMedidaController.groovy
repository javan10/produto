package br.com.valeAPena

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UnidadeMedidaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond UnidadeMedida.list(params), model:[unidadeMedidaCount: UnidadeMedida.count()]
    }

    def show(UnidadeMedida unidadeMedida) {
        respond unidadeMedida
    }

    def create() {
        respond new UnidadeMedida(params)
    }

    @Transactional
    def save(UnidadeMedida unidadeMedida) {
        if (unidadeMedida == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (unidadeMedida.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond unidadeMedida.errors, view:'create'
            return
        }

        unidadeMedida.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'unidadeMedida.label', default: 'UnidadeMedida'), unidadeMedida.id])
                redirect unidadeMedida
            }
            '*' { respond unidadeMedida, [status: CREATED] }
        }
    }

    def edit(UnidadeMedida unidadeMedida) {
        respond unidadeMedida
    }

    @Transactional
    def update(UnidadeMedida unidadeMedida) {
        if (unidadeMedida == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (unidadeMedida.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond unidadeMedida.errors, view:'edit'
            return
        }

        unidadeMedida.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'unidadeMedida.label', default: 'UnidadeMedida'), unidadeMedida.id])
                redirect unidadeMedida
            }
            '*'{ respond unidadeMedida, [status: OK] }
        }
    }

    @Transactional
    def delete(UnidadeMedida unidadeMedida) {

        if (unidadeMedida == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        unidadeMedida.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'unidadeMedida.label', default: 'UnidadeMedida'), unidadeMedida.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'unidadeMedida.label', default: 'UnidadeMedida'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
