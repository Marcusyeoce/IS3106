/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author oimun
 */
public class CompanyExistException extends Exception {

    public CompanyExistException() {
    }

    public CompanyExistException(String message) {
        super(message);
    }
    
}
