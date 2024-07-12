package com.revature.bankingapp.util.interfaces;

public interface Crudable<O> extends Serviceable<O> {
    boolean update(O updatedObject);
    boolean delete(int number);
}
