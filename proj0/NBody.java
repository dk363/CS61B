public class NBody {
	public static double readRadius(String filename) {
		In in = new In(filename);
		int numberPlants = in.readInt();
		return in.readDouble();
	}

	public static Planet[] readPlanets(String filename) {
		In in = new In(filename);
		int numberPlanets = in.readInt();
		double radius = in.readDouble();

		Planet[] p = new Planet[numberPlanets];
		for (int i = 0; i < numberPlanets; i++) {
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String imgFileName = in.readString();

			p[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
		}

		return p;
	}

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2]; 

		Planet[] planets = readPlanets(filename);
		double radius = readRadius(filename);
		
		StdDraw.setScale(-radius, radius);
		StdDraw.enableDoubleBuffering();
		// StdDraw.clear();

		StdDraw.picture(0, 0, "images/starfield.jpg");

		for (Planet planet : planets) {
			planet.draw();
		}

		double t = 0.0;

		while (t != T) {
			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];

			for (int i = 0; i < planets.length; i++) {
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
				planets[i].update(dt, xForces[i], yForces[i]);	
			}

			StdDraw.picture(0, 0, "images/starfield.jpg");
			for (Planet planet : planets) {
				planet.draw();
			} 
			StdDraw.show();
			StdDraw.pause(10);
			t += dt;

		}
		StdDraw.show();

		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", 
							planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
							planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
		}
	}

}