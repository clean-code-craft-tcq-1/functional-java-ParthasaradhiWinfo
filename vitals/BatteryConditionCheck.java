package vitals;

import java.util.function.Function;

public class BatteryConditionCheck {

	static boolean batteryIsOk(float temperature, float soc, float chargeRate) {
		try {
			Function<Float, Function<Float, Boolean>> socCheckMethod = temperatureCheck(temperature);
			Function<Float, Boolean> chargeRateCheckMethod = socCheckMethod.apply(soc);
			return chargeRateCheckMethod.apply(chargeRate);
		} catch (NullPointerException ne) {
			return false;
		}
	}

	static Function<Float, Boolean> chargeRateCheck = (chargeRate) -> {
		if (chargeRate > 0.8) {
			System.out.println("Battery charge rate check failed");
			return false;
		}
		return true;
	};
	static Function<Float, Function<Float, Boolean>> socCheck = (soc) -> {
		if (soc < 20 || soc > 80) {
			System.out.println("Battery soc check failed");
			return null;
		}
		return chargeRateCheck;
	};

	static Function<Float, Function<Float, Boolean>> temperatureCheck(float temperature) {
		if (temperature < 0 || temperature > 45) {
			System.out.println("Battery temperature check failed");
			return null;
		}
		return socCheck;
	}
}