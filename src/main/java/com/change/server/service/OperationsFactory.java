package com.change.server.service;

import com.change.server.operations.IOperation;
import com.change.server.operations.Login;
import com.change.server.operations.Logout;

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
        return op;
    }

    public IOperation getOperations(){
        return this.operations;
    }
}
