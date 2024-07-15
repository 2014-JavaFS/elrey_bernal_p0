package com.revature.bankingapp.util.interfaces;

import com.revature.bankingapp.util.exceptions.InvalidInputException;
import java.util.List;

public interface Serviceable<O> {
    List<O> findAll();
    O create(O newObject);
    O findById(int number);
}
