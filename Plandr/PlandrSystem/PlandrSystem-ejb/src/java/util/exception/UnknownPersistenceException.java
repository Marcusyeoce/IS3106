/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Pham The Dzung
 */
public class UnknownPersistenceException extends Exception {

    public UnknownPersistenceException() {
    }

    public UnknownPersistenceException(String message) {
        super(message);
    }
    
}
