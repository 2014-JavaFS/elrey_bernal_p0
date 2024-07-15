package com.revature.bankingapp.util.interfaces;

import io.javalin.Javalin;

public interface Controller {
    void registerPaths(Javalin app);
}
