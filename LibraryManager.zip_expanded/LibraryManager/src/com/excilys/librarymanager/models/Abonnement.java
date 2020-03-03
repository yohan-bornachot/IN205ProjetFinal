package com.excilys.librarymanager.models;

import java.util.NoSuchElementException;

public enum Abonnement {
	
	BASIC(0, "BASIC"),
	PREMIUM(1, "PREMIUM"), 
	VIP(2, "VIP");
	
    private int value;
    private String label;
    
    /** Constructors */
    Abonnement(int value, String label) {
        this.value = value;
        this.label = label;
    }
    
    /** Methods */
    public static Abonnement valueOf(int value) {
        for (Abonnement abonnement : Abonnement.values()) {
            if (abonnement.value == value) {
                return abonnement;
            }
        }
        throw new NoSuchElementException("no enum for value " + value);
    }

    public String toString() {
        return this.label;
    }
}
