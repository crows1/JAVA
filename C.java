class C 
{
	C(){
		//m1();
		m2();
	}
	void m1(){
		System.out.println("5"); 
	}
	void m2(){
		System.out.println("6"); 
	}
	public static void main(String[] args) 
	{
		System.out.println("1"); 
		//C c = new C();
		//c.m1();
		new C().m1();
		System.out.println("3"); 
	}
}

//¹° ÄÅ = new ¹°(); 
//¿ìÀ¯ ÄÅ = new ¿ìÀ¯();
