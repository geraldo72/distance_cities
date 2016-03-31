package com.br.distanceCities.core;

import java.math.BigDecimal;

import com.br.distanceCities.enums.Unit;
import com.br.distanceCities.exception.DistanceException;
import com.br.distanceCities.exception.VincentyException;
import com.br.distanceCities.model.City;
import com.br.distanceCities.model.Distance;

public class DistanceCalculation {

	private class Constants {
		private static final double EARTH_MAJOR_AXIS_KM = 6378.1370;
		private static final double EARTH_MINOR_AXIS_KM = 6356.7523;
		private static final double EARTH_MAJOR_AXIS_MI = 3963.1906;
		private static final double EARTH_MINOR_AXIS_MI = 3949.9028;

		// Precisao de aproximadamente 0,06mm
		private static final double MIN_VALUE_APROX = 1e-12;
	}

	private double minorAxis;
	private double majorAxis;
	private City city1;
	private City city2;
	private Unit unit;

	public DistanceCalculation(Unit unit, City city1, City city2) {
		super();
		switch (unit) {
		case MI:
			majorAxis = Constants.EARTH_MAJOR_AXIS_MI;
			minorAxis = Constants.EARTH_MINOR_AXIS_MI;
			break;

		default:// Usa valores em metro como padrao
			majorAxis = Constants.EARTH_MAJOR_AXIS_KM;
			minorAxis = Constants.EARTH_MINOR_AXIS_KM;
			break;
		}
		this.unit = unit;
		this.city1 = city1;
		this.city2 = city2;
	}

	public Distance getDistance() throws DistanceException {

		double distance;
		try {
			distance = vincentyMethod();
			Distance ret = new Distance(city1, city2, new BigDecimal(distance), unit);
			return ret;
		} catch (VincentyException e) {
			throw new DistanceException("Não foi possivel calcular a distancia entre >"+city2.getName()+"< e >"+city2.getName()+"<",e);
		}


	}

	private double vincentyMethod() throws VincentyException {
		// Coordenadas da cidade
		double lat1 = Math.toRadians(city1.getLatitude().doubleValue());
		double lon1 = Math.toRadians(city1.getLongitude().doubleValue());
		double lat2 = Math.toRadians(city2.getLatitude().doubleValue());
		double lon2 = Math.toRadians(city2.getLongitude().doubleValue());

		// Valores auxiliares
		double flattening = (majorAxis - minorAxis) / majorAxis;

		// Latitude Reduzida
		double U1 = Math.atan((1 - flattening) * Math.tan(lat1));
		double U2 = Math.atan((1 - flattening) * Math.tan(lat2));

		// Senos da latitude reduzida
		double sinU1 = Math.sin(U1);
		double sinU2 = Math.sin(U2);

		// Cossenos da latitude reduzida
		double cosU1 = Math.cos(U1);
		double cosU2 = Math.cos(U2);

		//Variaveis utilizadas na iteracao
		double sinLambda;
		double cosLambda;
		double sinSigma;
		double cosSigma;
		double sigma;
		double sinAlpha;
		double cos2Alpha;
		double cos2SigmaM;
		double C;

		// Primeira aproximacao
		double lambda = lon2 - lon1;
		double lambdaP;
		int tries = 100;
		do {
			//1 - Calcular seno sigma
			sinLambda = Math.sin(lambda);
			cosLambda = Math.cos(lambda);
			sinSigma = Math.sqrt((cosU2 * sinLambda) * (cosU2 * sinLambda)
					+ (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda) * (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda));
			if (sinSigma == 0) {
				throw new VincentyException("Seno do sigma igual a 0");
			}
			//2 - Calcular coseno de sigma
			cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda;
			
			//3 - Calcular sigma
			sigma = Math.atan2(sinSigma, cosSigma);
			
			//4 - Calcular seno de alpha
			sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma;
			
			//5 - Calcular cosseno quadrado de alpha
			cos2Alpha = 1 - sinAlpha * sinAlpha;
			
			//6 - Calcular cosseno de 2 sigma m
			cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cos2Alpha;

			//7 - Calcular C
			C = flattening / 16 * cos2Alpha * (4 + flattening * (4 - 3 * cos2Alpha));
			
			//Armazena valor anterior do lambda
			lambdaP = lambda;
			
			//Calcula novamente o valor de lambda
			lambda = (lon2 - lon1) + (1 - C) * flattening * sinAlpha
					* (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)));

		} while (Math.abs(lambda - lambdaP) > Constants.MIN_VALUE_APROX && --tries > 0);

		if (tries == 0) {
			throw new VincentyException("Lambda nao convergiu em 100 tentativas");
		}
		
		//Calcula valor de u quadrado
		double u2 = cos2Alpha * (majorAxis * majorAxis - minorAxis * minorAxis) / (minorAxis * minorAxis);
		
		//Calculo de A e B utilizando formula de expansao de Helmert
		double u2Sq = Math.sqrt(1+u2);
		double k1 = (u2Sq-1)/(u2Sq+1);
		double A = (1+(k1*k1)/4)/(1-k1);
		double B = k1*(1-(3/8)*(k1*k1));
		
		//Calcula delta do sigma
		double deltaSigma = B * sinSigma * (cos2SigmaM + B / 4 * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)
				- B / 6 * cos2SigmaM * (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)));

		//Retorna valor da distancia
		return minorAxis * A * (sigma - deltaSigma);
	}

}
