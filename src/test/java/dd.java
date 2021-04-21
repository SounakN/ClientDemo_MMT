import com.automation.driver.gg;

public class dd extends gg {
	public dd( int a, int b ){
        super(a,b);

    }
    
    public void area() {
       System.out.print("Rectangle class area :");
        System.out.print(width * height); 
    }
    public void area2 () {
      System.out.print("Rectangle class area 2:");
        System.out.print(width * height); 
    }
    public static void main(String args[]) {
    	 dd x = new dd(10,7);
    	    x.area();
    }
   
}
