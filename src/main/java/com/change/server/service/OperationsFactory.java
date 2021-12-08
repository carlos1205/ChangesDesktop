package com.change.server.service;

import com.change.server.operations.*;

public class OperationsFactory {
    private static OperationsFactory instance;

    private final IOperation operations;

    public static OperationsFactory getInstance(){
        if(null == instance)
            instance = new OperationsFactory();
        return instance;
    }

    private OperationsFactory(){
        this.operations = make();
    }

    private IOperation make(){
        IOperation op = new Login();
        op = new Logout(op);
        op = new Cadastro(op);
        op = new Edicao(op);
        op = new Delecao(op);
        op = new RecuperarSenha(op);
        op = new CadastroSP(op);
        op = new ListagemSP(op);
        op = new EditarSP(op);
        return op;
    }

    public IOperation getOperations(){
        return this.operations;
    }
}
