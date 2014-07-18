package test;

import datastruct.HashMap;

class Name {
	private String first;
	private String last;

	public Name(String first, String last) {
		this.first = first;
		this.last = last;
	}

	// 根据 first 判断两个 Name 是否相等
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o.getClass() == Name.class) {
			Name n = (Name) o;
			return n.first.equals(first);
		}
		return false;
	}

	// 根据 first 计算 Name 对象的 hashCode() 返回值  
	@Override
	public int hashCode() {
		return this.first.hashCode();
	}
	
}

public class HashTest {
	public static void main(String[] args) {
		Name name = new Name("123", "abc111");
		HashMap<Name, String> map = new HashMap<Name, String>();
		map.put(name, "123abc");
		map.put(new Name("123", "abc"), "123dcfsdfabc");
		map.put(new Name("123", "abc"), "123dc");
		System.out.println(map.get(new Name("123", "abc")));
	}

}
