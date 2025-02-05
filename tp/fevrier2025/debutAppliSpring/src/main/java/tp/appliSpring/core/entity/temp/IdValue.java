package tp.appliSpring.core.entity.temp;

public class IdValue {

	private Long id;
	private String value;
	
	public IdValue() {
		this(0L,"");
	}

	public IdValue(Long id, String value) {
		super();
		this.id = id;
		this.value = value;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "IdValue [id=" + id + ", value=" + value + "]";
	}
	
	

}
