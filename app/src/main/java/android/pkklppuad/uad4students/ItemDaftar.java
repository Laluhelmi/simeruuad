package android.pkklppuad.uad4students;

public class ItemDaftar {
	
	private String name;
	private String brand;

	public ItemDaftar(String name, String brand) {
		setName(name);
		setBrand(brand);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

}
