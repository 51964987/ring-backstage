package test.ringbackstage.log;

import java.util.regex.Pattern;

import org.junit.Test;

public class ExculePathTest {
	
	@Test
	public void test(){
//		String key = "mDataProp_*";
//		String str = "mDataProp_5";
//		System.out.println(Pattern.matches(key, str));
		String key = "/*/trees";
		String str = "/ssss/trees";
		System.out.println(Pattern.matches(key, str));
	}
}
