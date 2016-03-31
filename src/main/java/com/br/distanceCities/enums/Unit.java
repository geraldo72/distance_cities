package com.br.distanceCities.enums;

public enum Unit {

	KM,
	MI;
	
	public static Unit fromName(final String name) {
        for (Unit s : Unit.values()) {
            if (s.name().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }
	
}
