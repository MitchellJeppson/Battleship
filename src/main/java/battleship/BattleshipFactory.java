package battleship;

import java.util.HashMap;

public class BattleshipFactory {
	private static BattleshipFactory factory = new BattleshipFactory();
	private HashMap<String, Class<?>> registerMap = new HashMap<String, Class<?>>();
	
	private BattleshipFactory(){}
	public void registerProduct(String productId, Class<?> productClass){
		registerMap.put(productId, productClass);
	}
	
	public static BattleshipFactory instance(){
		return factory;
	}
	public BattleshipObject createProduct(String productId){
		Class<?> productClass = registerMap.get(productId);
		try {
			return (BattleshipObject) productClass.newInstance();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return null;
	}


}