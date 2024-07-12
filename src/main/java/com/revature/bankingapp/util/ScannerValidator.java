package com.revature.bankingapp.util;

import java.util.Scanner;

@FunctionalInterface
public interface ScannerValidator {
    boolean isValid(Scanner scanner, String errorMessage);
}
