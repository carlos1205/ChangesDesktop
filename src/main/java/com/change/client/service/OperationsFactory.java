package com.change.client.service;

import com.change.client.service.operations.*;

public class OperationsFactory {
    private static OperationsFactory instance;
    private final IHandle operations;

    public static OperationsFactory getInstance(){
        if(null == instance)
            instance = new OperationsFactory();
        return instance;
    }

    private OperationsFactory(){
        this.operations = make();
    }

    private IHandle make(){
        IHandle op = new OpenChatHandle();
        op = new OpenChatHandle(op);
        op = new ReceberMensagemHandle(op);
        op = new CloseHandle(op);
        op = new FecharNegocioHandle(op);
        op = WaitHandle.getInstance(op);
        return op;
    }

    public IHandle getOperations(){
        return this.operations;
    }
}
