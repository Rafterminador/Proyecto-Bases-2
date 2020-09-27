/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.url.proyectobases2;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author ferna
 */
public class cifrado {
    public String run(String pass) {
        // default argon2i, salt 16 bytes, hash length 32 bytes.
        Argon2 argon2 = Argon2Factory.create();

        char[] password = pass.toCharArray();
        Instant start = Instant.now();  // start timer
        String hash = "";
        try {
            // iterations = 10
            // memory = 64m
            // parallelism = 1
            hash = argon2.hash(22, 65536, 1, password);
            
            
            
//            String hash = "$argon2i$v=19$m=65536,t=22,p=1$IDBdO7oy/R0kWzTX0RWvIg$8yPEJO9sb8ZdlsDxxC62o9HTPL2QzqUztkk8d/9fEnU";
//            System.out.println(hash);
//
//            // argon2 verify hash
//            if (argon2.verify(hash, password)) {
//                System.out.println("Hash matches password.");
//            }

            //int iterations = Argon2Helper.findIterations(argon2, 1000, 65536, 1);
            //System.out.println(iterations);

        } finally {
            // Wipe confidential data
            argon2.wipeArray(password);
        }

//        Instant end = Instant.now();    // end timer

//        System.out.println(String.format(
//                "Hashing took %s ms",
//                ChronoUnit.MILLIS.between(start, end)
//        ));
        return hash;
    }
}
