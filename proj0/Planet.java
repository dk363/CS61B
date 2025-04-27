public class Planet {
	private static final double G = 6.67e-11;

	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public Planet(double xP, double yP, double xV, 
					double yV, double m, String img) {
			this.xxPos = xP;
			this.yyPos = yP;
			this.xxVel = xV;
			this.yyVel = yV;
			this.mass = m;
			this.imgFileName = img;
	}

	public Planet(Planet p) {
		this.xxPos = p.xxPos;
		this.yyPos = p.yyPos;
		this.xxVel = p.xxVel;
		this.yyVel = p.yyVel;
		this.mass = p.mass;
		this.imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet that) {
		double dx = that.xxPos - this.xxPos;
		double dy = that.yyPos - this.yyPos;
		return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
	}

	public double calcForceExertedBy(Planet that) {
		double distance = this.calcDistance(that);
		return (G * this.mass * that.mass) / (distance * distance);
	}

	public double calcForceExertedByX(Planet that) {
		double dx = that.xxPos - this.xxPos;
		return dx / this.calcDistance(that) * this.calcForceExertedBy(that);
	}

	public double calcForceExertedByY(Planet that) {
		double dy = that.yyPos - this.yyPos;
		return dy / this.calcDistance(that) * this.calcForceExertedBy(that);
	}

	public double calcNetForceExertedByX(Planet that[]) {
		double dx = 0.0;
		double forceX = 0.0;
		for (Planet p : that) {
			if (this.equals(p)) {
				continue;
			} else {	
				forceX += calcForceExertedByX(p);
			}
		}
		return forceX;
	}

	public double calcNetForceExertedByY(Planet that[]) {
		double dy = 0.0;
		double forceY = 0.0;
		for (Planet p : that) {
			if (this.equals(p)) {
				continue;
			} else {
				forceY += calcForceExertedByY(p);
			}
		}
		return forceY;
	}

	public void update(double dt, double fX, double fY) {
		double ax = fX / this.mass;
		double ay = fY / this.mass;
		this.xxVel += dt * ax;
		this.yyVel += dt * ay;
		this.xxPos += dt * this.xxVel;
		this.yyPos += dt * this.yyVel;
	}

	public void draw() {
		StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
	}
}

