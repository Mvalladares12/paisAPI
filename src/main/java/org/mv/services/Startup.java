package org.mv.services;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

@Singleton
public class Startup {

    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
        // reset and load all test users
//        User.deleteAll();
//        User.add("admin", "admin", "admin");
//        User.add("writer ", "writer", "writer");
//        User.add("user", "user", "user");
    }
}