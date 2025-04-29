public class Dog {
	public int weightInPounds;

	public Dog(int w) {
		weightInPounds = w;
	}

	public Dog maxDog(Dog d2) {
		if (weightInPounds > d2.weightInPounds) {
			return this;
		}
		return d2;
	}

	public void makeNoise() {
		if (weightInPounds < 10) {
			System.out.println("yip");
		} else if (weightInPounds < 30) {
			System.out.println("bark");
		} else {
			System.out.println("woof");
		}
	}

	public static void main(String[] args) {
		Dog d1 = new Dog(15);
		Dog d2 = new Dog(6);

		Dog biggerDog = d1.maxDog(d2);
		biggerDog.makeNoise();
	}
}